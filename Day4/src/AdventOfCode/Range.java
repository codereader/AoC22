package AdventOfCode;

public class Range {
	
	private final int _min;
	private final int _max;
	
	public Range(String input)
	{
		var minMax = input.split("-");
		_min = Integer.parseInt(minMax[0]); 		
		_max = Integer.parseInt(minMax[1]); 		
	}
	
	public boolean contains(Range other)
	{
		return other._min >= _min && other._max <= _max;
	}
	
	public boolean overlaps(Range other)
	{
		return other._min >= _min && other._min <= _max || 
			other._max >= _min && other._max <= _max;
	}
}
