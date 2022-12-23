package AdventOfCode;

import java.util.ArrayList;
import java.util.List;

import AdventOfCode.Common.Vector2;

public class Cube extends Field
{
	private List<Area> _areas;
	
	public Cube(List<String> lines)
	{
		super(lines);
		_areas = new ArrayList<Area>();
	}
	
	public Area addArea(Area area)
	{
		_areas.add(area);
		return area;
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
	
	@Override
	public Vector2 getForwardPosition(State state)
	{
		var forward = state.getForwardDirection();
		
		var currentArea = getArea(state.Position);
		
		var newPos = state.Position.plus(forward);
		
		// Leaving the area?
		if (getArea(newPos) != currentArea)
		{
			// Get the target area and transform position and direction
			var connection = currentArea.getTargetAreaForDirection(forward);
			var localCoords = getLocalAreaCoords(currentArea, state.Position);
			
			forward = connection.Transform.transformDirection(forward);
			localCoords = connection.Transform.transformPoint(localCoords);
			
			return getGlobalCoords(connection.TargetArea, localCoords);
		}
		
		return newPos;
	}
	
	// Coordinates measured from the upper left area corner
	private static Vector2 getLocalAreaCoords(Area area, Vector2 position)
	{
		return new Vector2(
			position.getX() - area.UpperLeft.getX(),
			position.getY() - area.UpperLeft.getY()
		);
	}
	
	// Coordinates measured in global coords
	private static Vector2 getGlobalCoords(Area area, Vector2 local)
	{
		return new Vector2(
			local.getX() + area.UpperLeft.getX(),
			local.getY() + area.UpperLeft.getY()
		);
	}
	
	private Area getArea(Vector2 position)
	{
		return _areas.stream().filter(a -> a.contains(position)).findFirst().orElse(null);
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

	@Override
	public void moveForward(State state)
	{
		var forward = state.getForwardDirection();
		var currentArea = getArea(state.Position);
		var newPos = state.Position.plus(forward);
		
		if (getArea(newPos) != currentArea)
		{
			// Get the target area and transform position and direction
			var connection = currentArea.getTargetAreaForDirection(forward);
			var localCoords = getLocalAreaCoords(currentArea, state.Position);
			
			forward = connection.Transform.transformDirection(forward);
			localCoords = connection.Transform.transformPoint(localCoords);
			
			state.Position = getGlobalCoords(connection.TargetArea, localCoords);
			state.setDirectionFromVector(forward);
		}
		else
		{
			state.Position = newPos;
		}
	}
}
