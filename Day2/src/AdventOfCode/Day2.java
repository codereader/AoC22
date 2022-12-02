package AdventOfCode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day2 {

	public static void main(String[] args) {
		var sequence = ReadFile("./input.txt");
		
		var rounds = new ArrayList<Round>();
		
		for (var line : sequence) {
			
			var round = new Round(line);
			rounds.add(round);
			
			System.out.println(String.format("Round %s yields %d", line, round.getRoundScore()));
		}
		
		System.out.println(String.format("Total Player Score: %d", 
				rounds.stream().map(r -> r.getRoundScore()).reduce(Integer::sum).get()));
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
