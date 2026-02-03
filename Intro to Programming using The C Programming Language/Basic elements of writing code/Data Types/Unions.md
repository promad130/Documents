A **union** in C is a user-defined data type that allows multiple variables of different data types to share the same memory location. This means that, although a union can have several members (for example, an `int`, a `float`, and a `char[]`), only one of its members can contain a valid value at any given time, because all members overlap in memory.

## Key Characteristics of Unions
- **Memory Sharing:** All members of a union share the same memory space. The memory allocated for a union is equal to the size of its largest member.
- **One Value at a Time:** Only the most recently assigned member holds a valid value; assigning a value to one member will overwrite the values of the others
- **Syntax:** Unions are defined using the `union` keyword, similar to how structures (`struct`) are defined.

## Example Declaration
```c
union Data 
{     
	int i;    
	float f;    
	char str[20]; 
};
```
In this example, `Data` can store either an `int`, a `float`, or a string of up to 19 characters (plus null terminator), but only one at a time.

## Usage
- **Memory Efficiency:** Unions are useful in situations where memory is limited and you only need to store one type of data at a time, such as in embedded systems or when handling data that could be of different types but never simultaneously.
- **Accessing Members:** Use the dot operator (`.`) to access members, just like with structures. However, remember that only the last written value is reliable.

## Example Usage
```c
union Data d; 

d.i = 10; 
printf("%d\n", d.i); // Valid 

d.f = 220.5; 
printf("%f\n", d.f); // Valid 

printf("%d\n", d.i); // Now invalid, as 'f' overwrote 'i'`
```
## Difference Between Union and Structure

|Feature|Structure (`struct`)|Union (`union`)|
|---|---|---|
|Memory|Each member has its own space|All members share the same space|
|Size|Sum of all members' sizes|Size of the largest member|
|Data Access|All members can be used at once|Only one member holds a valid value|
|Use Case|Store multiple values simultaneously|Store one of several possible values|

Unions are a powerful tool for efficient memory usage when only one of several data types is needed at a time.