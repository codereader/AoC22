package AdventOfCode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors;

public class Elements extends ArrayList<Object> implements Comparable<Elements>
{
	private static final long serialVersionUID = 2057457246295700888L;

	public Elements()
	{}
	
	public static Elements parse(String line)
	{
		var iterator = line.chars().iterator();
		
		// Consume the first opening bracket
		if (iterator.next() != '[') throw new IllegalArgumentException("Opening bracket expected");
		
		return parseList(iterator); 
	}
	
	@Override
	public String toString()
	{
		var builder = new StringBuilder();
		
		builder.append('[');
		builder.append(this.stream().map(element -> element.toString()).collect(Collectors.joining(",")));
		builder.append(']');
		
		return builder.toString();
	}
	
	public static Elements parseList(Iterator<Integer> iterator)
	{
		var elements = new Elements();
		
		var tokens = "";
		
		while (iterator.hasNext())
		{
			var ch = (char)iterator.next().intValue();
			
			switch (ch)
			{
			case '[':
				// Enter recursion
				elements.add(parseList(iterator));
			case ',':
				// Next list element, complete the previous one
				if (!tokens.isEmpty())
				{
					elements.add(Integer.parseInt(tokens));
					tokens = "";
				}
				continue;
			case ']':
				// List is done, complete token and return
				if (!tokens.isEmpty())
				{
					elements.add(Integer.parseInt(tokens));
					tokens = "";
				}
				break;
			default:
				// Must be a numeric type, accumulate that
				if (!Character.isDigit(ch)) throw new IllegalArgumentException("Unknown character " + ch);
				tokens += ch;
				continue;
			}
		}
		
		return elements;
	}

	@Override
	public int compareTo(Elements other)
	{
		// Both are of type Element, so we are comparing lists
		return compare(this, other);
	}
	
	private static int compare(Elements left, Elements right)
	{
		for (int i = 0; i <= left.size(); i++)
		{
			if (i >= right.size())
			{
				return +1; // right ran out of elements
			}
			else if (i >= left.size())
			{
				return i < right.size() ? -1 : 0; // left ran out of elements
			}
			
			// Compare elements
			var elementComparison = compare(left.get(i), right.get(i));
			
			if (elementComparison == 0)
			{
				continue; // go on
			}
			
			// Elements are not equal, propagate the result
			return elementComparison;
		}
		
		return 0;
	}
	
	private static int compare(Object left, Object right)
	{
		if (left.getClass() == Integer.class && right.getClass() == Integer.class)
		{
			return ((Integer)left).compareTo((Integer)right);
		}
		
		// Both of them must be a list
		if (left.getClass() == Elements.class && right.getClass() == Elements.class)
		{
			return compare((Elements)left, (Elements)right);
		}
		
		// One of them is not a list
		if (left.getClass() == Elements.class)
		{
			// Convert right to a temporary list
			var tempList = new Elements();
			tempList.add(right);
			return compare((Elements)left, tempList);
		}
		else // Convert left to a temporary list
		{
			var tempList = new Elements();
			tempList.add(left);
			return compare(tempList, (Elements)right);
		}
	}
}
