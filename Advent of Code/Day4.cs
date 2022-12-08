namespace Advent_of_Code
{
	using System;
	using System.Collections.Generic;
	using AdventOfCode_2;

	internal class Day4
	{
		public static void Main()
		{
			List<string> pairs = Day2.ReadFile("Pairs.txt");
			var counter = 0;
			foreach (var pair in pairs)
			{
				var elfs = pair.Split(',');

				var elf1 = elfs[0];
				var elf2 = elfs[1];
				var range1 = GetRange(elf1);
				var range2 = GetRange(elf2);
				if (Compare(range1, range2))
				{
					counter++;
				}
			}
			Console.WriteLine(counter);
			Console.ReadLine();
		}

		private static List<int> GetRange(string elf)
		{
			var firstToLast = elf.Split('-');
			var range = new List<int>();
			for (int i = int.Parse(firstToLast[0]); i <= int.Parse(firstToLast[1]); i++)
			{
				range.Add(i);
			}

			return range;
		}

		private static bool Compare(List<int> range1, List<int> range2)
		{
			if (range1.Count <= range2.Count)
			{
				foreach (var i in range1)
				{
					if (range2.Contains(i))
					{
						return true;
					}
				}
			}
			else if (range1.Count > range2.Count)
			{
				foreach (var i in range2)
				{
					if (range1.Contains(i))
					{
						return true;
					}
				}
			}
			return false;
		}
	}
}
