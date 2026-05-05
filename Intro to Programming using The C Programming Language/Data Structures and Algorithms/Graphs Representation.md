***This guide introduces the core concepts of Graph Theory, from how computers "see" graphs to specialized graph types and complex problems like the $k$-Clique.***

## 1. Graph Representations

A graph $G = (V, E)$ consists of a set of vertices (nodes) $V$ and edges (connections) $E$. To work with graphs in code, we need efficient ways to store them.

### 1.1 Adjacency List

Think of this as a **"Who are my friends?"** list for every person in a room.

- **Structure:** An array `Adj` of $|V|$ lists. `Adj[u]` contains all vertices $v$ such that $(u, v) \in E$.
- **Space:** $\Theta(V + E)$. You only store what exists.
- **Time to check edge** $(u, v)$**:** $O(\text{deg}(u))$.
- **Best For:** **Sparse graphs** ($|E| \ll |V|^2$).

**Pseudocode:**

```
// To find all neighbors of vertex u:
FOR each v IN Adj[u]:
    PRINT v
```

### 1.2 Adjacency Matrix

Think of this as a **spreadsheet** where rows and columns are vertices.

- **Structure:** A $|V| \times |V|$ grid $A$. $A[u][v] = 1$ if $(u, v) \in E$, else $0$.    
- **Symmetry:** For **undirected** graphs, the matrix is symmetric ($A[u][v] = A[v][u]$).
- **Time to check edge** $(u, v)$**:** $\Theta(1)$ (instant lookup).
- **Space:** $\Theta(V^2)$.
- **Best For:** **Dense graphs** or fast edge lookups.

### 1.3 Incidence Matrix

This focuses on the relationship between **vertices** and **edges**.

- **Structure:** A $|V| \times |E|$ matrix $B$.    
- **Undirected Graphs:** $B[v][e] = 1$ if vertex $v$ is an endpoint of edge $e$, else $0$.
- **Directed Graphs:**
    
    - $B[v][e] = -1$ if edge $e$ **leaves** vertex $v$.
    - $B[v][e] = +1$ if edge $e$ **enters** vertex $v$.
    - $B[v][e] = 0$ otherwise.
    
- **Space:** $\Theta(V \cdot E)$.

### 1.4 Summary Table

|   |   |   |   |
|---|---|---|---|
|**Feature**|**Adjacency List**|**Adjacency Matrix**|**Incidence Matrix**|
|**Space**|$\Theta(V + E)$|$\Theta(V^2)$|$\Theta(V \cdot E)$|
|**Edge Query** $(u, v)$|$O(\text{deg}(u))$|$\Theta(1)$|$O(E)$|
|**List Neighbors**|$\Theta(\text{deg}(u))$|$\Theta(V)$|$O(E)$|

## 2. Special Graph Families

### 2.1 Proximity Graphs

Edges are determined by distances between points $P \subset \mathbb{R}^d$.

- **Euclidean Graph:** $V = P$, edge $\{p, q\}$ has weight $= ||p - q||_2$.
- **Unit Disk Graph:** Connect $p$ and $q$ iff $||p - q|| \le r$. (Used for wireless range modeling).
- $k$**-Nearest Neighbor (kNN):** Connect each point to its $k$ closest points. (Used in ML/clustering).

### 2.2 Intersection Graphs

Vertices are objects; edges exist if objects overlap.

- **Interval Graphs:** Each vertex is an interval $[s_i, f_i]$. Edge exists if $[s_i, f_i] \cap [s_j, f_j] \neq \emptyset$.
    - **Max Independent Set:** To find the most non-conflicting jobs, use a Greedy approach: sort by finish time $f_i$ and pick greedily.
- **Disk/Rectangle Intersection:** Vertices are 2D shapes. Models frequency assignment or map labeling.

## 3. The $k$-Clique Problem

- **Definition:** Does there exist a subset of $k$ vertices where every pair is connected?
    
- **General Graphs:** In an adjacency matrix, checking all possibilities takes roughly $\Omega(n^k)$ time (NP-Complete).
    
- **Unit Disk Graphs:** Because of geometric constraints, we can often lower the complexity using specialized geometric algorithms that beat the $\Omega(n^k)$ general bound.
    

## 4. Practice Questions & Proofs

### Q1: Space Complexity & Usage

For $|V|=n$ and $|E|=m$, **Adjacency List** takes $\Theta(n + m)$ space.

**Matrix** is better when the graph is **dense** ($m \approx n^2$) or you need $O(1)$ edge checks.

### Q2: Complete Bipartite Graph $K_{m,n}$  

Vertices are split into two sets of size $m$ and $n$. Every vertex in $m$ connects to every vertex in $n$.

**Total Edges:** $m \cdot n$.

### Q3: Proof: Tree of $n$ vertices has $n-1$ edges

**Proof by Induction:**

1. **Base Case (**$n=1$**):** A single node has 0 edges ($1-1=0$). True.
    
2. **Assumption:** A tree with $k$ nodes has $k-1$ edges.
    
3. **Step (**$n=k+1$**):** To add a $(k+1)$-th node to a tree, you must connect it to the existing structure using **exactly one** edge to keep it connected without creating a cycle.
    
4. **Result:** New edge count = $(k-1) + 1 = k$. Since $n = k+1$, then $k = n-1$.
    

## 5. Visualizing Matrices

### Undirected Adjacency Matrix

```
(0)---(1)
 |
(2)

    0  1  2
0 [ 0  1  1 ]
1 [ 1  0  0 ]
2 [ 1  0  0 ]
```

### Directed Incidence Matrix

```
(0) --e1--> (1)

      e1
0 [ -1 ] (Leaves 0)
1 [ +1 ] (Enters 1)
```