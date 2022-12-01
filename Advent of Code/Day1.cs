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
			Console.WriteLine("Start the program...");
			string[] numStrings = File.ReadAllLines("C:\\Users\\KathrinHubmann\\source\\repos\\Advent of Code\\AoC22\\Advent of Code\\Calories_list.txt");

			List<int> sums = new List<int>();
			var sum = 0;

			foreach (var num in numStrings)
			{
				try
				{
					var actualNum = int.Parse(num);
					sum += actualNum;
				}
				catch (FormatException)
				{
					sums.Add(sum);
					sum = 0;
				}
			}

			if (sum != 0)
			{
				sums.Add(sum);
				sum = 0;
			}

			sums.Sort();

			int[] topThree = {sums.Last(), sums[sums.Count - 2], sums[sums.Count - 3]};

			foreach (var calories in topThree)
			{
				sum += calories;
			}
			Console.WriteLine(sum);
			Console.ReadLine();
		}
	}
}
