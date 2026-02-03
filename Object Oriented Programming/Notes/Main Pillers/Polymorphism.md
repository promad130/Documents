![[Object Oriented Programming#Polymorphism]]

<blockquote>Object-Oriented Principle #5: Polymorphism—Derived classes can override methods from the base class. The correct version is determined at runtime, so you will get different behavior depending on the object’s class.</blockquote>

So, in polymorphism, in order to give a method the permission to get overriden we need to declare it as a **Virtual** method:
```csharp
public class ChessPiece
{
	public int Row { get; set; }
	public int Column { get; set; }
	
	public virtual bool IsLegalMove(int row, int column) =>
		IsOnBoard(row, column) && !IsCurrentLocation(row, column);
	
	protected bool IsOnBoard(int row, int column) =>
		row >= 0 && row < 8 && column >= 0 && column < 8;
	
	protected bool IsCurrentLocation(int row, int column) =>
		row == Row && column == Column;
}
```

And now, this virtual method can be overriden by other derived classes:
```csharp
class King :  ChessPiece
{
	public override bool isLegalMove(int row, int col)
	{
		if(!base.isLegalMove(row, col))
			return false;
			
		if (Math.Abs(row - Row) > 1) return false;
		if (Math.Abs(column - Column) > 1) return false;
		
		return true;
	}
}
```

Ok, so we have overriden a base class's method, but how do we get access to the base class?
## The `base`:
The `base` keyword in C# is used within a derived class to access members (methods, properties, constructors) of its immediate base class. It serves as a way to explicitly refer to the functionality in the parent class from the child class, often used to extend or modify inherited behavior.