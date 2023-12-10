package AdventOfCode;

import java.util.ArrayList;
import java.util.HashMap;

public class Inventory implements Cloneable
{
	public HashMap<Material, Integer> Materials = new HashMap<>();
	
	Robot RobotInProduction = null;
	HashMap<Material, ArrayList<Robot>> Robots = new HashMap<>();
	
	public Inventory Previous;
	public int Time;
	
	public Inventory(int time)
	{
		Time = time;
		
		Materials.put(Material.Ore, 0);
		Materials.put(Material.Clay, 0);
		Materials.put(Material.Obsidian, 0);
		Materials.put(Material.Geode, 0);
		
		Robots.put(Material.Ore, new ArrayList<Robot>());
		Robots.put(Material.Clay, new ArrayList<Robot>());
		Robots.put(Material.Obsidian, new ArrayList<Robot>());
		Robots.put(Material.Geode, new ArrayList<Robot>());
	}
	
	public Inventory(int time, Inventory previous)
	{
		this(time);
		
		Previous = previous;

		for (var entry : Previous.Materials.entrySet())
		{
			Materials.put(entry.getKey(), entry.getValue().intValue());
		}
		
		RobotInProduction = Previous.RobotInProduction;
		
		for (var entry : Previous.Robots.entrySet())
		{
			Robots.put(entry.getKey(), (ArrayList<Robot>)entry.getValue().clone());
		}
	}
	
	public boolean hasMaterialsForRobot(Robot robot)
	{
		return robot.getObsidianCost() <= Materials.get(Material.Obsidian) &&
			robot.getClayCost() <= Materials.get(Material.Clay) &&
			robot.getOreCost() <= Materials.get(Material.Ore);
	}
	
	private void take(Material material, int count)
	{
		Materials.put(material, Materials.get(material) - count);
	}
	
	public void startProducing(Robot robot)
	{
		//System.out.println(String.format("Spend %d ore, %d clay and %d obsidian to start building a %s-cracking robot.", 
		//	robot.getOreCost(), robot.getClayCost(), robot.getObsidianCost(), robot.getName()));
		
		RobotInProduction = robot;
		
		take(Material.Ore, robot.getOreCost());
		take(Material.Clay, robot.getClayCost());
		take(Material.Obsidian, robot.getObsidianCost());
	}
	
	public void runRobotProduction()
	{
		for (var entry : Robots.entrySet())
		{
			if (entry.getValue().isEmpty()) continue;
			
			//var materialName = entry.getKey().toString();
			//var stock = Materials.get(entry.getKey()).intValue();
			
			entry.getValue().forEach(r -> r.produce(this));
			
			//var newStock = Materials.get(entry.getKey()).intValue();
		}
	}

	public void completeRobotProduction()
	{
		if (RobotInProduction == null) return;
		
		Robots.get(RobotInProduction.getMaterial()).add(RobotInProduction);
		RobotInProduction = null;
	}
	
	@Override
	public String toString()
	{
		var text = new StringBuilder();
		
		text.append(String.format("== Minute %d ==\n", Time));
		
		if (RobotInProduction != null)
		{
			text.append(String.format("Building a %s-cracking robot.", RobotInProduction.getName()));
			text.append("\n");
		}
		
		for (var entry : Robots.entrySet())
		{
			if (entry.getValue().isEmpty()) continue;
			
			var materialName = entry.getKey().toString();
			
			text.append(String.format("%d %s-collecting robot(s).", 
				entry.getValue().size(), materialName));
			text.append("\n");
		}
		
		for (var entry : Materials.entrySet())
		{
			text.append(String.format("%s: %d | ", entry.getKey().toString(), entry.getValue().intValue()));
		}
		
		return text.toString();
	}
}
