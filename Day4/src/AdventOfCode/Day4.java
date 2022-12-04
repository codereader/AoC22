package AdventOfCode;

import AdventOfCode.Common.FileUtils;

public class Day4 {
	public static void main(String[] args) {
		var lines = FileUtils.readFile("./input.txt");
		
		var containedRanges = 0;
		var overlappingRanges = 0;
		for (var line : lines)
		{
			var parts = line.split(",");
			
			var range1 = new Range(parts[0]);
			var range2 = new Range(parts[1]);
			
			if (range1.contains(range2) || range2.contains(range1))
			{
				++containedRanges;
			}
			
			if (range1.overlaps(range2) || range2.overlaps(range1))
			{
				++overlappingRanges;
			}
		}
		
		System.out.println(String.format("[Part1] Number of fully redundant pairs: %d", containedRanges));
		System.out.println(String.format("[Part2] Number of overlapping pairs: %d", overlappingRanges));
	}
}
