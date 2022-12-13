package AdventOfCode;

import AdventOfCode.Common.FileUtils;

public class Day13 {

	public static void main(String[] args)
	{
		var lines = FileUtils.readFile("./test.txt");
		
		var iter = lines.stream().iterator();
		var pairIndex = 1; 
		var correctIndexSum = 0;
		
		while (iter.hasNext())
		{
			var line1 = iter.next();
			if (line1.isBlank()) continue;
			var line2 = iter.next();
			
			var entry1 = Elements.parse(line1);
			var entry2 = Elements.parse(line2);
			
			System.out.println(String.format("Line1: %s", entry1.toString()));
			System.out.println(String.format("Line2: %s", entry2.toString()));
			System.out.println();
			
			if (entry1.compareTo(entry2) < 0)
			{
				System.out.println(String.format("Packets with index %d are in correct order", pairIndex));
				correctIndexSum += pairIndex;
			}
			else
			{
				System.out.println(String.format("Packets with index %d are in INCORRECT order", pairIndex));
			}
			
			++pairIndex;
		}
		
		System.out.println(String.format("Sum of packets with correct ordering: %d", correctIndexSum));
	}

}
