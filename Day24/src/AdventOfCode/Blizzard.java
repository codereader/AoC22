package AdventOfCode;

import AdventOfCode.Common.Vector2;

public class Blizzard
{
	public Vector2 Position;
	public int Direction;
	
	public static final int Right = 1 << 0;
	public static final int Left = 1 << 1;
	public static final int Down = 1 << 2;
	public static final int Up = 1 << 3;
	
	public static int GetDirection(char direction)
	{
		switch (direction)
		{
		case '>': return Right;
		case 'v': return Down;
		case '<': return Left;
		case '^': return Up;
		default: throw new IllegalArgumentException("Unknown direction");
		}
	}
}
