package AdventOfCode;

import java.util.stream.Collectors;

import AdventOfCode.Common.FileUtils;

public class Day20 {

	public static void main(String[] args)
	{
		var lines = FileUtils.readFile("./input.txt");
		
		var originalOrder = lines.stream().map(Long::parseLong).collect(Collectors.toList());
		
		var workingSet = new NumberSet(originalOrder);

		System.out.println(String.format("Initial Situation: %s\n", workingSet)); 
		
		for (int i = 0; i < workingSet.getNumberCount(); i++)
		{
			//System.out.println(String.format("Before: %s", workingSet)); 
			
			workingSet.moveNthNumber(i);
			
			//System.out.println(String.format("After : %s\n---", workingSet)); 
		}
		
		System.out.println(String.format("[Part1]: Sum of 1000th+2000th+3000th = %d", workingSet.getGroveCoordinates()));
		
		// Part 2: Factor in 811589153
		originalOrder = originalOrder.stream().map(n -> n * 811589153L).collect(Collectors.toList());
		workingSet = new NumberSet(originalOrder);
		
		for (int n = 0; n < 10; n++)
		{
			System.out.println(String.format("Round %d", n+1));
			
			for (int i = 0; i < workingSet.getNumberCount(); i++)
			{
				//System.out.println(String.format("Before: %s", workingSet)); 
				workingSet.moveNthNumber(i);
				//System.out.println(String.format("After : %s\n---", workingSet)); 
			}
		}
		
		System.out.println(String.format("[Part2]: Sum of 1000th+2000th+3000th = %d", workingSet.getGroveCoordinates()));
	}

}
