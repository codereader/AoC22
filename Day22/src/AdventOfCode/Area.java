package AdventOfCode;

import java.util.ArrayList;

import AdventOfCode.Common.Vector2;

public class Area
{
	public class Connection
	{
		public Area TargetArea;
		public Vector2 OriginalDirection;
		public Vector2 NewDirection;
	}
	
	public Vector2 UpperLeft;
	public Vector2 LowerRight;
	
	public ArrayList<Connection> Connections;
	
	public Area(int x, int y, int width, int height)
	{
		UpperLeft = new Vector2(x, y);
		LowerRight = UpperLeft.plus(new Vector2(width - 1, height - 1));
		Connections = new ArrayList<>();
	}
	
	public boolean contains(Vector2 position)
	{
		return position.getX() >= UpperLeft.getX() && 
			position.getX() <= LowerRight.getX() &&
			position.getY() >= UpperLeft.getY() &&
			position.getY() <= LowerRight.getY();
	}
	
	public void addConnection(Area toArea, Vector2 originalDirection, Vector2 newDirection)
	{
		addConnection(toArea, originalDirection, newDirection, true);
	}
	
	private void addConnection(Area toArea, Vector2 originalDirection, Vector2 newDirection, boolean registerOpposite)
	{
		var conn = new Connection();
		conn.TargetArea = toArea;
		conn.OriginalDirection = originalDirection;
		conn.NewDirection = newDirection;
		Connections.add(conn);
		
		if (registerOpposite)
		{
			// Connect the area back to this one
			toArea.addConnection(this, newDirection.times(-1), originalDirection.times(-1), false);
		}
	}
}
