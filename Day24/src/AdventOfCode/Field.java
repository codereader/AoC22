package AdventOfCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import AdventOfCode.Common.Vector2;

public class Field
{
	private final int _width;
	private final int _height;

	private List<Blizzard> _blizzards;
	private HashMap<Vector2, List<Blizzard>> _blizzardPositions;
	private Vector2 _elfPosition;
	
	public Field(List<String> lines)
	{
		_width = lines.get(0).length() - 2;
		_height = lines.size() - 2;
		_blizzardPositions = new HashMap<>();
		_blizzards = new ArrayList<>();
		
		for (int y = 0; y < _height; y++)
		{
			for (int x = 0; x < _width; ++x)
			{
				var ch = lines.get(y + 1).charAt(1 + x);
				
				if (ch != '.')
				{
					var blizzard = new Blizzard();
					blizzard.Position = new Vector2(x,y);
					blizzard.Direction = getDirection(ch);
					
					var list = new ArrayList<Blizzard>();
					list.add(blizzard);
					_blizzardPositions.put(blizzard.Position, list);
					_blizzards.add(blizzard);
				}
			}
		}
		
		_elfPosition = getStartCoords();
	}
	
	public Vector2 getStartCoords()
	{
		return new Vector2(0, -1);
	}
	
	public Vector2 getTargetCoords()
	{
		return new Vector2(_width - 1, _height);
	}

	private static Vector2 getDirection(char direction)
	{
		switch (direction)
		{
		case '>': return new Vector2(1, 0);
		case 'v': return new Vector2(0, 1);
		case '<': return new Vector2(-1, 0);
		case '^': return new Vector2(0, -1);
		default: throw new IllegalArgumentException("Unknown direction");
		}
	}
	
	public static char getDirectionChar(Vector2 direction)
	{
		if (direction.equals(new Vector2(1, 0)))
		{
			return '>';
		}
		else if (direction.equals(new Vector2(0, 1)))
		{
			return 'v';
		}
		else if (direction.equals(new Vector2(-1, 0)))
		{
			return '<';
		}
		else
		{
			return '^';
		}
	}
	
	@Override 
	public String toString()
	{
		var text = new StringBuilder();
		
		for (int y = -1; y < _height + 1; y++)
		{
			text.append('#'); // left wall
			
			for (int x = 0; x < _width; ++x)
			{
				var position = new Vector2(x,y);
				
				if (position.equals(_elfPosition))
				{
					text.append('E');
					continue;
				}
				
				if (y < 0 || y >= _height)
				{
					text.append(position.equals(getTargetCoords()) || position.equals(getStartCoords()) ? '.' : '#');
					continue;
				}
				
				var blizzards = _blizzardPositions.get(position);
				
				if (blizzards != null)
				{
					if (blizzards.size() == 0)
					{
						text.append('.');
					}
					else if (blizzards.size() == 1)
					{
						text.append(getDirectionChar(blizzards.get(0).Direction));
					}
					else
					{
						text.append(blizzards.size());
					}
				}
				else
				{
					text.append('.');
				}
			}
			
			text.append('#'); // right wall
			text.append('\n');
		}
		
		return text.toString();
	}
}
