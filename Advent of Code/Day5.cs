namespace Advent_of_Code
{
	using System;
	using System.Collections.Generic;
	using System.Globalization;
	using System.Linq;
	using AdventOfCode_2;

	internal class Day5
	{
		public static void Main()
		{
			Stack<char> one = new Stack<char>(new[] { 'S', 'Z', 'P', 'D', 'L', 'B', 'F', 'C' });
			Stack<char> two = new Stack<char>(new[] { 'N', 'V', 'G', 'P', 'H', 'W', 'B'});
			Stack<char> three = new Stack<char>(new[] { 'F', 'W', 'B', 'J', 'G'});
			Stack<char> four = new Stack<char>(new[] { 'G', 'J', 'N', 'F', 'L', 'W', 'C', 'S' });
			Stack<char> five = new Stack<char>(new[] { 'W', 'J', 'L', 'T', 'P', 'M', 'S', 'H' });
			Stack<char> six = new Stack<char>(new[] { 'B', 'C', 'W', 'G', 'F', 'S'});
			Stack<char> seven = new Stack<char>(new[] { 'H', 'T', 'P', 'M', 'Q', 'B', 'W'});
			Stack<char> eight = new Stack<char>(new[] { 'F', 'S', 'W', 'T'});
			Stack<char> nine = new Stack<char>(new[] { 'N', 'C', 'R'});

			List<string> instructions = Day2.ReadFile("Stack_instructions.txt");
			List<int> amounts = new List<int>();
			List<int> from = new List<int>();
			List<int> to = new List<int>();

			foreach (var instruction in instructions)
			{
				var split = instruction.Split(' ');
				amounts.Add(int.Parse(split[1]));
				from.Add(int.Parse(split[3]));
				to.Add(int.Parse(split[5]));
			}

			for (int i = 0; i < amounts.Count; i++)
			{
				var tempStr = "";
				for (int j = 0; j < amounts[i]; j++)
				{
					
					switch (from[i])
					{
						case 1:
							tempStr += one.Pop();
							break;
						case 2:
							tempStr += two.Pop();
							break;
						case 3:
							tempStr += three.Pop();
							break;
						case 4:
							tempStr += four.Pop();
							break;
						case 5:
							tempStr += five.Pop();
							break;
						case 6:
							tempStr += six.Pop();
							break;
						case 7:
							tempStr += seven.Pop();
							break;
						case 8:
							tempStr += eight.Pop();
							break;
						case 9:
							tempStr += nine.Pop();
							break;
					}

				}
				switch (to[i])
				{
					case 1:
						foreach (var c in tempStr.Reverse())
						{
							one.Push(c);
						}
						break;
					case 2:
						foreach (var c in tempStr.Reverse())
						{
							two.Push(c);
						}
						break;
					case 3:
						foreach (var c in tempStr.Reverse())
						{
							three.Push(c);
						}
						break;
					case 4:
						foreach (var c in tempStr.Reverse())
						{
							four.Push(c);
						}
						break;
					case 5:
						foreach (var c in tempStr.Reverse())
						{
							five.Push(c);
						}
						break;
					case 6:
						foreach (var c in tempStr.Reverse())
						{
							six.Push(c);
						}
						break;
					case 7:
						foreach (var c in tempStr.Reverse())
						{
							seven.Push(c);
						}
						break;
					case 8:
						foreach (var c in tempStr.Reverse())
						{
							eight.Push(c);
						}
						break;
					case 9:
						foreach (var c in tempStr.Reverse())
						{
							nine.Push(c);
						}
						break;
				}
			}

			var tops = one.Pop().ToString() + two.Pop() + three.Pop() + four.Pop() + five.Pop() + six.Pop() + seven.Pop() +
			           eight.Pop() + nine.Pop();

			Console.WriteLine(tops);
		}
	}
}
