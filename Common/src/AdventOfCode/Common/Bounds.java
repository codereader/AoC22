package AdventOfCode.Common;

public class Bounds
{
	public final Vector2 Min;
	public final Vector2 Max;
	
	public Bounds(Vector2 min, Vector2 max)
	{
		Min = min;
		Max = max;
	}
	
	public Bounds expandBy(int amount)
	{
		return new Bounds(
			Min.plus(new Vector2(-amount, -amount)),
			Max.plus(new Vector2(+amount, +amount))
		);
	}
}
