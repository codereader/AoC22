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
			
			/*for (var infoValve : valvesByFlowRate)
			{
				System.out.println(String.format("Opening valve %s would produce a slope of %f", 
					infoValve.getName(), infoValve.getFlowRate() / (double)currentValve.getCostToOpen(infoValve)));
			}*/
			
			var bestValveToOpen = determineBestValveToOpen(currentValve, valvesByFlowRate, minutesLeft);
			
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
	
	private static Valve determineBestValveToOpen(Valve currentValve, List<Valve> valvesByFlowRate, int minutesLeft)
	{
		if (valvesByFlowRate.size() == 1)
		{
			return valvesByFlowRate.get(0);
		}
		
		var possibleValves = valvesByFlowRate.stream()
				.filter(v -> currentValve.getCostToOpen(v) <= minutesLeft)
				.collect(Collectors.toList());
		
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
			System.out.println(combo.toString());
		}
		
		return bestCombos.get(0).FirstValve;
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
