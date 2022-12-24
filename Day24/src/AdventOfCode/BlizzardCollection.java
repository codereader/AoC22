package AdventOfCode;

import java.util.ArrayList;
import java.util.List;

import AdventOfCode.Common.Vector2;

public class BlizzardCollection
{
	public int Time;
	private short[][] _field;
	
	public BlizzardCollection(int time, int width, int height)
	{
		_field = new short[height][width];
	}
	
	public void addBlizzard(int x, int y, int blizzardDirection)
	{
		_field[y][x] |= blizzardDirection;
	}
	
	public char getBlizzardCharacter(int x, int y)
	{
		switch (_field[y][x])
		{
		case 0: return '.';
		case Blizzard.Right: return '>';
		case Blizzard.Left: return '<';
		case Blizzard.Up: return '^';
		case Blizzard.Down: return 'v';
		default: return 'X';
		}
	}
	
	public static List<Blizzard> ParseFromInput(List<String> lines)
	{
		var list = new ArrayList<Blizzard>();
		int width = lines.get(0).length() - 2;
		int height = lines.size() - 2;
		
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; ++x)
			{
				var ch = lines.get(y + 1).charAt(1 + x);
				
				if (ch != '.')
				{
					var blizzard = new Blizzard();
					blizzard.Position = new Vector2(x,y);
					blizzard.Direction = Blizzard.GetDirection(ch);
					list.add(blizzard);
				}
			}
		}
		
		return list;
	}
}
