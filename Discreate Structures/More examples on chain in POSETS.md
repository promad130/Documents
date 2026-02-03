## Example 1: Chains in a Divisibility Poset

Let's work with the poset ({1,2,3,4,6,8,12}, ∣ )({1,2,3,4,6,8,12},∣) where a ∣ ba∣b means "a divides b"​[](https://trotter.math.gatech.edu/math-3012/11-Chain_and_Antichain_Partitions.pdf)​.

## Example 1.1: Chain {1,2,6,12}{1,2,6,12}

**Why is this a chain?** Let me verify that every pair is comparable:

- 1 ∣ 21∣2 ✓ (1 divides 2)
    
- 1 ∣ 61∣6 ✓ (1 divides 6)
    
- 1 ∣ 121∣12 ✓ (1 divides 12)
    
- 2 ∣ 62∣6 ✓ (2 divides 6)
    
- 2 ∣ 122∣12 ✓ (2 divides 12)
    
- 6 ∣ 126∣12 ✓ (6 divides 12)
    

Since every pair is comparable (and the relation forms a total order within this subset), **this is a chain**![](https://trotter.math.gatech.edu/math-3012/11-Chain_and_Antichain_Partitions.pdf)​

**Visualization**: We can arrange this chain linearly:

text

`1 → 2 → 6 → 12`

Each element divides the next one in the sequence.[](https://trotter.math.gatech.edu/math-3012/11-Chain_and_Antichain_Partitions.pdf)​

## Example 1.2: Chain {1,3,6,12}{1,3,6,12}

**Verification**:

- 1 ∣ 31∣3 ✓
    
- 1 ∣ 61∣6 ✓
    
- 1 ∣ 121∣12 ✓
    
- 3 ∣ 63∣6 ✓
    
- 3 ∣ 123∣12 ✓
    
- 6 ∣ 126∣12 ✓
    

**Linear arrangement**:

text

`1 → 3 → 6 → 12`

This is also a chain with length 4.[](https://en.wikipedia.org/wiki/Antichain)​

## Example 1.3: Chain {2,4,8}{2,4,8}

**Verification**:

- 2 ∣ 42∣4 ✓ (since 4=2×24=2×2)
    
- 2 ∣ 82∣8 ✓ (since 8=2×48=2×4)
    
- 4 ∣ 84∣8 ✓ (since 8=4×28=4×2)
    

**Linear arrangement**:

text

`2 → 4 → 8`

This is a chain of length 3.[](https://en.wikipedia.org/wiki/Antichain)​

## Example 1.4: NOT a Chain — {2,3}{2,3}

**Why is this NOT a chain?**

Let's check comparability:

- Is 2 ∣ 32∣3? No, 2 doesn't divide 3
    
- Is 3 ∣ 23∣2? No, 3 doesn't divide 2
    

Since 2 and 3 are **incomparable**, the subset {2,3}{2,3} is **not a chain**. Instead, it's an **antichain**![](https://trotter.math.gatech.edu/math-3012/11-Chain_and_Antichain_Partitions.pdf)​

## Example 1.5: NOT a Chain — {1,2,3,6}{1,2,3,6}

Even though this subset contains many comparable pairs, let's check:

- 1 ∣ 21∣2 ✓
    
- 1 ∣ 31∣3 ✓
    
- 1 ∣ 61∣6 ✓
    
- 2 ∣ 62∣6 ✓
    
- 3 ∣ 63∣6 ✓
    
- But: 22 and 33 are **incomparable** ✗
    

Since there exists at least one pair (2 and 3) that is incomparable, **this is NOT a chain**.[](https://en.wikipedia.org/wiki/Antichain)​

**Important lesson**: For a subset to be a chain, **ALL** pairs must be comparable, not just some.[](https://www.isibang.ac.in/~d.yogesh/Course_Notes/DM1/Ch8.S1.html)​

## Example 2: Chains in a Subset Poset

Consider the poset (P({a,b,c}),⊆)(P({a,b,c}),⊆) — the power set of {a,b,c}{a,b,c} ordered by subset inclusion.​[](https://trotter.math.gatech.edu/math-3012/11-Chain_and_Antichain_Partitions.pdf)​

## Example 2.1: Maximum Chain {∅,{a},{a,b},{a,b,c}}{∅,{a},{a,b},{a,b,c}}

**Verification**:

- ∅⊆{a}∅⊆{a} ✓
    
- ∅⊆{a,b}∅⊆{a,b} ✓
    
- ∅⊆{a,b,c}∅⊆{a,b,c} ✓
    
- {a}⊆{a,b}{a}⊆{a,b} ✓
    
- {a}⊆{a,b,c}{a}⊆{a,b,c} ✓
    
- {a,b}⊆{a,b,c}{a,b}⊆{a,b,c} ✓
    

**Linear arrangement**:

text

`∅ ⊆ {a} ⊆ {a,b} ⊆ {a,b,c}`

This is a **maximum chain** with length 4. Notice how each set properly contains the previous one![](https://www.iitg.ac.in/rinkulu/note/dilwposets-note.pdf)​

## Example 2.2: Another Maximum Chain {∅,{b},{b,c},{a,b,c}}{∅,{b},{b,c},{a,b,c}}

**Verification**:

- ∅⊆{b}∅⊆{b} ✓
    
- ∅⊆{b,c}∅⊆{b,c} ✓
    
- ∅⊆{a,b,c}∅⊆{a,b,c} ✓
    
- {b}⊆{b,c}{b}⊆{b,c} ✓
    
- {b}⊆{a,b,c}{b}⊆{a,b,c} ✓
    
- {b,c}⊆{a,b,c}{b,c}⊆{a,b,c} ✓
    

**Linear arrangement**:

text

`∅ ⊆ {b} ⊆ {b,c} ⊆ {a,b,c}`

This is also a maximum chain with length 4.[](https://trotter.math.gatech.edu/math-3012/11-Chain_and_Antichain_Partitions.pdf)​

## Example 2.3: Shorter Chain {{a},{a,b}}{{a},{a,b}}

**Verification**:

- {a}⊆{a,b}{a}⊆{a,b} ✓
    

This is a valid chain, but it's shorter (length 2).[](https://en.wikipedia.org/wiki/Antichain)​

## Example 2.4: NOT a Chain — {{a},{b}}{{a},{b}}

**Why NOT?**

- Is {a}⊆{b}{a}⊆{b}? No
    
- Is {b}⊆{a}{b}⊆{a}? No
    

The sets {a}{a} and {b}{b} are **incomparable**, so this is **not a chain**.[](https://trotter.math.gatech.edu/math-3012/11-Chain_and_Antichain_Partitions.pdf)​

## Example 2.5: NOT a Chain — {{a,b},{b,c}}{{a,b},{b,c}}

**Why NOT?**

- Is {a,b}⊆{b,c}{a,b}⊆{b,c}? No (because a∉{b,c}a∈/{b,c})
    
- Is {b,c}⊆{a,b}{b,c}⊆{a,b}? No (because c∉{a,b}c∈/{a,b})
    

These two sets are **incomparable**, so this is **not a chain**.[](https://en.wikipedia.org/wiki/Antichain)​