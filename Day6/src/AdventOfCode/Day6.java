package AdventOfCode;

import AdventOfCode.Common.FileUtils;

public class Day6 {

	public static void main(String[] args) {
		var line = FileUtils.readFile("./input.txt").get(0);
		
		var part1Index = findIndexOfUniqueSequence(line, 4);
		var part2Index = findIndexOfUniqueSequence(line, 14);
		
		System.out.println(String.format("[Part1]: %d", part1Index + 1));
		System.out.println(String.format("[Part2]: %d", part2Index + 1));
	}

	private static int findIndexOfUniqueSequence(String line, int numUniqueChars)
	{
		for (var index = numUniqueChars - 1; index < line.length(); ++index)
		{
			if (line.subSequence(index - numUniqueChars + 1, index + 1)
					.chars().distinct().count() == numUniqueChars)
			{
				return index;
			}
		}
		
		return -1;
	}
}
