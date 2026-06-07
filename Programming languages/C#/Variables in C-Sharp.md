**Expected to know:** [[Variables and their scope]], [[Syntax of C-sharp]]
**Topics Covered:**
**Tags:** [[Variables and their scope]]

![[Syntax of C-sharp#**Variables**]]

Variables here in C# have the same declaration syntax as that of The C Programming language:
```c
DataType VariableName;
```
As for Constants, the syntax is same:
```c
const dataType variableName;
```

---
# Naming Variables and constants;
Now that we have seen how a variable is declared and assigned a value to, lets discuss naming them.

The rules for identifiers are generalized.

However, there is a common practice used in C# by the developers when naming things, seems useless but might be helpful to recall a lot of syntax in C# when one knows a lot of languages!
## Variables
For variables, we use something called *camelCase*, i.e., each variable's name starts with a small letter, and all the words that follows it starts with CAPITAL letter.
## Constants
For constants, we use something called *PascalCase* in which all the words start with a capital letter(including the first one).

---
# Accessing the Variables

Variables are accessed by value in C# by default. So when you access a variable, here’s what the computer does:
1. Locates the variable that you asked for in memory.
2. Looks in the contents of the variable to see what value it contains.
3. Makes a copy of that value to use where it is needed.
