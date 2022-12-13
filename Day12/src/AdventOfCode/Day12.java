package AdventOfCode;

import AdventOfCode.Common.FileUtils;

public class Day12 {

	public static void main(String[] args)
	{
		var lines = FileUtils.readFile("./input.txt");
		
		var map = Map.FromLines(lines);
		
		var solver = new FloodFillFinder(map);
		var foundLength = solver.getFastestPathToTarget();
		
		System.out.println(String.format("[Part1]: Found path with length %d", foundLength));
	}

}
