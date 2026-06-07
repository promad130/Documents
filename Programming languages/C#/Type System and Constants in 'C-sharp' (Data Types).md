;**Expected to know:** [[Data Types and Constants]], [[Variables in C-Sharp]]
**Topics Covered:** [[#Escape Characters]], [[#String Interpolation]] 
**Tags:** [[Data Types and Constants]]

Use [[Data types in C-Sharp]] for completing these and delete it after that.

***Remember that you don’t need to master it all in one reading. Get a feel for what types are available, and come back for the details later***

![[Syntax of C-sharp#Data types]]

# Data Type Modifier

Data types modifier in C# is same as that in The C Programming Language. But the syntax is different.
- `Signed DataType`:
	- Signed means both negative and positive values are included
	- This is written as `sDataType` in C# instead of `signed DataType`.
- `Unsigned DataType`:
	- Unsigned means only positive numbers(including 0)
	- This is written as `uDataType` 

`Byte` by default is unsigned, and can never be signed.
For **Data Type Modifiers**, we just write what the modifier syntax after the number before writing anything else to distinguish it.
Like: `ulong BinOne = 2313153131325223UL;`

---
# More info about Data Types in C#:

## **`float` Type:

It consumes 4 bytes of data and provides with precision of 7 digits, with largest value that can be stored being $3.4\times 10^{38}$. Hence, due to a 7 digit precision, it can't tell the difference between `1,000,000,000` and `1,000,000,001`

There is also a limit to how small (as in, “close to zero”) the float type can store, and that is about $±1.5\times 10^{-45}$ — a very tiny number.

The float constant in C# always has to end with the letter `f`(case doesn't matter), (like the **literal** `1.2f` is considered as float) for the compiler to know that it is a float, otherwise it is assumed to be `double`.

## **`double` Type:

Consumes 8 bytes of space, offering more precision, i.e, a 15 or 16 digits of precision, and can store values as small as $\pm5\times 10^{-324}$ to $\pm1.7\times 10^{308}$.

Any number written with a decimal point in C# is considered as a double if it follows nothing, like the **literal** `1.2665` is considered as a double.

---
## Integer Types

Well we know what `int` and `long` are in C#, however, any literal written like this `25646552` would be considered as an int by the compiler. For `long`, we have to write `l` or `L` following it(`L` preferred).

For **Data Type Modifiers**, we just write what the modifier syntax after the number before writing anything else to distinguish it.
Like: `ulong BinOne = 2313153131325223UL;`

### Binary Literals
We write integers in term of binary instead of numbers sometimes as it might be easy to work with.
In order to do that, we start by writing `0b` followed by the binary number.
`int a = 0b0110010;`. No need to pad the number with leading 0's.

### Hexadecimal literals
We can also write integers in terms of hexadecimal, as sometimes we might work with colors!
For that, we preface the hex number with `0x`:

`int theColorMagenta = 0xFF00FF;`
(The Case does not matter in this.)


## **`decimal` Type:

When it is money we are calculating, we can't have any errors, hence we needed a data type with higher precision, hence we have `decimal` which has the range of $\pm1.0\times 10^{-28}\text{ to }\pm7.9\times 10^{28}$.
Quite small in comparison, but offers the precision of 28 to 29 digits.

You can create variables using the decimal type in a way that is very similar to all of the other types that we’ve talked about. 
Similar to the float type, you’ll need to put an ‘m’ or ‘M’ at the end of the literals.

```csharp
decimal number = 1.495m;
number = 14.4m;
```

## Strings and characters
We have seen the syntax and initialization of string in [[Syntax of C-sharp]],char same as that in The C Programming language.
### String Interpolation
It’s common when you display something to the user to mix together text with some code. For example:
```Csharp
Console.WriteLine("The cylinder's volume is: " + volume + " cubic units.");
```
String interpolation does this, but in a more readable manner:
```csharp
Console.WriteLine($"The cylinder's volume is:\t{volume} cubic units.");
```
It starts with `$` sign, and then the variables to include are enclosed by `{}`.
#### Alignment:
We can align letter, i.e., have padding for them in C# while using interpolation by adding the amount of padding after the variable name using a `,`:
```csharp
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Diagnostics.CodeAnalysis;

namespace Practice1
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine(@"HH\i \there,\ how\ are\ you???\?");
            string Name1 = Console.ReadLine();
            string Name2 = Console.ReadLine();
            Console.WriteLine($"{Name1,50}: Is the Name A");
            Console.WriteLine($"{Name2,50}: Is the Name B");
        }
    }
}
```
Output 1:
```text
HH\i \there,\ how\ are\ you???\?
ThereIsATotalOf50LettersOfAllignementAvailableHere
ThereIsATotalOf50LettersOfAllignementAvailableHere
ThereIsATotalOf50LettersOfAllignementAvailableHere: Is the Name A
ThereIsATotalOf50LettersOfAllignementAvailableHere: Is the Name B
```
Output 2:
```text
HH\i \there,\ how\ are\ you???\?
helo
helo
                                              helo: Is the Name A
                                              helo: Is the Name B
```
So as you can see, a positive number gives a padding of given amount of letter to the left of the given variable, i.e, it reserves that much amount of space for the variable.

And hence, the negative number reserves the space for the variable to the right of the variable
```csharp
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Diagnostics.CodeAnalysis;

namespace Practice1
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine(@"HH\i \there,\ how\ are\ you???\?");
            string Name1 = Console.ReadLine();
            string Name2 = Console.ReadLine();
            Console.WriteLine($"{Name1,-50}: Is the Name A");
            Console.WriteLine($"{Name2,-50}: Is the Name B");
        }
    }
}
```
```text
HH\i \there,\ how\ are\ you???\?
helo
helo
helo                                              : Is the Name A
helo                                              : Is the Name B
```

---
### Escape Characters
**(These are same as that in The C Programming Language)**
Now what if we want to include a `"` in our string? We can't do something like `"""`, so this is when we use escape characters(`\`). (refer [[Character Type#Escape Sequence]] for more info)
```csharp
string A = "\"";
```

Using `\` we start the escape sequence. This is a special character provided by C#

#### Verbatim string literal
In some instances, you do not care to do an escape sequence, and the extra slashes to escape everything are just in your way. You can put the @ symbol before the text (called a ***verbatim string literal***) to instruct the compiler to treat everything exactly as it looks:
```csharp
Console.WriteLine(@"C:\Users\RB\Desktop\MyFile.txt");
```

---
## The Digit Separator:
When writing long numbers, it is often easy to read the number when we have something to separate the digits so that it is easy for us to read and work with that number.
In C#, we can use `_` to separate the digits.
`int bigNumber = 1_000_000_000;`

#### Some restrictions in the Digits separator:
You can’t place them at the start or end of a literal, nor can it be immediately before or after any special character or symbol within a literal:
```csharp
// COMPILER ERRORS WITH THE DIGIT SEPARATOR!
int a = _1; // Can't start with an underscore.
int b = 1_; // Can't end with an underscore.
int c = 0_b_10101; // Can't place an underscore before or after the 'b' in a binary literal...
int d = 0_xFFDDA0; // ... or the 'x' in a hex literal.
double e = 1_e3; // Can't place one before or after the 'e' in exponential notation.
float f = 3.14_f; // Or around the characters used to mark the type, like the 'f' here...
ulong g = 1_U_L; // ... or the U or L here.
double h = 1_.3; // Can't place an underscore immediately before or after the decimal point.
```

---
## **Formatting the output:

We can decide the compulsory places to be displayed in the output for a number using formatting in C#. 
This is a rather useful stuff, and works just like what we had used in The C Programming Language for decimal variable's representation:
![[Input&Output and placeholders#What does %x.ylf does?]]

In C#, it is used as:
```csharp
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Diagnostics.CodeAnalysis;

namespace Practice1
{
    class Program
    {
        static void Main(string[] args)
        {
            double a = 56.49;
            Console.WriteLine($"The number is:{a:0.0}");
        }
    }
}
```
- We use `:` right after the variable and then use `0` to tell which decimal places are necessary to be displayed.
- It rounds off whenever necessary.
So the Output is:
```text
The number is:56.5
```

It is not exactly like what we saw in the C Programming Language, where the number before the decimal means the minimum number of the digits to be represented, and the number after the decimal means the number of decimal digits to represent.
Here, the places where `0` is there are the places where the output becomes **compulsory**. So for an `int` like `5` used like:
```csharp
int a = 5;
Console.WriteLine($"The Number is {a:00.000000}");
```
Would give an output like:
```text
The Number is 05.000000
```
