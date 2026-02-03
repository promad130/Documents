Point a = new Point() { x = 2, y = 3 };
Point b = new Point() { x = -4, y = 0 };

a.displayPoint();
b.displayPoint();

class Point
{
    public int x { get; init; } = 0;
    public int y { get; init; } = 0;

    public void displayPoint()
    {
        Console.WriteLine($"({x},{y})");
    }
}

