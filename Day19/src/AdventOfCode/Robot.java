package AdventOfCode;

public class Robot
{
	private final int _oreCost;
	private final int _clayCost;
	private final int _obsidianCost;
	private final Material _material;
	
	public Robot(Material material, int oreCost, int clayCost, int obsidianCost)
	{
		_material = material;
		_oreCost = oreCost;
		_clayCost = clayCost;
		_obsidianCost = obsidianCost;
	}
	
	public String getName()
	{
		return _material.toString();
	}
	
	public Material getMaterial()
	{
		return _material;
	}
	
	public int getOreCost()
	{
		return _oreCost;
	}
	
	public int getClayCost()
	{
		return _clayCost;
	}
	
	public int getObsidianCost()
	{
		return _obsidianCost;
	}
	
	public void produce(Inventory inventory)
	{
		inventory.Materials.put(_material, inventory.Materials.get(_material) + 1);
	}
}
