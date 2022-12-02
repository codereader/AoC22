package AdventOfCode;
import java.io.*;
import java.util.*;

public class Day1 {

	public static void main(String[] args) {
		
		var lines = ReadFile("./input.txt");
		
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
	
	// Read the given text file into a list of strings
	public static List<String> ReadFile(String path)
	{
		var list = new ArrayList<String>();
		
		BufferedReader reader;

		try {
			reader = new BufferedReader(new FileReader(path));
			String line = reader.readLine();

			while (line != null) {
				list.add(line);
				line = reader.readLine();
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
	}

}
