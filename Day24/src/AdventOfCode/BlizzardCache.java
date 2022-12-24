package AdventOfCode;

import java.util.HashMap;
import java.util.List;

import AdventOfCode.Common.Vector2;

public class BlizzardCache
{
	private final HashMap<Integer, BlizzardCollection> _blizzardsByTime;
	private final List<Blizzard> _blizzards;
	private final int _fieldWidth;
	private final int _fieldHeight;
	
	public BlizzardCache(List<Blizzard> blizzards, int width, int height)
	{
		_blizzardsByTime = new HashMap<>();
		_blizzards = blizzards;
		_fieldWidth = width;
		_fieldHeight = height;
	}
	
	public int getFieldWidth()
	{
		return _fieldWidth;
	}
	
	public int getFieldHeight()
	{
		return _fieldHeight;
	}
	
	private static final Vector2 Up = new Vector2(0, -1);
	private static final Vector2 Right = new Vector2(1, 0);
	private static final Vector2 Left = new Vector2(-1, 0);
	private static final Vector2 Down = new Vector2(0, 1);
	
	public BlizzardCollection getForTime(int time)
	{
		return _blizzardsByTime.computeIfAbsent(time, t -> generateForTime(t));
	}
	
	private BlizzardCollection generateForTime(int time)
	{
		var collection = new BlizzardCollection(time, _fieldWidth, _fieldHeight);
		
		for (var blizzard : _blizzards)
		{
			switch (blizzard.Direction)
			{
			case Blizzard.Right:
				collection.addBlizzard((blizzard.Position.getX() + time) % _fieldWidth, 
						blizzard.Position.getY(), blizzard.Direction);
				break;
				
			case Blizzard.Left:
				var x = blizzard.Position.getX() - time;
				while (x < 0) x += _fieldWidth;
				collection.addBlizzard(x, blizzard.Position.getY(), blizzard.Direction);
				break;
				
			case Blizzard.Up:
				var y = blizzard.Position.getY() - time;
				while (y < 0) y += _fieldHeight;
				collection.addBlizzard(blizzard.Position.getX(), y, blizzard.Direction);
				break;
			
			case Blizzard.Down:
				collection.addBlizzard(blizzard.Position.getX(), 
						(blizzard.Position.getY() + time) % _fieldHeight, blizzard.Direction);
				break;
			}
		}
		
		return collection;
	}
}
