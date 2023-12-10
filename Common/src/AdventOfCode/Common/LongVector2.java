package AdventOfCode.Common;

import java.util.Objects;

public class LongVector2
{
	private final long _x;
	private final long _y;
	
	public LongVector2(long x, long y)
	{
		_x = x;
		_y = y;
	}
	
	public long getX()
	{
		return _x;
	}
	
	public long getY()
	{
		return _y;
	}
	
	public LongVector2 plus(LongVector2 other)
	{
		return new LongVector2(_x + other._x, _y + other._y);
	}
	
	public LongVector2 times(long factor)
	{
		return new LongVector2(_x * factor, _y * factor);
	}
	
	public LongVector2 distanceTo(LongVector2 other)
	{
		return new LongVector2(other._x - _x, other._y - _y);
	}
	
	@Override
    public boolean equals(Object o)
	{
        if (this == o) return true;
        
        if (o == null || getClass() != o.getClass())
        {
        	return false;
        }
        
        var other = (LongVector2)o;
        return _x == other._x && _y == other._y;
    }

    @Override
    public int hashCode()
    {
    	return Objects.hash(_x, _y);
    }
}
