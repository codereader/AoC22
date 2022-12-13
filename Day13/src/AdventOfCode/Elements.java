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
				return elements;
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
		System.out.println(String.format("Compare %s vs %s", this.toString(), other.toString()));
		
		// Both are of type Element, so we are comparing lists
		return compare(this, other, 0);
	}
	
	private static int compare(Elements left, Elements right, int level)
	{
		var indentation = "";
		for (int i = 0; i < level; i++) {
			indentation += "  ";
		}
		
		for (int i = 0; i <= left.size(); i++)
		{
			if (i >= left.size() && i >= right.size())
			{
				return 0; // lists are equal
			}
			
			if (i >= left.size())
			{
				System.out.println(String.format("%sLeft side ran out of items, so inputs are in the right order", indentation));
				return -1; // left ran out of elements
			}
			
			if (i >= right.size())
			{
				System.out.println(String.format("%sRight side ran out of items, so inputs are not in the right order", indentation));
				return +1; // right ran out of elements
			}
			
			// Compare elements
			var elementComparison = compare(left.get(i), right.get(i), level + 1);
			
			if (elementComparison == 0)
			{
				continue; // go on
			}
			
			if (elementComparison < 0)
			{
				System.out.println(String.format("%sLeft side is smaller => right order", indentation));
			}
			else
			{
				System.out.println(String.format("%sRight side is smaller => incorrect order", indentation));
			}
			
			// Elements are not equal, propagate the result
			return elementComparison;
		}
		
		return 0;
	}
	
	private static int compare(Object left, Object right, int level)
	{
		var indentation = "";
		for (int i = 0; i < level; i++) {
			indentation += "  ";
		}
		
		if (left.getClass() == Integer.class && right.getClass() == Integer.class)
		{
			System.out.println(String.format("%s- Compare %s vs %s", indentation, left.toString(), right.toString()));
			return ((Integer)left).compareTo((Integer)right);
		}
		
		// Both of them must be a list
		if (left.getClass() == Elements.class && right.getClass() == Elements.class)
		{
			System.out.println(String.format("%s- Compare %s vs %s", indentation, left.toString(), right.toString()));
			return compare((Elements)left, (Elements)right, level + 1);
		}
		
		System.out.println(String.format("%s- Compare %s vs %s", indentation, left.toString(), right.toString()));
		
		// One of them is not a list
		if (left.getClass() == Elements.class)
		{
			// Convert right to a temporary list
			var tempList = new Elements();
			tempList.add(right);
			System.out.println(String.format("%s- Mixed types; convert right to %s and retry comparison", indentation, tempList.toString()));
			return compare((Elements)left, tempList, level + 1);
		}
		else // Convert left to a temporary list
		{
			var tempList = new Elements();
			tempList.add(left);
			System.out.println(String.format("%s- Mixed types; convert left to %s and retry comparison", indentation, tempList.toString()));
			return compare(tempList, (Elements)right, level +1);
		}
	}
}
