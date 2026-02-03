**Expected to know:** [[Data Types and Constants]] (Only Primitive), [[Introduction to Programming]] (Till now)
**Topics Covered:** What are variables, constants, [[Identifier]], macros
**Tags:** [[Introduction to Programming]]

Variables are fundamental building blocks in almost every programming language. Think of them as **labeled containers** that hold information (data) in your computer's memory. You can put different kinds of information into these containers, and you can change the information inside as your program runs.

Here's a breakdown of variables for general programming:

# **What are variables?**

- **Named Storage Locations:** A variable is a named place in your computer's memory where you can store a value. The name is how you refer to that specific memory location.
- **Hold Data:** Variables hold data of a specific type (like numbers, text, or true/false values). The data type determines what kind of information the variable can store.
- **Values Can Change:** The "variable" part means that the value stored in a variable can change as your program runs. This is what makes programs dynamic and able to react to different situations.

# **How do you use variables?**

1. **Declaration:** 
	- Before you can use a variable, you need to declare it. 
	- This means telling the computer that you want to create a new variable, it's data type and giving it a name. 
	- The syntax for declaring a variable varies slightly between languages, but it generally involves specifying the data type and the variable name.
    - Example (Python): `age = 25` (This declares a variable named `age` that will store an integer.)
    - Example (JavaScript): `let name = "Alice";` (This declares a variable named `name` that will store a string.)
    - Example (C++): `int score;` (This declares a variable named `score` that will hold an integer. You would usually initialize it in a separate step.)

2. **Initialization (Optional, but good practice):** When you declare a variable, you can also give it an initial value. This is called initialization. It's good practice to initialize variables when you declare them to avoid unexpected behavior later.
    
    - Example (Python): `count = 0` (Declares `count` and sets its initial value to 0.)
    - Example (JavaScript): `let message = "Hello!";` (Declares `message` and sets its initial value to "Hello!")
    - Example (C++): `int score = 100;` (Declares `score` and sets its initial value to 100.)

3. **Assignment:** To put a value into a variable (or to change the value that's already there), you use the assignment operator (usually `=`). Don't  confuse this with initialization, as initialization happens when we are first creating a variable
    
    - Example (Python): `age = 30` (Changes the value of `age` to 30.)
    - Example (JavaScript): `name = "Bob";` (Changes the value of `name` to "Bob".)
    - Example (C++): `score = score + 50;` (Increases the value of `score` by 50.)
    
4. **Using Variables:** Once a variable has a value, you can use it in your code. You can use it in calculations, comparisons, or to display information.
    
    - Example (Python): `print("Your age is:", age)` (Displays the value of `age`.)
    - Example (JavaScript): `let finalScore = score * 2;` (Calculates a final score using the value of `score`.)
    - Example (C++): `if (age >= 18) { cout << "You are an adult."; }` (Checks if the value of `age` is greater than or equal to 18.)

# Naming Variables (Identifiers):

- **Rules:** Most languages have rules about what characters you can use in variable names. Usually, you can use letters (a-z, A-Z), numbers (0-9), and underscores . Variable names often can't start with a number.
- **Conventions:** It's good practice to use descriptive names that make your code easier to understand. For example, `numberOfStudents` is a better name than `nos`. Many languages also have naming conventions (e.g., camelCase, snake_case) that are commonly used.


**Key Points:**
- Variables are essential for storing and manipulating data in your programs.
- Every variable has a name and a data type.
- The value of a variable can change during program execution.
- Use meaningful names for your variables to make your code more readable.

Understanding variables is one of the first and most important steps in learning any programming language. They are the foundation upon which you'll build more complex programs.

# Scope
Every variable declared in a program cannot be accessed everywhere in the program.
A program consists of blocks:
![[Syntax#**4. Code Blocks (Braces) **]]
And depending upon:
- Where it is declared (inside a function, block, or globally)
- The keyword used to declare it (`var`, `let`, `const` in JavaScript; type name in C/Java)
- Language-specific rules
The **Scope**(I.e., the area where the function can be accessed) of the variable is decided.

There are different types of scopes:
- **Global Scope**
	- A variable is said to have a global scope when it can be accessed everywhere in the script
	- The region where the variable must be declared in so that it becomes global scope can depend upon the programming language, but in The C Programming Language, the variable declared outside the main function with the headers grants the variable a global scope.
- **Local Scope/Function Scope**
	- A variable is said to have a local scope if it is not accessible outside the function block it is defined into.
- **Block Scope**
	- Refers to variables that are only accessible within the nearest enclosing curly braces `{}`—such as in an `if`, `for`, or `while` statement, or a standalone block.
	
	But wait, function is also uses `{}` i.e., a block, so variables declared inside it are also a block scope as they are not accessible outside the function block?
	No, there is a huge difference in function scope and block scope. Function Scope refers specifically to variables that are accessible anywhere within the function in which they are declared, regardless of whether they are inside a nested block within that function, while block scope means stuff that are accessible inside the nearest block they are defined into!!!
	
	**In the C programming language, variable scope is determined entirely by their placement within blocks (curly braces `{}`), and there are no special scope-defining keywords like in JavaScript (`let`, `const`, `var`) or C# (`var`, `dynamic`).**
- **Enclosed (or Enclosing) Scope**
	- **Definition:** Applies to nested functions, where an inner function can access variables from its outer (enclosing) function.
	- **Visibility:** Inner (nested) functions can access variables from their parent function's scope.
	- **Example:** Common in languages that support closures, such as Python and JavaScript.
-  **Built-in or Module Scope**
	- **Definition:** Refers to variables and functions that are pre-loaded into the program by the language runtime or imported modules.
	- **Visibility:** Available everywhere in the program unless shadowed by local definitions.
	- **Example:** Python's built-in functions like `len()`, or constants in a standard library.
- **Static Scope**
	- **Definition:** Variables declared as `static` inside a function retain their value across multiple calls but are still only accessible within that function.
	- **Use case:** Useful for maintaining state between function calls (e.g., counting how many times a function has been called).
	- **Example:** Common in PHP, C, and C++.
- **Namespace and Class Scope (Object-Oriented Languages)**
	- **Namespace Scope:** Variables or functions declared within a namespace are only accessible within that namespace.
	- **Class Scope:** Variables (attributes) and methods defined inside a class are accessible to all members of that class, and sometimes its subclasses, depending on access modifiers