
---

## 1. System Overview

This project simulates a **GPS-denied swarm drone system** designed to autonomously explore and map an unknown, randomly generated maze environment. The system consists of **one mother drone** and **four mapper drones**, all operating without GPS — relying instead on onboard IMUs, Time-of-Flight (ToF) sensors, and an Extended Kalman Filter (EKF) for navigation and state estimation.

The codebase has two execution modes:

|Mode|Entry Point|Physics|
|---|---|---|
|Full Physics|`run_simulation.py`|MuJoCo 1 kHz rigid-body simulation|
|Maze Mapping|`maze_sim.py`|Lightweight kinematic simulation|

The live telemetry from both modes is streamed over WebSocket (`server.py`) to a browser dashboard at ~20 Hz.

---

## 2. Maze Generation

**File:** `maze_sim.py → MazeGenerator`

The maze is procedurally generated using a **recursive backtracker (DFS) algorithm** — one of the most common perfect-maze generators.

### Algorithm

1. Start at cell (0,0) with a stack containing only that cell.
2. At each step, pick a random unvisited neighbor, remove the wall between them, and push the neighbor onto the stack.
3. If no unvisited neighbors exist, backtrack (pop the stack).
4. Continue until all cells are visited.

This guarantees:

- **Perfect maze**: exactly one path between any two cells (no loops, no isolated regions).
- **Full connectivity**: every cell is reachable from every other cell.
- **Reproducibility**: a `seed` parameter produces the same maze on every run. Without a seed, a random one is generated.

### Grid Structure

- The maze is divided into **2 m × 2 m cells** (`CELL = 2.0`).
- Walls are stored separately as:
    - `h_walls[r][c]` — horizontal wall on the **south edge** of cell (r, c)
    - `v_walls[r][c]` — vertical wall on the **east edge** of cell (r, c)
- Border walls are implicitly always present.
- Default grid: **10 × 10** (100 cells, 20 m × 20 m total area). Configurable via `--rows` / `--cols`.

---

## 3. Drone Hardware Model

### 3.1 Mother Drone

|Property|Value|
|---|---|
|Role|Aerial coordinator, map aggregator|
|Altitude|**5.0 m** (above 3 m walls)|
|Mass|2.0 kg|
|Arm length|0.23 m|
|Max total thrust|~39.2 N (4 × 9.81 N)|
|Max horizontal speed|3.0 m/s|
|Max vertical speed|2.0 m/s|
|Position|Centre of maze|

The mother drone hovers statically above the maze centre. Its primary role is **not navigation** but **map aggregation**: it receives wall detection reports from all mapper drones and builds a unified wall map.

### 3.2 Mapper Drones (×4)

|Property|Value|
|---|---|
|Role|Navigation, ToF sensing, wall detection|
|Altitude|**1.5 m** (below 3 m walls — within the maze corridors)|
|Mass|0.3 kg|
|Arm length|0.105 m|
|Max total thrust|~7.36 N (2.5× hover margin)|
|Max horizontal speed|4.0 m/s|
|Deployment|One from each corner of the maze|

The 1.5 m altitude is a deliberate design choice: maze walls are 3 m tall, so mapper drones fly **within the maze passages**, where their ToF sensors can detect walls directly. The mother drone flies **above the walls** for a global coordination view.

---

## 4. Time-of-Flight (ToF) Sensors

**File:** `maze_sim.py → ToFSensor`

Each mapper drone carries **4 ToF sensors** pointing in the cardinal directions:

|Index|Direction|(dx, dy)|
|---|---|---|
|0|North|(0, −1)|
|1|South|(0, +1)|
|2|West|(−1, 0)|
|3|East|(+1, 0)|

### Ray-Casting (DDA Algorithm)

The sensor performs a **Digital Differential Analyzer (DDA) ray cast** through the maze grid:

- Steps along the ray in increments of `CELL × 0.07 = 0.14 m`.
- At each step, checks if the ray has crossed a cell boundary.
- If a boundary is crossed and the maze has a wall between those two cells, the intersection distance is returned.
- Maximum measurable range: **4.0 m**.

### Noise Model

Each reading is corrupted with zero-mean Gaussian noise: **σ = 0.04 m**, simulating realistic MEMS ToF sensor behavior.

### Wall Detection Report

When a sensor detects a wall (reading < 3.95 m), the drone generates a detection record:

JSON

```
{"drone_id": 2, "wall_key": "h_3_4", "dir": "N", "dist": 1.823}
```

The `wall_key` uniquely identifies the specific wall segment in the maze grid (`h_r_c` for horizontal, `v_r_c` for vertical). These reports are transmitted to the mother drone.

---

## 5. Navigation: BFS Frontier Exploration

**File:** `maze_sim.py → FrontierExplorer`

Each mapper drone uses **Breadth-First Search (BFS)** to navigate the maze and avoid walls. This is the core movement algorithm.

### Frontier Selection

1. From the drone's current cell, BFS expands outward through passable cells (no wall between neighbors).
2. The first cell found that has **not yet been visited** within the drone's assigned sector is selected as the **frontier target**.
3. This guarantees the nearest unvisited cell is always chosen (BFS is optimal for unweighted graphs).

### Path Planning

Once a frontier cell is selected, a second BFS run computes the **full shortest path** from the current cell to the target:

- Respects all maze walls — the drone never attempts to pass through a wall.
- Returns an ordered list of cells: `[current, c1, c2, ..., target]`.
- The drone uses a **2-cell lookahead** on the path to set its waypoint, which produces smoother flight trajectories than jumping one cell at a time.

### Waypoint Tracking

- The drone moves toward the current waypoint at **1.6 m/s** (`MAPPER_SPEED`).
- Arrival is detected when the drone is within **0.22 m** (`ARRIVAL_DIST`) of the waypoint.
- On arrival, the path index advances to the next cell; when the full path is consumed, the frontier cell is marked visited and a new BFS search begins.

### Sector Assignment

The maze is divided into **4 quadrants**, one per mapper:

- Mapper 1 (top-left corner) → top-left quadrant
- Mapper 2 (top-right corner) → top-right quadrant
- Mapper 3 (bottom-left corner) → bottom-left quadrant
- Mapper 4 (bottom-right corner) → bottom-right quadrant

Each drone's BFS search is **restricted to its sector**, ensuring parallel, non-overlapping coverage of the entire maze.

---

## 6. Mother Drone: Map Aggregation

**File:** `maze_sim.py → MotherDrone`

The mother drone acts as a **passive aerial aggregator** during the mapping phase. It:

1. **Receives** wall detection packets from all mapper drones at each simulation step.
2. Stores each newly detected wall in `wall_map` (a dictionary keyed by wall ID), along with which drone first detected it.
3. Tracks **visited cells per drone** (`cells_per_drone`) to compute per-drone and total coverage statistics.
4. Reports:
    - `wall_detection_pct` — percentage of internal walls detected out of the total
    - `coverage` — percentage of total cells visited
    - `coverage_per_drone` — per-drone cell coverage breakdown
    - `wall_detection_counts` — how many walls each drone has detected

The mother drone never navigates laterally during the maze phase; it simply hovers at the maze centre at 5 m altitude throughout.

---

## 7. Flight Controller Architecture

**File:** `control/pid_controller.py`

Both drone types use a **4-layer cascaded PID controller**:

Code

```
Position Error
     │
     ▼
[Layer 1: Position PID] → velocity setpoint (clipped)
     │
     ▼
[Layer 2: Velocity PID] → desired acceleration → total thrust + roll/pitch setpoints
     │
     ▼
[Layer 3: Attitude PID] → angular rate setpoints
     │
     ▼
[Layer 4: Rate PID] → torque commands
     │
     ▼
[Motor Mixing Matrix] → 4× rotor thrust [0..1]
```

### Motor Mixing

A 4×4 mixing matrix maps `[total_thrust, τ_roll, τ_pitch, τ_yaw]` to individual rotor thrusts using the arm geometry and a torque/thrust coefficient `k = 0.016 N·m/N`. The matrix is inverted once at startup and reused every control step.

### Controller Tuning (Mapper vs. Mother)

|Parameter|Mother|Mapper|
|---|---|---|
|Position Kp (XY)|0.8|1.2|
|Position Kp (Z)|1.2|1.8|
|Velocity Kp (XY)|1.5|2.0|
|Max attitude tilt|30°|35°|
|Max angular rate|180°/s|360°/s|

Mapper drones are tuned more aggressively (higher gains, wider limits) for faster, more agile maze navigation. The mother drone is tuned for stability and smooth hovering.

### Anti-Windup & Filtering

All PID integrators are clamped to prevent windup. Derivative terms use a **low-pass filter** (`α = 0.8`) to suppress noise, or accept a measured derivative (gyro angular velocity) directly for the inner rate loop.

**Update rate:** 250 Hz (`dt = 0.004 s`)

---

## 8. State Estimation: Extended Kalman Filter

**File:** `core/ekf.py`

Each drone runs its own **15-state EKF** for GPS-denied inertial navigation, processing IMU data at 250 Hz.

### State Vector

Code

```
x[0:3]   = position      (m, world frame)
x[3:6]   = velocity      (m/s, world frame)
x[6:9]   = euler angles  (roll, pitch, yaw in rad)
x[9:12]  = accelerometer bias  (m/s²)
x[12:15] = gyroscope bias      (rad/s)
```

### Predict Step (IMU-driven)

1. **Remove estimated biases** from raw IMU readings.
2. **Rotate** corrected body-frame acceleration to world frame using the rotation matrix derived from the current Euler angle estimate.
3. **Integrate** position and velocity using second-order kinematics.
4. **Integrate** Euler angles using the kinematic rate transformation matrix `T(η)`.
5. **Propagate covariance** via the linearised Jacobian `F = ∂f/∂x`.

### Update Steps

- **Magnetometer (50 Hz):** Corrects yaw drift. Extracts heading from `atan2(-my, mx)` and applies a 1D measurement update on the yaw state.
- **Barometer (optional):** Corrects altitude (z-position) drift using a 1D update.

### IMU Noise Model

|Sensor|White Noise σ|Bias Drift σ/step|
|---|---|---|
|Accelerometer|0.04–0.08 m/s²|0.005 m/s²|
|Gyroscope|0.004–0.006 rad/s|0.0005 rad/s|

Mother drone sensors are higher quality (lower noise). The `IMUSimulator` class adds correlated noise and random-walk bias drift to MuJoCo's ground-truth sensor readings to simulate realistic MEMS behavior.

---

## 9. Swarm Coordination

**File:** `coordination/swarm_coordinator.py`

The `SwarmCoordinator` manages the overall mission phases and inter-drone relationships when the full physics simulation is used.

### Mission Phases

|Phase|Trigger|Behavior|
|---|---|---|
|TAKEOFF|Start|All drones climb to 2.0 m|
|FORM_UP|All > 1.8 m|Mappers form up around mother|
|SURVEY|Formation error < 0.4 m|Mappers explore assigned sectors; mother patrols|
|REGROUP|Coverage > 95%|All drones return to home formation|
|LAND|Mother at origin|All drones descend|

### Formation Shapes

Four selectable formations (relative to mother):

|Shape|Description|
|---|---|
|DELTA|Triangle behind mother|
|LINE|Side-by-side|
|DIAMOND|Mother leads, mappers at corners|
|SPREAD|Wide fan for maximum coverage|

### Sector Assignments

- Mapper 1: SW quadrant (−10..0, −10..0)
- Mapper 2: SE quadrant (0..10, −10..0)
- Mapper 3: entire north half (−10..10, 0..10)

### Collision Avoidance

An **inverse-square virtual repulsion** velocity is added to each mapper's setpoint when another drone comes within `safe_dist = 1.4 m`. The repulsion force scales as `1/d²` and is capped at ±2 m/s to prevent overcorrection:

Python

```
v_rep += gain * diff / (d ** 2)   # inverse-square law
```

### Communication Model

Inter-drone radio links are simulated with:

- **Hard cutoff** at 15 m range.
- **Linear packet loss** beginning at 80% of max range.
- **2% baseline** packet loss at any range.

During survey, a mapper only updates its waypoint if it currently has a comm link to the mother drone — simulating a realistic mesh protocol where new tasks require acknowledgment.

---

## 10. Simulation Loop Timing

### MuJoCo Physics Loop (`run_simulation.py`)

|Layer|Rate|Period|
|---|---|---|
|MuJoCo physics|1000 Hz|1 ms|
|EKF + PID control|250 Hz|4 ms (every 4 physics steps)|
|Magnetometer update|50 Hz|(every 20 ms)|
|Swarm coordination|10 Hz|(every 100 physics steps)|
|Data logging|~25 Hz|(every 40 ms)|

### Maze Simulation Loop (`maze_sim.py`)

|Layer|Rate|Notes|
|---|---|---|
|Broadcast frame|20 Hz|`DT = 0.05 s`|
|Physics sub-steps|8 per frame|Smoother motion, 160 Hz effective|
|Simulation speedup|1×–10×|Configurable via `--speed`|

---

## 11. WebSocket Telemetry (`server.py`)

A thread-safe broadcaster pushes JSON telemetry packets to browser dashboards at 20 Hz. The `TelemetryBroadcaster` runs an asyncio event loop in a background daemon thread and uses a thread-safe queue (`maxsize=20`) to receive packets from the simulation thread. Overflow packets are silently dropped (old frames are less important than fresh ones).

Each packet includes:

- Simulation time, phase
- Per-drone: position, velocity, Euler angles, EKF uncertainty (σ), rotor throttles, setpoint, ToF readings
- Coverage percentage (total and per-drone)
- Wall map and detection statistics
- 4×4 communication link matrix

The browser can also **send commands back** over WebSocket: pause, resume, reset, change formation, adjust wind, or set simulation speedup.

---

## 12. Data Logging

At the end of a run, per-drone logs are saved to `logs/drone{id}_{timestamp}.json` containing:

- True positions (from MuJoCo ground truth)
- EKF-estimated positions and velocities
- Euler angle estimates
- Motor control commands

A swarm-level log records time-series coverage fraction and the communication matrix.

---

## 13. Key Design Decisions & Trade-offs

|Decision|Rationale|
|---|---|
|1.5 m mapper altitude|Below 3 m walls → ToF sensors see walls; above floor obstacles|
|5.0 m mother altitude|Above walls → unobstructed hovering, global view|
|2 m × 2 m cells|Sufficient for quadrotor width; minimises total cell count|
|BFS over DFS/A*|Guarantees nearest frontier; simple and wall-aware|
|Sector-based assignment|Prevents drones from redundantly exploring the same area|
|Corner deployment|Maximises initial spatial separation, minimising early overlap|
|Inverse-square repulsion|Soft collision avoidance without hard constraints on trajectory|
|EKF with bias states|Enables long-duration flight without GPS by correcting IMU drift|
|Comm-gated waypoints|Prevents mappers from acting on stale data when link drops|

---

## 14. Summary

The system implements a complete, end-to-end GPS-denied swarm mapping pipeline. The **maze generator** produces a fresh, fully connected maze on every run. **Four mapper drones** deploy from the four corners simultaneously, each assigned a quadrant. They navigate using **BFS-based frontier exploration**, strictly respecting maze walls, and fire four-directional **ToF sensors** at every step. All detections are reported to the **mother drone**, which aggregates a unified wall map from 5 m altitude. Each drone runs an independent **15-state EKF** fusing IMU and magnetometer data for drift-resilient position estimation, and a **cascaded 4-layer PID controller** for stable flight. The simulation terminates when all frontiers are exhausted (100% sector coverage), and the complete wall map can be reconstructed from the mother drone's records.