package AdventOfCode;

import java.util.HashMap;
import java.util.Set;

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
	
	public String getLayer(int z, Set<NumberTriple> reachable)
	{
		var output = new StringBuilder();
		
		for (var y = 0; y < Max.Y; ++y)
		{
			for (var x = 0; x < Max.X; ++x)
			{
				var n = new NumberTriple(x,y,z);
				switch (get(n))
				{
				case Void: output.append('#'); break;
				case Water: output.append('~'); break;
				case Air: output.append(' '); break;
				case Lava:
					if (reachable.contains(n))
					{
						output.append('O');
					}
					else
					{
						output.append('X');
					}
					break;
				}
			}
			
			output.append("\n");
		}
		
		return output.toString();
	}
}
