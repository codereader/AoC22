package AdventOfCode;

import java.util.ArrayList;

import AdventOfCode.Common.Matrix3;
import AdventOfCode.Common.Vector2;

public class Area
{
	public class Connection
	{
		public Area TargetArea;
		public Matrix3 Transform;
		public Vector2 Direction;
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
	
	public void addConnection(Vector2 direction, Area toArea, Matrix3 transform)
	{
		var conn = new Connection();
		conn.TargetArea = toArea;
		conn.Transform = transform;
		conn.Direction = direction;
		
		Connections.add(conn);
	}

	public Connection getTargetAreaForDirection(Vector2 forward)
	{
		for (var conn : Connections)
		{
			if (conn.Direction.equals(forward))
			{
				return conn;
			}
		}
		
		return null;
	}
}
