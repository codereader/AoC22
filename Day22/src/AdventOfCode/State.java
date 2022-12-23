package AdventOfCode;

import java.util.List;

import AdventOfCode.Common.Vector2;

public class State
{
	public final static int FaceRight = 0;
	public final static int FaceDown = 1;
	public final static int FaceLeft = 2;
	public final static int FaceUp = 3;
	
	public Vector2 Position;
	public int Direction; // 0 = right, 1 = down, 2 = left, 3 = up
	
	public State()
	{
		Position = new Vector2(0,0);
		Direction = FaceRight;
	}
	
	public Vector2 getForwardPosition(Field field)
	{
		var forward = getForwardDirection();
		
		var x = Position.getX();
		
		// Move y first
		var y = (Position.getY() + forward.getY() + field.getHeight()) % field.getHeight();
		
		while (field.getBlock(x, y) == ' ')
		{
			y = (y + forward.getY() + field.getHeight()) % field.getHeight();
		}
		
		// Move x
		var lineWidth = field.getWidth(y);
		
		x = (x + forward.getX() + lineWidth) % lineWidth; 
		
		while (field.getBlock(x, y) == ' ')
		{
			x = (x + forward.getX() + lineWidth) % lineWidth;
		}
		
		return new Vector2(x, y);
	}
	
	public Vector2 getForwardDirection()
	{
		switch (Direction)
		{
		case FaceRight: return new Vector2(1, 0);
		case FaceDown: return new Vector2(0, 1);
		case FaceLeft: return new Vector2(-1, 0);
		case FaceUp: return new Vector2(0, -1);
		default: throw new IllegalArgumentException("Unknown direction");
		}
	}
	
	public void turnLeft()
	{
		Direction = (Direction - 1 + 4) % 4;
	}
	
	public void turnRight()
	{
		Direction = (Direction + 1) % 4;
	}
	
	private String getDirectionString()
	{
		switch (Direction)
		{
		case FaceRight: return "Right";
		case FaceDown: return "Down";
		case FaceLeft: return "Left";
		case FaceUp: return "Up";
		default: throw new IllegalArgumentException("Unknown direction");
		}
	}
	
	public char getDirectionChar()
	{
		switch (Direction)
		{
		case FaceRight: return '>';
		case FaceDown: return 'v';
		case FaceLeft: return '<';
		case FaceUp: return '^';
		default: throw new IllegalArgumentException("Unknown direction");
		}
	}
	
	public long getPassword()
	{
		return (long)(Position.getY() + 1) * 1000 + (Position.getX() + 1) * 4 + Direction; 
	}
	
	@Override
	public String toString()
	{
		return String.format("At %d,%d facing %s", Position.getX(), Position.getY(), getDirectionString());
	}
}
