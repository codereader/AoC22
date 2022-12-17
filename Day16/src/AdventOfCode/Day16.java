package AdventOfCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import AdventOfCode.Common.FileUtils;

public class Day16
{
	public static void main(String[] args)
	{
		var lines = FileUtils.readFile("./input.txt");
		var valves = lines.stream().map(l -> new Valve(l)).collect(Collectors.toMap(v -> v.getName(), v -> v));
		var startValve = valves.get("AA");
		
		buildConnectivityFW(valves);
		
		var openedValves = new ArrayList<Valve>();
		
		var minutesLeft = 30;
		var releasedPressure = 0;
		var currentFlowRate = 0;
		
		// Only consider valves with a non-zero flow rate
		// Sort the valves by flow rate to pick the heavy ones first
		var valvePool = valves.values().stream()
			.filter(v -> v.getFlowRate() > 0)
			.sorted((v1,v2) -> 
			{
				return Integer.compare(v2.getFlowRate(), v1.getFlowRate()); // descending;	
			})
			.collect(Collectors.toList());
		
		var players = new ArrayList<Player>();
		
		players.add(new Player(startValve));
		
		for (var player : players)
		{
			pickNewValveToOpen(player, valvePool, minutesLeft);
		}
		
		while (minutesLeft > 0)
		{
			System.out.println(String.format("--- Minute %d ----", 30 - minutesLeft + 1));
			
			for (var player : players)
			{
				switch (player.CurrentState)
				{
				case Idle:
					pickNewValveToOpen(player, valvePool, minutesLeft);
					break;
					
				case ProcessingValve:
					if (player.FinishTime == minutesLeft)
					{
						// Player is done moving and opening
						player.CurrentValve = player.TargetValve;
						player.CurrentState = Player.State.Idle;
						System.out.println(String.format("Opening valve %s", player.TargetValve.getName()));
						
						openedValves.add(player.TargetValve);
						currentFlowRate += player.TargetValve.getFlowRate();
						
						pickNewValveToOpen(player, valvePool, minutesLeft);
					}
					else
					{
						if (minutesLeft - 1 == player.FinishTime)
						{
							System.out.println(String.format("Player is opening %s", player.TargetValve.getName()));
						}
						else
						{
							System.out.println(String.format("Player is moving to %s", player.TargetValve.getName()));
						}
					}
					break;
				}
			}
			
			// Accumulate the released pressure so far
			System.out.println(String.format("Current Flow Rate = %d", currentFlowRate));
			releasedPressure += currentFlowRate;
			
			System.out.println(String.format("Released Pressure: %d", releasedPressure));
			
			// Advance the time
			--minutesLeft;
		}
		
		System.out.println(String.format("Final Flow Rate = %d", currentFlowRate));
		
		// Remaining time
		var finalFlowRate = openedValves.stream().map(v -> v.getFlowRate()).reduce(Integer::sum).get();
		releasedPressure += (long)finalFlowRate * minutesLeft;
		
		System.out.println(String.format("Released Pressure: %d", releasedPressure));
	}
	
	private static void pickNewValveToOpen(Player player, List<Valve> valvePool, int minutesLeft)
	{
		// Pick a new valve to open and start moving
		var bestValveToOpen = determineBestValveToOpen(player.CurrentValve, valvePool, minutesLeft);
		
		if (bestValveToOpen != null)
		{
			// Remove this valve from the pool
			valvePool.remove(bestValveToOpen);
			
			player.AssignValve(bestValveToOpen, minutesLeft - player.CurrentValve.getCostToOpen(bestValveToOpen));
			
			System.out.println(String.format("Player will handle %s, cost is %d, will arrive at %d", 
					player.TargetValve.getName(),
					player.CurrentValve.getCostToOpen(bestValveToOpen),
					player.FinishTime));
		}
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
		
		return bestCombos.get(0).FirstValve;
	}
	
	// Floyd-Warshall algorithm to compute the shortest distances of every node to another node
	private static void buildConnectivityFW(Map<String, Valve> valves)
	{
		System.out.println(String.format("-- Calculating Distances --"));
		
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
		
		System.out.println(String.format("-- Distances calculated --"));
	}
}
