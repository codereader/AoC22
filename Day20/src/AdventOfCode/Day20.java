package AdventOfCode;

import java.util.stream.Collectors;

import AdventOfCode.Common.FileUtils;

public class Day20 {

	public static void main(String[] args)
	{
		var lines = FileUtils.readFile("./input.txt");
		
		var originalOrder = lines.stream().map(Integer::parseInt).collect(Collectors.toList());
		
		var workingSet = new NumberSet(originalOrder);

		//System.out.println(String.format("Initial Situation: %s\n", workingSet)); 
		
		for (int i = 0; i < workingSet.getNumberCount(); i++)
		{
			//System.out.println(String.format("Before: %s", workingSet)); 
			
			workingSet.moveNthNumber(i);
			
			//System.out.println(String.format("After : %s\n---", workingSet)); 
		}
		
		var zeroIndex = workingSet.getPosition(0);
		
		var first = workingSet.getNumberAt(zeroIndex + 1000);
		var second = workingSet.getNumberAt(zeroIndex + 2000);
		var third = workingSet.getNumberAt(zeroIndex + 3000);
		
		System.out.println(String.format("1000th: %d, 2000th: %d, 3000th: %d", first, second, third));
		System.out.println(String.format("[Part1]: Sum of 1000th+2000th+3000th = %d", first + second + third));
	}

}
