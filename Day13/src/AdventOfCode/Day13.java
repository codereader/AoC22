package AdventOfCode;

import java.util.ArrayList;

import AdventOfCode.Common.FileUtils;

public class Day13 {

	public static void main(String[] args)
	{
		var lines = FileUtils.readFile("./input.txt");
		
		var iter = lines.stream().iterator();
		var pairIndex = 1; 
		var correctIndexSum = 0;
		
		var entries = new ArrayList<Elements>();
		
		while (iter.hasNext())
		{
			var line1 = iter.next();
			if (line1.isBlank()) continue;
			var line2 = iter.next();
			
			var entry1 = Elements.parse(line1);
			var entry2 = Elements.parse(line2);
			
			entries.add(entry1);
			entries.add(entry2);
			
			if (entry1.compareTo(entry2) < 0)
			{
				correctIndexSum += pairIndex;
			}
			
			++pairIndex;
		}
		
		System.out.println(String.format("[Part1]: Sum of packet indices with correct ordering: %d", correctIndexSum));
		
		// Part 2: introduce two more packages
		var twoPackage = Elements.parse("[[2]]");
		var sixPackage = Elements.parse("[[6]]");
		
		entries.add(twoPackage);
		entries.add(sixPackage);
		
		entries.sort(new EntryComparator());
		
		var twoIndex = entries.indexOf(twoPackage) + 1;
		var sixIndex = entries.indexOf(sixPackage) + 1;
		
		var decoderKey = twoIndex * sixIndex;
		
		System.out.println(String.format("[Part2]: Decoder Key: %d", decoderKey));
	}

}
