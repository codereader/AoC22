package AdventOfCode;

import java.util.*;
import AdventOfCode.Common.*;

public class Day1 {

	public static void main(String[] args) {
		
		var lines = FileUtils.readFile("./input.txt");
		
		System.out.printf(String.format("Lines read: %d\n", lines.size()));
		
		// Find the elf with the highest amount of calories in their bag
		var sums = new ArrayList<Integer>();
		
		var currentSum = 0;
		
		for (var line : lines)
		{
			if (line.isBlank())
			{
				sums.add(currentSum);
				currentSum = 0;
				continue;
			}
			
			currentSum += Integer.parseInt(line);
		}
		
		if (currentSum > 0)
		{
			sums.add(currentSum);
		}
		
		var highestCalories = sums.stream().sorted((a,b) -> b.compareTo(a)).findFirst().get();
		
		System.out.printf(String.format("Elf with highest calories: %d\n", highestCalories));
		
		// Take the top three elves
		var topThreeSum = sums.stream().sorted((a,b) -> b.compareTo(a)).limit(3).reduce(0, Integer::sum);
		
		System.out.printf(String.format("Sum of the top three elves with the highest calories: %d\n", topThreeSum));
	}
}
