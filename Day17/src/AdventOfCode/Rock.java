package AdventOfCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import AdventOfCode.Common.Vector2;

public class Rock
{
	public final static char Solid = '#';
	
	public int Width;
	public int Height;
	public List<String> _shape;
	
	public Vector2 Position;
	
	public Rock(String... lines)
	{
		_shape = Arrays.asList(lines);
		Width = _shape.stream().mapToInt(s -> s.length()).max().getAsInt();
		Height = _shape.size();
		Position = new Vector2(0,0);
	}
	
	private Rock(Rock other)
	{
		_shape = new ArrayList<String>(other._shape);
		Width = other.Width;
		Height = other.Height;
		Position = other.Position;
	}
	
	// Local positions are measured from the top left of the rock shape
	public boolean isSolidAt(Vector2 localPosition)
	{
		if (localPosition.getX() < 0 || localPosition.getX() >= Width ||
			localPosition.getY() < 0 || localPosition.getY() >= Height)
		{
			throw new IllegalArgumentException("Local Position out of bounds");
		}
		
		return _shape.get(localPosition.getY()).charAt(localPosition.getX()) == Solid;
	}
	
	@Override
	public Object clone()
	{
		return new Rock(this);
	}
}
