namespace Advent_of_Code
{
	using System;
	using System.Collections.Generic;
	using System.Globalization;
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
				for (int j = 0; j < amounts[i]; j++)
				{
					var tempChar = 'g';
					switch (from[i])
					{
						case 1:
							tempChar = one.Pop();
							break;
						case 2:
							tempChar = two.Pop();
							break;
						case 3:
							tempChar = three.Pop();
							break;
						case 4:
							tempChar = four.Pop();
							break;
						case 5:
							tempChar = five.Pop();
							break;
						case 6:
							tempChar = six.Pop();
							break;
						case 7:
							tempChar = seven.Pop();
							break;
						case 8:
							tempChar = eight.Pop();
							break;
						case 9:
							tempChar = nine.Pop();
							break;
					}

					switch (to[i])
					{
						case 1:
							one.Push(tempChar);
							break;
						case 2:
							two.Push(tempChar);
							break;
						case 3:
							three.Push(tempChar);
							break;
						case 4:
							four.Push(tempChar);
							break;
						case 5:
							five.Push(tempChar);
							break;
						case 6:
							six.Push(tempChar);
							break;
						case 7:
							seven.Push(tempChar);
							break;
						case 8:
							eight.Push(tempChar);
							break;
						case 9:
							nine.Push(tempChar);
							break;
					}
				}
			}

			var tops = one.Pop().ToString() + two.Pop() + three.Pop() + four.Pop() + five.Pop() + six.Pop() + seven.Pop() +
			           eight.Pop() + nine.Pop();

			Console.WriteLine(tops);
		}
	}
}
