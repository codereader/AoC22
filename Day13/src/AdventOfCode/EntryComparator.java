package AdventOfCode;

import java.util.Comparator;

public class EntryComparator implements Comparator<Elements>
{
	@Override
	public int compare(Elements o1, Elements o2)
	{
		return o1.compareTo(o2);
	}
}
