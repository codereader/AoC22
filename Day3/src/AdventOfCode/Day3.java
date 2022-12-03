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
			
			var priority = Character.isUpperCase(item) ? (item - 'A' + 27) : (item - 'a' + 1);

			priorities.add(priority);
		}
		
		System.out.println(String.format("[Part1]: Priority Sum is %d", 
				priorities.stream().reduce(Integer::sum).get()));
	}

}
