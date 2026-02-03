**Expected to know:**
**Topics Covered:**
**Tags:**

**Assertions** are statements embedded in code to verify that certain conditions hold true at specific points during program execution. They act as internal self-checks for the program, ensuring that the assumptions made by the programmer remain valid as the code runs.

## How Assertions Work
- An assertion typically evaluates a Boolean expression (a condition that should always be true).
- If the condition is true, the program continues as normal.
- If the condition is false, the program usually halts execution and reports an assertion failure, often providing information about where and why the failure occurred

```C
assert(<expression>)
```

At run time, the expression is evaluated. If the assertion evaluates to true, nothing happens, else the code is aborted and the assertion failure is printed in the console. 
Assertions are makros.