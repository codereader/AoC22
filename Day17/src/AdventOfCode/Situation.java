package AdventOfCode;

import java.util.ArrayList;
import java.util.Objects;

public class Situation
{
	public long StackHeight; 
	public String HeadOfGrid;
	public long FirstNonSolidRow;
	public long StoppedRocks;
	public int Checksum;
	public int NextRock;
	public int NextJetDirection;
	public int HitCount = 0;
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
        
        if (o == null || getClass() != o.getClass())
        {
        	return false;
        }
        
        var other = (Situation)o;
        return HeadOfGrid.equals(other.HeadOfGrid) && 
    		NextRock == other.NextRock && 
    		NextJetDirection == other.NextJetDirection;
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(HeadOfGrid, NextRock, NextJetDirection);
	}
}
