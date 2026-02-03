**Expected to know:** [[Data Types and Constants]] [[ASCII]]
**Topics Covered:** char, string, ASCII relation
**Tags:** [[Data Types and Constants]] 

We humans also use words instead of numbers, but computers only understand binary number system, so in order to resolve this, a special data type was create called "Character Type", which relates each character/symbol with a special number.

And in order to maintain uniformity, we use something called ASCII in order to maintain it.

# **1) Char**
- Used for storing single characters (e.g., 'a', 'Z', '9').
    - Also used to store small integer values.(*as ASCII values*)
    - can also be modified by signed, and unsigned.
-  Size: 1 byte (8 bits)
    - `signed char`: -128 to 127
    - `unsigned char`: 0 to 255

# **2) String**

When it comes to writing words, we cannot use a char, we need something that can store a collection of characters. This is where we use String. 

This is not a primitive data type, and is not offered by the C programming language, but is offered in the standard directory of other languages like python, C#, C++, F#, etc.
This uses the concept of Arrays which we shall see later.

When it comes to characters, we have something special called Special Characters or Escape Sequences which can be used for handling string related tasks.

---
# Escape Characters

When working with strings in programming, _special characters_—also known as _escape sequences_—are essential tools for handling cases where certain characters cannot be used directly within string literals. These sequences allow programmers to include characters like quotes, backslashes, newlines, and tabs, which would otherwise cause errors or be misinterpreted by the compiler or interpreter.

## Escape Sequence
An **escape sequence** is a combination of characters, typically starting with a backslash (`\`), that represents a character with a special meaning rather than its literal form. The backslash signals to the language that the following character(if a special character) should be interpreted differently.

| Escape Sequence | Name               | Description                                                                            |
| --------------- | ------------------ | -------------------------------------------------------------------------------------- |
| \a              | Alarm or Beep      | It is used to generate a bell sound in the C program.                                  |
| \b              | Backspace          | It is used to move the cursor one place backward.                                      |
| \f              | Form Feed          | It is used to move the cursor to the start of the next logical page.                   |
| \n              | New Line           | It moves the cursor to the start of the next line.                                     |
| \r              | Carriage Return    | It moves the cursor to the start of the current line.                                  |
| \t              | Horizontal Tab     | It inserts some whitespace to the left of the cursor and moves the cursor accordingly. |
| \v              | Vertical Tab       | It is used to insert vertical space.                                                   |
| \\              | Backlash           | Use to insert backslash character.                                                     |
| \'              | Single Quote       | It is used to display a single quotation mark.                                         |
| \"              | Double Quote       | It is used to display double quotation marks.                                          |
| \?              | Question Mark      | It is used to display a question mark.                                                 |
| \ooo            | Octal Number       | It is used to represent an octal number.                                               |
| \xhh            | Hexadecimal Number | It represents the hexadecimal number.                                                  |
| \0              | NULL               | It represents the NULL character.                                                      |
Out of all these escape sequences, \n and \0 are used the most. In fact, escape sequences like \f, \a,  are not even used by programmers nowadays.