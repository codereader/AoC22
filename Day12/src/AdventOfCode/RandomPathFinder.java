package AdventOfCode;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;

import AdventOfCode.Common.Vector2;

public class RandomPathFinder {

	private final Map _map;
	private final Random _rand;
	private final Vector2 _mapDimensions;
	
	public RandomPathFinder(Map map)
	{
		_map = map;
		_rand = new Random();
		_mapDimensions = _map.getDimensions();
		
		_candidateDirections = new ArrayList<Vector2>();
		_candidateDirections.add(_left);
		_candidateDirections.add(_up);
		_candidateDirections.add(_right);
		_candidateDirections.add(_down);
	}
	
	public Path solve()
	{
		var foundPaths = new TreeMap<Integer, Path>();
		
		var pathsToFind = 1;
		
		while (pathsToFind > 0)
		{
			var path = generatePath();
			
			if (path == null) continue;
			
			pathsToFind--;
			foundPaths.put(path.getLength(), path);
		}
		
		return foundPaths.firstEntry().getValue();
	}

	private Path generatePath()
	{
		var path = new Path(_map); // start position is already added
		
		while (!path.isAimedAtTarget())
		{
			// Pick a new position
			var newPos = pickRandomValidNeighbour(path);
			
			if (newPos == null)
			{
				return null; // ran out of valid options, bail out
			}
			
			path.add(newPos);
		}
		
		return path;
	}
	
	final Vector2 _up = new Vector2(0, 1);
	final Vector2 _down = new Vector2(0, -1);
	final Vector2 _left = new Vector2(-1, 0);
	final Vector2 _right = new Vector2(1 ,0);
	final ArrayList<Vector2> _candidateDirections;
	
	private Vector2 pickRandomValidNeighbour(Path path)
	{
		var lastPos = path.getTail();
		var lastHeight = _map.getHeight(lastPos);
		var candidates = (ArrayList<Vector2>)_candidateDirections.clone();
			
		while (!candidates.isEmpty())
		{
			var index = _rand.nextInt(candidates.size());
			var nextPos = path.getTail().plus(candidates.get(index));
			
			if (nextPos.getX() >= 0 && nextPos.getX() < _mapDimensions.getX() &&
				nextPos.getY() >= 0 && nextPos.getY() < _mapDimensions.getY() &&
				_map.getHeight(nextPos) <= lastHeight + 1 &&
				!path.contains(nextPos))
			{
				return nextPos;
			}
			
			// No luck, try another
			candidates.remove(index);
		}
		
		return null;
	}
}
