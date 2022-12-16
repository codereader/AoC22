package AdventOfCode;

import java.util.HashMap;
import java.util.regex.Pattern;

public class Valve {

	private final String _valveName;
	private final int _flowRate;
	private final String[] _tunnels;
	
	private HashMap<String, Integer> _travelCost;
	
	public Valve(String line)
	{
		// Valve QE has flow rate=3; tunnels lead to valves OU, ME, UX, AX, TW
		var pattern = Pattern.compile("Valve (\\w+) has flow rate=(\\d+); .* to valve[s]* (.+)", 
				Pattern.CASE_INSENSITIVE);
	    var matcher = pattern.matcher(line);
	    
	    if (!matcher.find()) throw new IllegalArgumentException("Line not supported: " + line);
	    
	    _valveName = matcher.group(1);
	    _flowRate = Integer.parseInt(matcher.group(2));
	    _tunnels = matcher.group(3).split(", ");
	    
	    _travelCost = new HashMap<>();
	    _travelCost.put(_valveName, 0);
	}
	
	public String getName()
	{
		return _valveName;
	}
	
	public String[] getReachableValves()
	{
		return _tunnels;
	}
	
	public void registerTunnel(String source, String target)
	{
		//System.out.println(String.format("Call %s with info %s => %s", getName(), source, target));
		
		if (source.equals(getName()))
		{
			_travelCost.put(target, 1);
			return;
		}
		
		// Do we know the way to the source valve?
		var knownCostToSource = _travelCost.get(source);
		
		// The cost we're getting reported is the cost to reach <from> + 1
		if (knownCostToSource != null)
		{
			var reportedCost = knownCostToSource.intValue() + 1;
			
			// Is this a new valve we haven't got in our map yet?
			var knownCostToTarget = _travelCost.get(target);
			
			if (knownCostToTarget != null)
			{
				if (knownCostToTarget.intValue() > reportedCost)
				{
					_travelCost.put(target, reportedCost); // this one is better
				}
			}
			else
			{
				_travelCost.put(target, reportedCost);
			}
		}
		else
		{
			throw new IllegalArgumentException(String.format("No mapping for source valve %s", source));
		}
	}
	
	@Override
	public String toString()
	{
		return _valveName;
	}

	public void printConnectivity()
	{
		for (var key : _travelCost.keySet())
		{
			System.out.println(String.format("Valve %s => %s costs %d steps", getName(), key, _travelCost.get(key)));
		}
	}
}
