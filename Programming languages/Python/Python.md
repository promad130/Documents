# Introduction to Python
## **What is Python?**
Python is a high-level, general-purpose programming language created by Guido van Rossum and first released in 1991. It is widely known for its simple, readable syntax and versatility, making it one of the most popular programming languages in the world today.

## **Key Features of Python**
- **Readability:** Python code is designed to be easy to read and write, with a syntax that often resembles plain English(Like literally).
- **Versatility:** It is used for a wide range of applications, including web development, software development, data analysis, artificial intelligence, scientific computing, automation, and more.
- **Cross-Platform:** Python runs on multiple platforms, including Windows, Mac, Linux, and Raspberry Pi.
- **Multi-Paradigm:** Supports procedural, object-oriented, and functional programming styles.
- **Dynamic Typing:** Variables in Python do not require explicit type declarations, making the language flexible and concise.
- **Interpreted Language:** Python code is executed line by line, which allows for rapid prototyping and interactive development.

---
# Python Syntax Basics
- Python uses indentation (whitespace) to define code blocks, unlike many languages that use curly braces.
- New lines are used to end statements, not semicolons.
- Example of a simple Python program:
```python
print("Hello, World!")
```

So let's have a look at the ![Syntax](Syntax%20in%20Python)

## Ways to run a python script:
To run a Python script, you can use multiple methods depending on your environment and needs. Here are the most common approaches:
#### 1. **Command Line Execution**
- **Basic command:**  
    Navigate to the script's directory and run:
```bash
python script_name.py
```
- On Linux/macOS, use `python3` if Python 2 is the default.
- On Windows, `py` or `pythonw` (for GUI apps) can also be used.

- **Direct execution (Unix-based systems):**  
    Add a shebang line (`#!/usr/bin/env python3`) to the script, make it executable with `chmod +x script_name.py`, and run:
```bash
./script_name.py
```

#### 2. Integrated Development Environments (IDEs)
- **Visual Studio Code:**  
    Use the **Run Python File** button (▶️) or press `Ctrl+F5`.
- **PyCharm/IDLE:**  
    Use built-in run options like `Ctrl+R` or toolbar buttons.
- **Jupyter Notebooks:**  
    Execute code cells interactively.

#### 3. File Manager (Double-Click Execution)
- **Windows:** Associate `.py` files with `python.exe` to run by double-clicking.
- **Linux/macOS:** Ensure the script has execute permissions and a shebang line.

#### 4. Programmatic Execution
- **From another Python script:**  
    Use `exec()` or `subprocess`:
```python
exec(open("script.py").read())  # Simple execution
```
```python
import subprocess 
subprocess.run(["python", "script.py"])  # With output handling.
```

---
# Getting Started

- [[Data Types in Python]]
- [[Variables and typecasting in Python]].
- [[Input output in Python]] 
- [[Operators and mathematical functions in Python]] 
- [[Control Flow Statements in python]] 
- [[Data Structures in Python]]
- [[Functions in Python]]

Now lets have a [side quest](String%20Functions%20in%20Python)!!

# Inbuilt Algorithms:

## Sorting Algorithms

Python has **two built-in sorting functions** that use the same algorithm (Timsort).

### sort() vs sorted()

**`list.sort()`** - Modifies the original list, returns None:

```python
arr = [3, 1, 4, 2]
arr.sort()
# arr is now [1, 2, 3, 4]
```

**`sorted()`** - Returns a new sorted list, original unchanged:

```python
arr = [3, 1, 4, 2]
new_arr = sorted(arr)
# new_arr is [1, 2, 3, 4]
# arr is still [3, 1, 4, 2]
```


## Key Parameters

**Reverse order**:

```python
arr.sort(reverse=True)  # [4, 3, 2, 1]
sorted(arr, reverse=True)
```

**Custom sorting with key**:

```python
words = ["apple", "pie", "zoo"]
words.sort(key=len)  # Sort by length: ["pie", "zoo", "apple"]

students = [("Alice", 25), ("Bob", 20)]
sorted(students, key=lambda x: x[^1])  # Sort by age
```


## Timsort Algorithm

Python uses **Timsort** - a hybrid of merge sort and insertion sort.

**Time Complexity**:

- **Best case**: O(n) - when data is already sorted or nearly sorted
- **Average case**: O(n log n)
- **Worst case**: O(n log n)

**Space Complexity**: O(n)


# MatPlotLib
## What is Matplotlib? 
**Matplotlib** is Python's most popular library for creating static, animated, and interactive visualizations.  It lets you turn data into graphs, charts, and plots. 

**Why use it?**
- Visualize data to understand patterns
- Create publication-quality figures
- Works seamlessly with NumPy and Pandas
- Industry standard for Python plotting

---

## Installation

```bash
pip install matplotlib
```

---
# `pyplot` in matplotlib

## Core Concepts

### 1. **Figure and Axes**
- **Figure**: The entire window/page where you draw
- **Axes**: The actual plot area (where data is shown)
- One Figure can contain multiple Axes (subplots)

```python
import matplotlib.pyplot as plt

# Create figure and axes
fig, ax = plt.subplots()  # Returns figure and one axes
plt.show()
```

**Why this matters:** Understanding this hierarchy helps you control every element of your plot.


## Basic Plotting

### Line Plot
```python
import matplotlib.pyplot as plt

# Data
x = [1, 2, 3, 4, 5]
y = [2, 4, 6, 8, 10]

# Create plot
plt.plot(x, y)
plt.show()
```

**What happens:**
- `plt.plot()` creates a line connecting points
- `plt.show()` displays the plot

---

### Customizing Line Plots

```python
plt.plot(x, y, 
         color='red',           # Line color
         linewidth=2,           # Line thickness
         linestyle='--',        # Dashed line
         marker='o',            # Point markers
         markersize=8,          # Marker size
         label='My Data')       # Label for legend

plt.xlabel('X Axis')            # X-axis label
plt.ylabel('Y Axis')            # Y-axis label
plt.title('My First Plot')      # Title
plt.legend()                    # Show legend
plt.grid(True)                  # Add grid
plt.show()
```

**Common linestyles:** `'-'` (solid), `'--'` (dashed), `'-.'` (dash-dot), `':'` (dotted)

**Common markers:** `'o'` (circle), `'s'` (square), `'^'` (triangle), `'*'` (star), `'+'` (plus)

---

## Types of Plots

### 1. Scatter Plot
Shows individual data points without connecting them. 

```python
x = [1, 2, 3, 4, 5]
y = [2, 3, 5, 7, 11]

plt.scatter(x, y, 
            s=100,              # Size of points
            c='blue',           # Color
            alpha=0.5,          # Transparency (0-1)
            edgecolors='black') # Border color

plt.title('Scatter Plot')
plt.show()
```

**When to use:** Showing relationships between two variables, identifying correlations.

---

### 2. Bar Chart
Compares categories with rectangular bars.

```python
categories = ['A', 'B', 'C', 'D']
values = [10, 25, 15, 30]

plt.bar(categories, values, 
        color='green',
        width=0.6,              # Bar width
        edgecolor='black')

plt.title('Bar Chart')
plt.ylabel('Values')
plt.show()
```

**Horizontal bar chart:** Use `plt.barh(categories, values)`

**When to use:** Comparing quantities across categories.

---

### 3. Histogram
Shows distribution of numerical data.

```python
data = [1, 2, 2, 3, 3, 3, 4, 4, 5, 5, 5, 5, 6, 7, 8]

plt.hist(data, 
         bins=5,                # Number of bins
         color='purple',
         edgecolor='black',
         alpha=0.7)

plt.title('Histogram')
plt.xlabel('Value')
plt.ylabel('Frequency')
plt.show()
```

**What are bins?** Ranges that group data.  More bins = more detail.

**When to use:** Understanding data distribution, finding patterns.

---

### 4. Pie Chart
Shows proportions of a whole.

```python
labels = ['Python', 'Java', 'C++', 'JavaScript']
sizes = [40, 25, 20, 15]
colors = ['gold', 'lightblue', 'lightgreen', 'pink']
explode = (0.1, 0, 0, 0)  # Explode first slice

plt.pie(sizes, 
        labels=labels,
        colors=colors,
        explode=explode,        # Separate slices
        autopct='%1.1f%%',      # Show percentages
        shadow=True,
        startangle=90)          # Rotation angle

plt.title('Programming Languages')
plt.show()
```

**When to use:** Showing percentage breakdown of categories.


#### `explode` (Slice Separation)

This pulls specific slices out from the center to highlight them.[^1]

- **Input:** A list or tuple of numbers (floats), one for each slice.
- **How it works:** `0` keeps the slice in the center. Any value greater than `0` (e.g., `0.1`, `0.2`) moves that slice outward by a fraction of the radius.[^8][^14]
- **Use case:** Emphasizing a specific category, like a "winning" slice or an outlier.


#### `autopct` (Automatic Percentage)

This automatically calculates and displays the percentage value of each slice directly on the chart.[^4][^1]

- **Input:** A format string (e.g., `'%.1f%%'`) or a function.
- **How it works:**
    - `'%.1f%%'`: Shows 1 decimal place (e.g., 25.0%).[^4]
    - `'%.0f%%'`: Shows integers only (e.g., 25%).
    - The trailing `%%` is required to display the actual "%" symbol.[^4]
- **Use case:** Showing exact proportions without manually calculating them.


#### `startangle` (Rotation)

This rotates the entire pie chart counter-clockwise to change where the first slice begins.[^2][^7]

- **Input:** A number in degrees (float or int).
- **How it works:**
    - `0` (default): Starts at the positive x-axis (3 o'clock position).[^3]
    - `90`: Starts at the top (12 o'clock position).[^2]
- **Use case:** Aligning the most important slice to start at the top for better readability.


#### Quick Example

```python
import matplotlib.pyplot as plt

# Data
sizes = [30, 10, 60]
labels = ['A', 'B', 'C']

# 1. explode: Pull out the 2nd slice ('B') by 0.1
explode_values = (0, 0.1, 0) 

plt.pie(sizes, labels=labels, 
        explode=explode_values,  # Highlights slice 'B'
        autopct='%.1f%%',        # Shows percentages (e.g., 10.0%)
        startangle=90)           # Rotates chart so start is at 12 o'clock

plt.show()
```

---

## Subplots (Multiple Plots)

### Method 1: Using `plt.subplots()`

```python

fig, axes = plt.subplots(2, 2, figsize=(10, 8))  # 2 rows, 2 columns

# axes is a 2D array
axes[0, 0].plot([1, 2, 3], [1, 4, 9])
axes[0, 0].set_title('Plot 1')

axes[0, 1].scatter([1, 2, 3], [2, 3, 4])
axes[0, 1].set_title('Plot 2')

axes[1, 0].bar(['A', 'B', 'C'], [10, 20, 15])
axes[1, 0].set_title('Plot 3')

axes[1, 1].hist([1, 2, 2, 3, 3, 3, 4], bins=4)
axes[1, 1].set_title('Plot 4')

plt.tight_layout()  # Adjust spacing
plt.show()
```

**Why `tight_layout()`?** Prevents overlapping titles and labels.

---

### Method 2: Using `plt.subplot()`

```python
plt.figure(figsize=(10, 4))

plt.subplot(1, 2, 1)  # 1 row, 2 cols, position 1
plt.plot([1, 2, 3], [1, 4, 9])
plt.title('First Plot')

plt.subplot(1, 2, 2)  # 1 row, 2 cols, position 2
plt.scatter([1, 2, 3], [2, 3, 4])
plt.title('Second Plot')

plt.show()
```

There are two similar functions: `plt.subplot()` (adds one plot) and `plt.subplots()` (creates a grid of plots at once). Here are the parameters for both, simplified.

### 1. `plt.subplot()`

Used to add a single plot to a figure individually.[^1][^8]

* `nrows`, `ncols`, `index` (Positional arguments)
    * **What it does:** Defines the grid layout and where this specific plot goes.
    * **Format:** Can be three separate integers (e.g., `2, 2, 1`) or a compressed 3-digit integer (e.g., `221`).
    * **Example:** `subplot(2, 2, 1)` creates a 2x2 grid and selects the 1st slot (top-left).


### 2. `plt.subplots()`

Used to create a full figure and multiple subplots in one go.[^2][^5]

* `nrows`, `ncols` (Optional, default=1)
    * **What it does:** Sets how many rows and columns of charts you want.
    * **Example:** `plt.subplots(2, 3)` = 2 rows, 3 columns (6 charts total).
* `sharex`, `sharey` (Optional, default=False)
    * **What it does:** Locks axes together. If you zoom on one, the others follow. Helpful when comparing data with the same units.
    * **Values:** `True`, `False`, `'row'` (share per row), `'col'` (share per column).[^9]
* `figsize` (Keyword argument via `**fig_kw`)
    * **What it does:** Sets the total size of the figure in inches `(width, height)`.
    * **Example:** `plt.subplots(figsize=(10, 5))`.
* `constrained_layout` (Keyword argument via `**fig_kw`)
    * **What it does:** Automatically adjusts spacing so labels and titles don't overlap.
    * **Value:** `True` or `False`.


### Cheat Sheet

| Task           | Function                         | Common Usage                   |
| :------------- | :------------------------------- | :----------------------------- |
| **Add 1 plot** | `plt.subplot(rows, cols, index)` | `plt.subplot(2, 1, 1)`         |
| **Make grid**  | `plt.subplots(rows, cols)`       | `fig, ax = plt.subplots(2, 2)` |


---

## Customization Deep Dive

### Figure Size
```python
plt.figure(figsize=(10, 6))  # Width, Height in inches
```

---

### Axis Limits
```python
plt.xlim(0, 10)   # X-axis from 0 to 10
plt.ylim(0, 100)  # Y-axis from 0 to 100
```

---

### Axis Ticks
```python
plt.xticks([0, 2, 4, 6, 8, 10])           # Custom tick positions
plt.yticks([0, 25, 50, 75, 100], 
           ['Low', 'Med-Low', 'Med', 'Med-High', 'High'])  # Custom labels
```

---

### Colors

**Named colors:** `'red'`, `'blue'`, `'green'`, `'black'`, etc.

**Hex codes:** `'#FF5733'`

**RGB tuples:** `(0. 5, 0.2, 0.8)` (values 0-1)

**Short codes:** `'r'` (red), `'b'` (blue), `'g'` (green), `'k'` (black)

---

### Styles
```python
plt.style.use('ggplot')  # Apply style
```

**Popular styles:** `'ggplot'`, `'seaborn'`, `'dark_background'`, `'bmh'`, `'classic'`

**See all styles:** `print(plt.style.available)`

---

## Saving Figures

```python
plt.plot([1, 2, 3], [1, 4, 9])
plt.savefig('my_plot.png', 
            dpi=300,              # Resolution
            bbox_inches='tight',  # Remove white space
            transparent=True)     # Transparent background
```

**Supported formats:** PNG, PDF, SVG, JPG

---

## Object-Oriented vs Pyplot Interface

### Pyplot Interface (Beginner-Friendly)
```python
plt.plot([1, 2, 3], [1, 4, 9])
plt.title('My Plot')
plt.show()
```

**Pros:** Quick and simple
**Cons:** Less control with complex plots

---

### Object-Oriented Interface (Recommended)
```python
fig, ax = plt.subplots()
ax.plot([1, 2, 3], [1, 4, 9])
ax.set_title('My Plot')
ax.set_xlabel('X')
ax.set_ylabel('Y')
plt.show()
```

**Pros:** Full control, better for complex visualizations
**Cons:** Slightly more verbose

---

## Working with NumPy

```python
import numpy as np
import matplotlib.pyplot as plt

# Generate data
x = np.linspace(0, 10, 100)  # 100 points from 0 to 10
y = np.sin(x)

plt.plot(x, y, label='sin(x)')
plt.plot(x, np.cos(x), label='cos(x)')
plt.legend()
plt.show()
```

---

## Annotations and Text

```python
x = [1, 2, 3, 4, 5]
y = [1, 4, 9, 16, 25]

plt. plot(x, y, 'o-')

# Add text annotation
plt.annotate('Peak', 
             xy=(5, 25),              # Point to annotate
             xytext=(4, 20),          # Text position
             arrowprops=dict(arrowstyle='->', color='red'))

# Add text
plt.text(2, 10, 'Important Point', fontsize=12)

plt.show()
```

---

## Common Mistakes to Avoid

1. **Forgetting `plt.show()`** - Plot won't display
2. **Not clearing figures** - Use `plt.clf()` or `plt.close()` between plots
3. **Wrong data types** - Ensure x and y are lists/arrays, same length
4. **Overlapping labels** - Use `plt.tight_layout()`
5. **Not using labels/titles** - Makes plots hard to understand

---

## Practical Example:  Complete Workflow

```python
import matplotlib.pyplot as plt
import numpy as np

# Generate sample data
months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun']
sales_2024 = [120, 150, 180, 160, 200, 250]
sales_2025 = [130, 160, 190, 180, 220, 280]

# Create figure with style
plt.style.use('seaborn-v0_8-darkgrid')
fig, (ax1, ax2) = plt.subplots(1, 2, figsize=(14, 5))

# Plot 1: Line comparison
ax1.plot(months, sales_2024, 'o-', linewidth=2, markersize=8, label='2024')
ax1.plot(months, sales_2025, 's--', linewidth=2, markersize=8, label='2025')
ax1.set_title('Monthly Sales Comparison', fontsize=14, fontweight='bold')
ax1.set_xlabel('Month', fontsize=12)
ax1.set_ylabel('Sales ($1000s)', fontsize=12)
ax1.legend(loc='upper left')
ax1.grid(True, alpha=0.3)

# Plot 2: Bar comparison
x = np.arange(len(months))
width = 0.35
ax2.bar(x - width/2, sales_2024, width, label='2024', color='skyblue')
ax2.bar(x + width/2, sales_2025, width, label='2025', color='orange')
ax2.set_title('Sales by Month', fontsize=14, fontweight='bold')
ax2.set_xlabel('Month', fontsize=12)
ax2.set_ylabel('Sales ($1000s)', fontsize=12)
ax2.set_xticks(x)
ax2.set_xticklabels(months)
ax2.legend()

plt.tight_layout()
plt.savefig('sales_report.png', dpi=300, bbox_inches='tight')
plt.show()
```

---

# patches in matplotlib

## What are Patches?

**Patches** are geometric shapes (rectangles, circles, polygons, etc.) that you can add to Matplotlib plots. Unlike lines created with `plt.plot()`, patches are filled 2D shapes with customizable borders and colors.

## Why Use Patches?

- Highlight regions in plots (e.g., mark safe zones, danger zones)
- Draw schematic diagrams (robots, buildings, circuits)
- Create custom visualizations (flowcharts, infographics)
- Annotate data with shapes


## Setup

```python
import matplotlib.pyplot as plt
import matplotlib.patches as patches

# Always create a figure and axes first
fig, ax = plt.subplots()
```


***

## Common Patch Types

### 1. Rectangle

The most used patch. Defined by **bottom-left corner** (not center).[^2][^1]

```python
rect = patches.Rectangle(
    xy=(2, 3),       # Bottom-left corner (x, y)
    width=4,         # Horizontal size
    height=2,        # Vertical size
    angle=0,         # Rotation in degrees (rotates around xy)
    fc='blue',       # Face color (fill)
    ec='black',      # Edge color (border)
    linewidth=2,     # Border thickness
    alpha=0.7        # Transparency (0=invisible, 1=opaque)
)
ax.add_patch(rect)
```

**Key Point**: `angle` rotates around the `xy` point (bottom-left), not the center.[^3]

### 2. Circle

Defined by **center** and **radius**.

```python
circle = patches.Circle(
    xy=(5, 5),       # Center (x, y)
    radius=2,        # Radius
    fc='red',        # Fill color
    ec='black',      # Border color
    fill=True        # Set False for just outline
)
ax.add_patch(circle)
```


### 3. Ellipse

Oval shape with separate width and height.[^2]

```python
ellipse = patches.Ellipse(
    xy=(5, 5),       # Center
    width=4,         # Total width (not radius!)
    height=2,        # Total height
    angle=30,        # Rotation angle
    fc='yellow'
)
ax.add_patch(ellipse)
```


### 4. Polygon

Custom shape from list of vertices.[^4]

```python
# Define vertices as (x, y) coordinates
vertices = [(1, 1), (4, 1), (5, 3), (2, 4)]

polygon = patches.Polygon(
    vertices,        # List of (x, y) points
    closed=True,     # Connect last point to first
    fc='green',
    ec='black',
    linewidth=2
)
ax.add_patch(polygon)
```


### 5. RegularPolygon

Creates symmetric shapes (triangles, hexagons, etc.).[^2]

```python
# Triangle
triangle = patches.RegularPolygon(
    xy=(5, 5),       # Center
    numVertices=3,   # Number of sides
    radius=2,        # Distance from center to vertex
    orientation=0,   # Rotation in radians
    fc='purple'
)

# Hexagon
hexagon = patches.RegularPolygon((8, 8), 6, radius=1.5, fc='orange')

ax.add_patch(triangle)
ax.add_patch(hexagon)
```


### 6. Arrow

Directional indicator.

```python
arrow = patches.Arrow(
    x=1, y=1,        # Start position
    dx=3, dy=2,      # Direction vector (end = start + d)
    width=0.5,       # Arrow thickness
    color='red'
)
ax.add_patch(arrow)
```


### 7. Wedge

Pie slice or arc.

```python
wedge = patches.Wedge(
    center=(5, 5),   # Center point
    r=3,             # Radius
    theta1=0,        # Start angle (degrees)
    theta2=60,       # End angle (degrees)
    fc='cyan',
    ec='black'
)
ax.add_patch(wedge)
```


***

## Essential Workflow

### Step 1: Create Figure \& Axes

```python
fig, ax = plt.subplots(figsize=(8, 6))
ax.set_xlim(0, 10)    # Set x-axis range
ax.set_ylim(0, 10)    # Set y-axis range
ax.set_aspect('equal') # CRITICAL: Prevents distortion
```


### Step 2: Create Patch Object

```python
shape = patches.Rectangle((2, 3), 4, 2, fc='blue')
```


### Step 3: Add to Axes

```python
ax.add_patch(shape)  # REQUIRED: Adds shape to plot
```


### Step 4: Display

```python
plt.show()
```


***

# Animations in Matplotlib

## What is Animation?

Animation creates the illusion of motion by displaying a sequence of images (frames) rapidly. In Matplotlib, this means **repeatedly updating a plot** to show changes over time.[^1][^2]

Think of it like a flipbook: each page is a "frame" that's slightly different from the previous one.

## Why Use Animations?

- Visualize data changing over time (stock prices, sensor readings)
- Show algorithm execution (sorting, pathfinding)
- Create simulations (physics, robotics, populations)
- Make presentations more engaging
- Understand dynamic processes better than static plots


## The Core Concept

**Animation = Calling a function repeatedly to update plot objects**.

Instead of:

```python
plt.plot(x, y)  # Draw once
plt.show()
```

You do:

```python
# Draw initial state
line, = plt.plot([], [])

# Define what changes each frame
def update(frame):
    line.set_data(new_x, new_y)
    return line,

# Repeat update() many times
FuncAnimation(fig, update, frames=100)
```


## FuncAnimation - The Main Tool

### Import

```python
from matplotlib.animation import FuncAnimation
```


### Basic Structure

```python
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation

# 1. Create figure and initial objects
fig, ax = plt.subplots()
line, = ax.plot([], [], 'b-')  # Note the comma!

# 2. Define update function
def update(frame):
    # Modify plot objects based on frame number
    # frame = 0, 1, 2, 3, ..., frames-1
    return line,  # Return tuple of modified objects

# 3. Create animation
ani = FuncAnimation(
    fig,           # Figure to animate
    update,        # Function to call each frame
    frames=100,    # Number of frames (0 to 99)
    interval=50    # Milliseconds between frames (50ms = 20 FPS)
)

# 4. Display
plt.show()
```


### Key Parameters[^4]

| Parameter | Type | Description | Example |
| :-- | :-- | :-- | :-- |
| `fig` | Figure | The figure to animate | `fig` |
| `func` | function | Called each frame to update plot | `update` |
| `frames` | int/iterable | Number of frames or sequence of values | `100`, `range(50)` |
| `interval` | int | Milliseconds between frames | `50` (20 FPS) |
| `repeat` | bool | Loop animation? Default: `True` | `False` |
| `repeat_delay` | int | Delay before repeat (ms) | `2000` |
| `blit` | bool | Optimization (advanced) | `False` |
| `init_func` | function | Reset function (optional) | `init` |


***

## The Update Function

The update function is **called automatically** for each frame.

### Structure

```python
def update(frame):
    """
    frame: Automatically passed by FuncAnimation
           - If frames=100, frame goes 0, 1, 2, ..., 99
           - If frames=[5, 10, 15], frame gets 5, then 10, then 15
    """
    # Modify plot objects here
    line.set_data(x_new, y_new)
    circle.center = (new_x, new_y)
    
    # MUST return tuple of modified artists
    return line, circle
```


### Why Return a Tuple?

Matplotlib needs to know **which objects changed** so it can redraw them efficiently.

```python
return line,        # Single object (note the comma!)
return line, circle # Multiple objects
```


***

## Complete Examples

### Example 1: Growing Line (Classic)

```python
import numpy as np
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation

# Prepare data
x = np.linspace(0, 2*np.pi, 100)
y = np.sin(x)

# Create figure
fig, ax = plt.subplots()
ax.set_xlim(0, 2*np.pi)
ax.set_ylim(-1.5, 1.5)
ax.set_title('Growing Sine Wave')
ax.grid(True)

# Create empty line (will be filled during animation)
line, = ax.plot([], [], 'b-', linewidth=2)

def update(frame):
    # Show first 'frame' points
    line.set_data(x[:frame], y[:frame])
    return line,

# Animate: 100 frames, 50ms per frame = 5 second animation
ani = FuncAnimation(fig, update, frames=len(x), interval=50)
plt.show()
```

**What happens:**

- Frame 0: Line has 0 points
- Frame 50: Line has 50 points
- Frame 99: Line has 100 points (complete sine wave)


### Example 2: Moving Object

```python
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.patches as patches
from matplotlib.animation import FuncAnimation

# Create path (circular motion)
t = np.linspace(0, 2*np.pi, 100)
x_path = 5 + 3*np.cos(t)
y_path = 5 + 3*np.sin(t)

# Setup figure
fig, ax = plt.subplots()
ax.set_xlim(0, 10)
ax.set_ylim(0, 10)
ax.set_aspect('equal')
ax.set_title('Moving Circle')

# Create circle
circle = patches.Circle((0, 0), 0.3, fc='red')
ax.add_patch(circle)

def update(frame):
    # Move circle to new position
    circle.center = (x_path[frame], y_path[frame])
    return circle,

ani = FuncAnimation(fig, update, frames=len(t), interval=30)
plt.show()
```


### Example 3: Scatter Plot Animation

```python
import numpy as np
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation

fig, ax = plt.subplots()
ax.set_xlim(0, 10)
ax.set_ylim(0, 10)
ax.set_title('Random Walk')

# Create empty scatter
scat = ax.scatter([], [], s=100, c='blue')

# Initialize position
x_data = [^5]
y_data = [^5]

def update(frame):
    # Random walk
    x_data.append(x_data[-1] + np.random.uniform(-0.5, 0.5))
    y_data.append(y_data[-1] + np.random.uniform(-0.5, 0.5))
    
    # Update scatter data
    scat.set_offsets(np.c_[x_data, y_data])
    return scat,

ani = FuncAnimation(fig, update, frames=100, interval=100, repeat=False)
plt.show()
```


### Example 4: Multiple Objects (Robot on Path)

```python
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.patches as patches
from matplotlib.animation import FuncAnimation

# Path
t = np.linspace(0, 4*np.pi, 200)
x_path = t
y_path = np.sin(t)

# Setup
fig, ax = plt.subplots(figsize=(10, 4))
ax.set_xlim(0, 4*np.pi)
ax.set_ylim(-2, 2)
ax.set_aspect('equal')
ax.plot(x_path, y_path, 'k--', alpha=0.3, label='Path')

# Robot (rectangle)
robot = patches.Rectangle((0, 0), 0.5, 0.3, fc='blue', ec='black', lw=2)
ax.add_patch(robot)

# Trail (line showing where robot has been)
trail, = ax.plot([], [], 'r-', alpha=0.5, linewidth=1)
trail_x, trail_y = [], []

def update(frame):
    # Update robot position
    x, y = x_path[frame], y_path[frame]
    robot.set_xy((x - 0.25, y - 0.15))  # Center the robot
    
    # Update trail
    trail_x.append(x)
    trail_y.append(y)
    trail.set_data(trail_x, trail_y)
    
    return robot, trail

ani = FuncAnimation(fig, update, frames=len(t), interval=30)
plt.legend()
plt.show()
```


***

## Animation Types

### Type 1: Accumulating Data (Growing)

Data accumulates over time (e.g., drawing a line).

```python
def update(frame):
    line.set_data(x[:frame], y[:frame])  # First 'frame' points
    return line,
```


### Type 2: Moving Window (Scrolling)

Show a fixed window that moves through data.

```python
window_size = 50

def update(frame):
    start = max(0, frame - window_size)
    line.set_data(x[start:frame], y[start:frame])
    return line,
```


### Type 3: Complete Replacement

Replace entire dataset each frame (e.g., heatmap, real-time sensor).

```python
def update(frame):
    new_data = generate_random_data()
    im.set_array(new_data)
    return im,
```


### Type 4: Object Position

Move object to new coordinates.

```python
def update(frame):
    circle.center = (x_positions[frame], y_positions[frame])
    return circle,
```


***

## The `frames` Parameter Deep Dive[^6]

### Integer: Simple Frame Count

```python
FuncAnimation(fig, update, frames=100)
# update() receives: 0, 1, 2, 3, ..., 99
```


### List/Array: Custom Sequence

```python
FuncAnimation(fig, update, frames=[0, 5, 10, 20, 50])
# update() receives: 0, then 5, then 10, then 20, then 50
```


### Range: With Step

```python
FuncAnimation(fig, update, frames=range(0, 100, 5))
# update() receives: 0, 5, 10, 15, ..., 95
```


### Generator: Infinite Animation

```python
def frame_gen():
    i = 0
    while True:
        yield i
        i += 1

FuncAnimation(fig, update, frames=frame_gen)
# Runs forever, update() gets 0, 1, 2, 3, ... indefinitely
```


***

## Init Function (Optional but Useful)[^3]

The `init_func` **resets the animation** when it repeats.[^5]

```python
line, = ax.plot([], [])

def init():
    """Called at start and when animation loops."""
    line.set_data([], [])  # Clear the line
    return line,

def update(frame):
    line.set_data(x[:frame], y[:frame])
    return line,

ani = FuncAnimation(fig, update, init_func=init, frames=100)
```

**When to use:**

- When animation repeats (`repeat=True`)
- When you need a clean starting state
- When objects need to be cleared between loops

***

## Controlling Animation Speed

### Method 1: `interval` Parameter

```python
FuncAnimation(fig, update, frames=100, interval=50)
# 50ms per frame = 20 frames per second (FPS)
```

**Common values:**

- `interval=16` → 60 FPS (smooth)
- `interval=33` → 30 FPS (standard)
- `interval=50` → 20 FPS (acceptable)
- `interval=100` → 10 FPS (slow)


### Method 2: Skip Frames

```python
FuncAnimation(fig, update, frames=range(0, 1000, 10), interval=50)
# Shows frame 0, 10, 20, 30, ... (faster animation)
```


***

## Saving Animations

### Save as MP4 (Requires ffmpeg)

```python
ani = FuncAnimation(fig, update, frames=100)
ani.save('animation.mp4', writer='ffmpeg', fps=20)
```


### Save as GIF (Requires Pillow)

```python
ani.save('animation.gif', writer='pillow', fps=20)
```


### Install Writers

```bash
# FFmpeg (for MP4)
# Linux/Mac: sudo apt install ffmpeg / brew install ffmpeg
# Windows: Download from https://ffmpeg.org/

# Pillow (for GIF)
pip install pillow
```


### Save Without Displaying

```python
ani = FuncAnimation(fig, update, frames=100)
ani.save('output.mp4', writer='ffmpeg', fps=20)
plt.close()  # Don't show window
```


***

## Common Patterns

### Pattern 1: Update Line Data

```python
line, = ax.plot([], [])

def update(frame):
    line.set_data(x_data, y_data)
    # OR
    line.set_xdata(x_data)
    line.set_ydata(y_data)
    return line,
```


### Pattern 2: Update Scatter

```python
scat = ax.scatter([], [])

def update(frame):
    # set_offsets expects Nx2 array
    data = np.column_stack([x_data, y_data])
    scat.set_offsets(data)
    return scat,
```


### Pattern 3: Update Patch Position

```python
rect = patches.Rectangle((0, 0), 2, 1)
ax.add_patch(rect)

def update(frame):
    rect.set_xy((new_x, new_y))
    rect.angle = new_angle
    return rect,
```


### Pattern 4: Update Text

```python
text = ax.text(5, 5, '', fontsize=12)

def update(frame):
    text.set_text(f'Frame: {frame}')
    return text,
```


### Pattern 5: Update Image/Heatmap

```python
im = ax.imshow(initial_data)

def update(frame):
    new_data = generate_data(frame)
    im.set_array(new_data)
    return im,
```


***

## Common Errors \& Fixes

| Error | Cause | Fix |
| :-- | :-- | :-- |
| `TypeError: 'Line2D' object is not iterable` | Missing comma in return | `return line,` not `return line` |
| Nothing animates | Forgot to assign `ani` variable | `ani = FuncAnimation(...)` (don't delete) |
| Animation doesn't repeat | `repeat=False` | Remove or set `repeat=True` |
| Animation too fast/slow | Wrong `interval` | Adjust `interval` (smaller = faster) |
| Can't save animation | Missing writer | Install `ffmpeg` or `pillow` |
| Only first frame shows | Not returning objects | Add `return line,` to update() |
| Memory leak | Creating new objects each frame | Modify existing objects, don't create new |


***

## Advanced: Dynamic Axis Limits

Sometimes you need axes to grow with the data.

```python
fig, ax = plt.subplots()
line, = ax.plot([], [], 'b-')

x_data, y_data = [], []

def update(frame):
    # Add new data
    x_data.append(frame)
    y_data.append(np.sin(frame * 0.1))
    
    # Update line
    line.set_data(x_data, y_data)
    
    # Adjust axes to fit data
    ax.set_xlim(0, max(x_data) + 1)
    ax.set_ylim(min(y_data) - 0.5, max(y_data) + 0.5)
    
    # Redraw axes
    ax.figure.canvas.draw()
    
    return line,

ani = FuncAnimation(fig, update, frames=100, interval=50)
plt.show()
```


***

## Performance Tips

### 1. Use `blit=True` (Advanced)

Only redraws changed objects (much faster).

```python
def init():
    line.set_data([], [])
    return line,

def update(frame):
    line.set_data(x[:frame], y[:frame])
    return line,

ani = FuncAnimation(fig, update, init_func=init, frames=100, 
                    interval=50, blit=True)
```

**Requirements for blit:**

- Must have `init_func`
- Must return sequence of artists
- Axes limits must be fixed (can't change during animation)


### 2. Reuse Objects

```python
# BAD: Creates new circle each frame (slow)
def update(frame):
    circle = patches.Circle((x, y), 0.1)
    ax.add_patch(circle)

# GOOD: Modifies existing circle (fast)
circle = patches.Circle((0, 0), 0.1)
ax.add_patch(circle)

def update(frame):
    circle.center = (x, y)
    return circle,
```


### 3. Limit Data Points

```python
# Show only last 100 points
def update(frame):
    start = max(0, frame - 100)
    line.set_data(x[start:frame], y[start:frame])
    return line,
```


***

## Practical Real-World Example

```python
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.patches as patches
from matplotlib.animation import FuncAnimation

# Simulate sensor data
np.random.seed(42)
time_data = np.linspace(0, 10, 200)
sensor_data = np.sin(time_data) + 0.1 * np.random.randn(200)
threshold = 0.7

# Setup
fig, (ax1, ax2) = plt.subplots(2, 1, figsize=(10, 6))

# Plot 1: Live sensor reading
ax1.set_xlim(0, 10)
ax1.set_ylim(-2, 2)
ax1.set_title('Live Sensor Data')
ax1.axhline(threshold, color='r', linestyle='--', label='Threshold')
ax1.axhline(-threshold, color='r', linestyle='--')
line, = ax1.plot([], [], 'b-', linewidth=2)
ax1.legend()
ax1.grid(True)

# Plot 2: Status indicator
ax2.set_xlim(0, 1)
ax2.set_ylim(0, 1)
ax2.axis('off')
status_box = patches.Rectangle((0.1, 0.3), 0.8, 0.4, fc='green')
ax2.add_patch(status_box)
status_text = ax2.text(0.5, 0.5, 'NORMAL', ha='center', va='center', 
                       fontsize=20, color='white', weight='bold')

# Data buffers
x_buffer, y_buffer = [], []

def update(frame):
    # Add new data point
    x_buffer.append(time_data[frame])
    y_buffer.append(sensor_data[frame])
    
    # Update line
    line.set_data(x_buffer, y_buffer)
    
    # Check threshold
    current_value = sensor_data[frame]
    if abs(current_value) > threshold:
        status_box.set_facecolor('red')
        status_text.set_text('ALERT!')
    else:
        status_box.set_facecolor('green')
        status_text.set_text('NORMAL')
    
    return line, status_box, status_text

ani = FuncAnimation(fig, update, frames=len(time_data), 
                    interval=50, repeat=False)
plt.tight_layout()
plt.show()
```



# Numpy in python

NumPy (Numerical Python) is the foundational library for scientific computing in Python. It introduces the **Array** object, which is significantly more efficient than standard Python lists.

## What \& Why

* **What:** NumPy replaces Python lists with "ndarrays" (n-dimensional arrays).
* **Why:** Speed and Convenience. NumPy arrays are stored in **contiguous memory** (like C arrays), making them up to 50x faster than Python lists for math operations.[^1][^2]
* **The "Killer Feature":** **Vectorization**. You can do math on entire arrays at once without writing loops.


## Installation

Run this in your terminal:

```bash
pip install numpy
```

Import it in your script (standard convention):

```python
import numpy as np
```


## How to Use It (The Essentials)

### 1. Creating Arrays

Unlike lists, NumPy arrays must contain data of the **same type** (usually integers or floats).

```python
# Convert a list to an array
arr = np.array([1, 2, 3])

# Create a 2D array (Matrix)
matrix = np.array([[1, 2, 3], 
                   [4, 5, 6]])

# Create arrays with placeholders
zeros = np.zeros((3, 3))   # 3x3 matrix of 0s
ones = np.ones((2, 2))     # 2x2 matrix of 1s
rng = np.arange(0, 10, 2)  # Range: Start, Stop, Step -> [0, 2, 4, 6, 8]
```


### 2. Inspecting Arrays

You must know the shape of your data before feeding it into models or formulas.

```python
print(matrix.shape)  # Output: (2, 3) -> 2 rows, 3 columns
print(matrix.ndim)   # Output: 2      -> 2 Dimensions
print(matrix.dtype)  # Output: int64  -> Data type
```


### 3. Vectorization (Math Without Loops)

This is why you use NumPy. Never write a `for` loop to add numbers in an array.

```python
arr = np.array([1, 2, 3])

# Python List way (Slow & Verbose):
# result = [x * 10 for x in list]

# NumPy way (Fast & Clean):
print(arr * 10)      # Output: [10 20 30]
print(arr + arr)     # Output: [2 4 6]
```

*Note: This works for any size. If you have a matrix of 1 million solar panel voltage readings, `arr * 1.1` increases them all by 10% instantly.*[^1]

### 4. Indexing \& Slicing

NumPy allows you to slice multiple dimensions at once using `[row, column]`.

```python
matrix = np.array([[10, 20, 30], 
                   [40, 50, 60]])

# Get specific element [Row 0, Column 1]
print(matrix[0, 1])    # Output: 20

# Get the first two columns of ALL rows
# ":" selects all rows, ":2" selects columns 0 and 1
print(matrix[:, :2])   
# Output:
# [[10 20]
#  [40 50]]
```


### 5. Filtering (Boolean Masking)

You can filter data using conditions directly inside the brackets. This is common for cleaning data (e.g., removing outliers).

```python
data = np.array([10, 55, 6, 80, 2])

# Step 1: Create a mask (True/False)
mask = data > 50  
# mask is [False, True, False, True, False]

# Step 2: Apply mask
high_values = data[data > 50]

print(high_values) # Output: [55 80]
```


### 6. Reshaping

Machine learning models often require specific input shapes (e.g., flattening an image).

```python
# Create 1D array of 6 numbers
arr = np.arange(6) # [0, 1, 2, 3, 4, 5]

# Reshape into 2 Rows, 3 Columns
reshaped = arr.reshape(2, 3)
# Output:
# [[0 1 2]
#  [3 4 5]]
```


# SciPy - Complete Beginner's Guide

## What is SciPy?

**SciPy** (Scientific Python) is a library built on top of NumPy that provides **advanced mathematical and scientific tools**. Think of NumPy as basic math (arrays, addition, multiplication) and SciPy as advanced math (calculus, optimization, statistics, signal processing).[^1][^2]

## Why Use SciPy?

- **Pre-built algorithms**: Instead of coding calculus or statistics from scratch, just call a function
- **Optimized**: C/Fortran under the hood → very fast[^3]
- **Industry standard**: Used in research, engineering, data science
- **Real-world problems**: Interpolation, curve fitting, solving equations, signal processing[^2]


## Installation

```bash
pip install scipy
```

**Note**: SciPy requires NumPy. Always import both:

```python
import numpy as np
from scipy import <module_name>
```


***

## SciPy Structure

SciPy is organized into **subpackages** (modules) for different domains:[^4][^1]


| Module | Purpose | Example Use |
| :-- | :-- | :-- |
| `interpolate` | Fill gaps in data | Estimate sensor reading at time not measured |
| `optimize` | Find min/max, solve equations | Find best parameters for a model |
| `integrate` | Calculus (integrals, ODEs) | Calculate area under curve, physics simulations |
| `linalg` | Linear algebra | Solve systems of equations, eigenvalues |
| `stats` | Statistics | Distributions, hypothesis tests, correlations |
| `signal` | Signal processing | Filter noise, find peaks in data |
| `ndimage` | Image processing | Blur, rotate, filter images |
| `sparse` | Sparse matrices | Handle large matrices with mostly zeros |

**You don't import SciPy as a whole**. Import specific modules:

```python
from scipy import interpolate  # Good
from scipy import optimize, integrate  # Also good
import scipy  # Bad practice (use specific modules)
```


***

## 1. Interpolate - Fill Gaps in Data

### What \& Why

You have measurements at specific points but need to **estimate values in between**. Example: You measured temperature at 12pm and 2pm; what was it at 1pm?[^5]

### How: Basic Interpolation

```python
import numpy as np
from scipy import interpolate

# Known data points
x = np.array([0, 1, 2, 3, 4])
y = np.array([0, 1, 4, 9, 16])  # y = x²

# Create interpolation function
f = interpolate.interp1d(x, y, kind='linear')

# Estimate values at new points
x_new = np.array([0.5, 1.5, 2.5])
y_new = f(x_new)

print(y_new)  # [0.5, 2.5, 6.5]
```


### Interpolation Types (`kind` parameter)

| Type | Description | When to Use |
| :-- | :-- | :-- |
| `'linear'` | Straight lines between points | Simple, fast, good for rough data |
| `'quadratic'` | Curved (2nd degree polynomial) | Smoother than linear |
| `'cubic'` | Smooth curves (3rd degree) | Best for smooth natural data |

```python
# Compare interpolation types
f_linear = interpolate.interp1d(x, y, kind='linear')
f_cubic = interpolate.interp1d(x, y, kind='cubic')

x_smooth = np.linspace(0, 4, 100)
y_linear = f_linear(x_smooth)
y_cubic = f_cubic(x_smooth)
```


### Practical Example: Sensor Data

```python
# Sensor readings (time in hours, temperature in °C)
time = np.array([0, 3, 6, 9, 12])
temp = np.array([20, 22, 25, 23, 21])

# Create interpolator
temp_func = interpolate.interp1d(time, temp, kind='cubic')

# What was temperature at 7:30am (7.5 hours)?
temp_at_7_30 = temp_func(7.5)
print(f"Temperature at 7:30am: {temp_at_7_30:.1f}°C")
```


***

## 2. Optimize - Find Best Values

### What \& Why

**Optimization** means finding the minimum or maximum of a function, or solving equations. Examples:[^6]

- Find the lowest cost for manufacturing
- Find parameters that best fit a curve to data
- Solve equations like $x^2 - 5 = 0$


### A. Finding Minimum/Maximum

```python
from scipy import optimize

# Define function
def cost_function(x):
    return (x - 3)**2 + 5  # Minimum at x=3

# Find minimum
result = optimize.minimize_scalar(cost_function)

print(f"Minimum at x = {result.x}")      # x = 3.0
print(f"Minimum value = {result.fun}")   # value = 5.0
```


### B. Curve Fitting (Find Best Parameters)

**Problem**: You have data points and want to find the equation that best fits them.

```python
from scipy.optimize import curve_fit

# Experimental data (with noise)
x_data = np.array([1, 2, 3, 4, 5])
y_data = np.array([2.1, 3.9, 9.2, 16.1, 25.0])  # Roughly y = x²

# Define model function
def model(x, a, b):
    return a * x**2 + b  # We guess it's quadratic

# Find best parameters (a, b)
params, covariance = curve_fit(model, x_data, y_data)

print(f"Best fit: y = {params[^0]:.2f}x² + {params[^1]:.2f}")
# Output: y = 1.01x² + 0.02 (close to x²)
```


### C. Solving Equations

**Find x where f(x) = 0** (called "root finding"):

```python
from scipy.optimize import fsolve

# Solve: x² - 5 = 0  (answer: x = ±√5 ≈ ±2.236)
def equation(x):
    return x**2 - 5

# Need initial guess
solution = fsolve(equation, x0=2)  # Start searching near x=2
print(f"Solution: x = {solution[^0]:.3f}")  # x = 2.236
```


***

## 3. Integrate - Calculus

### What \& Why

**Integration** calculates the area under a curve. Uses:[^2]

- Physics: distance from velocity, energy calculations
- Statistics: probability distributions
- Engineering: accumulated quantities


### A. Definite Integral (Area Under Curve)

```python
from scipy import integrate

# Define function
def f(x):
    return x**2

# Integrate from 0 to 3: ∫₀³ x² dx
area, error = integrate.quad(f, 0, 3)

print(f"Area: {area}")  # 9.0
# Exact: x³/3 evaluated 0 to 3 = 27/3 = 9
```

**`quad(function, lower_limit, upper_limit)`** returns `(result, error_estimate)`

### B. Practical Example: Total Distance

```python
# Velocity function: v(t) = 2t + 1
def velocity(t):
    return 2*t + 1

# Total distance traveled from t=0 to t=5
distance, _ = integrate.quad(velocity, 0, 5)
print(f"Distance: {distance:.1f} meters")  # 30.0
```


### C. Solving Differential Equations (ODEs)

**Differential equations** describe how things change (population growth, cooling, motion).

```python
from scipy.integrate import solve_ivp

# Example: dy/dt = -y (exponential decay)
def decay(t, y):
    return -y

# Initial condition: y(0) = 10
# Solve from t=0 to t=5
solution = solve_ivp(decay, t_span=(0, 5), y0=[^10], t_eval=np.linspace(0, 5, 50))

# solution.t = time points
# solution.y = y values
print(f"At t=5: y = {solution.y[0, -1]:.3f}")  # ≈ 0.067
# Exact: y = 10*e^(-5) ≈ 0.067
```


***

## 4. Linear Algebra (linalg)

### What \& Why

**Linear algebra** deals with systems of equations, matrices, vectors. Critical for:[^2]

- Engineering (circuit analysis, structural analysis)
- Machine learning (matrix operations everywhere)
- Computer graphics (transformations)


### A. Solve System of Equations

```python
from scipy import linalg

# Solve:  2x + 3y = 8
#         4x + 5y = 14

# Matrix form: Ax = b
A = np.array([[2, 3],
              [4, 5]])
b = np.array([8, 14])

# Solve for x (returns [x, y])
solution = linalg.solve(A, b)
print(f"x = {solution[^0]}, y = {solution[^1]}")  # x=1, y=2
```


### B. Matrix Determinant

```python
A = np.array([[1, 2],
              [3, 4]])

det = linalg.det(A)
print(f"Determinant: {det}")  # -2.0
```


### C. Eigenvalues (Advanced)

**Eigenvalues** show important properties of transformations.

```python
A = np.array([[4, 2],
              [1, 3]])

eigenvalues, eigenvectors = linalg.eig(A)
print(f"Eigenvalues: {eigenvalues}")
```


### D. Matrix Inverse

```python
A = np.array([[1, 2],
              [3, 4]])

A_inv = linalg.inv(A)
print(A_inv)

# Verify: A * A_inv = Identity
print(np.dot(A, A_inv))  # [[1, 0], [0, 1]]
```


***

## 5. Statistics (stats)

### What \& Why

**Statistics** analyzes data to find patterns, make predictions, test hypotheses.[^2]

### A. Probability Distributions

```python
from scipy import stats

# Normal distribution (bell curve)
# mean=0, standard deviation=1
mu, sigma = 0, 1
normal = stats.norm(loc=mu, scale=sigma)

# Calculate probability P(X < 1)
prob = normal.cdf(1)  # Cumulative Distribution Function
print(f"P(X < 1) = {prob:.3f}")  # 0.841

# Generate random samples
samples = normal.rvs(size=1000)  # 1000 random values
```


### B. Descriptive Statistics

```python
data = np.array([2, 4, 4, 4, 5, 5, 7, 9])

mean = np.mean(data)
median = np.median(data)
mode = stats.mode(data, keepdims=True)
std = np.std(data)

print(f"Mean: {mean}")      # 5.0
print(f"Median: {median}")  # 4.5
print(f"Mode: {mode.mode[^0]}")    # 4
print(f"Std Dev: {std:.2f}") # 1.93
```


### C. Hypothesis Testing (t-test)

**Question**: Are two groups significantly different?

```python
# Two groups of measurements
group1 = np.array([5.2, 5.5, 5.8, 6.0, 5.9])
group2 = np.array([6.1, 6.3, 6.5, 6.8, 6.7])

# Perform t-test
t_stat, p_value = stats.ttest_ind(group1, group2)

print(f"p-value: {p_value:.4f}")

if p_value < 0.05:
    print("Groups are significantly different")
else:
    print("No significant difference")
```


### D. Common Distributions

```python
# Uniform distribution (all values equally likely)
uniform = stats.uniform(loc=0, scale=10)  # 0 to 10

# Binomial (coin flips)
binomial = stats.binom(n=10, p=0.5)  # 10 flips, 50% heads

# Poisson (events per time period)
poisson = stats.poisson(mu=3)  # Average 3 events
```


***

## 6. Signal Processing (signal)

### What \& Why

**Signal processing** analyzes and modifies time-series data (audio, sensors, vibrations).[^2]

### A. Find Peaks in Data

```python
from scipy import signal

# Noisy signal with peaks
x = np.linspace(0, 10, 100)
y = np.sin(x) + 0.1 * np.random.randn(100)

# Find local maxima
peaks, properties = signal.find_peaks(y, height=0.5)

print(f"Peaks at indices: {peaks}")
print(f"Peak values: {y[peaks]}")
```


### B. Filter Noise

```python
from scipy.signal import butter, filtfilt

# Create noisy signal
t = np.linspace(0, 1, 500)
clean_signal = np.sin(2 * np.pi * 5 * t)  # 5 Hz sine wave
noisy_signal = clean_signal + 0.5 * np.random.randn(500)

# Design low-pass filter (remove high frequencies)
b, a = butter(N=4, Wn=0.1)  # 4th order, cutoff 0.1

# Apply filter
filtered = filtfilt(b, a, noisy_signal)

# filtered is now much cleaner than noisy_signal
```


***

## 7. Image Processing (ndimage)

### What \& Why

**Image processing** for scientific images (not photos - use OpenCV/Pillow for that).[^2]

### A. Gaussian Blur

```python
from scipy import ndimage

# Create simple image (2D array)
image = np.array([[1, 2, 3],
                  [4, 5, 6],
                  [7, 8, 9]])

# Apply blur
blurred = ndimage.gaussian_filter(image, sigma=1)
print(blurred)
```


### B. Image Rotation

```python
# Rotate image by 45 degrees
rotated = ndimage.rotate(image, angle=45)
```


### C. Edge Detection

```python
# Sobel edge detection
edges = ndimage.sobel(image)
```


***

## Practical Real-World Examples

### Example 1: Polynomial Interpolation for Robotics

```python
from scipy.interpolate import lagrange

# 8 waypoints for robot path
x_points = np.array([0, 1, 2, 3, 4, 5, 6, 7])
y_points = np.array([0, 2, 1, 3, 2, 4, 3, 5])

# Create exact polynomial through all points
poly = lagrange(x_points, y_points)

# Generate smooth path (100 points)
x_smooth = np.linspace(0, 7, 100)
y_smooth = poly(x_smooth)

# Get derivative for robot orientation
from numpy import polyder
poly_deriv = polyder(poly)
slopes = poly_deriv(x_smooth)
angles = np.arctan(slopes)  # Heading angles
```


### Example 2: Minimize Cost Function

```python
from scipy.optimize import minimize

# Cost function: manufacturing cost
# x[^0] = units of product A, x[^1] = units of product B
def total_cost(x):
    return 5*x[^0]**2 + 3*x[^1]**2 + 2*x[^0]*x[^1]

# Constraint: must produce at least 100 total units
constraints = {'type': 'ineq', 'fun': lambda x: x[^0] + x[^1] - 100}

# Initial guess
x0 = [50, 50]

# Minimize
result = minimize(total_cost, x0, constraints=constraints)
print(f"Optimal production: A={result.x[^0]:.1f}, B={result.x[^1]:.1f}")
print(f"Minimum cost: ${result.fun:.2f}")
```


### Example 3: Analyze Experiment Data

```python
from scipy import stats
from scipy.optimize import curve_fit

# Experimental data: time vs concentration
time = np.array([0, 1, 2, 3, 4, 5])
concentration = np.array([10.0, 8.2, 6.7, 5.5, 4.5, 3.7])

# Model: exponential decay C(t) = A * exp(-k*t)
def decay_model(t, A, k):
    return A * np.exp(-k * t)

# Fit model to data
params, covariance = curve_fit(decay_model, time, concentration)
A_fit, k_fit = params

print(f"Fitted model: C(t) = {A_fit:.2f} * exp(-{k_fit:.2f}*t)")

# Predict concentration at t=6
predicted = decay_model(6, A_fit, k_fit)
print(f"Predicted C(6) = {predicted:.2f}")
```


***

## Common Patterns

### Pattern 1: Interpolate Then Plot

```python
from scipy.interpolate import interp1d
import matplotlib.pyplot as plt

# Sparse data
x = np.array([0, 1, 2, 3, 4])
y = np.array([0, 1, 4, 9, 16])

# Interpolate
f = interp1d(x, y, kind='cubic')
x_smooth = np.linspace(0, 4, 100)
y_smooth = f(x_smooth)

# Plot
plt.plot(x, y, 'ro', label='Data')
plt.plot(x_smooth, y_smooth, 'b-', label='Interpolated')
plt.legend()
plt.show()
```


### Pattern 2: Optimize Then Verify

```python
from scipy.optimize import minimize_scalar

def f(x):
    return x**2 - 4*x + 5

result = minimize_scalar(f)
print(f"Minimum at x={result.x}, f(x)={result.fun}")

# Verify by plotting
x_vals = np.linspace(0, 5, 100)
plt.plot(x_vals, f(x_vals))
plt.plot(result.x, result.fun, 'ro', markersize=10)
plt.show()
```


***

## Key Takeaways

### Import Strategy

```python
from scipy import interpolate, optimize, integrate, linalg, stats
# Import only what you need
```


### Most Used Functions

| Module | Function | Purpose |
| :-- | :-- | :-- |
| `interpolate` | `interp1d(x, y, kind)` | Fill gaps in data |
| `optimize` | `minimize_scalar(func)` | Find minimum |
| `optimize` | `curve_fit(model, x, y)` | Fit curve to data |
| `integrate` | `quad(func, a, b)` | Definite integral |
| `linalg` | `solve(A, b)` | Solve Ax=b |
| `stats` | `norm, uniform, etc.` | Probability distributions |
| `signal` | `find_peaks(data)` | Find peaks in signal |
