package AdventOfCode;

import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Pattern;

import AdventOfCode.Common.FileUtils;

public class Day5 {

	public static void main(String[] args) {
		var lines = FileUtils.readFile("./input.txt");
		
		var stacks = new ArrayList<Stack<Character>>();

		for (var i = 0; i < 9 ; ++i)
		{
			stacks.add(new Stack<Character>());
		}
		
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
		    
		    var fromStack = stacks.get(from);
		    var toStack = stacks.get(to);
		    
		    for (var c = 0; c < count; ++c)
		    {
		    	toStack.push(fromStack.pop());
		    }
		}
		
		for (var stack : stacks)
		{
			System.out.print(stack.peek());
		}
	}

}
