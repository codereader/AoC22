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
	
	private static final Vector2 Up = new Vector2(0, -1);
	private static final Vector2 Right = new Vector2(1, 0);
	private static final Vector2 Left = new Vector2(-1, 0);
	private static final Vector2 Down = new Vector2(0, 1);
	
	public Field(BlizzardCache cache, int time)
	{
		Time = time;
		_blizzardCache = cache;
		_blizzards = cache.getForTime(Time);
		
		_width = _blizzardCache.getFieldWidth();
		_height = _blizzardCache.getFieldHeight();
		
		_elfPosition = getStartCoords();
	}
	
	public Field(Field other, int time, Vector2 elfDecision)
	{
		Time = time;
		_blizzardCache = other._blizzardCache;
		_blizzards = _blizzardCache.getForTime(Time);
		
		_width = other._width;
		_height = other._height;
		
		_elfPosition = elfDecision != null ? _elfPosition = other._elfPosition.plus(elfDecision) : other._elfPosition;
	}
	
	public Vector2 getStartCoords()
	{
		return new Vector2(0, -1);
	}
	
	public Vector2 getTargetCoords()
	{
		return new Vector2(_width - 1, _height);
	}

	public List<Vector2> getPossibleNextDirections()
	{
		var possibilities = new ArrayList<Vector2>();

		var position = _elfPosition.plus(Down);

		if (position.equals(getTargetCoords()))
		{
			possibilities.add(Down);
			return possibilities; // out of here!
		}
		
		if (fieldIsValid(position) && !fieldIsHitByBlizzardNextMove(position))
		{
			possibilities.add(Down);
		}
		
		position = _elfPosition.plus(Up);
		
		if (fieldIsValid(position) && !fieldIsHitByBlizzardNextMove(position))
		{
			possibilities.add(Up);
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
		return _blizzards.blizzardIsMovingTo(position);
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

	public void moveBlizzards()
	{
		
	}

	public boolean targetReached()
	{
		return _elfPosition.equals(getTargetCoords());
	}
}
