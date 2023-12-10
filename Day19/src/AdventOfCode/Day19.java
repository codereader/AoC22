package AdventOfCode;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import AdventOfCode.Common.FileUtils;

public class Day19
{
	public static void main(String[] args)
	{
		var lines = FileUtils.readFile("./input.txt");
		
		var bluePrints = lines.stream().map(line -> new BluePrint(line)).collect(Collectors.toList());
		
		var totalQualityLevel = 0;
		
		/*for (var bluePrint : bluePrints)
		{
			var maxGeodes = runSimulation(bluePrint.Robots, 24);
			
			System.out.println(String.format("BluePrint %d can produce %d Geodes max, has Quality Level = %d", bluePrint.Index, maxGeodes, bluePrint.Index * maxGeodes));
			
			totalQualityLevel += bluePrint.Index * maxGeodes;
		}*/
		
		System.out.println(String.format("[Part1]: Total Quality Level = %d", totalQualityLevel));
		
		var firstThreeBluePrints = bluePrints.stream().limit(3).collect(Collectors.toList());

		var productOfTopThree = 1;
		
		for (var bluePrint : firstThreeBluePrints)
		{
			var maxGeodes = runSimulation(bluePrint.Robots, 32);
			
			System.out.println(String.format("BluePrint %d can produce %d Geodes max", bluePrint.Index, maxGeodes));
			
			productOfTopThree *= maxGeodes;
		}
		
		System.out.println(String.format("[Part2]: Product of max geodes of first three blueprints = %d", productOfTopThree));
	}
	
	public static int runSimulation(List<Robot> robots, int minutes)
	{
		var startTime = Instant.now();
		
		// Starting situation
		var startingInventory = new Inventory(1);
		var oreRobot = robots.stream().filter(r -> r.getMaterial() == Material.Ore).findFirst().get();
		startingInventory.Robots.get(Material.Ore).add(oreRobot);

		// The maximum clay and ore costs of all robots
		var maxCost = new HashMap<Material, Integer>();
		
		maxCost.put(Material.Ore, robots.stream().mapToInt(r -> r.getOreCost()).max().getAsInt());
		maxCost.put(Material.Clay, robots.stream().mapToInt(r -> r.getClayCost()).max().getAsInt());
		maxCost.put(Material.Obsidian, robots.stream().mapToInt(r -> r.getObsidianCost()).max().getAsInt());
		
		// Stack of inventories to calculcate
		var inventoriesToInvestigate = new Stack<Inventory>();
		inventoriesToInvestigate.push(startingInventory);
		
		var finishedInventories = new ArrayList<Inventory>();
		var bestGeodeProduction = 0;
		
		while (!inventoriesToInvestigate.isEmpty())
		{
			var inventory = inventoriesToInvestigate.pop();
			
			inventory.runRobotProduction();
			inventory.completeRobotProduction();
			
			if (inventory.Time == minutes)
			{
				// End of the time line, add to finished inventories if this is better
				if (inventory.Materials.get(Material.Geode) > bestGeodeProduction)
				{
					finishedInventories.clear();
					finishedInventories.add(inventory);
					bestGeodeProduction = inventory.Materials.get(Material.Geode);
				}
				continue;
			}
			
			var timeLeft = minutes - inventory.Time;
			var geodeProduction = inventory.Robots.get(Material.Geode).size();
			var theoreticalGeodeOutput = timeLeft * geodeProduction + // remaining production of the robots we have
					(timeLeft * (timeLeft - 1) / 2) + 		 // what is produced if we added one geode robot per minute
					inventory.Materials.get(Material.Geode); // what we have already
			
			if (theoreticalGeodeOutput <= bestGeodeProduction)
			{
				// It's not possible for this path to top the best production we know so far
				continue;
			}
			
			// Check possibility of doing nothing
			inventoriesToInvestigate.push(new Inventory(inventory.Time + 1, inventory));
			
			// Check each possible production scenario
			for (var robot : robots)
			{
				var robotOutput = robot.getMaterial();
				
				if (robotOutput != Material.Geode && inventory.Robots.get(robotOutput).size() >= maxCost.get(robotOutput))
				{
					continue; // don't build any more robots than we can make use of
				}
				
				if (inventory.hasMaterialsForRobot(robot))
				{
					var newState = new Inventory(inventory.Time + 1, inventory);
					newState.startProducing(robot);
					
					inventoriesToInvestigate.push(newState);
				}
			}
		}

		//System.out.println(String.format("Calculated %d possibilities", finishedInventories.size()));
		System.out.println(String.format("Best Geode Production: %d", bestGeodeProduction));
		
		if (!finishedInventories.isEmpty())
		{
			System.out.println(String.format("Best Inventory: %s", finishedInventories.get(0)));
		}
		
		
		var endTime = Instant.now();
		System.out.println(Duration.between(startTime, endTime));
		
		return bestGeodeProduction;
	}
}
