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
		if (coords.X < 0 || coords.Y < 0 || coords.Z < 0) return BlockType.Void;
		if (coords.X > Max.X || coords.Y > Max.Y || coords.Z > Max.Z) return BlockType.Void;
		
		return _field.getOrDefault(coords, BlockType.Air);
	}
	
	public void set(NumberTriple coords, BlockType type)
	{
		_field.put(coords, type);
	}
	
	public String getLayer(int z)
	{
		var output = new StringBuilder();
		
		for (var y = 0; y < Max.Y; ++y)
		{
			for (var x = 0; x < Max.X; ++x)
			{
				switch (get(new NumberTriple(x,y,z)))
				{
				case Void: output.append('#'); break;
				case Water: output.append('~'); break;
				case Air: output.append(' '); break;
				case Lava: output.append('X'); break;
				}
			}
			
			output.append("\n");
		}
		
		return output.toString();
	}
}
