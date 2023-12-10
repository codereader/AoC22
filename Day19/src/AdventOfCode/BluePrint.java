package AdventOfCode;

import java.util.ArrayList;
import java.util.List;

public class BluePrint
{
	public BluePrint(String line)
	{
		// Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.
		var numbers = new ArrayList<Integer>();
		
		for (var part : line.split("[ :]"))
		{
			try
			{
				numbers.add(Integer.parseInt(part));
			}
			catch (NumberFormatException ex)
			{
				continue;
			}
		}
		
		Index = numbers.get(0);
		OreRobotOreCost = numbers.get(1);
		ClayRobotOreCost = numbers.get(2);
		ObsidianRobotOreCost = numbers.get(3);
		ObsidianRobotClayCost = numbers.get(4);
		GeodeRobotOreCost = numbers.get(5);
		GeodeRobotObsidianCost = numbers.get(6);
		
		createRobots();
	}
	
	public final int Index;
	
	public final int OreRobotOreCost;
	public final int ClayRobotOreCost;
	public final int ObsidianRobotOreCost;
	public final int ObsidianRobotClayCost;
	public final int GeodeRobotOreCost;
	public final int GeodeRobotObsidianCost;
	
	public List<Robot> Robots;
	
	private void createRobots()
	{
		Robots = new ArrayList<Robot>();
		
		Robots.add(new Robot(Material.Geode, GeodeRobotOreCost, 0, GeodeRobotObsidianCost));
		Robots.add(new Robot(Material.Obsidian, ObsidianRobotOreCost, ObsidianRobotClayCost, 0));
		Robots.add(new Robot(Material.Clay, ClayRobotOreCost, 0, 0));
		Robots.add(new Robot(Material.Ore, OreRobotOreCost, 0, 0));
	}
}
