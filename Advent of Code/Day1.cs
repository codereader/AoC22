namespace Advent_of_Code
{
	using System;
	using System.Collections.Generic;
	using System.IO;
	using System.Linq;

	internal class Day1
	{

		public static void Main()
		{
            Day2.Main();
			//Console.WriteLine("Start the program...");
			//string[] numStrings = File.ReadAllLines("C:\\Users\\KathrinHubmann\\source\\repos\\Advent of Code\\AoC22\\Advent of Code\\Calories_list.txt");

			//List<int> sums = new List<int>();
			//var sum = 0;

			//foreach (var num in numStrings)
			//{
				//try
				//{
					//var actualNum = int.Parse(num);
					//sum += actualNum;
				//}
				//catch (FormatException)
				//{
					//sums.Add(sum);
					//sum = 0;
				//}
			//}

			//if (sum != 0)
			//{
				//sums.Add(sum);
				//sum = 0;
			//}

			//sums.Sort();

			//int[] topThree = {sums.Last(), sums[sums.Count - 2], sums[sums.Count - 3]};

			//foreach (var calories in topThree)
			//{
				//sum += calories;
			//}
			//Console.WriteLine(sum);
			//Console.ReadLine();
		}
	}

	internal class Day2
    {
        public static void Main()
        {
            List<string> content = readStrategy("");
            int sum = 0;
            foreach (var line in content)
            {
                string[] parts = line.Split();
                sum += Score(parts[0], parts[1]);
            }
            Console.WriteLine(sum);
            Console.ReadLine;
        }

        private static int Score(string opponent, string me)
        {
            switch(opponent)
            {
                case "A":
                    switch(me)
                    {
                        case "X":
                            return 1 + 3;
                        
                        case "Y":
                            return 2 + 6;

                        case "Z":
                            return 3;

                        default:
                            return 0;
                    }

                case "B":
                    switch (me)
                    {
                        case "X":
                            return 1;

                        case "Y":
                            return 2 + 3;

                        case "Z":
                            return 3 + 6;

                        default:
                            return 0;
                    }

                case "C":
                    switch (me)
                    {
                        case "X":
                            return 1 + 6;

                        case "Y":
                            return 2;

                        case "Z":
                            return 3 + 3;

                        default:
                            return 0;
                    }
            }
        }

        private static List<string> readStrategy(string file)
        {
            string[] fileContent = File.ReadAllLines(file);
            List<string> strategy = new List<string>();

            foreach (var line in fileContent)
            {
                strategy.Add(line);
            }
        }
    }
}
