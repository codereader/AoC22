package AdventOfCode;

import AdventOfCode.Common.FileUtils;

public class Day24
{
	public static void main(String[] args)
	{
		var lines = FileUtils.readFile("./test.txt");
		
		var field = new Field(lines);
		
		System.out.println(String.format("Initial field:\n%s", field));
		System.out.println(String.format("Target coords:\n%s", field.getTargetCoords()));
	}
}
