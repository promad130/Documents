Mathematical induction is a common and powerful method to prove statements about natural numbers. The proof involves two steps:
- **Base Step:** Prove the statement is true for the first value (often n=0 or n=1).
- **Induction Step:** Suppose the statement is true for n=k (Induction Hypothesis), then prove for n=k+1.

## Induction Template
Suppose you want to prove: "For all n ≥ 0, P(n) is true.”
- **Step 1**: Prove P(0).
- **Step 2**: For arbitrary k ≥ 0, if P(k) is true, prove P(k+1).    
- **Conclusion**: Therefore, P(n) holds for all n ≥ 0.

---
## Practical Uses
- **Proving formulas** (e.g., sums, products).
- **Algorithm correctness** (loop invariants).
- **Properties about sets** recursively defined (linked lists, trees).


---
---
# Structural Induction
Structural induction is used to prove properties about recursively defined structures, especially formulas in logic.

**Process:**
- **Base Step:** Prove the property for all atomic propositions $p$ in the set of all atomic propositions, denoted as IP.    
- **Induction Step:** If a property holds for formula components $\text{(B and/or C)}$, show it holds for their combination (e.g., using logical connectives operators).

**Example Used:**
- For every formula $A$, the number of 0left parentheses equals the number of right parentheses.
- **Proof by structural induction:**
    - _Base step:_ Atomic proposition—no parentheses, property holds.
    - _Induction step:_ If true for sub-formulas, it’s true for combinations via logical operators.

