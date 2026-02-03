**Expected to know:** [[Control Flow Statements]]
**Topics Covered:** break, continue, goto, return
**Tags:**

Jump statements are used to **transfer control** from one part of the program to another. They allow early termination of loops, skipping iterations, or jumping to specific code sections.

Jump statements are used in programming languages to **alter the normal flow of execution** by skipping, exiting, or jumping to different parts of the program.

### **Types of Jump Statements**

C provides **four** types of jump statements:

| Jump Statement | Description                                                      |
| -------------- | ---------------------------------------------------------------- |
| `break;`       | Exits a loop or `switch` statement.                              |
| `continue;`    | Skips the current iteration of a loop and moves to the next one. |
| `goto label;`  | Jumps to a labeled statement in the program.                     |
| `return;`      | Exits from a function and optionally returns a value.            |

---
# **1) `break;` 

The `break;` statement **immediately terminates** the nearest enclosing loop (`for`, `while`, `do-while`) or a `switch` case and **transfers control to the next statement after the loop or switch block**.

### **Usage of `break;`**

#### **1. In Loops (`for`, `while`, `do-while`)**

- `break;` **stops the loop** when a certain condition is met.

**Example: Stopping a `for` loop early**

```c
#include <stdio.h>

int main() {
    for (int i = 1; i <= 10; i++) {
        if (i == 5) {
            break;  // Exit loop when i == 5
        }
        printf("%d ", i);
    }
    return 0;
}
```

**Output:**  
`1 2 3 4`  
(The loop **stops at 5** and exits.)

**Example: Stopping a `while` loop early**

```c
#include <stdio.h>

int main() {
    int i = 1;
    while (i <= 10) {
        if (i == 5) {
            break;
        }
        printf("%d ", i);
        i++;
    }
    return 0;
}
```

**Output:**  
`1 2 3 4`

#### **2. In `switch` Statements**

- `break;` is used to **exit a `switch` case** after executing the matched case.
- Without `break;`, execution **falls through** to the next case.

 **Example: Using `break;` in `switch`**

```c
#include <stdio.h>

int main() {
    int num = 2;
    switch (num) {
        case 1:
            printf("One\n");
            break;
        case 2:
            printf("Two\n");
            break;
        case 3:
            printf("Three\n");
            break;
        default:
            printf("Invalid\n");
    }
    return 0;
}
```

**Output:**  
`Two`

 **What happens if we remove `break;`?**

```c
case 2:
    printf("Two\n");  // No break
case 3:
    printf("Three\n");
    break;
```

**Output (wrong behavior):**

```
Two
Three
```

**(It "falls through" to case 3, which is usually not desired.)**


### **Key Takeaways**

- **`break;` is used to exit loops and switch cases early.**  
-  **In loops, it stops execution and moves to the next statement after the loop.**  
- **In switch, it prevents fall-through to the next case.**

---
# **2) `continue;` 

The `continue;` statement is used inside loops to **skip the current iteration** and move to the next one. Unlike `break;`, which **exits** the loop, `continue;` simply **skips the rest of the loop body** for that iteration and proceeds with the next iteration.

### **Where Can `continue;` Be Used?**

✔ `for` loops  
✔ `while` loops  
✔ `do-while` loops

### **1️⃣ `continue;` in `for` Loop**

```c
#include <stdio.h>

int main() {
    for (int i = 1; i <= 5; i++) {
        if (i == 3) {
            continue;  // Skip iteration when i == 3
        }
        printf("%d ", i);
    }
    return 0;
}
```

**Output:**  
`1 2 4 5`  
(The loop **skips** `3`, but continues execution.)

### **2️⃣ `continue;` in `while` Loop**

```c
#include <stdio.h>

int main() {
    int i = 0;
    while (i < 5) {
        i++;
        if (i == 3) {
            continue;  // Skip iteration when i == 3
        }
        printf("%d ", i);
    }
    return 0;
}
```

**Output:**  
`1 2 4 5`  
(Same behavior as in `for` loop.)

### **3️⃣ `continue;` in `do-while` Loop**

```c
#include <stdio.h>

int main() {
    int i = 0;
    do {
        i++;
        if (i == 3) {
            continue;  // Skip iteration when i == 3
        }
        printf("%d ", i);
    } while (i < 5);
    return 0;
}
```

**Output:**  
`1 2 4 5`  
(Same behavior, `3` is skipped.)

### **Key Differences Between `break;` and `continue;`**

|Statement|Effect|
|---|---|
|`break;`|Exits the loop completely|
|`continue;`|Skips the current iteration but continues the loop|

### **Example: `break;` vs `continue;`**

```c
#include <stdio.h>

int main() {
    for (int i = 1; i <= 5; i++) {
        if (i == 3) {
            break;  // Stops the loop completely
        }
        printf("%d ", i);
    }
    printf("\n");

    for (int i = 1; i <= 5; i++) {
        if (i == 3) {
            continue;  // Skips 3 but continues the loop
        }
        printf("%d ", i);
    }
    return 0;
}
```

**Output:**

```
1 2   // `break;` stops the loop at 3
1 2 4 5   // `continue;` skips 3 but continues
```

### **When to Use `continue;`?**

✔ **Skip specific iterations** (e.g., skip processing invalid input).  
✔ **Filter values** (e.g., skip negative numbers in a loop).  
✔ **Avoid unnecessary processing** inside loops.

---
# **3) `goto`**

The `goto` statement is used to **jump** from one part of a program to another using labels. It **transfers control** directly to a labeled statement instead of following the normal sequential flow.

**Note:** `goto` is **not recommended** because it makes code harder to read and debug (spaghetti code). However, it can be useful in some rare cases, like breaking out of deeply nested loops.
### **Syntax of `goto`**

```c
goto label;
...
label: 
// Code to execute after the jump
```


### **Example: Basic `goto` Usage**

```c
#include <stdio.h>

int main() {
    printf("Start\n");
    goto skip;  // Jumps to the "skip" label

    printf("This will be skipped.\n");

    skip:
    printf("Jumped here using goto.\n");

    return 0;
}
```

**Output:**

```
Start
Jumped here using goto.
```

(The line `"This will be skipped."` is **never executed** because `goto` jumps over it.)

### **Example: Breaking Out of Nested Loops Using `goto`**

`goto` can be used to **exit multiple loops at once**, which `break;` cannot do easily.

```c
#include <stdio.h>

int main() {
    for (int i = 1; i <= 3; i++) {
        for (int j = 1; j <= 3; j++) {
            if (i == 2 && j == 2) {
                goto exit_loops;  // Jump out of both loops
            }
            printf("i = %d, j = %d\n", i, j);
        }
    }
exit_loops:
    printf("Exited loops.\n");
    return 0;
}
```

**Output:**

```
i = 1, j = 1
i = 1, j = 2
i = 1, j = 3
i = 2, j = 1
Exited loops.
```

(Execution **jumps out** when `i == 2` and `j == 2`, skipping further iterations.)
#### **Example: Error Handling with `goto`**

```c
#include <stdio.h>

int main() {
    FILE *file = fopen("data.txt", "r");

    if (!file) {
        printf("Error: File not found!\n");
        goto cleanup;
    }

    // Process the file...
    printf("Processing file...\n");

cleanup:
    printf("Exiting program.\n");
    return 0;
}
```

(If the file is not found, `goto cleanup;` ensures the program exits gracefully.)

#### **Example: making infinite loop:**

```c
#include <stdio.h> 
int main() { printf("Start\n"); 
	skip: // Jumps to the "skip" label 
		printf("This will be skipped.\n"); 
	goto skip; 
		printf("Jumped here using goto.\n"); 
	return 0; 
}
```
Hence we can make infinite loop using goto function!
### **Alternatives to `goto`**

Instead of `goto`, you can use: ✔ **Break statements** (for exiting loops).  
✔ **Function returns** (to exit early).  
✔ **Boolean flags** (to control execution flow).

---
# **4) `return` 

The `return` statement is used in functions to **exit** and optionally send a value back to the caller. It **immediately stops** the execution of the function and returns control to the calling function.

## **1️⃣ Syntax of `return`**

```c
return;          // Used in `void` functions (no value returned)
return value;    // Used in functions that return a value
```


## **2️⃣ `return` in `void` Functions**

Functions with `void` return type do not return a value, but you can still use `return;` to exit early.

### **Example: `return` in `void` Function**

```c
#include <stdio.h>

void checkNumber(int num) {
    if (num < 0) {
        printf("Negative number! Exiting function.\n");
        return;  // Exits function early
    }
    printf("Number is: %d\n", num);
}

int main() {
    checkNumber(-5);
    checkNumber(10);
    return 0;
}
```

**Output:**

```
Negative number! Exiting function.
Number is: 10
```

(For `-5`, the function exits early, skipping the second `printf`.)


## **3️⃣ `return` in Functions That Return a Value**

If a function **expects a return value**, you must return the correct data type.

### **Example: `return` in an `int` Function**

```c
#include <stdio.h>

int square(int num) {
    return num * num;  // Returns the square of num
}

int main() {
    int result = square(5);
    printf("Square: %d\n", result);
    return 0;
}
```

**Output:**

```
Square: 25
```

(The function `square(5)` returns `25`, which is stored in `result`.)


## **4️⃣ `return` in `main()` Function**

In `main()`, `return 0;` usually indicates that the program ran successfully.

### **Example: `return` in `main()`**

```c
int main() {
    return 0;  // Exits the program
}
```

Most operating systems interpret `return 0;` as **successful execution**. Other return values can indicate errors.


## **5️⃣ Multiple `return` Statements**

A function can have multiple `return` statements, but only one executes at a time.

### **Example: Early `return` in a Function**

```c
#include <stdio.h>

int max(int a, int b) {
    if (a > b)
        return a;  // If a is greater, return a
    return b;      // Otherwise, return b
}

int main() {
    printf("Max: %d\n", max(10, 20));
    return 0;
}
```

**Output:**

```
Max: 20
```

(The function **returns early** if `a > b`, otherwise, it returns `b`.)

## **6️⃣ `return` vs `exit()`**

|Feature|`return`|`exit()`|
|---|---|---|
|Used in|Functions|Entire program|
|Where it returns|Calling function|OS|
|Can be used in `main()`?|Yes|Yes|
|Recommended?|Yes, in functions|Only when needed|

### **Example: `exit()` Usage**

```c
#include <stdio.h>
#include <stdlib.h>  // Required for exit()

int main() {
    printf("Exiting program.\n");
    exit(0);  // Exits program immediately
}
```

**Difference:** `exit(0);` **stops the entire program**, while `return` just returns from the function.

## **7️⃣ What Happens If You Forget `return`?**

If a function **should** return a value but does not, it may lead to **undefined behavior**.

### **Incorrect Example: Missing `return`**

```c
int add(int a, int b) {
    int sum = a + b;
}  // No return statement!
```

🚨 **Error:** Some compilers will throw a warning or an error.
