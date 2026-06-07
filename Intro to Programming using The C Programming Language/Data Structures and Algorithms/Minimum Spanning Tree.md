### **Definition of MST**

A **Minimum Spanning Tree** of a weighted undirected graph is a **tree** that:

1. **Connects all vertices** - Every vertex is reachable from every other
2. **Contains no cycles** - It's a tree
3. **Has minimum total weight** - The sum of edge weights is as small as possible
4. Uses exactly **n-1 edges** (where n = number of vertices)

---

### **7. Detailed Example: Building an MST**

Let me show you step-by-step how to understand MSTs.

**Original Graph (4 vertices, all possible edges):**

```Code
        5
    1 ---- 2
   /|      |\
  6 |      | 7
   \|      |/
    3 ---- 4
        2
```

**All edges listed:**

|Edge|Weight|
|---|---|
|1-2|5|
|1-3|6|
|2-4|7|
|3-4|2|

**What we need for an MST:**

- Must connect all 4 vertices: ✓
- Must use exactly 3 edges (4-1=3): ✓
- Must have NO cycles: ✓
- Must have MINIMUM total weight: ✓

**Finding the MST:**

**Option 1:** Use edges: (1-2: 5), (1-3: 6), (2-4: 7)

```Code
Total weight: 5 + 6 + 7 = 18

    1 ---- 2
    |      |
    3      4

Check:
- All vertices connected? YES ✓
- No cycles? YES ✓
- 3 edges? YES ✓
- Minimum weight? Let's check other options...
```

**Option 2:** Use edges: (3-4: 2), (1-2: 5), (1-3: 6)

```Code
Total weight: 2 + 5 + 6 = 13

    1 ---- 2
    |
    3 ---- 4

Check:
- All vertices connected? YES ✓
- No cycles? YES ✓
- 3 edges? YES ✓
- Minimum weight? 13 < 18, so this is better!
```

**Option 3:** Use edges: (3-4: 2), (1-2: 5), (2-4: 7)

```Code
Total weight: 2 + 5 + 7 = 14

    1 ---- 2
           |
    3 ---- 4

Check: 
- All vertices connected? Let's see... 1→2→4→3. YES ✓
- No cycles? YES ✓
- 3 edges? YES ✓
- Minimum weight? 14 > 13, so worse than Option 2
```

**The MST is Option 2 with weight 13.**

---

### **8. Why Can an MST Be Different?**

**Important:** An MST is not always unique!

If two edges have the same weight, you might have multiple valid MSTs:

```Code
Graph with equal weights:
    1 ---- 2
    |      |
    4      4
    |      |
    3 ---- 4
        4

MST Option 1: Use (1-2:4), (1-3:4), (3-4:4) = weight 12
MST Option 2: Use (1-2:4), (1-3:4), (2-4:4) = weight 12
MST Option 3: Use (1-2:4), (2-4:4), (3-4:4) = weight 12

All are valid MSTs! They just connect different edges.
```

---

### **9. Real-World Examples**

**Example 1: Connecting Cities**

```Code
Cities: New York, Boston, Philadelphia, Washington DC

Costs to build roads:
NY ----100---- Boston
|                |
|                |
50              40
|                |
Philadelphia --- DC
       30

MST would minimize total road-building cost:
Use roads: NY-Philly (50), Philly-DC (30), Boston-DC (40)
Total cost: 120

Why? We need to connect all 4 cities with minimum cost.
Can't use NY-Boston (100) because that's too expensive.
```

**Example 2: Computer Network**

```Code
Servers: A, B, C, D, E

Cable costs to connect (in $):
A-B: 50    B-C: 30    C-D: 10    D-E: 20
A-C: 80    B-D: 70    A-E: 100
B-E: 60

MST would find cheapest way to connect all servers:
Use: C-D (10), D-E (20), B-C (30), A-B (50)
Total: 110

This ensures all servers are connected with minimum cable cost.
```

---

## **PART 3: PROPERTIES OF MST**

### **10. Why MSTs Matter**

1. **Minimize Cost**
    
    - Building a road network to connect cities
    - Installing water pipes to connect neighborhoods
    - Network cables to connect servers
2. **Find Connected Components**
    
    - Identify which points must be connected
    - Group related items together
3. **Approximation Algorithm**
    
    - Use as stepping stone for harder problems
    - Traveling Salesman Problem approximation
4. **Data Clustering**
    
    - Group similar data points
    - Image segmentation