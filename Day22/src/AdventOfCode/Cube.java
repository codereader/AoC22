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
		return super.getForwardPosition(state); // TODO
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

	public void addConnectivity(int fromArea, Vector2 originalDirection, int toArea, Vector2 newDirection)
	{
		
	}
}
