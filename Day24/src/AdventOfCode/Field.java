package AdventOfCode;

import java.util.ArrayList;
import java.util.List;

import AdventOfCode.Common.Vector2;

public class Field
{
	private final int _width;
	private final int _height;
	public int Time;

	private final BlizzardCache _blizzardCache;
	private BlizzardCollection _blizzards;
	private Vector2 _elfPosition;
	
	// Is null if the elves decides to stay
	public Vector2 ElfDecision;
	
	private static final Vector2 Up = new Vector2(0, -1);
	private static final Vector2 Right = new Vector2(1, 0);
	private static final Vector2 Left = new Vector2(-1, 0);
	private static final Vector2 Down = new Vector2(0, 1);
	
	public Field(BlizzardCache cache, int time)
	{
		Time = time;
		ElfDecision = null;
		_blizzardCache = cache;
		_blizzards = cache.getForTime(Time);
		
		_width = _blizzardCache.getFieldWidth();
		_height = _blizzardCache.getFieldHeight();
		
		_elfPosition = getStartCoords();
	}
	
	public Field(Field other, int time, Vector2 elfDecision)
	{
		Time = time;
		ElfDecision = elfDecision;
		
		_blizzardCache = other._blizzardCache;
		_width = other._width;
		_height = other._height;
		
		_blizzards = _blizzardCache.getForTime(time);
		_elfPosition = other._elfPosition;
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
		case '>': return Right;
		case 'v': return Down;
		case '<': return Left;
		case '^': return Up;
		default: throw new IllegalArgumentException("Unknown direction");
		}
	}
	
	public static char getDirectionChar(Vector2 direction)
	{
		if (direction.equals(Right))
		{
			return '>';
		}
		else if (direction.equals(Down))
		{
			return 'v';
		}
		else if (direction.equals(Left))
		{
			return '<';
		}
		else
		{
			return '^';
		}
	}
	
	public List<Vector2> getPossibleNextDirections()
	{
		var possibilities = new ArrayList<Vector2>();

		var position = _elfPosition.plus(Up);
		if (fieldIsValid(position) && !fieldIsHitByBlizzardNextMove(position))
		{
			possibilities.add(Up);
		}
		
		position = _elfPosition.plus(Down);
		if (fieldIsValid(position) && !fieldIsHitByBlizzardNextMove(position))
		{
			possibilities.add(Down);
		}
		
		position = _elfPosition.plus(Right);
		if (fieldIsValid(position) && !fieldIsHitByBlizzardNextMove(position))
		{
			possibilities.add(Right);
		}
		
		position = _elfPosition.plus(Left);
		if (fieldIsValid(position) && !fieldIsHitByBlizzardNextMove(position))
		{
			possibilities.add(Left);
		}
		
		return possibilities;
	}
	
	private boolean fieldIsValid(Vector2 position)
	{
		if (position.getX() < 0 || position.getX() >= _width) return false;
		if (position.getY() < 0 || position.getY() >= _height) return false;

		return true;
	}

	public boolean elfIsHitByBlizzardNextMove()
	{
		return fieldIsHitByBlizzardNextMove(_elfPosition); 
	}
	
	public boolean fieldIsHitByBlizzardNextMove(Vector2 position)
	{
		// Check the surrounding fields for blizzards
		if (fieldContainsBlizzardThatMovesTo(position.plus(Up), position) ||
			fieldContainsBlizzardThatMovesTo(position.plus(Down), position) ||
			fieldContainsBlizzardThatMovesTo(position.plus(Left), position) ||
			fieldContainsBlizzardThatMovesTo(position.plus(Right), position))
		{
			return true;
		}
		
		return false;
	}
	
	public boolean fieldContainsBlizzardThatMovesTo(Vector2 blizzardPosition, Vector2 targetField)
	{
		return false;
		/*
		// Get all blizzards in that field
		var blizzards = _blizzardPositions.get(blizzardPosition);
		
		// If any of them is moving towards the target field, return true
		return blizzards != null && !blizzards.isEmpty() &&
			blizzards.stream().anyMatch(b -> b.Position.plus(b.Direction).equals(targetField));
		*/
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
				
				text.append(_blizzards.getBlizzardCharacter(position.getX(), position.getY()));
			}
			
			text.append('#'); // right wall
			text.append('\n');
		}
		
		return text.toString();
	}

	public void moveElf()
	{
		if (ElfDecision != null)
		{
			_elfPosition = _elfPosition.plus(ElfDecision);
			ElfDecision = null;
		}
	}

	public void moveBlizzards()
	{
		
	}

	public boolean targetReached()
	{
		return _elfPosition.equals(getTargetCoords());
	}
}
