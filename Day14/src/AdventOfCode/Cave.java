package AdventOfCode;

import java.util.HashMap;
import java.util.List;

import AdventOfCode.Common.Vector2;

public class Cave
{
	private HashMap<Vector2, Character> _fields;
	private Vector2 _minPoint;
	private Vector2 _maxPoint;
	private Vector2 _sandEntryPoint;
	private Vector2 _activeSandBlock;
	boolean _sandBlockReachedBottom;
	boolean _canProduceSandBlocks;
	int _numberOfProducedSandBlocks = 0;
			
	public Cave(List<Trace> traces, int originX)
	{
		_canProduceSandBlocks = true;
		_sandBlockReachedBottom = false;
		_fields = new HashMap<Vector2, Character>();
		
		_minPoint = new Vector2(
			traces.stream().map(t -> t.getMin().getX()).min(Integer::compare).get(),
			traces.stream().map(t -> t.getMin().getY()).min(Integer::compare).get()
		);
		
		_maxPoint = new Vector2(
			traces.stream().map(t -> t.getMax().getX()).max(Integer::compare).get(),
			traces.stream().map(t -> t.getMax().getY()).max(Integer::compare).get()
		);
		
		_minPoint = new Vector2(_minPoint.getX(), Math.min(_minPoint.getY(), 0));
		_maxPoint = new Vector2(_maxPoint.getX(), _maxPoint.getY() + 2);
		
		// Sand will start pouring in at the top, at originX
		_sandEntryPoint = new Vector2(originX, _minPoint.getY());
		
		for (int y = _minPoint.getY(); y <= _maxPoint.getY(); y++)
		{
			for (int x = _minPoint.getX(); x <= _maxPoint.getX(); x++)
			{
				_fields.put(new Vector2(x, y), '.');
			}
		}
		
		for (var trace : traces)
		{
			for (int i = 0; i < trace.getPoints().size() - 1; ++i)
			{
				var a = trace.getPoints().get(i);
				var b = trace.getPoints().get(i+1);
				
				var incrX = (b.getX() - a.getX()) / Math.max(1, Math.abs(b.getX() - a.getX()));
				var incrY = (b.getY() - a.getY()) / Math.max(1, Math.abs(b.getY() - a.getY()));

				for (var y = a.getY(); incrY == 0 || y != b.getY() + incrY; y += incrY)
				{
					for (var x = a.getX(); incrX == 0 || x != b.getX() + incrX; x += incrX)
					{
						_fields.put(new Vector2(x, y), '#');
						
						if (incrX == 0) break;
					}
					
					if (incrY == 0) break;
				}
			}
		}
	}
	
	private final Vector2 _down = new Vector2(0, +1);
	private final Vector2 _downLeft = new Vector2(-1, +1);
	private final Vector2 _downRight = new Vector2(+1, +1);
	
	public void runFrame()
	{
		if (!_canProduceSandBlocks) return;
		
		if (_activeSandBlock == null)
		{
			// Can we produce any more blocks?
			if (_fields.get(_sandEntryPoint) != '.')
			{
				_canProduceSandBlocks = false;
				return;
			}
			
			_activeSandBlock = new Vector2(_sandEntryPoint.getX(), _sandEntryPoint.getY());
			_fields.put(_activeSandBlock, 'x');
			_numberOfProducedSandBlocks++;
			return;
		}
		
		// Hit the bottom?
		if (_activeSandBlock.getY() >= _maxPoint.getY() - 1)
		{
			_sandBlockReachedBottom = true;
			_activeSandBlock = null; // come to rest
			return;
		}
		
		// Can it fall straight down?
		if (tryMoveSandBlock(_down) || tryMoveSandBlock(_downLeft) || tryMoveSandBlock(_downRight))
		{
			// Check if sand reached bottom coordinates
			if (_activeSandBlock.getY() > _maxPoint.getY())
			{
				_sandBlockReachedBottom = true;
			}
			return;
		}
		
		// Come to rest
		_activeSandBlock = null;
	}
	
	public boolean tryMoveSandBlock(Vector2 direction)
	{
		var target = _activeSandBlock.plus(direction);
		
		if (_fields.get(target) == null || _fields.get(target) == '.')
		{
			_fields.put(target, 'x');
			_fields.put(_activeSandBlock, '.');
			_activeSandBlock = target;
			return true;
		}
		
		return false;
	}
	
	public boolean getSandBlockReachedBottom()
	{
		return _sandBlockReachedBottom;
	}
	
	public boolean getCanProduceSandBlocks()
	{
		return _canProduceSandBlocks;
	}
	
	public int getNumberOfProducedSandBlocks()
	{
		return _numberOfProducedSandBlocks;
	}
	
	@Override
	public String toString()
	{
		var output = new StringBuilder();
		
		for (int y = _minPoint.getY(); y <= _maxPoint.getY(); y++)
		{
			for (int x = _minPoint.getX(); x <= _maxPoint.getX(); x++)
			{
				if (y == _maxPoint.getY())
				{
					output.append('#');
				}
				else
				{
					output.append(_fields.get(new Vector2(x, y)));
				}
			}
			
			output.append('\n');
		}
		
		return output.toString();
	}
}
