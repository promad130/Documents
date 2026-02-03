using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Diagnostics.CodeAnalysis;

namespace Practice1
{

    enum ArrowHead {
        Steel,
        Wood,

        Obsidian
    }
    enum arrowFletching {
        Plastic,
        Turkey_feathers,
        Goose_feathers
    }
    class Arrow
    {
        private (string typeHead, float costHead) arrowHead = ("", 0f);
        private (float shaftLength, float shaftCost) arrowShaft = (60f, 3f);
        private (string typeFletching, float costFletching) arrowFletching = ("", 0f);
        private float arrowCost = 0f;

        public void setArrow((string, float) arrowHead, (float, float) arrowShaft, (string, float) arrowFletching)
        {
            this.arrowHead = arrowHead;
            this.arrowShaft = arrowShaft;
            this.arrowFletching = arrowFletching;
        }

        public (string, string, float) GetInput()
        {
            int option = 0;
            string arrowHeadChoice = "";
            string arrowFletchingChoice = "";
            float arrowLength = 60f;
            do
            {
                Console.BackgroundColor = ConsoleColor.Black;
                Console.Write("What do you want to do?\n1. Pick the arrow head\n2. Pick the fletching type\n3. Pick the length");
                option = Convert.ToInt32(Console.ReadLine());


                if (option == 1)
                {
                    Console.BackgroundColor = ConsoleColor.Green;
                    Console.ForegroundColor = ConsoleColor.White;
                    Console.WriteLine("The expense for:");
                    Console.Write("1. Steel: 10  gold\n2. Wood: 3 gold\n3. Obsidian: 5 gold\nEnter your choice of arrow head:");
                    arrowHeadChoice = Console.ReadLine() ?? "";
                }
                else
                if (option == 2)
                {
                    Console.BackgroundColor = ConsoleColor.DarkYellow;
                    Console.ForegroundColor = ConsoleColor.White;
                    Console.WriteLine("The expense for:");
                    Console.Write("1. Plastic: 10  gold\n2. Goose_feathers: 3 gold\n3. Turkey_feathers: 5 gold\nEnter your choice of arrow head:");
                    arrowFletchingChoice = Console.ReadLine() ?? "";
                }
                else if (option == 3)
                {
                    Console.WriteLine("The minimum shaft length is 60cm, and maximum is 100cm.\nCost per cm is 0.05 gold");
                    Console.Write("Enter the length for your arrow?");
                    arrowLength = (float)Convert.ToDouble(Console.ReadLine());
                }

                Console.Write("Do you want to continue?(Yes/No)");
                if (Console.ReadLine() == "Yes")
                {
                    option = 4;
                }
                else
                {
                    option = 0;
                }
            } while (option == 4);

            (string, string, float) finalTypes = (arrowHeadChoice, arrowFletchingChoice, arrowLength);
            SetCost(finalTypes);
            return finalTypes;
        }

        private float SetCost((string Head, string Fletch, float Length) ArrowSettings)
        {
            
            return 0f;
        }

    }
   public class Rectangle{
	public float Width {get;set;}
	public float Height {get;set;}
	public float Area { get { return Height * Width; } }
	public string name {get;}
	public Rectangle(string name){
		this.name = name;
	}
}
    class Program
    {
        static void Main(string[] args)
        {
            Arrow myArrow;
            Rectangle myRec = new Rectangle("My rec");
            myRec.Height = 56;
            myRec.Width = 4;
            Console.WriteLine(myRec.Area);
        }
    }
}