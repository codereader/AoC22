package AdventOfCode;

import java.util.List;
import java.util.function.Function;
import AdventOfCode.Common.*;

public class Day2 {

	public static void main(String[] args)
	{
		var sequence = FileUtils.readFile("./input.txt");
		
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
}
