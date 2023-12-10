package AdventOfCode;

import java.util.Objects;

public class NumberTriple
{
	public int X;
	public int Y;
	public int Z;
	
	public NumberTriple(int x, int y, int z)
	{
		X = x;
		Y = y;
		Z = z;
	}
	
	@Override
    public boolean equals(Object o)
	{
        if (this == o) return true;
        
        if (o == null || getClass() != o.getClass())
        {
        	return false;
        }
        
        var other = (NumberTriple)o;
        return X == other.X && Y == other.Y && Z == other.Z;
    }

    @Override
    public int hashCode()
    {
    	return Objects.hash(X, Y, Z);
    }
}
