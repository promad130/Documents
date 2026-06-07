## 1. Introduction and Motivation
Think of the real world: almost everything is connected. Cities are connected by roads, people are connected by friendships, and computers are connected by the internet. **Graph theory** is the mathematical foundation we use to model, study, and solve problems involving these connections.

If you want to find the shortest route on Google Maps, recommend friends on Facebook, or schedule a massive construction project, you use graphs.

## 2. What is a Graph?
A graph is simply a collection of points and the lines that connect them.
Mathematically, a graph is written as **G = (V, E)**.
- **V (Vertices / Nodes):** The "points" or entities
- **E (Edges):** The "lines" or connections between the points.

### Undirected vs. Directed Graphs

**1. Undirected Graph**
The connections are two-way streets. If A is connected to B, B is equally connected to A. Edges are represented as unordered pairs: `{u, v}`.

_Visual Example (Undirected):_

```
  (A)-------(B)
   |       /
   |      /
   |     /
  (C)---(D)
```

- **Vertices (V):** {A, B, C, D}
- **Edges (E):** {{A,B}, {A,C}, {C,D}, {B,D}} 

**2. Directed Graph (Digraph)**
The connections are one-way streets. Arrows indicate the direction. Edges are ordered pairs: `(u, v)`.
- **u** is the _tail_ (where it starts).
- **v** is the _head_ (where it points).

_Visual Example (Directed):_

```
  (A) ----> (B)
   ^         |
   |         |
   |         v
  (C) <---- (D)
```

- **Edges (E):** {(A,B), (B,D), (D,C), (C,A)}

## Weighted Graphs

A **weighted graph** assigns a numerical value (weight) to each edge.

**What does weight represent?**

- Distance between cities
- Cost of connecting two points
- Time to travel
- Network bandwidth
- Cost to install a cable

**Visual Example with Weights:**

```Code
        5
    A ---- B
    |      |
    3      2
    |      |
    C ---- D
        1

This means:
- Edge A-B has weight 5
- Edge A-C has weight 3
- Edge B-D has weight 2
- Edge C-D has weight 1
```

**How to Read Weights:** Each number on an edge tells you the "cost" or "distance" of that edge.

## 2.1 Real-World Examples

To lock this into your brain:
- **Road Networks:** Vertices = Intersections. Edges = Roads. (Usually undirected, but one-way streets make it directed).
- **Social Networks:** Vertices = People. Edges = Friendships (Undirected: if I am your friend, you are mine).
- **Twitter/Instagram:** Vertices = Users. Edges = Follows (Directed: I can follow you, but you don't have to follow me back).
- **Web Graph:** Vertices = Webpages. Edges = Hyperlinks (Directed).
- **Task Scheduling:** Vertices = Tasks. Edges = Prerequisites (Directed: Task A must happen _before_ Task B).

## 3. Basic Definitions

### 3.1 Adjacency (Who is your neighbor?)
- **Undirected:** If there is a line between A and B, they are **adjacent**.
- **Directed:** If there is an arrow from A to B, B is **adjacent from** A. (A is adjacent _to_ B).

### 3.2 Degree (How popular are you?)

The **degree** of a vertex is the number of lines connected to it.

**In an Undirected Graph:**

In the undirected visual above, vertex (B) connects to A and D.

- `Degree(B) = 2`    

**In a Directed Graph, it splits into two:**

- **In-degree:** Number of arrows pointing _into_ the vertex.
- **Out-degree:** Number of arrows pointing _out of_ the vertex.

**The Handshaking Lemma:**

Imagine a party. Every time two people shake hands, two hands are involved. In a graph, every edge connects two vertices. Therefore, if you add up the degrees of _all_ vertices, the sum will always be exactly twice the number of edges.

- `Sum of all degrees = 2 * (Number of Edges)`

### 3.3 Paths and Cycles

- **Path:** A sequence of vertices where you can walk from one to the next following the edges.
    
    - _Example:_ A → B → D is a path from A to D.
    
- **Length of a Path:** The number of _edges_ you crossed (not the number of vertices!). The path A → B → D has a length of 2.
    
- **Simple Path:** A path where you never visit the same vertex twice.
    
- **Cycle:** A path that starts and ends at the _same_ vertex without repeating any other vertices along the way.
    
    - _Example:_ A → B → D → C → A is a cycle.
        

### 3.4 Connectivity (Is anyone left out?)

- **Undirected Graph:** It is **connected** if you can start at any vertex and walk to any other vertex. (It is one single piece).
    
- **Directed Graph:**
    
    - **Strongly Connected:** You can reach _every_ vertex from _every other_ vertex by strictly following the arrows.    
    - **Weakly Connected:** If you ignore the arrows (pretend it's undirected), the graph is connected. But with the arrows, some vertices might be impossible to reach from others.

## 3.5 Special Graphs

There are specific shapes of graphs that are so common they get their own names.

### 1. Tree
A connected, undirected graph with **zero cycles**.

_Rule:_ A tree with `N` vertices always has exactly `N - 1` edges.

```
      (A)
     /   \
   (B)   (C)
  /   \
(D)   (E)
```

Check out [[Tree Discrete Structure]] and then [[Binary tree]]

### 2. Forest

Just what it sounds like: a collection of unconnected Trees. An undirected graph with no cycles, but not fully connected.

```
  Tree 1       Tree 2
   (A)          (D)
    |          /   \
   (B)       (E)   (F)
```

### 3. DAG (Directed Acyclic Graph)

A directed graph with **no directed cycles**. You can never loop back to where you started. These are crucial for modeling schedules, prerequisites, and family trees.

```
  (Start) ----> (Task A)
     |             |
     v             v
 (Task B) ----> (End)
```

### 4. Bipartite Graph

The vertices can be split into two separate teams (Team U and Team V). Edges _only_ connect a player from Team U to a player from Team V. Nobody on Team U connects to anyone else on Team U.

_(Example: Job applicants on the left, Job Openings on the right)_

```
 Team U       Team V
  (1) -------- (A)
        \
  (2) ---\---- (B)
          \ 
  (3) -------- (C)
```

### 5. Complete Graph (Kn)

Everyone knows everyone. Every single vertex is directly connected to every other vertex.

_Rule:_ A complete graph with `n` vertices has `n * (n - 1) / 2` edges.

_Visual Example (K4 - Complete graph with 4 vertices):_

```
  (A)-------(B)
   | \     / |
   |  \   /  |
   |   \ /   |
   |    X    |
   |   / \   |
   |  /   \  |
  (C)-------(D)
```

## Appendix: How to represent a Graph in Pseudocode

If you were to write a program using graphs, you don't draw circles. You usually use an **Adjacency List** (a list of neighbors for each vertex).

```
// Representing the Undirected Graph: A-B, A-C, C-D, B-D

Graph = {
  "A": ["B", "C"],
  "B": ["A", "D"],
  "C": ["A", "D"],
  "D": ["C", "B"]
}

// To find the degree of "B":
degree_of_B = length_of(Graph["B"]) // returns 2
```


# Graph Representation
There are many ways to represent graphs:
![[Graphs Representation]]