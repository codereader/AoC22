package AdventOfCode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day2 {

	public static void main(String[] args) {
		var sequence = ReadFile("./input.txt");
		
		runPart1(sequence);
		runPart2(sequence);
	}
	
	public static void runPart1(List<String> sequence)
	{
		// Part 1
		var rounds = sequence.stream().map(line -> new Round(line));
		
		System.out.println(String.format("[Part1] Total Player Score: %d", 
				rounds.map(r -> r.getRoundScore()).reduce(Integer::sum).get()));
	}
	
	public static void runPart2(List<String> sequence)
	{
		// Part 2
		var rounds = sequence.stream().map(line -> new RoundWithRequiredOutcome(line));
		
		System.out.println(String.format("[Part2] Total Player Score: %d", 
				rounds.map(r -> r.getRoundScore()).reduce(Integer::sum).get()));
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
