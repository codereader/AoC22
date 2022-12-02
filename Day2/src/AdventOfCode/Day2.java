package AdventOfCode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class Day2 {

	public static void main(String[] args) {
		var sequence = ReadFile("./input.txt");
		
		runPart(1, sequence, line -> new Round(line));
		runPart(2, sequence, line -> new RoundWithRequiredOutcome(line));
	}
	
	public static void runPart(int part, List<String> sequence, Function<String, Round> roundCreator)
	{
		var rounds = sequence.stream().map(roundCreator);
		
		System.out.println(String.format("[Part%d] Total Player Score: %d",
				part,
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
