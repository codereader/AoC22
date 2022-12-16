package AdventOfCode;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import AdventOfCode.Common.FileUtils;

public class Day16 {

	public static void main(String[] args)
	{
		var lines = FileUtils.readFile("./input.txt");
		var valves = lines.stream().map(l -> new Valve(l)).collect(Collectors.toMap(v -> v.getName(), v -> v));
		var firstValve = new Valve(lines.get(0)).getName();
		
		var startValve = valves.get(firstValve);
		
		for (var valveName : valves.keySet())
		{
			var visitedValves = new HashSet<Valve>();
			var valve = valves.get(valveName);
			buildConnectivity(valve, valves, valve, visitedValves);
			//valve.printConnectivity();
		}
	}

	private static void buildConnectivity(Valve startValve, Map<String, Valve> valves, Valve toProcess, Set<Valve> visitedValves)
	{
		if (visitedValves.contains(toProcess))
		{
			return;
		}
		
		//System.out.println(String.format("Processing valve %s", toProcess.getName()));
		
		visitedValves.add(toProcess);
		
		for (var target : toProcess.getReachableValves())
		{
			// Source => Target
			toProcess.registerTunnel(toProcess.getName(), target);
		}
		
		// Let the start node know about the new connectivities
		for (var target : toProcess.getReachableValves())
		{
			// Start Valve => Target
			startValve.registerTunnel(toProcess.getName(), target);
		}
		
		// Enter recursion
		for (var target : toProcess.getReachableValves())
		{
			buildConnectivity(startValve, valves, valves.get(target), visitedValves);
		}
	}
}
