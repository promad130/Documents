![[Introduction to Prolog#Relations in Prolog]]

# Types of Relations in Prolog
Prolog is built around **relations**—connections between objects, properties, or concepts. These relations are defined using **facts** and **rules**.

## 1. Unary Relations
- **Definition:** Relate a single object to a property or category.
- **Example:**
    - `male(homer).` ("Homer is male.")
    - `female(marge).` ("Marge is female.")

## 2. Binary Relations
- **Definition:** Relate two objects to each other.
- **Examples:**
    - `parent(homer, bart).` ("Homer is Bart's parent.")
    - `mother(X, Y) :- parent(X, Y), female(X).` ("X is Y's mother if X is Y's parent and X is female.")
    - `friend(X, Y).` ("X is a friend of Y.")

## 3. N-ary Relations
- **Definition:** Relate more than two objects.
- **Example:**
    - `distance(X, Y, Z).` ("The distance from X to Y is Z." or "The distance from Y to Z is X.")

## 4. Family and Recursive Relations
- **Complex relations** can be built using rules and recursion:
    - **Sibling:** `sibling(X, Y) :- parent(Z, X), parent(Z, Y), X \== Y.`
    - **Ancestor (recursive):**
```rolog
ancestor(X, Y) :- parent(X, Y). 
ancestor(X, Y) :- parent(X, Z), ancestor(Z, Y).
```
This means X is an ancestor of Y if X is a parent of Y, or X is a parent of Z and Z is an ancestor of Y.

If I ask a query like `ancistor(X, susie).` ,how does this recursion works?
- The base case is the main start, if that is not fulfilled, then the recursion is not initiated.
- if the base case has found a true condition, then it enters the recursion like this:
	- First, it will go for: which values of X makes ancestor(X, susie) true in the recursion?
	- hence, it unfolds like this:
 ```text
ancestor(X, susie) : parent(X, Z) AND ancestor(Z, susie):
	First this: ancestor(Z,susie) : parent( Z, susie) ?
	if not that, then : ancestor(Z,susie) : parent(Z, Z1) AND ancestor(Z1, susie);   
  ```
This keeps on repeating, each time, once a possible value of X has been found, it is discarded from the database(collection of all possible values) so that it does not repeat.

But what values are exactly considered?
Prolog tries to find a match for the fact `parent(X, a)` for every possible value for X.
1. Prolog looks through its database for any fact where the **second argument is `a`**.
2. It doesn’t brute force every value of X—rather, it checks each stored `parent(FIRST, SECOND)` fact to see if `SECOND = a`.
3. For each match, Prolog returns the corresponding FIRST value bound to X.
4. If **none** of your facts have `a` as the **second argument**, Prolog returns **no** (false).

## 5. Properties of Relations
- **Reflexive:** Relates an object to itself (e.g., `equals(X, X)`).
- **Symmetric:** If `R(a, b)` then `R(b, a)` (e.g., `sibling(X, Y)` is symmetric).
- **Transitive:** If `R(a, b)` and `R(b, c)` then `R(a, c)` (e.g., `ancestor(X, Y)` is transitive).

## 6. Structured Relations
- Relations can use **structures** to represent more complex data:
    - Example: `sum(prod(num(3), num(4)), sum(num(5), num(6)))` can represent a math expression tree.

**Summary:**
- Prolog relations can be unary, binary, or n-ary.
- They can express simple facts, complex family relationships, and recursive connections.
- Properties like reflexivity, symmetry, and transitivity can be modeled using rules.

For example:
```prolog
parent(pam, bob).

parent(tom, bob).

parent(tom, liz).

parent(bob, ann).

parent(bob, pat).

parent(pat, jim).

  

female(pam).

female(liz).

female(ann).

female(pat).

male(tom).

male(bob).

male(jim).
```
We have used both, unary and binary relations.