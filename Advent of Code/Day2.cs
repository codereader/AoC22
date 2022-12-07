namespace AdventOfCode_2
{
	using System;
	using System.Collections.Generic;
	using System.IO;

	internal class Day2
	{
		public static void Main()
		{
			List<string> content = ReadFile("C:\\Users\\KathrinHubmann\\source\\repos\\AdventOfCode_2\\AdventOfCode_2\\strategy.txt");
			int sum = 0;
			foreach (var line in content)
			{
				string[] parts = line.Split();
				sum += Score(parts[0], parts[1]);
			}
			Console.WriteLine(sum);
			Console.ReadLine();
		}

		private static int Score(string opponent, string me)
		{
			switch (opponent)
			{
				case "A":
					switch (me)
					{
						case "X":
							return 3;

						case "Y":
							return 1 + 3;

						case "Z":
							return 2 + 6;

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
							return 2;

						case "Y":
							return 3 + 3;

						case "Z":
							return 1 + 6;

						default:
							return 0;
					}
			}

			return 0;
		}

		public static List<string> ReadFile(string file)
		{
			string[] fileContent = File.ReadAllLines(file);
			List<string> strategy = new List<string>();

			foreach (var line in fileContent)
			{
				strategy.Add(line);
			}
			return strategy;
		}
	}
}
