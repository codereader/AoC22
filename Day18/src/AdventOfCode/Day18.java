package AdventOfCode;

import java.util.stream.Collectors;

import AdventOfCode.Common.FileUtils;

public class Day18
{
	public static void main(String[] args)
	{
		var lines = FileUtils.readFile("./input.txt");
		
		var numbers = lines.stream().map(Day18::parseTriple).collect(Collectors.toList());
		
		var maxX = numbers.stream().mapToInt(n -> n.X).max().getAsInt();
		var maxY = numbers.stream().mapToInt(n -> n.Y).max().getAsInt();
		var maxZ = numbers.stream().mapToInt(n -> n.Z).max().getAsInt();
		
		var field = new boolean[maxZ+1][maxY+1][maxX+1];
		
		for (var number : numbers)
		{
			field[number.Z][number.Y][number.X] = true;
		}
		
		var openSides = 0;
		
		for (var number : numbers)
		{
			openSides += getNumSidesWithoutNeighbour(field, number.X, number.Y, number.Z);
		}
		
		System.out.println(String.format("[Part1]: Surface Area = %d", openSides));
	}
	
	private static int getNumSidesWithoutNeighbour(boolean[][][] field, int x, int y, int z)
	{
		var count = 0;
		
		if (!getFieldValueSafe(field, x-1, y, z)) ++count;
		if (!getFieldValueSafe(field, x+1, y, z)) ++count;
		if (!getFieldValueSafe(field, x, y-1, z)) ++count;
		if (!getFieldValueSafe(field, x, y+1, z)) ++count;
		if (!getFieldValueSafe(field, x, y, z-1)) ++count;
		if (!getFieldValueSafe(field, x, y, z+1)) ++count;
		
		return count;
	}
	
	private static boolean getFieldValueSafe(boolean[][][] field, int x, int y, int z)
	{
		if (z < 0 || y < 0 || x < 0 || z >= field.length) return false;
		
		if (y >= field[z].length) return false;
		if (x >= field[z][y].length) return false;
		
		return field[z][y][x];
	}

	private static NumberTriple parseTriple(String line)
	{
		var parts = line.split(",");
		var triple = new NumberTriple();
		
		triple.X = Integer.parseInt(parts[0]);
		triple.Y = Integer.parseInt(parts[1]);
		triple.Z = Integer.parseInt(parts[2]);
		
		return triple;
	}
}
