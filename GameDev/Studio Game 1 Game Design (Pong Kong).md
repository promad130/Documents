
## 1. Project Overview

### 1.1 Game Summary

**Pong Evolved** is a fast-paced, 2D competitive arcade game rooted in the classic Pong formula, dramatically expanded with a strategic power-up economy, breakable paddles, dynamic physics, and fluid ball behaviour. Two players compete to score points against each other by deflecting a ball, while spending earned points on power-ups that can shift the momentum of the match entirely.

### 1.2 Core Design Pillars

|Pillar|Description|
|---|---|
|**Skill Expression**|Players must master timing, power-up chaining, and reading ball trajectories.|
|**Risk vs. Reward**|Every power-up costs points. Spending them recklessly leaves you at a disadvantage.|
|**Escalating Tension**|Ball speed scales with total game score, ensuring late-game moments feel explosive.|
|**Fluid Physicality**|Ball and paddle feel alive — deforming, pulsing, and reacting to every interaction.|
|**Accessible Depth**|Easy to pick up in local multiplayer; deep enough to reward competitive play online.|

### 1.3 Target Audience

- Casual players enjoying local co-op / versus on the couch
- Competitive online players looking for strategic depth in a retro format
- Mobile players wanting short, intense sessions

### 1.4 Unique Selling Points

- Power-up economy layered on top of classic Pong
- Fluid, physics-responsive ball and paddle animations
- Breakable paddles that raise stakes mid-match
- Seamless cross-platform play (Desktop + Mobile)
- Photon-powered real-time multiplayer

---

## 2. Technical Specifications

### 2.1 Engine & Platform

|Property|Value|
|---|---|
|**Engine**|Unity 6|
|**World Space**|2D|
|**Target Platforms**|Windows, Linux, macOS, Android, iOS|
|**Render Pipeline**|URP (Universal Render Pipeline)|
|**Networking**|Photon PUN 2|
|**Architecture**|Event-Driven (no direct method calls between systems)|
|**Physics**|Unity 2D Physics (Rigidbody2D, custom bounce logic)|
|**Orientation**|Landscape (Desktop & Mobile)|

### 2.2 Event-Driven Architecture Overview

All game systems communicate exclusively through a central **EventBus**. No system holds a direct reference to another. See [Section 14](https://claude.ai/chat/5d1cfab6-893b-49f6-ab10-b5805ade541b#14-event-based-architecture) for detailed event definitions.

```
[Input System] ──► [EventBus] ──► [Ledge System]
[Ball System]  ──► [EventBus] ──► [PowerUp System]
[Score System] ──► [EventBus] ──► [UI System]
[AI System]    ──► [EventBus] ──► [Network System]
```

### 2.3 Scene Structure

```
Scenes/
├── Bootstrap          ← App init, service locator, EventBus setup
├── MainMenu           ← Mode selection, settings
├── GameArena          ← Core gameplay scene (loaded additively)
├── Lobby              ← Photon lobby/room browser
└── Results            ← Post-match score screen
```

### 2.4 Folder Structure

```
Assets/
├── _Game/
│   ├── Events/            ← ScriptableObject-based event channels
│   ├── Systems/
│   │   ├── Ball/
│   │   ├── Ledge/
│   │   ├── PowerUp/
│   │   ├── Score/
│   │   ├── AI/
│   │   └── Network/
│   ├── Arenas/
│   ├── UI/
│   ├── Audio/
│   └── VFX/
└── Plugins/
    └── PhotonPUN/
```

---

## 3. Game Modes

### 3.1 Mode Overview

|Mode|Players|Network|Description|
|---|---|---|---|
|**Single-Player**|1 Human + 1 AI|Offline|Player vs. AI opponent. Difficulty scales with player performance.|
|**Local Multiplayer (2P)**|2 Humans|Offline|Same device, split input. Classic couch versus.|
|**Online Multiplayer (2P)**|2 Humans|Photon PUN|Ranked or casual online match.|
|**Online Multiplayer (4P)**|4 Humans|Photon PUN|Future — top/bottom lanes added. See Section 15.|

### 3.2 Mode Selection Flow

```
Main Menu
 └── Play
      ├── Single Player     → Difficulty Select → Arena Select → Game
      ├── Local Multiplayer → Arena Select → Game
      └── Online
           ├── Quick Match  → Matchmaking → Lobby → Game
           └── Private Room → Create/Join with code → Lobby → Game
```

### 3.3 Match Structure

- A match has no fixed round count by default — it ends when a **score threshold** or **time limit** is reached (configurable in settings).
- Default win condition: **First to 20 points** (adjustable: 10 / 20 / 30 / custom).
- Optional **Sudden Death**: if tied at win threshold, next point wins.

---

## 4. Core Game Mechanics

### 4.1 Arena Boundaries

```
┌─────────────────────────────────────┐  ← (No wall — TELEPORT ZONE)
│                                     │
│  [P1 Ledge]             [P2 Ledge]  │
│                  ●                  │
│                                     │
└─────────────────────────────────────┘  ← (No wall — TELEPORT ZONE)

◄ LEFT WALL (Hard boundary — P1 miss zone)
                          RIGHT WALL ► (Hard boundary — P2 miss zone)
```

- **Left & Right Walls:** Solid boundaries. If the ball passes a wall, the opposing player scores.
- **Top & Bottom:** No walls. The ball wraps around — exiting from the top immediately re-enters from the bottom at the same horizontal position and velocity, and vice versa.
- The wrap is instantaneous and does not affect the ball's speed or trajectory.

### 4.2 Ball Physics & Movement

See [Section 6](https://claude.ai/chat/5d1cfab6-893b-49f6-ab10-b5805ade541b#6-ball-system) for full detail.

- Ball launches from centre at match start.
- Starting speed is low; increases proportionally with **total accumulated points in the match**.
- Ball can receive speed multipliers from specific power-ups (Force Bounce, Destroyer Bounce).
- Ball direction after ledge contact is determined by **hit position on the ledge + ledge velocity at time of contact**.

### 4.3 Scoring a Point

A point is scored when:

1. The ball crosses the **left boundary** → Player 2 scores.
2. The ball crosses the **right boundary** → Player 1 scores.
3. A **Ball Vanish** miss occurs → see Section 7.4.

After a point:

- A short celebration animation plays.
- The ball resets to center and re-launches after a brief delay (1.5s).
- If the ledge life counter drops to 0, the **2x point modifier** is applied to all subsequent scores against that player until they recover.

### 4.4 Ball Speed Scaling

Ball speed is determined by the formula:

```
currentSpeed = baseSpeed + (totalMatchPoints × speedScalingFactor)
```

|Variable|Default Value|Notes|
|---|---|---|
|`baseSpeed`|6 units/sec|Starting speed at score 0–0|
|`speedScalingFactor`|0.15 units per point|Tunable per arena|
|`maxSpeed`|22 units/sec|Hard cap to keep game playable|

Speed resets to `baseSpeed` after each point is scored and re-accumulates.

---

## 5. Ledge (Paddle) System

### 5.1 Ledge Properties

|Property|Value|
|---|---|
|Default Height|20% of arena height|
|Default Width|1.2 units|
|Movement Axis|Vertical only (Y-axis)|
|Movement Speed|8 units/sec (base)|
|Max Y Position|Arena top bound|
|Min Y Position|Arena bottom bound|
|Life Count|3 lives (default)|

### 5.2 Ledge Life System

Each ledge has a **life counter** (default: 3). Certain power-ups can reduce this counter.

- When lives > 0: Normal gameplay.
- When lives = 0: **Broken Ledge State.**
    - Ledge becomes shorter (visual crack/break effect).
    - Any point scored against the owning player counts as **2x points** for the scorer.
    - Lives do not regenerate mid-match (future upgrade: power-up to restore 1 life).
- Lives can only be reduced by specific power-ups (see Section 7).

### 5.3 Ledge Visual States

|Life Count|Visual|
|---|---|
|3|Full, solid, bright|
|2|Small crack on the ledge surface|
|1|Larger cracks, flickering glow, shortened by ~10%|
|0|Shattered look, red tint, particle debris|

### 5.4 Input Mapping

**Desktop (Keyboard):**

|Player|Up|Down|
|---|---|---|
|Player 1|`W`|`S`|
|Player 2|`↑`|`↓`|

**Desktop (Controller):**

|Player|Control|
|---|---|
|Both|Left Stick Y-Axis|

**Mobile:**

- Each player controls their side via a **vertical drag zone** occupying their half of the screen.
- Drag up/down to move the ledge in real time.

---

## 6. Ball System

### 6.1 Ball Physics Model

The ball uses **Unity 2D Physics** with a custom `BallController` component that overrides default bounce logic:

- `PhysicsMaterial2D` with `bounciness = 1.0` and `friction = 0`.
- On ledge contact, outgoing angle is calculated as:

```
outAngle = mirroredIncidentAngle + (hitOffset × deflectionMultiplier)
```

Where `hitOffset` is the normalised distance from ledge center (-1 to 1). Hitting the edge of a ledge produces a sharper angle; hitting the center produces a nearly flat return.

### 6.2 Fluid Ball Deformation

The ball is **not a rigid circle**. It deforms dynamically using a **squash-and-stretch shader + sprite scaling**:

|Event|Deformation|
|---|---|
|High speed travel|Elongated in direction of travel|
|Ledge contact|Brief squash perpendicular to travel direction|
|Idle / slow speed|Near-perfect circle|
|Force Bounce activation|Sharp elongation spike on contact|
|Destroyer Bounce impact|Brief inflation + shockwave ripple|

The deformation magnitude is capped to avoid visual distortion at extreme speeds.

### 6.3 Ball Trail

- A soft-glow particle trail follows the ball.
- Trail length and opacity scale with ball speed.
- Trail colour changes per arena or active power-up state (e.g., red for Destroyer Bounce, translucent for Ball Vanish).

### 6.4 Ball Respawn

After a point, ball respawn sequence:

1. Ball fades out at miss zone.
2. 1.5 second pause (brief results flash on screen).
3. Ball fades in at centre.
4. 0.5 second telegraph flash, then launches toward the player who just scored against (gives them momentum pressure).

---

## 7. Power-Up System

### 7.1 Power-Up Economy

- Players **earn points** by scoring goals.
- Points are spent to **activate power-ups**.
- Power-ups are selected from a **quick-access HUD** during play.

**Activation Window:**

- A player activates a power-up at any time.
- The power-up enters **Primed State**: it is queued but not yet active.
- It becomes **Active** the moment the ball enters the activating player's court.
- It **deactivates** when:
    - The power-up effect is triggered/consumed, **OR**
    - The ball leaves the player's court without the effect being triggered.

```
[Player presses power-up key]
         │
         ▼
   [PRIMED STATE] ──── ball leaves court ────► [DEACTIVATED — refund? TBD]
         │
    ball enters court
         │
         ▼
   [ACTIVE STATE] ──── effect triggered ────► [DEACTIVATED]
```

> Design Note: Whether to refund points if the primed power-up is not triggered is a tuning decision. Recommended: No refund — creates risk in pre-activating power-ups.

### 7.2 Ball Dash (Cost: 1 Point)

**Intent:** Quickly reposition the ledge to guarantee contact with the ball. Designed to be paired with another power-up to ensure that power-up is not wasted.

**Behaviour:**

- When the ball enters the player's court, the ledge **dashes** a short distance along the Y-axis toward the ball's current Y position.
- Dash speed is significantly faster than normal ledge movement.
- Dash distance is capped so it cannot teleport across the entire arena.

**Visual Feedback:**

- Quick **zoom-in pulse** of the ledge.
- Faint speed-lines or motion blur trail on the ledge during dash.
- Subtle camera **zoom-in then out** to emphasise the snap of movement.

**Stats:**

|Property|Value|
|---|---|
|Cost|1 point|
|Dash Speed|3× normal ledge speed|
|Max Dash Distance|35% of arena height|
|Duration|Instant (one-time per activation)|

### 7.3 Force Bounce (Cost: 1 Point)

**Intent:** Supercharge the ball's outgoing speed after ledge contact, turning a defensive return into an aggressive attack.

**Behaviour:**

- When the ball contacts the ledge during Active State, the ball's outgoing speed is **multiplied**.
- The angle of bounce is preserved (no angle change from Force Bounce alone).
- After the bounce, the ball retains its increased speed until the next point resets it.

**Visual Feedback:**

- Brief **vibration/rumble** of the ledge on contact.
- Ball emits a bright **impact flash** and its trail briefly flares.
- Subtle screen **shake** (0.1s, low amplitude).
- The ledge flashes with a white/yellow energy sheen.

**Stats:**

|Property|Value|
|---|---|
|Cost|1 point|
|Speed Multiplier|1.8× current ball speed|
|Duration|Single contact (consumed on use)|
|Stacks with|Destroyer Bounce (order matters)|

### 7.4 Destroyer Bounce (Cost: 2 Points)

**Intent:** A high-risk, high-reward power-up that escalates ball speed dramatically within a bounded zone and awards massive points on success.

**Behaviour:**

- On activation, a **Destroyer Boundary** appears — a secondary inner boundary surrounding the active player's court.
- While the ball bounces **within the Destroyer Boundary**, every bounce off any surface increases ball speed by a fixed increment.
- The accumulated speed and the "Destroyer" state carry over when the ball finally exits toward the opponent.
- Points scored from a ball in Destroyer State count as **3× normal points** (i.e., a regular miss becomes 4 points, or 8 if Ledge is broken).
- **Ledge damage:** A ball in Destroyer State that successfully passes the opponent's ledge reduces the opponent's ledge life count by 1.

**Visual Feedback:**

- The Destroyer Boundary appears as a glowing cage/frame of crackling energy.
- Each bounce within the boundary produces a **screen shake** that escalates in intensity.
- Ball trail turns deep red/orange during Destroyer State.
- Ball grows slightly with each internal bounce (visual cue of escalating danger).
- Opponent's HUD flashes a warning indicator.

**Stats:**

|Property|Value|
|---|---|
|Cost|2 points|
|Speed per internal bounce|+1.5 units/sec|
|Point multiplier (on score)|3×|
|Miss point penalty|4 points to opponent (8 if ledge broken)|
|Ledge life damage on hit|−1 life|
|Duration|Until ball exits player's court|

### 7.5 Ball Vanish (Cost: 4 Points)

**Intent:** A disorienting, high-risk ability that makes the ball temporarily invisible, creating a psychological game of prediction and risk.

**Behaviour:**

- Ball enters a **Visible → Invisible → Visible** cycling pattern.
- While **Invisible**, the ball continues to physically exist and bounce off the top/bottom wrap zones but does not trigger point scoring on wall contacts.
- When the ball becomes **Visible** again, it "snaps" to the position it would have been at — potentially appearing in an unexpected location.
- If the opponent **misses the ball during Visible State**, they lose 1 point AND the activating player gains **3× normal score** (8 points by default).
- If the activating player's ball goes unreturnably past the opponent during Invisible phase, there is no score — the ball just re-appears.

**Visible/Invisible Cycle:**

|Phase|Duration|
|---|---|
|Visible|1.2 seconds|
|Invisible|0.8 seconds|
|Total cycle|2.0 seconds|

**Visual Feedback:**

- Transition: ball flickers like a hologram before vanishing.
- During invisible state: faint ghost outline visible only to the ball owner (local) — completely invisible to opponent.
- Screen gains a subtle **vignette + desaturation** effect during Vanish phase.
- Mysterious, ethereal ambient sound loop plays during activation.
- On ball reappear: brief **radial flash** from the ball's position.

**Stats:**

|Property|Value|
|---|---|
|Cost|4 points|
|Miss penalty (for misser)|−1 point|
|Score on miss|3× normal (8 points base)|
|Broken ledge miss score|8× (16 points)|
|Duration|Until ball exits player's court|

### 7.6 Power-Up Interaction Table

|Power-Up|Stacks With|Notes|
|---|---|---|
|Ball Dash|Any|Use to ensure another power-up lands|
|Force Bounce|Ball Dash, Destroyer Bounce|Stacking with Destroyer raises speed further|
|Destroyer Bounce|Force Bounce|Force Bounce first; Destroyer multiplies from that speed|
|Ball Vanish|Ball Dash|Dash ensures you can return during visible phase|
|Ball Vanish + Destroyer|—|Disallowed (too oppressive) — Destroyer cancels Vanish|

### 7.7 Power-Up HUD & Input

- Each power-up is accessible via a **numbered quick-slot** (1–4 on keyboard; on-screen buttons for mobile).
- The HUD shows:
    - Available power-ups with their cost.
    - Current point balance.
    - Active power-up indicator (glowing border animation).
    - Primed state indicator (pulsing ring).

---

## 8. Point & Scoring System

### 8.1 Base Point Values

|Event|Points Awarded To|
|---|---|
|Normal miss|Opponent: **+2 points**|
|Destroyer Bounce miss|Opponent: **+4 points** (3× score = 6, but base miss = 4)|
|Ball Vanish miss|Opponent: **+8 points**; Misser: **−1 point**|
|Broken Ledge normal miss|Opponent: **+4 points** (2× modifier)|
|Broken Ledge + Destroyer|Opponent: **+8 points**|
|Broken Ledge + Vanish|Opponent: **+16 points**|

> Design note: "Normal miss" baseline is 2 points, not 1, to ensure spending 1 point on a power-up always has meaningful payoff potential.

### 8.2 Point Spending

|Power-Up|Cost|
|---|---|
|Ball Dash|1 point|
|Force Bounce|1 point|
|Destroyer Bounce|2 points|
|Ball Vanish|4 points|

### 8.3 Score Display

- Each player's score is displayed prominently in their half of the arena header.
- A secondary **Spendable Points** counter (separate from total score) tracks available economy.
- Total match score (sum of both players) is displayed in the center — this reflects ball speed pressure visually.

### 8.4 Win Condition

|Setting|Value|
|---|---|
|Default win score|20 points|
|Optional thresholds|10 / 30 / Custom|
|Tie-breaker|Sudden Death (next point wins)|
|Forfeit|Player disconnect in online → auto-loss after 10s|

---

## 9. Enemy AI System

### 9.1 AI Overview

The AI opponent in Single-Player mode is designed to feel reactive and human-like, not robotic. It operates through **behaviour layers** that are activated based on game state.

### 9.2 AI Difficulty Levels

|Difficulty|Reaction Time|Prediction Accuracy|Power-Up Use|Error Rate|
|---|---|---|---|---|
|Easy|400ms|60%|Rare, random|High|
|Medium|200ms|80%|Occasional, semi-smart|Medium|
|Hard|80ms|95%|Frequent, strategic|Low|
|Adaptive|Scales|Scales|Scales|Matches player win-rate|

### 9.3 AI Behaviour Model

The AI uses a **three-layer decision stack**:

**Layer 1 — Ball Tracking (always active):**

- AI predicts ball Y position at time of arrival using linear trajectory + wrap simulation.
- Moves ledge toward the predicted intercept point.
- Intentional error is introduced based on difficulty (random offset from true position).

**Layer 2 — Power-Up Logic (active when points ≥ cost):**

- AI evaluates whether to spend points based on:
    - Current score deficit/lead.
    - Ball speed (higher speed → more likely to use Force Bounce or Ball Dash).
    - Ledge life remaining (damaged ledge → more defensive, more Ball Dash use).
- On Hard, AI can chain Ball Dash + Force Bounce optimally.

**Layer 3 — Adaptive Behaviour:**

- Adaptive mode tracks the player's **win rate** over the last 5 points.
- If player is winning, AI tightens reaction time and increases power-up frequency.
- If player is losing, AI introduces artificial hesitation and power-up delays.
- Goal: maintain approximately 50% win rate for the player (keep the match tense).

### 9.4 AI Events

The AI system listens to:

- `OnBallPositionUpdated` → recalculate intercept
- `OnBallEntersCourt(AI)` → evaluate power-up activation
- `OnPointScored` → update adaptive difficulty model

---

## 10. Multiplayer Architecture (PhotonPUN)

### 10.1 Overview

Online multiplayer uses **Photon PUN 2** for real-time peer-to-peer relay networking.

### 10.2 Room Structure

|Property|Value|
|---|---|
|Max Players per Room|2 (4 in future 4P mode)|
|Room Visibility|Public (Quick Match) / Private (Code-based)|
|Region|Auto-selected by ping|
|Matchmaking|ELO-based in Ranked; open in Casual|

### 10.3 Network Authority Model

|System|Authority|
|---|---|
|Ball Position & Physics|**Host (Master Client)** — simulated on host, synced to client|
|Ledge Position|**Local Owner** — each player sends their own position|
|Power-Up Activation|**Local Owner** sends event → Host validates and broadcasts|
|Score|**Host** — authoritative, cannot be spoofed|
|AI (in 1P)|**Local** — no network involvement|

### 10.4 Synchronised Events via Photon RPC

|RPC Name|Sender|Purpose|
|---|---|---|
|`RPC_BallState`|Host|Sync ball position + velocity every fixed tick|
|`RPC_PowerUpActivated`|Any|Notify both clients of power-up trigger|
|`RPC_ScoreUpdate`|Host|Broadcast updated scores|
|`RPC_LedgeLifeUpdate`|Host|Broadcast ledge life change|
|`RPC_MatchEnd`|Host|Trigger end-of-match on both clients|

### 10.5 Lag Compensation

- **Ledge movement** uses client-side prediction + server reconciliation.
- **Ball position** uses interpolation on the client with a 1-frame buffer.
- On detected desync (>50ms position difference), host state is authoritative and client snaps.
- Visual rubber-banding is smoothed over 0.2 seconds to avoid jarring corrections.

### 10.6 Disconnect Handling

- If a player disconnects mid-match:
    - A 10-second reconnect window is offered.
    - If they do not rejoin: the remaining player is declared winner.
    - Match result is recorded even on disconnect forfeit.

---

## 11. Audio & Visual Aesthetics

### 11.1 Visual Style

- **Art Direction:** Neon-on-dark minimalism. Clean geometric shapes with vibrant energy effects. Inspired by classic arcade aesthetics updated with modern shader work.
- **Colour palette:** Deep navy/black backgrounds; cyan, magenta, white for primary game elements; power-up-specific accent colours.
- **Resolution target:** 1920×1080 (desktop), adaptive for mobile.

### 11.2 Screen Feedback Per Event

|Event|Screen Effect|
|---|---|
|Destroyer Bounce (each internal bounce)|Escalating camera shake, red chromatic aberration pulse|
|Ball Vanish activation|Vignette darkens, mild desaturation|
|Ball Dash|Brief zoom-in (1.05×) then release|
|Force Bounce contact|Short 0.08s vibration shake|
|Ball miss|Quick flash of the scoring player's colour|
|Ledge life lost|Screen crack overlay fades in over 0.3s|
|Ledge broken (0 lives)|Heavy shake + red flash + crack texture persists|
|Point scored|Score counter animate + brief slowdown (0.2s at 0.4× timescale)|

### 11.3 Ball Visual States

|State|Appearance|
|---|---|
|Normal|White circle with soft glow trail|
|Force Bounce primed|Yellow/orange outline pulsing|
|Destroyer State|Red core, crackling particle trail|
|Ball Vanish (visible phase)|Shifting translucent blue, edge flicker|
|Ball Vanish (invisible phase)|Fully invisible to opponent; ghost ring to owner|
|High speed (near cap)|Heavily elongated, long trail, colour shifts to hot white|

### 11.4 Audio Design

|Sound|Trigger|Notes|
|---|---|---|
|Ball–Ledge hit|Each contact|Crisp, pitched slightly by speed|
|Ball–Wall hit (left/right)|Miss event|Deep impact + score increment sound|
|Ball wrap (top/bottom)|Wrap event|Soft whoosh|
|Ball Dash|Activation|Snap/thump with reverb tail|
|Force Bounce|On contact|Electric crack, pitched high|
|Destroyer (each bounce)|Internal bounce|Low-frequency thud, escalating pitch|
|Ball Vanish (activate)|Activation|Ethereal reverse-whoosh|
|Ball Vanish (flicker)|Each phase toggle|Glitch/static click|
|Ledge life lost|Damage|Crunch + glass crack|
|Ledge broken|0 lives|Heavy smash SFX + deep rumble|
|Point scored|Score event|Fanfare stab, custom per-arena|
|Match win|Match end|Victory melody|
|Match loss|Match end|Deflated descending tone|

### 11.5 Haptic Feedback (Mobile)

|Event|Haptic Pattern|
|---|---|
|Ball–Ledge contact|Light tap|
|Force Bounce|Medium pulse|
|Destroyer internal bounce|Heavy rumble (escalating)|
|Ball Vanish toggle|Double-tap (light)|
|Point scored|Medium pulse + pause|
|Ledge broken|Strong sustained buzz|

---

## 12. UI & UX Design

### 12.1 Main Menu

- **Background:** Animated arena loop — ball bouncing, ledges idling.
- **Menu Items:** Play, Settings, Leaderboard (future), Skins (future), Quit.
- Mode selection uses large tap-friendly cards with mode description.

### 12.2 In-Game HUD

```
┌──────────────────────────────────────────────────┐
│  [P1 Score: 12]   [Total: 24]   [P2 Score: 12]  │
│  [Spendable: 6]                 [Spendable: 4]   │
│                                                  │
│  [🟡1][⚡1][💥2][👁4]         [🟡1][⚡1][💥2][👁4] │
│                                                  │
│         [P1 Lives: ❤❤❤]  [P2 Lives: ❤❤○]        │
└──────────────────────────────────────────────────┘
```

- Power-up slots are greyed out when points are insufficient.
- Active power-up slot glows with animated border.
- Primed power-up slot pulses.

### 12.3 Pause Menu (Single-Player / Local)

- Resume / Restart / Settings / Quit to Menu.

### 12.4 Online Lobby

- Room code display (Private) / Auto-matching spinner (Quick Match).
- Ready toggle button.
- Arena vote (future).
- Chat (future).

### 12.5 Results Screen

- Final scores displayed with animation.
- Power-up usage stats: times used, points spent/earned per power-up.
- MVP badge (highest single power-up contribution).
- Play Again / Return to Menu / Share (future).

### 12.6 Accessibility Considerations

- All colour-coded elements have distinct shapes as backup.
- Font size scales with device resolution.
- Touch zones on mobile are minimum 44×44pt.
- Haptic and audio can be independently toggled.

---

## 13. Arena & World Design

### 13.1 Default Arena — "The Void"

- Minimalist dark background.
- Clean neon boundary lines.
- No environmental hazards.
- Standard physics settings.

### 13.2 Arena Properties (Extendable per Arena)

|Property|Configurable|
|---|---|
|Background skin|Yes|
|Speed scaling factor|Yes|
|Wrap zone visuals|Yes|
|Environmental physics modifiers|Yes (future)|
|Music track|Yes|
|Power-up cost multiplier|Yes (future)|

### 13.3 Future Arenas (See Section 15)

Each future arena will introduce a new physics rule or environmental hazard layered on top of the core game.

---

## 14. Event-Based Architecture

### 14.1 Core Event Channels

All events are **ScriptableObject-based event channels** (using the Unity pattern popularised by Ryan Hipple's GDC talk).

|Event Channel|Payload|Listeners|
|---|---|---|
|`OnBallLaunched`|`Vector2 direction`|BallVFX, AudioSystem|
|`OnBallPositionUpdated`|`Vector2 position`|AISystem, NetworkSync|
|`OnBallEnteredCourt`|`PlayerSide side`|PowerUpSystem, AISystem|
|`OnBallExitedCourt`|`PlayerSide side`|PowerUpSystem|
|`OnBallHitLedge`|`ContactData`|PowerUpSystem, VFXSystem, AudioSystem|
|`OnBallHitWall`|`WallSide side`|ScoreSystem, AudioSystem|
|`OnBallWrapped`|`Vector2 newPos`|VFXSystem, AudioSystem|
|`OnPointScored`|`PointScoredData`|ScoreSystem, UISystem, BallSystem|
|`OnPowerUpPrimed`|`PowerUpType, PlayerSide`|PowerUpSystem, UISystem|
|`OnPowerUpActivated`|`PowerUpType, PlayerSide`|PowerUpSystem, VFXSystem, AudioSystem|
|`OnPowerUpDeactivated`|`PowerUpType, PlayerSide`|PowerUpSystem, UISystem|
|`OnLedgeLifeChanged`|`PlayerSide, int lives`|LedgeSystem, UISystem, VFXSystem|
|`OnMatchEnd`|`WinnerSide`|UISystem, NetworkSystem|
|`OnAIDecision`|`AIAction`|LedgeSystem (AI-controlled)|

### 14.2 Event Flow Example — Force Bounce

```
Player Input (press key)
    │
    ▼
PowerUpSystem.OnForceBouncePrimed
    │ fires: OnPowerUpPrimed(ForceBouce, P1)
    ▼
UISystem → highlight ForceBouce slot

Ball enters P1 court
    │ fires: OnBallEnteredCourt(P1)
    ▼
PowerUpSystem → activates ForceBouce for P1
    │ fires: OnPowerUpActivated(ForceBounce, P1)
    ▼
VFXSystem → ledge glow
AudioSystem → readiness sound

Ball hits P1 ledge
    │ fires: OnBallHitLedge(contactData)
    ▼
PowerUpSystem → detects active ForceBounce
    → applies 1.8× speed multiplier to ball
    │ fires: OnPowerUpDeactivated(ForceBounce, P1)
    ▼
VFXSystem → impact flash + ball trail flare
AudioSystem → electric crack SFX
UISystem → dim ForceBouce slot
```

---

## 15. Future Roadmap

### Phase 1 — Polish & Core Completion

- Smooth ledge inertia and in-place stopping
- Full VFX pass on all power-ups
- Mobile touch input refinement
- Online leaderboards (Photon or custom backend)

### Phase 2 — Content Expansion

- **4-Player Multiplayer:** Top and bottom ledges added. Each player defends one edge. Team 2v2 or free-for-all.
- **Additional Power-Ups:**
    - _Gravity Shift_ — reverses the wrap direction temporarily.
    - _Decoy Ball_ — spawns a fake ball for 2 seconds.
    - _Ledge Heal_ — restore 1 ledge life.
    - _Speed Freeze_ — momentarily caps ball speed at current value.
- **Power-Up Upgrades:** spend in-game currency to upgrade power-up stats (dash speed, Vanish duration, etc.)
- **Arena Expansion:** New arenas, each with a unique physics twist.

### Phase 3 — Monetisation & Meta

- **In-App Currency / Points:** Earned through play; used for cosmetics.
- **Cosmetics:**
    - Ball skins (shape, colour, trail style)
    - Ledge skins (materials, animations)
    - Background / arena skins
    - Hit effect skins
- **Season Leaderboards:** Global and friends-only ranking.
- **Dynamic Power-Up Animations:** Fully animated HUD icons per power-up.

### Phase 4 — Arena Physics System

- Each arena introduces a **named physics rule** (e.g., "Gravity Well" arena pulls the ball slightly toward the centre).
- Arena introduction cinematic / flythrough on first play.
- Per-arena music and visual theme.

---

## 16. Glossary

|Term|Definition|
|---|---|
|**Ledge**|The player-controlled paddle used to deflect the ball.|
|**Court**|The half of the arena belonging to a player.|
|**Primed State**|A power-up is selected but waiting for the ball to enter the player's court.|
|**Active State**|A power-up is live and its effect can trigger.|
|**Wrap Zone**|The top and bottom edges — the ball teleports through these.|
|**Miss Zone**|The left and right boundaries — ball passing these scores a point.|
|**Destroyer State**|The ball state during Destroyer Bounce, carrying speed escalation and point multipliers.|
|**Broken Ledge**|A ledge with 0 lives remaining, granting 2× points to the opponent on all misses.|
|**Total Match Score**|The combined score of both players; used to calculate ball speed scaling.|
|**Spendable Points**|A player's current point balance available to spend on power-ups.|
|**Host**|The Photon Master Client; authoritative for ball simulation and scoring.|
|**EventBus**|The central event channel system used for all inter-system communication.|

---

_Document maintained by the development team. Version history tracked in source control._ _Last updated: 2026_