package AdventOfCode;

import java.util.List;

import AdventOfCode.Common.Vector2;

public class Field
{
	protected final List<String> _lines;
	
	public Field(List<String> lines)
	{
		_lines = lines;
	}
	
	public Vector2 getStartPosition()
	{
		return new Vector2(_lines.get(0).indexOf('.'), 0);
	}
	
	public int getWidth(int y)
	{
		return _lines.get(y).length();
	}
	
	public int getHeight()
	{
		return _lines.size(); 
	}
	
	public char getBlock(Vector2 position)
	{
		return getBlock(position.getX(), position.getY());
	}
	
	public char getBlock(int x, int y)
	{
		var line = _lines.get(y);
		
		if (x < 0 || x >= line.length()) return ' ';
		
		return line.charAt(x);
	}
	
	public boolean blockIsSolid(Vector2 position)
	{
		return getBlock(position) == '#';
	}
	
	public Vector2 getForwardPosition(State state)
	{
		var forward = state.getForwardDirection();
		
		var x = state.Position.getX();
		
		// Move y first, wrapping around to a valid field
		var y = (state.Position.getY() + forward.getY() + getHeight()) % getHeight();
		
		while (getBlock(x, y) == ' ')
		{
			y = (y + forward.getY() + getHeight()) % getHeight();
		}
		
		// Move x, wrapping to a valid field
		var lineWidth = getWidth(y);
		
		x = (x + forward.getX() + lineWidth) % lineWidth; 
		
		while (getBlock(x, y) == ' ')
		{
			x = (x + forward.getX() + lineWidth) % lineWidth;
		}
		
		return new Vector2(x, y);
	}
	
	public void moveForward(State state)
	{
		state.Position = getForwardPosition(state);
	}
	
	public void printState(State state)
	{
		var text = new StringBuilder();

		for (int i = 0; i < _lines.size(); i++)
		{
			var line = _lines.get(i);
			var builder = new StringBuilder(line);
			
			if (state.Position.getY() == i)
			{
				builder.setCharAt(state.Position.getX(), state.getDirectionChar());
			}

			builder.append('\n');
			text.append(builder.toString());
		}
		
		System.out.println(text);
		System.out.println("-------------------------");
	}
}
