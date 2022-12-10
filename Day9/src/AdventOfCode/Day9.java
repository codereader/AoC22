package AdventOfCode;

import AdventOfCode.Common.FileUtils;

public class Day9 {

	public static void main(String[] args) {
		var lines = FileUtils.readFile("./input.txt");
				
		var rope = new Rope(1);
		
		for (var line : lines)
		{
			var direction = getDirection(line);
			var times = getTimes(line);
			
			for (var i = 0; i < times; ++i)
			{
				rope.moveBy(direction);
			}
		}
		
		System.out.println(String.format("[Part1]: Number of visited tail positions: %d", rope.getNumVisitedTailPositions()));
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
