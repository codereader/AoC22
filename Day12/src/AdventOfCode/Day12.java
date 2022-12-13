package AdventOfCode;

import AdventOfCode.Common.FileUtils;
import AdventOfCode.Common.Vector2;

public class Day12 {

	public static void main(String[] args)
	{
		var lines = FileUtils.readFile("./input.txt");
		
		var map = Map.FromLines(lines);
		
		// Part 1 - Find path to target position from start
		var part1Solver = new FloodFillFinder(map, FloodFillFinder::oneStepUpwardsAnyDownwards);
		part1Solver.evaluateField(map.getStartPosition());
		
		System.out.println(String.format("[Part1]: Found path with length %d", part1Solver.getCost(map.getTargetPosition())));
		
		// Part 2 - Re-evaluate from the target position
		var part2Solver = new FloodFillFinder(map, (sourceHeight, destinationHeight) -> 
		{
			// Reverse the logic this time
			return FloodFillFinder.oneStepUpwardsAnyDownwards(destinationHeight, sourceHeight);
		});
		part2Solver.evaluateField(map.getTargetPosition());
		
		// Check all fields with a height of 0
		var bestCost = Integer.MAX_VALUE;
		
		for (int row = 0; row < map.getDimensions().getY(); row++)
		{
			for (int col = 0; col < map.getDimensions().getX(); col++)
			{
				var pos = new Vector2(col, row);
				
				if (map.getHeight(pos) == 0)
				{
					bestCost = Math.min(bestCost, part2Solver.getCost(pos));
				}
			}
		}
		
		System.out.println(String.format("[Part2]: Found spot with height 0 and length %d", bestCost));
	}

}
