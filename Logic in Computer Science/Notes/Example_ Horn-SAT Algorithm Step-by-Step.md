<img src="https://r2cdn.perplexity.ai/pplx-full-logo-primary-dark%402x.png" style="height:64px;margin-right:32px"/>

# Converting to DNF Using Tableaux

Let's break down how the **Semantic Tableaux** method helps convert a propositional logic formula to **Disjunctive Normal Form (DNF)**. DNF is a standard way of writing logical formulas as an OR of ANDs (i.e., a disjunction of conjunctions).

## Step-by-Step Process

1. **Build the Tableau for the Formula**
    - Start with your formula as the root node.
    - Apply the tableau rules (decompose α- and β-formulas) until all branches contain only literals (atomic formulas or their negations).
2. **Identify Open Branches**
    - Each open branch (no complementary pair) represents a possible assignment that makes the formula true.
    - The set of literals in each open branch forms a conjunction (AND) of literals.
3. **Form the DNF**
    - The DNF is the disjunction (OR) of all these conjunctions—one for each open branch.

## Example 1:



## Example 2: \$ (p \to q) \land (q \to r) \$

### Step 1: Rewrite Implications

- \$ p \to q \equiv \neg p \lor q \$
- \$ q \to r \equiv \neg q \lor r \$
- So, formula is \$ (\neg p \lor q) \land (\neg q \lor r) \$


### Step 2: Tableau Construction

- **Root:** \$ \{(\neg p \lor q) \land (\neg q \lor r)\} \$
- **Decompose \$ \land \$ (α-formula):**
    - \$ \{\neg p \lor q, \neg q \lor r\} \$
- **Decompose \$ \neg p \lor q \$ (β-formula):**
    - Branch 1: \$ \{\neg p, \neg q \lor r\} \$
    - Branch 2: \$ \{q, \neg q \lor r\} \$


#### Branch 1: \$ \{\neg p, \neg q \lor r\} \$

- Decompose \$ \neg q \lor r \$ (β-formula):
    - Sub-branch 1a: \$ \{\neg p, \neg q\} \$ → **open**
    - Sub-branch 1b: \$ \{\neg p, r\} \$ → **open**


#### Branch 2: \$ \{q, \neg q \lor r\} \$

- Decompose \$ \neg q \lor r \$ (β-formula):
    - Sub-branch 2a: \$ \{q, \neg q\} \$ → **closed**
    - Sub-branch 2b: \$ \{q, r\} \$ → **open**


### Collect Open Branches

- \$ \{\neg p, \neg q\} \$
- \$ \{\neg p, r\} \$
- \$ \{q, r\} \$


### Write DNF

$$
(\neg p \land \neg q) \lor (\neg p \land r) \lor (q \land r)
$$

## Summary

- **Tableaux** help break down formulas into sets of literals.
- **Open branches** give the conjunctions for DNF.
- **DNF** is the OR of all these conjunctions.

If you want to try a specific formula, let me know and we can walk through it together, step by step!
<span style="display:none">[^1][^2][^3]</span>

<div align="center">⁂</div>

[^1]: Lec14.pdf

[^2]: Lec15.pdf

[^3]: Lec10.pdf

