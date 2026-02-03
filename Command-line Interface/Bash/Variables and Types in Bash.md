**Expected to know: ** [[Variables and their scope]], [[Syntax of Bash]]
**Topics Covered:** [[#**How Variables Are Created in Bash**]]
**Tags: ** [[Variables and their scope]] 

![[Syntax of Bash#**4. Variables**]]

Lets keep a layer of abstraction on how it all works for now, as bash being a shell works closely with the architecture of the computer, a topic that would be too advanced to touch for now.

## Variables and Types in Bash
If you come from a C programming background, Bash variables and their handling will feel quite different. Bash is a dynamically typed shell language, and its variables are fundamentally more flexible but less strictly defined than in C.

---
## **How Variables Are Created in Bash**

- **Declaration:** Variables are created simply by assigning a value to a name. No type or keyword is needed.
    
- **Syntax:**
    
```bash
variable_name=value
```
_No spaces_ are allowed around the `=` sign.
Example:
```bash
name="Alice" 
number=42
```
    
- **Accessing Variables:**  
    Use a `$` prefix to access the value:
```bash
echo $name
```

---
## **Variable Naming Rules**

- Must start with a letter or underscore (`_`), not a number.
- Can contain letters, numbers, and underscores.
- Are case-sensitive (`myVar` and `MYVAR` are different).
- Should not contain spaces or special characters.

---
## **Types of Variables in Bash**

Bash variables are **untyped**—they are always stored as strings, but can be used as numbers if the context requires it.

### **Categories of Variables**
- **User-defined variables:** Created by users in scripts or at the command line.
- **System-defined (environment) variables:** Predefined by the OS, usually in uppercase (e.g., `PATH`, `HOME`).

## **Special Variables**
Bash provides special variables for script metadata and argument handling, such as `$0`, `$1`, `$@`, `$#`, `$$`, `$?`, etc..
This is what we call Positional arguments in Bash, something given in with the command.
We know how the command is structured:
```text
command argumentA argumentB argumentC ...
```
So, the positional variables for the given arguments goes like:
```text
$0 $1 $2 $3....
```
Where `$0` is for the command itself, reserved for the bash to execute, and from `$1`, we can access the arguments provided like `$1` for `argumentA`, `$2` for `argumentB`, and so on. 

---
## **How Bash Handles Types**
- **No Explicit Types:** Unlike C, you do not declare types (e.g., `int`, `char`, `float`).
- **Everything is a String:** All variables are strings by default[5](https://tldp.org/LDP/abs/html/untyped.html)[6](https://codedamn.com/news/linux/working-with-variables-in-bash).
- **Contextual Typing:** If a variable contains only digits and is used in an arithmetic context, Bash treats it as a number[5](https://tldp.org/LDP/abs/html/untyped.html)[6](https://codedamn.com/news/linux/working-with-variables-in-bash).
    
    Example:
    
    bash
    
    `num1=5 num2=10 sum=$((num1 + num2))   # sum is 15`
    
    If you use the variable in a string context, it's just a string:
    
    bash
    
    `greeting="Hello, " name="World" echo "$greeting$name"  # Hello, World`
    
- **Explicit Integer Declaration:**  
    You can use `declare -i` to tell Bash to treat a variable as an integer, but it’s still not a strict type system[5](https://tldp.org/LDP/abs/html/untyped.html):
    
    bash
    
    `declare -i x=10 x=x+5   # x is now 15`
    
- **Type Conversion:**  
    Bash will attempt to convert strings to numbers in arithmetic contexts, but non-numeric strings become zero or cause errors[5](https://tldp.org/LDP/abs/html/untyped.html).
    

---

## **Local vs. Global Variables**

- **Global:** By default, variables are global within the script.
    
- **Local:** Use `local` keyword inside functions to limit scope[1](https://www.w3schools.com/bash/bash_variables.php)[2](https://tldp.org/HOWTO/Bash-Prog-Intro-HOWTO-5.html).
    
    Example:
    
    bash
    
    `myfunc() {   local var="local value"  echo $var }`
    

---

## **Variable Expansion and Quoting**

- **$var** and **${var}**: Both expand the variable; braces are used to avoid ambiguity (e.g., `${var}suffix`)[3](https://stackoverflow.com/questions/18135451/what-is-the-difference-between-var-var-and-var-in-the-bash-shell).
    
- **"$var"**: Quotes prevent word splitting and preserve spaces in the value[3](https://stackoverflow.com/questions/18135451/what-is-the-difference-between-var-var-and-var-in-the-bash-shell).
    

---

## **Environment Variables**

- **Exporting:** Use `export` to make a variable available to child processes:
    
    bash
    
    `export MYVAR="value"`
    
- **Access:** Environment variables are inherited by subprocesses, user-defined variables are not unless exported[1](https://www.w3schools.com/bash/bash_variables.php)[7](https://www.hostinger.com/in/tutorials/bash-variables).
    

---

## **Arrays**

Bash supports arrays, but they are less commonly used than in C. Arrays are declared as:

bash

`arr=(one two three) echo ${arr[0]}   # one`

---

## **Key Differences from C**

|Feature|C Language|Bash Shell|
|---|---|---|
|Type System|Strong, static|Untyped, dynamic|
|Declaration|Required (`int x;`)|Not required (`x=5`)|
|Variable Scope|Block/function-based|Global by default, `local` in functions|
|Numeric Handling|Strictly typed|Contextual, string by default|
|Arrays|Typed, fixed-size|Untyped, flexible|

---

## **Summary**

- Bash variables are always strings unless used in arithmetic contexts.
    
- No explicit type declaration—Bash decides how to interpret variables based on usage.
    
- Variables are created by assignment, no keyword needed.
    
- Use `local` for function-local variables, and `export` for environment variables.
    
- Quoting and braces help control expansion and parsing.
    
- Bash's flexibility is powerful, but requires careful handling to avoid subtle bugs.
    

This dynamic, untyped approach is very different from C’s strict, static typing, so always be mindful of how values are used and expanded in your scripts[1](https://www.w3schools.com/bash/bash_variables.php)[2](https://tldp.org/HOWTO/Bash-Prog-Intro-HOWTO-5.html)[4](https://www.freecodecamp.org/news/bash-scripting-tutorial-linux-shell-script-and-command-line-for-beginners/)[5](https://tldp.org/LDP/abs/html/untyped.html)[6](https://codedamn.com/news/linux/working-with-variables-in-bash)[7](https://www.hostinger.com/in/tutorials/bash-variables).

### Citations:

1. [https://www.w3schools.com/bash/bash_variables.php](https://www.w3schools.com/bash/bash_variables.php)
2. [https://tldp.org/HOWTO/Bash-Prog-Intro-HOWTO-5.html](https://tldp.org/HOWTO/Bash-Prog-Intro-HOWTO-5.html)
3. [https://stackoverflow.com/questions/18135451/what-is-the-difference-between-var-var-and-var-in-the-bash-shell](https://stackoverflow.com/questions/18135451/what-is-the-difference-between-var-var-and-var-in-the-bash-shell)
4. [https://www.freecodecamp.org/news/bash-scripting-tutorial-linux-shell-script-and-command-line-for-beginners/](https://www.freecodecamp.org/news/bash-scripting-tutorial-linux-shell-script-and-command-line-for-beginners/)
5. [https://tldp.org/LDP/abs/html/untyped.html](https://tldp.org/LDP/abs/html/untyped.html)
6. [https://codedamn.com/news/linux/working-with-variables-in-bash](https://codedamn.com/news/linux/working-with-variables-in-bash)
7. [https://www.hostinger.com/in/tutorials/bash-variables](https://www.hostinger.com/in/tutorials/bash-variables)
8. [http://www.compciv.org/topics/bash/variables-and-substitution/](http://www.compciv.org/topics/bash/variables-and-substitution/)
9. [https://rowannicholls.github.io/bash/intro/variables.html](https://rowannicholls.github.io/bash/intro/variables.html)
10. [https://stackoverflow.com/questions/74181676/how-do-bash-variable-types-work-and-how-to-work-around-automatic-interpretation](https://stackoverflow.com/questions/74181676/how-do-bash-variable-types-work-and-how-to-work-around-automatic-interpretation)

---

Answer from Perplexity: [pplx.ai/share](https://www.perplexity.ai/search/pplx.ai/share)