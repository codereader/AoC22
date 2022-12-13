package AdventOfCode;

import java.util.Arrays;
import java.util.List;

import AdventOfCode.Common.Vector2;

public class Map {

	private int[][] _heights;
	private Vector2 _startPosition;
	private Vector2 _targetPosition;
	
	public Map(int width, int height)
	{
		_heights = new int[height][width];
		
		for (int row = 0; row < _heights.length; row++)
		{
			Arrays.fill(_heights[row], 0);
		}
	}
	
	public Vector2 getStartPosition()
	{
		return _startPosition;
	}
	
	public Vector2 getTargetPosition()
	{
		return _targetPosition;
	}
	
	public int getHeight(int row, int col)
	{
		return _heights[row][col];
	}
	
	public int getHeight(Vector2 position)
	{
		return _heights[position.getY()][position.getX()];
	}
	
	public Vector2 getDimensions()
	{
		return new Vector2(_heights[0].length, _heights.length);
	}
	
	public void setHeight(int row, int col, int height)
	{
		_heights[row][col] = height;
	}

	public static Map FromLines(List<String> lines)
	{
		var width = lines.get(0).length();
		var height = lines.size();
		
		var map = new Map(width, height);
		
		for (int row = 0; row < lines.size(); row++)
		{
			var line = lines.get(row);
			
			for (int col = 0; col < line.length(); col++)
			{
				var value = line.charAt(col) - 'a';
				
				if (line.charAt(col) == 'S')
				{
					value = 0;
					map._startPosition = new Vector2(col, row);
				}
				else if (line.charAt(col) == 'E')
				{
					value = 25;
					map._targetPosition = new Vector2(col, row);
				}
				
				map.setHeight(row, col, value);
			}
		}
		
		return map;
	}
}
