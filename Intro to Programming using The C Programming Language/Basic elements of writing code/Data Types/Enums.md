**Expected to know:**
**Topics Covered:**

## What is an Enum?

Enumeration is a custom user defined data type, needed when we want to choose from a limited amount of things.  
An _enumeration_ or an _enumerated type_ is a type whose choices are one of a small list of possible options. The verb enumerate means “to list off things, one by one,” hence the name. We can define new enumerations in our code to represent concepts of this nature.
It is a user-defined data type that consists of a set of named integer constants, providing a way to assign symbolic names to integral values. This improves code readability and maintainability by replacing "magic numbers" with meaningful identifiers.

## Syntax and Declaration
## Basic Syntax
```c
enum EnumName 
{     
	CONSTANT1,    
	CONSTANT2,    
	CONSTANT3,    // ... 
};
```
- `EnumName` is optional but recommended for clarity.
    
- Each constant is called an **enumerator**[1](https://www.w3schools.com/c/c_enums.php)[2](https://www.simplilearn.com/tutorials/c-tutorial/enum-in-c)[5](https://www.programiz.com/c-programming/c-enumeration).
    

## Example

c

`enum Level {     LOW,    MEDIUM,    HIGH };`

By default, `LOW` is 0, `MEDIUM` is 1, and `HIGH` is 2[1](https://www.w3schools.com/c/c_enums.php)[2](https://www.simplilearn.com/tutorials/c-tutorial/enum-in-c)[3](https://www.codecademy.com/resources/docs/c/enums).

## Assigning Custom Values

You can explicitly assign integer values to any enumerator. Unassigned enumerators continue incrementally from the previous value[2](https://www.simplilearn.com/tutorials/c-tutorial/enum-in-c)[5](https://www.programiz.com/c-programming/c-enumeration).

c

`enum Status {     OK = 200,    CREATED = 201,    BAD_REQUEST = 400,    NOT_FOUND = 404 };`

- `OK` is 200, `CREATED` is 201, `BAD_REQUEST` is 400, `NOT_FOUND` is 404[3](https://www.codecademy.com/resources/docs/c/enums)[5](https://www.programiz.com/c-programming/c-enumeration).
    

## Using Enums

## Declaring Variables

c

`enum Level myLevel; myLevel = MEDIUM;`

- Enum variables can only take values defined in the enum[1](https://www.w3schools.com/c/c_enums.php)[2](https://www.simplilearn.com/tutorials/c-tutorial/enum-in-c).
    

## Printing Enum Values

Enum values are stored as integers, so you can print them directly:

c

`printf("%d", myLevel); // Outputs 1 if myLevel = MEDIUM`

## Initialization and Value Rules

- If no value is assigned, the first enumerator is 0, the next is 1, and so on[1](https://www.w3schools.com/c/c_enums.php)[2](https://www.simplilearn.com/tutorials/c-tutorial/enum-in-c)[5](https://www.programiz.com/c-programming/c-enumeration).
    
- You can assign the same value to multiple enumerators[2](https://www.simplilearn.com/tutorials/c-tutorial/enum-in-c).
    
- Enumerators must be integral constants within the range of an `int`[2](https://www.simplilearn.com/tutorials/c-tutorial/enum-in-c)[5](https://www.programiz.com/c-programming/c-enumeration).
    
- You can assign values in any order; unassigned enumerators take the previous value + 1[2](https://www.simplilearn.com/tutorials/c-tutorial/enum-in-c)[6](https://publications.gbdirect.co.uk/c_book/chapter6/enums.html).
    

## Scope and Type

- Enum names and enumerators have scope rules similar to other identifiers in C[6](https://publications.gbdirect.co.uk/c_book/chapter6/enums.html).
    
- Enum variables are of type `enum EnumName`, but the enumerators themselves act as constants of type `int`[5](https://www.programiz.com/c-programming/c-enumeration)[6](https://publications.gbdirect.co.uk/c_book/chapter6/enums.html).
    
- Enum constants leak into the enclosing scope, so their names must be unique within that scope[6](https://publications.gbdirect.co.uk/c_book/chapter6/enums.html).
    

## Memory Size and Storage

- The size of an enum variable is implementation-defined, typically the size of an `int`, but it can be smaller or larger depending on the range of values[7](https://embedded.fm/blog/2016/6/28/how-big-is-an-enum)[5](https://www.programiz.com/c-programming/c-enumeration).
    
- You can check the size using `sizeof(enum EnumName)`[7](https://embedded.fm/blog/2016/6/28/how-big-is-an-enum).
    

## Best Practices

- Prefer enums over `#define` for better type safety and readability[8](https://linuxhaxor.net/code/use_enum_c_language.html).
    
- Group related constants into a single enum[8](https://linuxhaxor.net/code/use_enum_c_language.html).
    
- Use enums as function parameters for clarity and type safety[8](https://linuxhaxor.net/code/use_enum_c_language.html).
    
- Initialize variables to a known value (often the first enumerator)[8](https://linuxhaxor.net/code/use_enum_c_language.html).
    
- Use enums for state machines, error/status codes, flags, and command sets[8](https://linuxhaxor.net/code/use_enum_c_language.html).
    
- For bit flags, assign values as powers of two to allow bitwise operations[5](https://www.programiz.com/c-programming/c-enumeration)[8](https://linuxhaxor.net/code/use_enum_c_language.html).
    

## Advanced Features

## Bit Flags with Enums

Enums can be used to represent bit flags:

c

`enum DesignFlags {     ITALICS = 1,    BOLD = 2,    UNDERLINE = 4 }; int style = BOLD | UNDERLINE; if (style & ITALICS) { /* ... */ }`

This allows combining multiple flags using bitwise OR[5](https://www.programiz.com/c-programming/c-enumeration)[8](https://linuxhaxor.net/code/use_enum_c_language.html).

## Enums in Structs and Unions

Enums can be embedded in structs or unions for modeling complex data:

c

`struct Button {     enum DesignFlags style;    // ... };`

## Enum Limitations

- Only integer values are allowed; enums cannot hold floats or strings[4](https://www.shiksha.com/online-courses/articles/enum-in-c-best-practices-and-examples/).
    
- No type safety for enum variables; they can be assigned any integer, though compilers may warn[8](https://linuxhaxor.net/code/use_enum_c_language.html).
    
- Enum values are not namespaced (unless inside a struct or in C++ with `enum class`)[6](https://publications.gbdirect.co.uk/c_book/chapter6/enums.html).
    

## Comparison with Other Constants

|Feature|Enum (`enum`)|Macro (`#define`)|
|---|---|---|
|Type Safety|Yes (enum type)|No|
|Scope|Follows C scope rules|File scope (global)|
|Debug Support|Symbolic names in debuggers|Only numbers|
|Readability|High|Low|
|Usage|Grouped, related constants|Any constant|

[1](https://www.w3schools.com/c/c_enums.php)[2](https://www.simplilearn.com/tutorials/c-tutorial/enum-in-c)[8](https://linuxhaxor.net/code/use_enum_c_language.html)

## Typical Use Cases

- Days of the week, months, directions, states, error codes, flags, command sets, and more[3](https://www.codecademy.com/resources/docs/c/enums)[5](https://www.programiz.com/c-programming/c-enumeration)[8](https://linuxhaxor.net/code/use_enum_c_language.html).
    

