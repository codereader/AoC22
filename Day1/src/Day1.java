import java.io.*;
import java.util.*;

public class Day1 {

	public static void main(String[] args) {
		
		var lines = ReadFile("C:\\Users\\Matthias Baumann\\aoc22\\Day1\\input.txt");
		
		System.out.printf(String.format("Lines read: %d\n", lines.size()));
		
		// Find the elf with the highest amount of calories in their bag
		var highestCalories = 0;
		var currentSum = 0;
		
		for (var line : lines)
		{
			if (line.isBlank())
			{
				highestCalories = Math.max(currentSum, highestCalories);
				currentSum = 0;
				continue;
			}
			
			currentSum += Integer.parseInt(line);
		}
		
		System.out.printf(String.format("Elf with highest calories: %d\n", highestCalories));
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
