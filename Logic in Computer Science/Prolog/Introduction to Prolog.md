Prolog (Programming in Logic) is a **declarative logic programming language** primarily used in artificial intelligence and symbolic computation. Instead of detailing how to solve a problem, in Prolog you declare facts and rules about the problem domain, and the Prolog system automatically solves queries by logical inference based on this knowledge base.

## How Prolog Works
- Prolog programs consist of **facts, rules, and queries**.
- A **fact** states something known to be true (e.g., man(john).).
- A **rule** expresses conditional logical relations (e.g., grandfather(X,Y) :- father(X,Z), parent(Z,Y).).
- A **query** asks if something can be inferred from the facts and rules (e.g., ?- grandfather(john, mike).).
- Internally, Prolog uses **SLD resolution** (a form of logical inference) with **backtracking** to explore multiple possibilities and find all solutions satisfying the query.
- Prolog's execution begins with a query, attempting to prove it true by matching it against facts and applying rules with unification and backtracking for search.

## Compilation and Running Prolog Code

- Prolog is often interpreted, but compiled Prolog implementations exist.
    
- For example, GNU Prolog offers a compiler that translates Prolog source files into executable binaries much like C compilers do.
    
- Running a Prolog program commonly involves loading (consulting) a `.pl` source file in the Prolog interpreter (e.g., SWI-Prolog), then issuing queries interactively.

So first install prolog in the system, and a prolog extension in VS Code.

### Running a prolog file:
- Start the interpreter:
 ```bash
swipl  
```
This opens the Prolog interactive prompt (`?-`).

- Load you file by writing filename in square brackets followed by `.`, a dot means end of the command
```bash
  [filename].
```

- run commands related to your file, and if you want to exit, type in this in terminal to exit:
```bash
	halt.  
```

For example:
```prolog
parent(pam, bob).

parent(tom, bob).

parent(tom, liz).

parent(bob, ann).

parent(bob, pat).

parent(pat, jim).
```

Terminal:
```bash
rubber_duck@pop-os:~/Desktop/Coding/Documents/Logic in Computer Science/Prolog/Practice$ swipl
Welcome to SWI-Prolog (threaded, 64 bits, version 8.4.2)
SWI-Prolog comes with ABSOLUTELY NO WARRANTY. This is free software.
Please run ?- license. for legal details.

For online help and background, visit https://www.swi-prolog.org
For built-in help, use ?- help(Topic). or ?- apropos(Word).

?- [first_prolog].
true.

?- parent(bob, pat)
|    .
true.

?- parent(X, Y).
X = pam,
Y = bob ;
X = tom,
Y = bob ;
X = tom,
Y = liz ;
X = bob,
Y = ann ;
X = bob,
Y = pat ;
X = pat,
Y = jim.

?- halt
|    .
```

# Getting started
**Prolog** is a logic programming language, quite different from languages like Java or Python. Instead of writing step-by-step instructions, you describe relationships and rules, and Prolog figures out answers by logical inference.

## Core Concepts
- **Predicate:** These are the properties that we attach to the constants, like cat in the next example.
- **Constants:** It's kind of like an identifier that we attach properties to, like tom in the next example. Consider them as atomic prepositions.
- **Facts:** State things that are always true (e.g., `cat(tom).` means Tom is a cat).
- **Rules:** Define relationships based on facts (e.g., `happy(X) :- likes(X, pizza).` means X is happy if X likes pizza).
- **Queries:** Ask questions to Prolog (e.g., `?- cat(tom).` asks if Tom is a cat).    
- **Variables:** Start with uppercase letters and represent unknowns (e.g., `X` in `cat(X).`).

Prolog programs are just collections of facts and rules. You query the system to get answers based on what you've defined.

**Quick Example:**
```prolog
% Facts 
cat(tom).
loves_to_eat(kunal, pasta).

% Rule 
happy(X) :- loves_to_eat(X, pasta).
```
**Query:**
```prolog
?- happy(kunal).
```
This asks: "Is Kunal happy?" Prolog checks the rule and facts, and answers `true`.

Let's clarify the **basic syntax** of Prolog facts and rules, how they work together, and the rules for casing.

### Facts
- **Syntax:**    
    - Format: `predicate(argument1, argument2, ...).`
    - Example: `cat(tom).` means "Tom is a cat".
    - **Casing:**
        - Predicate names (like `cat`) **must start with a lowercase letter**.
        - Arguments can be lowercase (for constants like `tom`) or uppercase (for variables, see below).
        - Facts always end with a period (`.`).

### Rules
- **Syntax:**
	- Has a dick that asks question, basically if-else of prolog.
    - Format: `head :- body.`
    - Example: `happy(X) :- likes(X, pizza).` means "X is happy if X likes pizza".
    - The part before `:-` is the **head** (what you're defining).
    - The part after `:-` is the **body** (the condition that must be true).
    - Multiple conditions in the body are separated by commas (AND), or semicolons (OR).
        
    - **Casing:**
        - Predicate names (like `happy`, `likes`) **must start with lowercase**.
        - **Variables** (like `X`) **must start with uppercase**.
        - Constants (like `pizza`) are lowercase.
        - Rules end with a period (`.`).

## How Facts and Rules Work Together
- **Facts** are unconditional truths (e.g., `cat(tom).`).
- **Rules** use facts to define new relationships (e.g., `happy(X) :- cat(X), likes(X, milk).`).    
- When you ask a **query** (e.g., `?- happy(tom).`), Prolog checks if the facts and rules make it true.

## Example Breakdown
```prolog
cat(tom).           % Fact: Tom is a cat 
likes(tom, milk).   % Fact: Tom likes milk 

happy(X) :- cat(X), likes(X, milk).  % Rule: X is happy if X is a cat and likes milk
```
- Query: `?- happy(tom).` → Prolog checks if `cat(tom)` and `likes(tom, milk)` are true. If yes, it answers `true`.

## Casing Rules Recap
- **Predicates** (fact/rule names): lowercase only (`cat`, `likes`, `happy`).
- **Constants** (specific objects): lowercase only (`tom`, `pizza`).
- **Variables**: uppercase only (`X`, `Y`).
- **Never** use uppercase for predicate or constant names in facts/rules.

## Syntax Structure
- `predicate(argument1, argument2, ...)` is a **fact** if it ends with a period.
- `predicate(arguments) :- condition1, condition2.` is a **rule** if it uses `:-` and ends with a period.
- **Variables** let rules and queries be general, so Prolog can find all matches.

## Statement ending and comments
**1. Statement Endings**
- Every fact, rule, and query in Prolog **must end with a period (`.`)**. This tells the interpreter that the statement is complete.
    - Example fact: `cat(tom).`
    - Example rule: `happy(X) :- likes(X, pizza).`
    - Example query: `?- cat(tom).`

**2. Comments**
- **Single-line comments** start with `%` and continue to the end of the line. Use `%` at the beginning of a line or after code to add notes.[](https://athena.ecs.csus.edu/~mei/logicp/prolog.html)
    - Example:
```prolog
% This is a single-line comment cat(tom). 
% Tom is a cat
```        
- **Multi-line comments** use `/* ... */` and can span several lines. These cannot be nested.
    - Example:
```prolog
/*   
	This is a multi-line comment  
	explaining the code below 
*/ 

cat(tom).
```


## So just like how we have And and Or in prolog, do we have other logic operators as well?
Let's clarify the main **logic operators** in Prolog and how they work in rules and queries.

### Core Logical Operators

| Operator | Meaning                   | Usage Example        |
| -------- | ------------------------- | -------------------- |
| `,`      | AND (Conjunction)         | `A, B` means A AND B |
| `;`      | OR (Disjunction)          | `A ; B` means A OR B |
| `:-`     | IF/Implication            | `head :- body.`      |
| `\+`     | NOT (Negation as failure) | `\+ A` means NOT A   |

- **AND**: Use a comma to require all conditions to be true.    
- **OR**: Use a semicolon to allow any condition to be true.
- **NOT**: Use `\+` before a goal to check if it cannot be proven.
- **Implication**: `:-` separates the head (what you're defining) from the body (the condition).

### Key Operator Precedence (from highest to lowest)
1. **NOT (`\+`)**: Highest precedence among logical operators. Negation is applied first.
2. **AND (`,`)**: Next highest. Conjunctions are evaluated after NOT.
3. **OR (`;`)**: Lower than AND. Disjunctions are evaluated after conjunctions.
4. **Implication (`:-`)**: Used to define rules; lowest precedence among these.

### Example
```rolog
happy(X) :- cat(X), (likes(X, milk); likes(X, fish)).
```
- Parentheses are used to control grouping: here, `likes(X, milk); likes(X, fish)` is evaluated as a unit, then ANDed with `cat(X)`.    
- Without parentheses, `cat(X), likes(X, milk); likes(X, fish)` would be interpreted as `(cat(X), likes(X, milk)) OR likes(X, fish)` due to precedence.

[More about operators in prolog](Operators%20in%20Prolog)

# Asking Questions in Prolog:
When you load a Prolog program (facts and rules) into the interpreter, you can ask questions—called **queries**—to get answers based on your knowledge base.

## How to Write a Query
- Start with `?-` (question mark and hyphen).
- Write the predicate and arguments, just like a fact or rule.
- End with a period (`.`).

**Example:**
```prolog
?- food(pizza).
```
This asks: "Is pizza a food?" Prolog will answer `true` or `false` depending on your facts.

## Queries with Variables

- Use uppercase for variables to ask general questions.
- Example:
```prolog
?- meal(X).
```
This asks: "What is a meal?" Prolog will list all values of `X` that make the query true.    

## Multi-condition Queries

- Combine conditions with `,` (AND) or `;` (OR).
- Example:
```prolog
?- meal(X), lunch(X).
``` 
This asks: "Which food is both a meal and a lunch?"

## Getting All Answers
- After Prolog shows the first answer, type `;` (semicolon) to see more solutions, if available.

Try out this:
```prolog
food(burger).

food(sandwich).

food(pizza).

lunch(sandwich).

dinner(pizza).

meal(X) :- food(X).
```

Terminal:
```bash
rubber_duck@pop-os:~/Desktop/Coding/Documents/Logic in Computer Science/Prolog/Practice$ swipl
Welcome to SWI-Prolog (threaded, 64 bits, version 8.4.2)
SWI-Prolog comes with ABSOLUTELY NO WARRANTY. This is free software.
Please run ?- license. for legal details.

For online help and background, visit https://www.swi-prolog.org
For built-in help, use ?- help(Topic). or ?- apropos(Word).

?- [practice].
true.

?- food(pizza).
true.

?- food(x).
false.

?- food(X).
X = burger ;
X = sandwich ;
X = pizza.

?- meal(X).
X = burger ;
X = sandwich ;
X = pizza.

?- meal(x), dinner(Y).
false.

?- meal(X), dinner(Y).
X = burger,
Y = pizza ;
X = sandwich,
Y = pizza ;
X = Y, Y = pizza.

?- meal(X), dinner(X).
X = pizza.

?- halt.
```

# Getting in more depth 
## Data Objects in Prolog
In Prolog, data objects refer to the fundamental building blocks used to represent information and structure within the language. These include atoms, numbers, variables, and structures (also called compound terms), each serving a distinct purpose in logic programming.
![[Pasted image 20250918183205.png]]

- **Compound Terms (Structures):** These are formed by combining a functor (an atom) with a list of arguments, allowing representation of complex data. For example: `day(9, jun, 2017)`, `point(10, 25)`, `person('John', 30)`.
- **Variables:** Variables represent unknown or placeholder values that get determined during program execution. They start with an uppercase letter or an underscore. Examples: `X`, `Person`, `_Temp`.
- **Atoms(Constants):** These are constant symbols that represent fixed objects or entities. They typically start with a lowercase letter or are enclosed in single quotes. Examples: `tom`, `pat`, `'JohnDoe'`.
- **Numbers(Constants):** Prolog supports both integers and floating-point numbers. Examples: `100`, `3.14`, `-42`.

## Atoms and Numbers
### Atoms
**What are atoms?**
- Simple data objects that represent constants
- Used as names for objects, relations, and properties
- Distinguished from variables by starting with **lowercase letters**

**Three ways to write atoms:**[^1]

1. **Standard form**: Start with lowercase letter, can include letters, digits, underscore
    - Examples: `anna`, `x25`, `alpha_beta_procedure`, `miss_jones`
2. **Special characters**: Strings of symbols like `+`, `-`, `*`
    - Be careful - some have predefined meanings (like `:-`)
3. **Quoted atoms**: Enclosed in single quotes for special cases
    - Examples: `'Tom'`, `'South America'`, `'Sarah Jones'`
    - Use when you want atoms starting with capitals or containing spaces

## **Numbers**
**Two types:**[^1]
1. **Integers**: Simple whole numbers
    - Examples: `1313`, `-97`, `42`
    - Range typically at least -16383 to 16383
2. **Real numbers**: Decimal numbers
    - Examples: `3.14`, `-0.0035`, `100.2`
    - Not commonly used in Prolog (symbolic processing language)
    - Avoid due to rounding errors in arithmetic

## **Key Points**
- **Type recognition**: Prolog identifies data types by syntax - no declarations needed[^1]
- **Variables vs atoms**: Variables start with **uppercase** letters or underscore; atoms start with **lowercase**
- **Scope**: Same atom means same object throughout the entire program
- **Usage**: Atoms for symbolic representation, integers for counting/indexing


## **Examples**

```prolog
% Atoms
parent(tom, bob).     % tom, bob are atoms
likes(mary, tennis).  % mary, tennis are atoms

% Numbers  
age(tom, 25).        % 25 is integer
price(book, 12.99).  % 12.99 is real number

% Variables (start with uppercase)
parent(X, bob).      % X is variable
age(Person, Age).    % Person, Age are variables
```

The key is understanding the syntax rules - this determines how Prolog interprets your data objects.

## Variables
### What are Variables?
Variables are **placeholders** for objects that can be **instantiated** (given values) during program execution. They represent **unknown** or **changeable** values.

#### Syntax Rules
**How to write variables:**
- Start with **uppercase letter** or **underscore character** `_`
- Can contain letters, digits, and underscores
- Examples: `X`, `Result`, `Object2`, `Participantlist`, `Shoppinglist`, `_23`, `_`

### Anonymous Variables
**Special case: `_` (single underscore)**
- Used when you don't care about the variable's value
- Each `_` represents a **different** anonymous variable
- Value not displayed in Prolog output

**Examples:**
```prolog
hasachild(X) :- parent(X, _).        % Don't care about child's name
somebody_has_child :- parent(_, _).   % Don't care about either person
```

### Variable Scope
**Key rule: Scope is one clause only**
- Same variable name in **different clauses** = **different variables**
- Same variable name in **same clause** = **same variable**
- Variables are **local** to each clause

**Example:**
```prolog
rule1(X) :- parent(X, Y).    % X and Y are variables in rule1
rule2(X) :- age(X, 25).      % This X is DIFFERENT from X in rule1
```

### Instantiation
**What happens during execution:**
- Variables start **uninstantiated** (no value)
- Get **instantiated** through matching/unification
- Can become increasingly **specific** through multiple goals

**Example process:**
```prolog
?- parent(X, bob), age(X, Age).
% X becomes instantiated to whoever is parent of bob
% Age becomes instantiated to that person's age
```

## Structures
**Structures** are objects that have several components, where each component can also be a structure. They're **compound data objects** that combine multiple pieces of information into a single object.

### Basic Syntax
**Structure format:**
```
functor(argument1, argument2, ..., argumentN)
```

**Components:**
- **Functor**: Name of the structure (follows atom syntax rules)
- **Arguments**: The components (can be atoms, numbers, variables, or other structures)
- **Arity**: Number of arguments the functor takes

### **Example: Date Structure**
```prolog
date(1, may, 1983)
```
- **Functor**: `date`
- **Arguments**: `1`, `may`, `1983`
- **Arity**: 3

### **More Complex Examples**
**Geometric objects:**
```prolog
% Points
point(1, 1)
point(2, 3)

% Line segments (using points as components)
seg(point(1,1), point(2,3))

% Triangles  
triangle(point(4,2), point(6,4), point(7,1))
```

**Variable components:**[^1]
```prolog
date(Day, may, 1983)    % Day is variable - any day in May 1983
```

### Tree Representation
All structured objects can be visualized as **trees**:
- **Root**: The functor
- **Branches**: The arguments
- If an argument is also a structure, it becomes a subtree

### Functor Identity
**Two key properties define a functor:**
1. **Name** (follows atom syntax)
2. **Arity** (number of arguments)

**Same name, different arity = different functors:**
```prolog
point(X, Y)      % 2D point - functor point/2
point(X, Y, Z)   % 3D point - functor point/3
```

## Infix Notation
Special functors can be written in ***infix form***:
```prolog
% Standard form
+(*(a, b), -(c, 5))

% Infix notation (more readable)  
a * b + c - 5
```

### **Key Points
- **Terms**: All data objects in Prolog are terms
- **Principal functor**: The main functor at the root of a term
- **Flexibility**: Components can be any combination of atoms, numbers, variables, or structures
- **No declarations needed**: Prolog recognizes structure syntax automatically

### Practical Applications
**Electric circuits:**
```prolog
seq(r1, r2)                    % Sequential composition
par(r1, r2)                    % Parallel composition  
par(r1, seq(par(r2, r3), r4))  % Complex circuit
```

Structures provide powerful data representation capabilities, allowing you to model complex real-world objects and relationships naturally in Prolog.

## Matching (or _unification_)
Matching in Prolog is the process of making two terms identical by instantiating variables, always yielding the most general instantiation needed for identity. 
It underpins clause selection and goal satisfaction by checking whether a goal matches a clause head and propagating the resulting substitutions.

<blockquote>Two terms match if they are already identical, or if variables in the terms can be instantiated so the terms become identical after substitution. If they do not match, the process fails; if they do, variables are instantiated accordingly to make the terms syntactically equal.</blockquote>

### Core rules
In Prolog, **matching** (or _unification_) between two instantiated data objects of the same functor means the terms can be made equal if:

- They have the same functor (name)
- They have the same arity (number of arguments)    
- All their corresponding arguments are equal, i.e., they match (**recursively**)

If both objects are fully instantiated (i.e., contain only atoms or numbers as arguments), matching succeeds **only** if each respective argument is exactly the same.

For example:
```prolog
point(2, 3)
point(2, 3)
```
This matches, but:
```prolog
point(2, 3)
point(3, 2)
```
This does not match.

If either term contains variables, **Prolog can create bindings to** make the terms equal. For example:
```prolog
?- point(X, Y) = point(2, 3).
X = 2,
Y = 3.
```
but if both are fully instantiated, then the matching can fail:
```prolog
?- point(1, 2) = point(2, 3).
false.
```

## Structured matching
Matching proceeds top-down on the term trees: 
- first the principal functors, 
- then argument pairs in order, each requiring a successful sub-match to succeed overall. 

For example, `triangle(point(1,1),A,point(2,3))` matches `triangle(X,point(4,Y),point(Z,Z))` with `X = point(1,1), A = point(4,Y), Z = 3` after recursive argument matching.

---
# What is Backtracking in Prolog?
**Backtracking** is a core mechanism in Prolog where the system automatically explores alternative choices when trying to satisfy a query or goal.
## How Does Backtracking Work?
- **Choice Points:** When Prolog encounters a situation with multiple matching facts or rules (e.g. several possible values for a variable), it creates a "choice point." This is a spot to come back to if later steps fail.​
- **Depth-First Search:** Prolog tries possibilities one-by-one in a depth-first manner, following rules, facts, and recursive calls to satisfy goals.
- **If Failure Occurs:** If a fact, rule, or condition cannot be satisfied, Prolog "backtracks"—it returns to the last choice point and tries a different alternative.
- **Multiple Solutions:** Backtracking allows Prolog to find all possible answers, not just the first one. When one solution is found, you can press `;` (semicolon) to make Prolog search for more using backtracking.

## Example
```prolog
boy(tom). 
boy(bob). 
girl(alice). 
girl(lili). 
pay(X, Y) :- boy(X), girl(Y).
```
Query: `pay(X, Y).`
- Prolog will first choose `X = tom`, and for `Y` tries `alice` then `lili`.
- After all combinations with `X = tom`, Prolog **backtracks** and tries `X = bob`, again going through all `girl(Y)` options.
- This process enumerates every possible boy-girl pairing, one result at a time.

## Analogies
- Backtracking in Prolog is like exploring all possible paths in a maze: if you hit a dead end, you go back to the last fork and try a new direction.
- In code and rules, it's a way to systematically search for all possible solutions, even when some paths fail.

## Why is Backtracking Useful?
- It's automatic and invisible: you don't need to write explicit code for it—Prolog does it for you.
- It makes logic programming powerful for searching, constraint satisfaction, and finding multiple solutions.
---

# Relations in Prolog
In Prolog, **relations** represent relationships between objects and their properties. 
A relation is expressed using **facts** and **rules** which describe how objects relate to one another. Relations are the fundamental way to represent knowledge in Prolog.

- Relations are predicates with arguments (terms), such as `parent(john, mary)` to which we can assign meaning like John is a parent of Mary, or John is father of Mary, as now they are now attached to a functor.
- They can be simple facts, like `male(john).`
- In this, the order of assignment matters, i.e., `(john, mary)` is not same as `(mary, john)`.
- Or they can be rules that define relations based on other relations, e.g., `mother(X, Y) :- parent(X, Y), female(X).`
- Relations are defined by **clauses**; facts are clauses with only a head, rules have a head and a body (conditions).

[More on Relations in Prolog](Relations%20in%20Prolog)

# Lists in Prolog
(Learn relations in prolog first, then and then only all this will make sense)
Lists in Prolog are fundamental data structures used for storing ordered collections of items. They have a special syntax and wide support for various operations.

## List Representation
- A list is either:
    - An empty list, denoted `[]`.
    - Or a head element followed by a tail list, denoted `[Head|Tail]`.
- Examples:
    - `[]` is an empty list.
    - `[a,b,c]` is a list of three elements: `a`, `b`, and `c`.
    - `[a|[b,c]]` is another way to write `[a,b,c]`.

## List Operations:
### Checking Membership:
```prolog
member(X, [X|_]).
member(X,[_|T]) :- member(X, T).
```

### Concatenation of the list:
```prolog
conc([], L, L).
conc([X|L1], L2, [X|L3]) :- 
	conc(L1, L2, L3).
```
Prolog uses **pattern matching** (also known as **unification**) here in the base case.

So for example, for `L1 = [a,b,c,d]` , `L2 = [e,f,g,h,i]`
then: 
```prolog
conc([], L, L).
conc([X|L1], L2, [X|L3]) :-
	conc(L1, L2, L3).
```
then doing `conc(L1, L2, X).` would be like:
```text
?- conc([a,b,c,d],[e,f,g,h,i], [a|Y]):
	conc([b,c,d], [e,f,g,h,i], [b|Y1]):
		conc([c,d], [e,f,g,h,i], [c|Y2]):
			conc([d], [e,f,g,h,i], [d|Y3]):
				conc([], [e,f,g,h,i], [X4|Y4])
```

So we get `[X4|Y4] = [e,f,g,h,i]`, and then following the recursion, it gives out `X = [a,b,c,d,e,f,g,h,i]`

### Understanding `conc(X, [b,c,d], [a,b,c,d])` in Prolog
Let's trace how Prolog solves the query:
```prolog
conc(X, [b,c,d], [a,b,c,d]).
```
Given the definition:
```prolog
conc([], L, L). 
conc([H|T], L2, [H|R]) :- 
	conc(T, L2, R).
```
#### Step-by-step Backtracking and Matching
1. **Prolog tries to match the recursive clause first:**
    - `[a,b,c,d]` matches `[H|R]`, so `H = a`, `R = [b,c,d]`
    - This gives a new subgoal:
		```prolog
		conc(X1, [b,c,d], [b,c,d])
		```
		and sets `X = [a|X1]`
    
2. **Now, Prolog tries to solve the new subgoal:**
    - `[b,c,d]` matches `[H|R]`, so `H = b`, `R = [c,d]`
    - New subgoal:
        ```prolog
        conc(X2, [b,c,d], [c,d])
        ```
        and `X1 = [b|X2]`
    
3. **Continue recursion:**
    - `[c,d]` matches `[H|R]`, so `H = c`, `R = [d]`
    - New subgoal:
        ```prolog
        conc(X3, [b,c,d], [d])
        ```
        and `X2 = [c|X3]`
    
4. **Continue recursion:**
    - `[d]` matches `[H|R]`, so `H = d`, `R = []`
    - New subgoal:
        ```prolog
        conc(X4, [b,c,d], [])
        ```
        and `X3 = [d|X4]`
    
5. **Base case applies:**
    - `conc([], [b,c,d], [b,c,d])` matches only if `X4 = []` (since the third argument must be `[b,c,d]`)
    - But we want `conc(X4, [b,c,d], [])`, i.e., what do you append to `[b,c,d]` to get `[]`? The only way is for `X4 = []` and `[b,c,d] = []`, which is false. **So recursion stops and Prolog backtracks.**
    - Now Prolog tries the base case for earlier recursive call, but finds it only fits if you stop at some prefix.

#### The Actual Solution
- The recursion builds `X = [a]` then tries to find what to append to `[b,c,d]` to get `[b,c,d]` (which is `[]`).
- So, `X = [a]` and base case produces the right third argument. Each longer prefix would be invalid.

#### All Possible Answers
- Prolog will also try shorter prefixes for `X`:
    - If `X = []`, then you get `conc([], [b,c,d], [b,c,d])`, which succeeds.
- If `X = [a]`, iteration above produces:
    - `conc([a], [b,c,d], [a,b,c,d])` is true, because `[a] + [b,c,d] = [a,b,c,d]`.

**So, how does Prolog know to go for `[a]`, `[]`, etc.?**
- Because `conc/3` is a relation, not a function. Given the desired output and one input, Prolog tries to match different possible values of `X` (starting with `[]`, then `[a]`, `[a,b]`, etc.), checks if appending it to `[b,c,d]` yields `[a,b,c,d]`, and returns all values that work.

#### Summary Table

| X                                                                                        | conc(X, [b,c,d], [a,b,c,d]) |
| ---------------------------------------------------------------------------------------- | --------------------------- |
| []                                                                                       | `[b,c,d]`                   |
| [a]                                                                                      | `[a,b,c,d]`                 |
| [a,b]                                                                                    | `[a,b,b,c,d]` (not match)   |
| [a,b,c]                                                                                  | `[a,b,c,b,c,d]` (not match) |
|                                                                                          |                             |
| **Only** `X = []` and `X = [a]` succeed when matching the output list structure exactly. |                             |

### adding an item to the list:
We are just looking at adding item at the very front for now:
```prolog
add(X, L, [X|L]).
```

### Deleting an item from the list:
In order to delete an item, we first need a sublist where that item is the head, and then return the new tail with the original head:
```prolog
del(X, [X|Tail], Tail).
del(X, [Y| Tail], [Y|Tail1]) :- 
	del(X, Tail, Tail1).
```

# Arithmetic in Prolog
In Prolog, arithmetic operations are performed using standard arithmetic operators:
- Addition: `+`
- Subtraction: `-`
- Multiplication: `*`
- Division: `/`
- Modulus (remainder): `mod` and `rem`
- Exponentiation: `^` or `**`

To evaluate arithmetic expressions and assign the result to a variable, Prolog uses the `is` operator. For example, to assign the sum of 2 and 3 to a variable `X`, you write:
```prolog
X is 2 + 3.
```
which yields `x = 5`.

## Key points about arithmetic in Prolog:
- The expression on the right side of `is` is evaluated.
- Variables on the right must be instantiated to numeric values before evaluation.
- Arithmetic comparisons use special operators like `=:=` (equal), `=\=` (not equal), `<`, `=<`, `>`, `>=`.
- Operator precedence follows arithmetic conventions: multiplication and division before addition and subtraction.
- Expressions can be nested, e.g., `X is (3 + 4) * 2`.

Example arithmetic predicates:
```prolog
add_3_and_double(X, Y) :- 
	Y is (X + 3) * 2.
```
Querying `add_3_and_double(1, X)` results in `X = 8`.

Now, what if we want the count of all the elements in the list?
```prolog
count([], 0).
count([Head|Tail], N) :-
	count(Tail, N1), N is N1 + 1.
```

