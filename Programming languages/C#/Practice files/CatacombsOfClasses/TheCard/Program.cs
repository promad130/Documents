enum CardColor
{
    Red,
    Green,
    Blue,
    Yellow
}

enum CardRank
{
    One = 1,
    Two,
    Three,
    Four,
    Five,
    Six,
    Seven,
    Eight,
    Nine,
    Ten,
    Dollar,
    Percent,
    Caret,
    Ampersand
}

class Card
{
    public required CardColor color { get; init; }
    public required CardRank rank { get; init; }

    public void CheckCardFace()
    {
        if ((int)rank >= 11)
        {
            Console.WriteLine("The card is a Symbol Card")
        }
    }
}