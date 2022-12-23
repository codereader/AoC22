package AdventOfCode;

import java.util.stream.Collectors;

import AdventOfCode.Common.FileUtils;

public class Day22
{
	public static void main(String[] args)
	{
		var lines = FileUtils.readFile("./input.txt");
		
		var commands = lines.get(lines.size() - 1);
		var field = new Field(lines.stream().limit(lines.size() - 2).collect(Collectors.toList()));
		
		var state = new State();
		state.Position = field.getStartPosition();
		
		var next = 0;
		while (next < commands.length())
		{
			//field.printState(state);
			
			var ch = commands.charAt(next);
			
			if (ch == 'R')
			{
				state.turnRight();
				++next;
				continue;
			}
			else if (ch == 'L')
			{
				state.turnLeft();
				++next;
				continue;
			}
			
			var numberChars = "";
					
			while (next < commands.length() && Character.isDigit(commands.charAt(next)))
			{
				numberChars += commands.charAt(next++);
			}
			
			var nextPosition = state.getForwardPosition(field);
			var moves = Integer.parseInt(numberChars);
			
			while (moves-- > 0 && !field.blockIsSolid(nextPosition))
			{
				state.Position = nextPosition;
				nextPosition = state.getForwardPosition(field);
			}
		}
		
		System.out.println(String.format("Final State: %s", state));
		System.out.println(String.format("[Part1]: Password: %d", state.getPassword()));
		
	}
}
