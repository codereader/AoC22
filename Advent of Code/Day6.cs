namespace Advent_of_Code
{
	using System;
	using System.Globalization;
	using System.IO;
	using System.Linq;

	internal class Day6
	{
		public static void Main()
		{
			string stream = File.ReadAllText("datastream.txt");
			Console.WriteLine(FindMarker(stream));
		}

		private static int FindMarker(string stream)
		{
			for (int i = 0; i < stream.Length - 3; i++)
			{
				var j = i + 1;
				var k = i + 2;
				var l = i + 3;	
				var first = stream[i];
				var second = stream[j];
				var third = stream[k];
				var fourth = stream[l];

				if (first.Equals(second) || third.Equals(fourth) || second.Equals(third) || fourth.Equals(first) || first.Equals(third) || second.Equals(fourth))
				{
					continue;
				}

				return l + 1;
			}

			return -1;
		}
	}
}
