Color color = new Color() { r = 56, g = 128, b = 5 };

class Color
{
    public int r { get; init; } = 0;
    public int g { get; init; } = 0;
    public int b { get; init; } = 0;

    public void DisplayColor()
    {
        Console.WriteLine($"The Properties are:\nr:{r}\ng:{g}\nb:{b}");
    }

    public static Color White = new Color() { r = 255, g = 255, b = 255 };
    public static Color Black = new Color() { r = 0, g = 0, b = 0 };
    public static Color Red = new Color() { r = 255, g = 0, b = 0 };
    public static Color Orange = new Color() { r = 255, g = 165, b = 0 };
    public static Color Yellow = new Color() { r = 255, g = 255, b = 0 };
    public static Color Green = new Color() { r = 0, g = 255, b = 0 };
    public static Color Blue = new Color() { r = 0, g = 0, b = 255 };
    public static Color Purple = new Color() { r = 128, g = 0, b = 128 };
}