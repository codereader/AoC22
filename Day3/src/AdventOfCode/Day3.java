package AdventOfCode;

import java.util.ArrayList;
import java.util.stream.Collectors;

import AdventOfCode.Common.FileUtils;

public class Day3 {

	public static void main(String[] args) {
		var lines = FileUtils.readFile("./input.txt");
		
		var priorities = new ArrayList<Integer>();
		
		// Find the items that are present in both halves of the string
		for (var line : lines)
		{
			var part1 = line.subSequence(0, line.length() >> 1).chars().distinct()
					.mapToObj(e->(char)e).collect(Collectors.toList());
			
			var part2 = line.subSequence(line.length() >> 1, line.length()).chars().distinct()
					.mapToObj(e->(char)e).collect(Collectors.toList());
			
			var intersection = part1.stream().filter(part2::contains).collect(Collectors.toList());
			
			if (intersection.size() != 1)
			{
				throw new IllegalArgumentException("No character found");
			}
			
			var item = intersection.get(0);
			System.out.println(String.format("Line: %s has common character %c", line, item));
			
			priorities.add(getPriority(item));
		}
		
		System.out.println(String.format("[Part1]: Priority Sum is %d", 
				priorities.stream().reduce(Integer::sum).get()));
		
		priorities.clear();
		
		// Part 2: Take three rucksacks and find the common item
		for (var i = 0; i < lines.size(); i += 3)
		{
			var rucksack1 = lines.get(i);
			var rucksack2 = lines.get(i + 1);
			var rucksack3 = lines.get(i + 2);
			
			var sharedItems = rucksack1.chars().distinct().filter(c -> 
			{
				return rucksack2.indexOf(c) != -1 && rucksack3.indexOf(c) != -1;
			}).mapToObj(e->(char)e).collect(Collectors.toList());
			
			if (sharedItems.size() != 1)
			{
				throw new IllegalArgumentException("No character found");
			}
			
			var item = sharedItems.get(0);
			
			priorities.add(getPriority(item));
		}
		
		System.out.println(String.format("[Part2]: Priority Sum is %d", 
				priorities.stream().reduce(Integer::sum).get()));
	}
	
	private static int getPriority(char item)
	{
		return Character.isUpperCase(item) ? (item - 'A' + 27) : (item - 'a' + 1);
	}

}
