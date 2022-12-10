package AdventOfCode;

import java.util.ArrayList;
import java.util.HashSet;

public class Rope {

	private final HashSet<Vector2> _visitedPositions;
	
	private ArrayList<Vector2> _knots;
	
	public Rope(int numberOfKnots)
	{
		_visitedPositions = new HashSet<Vector2>();
		
		_knots = new ArrayList<Vector2>();
		_knots.add(new Vector2(0, 0)); // head
		
		for (int i = 0; i < numberOfKnots; i++)
		{
			_knots.add(new Vector2(0, 0));
		}
		
		_visitedPositions.add(_knots.get(_knots.size() - 1));
	}
	
	public int getNumVisitedTailPositions()
	{
		return _visitedPositions.size();
	}
	
	public void moveBy(Vector2 direction)
	{
		_knots.set(0, _knots.get(0).plus(direction));
		
		// Move the rest of the knots along
		for (int i = 1; i < _knots.size(); ++i)
		{
			var distance = _knots.get(i).distanceTo(_knots.get(i-1));
			var distanceNormalised = new Vector2(
					distance.getX() != 0 ? distance.getX() / Math.abs(distance.getX()) : 0, 
					distance.getY() != 0 ? distance.getY() / Math.abs(distance.getY()) : 0);
			
			if (Math.abs(distance.getX()) > 1 || Math.abs(distance.getY()) > 1)
			{
				_knots.set(i, _knots.get(i).plus(distanceNormalised));
			}
		}
		
		//  Record tail position
		_visitedPositions.add(_knots.get(_knots.size() - 1));
	}
}
