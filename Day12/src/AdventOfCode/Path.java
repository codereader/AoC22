package AdventOfCode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import AdventOfCode.Common.Vector2;

public class Path
{
	private final Map _map;
	private List<Vector2> _positions;
	private HashSet<Vector2> _positionSet;
	
	public Path(Map map)
	{
		_map = map;
		_positions = new ArrayList<Vector2>();
		_positionSet = new HashSet<Vector2>();
		
		add(_map.getStartPosition());
	}
	
	public int getLength()
	{
		return _positions.size() - 1; // start position step doesn't count
	}
	
	public void add(Vector2 position)
	{
		_positions.add(position);
		_positionSet.add(position);
	}
	
	public boolean contains(Vector2 position)
	{
		return _positionSet.contains(position);
	}

	public boolean isReachingGoal()
	{
		if (!_positions.get(_positions.size() - 1).equals(_map.getTargetPosition()))
		{
			return false;
		}
		
		var currentHeight = _map.getHeight(_positions.get(0));
		
		for (int i = 1; i < _positions.size(); i++)
		{
			if (currentHeight + 1 < _map.getHeight(_positions.get(i)))
			{
				return false;
			}
		}
		
		return false;
	}
	
	public Vector2 getTail()
	{
		return _positions.get(_positions.size() - 1);
	}
	
	public boolean isAimedAtTarget()
	{
		return getTail().equals(_map.getTargetPosition());
	}
	
	@Override
    public boolean equals(Object o)
	{
        if (this == o) return true;
        
        if (o == null || getClass() != o.getClass())
        {
        	return false;
        }
        
        var other = (Path)o;
        return other._positions.equals(this._positions);
    }

    @Override
    public int hashCode()
    {
    	return _positions.hashCode();
    }
}
