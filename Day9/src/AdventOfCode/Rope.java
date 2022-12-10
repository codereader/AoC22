package AdventOfCode;

import java.util.HashSet;

public class Rope {

	private final HashSet<Vector2> _visitedPositions;
	private Vector2 _head;
	private Vector2 _tail;
	
	public Rope()
	{
		_visitedPositions = new HashSet<Vector2>();
		_head = new Vector2(0, 0);
		_tail = new Vector2(0, 0);
		
		_visitedPositions.add(_tail);
	}
	
	public int getNumVisitedTailPositions()
	{
		return _visitedPositions.size();
	}
	
	public void moveBy(Vector2 direction)
	{
		_head = _head.plus(direction);
		
		var distance = _tail.distanceTo(_head);
		var distanceNormalised = new Vector2(
				distance.getX() != 0 ? distance.getX() / Math.abs(distance.getX()) : 0, 
				distance.getY() != 0 ? distance.getY() / Math.abs(distance.getY()) : 0);
		
		if (Math.abs(distance.getX()) > 1 || Math.abs(distance.getY()) > 1)
		{
			_tail = _tail.plus(distanceNormalised);
		}
		
		_visitedPositions.add(_tail);
	}
}
