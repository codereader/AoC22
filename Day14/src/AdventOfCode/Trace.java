package AdventOfCode;

import java.util.ArrayList;

import AdventOfCode.Common.Vector2;

public class Trace {

	private ArrayList<Vector2> _points;
	
	public Trace(String line)
	{
		_points = new ArrayList<Vector2>();
		
		for (var pair : line.split(" -> "))
		{
			var coordinates = pair.split(",");
			// Measure X relative to xOrigin
			_points.add(new Vector2(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])));
		}
	}
	
	public ArrayList<Vector2> getPoints()
	{
		return _points;
	}
	
	public Vector2 getMin()
	{
		return new Vector2(
			_points.stream().map(v -> v.getX()).min(Integer::compare).get(),
			_points.stream().map(v -> v.getY()).min(Integer::compare).get()
		);
	}
	
	public Vector2 getMax()
	{
		return new Vector2(
			_points.stream().map(v -> v.getX()).max(Integer::compare).get(),
			_points.stream().map(v -> v.getY()).max(Integer::compare).get()
		);
	}
}
