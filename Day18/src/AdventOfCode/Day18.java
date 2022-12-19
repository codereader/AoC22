package AdventOfCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
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
		
		var volume = new Volume(new NumberTriple(maxX, maxY, maxZ));
		
		for (var number : numbers)
		{
			volume.set(number, BlockType.Lava);
		}
		
		var openSides = 0;
		
		for (var number : numbers)
		{
			openSides += getNumSidesWithNeighbourType(volume, number, BlockType.Air);
		}
		
		System.out.println(String.format("[Part1]: Surface Area = %d", openSides));
		
		var reachableLavaBlocks = new HashSet<NumberTriple>();
		
		var blocksToFill = new Stack<NumberTriple>();
		blocksToFill.add(new NumberTriple(0,0,0));
		
		while (!blocksToFill.isEmpty())
		{
			var n = blocksToFill.pop();
			
			// Fill this field
			volume.set(n, BlockType.Water);
			
			checkNeighbour(volume, new NumberTriple(n.X+1,n.Y,n.Z), blocksToFill, reachableLavaBlocks);
			checkNeighbour(volume, new NumberTriple(n.X-1,n.Y,n.Z), blocksToFill, reachableLavaBlocks);
			checkNeighbour(volume, new NumberTriple(n.X,n.Y+1,n.Z), blocksToFill, reachableLavaBlocks);
			checkNeighbour(volume, new NumberTriple(n.X,n.Y-1,n.Z), blocksToFill, reachableLavaBlocks);
			checkNeighbour(volume, new NumberTriple(n.X,n.Y,n.Z+1), blocksToFill, reachableLavaBlocks);
			checkNeighbour(volume, new NumberTriple(n.X,n.Y,n.Z-1), blocksToFill, reachableLavaBlocks);
		}
		
		var externalSurfaceArea = 0;
		
		for (var number : reachableLavaBlocks)
		{
			externalSurfaceArea += getNumSidesWithNeighbourType(volume, number, BlockType.Water);
		}
		
		System.out.println(String.format("[Part2]: External Surface Area = %d", externalSurfaceArea));
	}
	
	private static void checkNeighbour(Volume volume, NumberTriple n, 
			Stack<NumberTriple> blocksToFill, Set<NumberTriple> reachableLavaBlocks)
	{
		if (n.X < 0 || n.Y < 0 || n.Z < 0) return;
		if (n.X > volume.Max.X || n.Y > volume.Max.Y || n.Z > volume.Max.Z) return;
		
		switch (volume.get(n))
		{
		case Lava:
			reachableLavaBlocks.add(n);
			break;
		case Air:
			blocksToFill.push(n);
			break;
		case Water:
			break;
		}
	}

	private static int getNumSidesWithNeighbourType(Volume volume, NumberTriple n, BlockType type)
	{
		var count = 0;
		
		if (volume.get(new NumberTriple(n.X-1, n.Y, n.Z)) == type) ++count;
		if (volume.get(new NumberTriple(n.X+1, n.Y, n.Z)) == type) ++count;
		if (volume.get(new NumberTriple(n.X, n.Y-1, n.Z)) == type) ++count;
		if (volume.get(new NumberTriple(n.X, n.Y+1, n.Z)) == type) ++count;
		if (volume.get(new NumberTriple(n.X, n.Y, n.Z-1)) == type) ++count;
		if (volume.get(new NumberTriple(n.X, n.Y, n.Z+1)) == type) ++count;
		
		return count;
	}
	
	private static NumberTriple parseTriple(String line)
	{
		var parts = line.split(",");
		
		return new NumberTriple(
			Integer.parseInt(parts[0]),
			Integer.parseInt(parts[1]),
			Integer.parseInt(parts[2])
		);
	}
}
