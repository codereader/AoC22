package AdventOfCode;

import java.io.IOException;

import AdventOfCode.Common.FileUtils;

public class Day9 {

	public static void main(String[] args) throws IOException {
		var lines = FileUtils.readFile("./input.txt");
				
		var ropePart1 = new Rope(2);
		var ropePart2 = new Rope(10);
		
		for (var line : lines)
		{
			var direction = getDirection(line);
			var times = getTimes(line);
			
			for (var i = 0; i < times; ++i)
			{
				ropePart1.moveBy(direction);
				ropePart2.moveBy(direction);
			}
		}
		
		System.out.println(String.format("[Part1]: Number of visited tail positions: %d", ropePart1.getNumVisitedTailPositions()));
		System.out.println(String.format("[Part2]: Number of visited tail positions: %d", ropePart2.getNumVisitedTailPositions()));
	}

	private static Vector2 getDirection(String line)
	{
		switch (line.charAt(0))
		{
		case 'L': return new Vector2(-1, 0);
		case 'R': return new Vector2(1, 0);
		case 'U': return new Vector2(0, 1);
		case 'D': return new Vector2(0, -1);
		default: throw new IllegalArgumentException("Unknown direction: " + line.charAt(0));
		}
	}
	
	private static int getTimes(String line)
	{
		return Integer.parseInt(line.substring(2));
	}
}
