package AdventOfCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import AdventOfCode.Common.FileUtils;

public class Day19
{
	public static void main(String[] args)
	{
		var lines = FileUtils.readFile("./test.txt");
		
		var bluePrints = lines.stream().map(line -> new BluePrint(line)).collect(Collectors.toList());
		
		var totalQualityLevel = 0;
		
		for (var bluePrint : bluePrints)
		{
			var robots = new ArrayList<Robot>();
			
			robots.add(new Robot(Material.Geode, bluePrint.GeodeRobotOreCost, 0, bluePrint.GeodeRobotObsidianCost));
			robots.add(new Robot(Material.Obsidian, bluePrint.ObsidianRobotOreCost, bluePrint.ObsidianRobotClayCost, 0));
			robots.add(new Robot(Material.Clay, bluePrint.ClayRobotOreCost, 0, 0));
			robots.add(new Robot(Material.Ore, bluePrint.OreRobotOreCost, 0, 0));
			
			var maxGeodes = runSimulation(robots, 24);
			
			System.out.println(String.format("BluePrint %d Quality Level = %d", bluePrint.Index, bluePrint.Index * maxGeodes));
			
			totalQualityLevel += bluePrint.Index * maxGeodes;
		}
		
		System.out.println(String.format("[Part1]: Total Quality Level = %d", totalQualityLevel));
	}
	
	public static int runSimulation(List<Robot> robots, int minutes)
	{
		// Starting situation
		var startingInventory = new Inventory(1);
		var oreRobot = robots.stream().filter(r -> r.getMaterial() == Material.Ore).findFirst().get();
		startingInventory.Robots.get(Material.Ore).add(oreRobot);

		// Stack of inventories to calculcate
		var inventoriesToInvestigate = new Stack<Inventory>();
		inventoriesToInvestigate.push(startingInventory);
		
		var finishedInventories = new ArrayList<Inventory>();
		
		while (!inventoriesToInvestigate.isEmpty())
		{
			var inventory = inventoriesToInvestigate.pop();
			
			inventory.runRobotProduction();
			inventory.completeRobotProduction();
			
			if (inventory.Time == minutes)
			{
				// End of the time line, add to finished inventories
				finishedInventories.add(new Inventory(inventory.Time, inventory.Previous));
				continue;
			}
			
			// Check possibility of doing nothing
			inventoriesToInvestigate.push(new Inventory(inventory.Time + 1, inventory));
			
			// Check each possible production scenario
			for (var robot : robots)
			{
				if (inventory.hasMaterialsForRobot(robot))
				{
					var newState = new Inventory(inventory.Time + 1, inventory);
					newState.startProducing(robot);
					
					inventoriesToInvestigate.push(newState);
				}
			}
		}

		System.out.println(String.format("Calculated %d possibilities", finishedInventories.size()));
		
		return finishedInventories.stream().mapToInt(i -> i.Materials.get(Material.Geode).intValue()).max().getAsInt();
	}
}
