**Expected to know:** [[Functions]]
**Topics Covered:**
**Tags:**

![[Syntax of Bash#**8. Functions**]]

## Functions in Bash: The Absolute Basic
A **function** in Bash is a reusable block of code that you can define once and call (run) multiple times in your script. Functions help organize your script, avoid repetition, and make complex scripts easier to manage.

---
## **How to Define a Function**
There are two main ways to define a function in Bash:

**1. Standard Syntax (most common):**

```text
my_function() 
{   
	# commands go here 
}
```

**2. Using the `function` keyword (alternative style):**

```bash
function my_function 
{   
	# commands go here 
}
```

- Both styles work the same way. Parentheses are optional when using the `function` keyword.

---
## **How to Call (Use) a Function**
Once defined, call the function by simply writing its name:

```bash
my_function
```
- You can call a function as many times as you want in your script.

---
## **Simple Example**

bash

`greet() {   echo "Hello, World!" } greet   # Calls the function and prints "Hello, World!"`

---

## **Functions with Arguments**

You can pass values (arguments) to a function. Inside the function, arguments are accessed as `$1`, `$2`, etc.

bash

`say_hello() {   echo "Hello, $1!" } say_hello Alice   # Prints: Hello, Alice! say_hello Bob     # Prints: Hello, Bob!`

- `$1` is the first argument, `$2` is the second, and so on[1](https://phoenixnap.com/kb/bash-function)[3](https://www.w3schools.com/bash/bash_functions.php)[5](https://www.baeldung.com/linux/bash-functions).
    

---

## **Returning Values from Functions**

- Bash functions don’t return values like in other languages, but you can use `echo` to output a value and capture it with command substitution:
    
    bash
    
    `add() {   echo $(($1 + $2)) } result=$(add 5 3) echo "Sum is $result"  # Prints: Sum is 8`
    
- You can also use the `return` statement to return an exit status (an integer between 0 and 255), but this is mostly used for success/failure checks, not for returning data[3](https://www.w3schools.com/bash/bash_functions.php).
    

---

## **Local Variables in Functions**

- Use the `local` keyword to make a variable local to the function (it won’t affect variables outside the function):
    
    bash
    
    `my_func() {   local temp="I am local"  echo $temp } my_func  # Prints: I am local`
    

---

## **Summary Table**

|Feature|Example Syntax|Description|
|---|---|---|
|Define function|`my_func() { ... }`|Standard way to define a function|
|Call function|`my_func`|Runs the code inside the function|
|With arguments|`$1`, `$2`, ...|Access arguments inside the function|
|Return value|`echo`, `result=$(func ...)`|Output and capture function results|
|Local variable|`local var=value`|Limits variable scope to the function|

---

**In short:**  
A Bash function is a named block of code you define with `function_name() { ... }` or `function function_name { ... }`. Call it by name, pass arguments, use variables, and reuse code easily in your scripts[1](https://phoenixnap.com/kb/bash-function)[2](https://linuxize.com/post/bash-functions/)[3](https://www.w3schools.com/bash/bash_functions.php)[5](https://www.baeldung.com/linux/bash-functions).

