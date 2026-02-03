**Topics Covered:** 
**Tags:** [[Access Modifier]], [[Class or Type]],

![[Object Oriented Programming#Properties, an Abstraction]]
## Properties in C#:
**Properties** in C# are a fundamental feature that provide a flexible mechanism to read, write, or compute the values of private fields in a class, while maintaining encapsulation and data integrity.

## Key Concepts
- **Encapsulation:** Properties allow you to hide sensitive data (private fields) from direct access and expose them through controlled accessors (get and set), supporting the principle of encapsulation.
- **Accessors:** Each property can have a `get` accessor (to retrieve the value), a `set` accessor (to assign a value), or both. The `value` keyword in the `set` accessor represents the value being assigned.
- **Syntax:** Properties are declared in a class using curly braces and the `get`/`set` syntax. For example:
```csharp
private string name; // Field 
public string Name // Property 
{     
	get { return name; }    
	set { name = value; } 
}
```
***The "value" is a keyword associated with set {} that means the value assigned to the property at runtime***
## Types of Properties

| Type       | Description                                            | Example Syntax                   |
| ---------- | ------------------------------------------------------ | -------------------------------- |
| Read-Write | Both `get` and `set` accessors are present.            | `public int Age { get; set; }`   |
| Read-Only  | Only `get` accessor is present.                        | `public string Name { get; }`    |
| Write-Only | Only `set` accessor is present (rare).                 | `public decimal Salary { set; }` |
| Init-Only  | Uses `init` accessor, settable only at initialization. | `public int Id { get; init; }`   |

## Advantages of Using Properties
- **Validation:** You can add logic to validate data before assigning it to a field.
- **Data Protection:** Fields remain private, reducing the risk of accidental misuse.
- **Abstraction:** Implementation details can change without affecting the external interface.
- **Custom Logic:** Properties can perform calculations, trigger events, or interact with other fields when accessed or modified.

## Example
```csharp
public class Person
{
    private int age;

    public int Age
    {
        get { return age; }
        set
        {
            if (value >= 0)
                age = value;
        }
    }
}

```
Usage:
```csharp
Person p = new Person();
p.Age = 25; // Valid
p.Age = -5; // Ignored due to validation
Console.WriteLine(p.Age); // Output: 25
```

## Auto-implemented property
Some properties will have complex logic for its getter, setter, or both. But others do not need anything fancy and end up looking like this:
```csharp
public class Player
{
	private string _name;
	public string Name
	{
		get => _name;
		set => _name = value;
	}
}
```
Because these are commonplace, there is a concise way to define properties of this nature called an *auto-implemented property* or an *auto property*:
```csharp
public class Player
{
	public string Name { get; set; }
}
```
You don’t define bodies for either getter or setter, and you don’t even define the backing field(the variable we are encapsulating).

You just end the getter and setter with a semicolon. The compiler will generate a backing field for this property and create a basic getter and setter method around it. 
However doing so makes impossible for us to access the backing field directly as it was never instantiated in the first place, it is handled by the compiler when we run the program, hence we can access it's value only via the getter field of the property, which is done by using the property identifier like a variable.

An example:
```csharp
public class Rectangle // Note how short this code got with auto-properties.
{
	public float Width { get; set; }
	public float Height { get; set; }
	public float Area => Width * Height;
	public Rectangle(float width, float height)
	{
		Width = width;
		Height = height;
	}
}
```

## Immutable Fields and Properties 
Auto-properties can be get-only, like a regular property. (They cannot be set-only; there is no scenario where that is useful as it would be a black hole for data.) This makes the property **immutable**.
When a property is *get-only*, it can still be assigned values, but only from within a constructor. These are also sometimes referred to as *read-only properties*.

Hence, if we do something like this:
```csharp
public class Rectangle{
	public float Width {get;set;}
	public float Height {get;set;}
	public float Area {get Height*Width;}
	
	public string name {get;} = "My Rectangular Class";
}
```
OR:
```csharp
public class Rectangle
{
	public float Width {get;set;}	
	public float Height {get;set;}
	public float Area 
	{ 
		get { return Height * Width; } 
	}
	public string name {get;}
	public Rectangle(string name)
	{
		this.name = name;
	}
}
```

Now, here we can only access the area and never set it!!!

### Immutable Fields
Now we saw immutable properties, but what if we want to do the same with fields? Then we use `readonly` keyword with it:
```csharp
public class Player
{
private readonly string _name;
}
	public Player(string name)
	{
		_name = name;
	}
```
This makes our field immutable once it has been assigned something via constructor.