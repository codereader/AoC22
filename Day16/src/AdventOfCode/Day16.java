package AdventOfCode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import AdventOfCode.Common.FileUtils;

public class Day16 {

	public static void main(String[] args)
	{
		var lines = FileUtils.readFile("./input.txt");
		var valves = lines.stream().map(l -> new Valve(l)).collect(Collectors.toMap(v -> v.getName(), v -> v));
		
		/*var otherDay = new ExampleDay();
		try
		{
			otherDay.dayN(lines);
		}
		catch (IOException e)
		{}*/
		
		var firstValve = "AA";
		
		var startValve = valves.get(firstValve);
		
		System.out.println(String.format("-- Distances --"));
		buildConnectivityFW(valves);
		
		var openedValves = new ArrayList<Valve>();
		
		var currentValve = startValve;
		var minutesLeft = 30;
		var releasedPressure = 0L;
		
		// Get all valves with a non-zero flow rate
		var valvesByFlowRate = valves.values().stream()
			.filter(v -> v.getFlowRate() > 0)
			.collect(Collectors.toList());
		
		var result = calculatePaths(valvesByFlowRate, Collections.emptySet(), startValve, 30, 0, new HashMap<>());
		
		var maxFlow = result.values().stream().mapToInt(x -> x).max().orElse(-1);
		System.out.println(String.format("[Part1]: Max Flow achieved: %d", maxFlow));
		
		while (!valvesByFlowRate.isEmpty() && minutesLeft > 0)
		{
			System.out.println("--- Step ----");
			
			var finalCurrentValve = currentValve;
			var finalMinutesLeft = minutesLeft;
			valvesByFlowRate = valvesByFlowRate.stream()
					.filter(v -> finalCurrentValve.getCostToOpen(v) <= finalMinutesLeft)
					.filter(v -> v.getFlowRate() > 0)
					.sorted((v1,v2) -> 
					{
						var cost1 = finalCurrentValve.getCostToOpen(v1);
						var cost2 = finalCurrentValve.getCostToOpen(v2);
						return Double.compare(v2.getFlowRate() / (double)cost2, v1.getFlowRate() / (double)cost1); // descending;	
					})
					.collect(Collectors.toList());
			
			var bestValveToOpen = determineBestValveToOpen(currentValve, valvesByFlowRate, minutesLeft);
			
			if (bestValveToOpen == null)
			{
				break;
			}
				
			final var costToOpen = currentValve.getTravelCost(bestValveToOpen) + 1;
			
			System.out.println(String.format("Opening valve %s, this costs %d", bestValveToOpen.getName(), costToOpen));
			
			currentValve = bestValveToOpen;
			minutesLeft -= costToOpen;
			
			// Accumulate released pressure
			if (!openedValves.isEmpty())
			{
				var currentFlowRate = openedValves.stream().map(v -> v.getFlowRate()).reduce(Integer::sum).get();
				System.out.println(String.format("Flow Rate = %d", currentFlowRate));
				releasedPressure += (long)currentFlowRate * costToOpen;
			}
			
			System.out.println(String.format("Released pressure: %d", releasedPressure));
			
			openedValves.add(bestValveToOpen);
			valvesByFlowRate.remove(currentValve);
		}
		
		// Remaining time
		var finalFlowRate = openedValves.stream().map(v -> v.getFlowRate()).reduce(Integer::sum).get();
		releasedPressure += (long)finalFlowRate * minutesLeft;
		
		System.out.println(String.format("Released pressure: %d", releasedPressure));
	}
	
	private static Map<Set<Valve>, Integer> calculatePaths(List<Valve> allValves, Set<Valve> openValves, Valve currentValve, 
			int timeLeft, int currentFlowRate, Map<Set<Valve>, Integer> flowByPath)
	{
		// Store the path in the map, overwriting any existing flow if the current one is greater
		flowByPath.merge(openValves, currentFlowRate, Math::max);

		for (var valve : allValves)
		{
			var timeAfter = timeLeft - currentValve.getCostToOpen(valve);
			if (openValves.contains(valve) || timeAfter <= 0) 
			{
				continue;
			}
			
			var newOpenValves = new HashSet<>(openValves);
			newOpenValves.add(valve);
			
			calculatePaths(allValves, newOpenValves, valve, timeAfter, 
				timeAfter * valve.getFlowRate() + currentFlowRate, flowByPath);
		}

		return flowByPath;
	}
	
	private static Valve determineBestValveToOpen(Valve currentValve, List<Valve> valvesByFlowRate, int minutesLeft)
	{
		if (valvesByFlowRate.size() == 1)
		{
			return valvesByFlowRate.get(0);
		}
		
		var possibleValves = valvesByFlowRate.stream()
				.filter(v -> currentValve.getCostToOpen(v) <= minutesLeft)
				.collect(Collectors.toList());
		
		if (possibleValves.isEmpty()) return null;
		if (possibleValves.size() == 1) return possibleValves.get(0);
		
		var combinations = new ArrayList<ValveCombination>();
		
		var valvesToPick = Math.min(possibleValves.size(), 6);
			
		// Check all possible combinations
		for (var n = 0; n < possibleValves.size(); ++n)
		{			
			for (var m = 0; m < possibleValves.size(); ++m)
			{
				if (n == m) continue;
				
				if (valvesToPick > 2)
				{
					for (var p = 0; p < possibleValves.size(); ++p)
					{
						if (p == n || p == m) continue;
						
						if (valvesToPick > 3)
						{
							for (var q = 0; q < possibleValves.size(); ++q)
							{
								if (q == n || q == m || q == p) continue;
								
								if (valvesToPick > 4)
								{
									for (var r = 0; r < possibleValves.size(); ++r)
									{
										if (r == n || r == m || r == p || r == q) continue;
										
										if (valvesToPick > 5)
										{
											for (var s = 0; s < possibleValves.size(); ++s)
											{
												if (s == n || s == m || s == p || s == q || s == r) continue;
												
											combinations.add(new ValveCombination(currentValve, minutesLeft,
												possibleValves.get(n), possibleValves.get(m), 
												possibleValves.get(p), possibleValves.get(q),
												possibleValves.get(r), possibleValves.get(s)));
											}
										}
										else
										{
											combinations.add(new ValveCombination(currentValve, minutesLeft,
												possibleValves.get(n), possibleValves.get(m), 
												possibleValves.get(p), possibleValves.get(q),
												possibleValves.get(r)));
										}
									}
								}
								else
								{
									combinations.add(new ValveCombination(currentValve, minutesLeft,
										possibleValves.get(n), possibleValves.get(m), possibleValves.get(p), possibleValves.get(q)));
								}
							}
						}
						else
						{
							combinations.add(new ValveCombination(currentValve, minutesLeft,
								possibleValves.get(n), possibleValves.get(m), possibleValves.get(p)));
						}
					}
				}
				else
				{
					combinations.add(new ValveCombination(currentValve, minutesLeft,
						possibleValves.get(n), possibleValves.get(m)));
				}
			}
		}
		
		var bestCombos = combinations.stream().sorted((pair1, pair2) ->
		{
			return Double.compare(pair2.GainedPressureVolume, pair1.GainedPressureVolume);
		}).collect(Collectors.toList());
		
		for (var combo : bestCombos)
		{
			//System.out.println(combo.toString());
		}
		
		if (bestCombos.isEmpty())
		{
			int i = 6;
		}
		
		return bestCombos.get(0).FirstValve;
	}
	
	// Floyd-Warshall algorithm to compute the shortest distances of every node to another node
	private static void buildConnectivityFW(Map<String, Valve> valves)
	{
		/*
 let dist be a |V| × |V| array of minimum distances initialized to ∞ (infinity)
for each edge (u, v) do
    dist[u][v] ← w(u, v)  // The weight of the edge (u, v)
for each vertex v do
    dist[v][v] ← 0
for k from 1 to |V|
    for i from 1 to |V|
        for j from 1 to |V|
            if dist[i][j] > dist[i][k] + dist[k][j] 
                dist[i][j] ← dist[i][k] + dist[k][j]
            end if
		 */
		
		// Initialise direct edges
		for (var valve : valves.values())
		{
			for (var neighbour : valve.getReachableValves())
			{
				valve.setTravelCost(valves.get(neighbour), 1);
			}
		}
		
		for (var k : valves.values())
		{
			for (var i : valves.values())
			{
				for (var j : valves.values())
				{ 
					var knownCost = i.getTravelCost(j);
					
					var iToK = i.getTravelCost(k);
					var kToj = k.getTravelCost(j);
					
					if (iToK == null) iToK = 9999999;
					if (kToj == null) kToj = 9999999;
					
					var newCost = iToK + kToj;
					
					if (knownCost == null || knownCost > newCost)
					{
						i.setTravelCost(j, newCost);
					}
				}
			}
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
