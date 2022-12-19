package AdventOfCode;

import java.util.HashMap;

public class Volume
{
	public NumberTriple Max;
	
	private HashMap<NumberTriple, BlockType> _field;
	
	public Volume(NumberTriple max)
	{
		Max = max;
		_field = new HashMap<>();
	}
	
	public BlockType get(NumberTriple coords)
	{
		return _field.getOrDefault(coords, BlockType.Air);
	}
	
	public void set(NumberTriple coords, BlockType type)
	{
		_field.put(coords, type);
	}
}
