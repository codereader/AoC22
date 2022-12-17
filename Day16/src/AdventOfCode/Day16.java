package AdventOfCode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
		
		var startValve = valves.get(firstValve);
		
		for (var valveName : valves.keySet())
		{
			var visitedValves = new HashSet<Valve>();
			var valve = valves.get(valveName);
			buildConnectivity(valve, valves, valve, visitedValves);
			//valve.printConnectivity();
		}
		
		// Get all valves with a non-zero flow rate
		var valvesByFlowRate = valves.values().stream()
			.filter(v -> v.getFlowRate() > 0)
			//.sorted((v1,v2) -> Integer.compare(v2.getFlowRate(), v1.getFlowRate())) // descending
			.collect(Collectors.toList());
		
		var openedValves = new ArrayList<Valve>();
		
		var currentValve = startValve;
		var minutesLeft = 30;
		var releasedPressure = 0L;
		
		while (!valvesByFlowRate.isEmpty() && minutesLeft > 0)
		{
			System.out.println("--- Step ---- ");
			
			for (var infoValve : valvesByFlowRate)
			{
				System.out.println(String.format("Opening valve %s would produce a slope of %f", 
					infoValve.getName(), infoValve.getFlowRate() / (double)currentValve.getCostToOpen(infoValve)));
			}
			
			var bestValveToOpen = determineBestValveToOpen(currentValve, valvesByFlowRate, minutesLeft);
			final var costToOpen = currentValve.getTravelCost(bestValveToOpen) + 1;
			
			System.out.println(String.format("Opening valve %s, this costs %d", bestValveToOpen.getName(), costToOpen));
			
			currentValve = bestValveToOpen;
			minutesLeft -= costToOpen;
			
			// Accumulate released pressure
			if (!openedValves.isEmpty())
			{
				releasedPressure += openedValves.stream().map(v -> v.getFlowRate() * (long)costToOpen).reduce(Long::sum).get();
			}
			
			openedValves.add(bestValveToOpen);
			valvesByFlowRate.remove(currentValve);
		}
		
		System.out.println(String.format("Released pressure: %d", releasedPressure));
	}

	private static Valve determineBestValveToOpen(Valve currentValve, List<Valve> valvesByFlowRate, int minutesLeft)
	{
		// Pick the valve with the best bang for buck
		var bestValvesToOpen = valvesByFlowRate.stream()
			.filter(v -> currentValve.getCostToOpen(v) <= minutesLeft)
			.sorted((v1, v2) -> 
				Double.compare(v2.getFlowRate() / (double)currentValve.getCostToOpen(v2), 
							   v1.getFlowRate() / (double)currentValve.getCostToOpen(v1)))
			.collect(Collectors.toList());
		
		for (var infoValve : bestValvesToOpen)
		{
			System.out.println(String.format("Sorted Valve %s it will release %d pressure (open cost %d)", 
				infoValve.getName(), currentValve.getPressureReduction(minutesLeft, infoValve), currentValve.getCostToOpen(infoValve)));
		}
		
		return bestValvesToOpen.get(0);
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
