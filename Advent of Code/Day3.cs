namespace AdventOfCode_2
{
	using System;
	using System.Collections.Generic;
	using System.Linq;

	internal class Day3
	{

		public static void Main()
		{
			List<string> content = Day2.ReadFile("contents.txt");
			var list = Day2.ReadFile("letterToNumber.txt");
			Dictionary<string, int> letterToNumber = MakeDictionary(list);

			int sum1 = Duplicates(content, letterToNumber);
			int sum2 = Groups(content, letterToNumber);
			
			Console.WriteLine(sum1);
			Console.WriteLine(sum2);
			//Console.ReadLine();
		}

		private static Dictionary<string, int> MakeDictionary(List<string> list)
		{
			Dictionary<string, int> letterToNumber = new Dictionary<string, int>();
			foreach (var text in list)
			{
				var split = text.Split(' ');
				letterToNumber.Add(split[0], int.Parse(split[1]));
			}
			return letterToNumber;
		}

		private static int Duplicates(List<string> content, Dictionary<string, int> letterToNumber)
		{
			int sum = 0;
			foreach (var rucksack in content)
			{
				var part1 = rucksack.Substring(0, rucksack.Length / 2).ToHashSet();
				var part2 = rucksack.Substring(rucksack.Length / 2, rucksack.Length / 2).ToHashSet();

				var letter = part1.Intersect(part2).Single();
				var score = char.IsLower(letter) ? (int)letter - 96 : letter - 38;
				sum += score;


				//foreach (var letter1 in part1)
				//{
				//	if (part2.Contains(letter1))
				//	{
				//		int value = letterToNumber[letter1.ToString()];
				//		sum += value;
				//		break;
				//	}
				//}
			}

			return sum;
		}

		private static int Groups(List<string> content, Dictionary<string, int> letterToNumber)
		{
			int sum = 0;

			List<string> groupList = new List<string>();
			string groupStr = "";
			var count = 0;
			foreach (var rucksack in content)
			{

				if (count == 3)
				{
					count = 0;
					groupList.Add(groupStr);
				}
				if (count == 0)
				{
					groupStr = rucksack + ";";
					count++;
				}
				else
				{
					groupStr = groupStr + rucksack + ";";
					count++;
				}

				if (rucksack.Equals(content.Last()))
				{
					groupList.Add(groupStr);
				}
			}

			foreach (var group in groupList)
			{
				var split = group.Split(';');
				var part1 = split[0];
				var part2 = split[1];
				var part3 = split[2];
				foreach (var letter in part1.ToCharArray().Distinct())
				{
					if (part2.Contains(letter) && part3.Contains(letter))
					{
						int value = letterToNumber[letter.ToString()];
						sum += value;
					}
				}
			}

			return sum;
		}
	}
}
