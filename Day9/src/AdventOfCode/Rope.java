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
		
		for (int i = 0; i < numberOfKnots - 1; i++)
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

	@Override
	public String toString()
	{
		int fieldWidth = 1;
		int fieldHeight = 1;
		fieldWidth = Math.max(Math.abs(_knots.get(0).getX()) * 2 + 1, fieldWidth);
		fieldHeight = Math.max(Math.abs(_knots.get(0).getY()) * 2 + 1, fieldHeight);
		
		var positions = new char[fieldHeight][fieldWidth];
		
		for (int h = 0; h < fieldHeight; ++h)
		{
			for (int w = 0; w < fieldWidth; ++w)
			{
				positions[h][w] = '.';
			}
		}
		
		for (int i = 0; i < _knots.size(); ++i)
		{
			var ropeId = i == 0 ? 'H' : (char) ('1' + i - 1);
			var knot = _knots.get(i);
			positions[-knot.getY() + fieldHeight / 2][knot.getX() + fieldWidth / 2] = ropeId;
		}
		
		var text = new StringBuilder();
		
		for (int h = 0; h < fieldHeight; ++h)
		{
			var row = new String(positions[h]);
			text.append(row);
			text.append("\n");
		}
		
		return text.toString();
	}
}
