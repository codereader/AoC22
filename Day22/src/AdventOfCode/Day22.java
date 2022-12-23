package AdventOfCode;

import java.util.List;
import java.util.stream.Collectors;

import AdventOfCode.Common.FileUtils;
import AdventOfCode.Common.Vector2;

public class Day22
{
	public static void main(String[] args)
	{
		var lines = FileUtils.readFile("./test.txt");
		
		runPart1(lines); // 75254
		runPart2(lines);
	}
	
	private final static Vector2 Down = new Vector2(0, 1);
	private final static Vector2 Up = new Vector2(0, -1);
	private final static Vector2 Right= new Vector2(1, 0);
	private final static Vector2 Left = new Vector2(-1, 0);
	
	private static void runPart2(List<String> lines)
	{
		var commands = lines.get(lines.size() - 1);
		var cube = new Cube(lines.stream().limit(lines.size() - 2).collect(Collectors.toList()));
		
		var area1 = cube.addArea(new Area(8, 0, 4, 4)); // 1 (index 0)
		var area4 = cube.addArea(new Area(8, 4, 4, 4)); // 4 (index 1)
		var area3 = cube.addArea(new Area(4, 4, 4, 4)); // 3 (index 2)
		var area2 = cube.addArea(new Area(0, 4, 4, 4)); // 2 (index 3)
		var area5 = cube.addArea(new Area(8, 8, 4, 4)); // 5 (index 4)
		var area6 = cube.addArea(new Area(12, 8, 4, 4)); // 6 (index 5)
		
		// Leaving Cube 1 to the left, reaches Cube 3 with a new direction
		area1.addConnection(area3, Left, Down);
		area1.addConnection(area2, Up, Down);
		area1.addConnection(area6, Right, Left);
		
		area4.addConnection(area6, Right, Down);
		
		area3.addConnection(area5, Down, Right);
		
		area2.addConnection(area6, Left, Up);
		area2.addConnection(area5, Down, Up);
		
		var state = new State();
		state.Position = cube.getStartPosition();
		
		var next = 0;
		while (next < commands.length())
		{
			cube.printState(state);
			
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
			
			var nextPosition = cube.getForwardPosition(state);
			var moves = Integer.parseInt(numberChars);
			
			while (moves-- > 0 && !cube.blockIsSolid(nextPosition))
			{
				state.Position = nextPosition;
				nextPosition = cube.getForwardPosition(state);
			}
		}
		
		System.out.println(String.format("Final State: %s", state));
		System.out.println(String.format("[Part2]: Password: %d", state.getPassword()));
	}

	private static void runPart1(List<String> lines)
	{
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
			
			var nextPosition = field.getForwardPosition(state);
			var moves = Integer.parseInt(numberChars);
			
			while (moves-- > 0 && !field.blockIsSolid(nextPosition))
			{
				state.Position = nextPosition;
				nextPosition = field.getForwardPosition(state);
			}
		}
		
		System.out.println(String.format("Final State: %s", state));
		System.out.println(String.format("[Part1]: Password: %d", state.getPassword()));
	}
}
