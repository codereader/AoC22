package AdventOfCode.Common;

import java.util.Objects;

public class Vector2 {
	
	private final int _x;
	private final int _y;
	
	public Vector2(int x, int y)
	{
		_x = x;
		_y = y;
	}
	
	public int getX()
	{
		return _x;
	}
	
	public int getY()
	{
		return _y;
	}
	
	public Vector2 plus(Vector2 other)
	{
		return new Vector2(_x + other._x, _y + other._y);
	}
	
	public Vector2 times(int factor)
	{
		return new Vector2(_x * factor, _y * factor);
	}
	
	public Vector2 distanceTo(Vector2 other)
	{
		return new Vector2(other._x - _x, other._y - _y);
	}
	
	@Override
    public boolean equals(Object o)
	{
        if (this == o) return true;
        
        if (o == null || getClass() != o.getClass())
        {
        	return false;
        }
        
        var other = (Vector2)o;
        return _x == other._x && _y == other._y;
    }

    @Override
    public int hashCode()
    {
    	return Objects.hash(_x, _y);
    }
	
    @Override
    public String toString()
    {
    	return String.format("%d,%d", _x, _y);
    }
}
