package AdventOfCode;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import AdventOfCode.Common.FileUtils;

public class Day16 {

	public static void main(String[] args)
	{
		var lines = FileUtils.readFile("./test.txt");
		var valves = lines.stream().map(l -> new Valve(l)).collect(Collectors.toMap(v -> v.getName(), v -> v));
		var firstValve = new Valve(lines.get(0)).getName();
		
		var visitedValves = new HashSet<Valve>();
		var startValve = valves.get(firstValve);
		buildConnectivity(valves, startValve, null, visitedValves);
	}

	private static void buildConnectivity(Map<String, Valve> valves, Valve toProcess, Valve previousValve, Set<Valve> visitedValves)
	{
		if (visitedValves.contains(toProcess))
		{
			return;
		}
		
		System.out.println(String.format("Processing valve %s", toProcess.getName()));
		
		visitedValves.add(toProcess);
		
		if (previousValve != null)
		{
			System.out.println(String.format("Broadcasting %s => %s", previousValve.getName(), toProcess.getName()));
			
			// Broadcast the way we got here to this node
			for (var valve : visitedValves)
			{
				valve.registerTunnel(previousValve.getName(), toProcess.getName());
			}
		}
		
		for (var target : toProcess.getReachableValves())
		{
			var targetValve = valves.get(target);
			
			for (var valve : visitedValves)
			{
				valve.registerTunnel(toProcess.getName(), target);

				// Register the path from target back to the source too
				targetValve.registerTunnel(target, toProcess.getName());
			}
		}
		
		for (var target : toProcess.getReachableValves())
		{
			// Acquaint the targets to each other (such that neighbour A knows about reaching neighbour B)
			for (var neighbour : toProcess.getReachableValves())
			{
				if (neighbour.equals(target)) continue;
				
				valves.get(neighbour).registerTunnel(toProcess.getName(), target);
			}
		}
		
		// Enter recursion
		for (var target : toProcess.getReachableValves())
		{
			buildConnectivity(valves, valves.get(target), toProcess, visitedValves);
		}
	}
}
