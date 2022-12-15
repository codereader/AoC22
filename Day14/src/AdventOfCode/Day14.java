package AdventOfCode;

import java.util.stream.Collectors;

import AdventOfCode.Common.FileUtils;
import AdventOfCode.Common.Vector2;

public class Day14 {

	public static void main(String[] args)
	{
		var lines = FileUtils.readFile("./input.txt");
		final int SandOriginX = 500;
		
		var traces = lines.stream().map(line -> new Trace(line)).collect(Collectors.toList());
		
		// Sand will start pouring at the origin point
		var field = new Cave(traces, SandOriginX);
		
		while (!field.getSandBlockReachedBottom())
		{
			field.runFrame();
		}
	
		System.out.println(String.format("[Part1] Number of produced sand blocks: %d", field.getNumberOfProducedSandBlocks() - 1));
		System.out.println(field.toString());
		
		// Part 2: continue running until no more sand blocks can be produced
		while (field.getCanProduceSandBlocks())
		{
			field.runFrame();
		}
	
		System.out.println(String.format("[Part2] Number of produced sand blocks: %d", field.getNumberOfProducedSandBlocks()));
		System.out.println(field.toString());
	}
}
