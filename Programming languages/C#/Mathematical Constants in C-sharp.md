**Expected to know:** [[Variables in C-Sharp]], [[Type System and Constants in 'C-sharp' (Data Types)]]
**Topics Covered:**
**Tags:** [[Mathematical Constants in C-sharp]]

We have weird stuff going on in math when we talk about big numbers, big enough to be considered infinity, not something perceivable.

# **Infinity $\infty$

In C#, infinity is considered a float and double, and can be given as:
```csharp
double a = double.PositiveInfinity;
double b = double.NegativeInfinity;
```
and same can be done with `floats`:
```csharp
float a = float.PositiveInfinity;
float b = float.NegativeInfinity;
```

# NaN numbers

Well if we have infinity, why not go wild and do something like **$\frac{\infty}{\infty}$**? Well yes we can, and things like this is what we call **indeterminate**.
This is called **Not A Number(NaN)** in C#:
This is again, considered as a float and double.
```csharp
double a = double.NaN;
```

# MinValue and MaxValue

Most of the numeric types define a MinValue and MaxValue inside of them. 
These can be used to see the minimum or maximum value that the type can hold.
You access these like `NaN` and `infinity` for floating point types:

```csharp
int maximum = int.MaxValue;
int minimum = int.MinValue;
```