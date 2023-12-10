package AdventOfCode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import AdventOfCode.Common.FileUtils;

public class Day5 {

	public static void main(String[] args) {
		var lines = FileUtils.readFile("./input.txt");
		
		var stacks = new ArrayList<Stack<Character>>();

		IntStream.range(0, 9).forEach(i -> stacks.add(new Stack<Character>()));
		
		// Read the initial stack configuration
		for (var lineIndex = 7; lineIndex >= 0; --lineIndex)
		{
			var line = lines.get(lineIndex);
			for (var stackIndex = 0; stackIndex < 9; ++stackIndex)
			{
				var crate = line.charAt(1 + stackIndex * 4);
				
				if (crate != ' ')
				{
					stacks.get(stackIndex).push(crate); 
				}
			}
		}
		
		// Clone the whole state for part 2
		var stacks2 = new ArrayList<Stack<Character>>();
		for (var stack : stacks)
		{
			stacks2.add((Stack<Character>)stack.clone());
		}

		// Read commands
		for (var lineIndex = 10; lineIndex < lines.size(); ++lineIndex)
		{
			var line = lines.get(lineIndex);
			// move 2 from 4 to 6
			var pattern = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)", Pattern.CASE_INSENSITIVE);
		    var matcher = pattern.matcher(line);
		    
		    if (!matcher.find()) continue;
		    
		    var count = Integer.parseInt(matcher.group(1));
		    var from = Integer.parseInt(matcher.group(2)) - 1;
		    var to = Integer.parseInt(matcher.group(3)) - 1;
		    
		    executeCrateCommand9000(stacks, count, from, to);
		    executeCrateCommand9001(stacks2, count, from, to);
		}
		
		System.out.println(String.format("[Part1]: Final configuration => %s", 
				stacks.stream().map(s -> s.peek().toString()).collect(Collectors.joining())));
		System.out.println(String.format("[Part2]: Final configuration => %s", 
				stacks2.stream().map(s -> s.peek().toString()).collect(Collectors.joining())));
	}
	
	private static void executeCrateCommand9000(ArrayList<Stack<Character>> stacks, int count, int fromIndex, int toIndex)
	{
		var fromStack = stacks.get(fromIndex);
	    var toStack = stacks.get(toIndex);
	    
	    for (var c = 0; c < count; ++c)
	    {
	    	toStack.push(fromStack.pop());
	    }
	}

	private static void executeCrateCommand9001(ArrayList<Stack<Character>> stacks, int count, int fromIndex, int toIndex)
	{
		var fromStack = stacks.get(fromIndex);
	    var toStack = stacks.get(toIndex);
	    
	    var buffer = new ArrayDeque<Character>();
	    
	    for (var c = 0; c < count; ++c)
	    {
	    	buffer.add(fromStack.pop());
	    }
	    
	    while (!buffer.isEmpty())
	    {
	    	toStack.push(buffer.pollLast());
	    }
	}
}
