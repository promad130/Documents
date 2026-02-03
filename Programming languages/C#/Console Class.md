**Expected to know:** [[C-sharp Programming Language]](Till Now)
**Topics Covered:**
**Tags:**

We have used a few functions from the Console Class, like:

![[Input & Output in C-sharp#**Output Commands**]]
![[Input & Output in C-sharp#**Input Commands**]]


---
# Changing Colors

The console has variables for background and foreground colors, with that, we also have a class called `ConsoleColor` which provides various colors for that:
```csharp
using System;
using System.Collections.Generic;
using System.Diagnostics;

namespace Practice1
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.BackgroundColor = ConsoleColor.Yellow;
            Console.WriteLine("And here we cahnged the background color of text to yellow!");
            Console.ForegroundColor = ConsoleColor.Red;
            Console.Write("And we changed the text color");
            
        }
    }
}
```
![[Pasted image 20250525203121.png]]
 (Reminder, it is executed line by line, i.e., it is interpreted line-by-line)

# Console.Clear();

Clears all the text in the console written by our program:
```csharp
using System;
using System.Collections.Generic;
using System.Diagnostics;

namespace Practice1
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.BackgroundColor = ConsoleColor.Yellow;
            Console.WriteLine("And here we cahnged the background color of text to yellow!");
            Console.ForegroundColor = ConsoleColor.Red;
            Console.Write("And we changed the text color");
            Console.Clear();
            
        }
    }
}
```
![[Pasted image 20250525210346.png]]

# Console.Title = "This would be the new title";
Console has an attribute for the title of the console for the program:
```csharp
using System;
using System.Collections.Generic;
using System.Diagnostics;

namespace Practice1
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.Title = "My First Programme, time to change some colors";
            Console.BackgroundColor = ConsoleColor.Yellow;
            Console.WriteLine("And here we cahnged the background color of text to yellow!");
            Console.ForegroundColor = ConsoleColor.Red;
            Console.Write("And we changed the text color");
            Console.Clear();
            
        }
    }
}
```
![[Pasted image 20250525210647.png]]

# Beep Method

We can also make console BEEP 🤖
```csharp
Console.Beep();
```
or we can also pass in the frequencies:
```csharp
Console.Beep(int Frequency, int DurationInMilliSec)//Where 1000 is 1 sec
```

This is only supported on Windows