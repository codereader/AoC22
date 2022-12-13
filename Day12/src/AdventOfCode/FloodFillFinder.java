package AdventOfCode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.function.BiFunction;

import AdventOfCode.Common.Vector2;

public class FloodFillFinder
{
	private final Map _map;
	private final Vector2 _mapDimensions;
	private HashSet<Vector2> _positionsToInvestigate;
	private BiFunction<Integer, Integer, Boolean> _stepEvaluator;
	
	private class Field
	{
		public Field(int cost, int height)
		{
			Cost = cost;
			Height = height;
		}
		
		public int Cost = 0;
		public int Height = 0;
	}
	
	private HashMap<Vector2, Field> _fields;
	
	public FloodFillFinder(Map map, BiFunction<Integer, Integer, Boolean> stepEvaluator)
	{
		_map = map;
		_mapDimensions = _map.getDimensions();
		_fields = new HashMap<Vector2, Field>();
		_stepEvaluator = stepEvaluator;
	}
	
	final Vector2 _up = new Vector2(0, 1);
	final Vector2 _down = new Vector2(0, -1);
	final Vector2 _left = new Vector2(-1, 0);
	final Vector2 _right = new Vector2(1 ,0);
	
	public void evaluateField(Vector2 startPosition)
	{
		_fields.clear();
		_positionsToInvestigate = new HashSet<Vector2>();
		
		_positionsToInvestigate.add(startPosition);
		_fields.put(startPosition, new Field(0, _map.getHeight(startPosition))); // start position has cost 0
		
		while (!_positionsToInvestigate.isEmpty())
		{
			var position = _positionsToInvestigate.iterator().next();
			_positionsToInvestigate.remove(position);
			
			var field = _fields.get(position);
			
			inspectPosition(position.plus(_up), field);
			inspectPosition(position.plus(_down), field);
			inspectPosition(position.plus(_left), field);
			inspectPosition(position.plus(_right), field);
		}
		
		/*// Dump field to console
		for (int row = 0; row < _mapDimensions.getY(); row++)
		{
			for (int col = 0; col < _mapDimensions.getX(); col++)
			{
				var pos = new Vector2(col, row);
				if (_fields.containsKey(pos))
				{
					var field = _fields.get(pos);
					System.out.print(String.format("%c%03d ", 'a' + (char)_map.getHeight(pos), field.Cost));
				}
				else
				{
					System.out.print(String.format("%c??? ",'a' + (char)_map.getHeight(pos)));
				}
			}
			
			System.out.println();
		}*/
	}
	
	public int getCost(Vector2 position)
	{
		if (!_fields.containsKey(position))
		{
			return Integer.MAX_VALUE;
		}
		
		return _fields.get(position).Cost;
	}
	
	public static boolean oneStepUpwardsAnyDownwards(int sourceHeight, int destinationHeight)
	{
		return destinationHeight <= sourceHeight + 1;
	}
	
	private void inspectPosition(Vector2 position, Field sourceField)
	{
		if (position.getX() < 0 || position.getX() >= _mapDimensions.getX() ||
			position.getY() < 0 || position.getY() >= _mapDimensions.getY())
		{
			return; // not a valid position
		}
		
		// Get the height of that field
		int height = _map.getHeight(position);
		
		if (!_stepEvaluator.apply(sourceField.Height, height))
		{
			return; // not reachable from here
		}
		
		// Position is reachable, at what cost?
		int cost = sourceField.Cost + 1;
		
		// Would the cost be cheaper than what we already know about this field?
		if (!_fields.containsKey(position))
		{
			_fields.put(position, new Field(cost, height));
			
			// Add this new field to the investigation list
			_positionsToInvestigate.add(position);
		}
		else
		{
			var field = _fields.get(position);
			
			if (field.Cost > cost)
			{
				field.Cost = cost;
				
				// Re-investigate from here
				_positionsToInvestigate.add(position);
			}
			else
			{
				// We already know a better path to this field, do nothing
			}
		}
	}
}
