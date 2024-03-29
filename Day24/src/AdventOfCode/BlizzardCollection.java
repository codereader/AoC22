package AdventOfCode;

import java.util.ArrayList;
import java.util.List;

import AdventOfCode.Common.Vector2;

public class BlizzardCollection
{
	private final short[][] _field;
	private final int _width;
	private final int _height;
	
	public BlizzardCollection(int width, int height)
	{
		_width = width;
		_height = height;
		_field = new short[_height][_width];
	}
	
	public void addBlizzard(int x, int y, int blizzardDirection)
	{
		_field[y][x] |= blizzardDirection;
	}
	
	// X and Y can exceed the field width safely
	private int getBlizzardAt(int x, int y)
	{
		while (y < 0) y += _height;
		while (x < 0) x += _width;
		
		return _field[y % _height][x % _width];
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

	public boolean blizzardIsMovingTo(int x, int y)
	{
		// Blizzards cannot hit outside fields
		if (x < 0 || x >= _width) return false;
		if (y < 0 || y >= _height) return false;
		
		return ((getBlizzardAt(x - 1, y) & Blizzard.Right) != 0 ||
			    (getBlizzardAt(x + 1, y) & Blizzard.Left) != 0 ||
			    (getBlizzardAt(x, y - 1) & Blizzard.Down) != 0 ||
		        (getBlizzardAt(x, y + 1) & Blizzard.Up) != 0);
	}
}
