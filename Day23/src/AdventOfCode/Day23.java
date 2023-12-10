package AdventOfCode;

import AdventOfCode.Common.FileUtils;

public class Day23
{
	public static void main(String[] args)
	{
		var lines = FileUtils.readFile("./input.txt");
		
		var field = new Field(lines);
		
		System.out.println(String.format("We start with %d empty fields:\n%s", field.getNumEmptySpacesInBounds(), field));
		
		var round = 0;
		
		while (++round <= 10 && field.runRound())
		{
			System.out.println(String.format("Round %d", round));
		}
		
		System.out.println(String.format("Final Situation:", round));
		System.out.println(String.format("%s", field));
		System.out.println(String.format("[Part1]: %d empty ground spaces", field.getNumEmptySpacesInBounds()));
		
		field = new Field(lines);
		round = 1;
		
		while (field.runRound())
		{
			round++;
		}
		
		System.out.println(String.format("[Part2]: First round where no elf moved: %d", round));
	}
}
