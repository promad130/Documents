![[Introduction to Prolog#So just like how we have And and Or in prolog, do we have other logic operators as well?]]
Prolog uses **operators** to make code more readable and expressive. Operators can be arithmetic, comparison, logical, list-related, or control-flow. Here’s a concise overview:

## 1. Arithmetic Operators

- `+`, `-`, `*`, `/` : Addition, subtraction, multiplication, division
- `mod`, `rem` : Modulus, remainder
- Example: `X is 2 + 3.`

## 2. Comparison Operators
- `=:=` : Numeric equality (e.g., `X =:= Y` means X equals Y numerically)
- `=\=` : Numeric inequality
- `>`, `<`, `>=`, `=<` : Greater than, less than, etc.
- Example: `X > Y`

## 3. Logical Operators
- `,` : AND (conjunction)
- `;` : OR (disjunction)
- `\+` : NOT (negation as failure)
- `:-` : IF/implication (used in rules)
- Example: `happy(X) :- cat(X), likes(X, milk).`

## 4. List Operators
- `|` : Pipe operator, separates head and tail of a list (e.g., `[H | T]` means H is the first element, T is the rest)
- `[]` : Empty list
- Example: `[a | [b, c]]` results in `[a, b, c]`

## 5. Identity and Unification Operators
- `==` : Checks if two terms are strictly identical, by strictly identical we mean that the binding is not allowed and the variables itself should be the same, i.e., `X == X` is true, but `X == Y` is false. Even, tom = X is false.
- `\==` : Checks if two terms are not strictly identical, by strictly identical, we mean - Unlike `=` which tries to unify (make terms equal by binding variables), `\==` checks if the terms are already _different_ without doing any binding. Example, `X \== Y` succeeds if `X` and `Y` are _not_ identical terms, i.e., exactly inverse of what `==` does.
- `=` : Unification (tries to make two terms equal), here, both terms can have different variables and binding is allowed.

## 6. Control Operators
- `!` : Cut operator, prevents backtracking past this point.

## 7. Operator Precedence and Types
In Prolog, **operator precedence** is a numeric value assigned to operators to determine the order in which parts of an expression are grouped and evaluated when parentheses are not explicitly used.

### Key Points on Operator Precedence:
- Prolog operator precedence ranges from **0 to 1200**.
- **Higher precedence numbers mean stronger binding** (contrary to some programming languages).
- The operator with the highest precedence in an expression becomes the **principal functor**, which determines how the expression is parsed.    
- Precedence controls how operators associate with their operands and with other operators.

### How Precedence Works:
- Consider the expression:
    $$2+3∗4$$    
    Here, the operator `+` has precedence 500, and `*` has precedence 400 in Prolog.
    
- Prolog treats `+` as the principal operator, so the structure is interpreted as:
    $$+(2,∗(3,4))$$
    which means multiplication happens first, then addition (following usual arithmetic rules).
 

### Associativity and Precedence Together:
- When operators have the same precedence, Prolog uses **associativity** to decide grouping.
- Associativity types include:
    - `yfx` - left associative (e.g., subtraction `-`)        
    - `xfy` - right associative (e.g., exponentiation `^`)        
    - `xfx` - non-associative (e.g., comparison operators)

### Examples of Operator Precedences (SWI-Prolog defaults):

|Operator|Precedence|Associativity|Description|
|---|---|---|---|
|`is`|700|xfx|Arithmetic evaluation|
|`+`|500|yfx|Addition (left associative)|
|`*`|400|yfx|Multiplication|
|`==`|700|xfx|Term equality|
|`\+`|900|fy|Negation, prefix unary|

### Defining Operators:
- Operators can be defined by:
```prolog
:- op(Precedence, Associativity, Operator).
```

### How is precedence decided?
- Operators have numeric precedence between 0 and 1200.
- Higher number = **greater precedence** (binds more tightly).
- Precedence is predefined for built-in operators (arithmetic, comparison, control).    
- Users can define new operators with specific precedence and associativity using:

```prolog

:- op(Precedence, Associativity, Operator).
```
- The chosen precedence determines parsing order; associativity clarifies grouping when operators have the same precedence.

### Are there predefined precedences?

Yes, Prolog defines a set of standard operators with fixed precedence and associativity, for example:

| Operator | Precedence | Associativity | Description                       |
| -------- | ---------- | ------------- | --------------------------------- |
| `*`      | 400        | yfx           | Multiplication (left associative) |
| `+`      | 500        | yfx           | Addition (left associative)       |
| `is`     | 700        | xfx           | Arithmetic evaluation             |
| `==`     | 700        | xfx           | Term equality                     |
| `,`      | 1000       | xfy           | Conjunction (right associative)   |
| `;`      | 1100       | xfy           | Disjunction (right associative)   |

**Practice:**
- Try writing a rule using AND and OR, or experiment with list patterns using the pipe operator.
- Want to see more examples or try queries with these operators?    
