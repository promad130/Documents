**Logic in Computer Science** is the study and application of formal systems for representing and reasoning about propositions and relationships. It provides the precise language and set of rules that underpin algorithms, programming, verification, and problem-solving in computing.
It is the _systematic study of inferences and arguments_. It forms the backbone of how computers process information, make decisions, and perform computations.
## What is a Statement ?
- A **statement** is a declarative sentence which is either true or false.
    - "The sky is blue."    
    - This can be verified as true or false, making it a statement.
    
- "2 + 2 = 4."
    - This is true, so it is a statement.
    
- "The computer is on."
    - This is either true or false depending on the situation.
    
	**Not Statements:**
- "This sentence is false."
    - This is a paradox; we cannot assign a truth value, so it is not a logical statement.
    
- "Who are you?"
    - A question, not a declarative sentence.

## Subtypes of Statements:
- **Atomic statements** 
	cannot be broken down using boolean operators (e.g., “2 + 2 = 5”). 
	
- **Compound statements** 
	are formed by combining atomic statements with logical operators (e.g., “2 + 2 = 4 and my name is Baskar”). They are also referred as **CIF( Compound Formula)** or **(Compound Well-Formed Formula).**
	
	**Boolean Operators are used to construct compound statements**:
	- **AND (∧):** True only if both propositions are true.    
	- **OR (∨):** True if at least one proposition is true.
	- **NOT (¬):** Negates the truth value of a proposition.
	- **[[IMPLIES (→)]]:** If the first is true, the second must also be true.
	- **IFF (↔):** Both propositions have the same truth value.

---
# Proposition Logic
Proposition Logic deals with statements that are either true or false, and ways to combine them using Logical Operators.
It is required for it to have a certain truth value all the time, it cannot have ambiguity.
A **proposition** is a statement (in logic terminology) regarded abstractly, usually denoted by symbols like **p,q,r**.
“*Regarded abstractly*” means looking at something in terms of its general properties—ignoring its specific details or context—so you can focus on patterns, structure, or relationships.

Propositions are classified into **atomic** and **compound**:
- **Atomic Proposition (Simple Statement)**
	- An atomic proposition *cannot be broken into smaller statements using logical operators.*
	- Examples:
	    - "7 is an odd number." (True, atomic)        
	    - "It is raining." (True or false, atomic)
	    - "The result is correct." (Atomic, depending on a context)
	
- **Compound Proposition (Composite Statement)** 
	- A compound proposition is _created by combining atomic propositions_ using logical operators (AND, OR, NOT, etc.).
	- Examples:
	    - "It is raining AND the temperature is below 15°C."
		    - Let $p$ : "It is raining," $q$ : "Temperature is below 15°C."
	        - Compound: $p\land q$.
            
		- "Either I will have tea OR I will have coffee."
	        - $p$ : "I will have tea," $q$ : "I will have coffee."
	        - Compound: $p\lor q$.
            
    - "If today is Sunday, then tomorrow is Monday."
        - $p$ : "Today is Sunday," $q$ : "Tomorrow is Monday."
        - Compound: $p\implies q$.

## Sets of Propositions and Syntax
- **IP:** The set of atomic propositions $\{p,q,r,...\}$
- **IF:** The set of all [[Well-Formed Formulas (wffs)]] built from IP using logical connectives.
    
- **Rules for Constructing Formulas:**
    - If $p$ is in *IP*, then $p$ is in *IF*.
    - If $A$ and $B$ are in *IF*, so are ($A∧B$), ($A∨B$), ($¬A$), ($A→B$), and ($A↔B$).
    
## **Examples**
- Is "59" a proposition? No (not in IP).
- "9 - 2" is not a proposition.
- "A" in CIF? No.

## Using Parenthesis
Parentheses in propositional logic are used **systematically**—never just for looks or emphasis! They follow strict syntactic rules to unambiguously reflect the structure of formulas.

### Standard Recursive Rules for WFF Formation (and Where Parentheses Appear)
Suppose your atomic propositions (from *IP*) are $q$ and $r$:
1. **Atomic Propositions**
    - If $x∈IP$, then $x$ is a WFF.
    - **No parentheses are needed here.**
    - Examples: $q$ ; $r$.
        
2. **Negation ($¬$)**
    - If $A$ is a WFF, then $(¬A)$ is a WFF.
    - **Parentheses must surround the negation and its operand.**
    - Example: $(¬q)$ is a WFF, but ¬q is **not** (by these rules).
    
3. **Binary Connectives ($∧$, $∨$, $→$, $↔$)**
    - If $A$ and $B$ are WFFs, then $(A∗B)$ is a WFF, where $∗$ is any binary connective.    
    - **Parentheses must surround the entire compound expression.**
    - Example: $(q→r)$; $((¬q)∨r)$.
	
4. **No Extra Parentheses**
    - You cannot arbitrarily add parentheses around atomic formulas—$(q)$ is **not** a WFF unless the formation rules explicitly permit it.
    - Only use parentheses per the construction rules.

# Formula 
## Recall:
![[Well-Formed Formulas (wffs)]]

A **formula** (sometimes also called a well-formed formula) is a sequence of symbols constructed using atomic propositions, Boolean connectives, and parentheses.

For example:
- If $A$ and $B$ are formulas, then $(A∨B)$ is a formula.
- Atomic propositions themselves are formulas: $p$ is a formula.
- Compounds like $¬(A∧B)$ are formulas if $A$ and $B$ are themselves formulas.
- Sequences like $A−B$ are NOT formulas unless both $A$ and $B$ satisfy the required properties.

## Principal Operator of a Formula
In the formation tree (which is a binary tree representation of the formula), the principal operator is the root node's operator.
For atomic formulas, there is no principal operator—only for compounds.

For example:
    - In $(b∨q)$, the principal operator is $∨$.
    - In $¬q∨r$, the principal operator is again $∨$.
    - In $¬p$, the principal operator is $¬$.

## Recursive Definitions in Logic Formulas
In logic, formulas are built from atomic propositions (like $p, q, r$) using logical operators (like NOT $\neg$, AND $\land$, OR $\lor$, etc.).

How are formulas built?
- **Base case:** Any atomic proposition (like $p$) is a formula.
- **Recursive step:**
    - If $B$ is a formula, then $\neg B$ is a formula.
    - If $B$ and $C$ are formulas, then $(B \land C)$, $(B \lor C)$, $(B \to C)$, and $(B \leftrightarrow C)$ are formulas.

So, **B** and **C** are just placeholders for any formulas (they could be simple or complex). When we write $A = B \ op \ C$, it means $A$ is a formula made by joining two smaller formulas $B$ and $C$ with a binary operator (like AND, OR, etc.).

## Subformulas:
**Subformulas (SFA):** The set of all formulas contained within a larger formula.

### Recursive Definition of Subformulas (SFA)
The set of subformulas of a formula $A$, denoted as *SFA*, is defined recursively as follows:
- **If A is an atomic proposition:**  
    `SFA`={$A$}
- **If A= op B:** _(where op is a unary operator, like NOT)_  
    `SFA`=`SFB` $∪$ {$A$}
    
- **If A= B op C:** _(where op is a binary operator, like AND, OR, IMPLIES etc.)_  
    `SFA`=`SFB` $∪$ `SFC` $∪$ {$A$} 

This definition ensures that all atomic parts and compacted parts (formed by logical operators) are included.

### Examples

#### Example 1: Simple Binary Compound
Let $A=(p∨q)$
- Here, $p$ and $q$ are atomic
- So, `SFA`={$p$} $∪$ {$q$} $∪$ {$p∨q$}

#### Example 2: Nested Compound Formula
Let $B=¬(p∨q)$
- $p∨q$ is itself a compound as above: `SFB`={$p,\;q,\;p∨q$}
- $¬(p∨q)$ applies unary NOT:  
    `SFB`={$p,\;q,\;p∨q$}  
    `SFA`=`SFB` $∪$ {$¬(p∨q)$} = {$p,\;q,\;p∨q,\;¬(p∨q)$} 

## Logically Equivalence and Syntactic Equality of WFFs:
Two formulas are **logically equivalent** if they always produce the same truth value regardless of the truth values of their propositional variables. 
In other words, two formulas $p$ and $q$ are logically equivalent, denoted $p≡q$, if and only if the biconditional $p⇔q$ is a tautology—meaning it is true in every possible valuation or interpretation.

While for the two formulas to be actually equal in true terms, i.e., for them to be considered **Syntactically equal** if and only if:
- If two formulas (wffs) look exactly the same (same symbols in the same order).
- Example: $p∧q$ and $p∧q$ — these are just written identically.

## Equisatisfiable formulas:
**Equisatisfiable formulas** are two logical formulas (or sets of formulas) that are either both satisfiable or both unsatisfiable, but they do not necessarily have the same truth values for every valuation.
- **Satisfiable** means: There exists at least one assignment of truth values to the variables that makes the formula true.
- **Equisatisfiable** means: If one formula is satisfiable, so is the other; if one is unsatisfiable, so is the other.

### Example
Suppose you have formulas $A$ and $B$:
- $A=p∨q$
- $B=(p∨q)∨(r∧¬r)$

Here, $r∧¬r$ is always false, so $B$ is satisfiable if and only if $A$ is satisfiable. Thus, $A$ and $B$ are **equisatisfiable**.

### Why is this useful?
Equisatisfiability is important when transforming formulas (for example, converting to Conjunctive Normal Form or CNF) for algorithms that only care about whether a solution exists, not about the exact truth assignments.

***And yes, two completely irrelevant wffs are also equisatisfiable.***

---
# Normal forms
**Normal forms** are standardized ways to rewrite any propositional formula into a uniform structure. The two primary normal forms are:
- **Disjunctive Normal Form (DNF)**
- **Conjunctive Normal Form (CNF)**

## Why Normal Forms Exist
1. **Uniform Representation:**  
    By transforming arbitrary formulas into a fixed pattern, automated tools need only handle one canonical case rather than every possible nesting of connectives.
    
2. **Algorithmic Simplicity:**  
    Many decision procedures (e.g., SAT solvers, validity checkers) operate more directly and efficiently on formulas in CNF or DNF.
    
3. **Equivalence Checking:**  
    Two formulas are logically equivalent if and only if their normal‐form representations coincide (up to reordering of clauses or terms).
    
4. **Theoretical Foundations:**  
    Existence of normal forms underpins key results (e.g., every Boolean function admits a DNF or CNF expression).
## Disjunctive Normal Form (DNF)
**DNF** is a standardized way to express logical formulas as a "sum of products".
**Structure**: A disjunction (OR) of conjunctions (AND) of literals
- **Literal**: An atomic proposition or its negation (P or ¬P)
- **Cube**: A conjunction of literals (P ∧ ¬Q ∧ R)
- **DNF**: A disjunction of cubes

**Example**: (P ∧ ¬Q) ∨ (¬P ∧ Q ∧ R) is in DNF.

**Key Property**: DNF satisfiability is easy to check - a DNF formula is satisfiable if it contains at least one non-contradictory cube (a cube without both P and ¬P).

## Conjunctive Normal Form (CNF)
**CNF** is the dual of DNF, expressing formulas as a "product of sums".
**Structure**: A conjunction (AND) of disjunctions (OR) of literals
- **Clause**: A disjunction of literals (P ∨ ¬Q ∨ R)
- **CNF**: A conjunction of clauses

**Example**: $(P ∨ ¬Q) ∧ (¬P ∨ R) ∧ (Q ∨ ¬R)$ is in CNF.

## Clausal and Horn Forms
Both **clausal form** and **Horn form** are special cases of formulas expressed in **Conjunctive Normal Form (CNF)**.
- **Clausal form** is simply a way to represent a CNF formula as a **set of clauses**, where each clause is a disjunction (OR) of literals.
	- Clausal form is a way of writing logical formulas as a **set of clauses**.
	- A **clause** is a disjunction (OR) of **literals**.
	- A **literal** is either an atomic proposition (like p) or its negation (like ¬p).
		So $\{p \lor \neg p\}$ is a clause.
	- The entire formula in clausal form is considered as a conjunction (AND) of these clauses.
	- So, clausal form = AND (ORs of literals).
- **Horn form** is a special kind of CNF formula where **each clause has at most one positive literal**.
	- Horn form is a **special kind of clausal form**.
	- A **Horn clause** is a clause with **at most one positive literal** (a positive literal means an atomic proposition without negation).
	- Formally, a clause is a Horn clause if it contains zero or one positive literal, and any number (including zero) of negative literals.

So, clausal form is the general CNF representation, and Horn form is a restricted, more structured subset of CNF formulas with nice computational properties.

# Ways of Defining Logic:
## Recursive Definition
A **recursive definition** is a way to define something (like a set or a function) in terms of itself, but always with a clear base case to stop the recursion. It's like defining a family tree: you start with the root (base case), and then define each branch in terms of smaller branches (recursive step).

### What is a Recursive Definition?
A **recursive definition** specifies a set or structure by giving:
- **Base case(s):** Simple starting points, usually atomic elements.
- **Recursive rules:** Ways to build complex elements from simpler ones already defined.
	This is usually written as a if condition, like if something, then something to be done.

It's like defining a family tree: list the founding ancestor (base case), then state how new members are produced (recursive step).

### Example: Define recursively the set of all subformulas of A which have negation as the principal operator
A **subformula** is any part of a formula that is itself a well-formed formula. Here, we want the set of all subformulas where the main (outermost) operator is negation (¬).

**Recursive definition:**
$$
\text{NegSub}(A) = 
\begin{cases}
    \varnothing & \text{if } A \in IP \quad (\text{atomic proposition}) \\
    \{A\} \cup \text{NegSub}(B) & \text{if } A = \neg B \\
    \text{NegSub}(B) \cup \text{NegSub}(C) & \text{if } A = B \ \text{ op } \ C,\ \text{op} \in \{\land, \lor, \rightarrow, \leftrightarrow\}
\end{cases}
$$
- **IP** stands for the set of all atomic propositions (like $p,q,r$).
- **Principal operator** is the main logical operator at the outermost level of a formula.


Let's say you want to find all subformulas of a formula $A$ where the main operator is negation $(\neg)$.

- If $A$ is just an atomic proposition (like $p$), there are no negations, so the answer is empty.
- If $A = \neg B$, then $A$ itself is a subformula with negation as the main operator, and you also want to find any such subformulas inside $B$.
- If $A = B \ op \ C$ (where op is AND, OR, etc.), then the main operator is **not** negation, so any subformula with negation as the main operator must be inside either $B$ or $C$. That's why we look at both $NegSub(B)$ and $NegSub(C)$ and take their union.

Example
Suppose $A = (p \lor \neg q)$:
- Here, $B = p$, $C = \neg q$, and op is OR ($\lor$).
- The main operator is not negation, so we look for negations inside $B$ and $C$.
- $NegSub(B) = NegSub(p) = \emptyset$ (since $p$ is atomic)
- $NegSub(C) = NegSub(\neg q) = \{\neg q\} \cup NegSub(q) = \{\neg q\}$
- So, $NegSub(A) = \emptyset \cup \{\neg q\} = \{\neg q\}$

### Example: Recursive Definition of Well-Formed Formulas (WFFs)
A WFF in propositional logic is any formula that properly follows the grammar of the logical language. These can be built _recursively_ (applying same rule again and again to reach a certain conclusion) from atomic propositions using logical connectives.

#### Let’s See How a Recursive definition is built:
#### 1. **Base Case**
- Every atomic proposition in *IP* (like $q$,$r$) **is a WFF**.
    - Example: $q$ is a WFF.
    
#### 2. **Recursive Construction Rules**
If $A$ and $B$ are WFFs, then the following are all also WFFs:
- $(¬A)$  (**NOT** a WFF unless constructed as "negation of a WFF" and parenthesized)
- $(A∧B)$ (**AND**)
- $(A∨B)$ (**OR**)
- $(A→B)$ (**IMPLIES**)
- $(A↔B)$ (**IFF**)

**Example:**
Let *IP*={$q$,$r$}:
- $q$ is in *IF* (WFF) [Base case]
- $(¬q)$ is in *IF* (because $q$ is a WFF, so its negation—still properly parenthesized—is a WFF)
- $(q→r)$ is in *IF* (both $q$ and $r$ are WFFs, so their implication is too)
- $(¬q)$ is in *IF*, but $(¬q)$ alone at a higher level cannot be used in further recursion unless the grammar specifically allows nested parens (which in most strict grammars, it does not unless needed for structure).

---
 **Non-Examples**
- “$pq$”: Not a WFF (doesn't follow the recursive rules—missing a connective)
- “$(q)$”: Not a WFF if your system doesn’t allow parentheses for single atoms (as in your image).
- “$q¬$”: Not a WFF (no structure or proper position for negation).

---
### Why Use Recursive Definitions?
- To precisely describe all possible valid formulas.    
- Computers and proof-checkers use these rules to _parse_ and _check_ logic for errors.


### and now, in order to get what we want, all an individual has to do is keep repeating the given set of rules, hence "recursion"


## BNF (Backus-Naur Form)
### What is BNF?
- A precise way to describe the **syntax** (rules for valid expressions) of formal languages (like logic formulas or programming languages).
- Specifies how complex expressions are constructed from simple symbols, unambiguously.

---
### Key Elements
- **Non-terminals:**
    - Placeholders for types or categories (written in angle brackets, e.g., `<wff>`, `<atom>`).
    - Can be recursively defined by the rules.
    
- **Terminals:**
    - Basic symbols that appear in the final strings (e.g., `q`, `r`, `¬`, `∧`, `→`, `(`, `)`).
    
- **Production rules:** (Implication Form)    
	- Each rule uses `::=` to define how a non-terminal can be replaced or expanded.
    - Right side may contain terminals, non-terminals, or both.
    - Alternatives separated by `|` (means “or”).
    
- **Start symbol:**
    - The primary non-terminal where derivation begins (often `<wff>`).

---
### Example: BNF for Propositional Logic
```text
<wff> ::= 
	<atom>        | 
	(¬ <wff>)       | 
	(<wff> ∧ <wff>)       | 
	(<wff> ∨ <wff>)       | 
	(<wff> → <wff>) 
	
<atom> ::= q | r
```
---
### How to Read and Use BNF
- Start with the **start symbol**.
- Apply rules step by step, replacing non-terminals with right-side options, until only terminals remain.
- Each string formed in this way is a valid formula according to the syntax.

---
### What Does `::=` Mean?
- Pronounced “is defined as” or “can be replaced by.”
- Specifies the allowed forms for the symbol on the left.

---
### BNF Features
- **Unambiguous:** Clearly establishes which expressions are valid.
- **Recursive:** Allows infinite complexity through self-reference.
- **Standard:** Used for specifying syntax in logic, programming, data formats, etc.

---
### Why Use BNF?
- Defines rules for “well-formed” formulas or statements.
- Enables humans and machines (e.g., parsers, compilers) to verify or generate valid expressions.
- Ensures clarity and avoids misinterpretation.


# Definability (for Operators)
**Definability** is when you can express a binary operator using only a given set of other operators.

## Simple Definition
Given a set of operators $O = \{op₁, op₂, ..., opₙ\}$, a binary operator **op** is **definable from O** if:
<blockquote>
For every A, B, there exists C such that C = (A op B) and C only uses A, B and any operators from O (may be more than one).
</blockquote>

## Examples
### AND (∧) is definable from {OR, NOT}
Using **De Morgan's Law**: $A ∧ B = ¬(¬A ∨ ¬B)$

### OR (∨) is definable from {AND, NOT}
Using **De Morgan's Law**: $A ∨ B = ¬(¬A ∧ ¬B)$

### IMPLICATION (→) is definable from {OR, NOT}
$A → B = ((¬A) ∨ B)$

### XOR (⊕) is definable from {AND, OR, NOT}
$A ⊕ B = ((A ∨ B) ∧ (¬(A ∧ B)))$

### NAND (↑) alone can define everything
- NOT: $¬A = A ↑ A$
- AND: $A ∧ B = ¬(A ↑ B) = (A ↑ B) ↑ (A ↑ B)$
- OR: $A ∨ B = (A ↑ A) ↑ (B ↑ B)$

## Key Point
**Functional completeness**: A set of operators is functionally complete if every possible Boolean function can be defined using only those operators. $\{∧, ∨, ¬\}$, $\{→, ¬\}$, and $\{↑\}$ are all functionally complete sets.
**NAND (↑)** and **NOR (↓)** are individually functionally complete - you can define any logical operator using just NAND or just NOR.

---
# Proofs: Structure & Strategies
Understanding proofs is core to both mathematical logic and computer science logic.

## 1. Structure of a Proof
A logical proof is a sequence of steps that starts from assumptions (axioms, premises, or known truths) and systematically deduces a conclusion, using valid rules of inference. The typical structure involves:

- **Stating the Claim:** Precisely articulate what you want to prove (e.g., “For every triangle, the sum of angles is 180°”).
- **Setting Up:**
    - Introduce arbitrary or specific elements (e.g., “Let $\triangle{ABC}$ be any triangle”).
    - Define variables, objects, or terms used.
- **Stepwise Reasoning:**
    - Use established facts, definitions, and logical deductions.
    - Make clear transitions between steps.
- **Arriving at the Conclusion:**
    - Restate the proven result at the end, sometimes in the original formal language.

This structure ensures clarity, rigor, and repeatability.

---
## 2. Major Proof Strategies
### a) Direct Method
- **Approach:** Assume the premise is true and logically deduce the required conclusion.
- **Example:** To prove “If $a$ and $b$ are odd, then $ab$ is odd,” assume $a$ and $b$ are odd and show through arithmetic that $ab$ is also odd.

### b) Proof by Contradiction
- **Approach:** Assume both the premise and the negation of the conclusion. Deduce a contradiction, showing that the assumption must be false.
- **Example:** To show $\sqrt{2}$ is irrational, assume the contrary (that it's rational), deduce a contradiction (by expressing it as a reduced fraction and demonstrating this is impossible).

### c) Contrapositive Method
- **Approach:** Prove the contrapositive of the statement: “If not Q then not P” rather than “If P then Q.” When the contrapositive is easier to prove, this is preferred.
- **Example:** To prove “If $n$ is even, then $n^2$ is even,” instead prove “If $n^2$ is odd, then $n$ is odd.”

### d) Proof by Cases
- **Approach:** Split the problem into exhaustive cases, and prove the statement holds in each case.
- **Example:** To prove $4n+1$ is odd for all $n$, consider separately the cases where $n$ is odd and where $n$ is even.

### e) [[Mathematical Induction]]
- **Approach:** Prove a base case (usually for the smallest value, e.g., $n=1$), then assume true for an arbitrary case $(n=k)$, and prove true for $(n=k+1)$.
- **Application:** Commonly used for statements involving natural numbers or recursively defined structures.
---
## 3. Best Practices for Proof Strategy Selection
- Choose direct proofs for simple, straightforward implications.
- Use contradiction or contrapositive when the direct approach is difficult or indirect.
- Use cases when the statement can be categorized.
- Use induction for claims involving sequences, natural numbers, or recursively defined objects.

---
# Propositional Logic Semantics
**Propositional logic semantics** defines the rules for _determining the truth value_ of any logical formula, given the assignments of truth values to its atomic variables.

## Some definitions to keep in mind:
As we go ahead and learn propositional logic semantics, we would encounter some common terms. Would be great to get familiarized with them:

### 1. Atomic Propositions and Assignments
- **Atomic Proposition:** A statement variable, e.g., p,q,r
- Each atomic variable can be assigned either **True (T/1)** or **False (F/0)**.
- An **assignment** (or “**valuation**”) specifies a truth value for every atomic proposition in a formula.    

---
### 2. Compound Formulas and Operators
Compound formulas are built from atomic propositions using:
- **NOT ($\neg$)** 
- **AND ($\wedge$)** 
- **OR ($\vee$)** 
- **IMPLIES ($\rightarrow$)** 
- **IFF (if-and-only-if, $\leftrightarrow$)** 

---
### 3. Semantic Rules (Evaluation Table)
How do you calculate the truth of a compound formula?
- **NOT**: $\neg A$ is True if A is False; False if A is True.
- **AND**: $A \wedge B$ is True only if both A and B are True.
- **OR**: $A \vee B$ is True if at least one of A or B is True.
- **IMPLIES**: $A \rightarrow B$ is False only if A is True and B is False; otherwise True.
- **IFF**: $A \leftrightarrow B$ is True if both A and B have the same truth value.

**Example Table:**

| A   | B   | A∧BA \wedge BA∧B | A∨BA \vee BA∨B | ¬A\neg A¬A | A→BA \rightarrow BA→B | A↔BA \leftrightarrow BA↔B |
| --- | --- | ---------------- | -------------- | ---------- | --------------------- | ------------------------- |
| T   | T   | T                | T              | F          | T                     | T                         |
| T   | F   | F                | T              | F          | F                     | F                         |
| F   | T   | F                | T              | T          | T                     | F                         |
| F   | F   | F                | F              | T          | T                     | T                         |

---
### 4. Valuation Function
A **valuation function** maps atomic variables to truth values:
- $v(p)=T$, $v(q)=F$, etc.
- For compound formulas, apply semantic rules _recursively_ based on the valuation of atomic components.

---
## Step-by-Step Evaluation
**How do you evaluate a formula?**
1. Assign truth values to all atomic propositions.
2. Build up the truth value of the formula using its structure (parentheses or syntax tree):
    - Start with atomic values
    - Apply operators recursively from bottom up

### Example
Given $p=T$, $q=F$:
- $p \vee q$ : $T \vee F = T$
- $\neg p$: $\neg T = F$
- $(p \wedge q) \rightarrow p$:  
    Calculate $p \wedge q = T \wedge F = F$, so $F \rightarrow T = T$ 

---
## 6. Truth Table Construction
A **truth table** lists the value of a formula for every possible assignment of atomic variables.

| ppp | qqq | p∨qp \vee qp∨q | ¬p\neg p¬p |
| --- | --- | -------------- | ---------- |
| T   | T   | T              | F          |
| T   | F   | T              | F          |
| F   | T   | T              | T          |
| F   | F   | F              | T          |
## Length of a Proposition
- Length is defined as the total number of symbols:
    - E.g., length of $(p∨q)$ = 5. Parentheses **do count** toward the length of a wff
        
- Recursive definition for length:
    - Atomic proposition: length=1.    
    - Compound: sum the lengths of parts plus for symbols.
Examples:
- if $A \in IP$ , length $(A)$ = 1
- if A = $\neg B$, length ($A$) = 1 + length($B$)
- if A = $(B \lor C)$, length ($A$) = 3 + length($B$) + length($C$)
- if A = $(B \land C)$, length ($A$) = 3 + length($B$) + length($C$)
- if A = $(B \implies C)$, length ($A$) = 3 + length($B$) + length($C$)
- if A = $(B \iff C)$, length ($A$) = 3 + length($B$) + length($C$)

---
# Derivation Sequences in Logic
A **derivative sequence** is a step-by-step process that shows how to build a well-formed formula (WFF) from atomic propositions, using the formation rules (usually defined by a BNF or recursive grammar).

---
## What Does a Derivation Sequence Look Like?
- Start from atomic symbols (e.g., $p$,$q$)
- At each step, apply one of the allowed logical operations (negation, AND, OR, IMPLIES, etc.) per the formation rules.
- Show how complex formulas are assembled from simpler subformulas.

---
### Example: Allowed Operations and Rules
Suppose the rules are:
1. **Atomic propositions:** $p$, $q$, $r$, etc. are WFFs.
2. **Negation:** If $A$ is a WFF, then $(\neg A)$ is a WFF.
3. **Binary connectives:** If $A$, $B$ are WFFs,
    - $(A \lor B)$, $(A \land B)$, $(A \rightarrow B)$, $(A \leftrightarrow B)$ are WFFs.

---

### **Derivation Example 1:** Build $((\neg p) \lor q)$

#### **Step-by-step Derivation**
1. $p$ is atomic, so $p$ is a WFF
2. $(¬p)$ by negation rule on $p$
3. $q$ is atomic, so $q$ is a WFF
4. $((¬p)∨q)$ by binary rule on $(\neg p)$ and $q$

So, $((\neg p) \lor q)$ is a valid WFF, constructed by this derivation sequence.

---
### Key Points for Derivation Sequences
- **Every step applies ONE formation rule.**
- **Intermediate formulas must all be WFFs** (by previous application of rules).
- This shows, with certainty, that a given sequence of symbols truly forms a WFF.

---
### **Non-Example**
Sequence: $(pq\lor r)$:

There is no rule that allows you to concatenate two atomic formulas $(pq)$ without a connective.  
Therefore, this is **not** a WFF.

# Ways of Writing a Logic Formula
## Binary Tree and Formation Tree
### What is a Formation Tree?
A **formation tree** (also called a **parse tree**) is a visual, hierarchical representation of how a complex propositional logic formula is constructed from atomic propositions and logical connectives, step by step, according to the grammar rules.
- **Root node:** The principle operator
- **Internal nodes:** Logical operators (e.g., $¬$,$∧$,$∨$,$→$)
- **Leaf nodes:** Atomic propositions (e.g., $p$,$q$,$r$)

**Example:**
For $((\neg p)\lor q)$:
```text
         ( \lor )
         /     \
    ( \neg )    q
       |
       p
```
- The root is the main operator (here, $∨$).
- The left child is $(¬p)$, showing negation applied to $p$.
- The right child is $q$.

### What is Binary Tree?
A **binary tree** is a form of tree where each node has at most two children. Logical formation trees for propositional formulas are **binary** because most logical operators are either unary ($¬$, with just one child) or binary ($∧$,$∨$,$→$ etc., with two children).

**Example:**
For $(p \implies (q \land r))$:
```text
         ( → )
         /    \
       p    ( ∧ )
             /   \
           q      r
```
- Root: ($→$)
- Left child: $p$
- Right child: ($∧$), which itself has left child $q$ and right child $r$.

## Polish and Reverse Polish Notation
These are special ways to write logical (or mathematical) expressions **without using parentheses** to show the order of operations.  
They are called “prefix” and “postfix” notations, and are very useful in computer science and logic.

---
### Polish Notation (Prefix Notation)
- **Operators come before their operands.**
- No need for parentheses—order is clear from the structure.

#### Examples
For the formula: $(p∨(¬q))$
- **Standard (Infix) Notation:** $(p∨(¬q))$
- **Polish Notation:**
    - Write the main operator $(∨)$ first: $∨$
    - Then left operand $(p)$, then right operand $(¬q)$: $p (¬q)$
    - For negation: $¬$ before $q$
    - **Final Polish:** $∨ p ¬q$

#### Other Example:
$(p→(q∧r))$
- Polish: $→ p ∧ q r$

---
### Reverse Polish Notation (Postfix Notation)
- **Operators come after their operands.**
- Again, no need for parentheses.

#### Examples
For the formula: $(p∨(¬q))$
- Write left operand $(p)$
- Write right operand $(¬q)$: first $q$, then $¬$ after it
- Write the operator $∨$ after both
- **Final RPN:** $p q ¬ ∨$

#### Other Example:
$(p→(q∧r))$
- RPN: $p q r ∧ →$

## What is a Proper Prefix?
A **proper prefix** of a string (or formula) is any initial segment of the string that is **not equal to the whole string itself**.
- In other words, if you take the first $k$ symbols of a string of length $n$, where $0 \leq k < n$, you get a proper prefix.
- The empty string (no symbols) is also considered a proper prefix.

### Example
Suppose the string is $A = (p \land q)$ (which has 7 symbols: (, p, space, \land, space, q, )).
The proper prefixes of $A$ are:

- "" (empty string)
- "("    
- "(p"
- "(p "
- "(p \land"
- "(p \land "
- "(p \land q"

But **not** "(p \land q)" itself (that's the whole string, so it's not a proper prefix).

---
# Algorithmic Problems
Now given a formula or a logic, we face a few problems that we need to check the satisfiability of, hence lets have a look at these problems. Before we jump into this, lets discuss:
## What is P vs NP?
**P** and **NP** are important classes of problems in computer science, especially when studying algorithms and logic. Here’s a clear, beginner-friendly explanation:

### What is P?
- **P** stands for **"Polynomial Time."**
- Problems in P can be **solved quickly** (efficiently) by a computer—meaning there’s a known algorithm that can always find the answer in a reasonable amount of time, even as the problem gets bigger.
- The solving time grows only as a polynomial function of the input size (for example, it could take $n^2$ or $n^3$ steps for an input of size $n$, not something super-fast like $n$, but not super-slow like $2^n$.
- **Examples:** Sorting a list, finding the shortest route on a map, multiplying numbers, detecting if a number is even.

### What is NP?
- **NP** stands for **"Nondeterministic Polynomial Time."**
- For NP problems, **checking** a proposed solution is quick—but **finding** the solution might be very hard or slow.
- If someone gives a possible answer ("a guess"), a computer can check if it’s correct in polynomial time.
- **Examples:** Solving a really tricky Sudoku, finding perfect assignments in puzzles, "Boolean satisfiability problem" (can you choose True/False for each variable to make a formula come out true?).

### How Are P and NP Related?
- **All P problems are NP problems** (if you can solve something fast, you can surely check the answer fast!) 
- The big question: **Is every NP problem also in P?** That’s the famous “P vs NP” problem that is still unsolved today.

## The Algorithmic Problems:
Quick Info:
The set $Mod(A)$ refers to the set of all **valuations** (or interpretations) that satisfy the well-formed formula $A$. Hence: 
$$\text{Mod}(A) = \{v \; |\quad v \models A\}$$

### Satisfiability Problem (SAT)
The **satisfiability problem** asks: "*Given a logical formula, does there exist an assignment of truth values to variables that makes the formula true?*"
**Definition**: A formula *A* is **satisfiable** if there exists a valuation $V$ such that $V ⊨ A$ (V makes A true).
**Example**:
- "$P ∧ ¬Q$" is satisfiable (set P=true, Q=false)
- "$P ∧ ¬P$" is unsatisfiable (contradiction)

The SAT problem is **NP-complete**, meaning it's one of the hardest problems in the NP complexity class.
Now in order to check satisfiability, we move step by step checking conditions for the satisfiability.

### Validity Problem
A formula A is **valid** if it's true under all possible truth assignments. **Validity** means the formula is a tautology - it cannot be false.
**Key relationship**: A formula A is valid if and only if ¬A (its negation) is unsatisfiable.

### Model Checking Problem
**Model checking** verifies whether a given model (like a computer system) satisfies a specified property.
**Definition**: Given a valuation V and formula A, check whether V ⊨ A (whether V satisfies A).

### Equivalence Problem
Two formulas A and B are **logically equivalent** $(A ≡ B)$ if they have the same truth value under all possible valuations. This means $Mod(A) = Mod(B)$, where $Mod(X)$ represents all models that satisfy $X$.

---
### Conjunction and Disjunction
**Disjunction** and **conjunction** are two fundamental logical operators that allow us to combine simple statements (propositions) into more complex ones.

- *Conjunction (AND)*
	**Conjunction** is a logical operation that connects two statements with "AND".
- *Disjunction (OR)*
	**Disjunction** is a logical operation that connects two statements with "OR".

---
# How to check if the given logic can be satisfied?
## Satisfaction Relations: ($\models$)
A satisfaction relation in logic (also called the "model relation") is a formal way to connect a logical formula to a truth assignment or structure and determine if the formula is true under that assignment/valuation.
In propositional logic, this means checking if, given a specific assignment of truth values to atomic propositions, the formula evaluates to true.

For example:
- If you have a formula $(A→B)$, and under a particular assignment, $A$ is false while $B$ is true, the satisfaction relation holds: that assignment "satisfies" the formula.
- Satisfaction is denoted as $\text{M} \models \varphi$, meaning the model $\text{M}$ (the assignment of values or interpretation) makes the formula $φ$ true.

This concept is central to semantics (the meaning) in logic, as opposed to syntax (the structure or rules for forming formulas).
Satisfaction relations are used to describe when formulas are true in models, helping determine the logical validity and entailment in reasoning systems.

## Valid, satisfiable and Unsatisfiable formulas
Here is an explanation and notes on valid and unsatisfiable formulas in logic based on logic concepts from your lecture materials and standard definitions:

### Valid Formulas (Tautologies)
- A **valid formula** in logic is a formula that is **true under every possible interpretation or valuation** of its atomic propositions.
- It means no matter what truth values are assigned to the atomic parts of the formula, the formula will always evaluate to true.
- Valid formulas are also called **tautologies**.
- Formally, a formula A is valid if for every valuation $v$, $v(A) = \text{True}$.
- Example: 
    - $A \lor \neg A$ (Law of the excluded middle) is valid since it is true regardless of the truth value of $A$.
- Valid formulas represent logical truths and form the basis for logical reasoning and inference.

### Unsatisfiable Formulas (Contradictions)
- An **unsatisfiable formula** is a formula that is **false under every possible interpretation or valuation**.
- It means there is no assignment of truth values to atomic propositions that makes the formula true.
- Unsatisfiable formulas are also known as **contradictions**.
- Formally, a formula $A$ is unsatisfiable if for every valuation $v$, $v(A) = \text{False}$.
- Example:
    - $A \land \neg A$ is unsatisfiable since $A$ cannot be both true and false simultaneously.
- Unsatisfiable formulas represent logical impossibilities.

### Satisfiable Formulas
- A formula is **satisfiable** if there is at least one interpretation or valuation that makes the formula true.
- Not every formula is valid or unsatisfiable. Some formulas lie in between, true in some interpretations and false in others.
- Valid formulas are always satisfiable, but the converse is not true (satisfiable does not imply valid).

### Relationship Summary

| Formula Type      | Truth Value Under All Valuations | Example                         |
| ----------------- | -------------------------------- | ------------------------------- |
| Valid (Tautology) | Always True                      | $A \lor \neg A$                 |
| Unsatisfiable     | Always False (Contradiction)     | $A \land \neg A$                |
| Satisfiable       | True for at least one valuation  | $A \land B$ (true if both true) |
### Some Observations:
- If $A$ is valid, then $A$ is satisfiable, but if $A$ is satisfiable, then it may or may not be valid.
- If $A$ is valid, then $\neg A$ is unsatisfiable, and if $A$ is unsatisfiable, then $\neg A$ is valid.
- ![[Pasted image 20250901030115.png]]

### Notes for Learning
- Evaluating validity and unsatisfiability can be done using **truth tables** or **semantics**.
- Validity is fundamental in proving logical arguments correct.
- Contradiction detection is important in logical consistency and automated theorem proving.
- In _propositional logic_, formulas are built from atomic propositions using logical connectives like AND ($\land$), OR ($\lor$), NOT ($\neg$), IMPLIES ($\to$).
- Validity checks all valuations; unsatisfiability finds none.    
- Logical implication and equivalence use concepts of validity and satisfiability for proofs.

## Satisfiability of DNF (DNF-SAT)
For a formula A in **Disjunctive Normal Form**
$$A=C_1  ∨  C_2  ∨  …  ∨  C_m$$

where each conjunction $C_i$ is a set of literals, the algorithm checks for a **non‐contradictory cube**:
```text
DNF-SAT(A):   
	for i = 1 to m:     
		if Ci contains no complementary literals (e.g. both P and ¬P):       
			return “satisfiable”   
		return “unsatisfiable”`
```
- A **complementary pair** means a literal and its negation appear in the same conjunction (making that conjunction false under any assignment).
- **Time complexity:** $O(m k^2)$, where $k$ is the maximum number of literals in any conjunction.

## Conversion to DNF:
Not all the logics / wffs are in DNF normal form, hence sometimes we might need to convert the logical formulas into one.

### DNF Conversion Algorithm (DNF Convert)
#### Step 1: **Eliminate Implications and Biconditionals**
Replace all implications ($\rightarrow$) and biconditionals ($\leftrightarrow$) with AND, OR, and NOT:
- **Implication:** $A→B≡¬A∨B$
- **Biconditional:** $A↔B≡(A→B)∧(B→A)≡(¬A∨B)∧(¬B∨A)$

#### Step 2: **Push Negations Inward (De Morgan's Laws)**
Move all negation symbols ($¬$) to the literals using De Morgan's rules:
- $¬(A∧B)≡¬A∨¬B$
- $¬(A∨B)≡¬A∧¬B$
- $¬(¬A)≡A$ (double negation elimination)

#### Step 3: **Distribute AND over OR**
Use the **distributive law** to convert to DNF:
- $A∧(B∨C)≡(A∧B)∨(A∧C)$
- $(A∨B)∧(C∨D)≡(A∧C)∨(A∧D)∨(B∧C)∨(B∧D)$

#### Step 4: **Simplify**
Remove redundant terms and contradictions:
- $A∨A≡A$ (idempotence)
- $A∧¬A≡False$ (contradiction)
- Remove any terms containing contradictions

### Converting CNF to DNF
Converting from **Conjunctive Normal Form (CNF)** to **Disjunctive Normal Form (DNF)** involves using the **distributive law**.

#### What Are We Converting?
- **CNF**: A conjunction (AND) of clauses, where each clause is a disjunction (OR) of literals
    - Example: `(A ∨ B) ∧ (C ∨ D)`
TO:
- **DNF**: A disjunction (OR) of terms, where each term is a conjunction (AND) of literals
    - Example: `(A ∧ C) ∨ (A ∧ D) ∨ (B ∧ C) ∨ (B ∧ D)`

#### The Key Tool: Distributive Law
The **distributive law** lets us "multiply out" logical expressions, similar to algebra:
**For Logic:**
- `A ∧ (B ∨ C) = (A ∧ B) ∨ (A ∧ C)`
- `(A ∨ B) ∧ (C ∨ D) = (A ∧ C) ∨ (A ∧ D) ∨ (B ∧ C) ∨ (B ∧ D)`

**Think of it like algebra:**
- `a × (b + c) = (a × b) + (a × c)`
- `(a + b) × (c + d) = ac + ad + bc + bd`

#### Step 1: Prepare the Formula
1. **Eliminate implications** (→) and biconditionals (↔)
    - `A → B` becomes `¬A ∨ B`
    - `A ↔ B` becomes `(A ∧ B) ∨ (¬A ∧ ¬B)`
2. **Push negations inward** using De Morgan's laws    
    - `¬(A ∧ B)` becomes `¬A ∨ ¬B`
    - `¬(A ∨ B)` becomes `¬A ∧ ¬B`

#### Step 2: Apply Distributive Law
Distribute AND over OR to get all terms in DNF form.

#### Key Points to Remember
1. **Each clause multiplies with every other clause**
2. **Remove contradictory terms** (like `A ∧ ¬A`)
3. **The number of terms can grow exponentially**
4. **Order doesn't matter** - you can distribute in any order 

#### Practice Steps
For any CNF formula:
1. **Identify all clauses** (terms connected by ∧)
2. **Take the first two clauses** and distribute
3. **Continue with the result and the next clause**
4. **Repeat until all clauses are distributed**
5. **Simplify by removing contradictions** 

#### Why This Can Get Large
With **m clauses** each having **k literals**, you can get up to **$k^m$ terms** in the DNF. This is why CNF-to-DNF conversion can become computationally expensive for large formulas.


## Validity and satisfiability algorithm for CNF:
- **Input:** Formula $A$ in CNF.
- **Step 1:** Take the **negation** of $A$ (written as $¬A$).
- **Step 2:** Convert $¬A$ to **DNF** (Disjunctive Normal Form) using **DeMorgan's rules** (these rules are used to systematically push negations inside logical formulas so conversion is possible/easy).
- **Step 3:** Apply an algorithm (labelled "Algo DNF-SAT") that checks **satisfiability for DNF formulas**.

### Decision Logic (Flowchart)
- If the DNF-SAT algorithm finds that $¬A$ **is satisfiable** (outputs "Yes"), it means $A$ is **not valid** (it is falsifiable in some assignment), but **$A$ is satisfiable**
- If DNF-SAT finds that $¬A$ **is not satisfiable** (outputs "No"), it means **$A$ is valid** (true for all possible assignments).
- If DNF-SAT finds that $\neg A$ **is valid** (outputs yes in all the possible valuation), it means that **$A$ is unsatisfiable**.  

This is because **a formula is valid if its negation is unsatisfiable**.

## HORN Satisfiability
So, as you know, Horn form is a special case of clausal form where we have atmost one positive literal. Now, as we have only one positive literal in a Horn Clause, we call that positive literal a "Head", and the other literals form the tail of the clause.

Depending upon the existence of head or tail, we have three types of Horn Clauses:
- **Definite Clause**: Contains both, the head and tail
- **Unit Clauses**: Contains only the head (Example: $p \equiv true \rightarrow p$)
- **Goal Clause**: Contains only the tail

Now as we have only one positive literal in the horn clause, we can write a Horn Formula in implication form, i.e., were we have a conjunction of implications.
For example:
$$
\begin{gather}
q ∧ s ∧ ¬w ∧ (¬p ∨ ¬q ∨ ¬s ∨ v) ∧ (¬v ∨ s) ∧ r ∧ (¬r ∨ p) \\
\equiv \\ 
(true → q) ∧ (true → s) ∧ (w → false) ∧ (p ∧ q ∧ s → v) ∧ (v → s) ∧ (true → r) ∧ (r → p)
\end{gather}
$$

Now to check the satisfiability of an Horn Formula, we do the following:
- First, for a clause, set a tail literal to be *false*, unless it is being forced by any other clause to be *true* for that clause to be true.
- Repeat the step one for all the literals in the tail of the clause.
- Now evaluate the result of the tail in the implication:
	- If the tail gives out *true*, then this forces the head to be *true*
	- if the tail gives out *false*, then this doesn't force any value on the head
- Now depending upon the result of the previous step, check for the contradictions in the value of the head, i.e., if it has already been forced a value that contradicts the given situation or not.

*Key Clarification*
- **You never set a positive literal (head) to false just because it appears as a head elsewhere.**
- **Variables are only set to true when forced by the propagation process.**
- **Once a variable is set to true, it stays true.**
- **Negative literals (body) are assumed false unless forced true.**


### Horn Formula Example:
- $true \to p$
- $p \to q$
- $q \land r \to false$
- $true \to r$
#### Step 1: Initialize
- Set all variables to **false**: $p = 0, q = 0, r = 0$
#### Step 2: Forced Assignments
- $true \to p$: Set $p = 1$
- $true \to r$: Set $r = 1$
#### Step 3: Propagate
- $p \to q$: Since $p = 1$, set $q = 1$
#### Step 4: Check for Contradiction
- $q \land r \to false$: Both $q = 1$ and $r = 1$, so this forces $false$ to be true—a contradiction!
#### Step 5: Conclusion
- **Contradiction found**: The formula is **unsatisfiable**.

![[Pasted image 20250909195134.png]]

# What is a Satisfiable Set of Formulas?
A **set of formulas** is **satisfiable** if there is **at least one valuation v** (an assignment of truth values `True` or `False` to all atomic propositions) such that **all formulas in the set of formulas are true** under v.
- **Valuation v:** Think of it like a setting for all variables in formulas, e.g., p=True, q=False.
- If we can find such v so that every formula in the set of formulas evaluates to true, then the set of formulas is satisfiable.

## Example of Satisfiable Set
Consider $I=\{p∨q,¬p\}$.
- Try valuation: v(p)=False,v(q)=True.
- Then $p∨q=False∨True=True$.
- $¬p=¬False=True$.
- Both formulas are true, so $I$ is satisfiable under v.

---
## 2. What is a Model of a Set of Formulas?
- A valuation v that **satisfies all formulas** in a set $I$ (i.e. makes them all true) is called a **model of $I$**.
- Denoted as $v∈Mod(I)$.
- The **set of all models** of I is written as:
    $$Mod(I)=\{v∣∀A∈I,v⊨A\}$$
- Similarly, $Mod(A)$ is the set of all valuations that satisfy the formula $A$.

## 3. Satisfiability and Models Relation
- **Satisfiable:** There exists **at least one** model $v$ for $I$, i.e., $Mod(I)≠∅$.

## 4. Logical Consequence (Entailment)
- Formula A is a **logical consequence** of a set $U$ of formulas, written as:
    $U⊨A$
    if **every valuation that satisfies all formulas in U also satisfies A**.
    
- In other words,
    $Mod(U)⊆Mod(A)$
- This means if all formulas in U are true, A must be true as well.

## 5. Important Properties
- If a set $U$ logically entails $A$ (i.e., $U⊨A$):
    - Then any model of $U$ is also a model of $A$.
    - $U$ can be thought of as "implying" or "supporting" $A$.

## Examples
- $U={p∧q}$, $A=p$
    If $p$ and $q$ are both true, then clearly $p$ is true.
    So, $U⊨p$.
    
- $U={p}$, $A=p∨q$
    If $p$ is true, then $p∨q$ is true.
    Hence, $U⊨p∨q$.

# Tseitin Procedure
The **Tseitin Procedure** converts any formula A to a CNF formula B such that A and B are equisatisfiable.

**Steps**:
1. Create formation tree for formula A
2. Introduce new atomic propositions for each internal node
3. Create equivalences for each node
4. Convert equivalences to CNF
5. Conjoin all resulting CNF formulas

**Example**: For $A = ((p \rightarrow q) \land r)$
1. Formation tree has internal nodes for ∧ and →
2. Introduce p₁ for (p → q), p₂ for the whole formula
3. Equivalences:
    - p₁ ↔ (p → q)
    - p₂ ↔ (p₁ ∧ r)
4. Convert to CNF and conjoin with p₂

# Semantic Tableaux
**Semantic Tableaux** is a systematic method to check if a logical formula is satisfiable (can be made true) or unsatisfiable. It is an algorithm to solve **Satisfiability Problem.**
A **semantic tableau** is a **tree structure** where:
- **Root** contains the formula you want to test
- **Branches** represent different ways to make the formula true
- **Leaves** show final outcomes (open or closed)

## What is the Satisfiability Problem?
The **Satisfiability Problem** is one of the fundamental problems in logic and computer science. It asks: "Given a logical formula, does there exist an assignment of truth values to its variables that makes the formula true?"

**Key Definitions:**
- **Satisfiable**: A formula is satisfiable if there exists at least one valuation (assignment of truth values) that makes it true
- **Unsatisfiable**: A formula is unsatisfiable if no valuation can make it true
- **Valid**: A formula is valid if it is true under all possible valuations

### Algorithm Requirements for Satisfiability
Any algorithm solving the satisfiability problem must satisfy two crucial properties:
1. **Termination**: The algorithm must terminate for all inputs
2. **Correctness**:
    - If A is satisfiable, then the algorithm returns "Yes"
    - If A is unsatisfiable, then the algorithm returns "No"

Whats an algorithm? Lets just say that its your boss giving you step by step commands to perform a task like a robot.

An **algorithm** is a precise, step-by-step set of instructions for solving a problem or completing a task. Think of it as a recipe: just as a recipe tells you exactly what to do to bake a cake, an algorithm tells a computer (or a person) exactly what steps to follow to get a result.

## What are Semantic Tableaux?
**Semantic Tableaux** (also called truth trees) are a systematic method for checking satisfiability of logical formulas. They provide a visual, tree-based approach to determine whether a formula can be satisfied.

### Basic Concepts
**Literal**: An atomic proposition (like p) or its negation (like ¬p)
**Complementary Pair**: If $l$ is a literal, then its complement is:
- If l = p, then complement = ¬p
- If l = ¬p, then complement = p
For example: if l = ¬q, then complement = q

### Formula Classification
Formulas in semantic tableaux are classified into two types:

#### α-formulas (C-formulas - No branching)
These create a single child in the tree:
- $A \land B$ → both A and B must be true
- $\neg(A \lor B)$ → both ¬A and ¬B must be true
- $\neg(A \rightarrow B)$ → both A and ¬B must be true

#### β-formulas (B-formulas - Branching)
These create two children in the tree:
- $A \lor B$ → either A or B must be true
- $\neg(A \land B)$ → either ¬A or ¬B must be true
- $A \rightarrow B$ → either ¬A or B must be true

### Tree Construction Rules
1. **For α-formulas**: Create a single child and add both components
2. **For β-formulas**: Create two children, add one component to each child
3. **Leaf marking**:
    - **Open**: Set of literals without complementary pairs
    - **Closed**: Set of literals with at least one complementary pair

For example:
![[Pasted image 20251009043218.png]]


## The Semantic Tableaux Algorithm
### Algorithm Steps
(v(n) is the label (the set of formulas) attached to node n.)
```
Algorithm: Semantic-Tableaux(A)
1. Create root node with label {A}
2. While (set of unmarked leaf nodes is not empty):
   a. Pick an unmarked leaf node n with formula B in v(n)
   b. If B is an α-formula with components C₁, C₂:
      - Create child n₁ of n
      - Set v(n₁) = (v(n) \ {B}) ∪ {C₁, C₂}
      - If v(n₁) has complementary pair → mark "closed"
      - Else → mark "open"
   c. If B is a β-formula with components B₁, B₂:
      - Create two children n₁, n₂ of n  
      - Set v(n₁) = (v(n) \ {B}) ∪ {B₁}
      - Set v(n₂) = (v(n) \ {B}) ∪ {B₂}
      - Mark each child "open" or "closed" based on complementary pairs
```

### Detailed Example: Checking Satisfiability
Let's work through: $(p \rightarrow (q \lor r))$
**Step 1**: Start with root node containing $\{p \rightarrow (q \lor r)\}$
**Step 2**: $p \rightarrow (q \lor r)$ is a β-formula, so create two branches:
- Left branch: $\{\neg p\}$
- Right branch: $\{q \lor r\}$
**Step 3**:
- Left branch $\{\neg p\}$ contains only literals → mark **open**
- Right branch: $q \lor r$ is β-formula, so branch again:
    - $\{q\}$ → **open**
    - $\{r\}$ → **open**

**Result**: All branches are open → formula is **satisfiable**

The satisfying valuations are:
- $V(p) = F$ (any values for q, r)
- $V(p) = T, V(q) = T$ (any value for r)
- $V(p) = T, V(r) = T$ (any value for q)

## Termination
**Theorem**: The Semantic Tableaux algorithm terminates for all propositional formulas.
**Proof Idea**: 
	Use a progress measure. 
	For each node $n$ in tree $T$, define $w(n)$ as the number of non-literal formulas in $v(n)$. 
	Since each application of a rule reduces this number and we have finite formulas, the process must terminate.

## 4. Applications of Semantic Tableaux
### 4.1 Checking Satisfiability
**Single Formula**: Apply the tableaux algorithm directly
**Set of Formulas**: Start with all formulas in the root node

### 4.2 Checking Validity
A formula A is **valid** if it's true under all valuations.
**Method**: Check if $¬A$ is unsatisfiable
- If tableaux for $¬A$ is closed → A is valid
- If tableaux for $¬A$ is open → A is not valid

### 4.3 Logical Consequence
To check if $\Gamma$ ⊨ A ($Γ$ entails A):

**Method**: Check if $Γ ∪ \{¬A\}$ is unsatisfiable
- If $closed → Γ ⊨ A$
- If $open → Γ ⊭ A$

Now, remember that $\{A\} \models B$ if $\{A\}\rightarrow B$ is valid.

**Example**: Does $\{p \rightarrow q, q \rightarrow r\} \models (p \rightarrow r)$?
Check satisfiability of: $\{p \rightarrow q, q \rightarrow r, \neg(p \rightarrow r)\}$
This equals: $\{p \rightarrow q, q \rightarrow r, p, \neg r\}$

Building the tableau will show this set is unsatisfiable, confirming the logical consequence.

### 4.4 Converting to DNF
Open branches in completed tableaux correspond to disjuncts in Disjunctive Normal Form.

### Example: Let’s convert $(p \lor q) \land (\neg p \lor r)$ to DNF using tableaux.
#### Tableau Construction
- **Root:** $\{(p \lor q) \land (\neg p \lor r)\}$
- **Decompose $\land$ (α-formula):**
    - $\{p \lor q, \neg p \lor r\}$
- **Decompose $p \lor q$ (β-formula):**
    - Branch 1: $\{p, \neg p \lor r\}$
    - Branch 2: $\{q, \neg p \lor r\}$

#### Branch 1: $\{p, \neg p \lor r\}$
- Decompose $\neg p \lor r$ (β-formula):
    - Sub-branch 1a: $\{p, \neg p\}$ → **closed** (complementary pair)
    - Sub-branch 1b: $\{p, r\}$ → **open**

#### Branch 2: $\{q, \neg p \lor r\}$
- Decompose $\neg p \lor r$ (β-formula):
    - Sub-branch $2_a$: $\{q, \neg p\}$ → **open**
    - Sub-branch $2_b$: $\{q, r\}$ → **open**

### Collect Open Branches
- $\{p, r\}$ 
- $\{q, \neg p\}$
- $\{q, r\}$

### Write DNF
$$
(p \land r) \lor (q \land \neg p) \lor (q \land r)
$$

## 5. Correctness of Semantic Tableaux
The **correctness** of semantic tableaux refers to two fundamental mathematical properties: **soundness** and **completeness**.
- **Soundness**: If $T$ is closed, then $A$ is unsatisfiable
- **Completeness**: If $A$ is unsatisfiable, then $T$ is closed

Before we move towards correctness or prove this, first lets understand what is Height of a tree structure:

### Height of a tree
The **height of a tree** is a key concept used to measure how “deep” or “tall” a tree-structured object is. In logic, especially in the **semantic tableau method**, a proof can be represented as a **tree**, where the root node contains the formula to be evaluated and each branch represents the logical decomposition of that formula into simpler components.

#### Definition (Recursive Form)
The **height** of a tree $T$ based at a node $n$ is defined recursively as follows:
1. **Base case**:  
    If $n$ is a **leaf node** (i.e., it has no children), then
    $$h(n)=0$$
    because there are no levels beneath it.
    
2. **Recursive step**:  
    If $n$ has **children nodes**, the height of $n$ is **one more than the maximum height of its children**:
    $$h(n)=1+max⁡(h(c1),h(c2),...,h(ck))$$
    where $c_1,c_2,...,c_k$ are the children of $n$.

Thus, the **height of the entire tree** is simply the height of its **root node**.
This recursive definition naturally fits the recursive nature of logical trees and becomes an excellent variable for **inductive proofs**.

### Correctness of Sematic Tableaux
A semantic tableau is a tree-based method for determining whether a formula is satisfiable or valid. It works by:
- Starting with a formula (or set of formulas)
- Systematically breaking down complex formulas into simpler components
- Checking for contradictions

### Soundness
**Theorem (Soundness)**: If a tableau for a set of formulas closes (all branches close), then the set is unsatisfiable.
#### Proof Sketch:
1. **Closure means contradiction**: A branch closes when it contains both a formula φ and its negation ¬φ on the same path
	
2. **Preservation of satisfiability**: Each tableau rule preserves satisfiability:
    - If the parent formulas are satisfiable, then at least one child branch is satisfiable
    - Conversely, if no child branch is satisfiable, the parent isn't either
    
3. **All branches close**: If every branch contains a contradiction, then no interpretation can satisfy all formulas on any path from root to leaf
    
4. **Conclusion**: The original set of formulas is unsatisfiable

#### Example:
To show $¬(P ∧ Q) ⊢ ¬P ∨ ¬Q$, we assume the negation and derive a contradiction:
Actually, to show validity of ¬(P ∧ Q) → (¬P ∨ ¬Q):
```
¬(¬(P ∧ Q) → (¬P ∨ ¬Q))
   |
¬(P ∧ Q)     (antecedent)
¬(¬P ∨ ¬Q)   (negated consequent)
   |
¬¬P          (De Morgan's)
¬¬Q          (De Morgan's)
   |
P
Q
   /\
  P  ¬P  (from ¬(P ∧ Q))
  |   |
  Q  ¬Q
  ×   ×  (both branches close)
```

### Completeness
**Theorem (Completeness)**: If a set of formulas is unsatisfiable, then there exists a closed tableau for it.

#### Proof Sketch (Systematic Tableau Construction):
1. **Systematic procedure**: Apply rules in a systematic way (e.g., breadth-first) to ensure all formulas are eventually decomposed
2. **Finite formulas**: Each formula can only be decomposed finitely many times
3. **Hintikka's Lemma**: An open, fully expanded branch (where all rules have been applied) represents a satisfying interpretation
4. **Contrapositive**: If the set is unsatisfiable, no such interpretation exists, so every fully expanded branch must close
5. **König's Lemma** (for first-order logic): In a systematic tableau construction, if there's an infinite branch, it represents a model (for countable languages)

### Key Components:
**Hintikka Set**: A set of formulas H is a Hintikka set if:
- It's propositionally consistent (no φ and ¬φ both in H)
- If (φ ∧ ψ) ∈ H, then φ ∈ H and ψ ∈ H
- If (φ ∨ ψ) ∈ H, then φ ∈ H or ψ ∈ H
- If ¬¬φ ∈ H, then φ ∈ H

**Result**: Every Hintikka set is satisfiable, and every open, saturated branch forms a Hintikka set.

# Hilbert's Axiom System:
## Overview
**Hilbert's system** is a formal proof system for logic based on:
- A small set of **axioms** (logical truths)
- A small set of **inference rules** (rules for deriving new theorems)

## Notations

| Notation     | English                                       |
| ------------ | --------------------------------------------- |
| **⊢ p**      | "p is provable (with no assumptions)"         |
| **q ⊢ p**    | "p is provable from assumption q"             |
| **q, r ⊢ p** | "p is provable from assumptions q and r"      |
| **Γ ⊢ p**    | "p is provable from the set of assumptions Γ" |

The key: **⊢ is about PROVING things using formal rules**

## The System (Propositional Logic)
### Axioms
Here are the **3 axiom schemas**:

**Axiom 1 (K - Simplification):**
```
⊢ p → (q → p)
```
"If p is true, then q implies p"

**Axiom 2 (S - Distribution):**
```
⊢ (p → (q → r)) → ((p → q) → (p → r))
```
"Distribute implication over implication"

**Axiom 3 (Contrapositive):**
```
⊢ (¬p → ¬q) → (q → p)
```
"From contrapositive, derive the implication"

### Inference Rule
**Modus Ponens (MP):**
```
From p and p → q, infer q
```

Or in notation:
```
  p    p → q
  ───────────
      q
```
### That's It!
Everything else in propositional logic can be **derived** from these axioms and this one rule.

## Key Theorems About Hilbert's System
### 1. **Deduction Theorem**
This is the **most important** theorem for Hilbert systems!

**Theorem (Deduction Theorem):**
```
If Γ,p ⊢ q, then Γ ⊢ p → q
```
Where **Γ** (Gamma) = a set of assumptions/formulas

**Meaning:**
- If you can prove q from Γ and assumption p
- Then you can prove "p implies q" from just Γ

**Why Important:**
- Makes proofs MUCH easier
- Allows us to assume premises temporarily
- Connects to natural deduction style reasoning

**Example:**
```
To prove ⊢ p → p:
1. Assume p               [assumption]
2. p                      [from 1]
3. Therefore, p → p       [by Deduction Theorem]
```

#### Proof of Deduction Theorem (Sketch):
**Given:** Γ, p ⊢ q (there's a proof of q from Γ and p)
**To show:** Γ ⊢ p → q

**Proof by induction** on the length of the proof of q:

**Base cases:**
1. **q is an axiom**: Then q is provable without p    
    - We have ⊢ q
    - By Axiom 1: ⊢ q → (p → q)
    - By MP: ⊢ p → q ✓
2. **q is in Γ**: Same as above
    
3. **q is p itself**: Need to prove ⊢ p → p
    
    - This is provable (see below)! ✓

**Inductive case:** q was derived by MP from earlier formulas k and k → q

- By IH (Inductive Hypothesis): Γ ⊢ p → k and Γ ⊢ p → (k → q)
- By Axiom 2: ⊢ (p → (k → q)) → ((p → k) → (p → q))
- By MP twice: Γ ⊢ p → q ✓

**QED**

### 2. **Soundness Theorem**
**Theorem (Soundness):**
```
If ⊢ p, then ⊨ p
```

**Meaning:** Everything provable in Hilbert's system is logically valid (a tautology).

**Proof Sketch:**
1. Check that all axioms are tautologies (truth table verification)
2. Check that MP preserves validity:
    - If p and p → q are both valid
    - Then q must be valid
3. By induction, everything provable is valid

### 3. **Completeness Theorem** 
**Theorem (Completeness):**
```
If ⊨ p, then ⊢ p
```

**Meaning:** Everything that is logically valid (a tautology) is provable in Hilbert's system.
This is **much harder** to prove! Gödel proved this in 1930.

**Consequence:**
```
⊢ p  if and only if  ⊨ p
```
The syntactic notion (provability) and semantic notion (validity) coincide!

### 4. **Consistency Theorem**
**Theorem (Consistency):**
Hilbert's system is **consistent**: There is no formula p such that both ⊢ p and ⊢ ¬p.
**Proof:** By soundness! If both were provable, both would be valid, which is impossible.

## Example Proofs in Hilbert's System
### Example 1: Prove ⊢ p → p (Identity)
This is surprisingly tricky in Hilbert's system!
```
1.  ⊢ (p → ((p → p) → p)) → ((p → (p → p)) → (p → p))    [Axiom 2: with p=p, q=(p→p), r=p]
2. ⊢ p → ((p → p) → p)                                    [Axiom 1: with p=p, q=(p→p)]
3. ⊢ (p → (p → p)) → (p → p)                             [MP on 1,2]
4. ⊢ p → (p → p)                                          [Axiom 1: with p=p, q=p]
5. ⊢ p → p                                                [MP on 3,4]
```

**QED** ✓
(This shows why Deduction Theorem is so useful - it makes this trivial!)

### Example 2: Prove ⊢ (p → q) → ((q → r) → (p → r)) (Transitivity)
Using the Deduction Theorem (much easier!):
```
1.  Assume p → q                  [assumption]
2. Assume q → r                  [assumption]  
3. Assume p                      [assumption]
4. q                             [MP on 1,3]
5. r                             [MP on 2,4]
6. p → r                         [Deduction Theorem on 3-5]
7. (q → r) → (p → r)            [Deduction Theorem on 2-6]
8. (p → q) → ((q → r) → (p → r)) [Deduction Theorem on 1-7]
```

**QED** ✓

### Example 3: Prove p, p → q ⊢ q
This is just Modus Ponens itself!
```
1. p                   [given]
2. p → q              [given]
3. q                   [MP on 1,2]
```

**QED** ✓

# What is Resolution?
## Overview
**Resolution** is a powerful proof technique used in automated theorem proving and logic programming (like Prolog).

**Key Idea:**
- Convert formulas to a special form (clauses)
- Apply ONE simple rule repeatedly (the resolution rule)
- Look for a contradiction (the empty clause)

**Resolution** is a **rule of inference** used in automated theorem proving for propositional and first-order logic. It's a systematic technique for determining whether logical formulas are satisfiable or whether conclusions follow from premises.​
It's a **refutation-based** system: to prove something is true, we prove its negation leads to a contradiction.

## The Core Idea
Resolution works by **proof by contradiction (refutation)**. To prove a statement:[](https://www.geeksforgeeks.org/artificial-intelligence/resolution-algorithm-in-artificial-intelligence/)​
1. **Negate** what you want to prove
2. **Convert** everything to Conjunctive Normal Form (CNF) - clauses connected by AND[](https://www.geeksforgeeks.org/artificial-intelligence/resolution-algorithm-in-artificial-intelligence/)​
3. **Apply the resolution rule** repeatedly to derive new clauses
4. If you derive the **empty clause** (⊥ or a box), you've found a contradiction, proving the original statement​

## Core Concept
### To prove a formula is valid:
1. **Negate** it
2. Convert to **CNF** (Conjunctive Normal Form)
3. Apply **resolution rule** repeatedly
4. If you derive the **empty clause □**, the original formula is valid!

## The Resolution Rule
This is the **ONLY** rule you need!
### The Rule:
Given two clauses:
```
C₁:  A ∨ p
C₂:  B ∨ ¬p
```

Where:
- A and B are disjunctions of other literals
- p appears positive in C₁ and negative in C₂

**Resolve** them to get:
```
C₃:  A ∨ B
```

We say: "**Resolve C₁ and C₂ on p**"

### Visual:
```
  p ∨ q         ¬p ∨ r
  ─────────────────────
       q ∨ r
```

The p and ¬p "cancel out", leaving q ∨ r

### Some Properties:
**$S ⊢_{(Res)} C$** ( $⊢_{(Res)}$ is like “provable from S using resolution”.) is usually defined via _refutation_:
- Take $S ∪ {¬C}$ (where ¬C is turned into clauses),
- Use the **resolution rule** on these clauses,
- If you can derive the **empty clause** ⊥, then  
    S ⊢₍Res₎ C

If **R is a resolvent of C and D**, then:
- Every model of S is also a model of S ∪ {R}  
	⇒ adding R does **not** remove any models (satisfiability is preserved).
- But models of S ∪ {R} might not satisfy C and D individually if you _drop_ them.

### More Examples:

**Example 1:**
```
C₁: p ∨ q
C₂: ¬p ∨ r

Resolve on p:
───────────────
C₃: q ∨ r
```

**Example 2:**

```
C₁: p ∨ q ∨ s
C₂: ¬p ∨ r

Resolve on p:
───────────────
C₃: q ∨ s ∨ r
```

**Example 3:**

Code

```
C₁: p
C₂: ¬p ∨ q

Resolve on p:
───────────────
C₃: q
```

**Example 4:**

Code

```
C₁: p
C₂: ¬p

Resolve on p:
───────────────
C₃: □   (empty clause - CONTRADICTION!)
```

---

## Complete Example: Prove (p → q) ∧ p ⊢ q

### Step 1: Setup

We want to prove: **(p → q) ∧ p ⊢ q**

To use resolution (refutation):

- Assume the premises: (p → q) ∧ p
- Negate the conclusion: ¬q
- Show this leads to contradiction

### Step 2: Convert to CNF

**Premises:** (p → q) ∧ p

Convert (p → q):

Code

```
p → q  becomes  ¬p ∨ q
```

So we have:

Code

```
(¬p ∨ q) ∧ p
```

**Negated conclusion:** ¬q

**Full set (CNF):**

Code

```
C₁: ¬p ∨ q
C₂: p
C₃: ¬q
```

### Step 3: Apply Resolution

Code

```
C₁: ¬p ∨ q
C₂: p
─────────── (resolve on p)
C₄: q
```

Code

```
C₄: q
C₃: ¬q
─────────── (resolve on q)
□   (empty clause!)
```

### Step 4: Conclusion

We derived the **empty clause □**, which means **contradiction**!

Therefore, the original claim **(p → q) ∧ p ⊢ q** is **VALID** ✓

---

## Example 2: Prove p ∨ q, ¬p ∨ r, ¬q ∨ r ⊢ r

### Step 1: Setup

**Premises:**

Code

```
p ∨ q
¬p ∨ r
¬q ∨ r
```

**Negated conclusion:** ¬r

**Full CNF:**

Code

```
C₁: p ∨ q
C₂: ¬p ∨ r
C₃: ¬q ∨ r
C₄: ¬r
```

### Step 2: Apply Resolution

Code

```
C₂: ¬p ∨ r
C₄: ¬r
─────────── (resolve on r)
C₅: ¬p
```

Code

```
C₁: p ∨ q
C₅: ¬p
─────────── (resolve on p)
C₆: q
```

Code

```
C₃: ¬q ∨ r
C₄: ¬r
─────────── (resolve on r)
C₇: ¬q
```

Code

```
C₆: q
C₇: ¬q
─────────── (resolve on q)
□   (empty clause!)
```

### Step 3: Conclusion

Empty clause derived! Therefore **p ∨ q, ¬p ∨ r, ¬q ∨ r ⊢ r** is **VALID** ✓

---

## Example 3: Is p ∨ q, ¬p ⊢ r valid?

### Step 1: Setup

**Premises:**

Code

```
C₁: p ∨ q
C₂: ¬p
```

**Negated conclusion:** ¬r

Code

```
C₃: ¬r
```

### Step 2: Apply Resolution

Code

```
C₁: p ∨ q
C₂: ¬p
─────────── (resolve on p)
C₄: q
```

Now we have: q, ¬p, ¬r

**No more resolutions possible!** We can't derive the empty clause.

### Step 3: Conclusion

We **cannot** derive □, so the argument is **INVALID** ✗

Indeed, p ∨ q and ¬p don't tell us anything about r!

---

## Resolution Refutation Tree

You can draw resolutions as a tree:

**Example:** Prove p, p → q ⊢ q
```
                    ¬p ∨ q      p
                        \      /
                         \    /
                          q
                          |
                         q    ¬q
                          \  /
                           □
```

The tree "grows" toward the empty clause.

---

## Why Resolution Works

### Soundness:

The resolution rule is **sound**:

- If C₃ is derived from C₁ and C₂, then C₁ ∧ C₂ ⊨ C₃

**Proof:**

- C₁ = A ∨ p
- C₂ = B ∨ ¬p
- Case 1: If p is true, then B must be true (from C₂), so A ∨ B is true
- Case 2: If p is false, then A must be true (from C₁), so A ∨ B is true
- Either way, A ∨ B is true ✓

### Completeness:
Resolution is **complete**:
- If a set of clauses is unsatisfiable, resolution will derive the empty clause
This is the **Resolution Theorem** (Robinson, 1965).

# Strong Soundness theorem 
 


# Complete Guide to Logic in Computer Science

I'll teach you these topics step by step, starting from the basics.  Think of this as a journey from simple logic concepts to powerful verification tools used in real computer science applications.

---

## 1. First-Order Logic (FOL): The Foundation

### **Syntax - The Grammar of Logic**
Think of logic like a language. Before we can say meaningful things, we need to learn the alphabet and grammar. 

#### **Basic Building Blocks:**
**1. Terms** (things we can talk about):
- **Constants**: Specific objects (like `john`, `5`, `london`)
- **Variables**: Placeholders (like `x`, `y`, `z`)
	- A **variable** is just a symbol like x, y, z that can occur in a formula
	- A **free variable in a formula A** is a variable that occurs in A but is **not** under the scope of any quantifier (∀ or ∃) that binds it.
	- A **bound variable in a formula A** is a variable that occurs in A but is under the scope of a quantifier (∀ or ∃) that binds it.
- **Functions**: Operations that produce new terms (like `father(john)`, `add(2,3)`)

**2. Predicates** (statements about things):
- Express relationships or properties
- Examples: `IsStudent(x)`, `Loves(x, y)`, `GreaterThan(x, 5)`

**3. Logical Connectives** (how we combine statements):
- `¬` (NOT) - negation
- `∧` (AND) - conjunction
- `∨` (OR) - disjunction
- `→` (IMPLIES) - implication
- `↔` (IFF) - bi-implication

**4. Quantifiers** (talking about how many):
- `∀` (FOR ALL) - universal quantifier: "for every..."
- `∃` (EXISTS) - existential quantifier: "there exists..."

### **First-Order Structure** (or Model)
#### **General Form:**

```
𝒥 = (D, {relations}, {functions}, {constants})
```

Or more formally
```
𝒮 = (D, R₁, R₂, .. ., f₁, f₂, .. ., c₁, c₂, ...)
```

---
#### The Four Components
**1. Domain (Universe of Discourse)**
```
D or 𝒰
```
- The set of objects we're talking about
- In your case: ℕ = {0, 1, 2, 3, ...}

**2. Relation Interpretations**
```
R^𝒥 ⊆ Dⁿ
```
- Interprets predicate symbols
- n-ary relations on the domain
- In your case: R = {(d₁, d₂) | d₁ ≤ d₂} ⊆ ℕ²

**3. Function Interpretations**
```
f^𝒥 : Dⁿ → D
```
- Interprets function symbols
- Maps n-tuples to domain elements
- In your case: {} (empty - no functions)

**4. Constant Interpretations**
```
c^𝒥 ∈ D
```
- Interprets constant symbols
- Specific elements of the domain
- In your case: 0^𝒥 = 0

**Example Sentences:**
```
Domain: People and their relationships

1. "Every student studies"
   ∀x (IsStudent(x) → Studies(x))

2. "There exists someone who loves everyone"
   ∃x ∀y Loves(x, y)

3. "John is taller than someone"
   ∃y TallerThan(john, y)

4. "If someone studies hard, they will pass"
   ∀x (StudiesHard(x) → Passes(x))
```

### **Semantics - What Logic Means**
Syntax tells us what we *can* write.  Semantics tells us what it *means*. 

#### **Interpretation/Model:**
An interpretation assigns meaning to our symbols:
1. **Domain**: The set of objects we're talking about
2. **Constant Assignment**: What each constant refers to
3. **Function Assignment**: What each function computes
4. **Predicate Assignment**: Which objects satisfy which predicates

#### **Example:**
```
Statement: ∀x (IsStudent(x) → HasID(x))
"Every student has an ID"

Model 1:
Domain: {Alice, Bob, Carol}
IsStudent: {Alice, Bob}
HasID: {Alice, Bob, Carol}
Result: TRUE (all students have IDs)

Model 2:
Domain: {Alice, Bob}
IsStudent: {Alice, Bob}
HasID: {Alice}
Result: FALSE (Bob is a student but has no ID)
```

**Truth Values:**
- A formula is **valid** if it's true in ALL interpretations
- A formula is **satisfiable** if it's true in AT LEAST ONE interpretation
- A formula is **unsatisfiable** if it's false in ALL interpretations
---

## 2. Semantic Tableaux (Truth Trees
### **What is it?**
Imagine you're a detective trying to prove someone is guilty. Instead of proving they're guilty directly, you assume they're innocent and look for a **contradiction**. If assuming innocence leads to impossibility, they must be guilty! 

Semantic tableaux work the same way - we assume the opposite of what we want to prove and try to find a contradiction.

### **The Method:**

**Step-by-step Process:**
1. **Negate** what you want to prove
2. **Break down** formulas using rules
3. **Branch** when you have alternatives (OR statements)
4. **Close branches** when you find contradictions (P and ¬P)
5. **If all branches close** → Original formula is valid! 

### **Tableau Rules:*
```
Double Negation (¬¬):
    ¬¬P
     |
     P

Conjunction (∧):
   P ∧ Q
     |
     P
     Q

Disjunction (∨):
   P ∨ Q
    / \
   P   Q

Negated Conjunction (¬∧):
   ¬(P ∧ Q)
     / \
    ¬P  ¬Q

Negated Disjunction (¬∨):
   ¬(P ∨ Q)
      |
     ¬P
     ¬Q

Implication (→):
   P → Q
    / \
   ¬P   Q

Universal (∀):
   ∀x P(x)
     |
   P(t)  [for any term t]

Existential (∃):
   ∃x P(x)
     |
   P(c)  [for a NEW constant c]
```

### **Example 1: Propositional Logic**

**Prove**: `(P → Q) ∧ P → Q` is valid
```
1. ¬((P → Q) ∧ P → Q)           [Negate the formula]
2. (P → Q) ∧ P                   [From 1, negating implication]
3. ¬Q                            [From 1]
4. P → Q                         [From 2, breaking conjunction]
5. P                             [From 2]
6.     / \                        [From 4, breaking implication]
    ¬P   Q
    |    |
    ×    ×                       [Left: contradicts 5, Right: contradicts 3]

Both branches closed! Formula is VALID ✓
```

### **Example 2: First-Order Logic*
**Prove**: `∀x P(x) → P(a)` is valid

```
1. ¬(∀x P(x) → P(a))             [Negate]
2. ∀x P(x)                       [From 1]
3. ¬P(a)                         [From 1]
4.  P(a)                          [From 2, instantiate with 'a']
5. ×                             [Contradiction: P(a) and ¬P(a)]

Branch closed! Formula is VALID ✓
```
---
## 3. Resolution
### **What is Resolution?**
Resolution is like having a **superpower rule** that can solve any logic problem.  Instead of many rules (like tableaux), we have ONE powerful rule.  But first, we need to convert formulas to a special form. 

### **The Journey to Resolution:**
**Step 1: Convert to CNF (Conjunctive Normal Form)**

CNF is like organizing your closet - everything must be in a specific format:
- **Literals**: P or ¬P (atomic or negated atomic)
- **Clauses**: Disjunction of literals (P ∨ ¬Q ∨ R)
- **CNF**: Conjunction of clauses ((P ∨ Q) ∧ (¬R ∨ S))

**Conversion Steps:**
```
1. Eliminate implications:    P → Q  becomes  ¬P ∨ Q
2. Move ¬ inward (De Morgan):  ¬(P ∧ Q) becomes ¬P ∨ ¬Q
3.  Distribute ∨ over ∧:        P ∨ (Q ∧ R) becomes (P ∨ Q) ∧ (P ∨ R)
```

**Example:**

```
Original: P → (Q ∧ R)

Step 1: ¬P ∨ (Q ∧ R)              [Eliminate →]
Step 2: (¬P ∨ Q) ∧ (¬P ∨ R)       [Distribute ∨]

CNF: (¬P ∨ Q) ∧ (¬P ∨ R) ✓
```

### **Step 2: The Resolution Rule**

```
If you have:
   Clause 1: ...  ∨ P ∨ ... 
   Clause 2: ...  ∨ ¬P ∨ ... 

You can derive:
   New Clause: ... ∨ ...   [Everything except P and ¬P]
```

**Think of it as**: If one clause says "P is true OR something else" and another says "P is false OR something else," then "something else" must be true! 

### **Resolution Algorithm:**

```
To prove: Premises ⊢ Goal

1. Convert Premises to CNF
2. Convert ¬Goal to CNF (proof by contradiction!)
3. Add all clauses to a set
4. Repeatedly apply resolution to derive new clauses
5. If you derive the empty clause □ → PROVEN!
6. If no new clauses can be derived → NOT PROVEN
```

### **Example: Propositional Resolution**
**Prove**: `P → Q, Q → R, P ⊢ R`
```
Premises in CNF:
1. ¬P ∨ Q           [P → Q]
2. ¬Q ∨ R           [Q → R]
3. P                [P]
4. ¬R               [Negated goal]

Resolution Steps:
5. Q                [Resolve 1 and 3 on P]
6. R                [Resolve 2 and 5 on Q]
7. □                [Resolve 4 and 6 on R - EMPTY CLAUSE!]

PROVEN! ✓
```

### **First-Order Resolution (with Unification)**
In first-order logic, we need **unification** - matching variables with terms.

**Example:**

```
Clause 1: P(x) ∨ Q(x)        [x is a variable]
Clause 2: ¬P(a) ∨ R(a)       [a is a constant]

Unify P(x) with P(a): x = a

Resolvent: Q(a) ∨ R(a)
```

**Full Example:**

**Prove**: "All humans are mortal.  Socrates is human. Therefore, Socrates is mortal."

```
1. ∀x (Human(x) → Mortal(x))
2. Human(socrates)
⊢ Mortal(socrates)

CNF:
1. ¬Human(x) ∨ Mortal(x)
2. Human(socrates)
3. ¬Mortal(socrates)         [Negated goal]

Resolution:
4. Mortal(socrates)          [Resolve 1 and 2, x=socrates]
5. □                         [Resolve 3 and 4]

PROVEN! ✓
```

---

### 🎯 **Practice Problem 3:**

**Given:**
- "All birds can fly"
- "Penguins are birds"
- "Tweety is a penguin"

**Use resolution to prove**: "Tweety can fly"

Express in FOL and solve step by step. 

<details>
<summary><b>Solution:</b></summary>

```
FOL:
1. ∀x (Bird(x) → CanFly(x))
2. ∀x (Penguin(x) → Bird(x))
3.  Penguin(tweety)
⊢ CanFly(tweety)

CNF:
1. ¬Bird(x) ∨ CanFly(x)
2. ¬Penguin(y) ∨ Bird(y)
3. Penguin(tweety)
4. ¬CanFly(tweety)          [Negated goal]

Resolution:
5. Bird(tweety)              [Resolve 2,3 with y=tweety]
6. CanFly(tweety)            [Resolve 1,5 with x=tweety]
7. □                         [Resolve 4,6]

PROVEN! ✓
```
</details>

---

## 4. Logic Programming (Prolog)

### **What is Logic Programming?**

Instead of telling the computer **HOW** to solve a problem (like in Python or Java), you tell it **WHAT** the problem is, and it figures out the solution! 

Think of it like this:
- **Imperative programming**: "Go 3 blocks north, turn left, go 2 blocks..."
- **Logic programming**: "I want to get to the library" (computer figures out the route)

### **Syntax: The Three Components**

**1. Facts** (things we know are true):
```prolog
parent(tom, bob).          % Tom is Bob's parent
parent(tom, liz).          % Tom is Liz's parent
parent(bob, ann).          % Bob is Ann's parent
parent(bob, pat).          % Bob is Pat's parent
```

**2. Rules** (if-then relationships):
```prolog
grandparent(X, Y) :- parent(X, Z), parent(Z, Y).
% X is Y's grandparent IF X is Z's parent AND Z is Y's parent

sibling(X, Y) :- parent(Z, X), parent(Z, Y), X \= Y.
% X and Y are siblings IF they share parent Z AND X is not Y
```

**3.  Queries** (questions we ask):
```prolog
?- parent(tom, bob).       % Is Tom Bob's parent?
% Answer: yes. 

?- grandparent(tom, ann).  % Is Tom Ann's grandparent?
% Answer: yes. 

?- grandparent(tom, Who).  % Who are Tom's grandchildren?
% Answer: Who = ann; Who = pat. 
```

### **Proof Theory: How Prolog Thinks (SLD Resolution)**

Prolog uses **backward chaining** - it starts with the goal and works backward:

```
Query: ? - grandparent(tom, ann). 

Step 1: Find rule for grandparent
        grandparent(X, Y) :- parent(X, Z), parent(Z, Y).
        Unify: X=tom, Y=ann

Step 2: New goals: parent(tom, Z), parent(Z, ann)

Step 3: Try parent(tom, Z)
        Match fact: parent(tom, bob)
        So Z=bob

Step 4: Try parent(bob, ann)
        Match fact: parent(bob, ann)
        Success! 

Answer: yes ✓
```

### **Semantics: Herbrand Models**

The meaning of a logic program is defined by what's true according to the facts and rules. 

**Least Herbrand Model** = The minimal set of facts that must be true:

```prolog
Facts:
parent(a, b).
parent(b, c). 

Rule:
ancestor(X, Y) :- parent(X, Y). 
ancestor(X, Y) :- parent(X, Z), ancestor(Z, Y). 

Least Herbrand Model:
{
  parent(a, b),
  parent(b, c),
  ancestor(a, b),    % Direct
  ancestor(b, c),    % Direct
  ancestor(a, c)     % Transitive
}
```

---

### 🎯 **Practice Problem 4:**

Write a Prolog program for a family tree:

**Facts:**
- Alice is the parent of Bob and Carol
- Bob is the parent of Dave
- Carol is the parent of Eve

**Rules to implement:**
1. `ancestor(X, Y)` - X is an ancestor of Y
2. `descendant(X, Y)` - X is a descendant of Y
3. `cousin(X, Y)` - X and Y are cousins (share a grandparent but not a parent)

**Queries to test:**
1. Who are Alice's descendants?
2. Are Dave and Eve cousins? 

<details>
<summary><b>Solution:</b></summary>

```prolog
% Facts
parent(alice, bob).
parent(alice, carol).
parent(bob, dave).
parent(carol, eve).

% Rules
ancestor(X, Y) :- parent(X, Y).
ancestor(X, Y) :- parent(X, Z), ancestor(Z, Y). 

descendant(X, Y) :- ancestor(Y, X). 

grandparent(X, Y) :- parent(X, Z), parent(Z, Y). 

cousin(X, Y) :- 
    grandparent(Z, X), 
    grandparent(Z, Y), 
    parent(P1, X), 
    parent(P2, Y), 
    P1 \= P2,
    X \= Y.

% Queries:
% ? - descendant(Who, alice).
% Answer: bob, carol, dave, eve

% ?- cousin(dave, eve).
% Answer: yes.
```
</details>

---

## 5. Logic Programming in CS Applications

### **A.  Digital Circuits**

Logic programs can describe and verify circuits! 

**Example: Half Adder**

```prolog
% XOR gate
xor(0, 0, 0). 
xor(0, 1, 1).
xor(1, 0, 1). 
xor(1, 1, 0).

% AND gate
and(0, 0, 0).
and(0, 1, 0).
and(1, 0, 0).
and(1, 1, 1). 

% Half adder: adds two bits
% Sum is XOR, Carry is AND
half_adder(A, B, Sum, Carry) :-
    xor(A, B, Sum),
    and(A, B, Carry).

% Query: ? - half_adder(1, 1, Sum, Carry).
% Answer: Sum=0, Carry=1 (1+1=10 in binary)
```

**Full Adder** (adds 3 bits: two inputs + carry-in):

```prolog
full_adder(A, B, CarryIn, Sum, CarryOut) :-
    half_adder(A, B, Sum1, Carry1),
    half_adder(Sum1, CarryIn, Sum, Carry2),
    or(Carry1, Carry2, CarryOut).

or(0, 0, 0). 
or(0, 1, 1).
or(1, 0, 1).
or(1, 1, 1). 
```

### **B. Automata**

**Finite State Machine in Prolog:**

```prolog
% States: q0 (start), q1, q2 (accept)
% Alphabet: a, b
% Accepts strings with even number of 'a's

transition(q0, a, q1).
transition(q0, b, q0).
transition(q1, a, q0).
transition(q1, b, q1). 

accept_state(q0). 

% Check if a string is accepted
accepts(State, []) :- accept_state(State).
accepts(State, [Symbol|Rest]) :-
    transition(State, Symbol, NextState),
    accepts(NextState, Rest).

% Query: ?- accepts(q0, [a, b, a]). 
% Answer: yes (even number of a's)

% Query: ?- accepts(q0, [a, a, a]).
% Answer: no (odd number of a's)
```

### **C. AI - Planning and Search**

**Blocks World Problem:**

```prolog
% Representing state: on(Block, Location)
% move(Block, From, To, OldState, NewState)

move(Block, From, To, State, NewState) :-
    member(on(Block, From), State),
    member(clear(Block), State),
    member(clear(To), State),
    Block \= To,
    delete(State, on(Block, From), S1),
    delete(S1, clear(To), S2),
    NewState = [on(Block, To), clear(From) | S2]. 

% Goal: stack blocks A on B on C
% Start: on(a, table), on(b, table), on(c, table)
% Can plan sequence of moves! 
```

**Path Finding:**

```prolog
% Graph edges
edge(a, b).
edge(b, c).
edge(a, c). 
edge(c, d). 

% Find path from X to Y
path(X, Y, [X, Y]) :- edge(X, Y).
path(X, Y, [X|Path]) :-
    edge(X, Z),
    path(Z, Y, Path).

% Query: ?- path(a, d, Path).
% Answer: Path = [a, c, d]
```

---

### 🎯 **Practice Problem 5:**

**Design a vending machine in Prolog:**

**Specification:**
- States: idle, has_coin, dispensing
- Inputs: insert_coin, select_item, take_item
- Accepts 1 coin, dispenses 1 item

Write the FSM and test it! 

<details>
<summary><b>Solution:</b></summary>

```prolog
% Transitions: trans(State, Input, NextState, Output)
trans(idle, insert_coin, has_coin, none).
trans(has_coin, select_item, dispensing, dispense).
trans(dispensing, take_item, idle, none). 

% Simulate vending machine
simulate(State, [], State, []).
simulate(State, [Input|Inputs], FinalState, [Output|Outputs]) :-
    trans(State, Input, NextState, Output),
    simulate(NextState, Inputs, FinalState, Outputs).

% Test:
% ? - simulate(idle, [insert_coin, select_item, take_item], Final, Outputs).
% Answer: Final=idle, Outputs=[none, dispense, none]
```
</details>


---

## 7. Model Checking
