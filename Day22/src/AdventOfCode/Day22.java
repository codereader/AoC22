package AdventOfCode;

import java.util.List;
import java.util.stream.Collectors;

import AdventOfCode.Common.FileUtils;
import AdventOfCode.Common.Matrix3;
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
		var edgeLength = 4;
		var cube = new Cube(lines.stream().limit(lines.size() - 2).collect(Collectors.toList()), edgeLength);
		
		var N = edgeLength - 1;
		var area1 = cube.addArea(new Area(8, 0, edgeLength, edgeLength)); // 1 (index 0)
		var area4 = cube.addArea(new Area(8, 4, edgeLength, edgeLength)); // 4 (index 1)
		var area3 = cube.addArea(new Area(4, 4, edgeLength, edgeLength)); // 3 (index 2)
		var area2 = cube.addArea(new Area(0, 4, edgeLength, edgeLength)); // 2 (index 3)
		var area5 = cube.addArea(new Area(8, 8, edgeLength, edgeLength)); // 5 (index 4)
		var area6 = cube.addArea(new Area(12, 8, edgeLength, edgeLength)); // 6 (index 5)

		// Wraps
		area1.addConnection(Down, area4, Matrix3.TranslationY(-N));
		area4.addConnection(Up, area1, Matrix3.TranslationY(N));
		
		area4.addConnection(Down, area5, Matrix3.TranslationY(-N));
		area5.addConnection(Up, area4, Matrix3.TranslationY(N));
		
		area5.addConnection(Right, area6, Matrix3.TranslationX(-N));
		area6.addConnection(Left, area5, Matrix3.TranslationX(N));
		
		area3.addConnection(Right, area4, Matrix3.TranslationX(-N));
		area4.addConnection(Left, area3, Matrix3.TranslationX(N));
		
		area2.addConnection(Right, area3, Matrix3.TranslationX(-N));
		area3.addConnection(Left, area2, Matrix3.TranslationX(N));
		
		area1.addConnection(Left, area3, Matrix3.MirrorY().getMultipliedBy(Matrix3.RotationCcw90()));
		area3.addConnection(Up, area1, Matrix3.RotationCw90().getMultipliedBy(Matrix3.MirrorY()));
		
		// From 1 => 2
		area1.addConnection(Up, area2, Matrix3.TranslationX(N).getMultipliedBy(Matrix3.Rotation180()));
		area2.addConnection(Up, area1, Matrix3.Rotation180().getMultipliedBy(Matrix3.TranslationX(-N)));
		
		// From 4 => 6 rotate CW, translate and mirror Y
		area4.addConnection(Right, area6, 
				Matrix3.Translation(new Vector2(N, -N))
				.getMultipliedBy(Matrix3.RotationCw90()));
		area6.addConnection(Up, area4, 
				Matrix3.RotationCcw90()
				.getMultipliedBy(Matrix3.Translation(new Vector2(-N, N))));
		
		// From 1 to 6
		area1.addConnection(Right, area6, Matrix3.Translation(new Vector2(2*N, N)) 
				.getMultipliedBy(Matrix3.MirrorX())
				.getMultipliedBy(Matrix3.MirrorY()));
		area6.addConnection(Right, area1, Matrix3.MirrorY()
				.getMultipliedBy(Matrix3.MirrorX())
				.getMultipliedBy(Matrix3.Translation(new Vector2(2*N, N))));
		
		// Area 3 to Area 5
		area3.addConnection(Down, area5, Matrix3.MirrorX()
				.getMultipliedBy(Matrix3.RotationCcw90())
				.getMultipliedBy(Matrix3.Translation(new Vector2(-N, -N))));
		area5.addConnection(Left, area3, Matrix3.Translation(new Vector2(-N, -N))
				.getMultipliedBy(Matrix3.RotationCw90())
				.getMultipliedBy(Matrix3.MirrorX()));
		
		// Area 2 to Area 6
		area2.addConnection(Left, area6, Matrix3.MirrorY()
				.getMultipliedBy(Matrix3.Translation(new Vector2(N, -N)))
				.getMultipliedBy(Matrix3.RotationCw90()));
		area6.addConnection(Down, area2, Matrix3.RotationCcw90()
				.getMultipliedBy(Matrix3.Translation(new Vector2(-N, N)))
				.getMultipliedBy(Matrix3.MirrorY()));
		
		// Area 2 to Area 5
		area2.addConnection(Down, area5, Matrix3.Translation(new Vector2(N, N))
				.getMultipliedBy(Matrix3.MirrorX())
				.getMultipliedBy(Matrix3.MirrorY())
				.getMultipliedBy(Matrix3.TranslationY(-N)));
		
		area5.addConnection(Down, area2, Matrix3.TranslationY(N)
				.getMultipliedBy(Matrix3.MirrorY())
				.getMultipliedBy(Matrix3.MirrorX())
				.getMultipliedBy(Matrix3.Translation(new Vector2(-N, -N))));
		
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
				cube.moveForward(state);
				cube.printState(state);
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
