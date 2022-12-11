package AdventOfCode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Monkey {
	
	private final int _index;
	private final Function<Integer, Integer> _worryFunction;
	private final ArrayList<Item> _items;
	private final int _divisor;
	private Monkey _targetMonkeyIfTrue;
	private Monkey _targetMonkeyIfFalse;
	private int _numInspections;
	private int _worryDivisor;
	
	public Monkey(int index, String items, Function<Integer, Integer> worryFunction, int divisor)
	{
		_index = index;
		_worryFunction = worryFunction;
		_divisor = divisor;
		_numInspections = 0;
		_worryDivisor = 3; // by default
		
		_items = new ArrayList<Item>();
		
		for (var worry : items.split(", "))
		{
			_items.add(new Item(Integer.parseInt(worry)));
		}
	}
	
	public int getIndex()
	{
		return _index;
	}
	
	public long getActivityIndex()
	{
		return _numInspections;
	}
	
	public int getDivisor()
	{
		return _divisor;
	}
	
	public void setDivisors(List<Integer> divisors)
	{
		for (var item : _items)
		{
			item.setDivisors(divisors);
		}
	}
	
	public void setTargetMonkeys(Monkey targetMonkeyIfTrue, Monkey targetMonkeyIfFalse)
	{
		_targetMonkeyIfTrue = targetMonkeyIfTrue;
		_targetMonkeyIfFalse = targetMonkeyIfFalse;
	}
	
	private void acceptItem(Item item)
	{
		_items.add(item); // add to end
	}
	
	public void disableWorryDivision()
	{
		_worryDivisor = 1;
	}

	public void runRound()
	{
		for (var item : _items)
		{
			++_numInspections;
			
			if (_worryDivisor != 1)
			{
				item.apply(i -> 
				{
					return Math.floorDiv(_worryFunction.apply(i), _worryDivisor);
				});
			}
			else
			{
				item.apply(_worryFunction);
			}
			
			// The item's new level is thrown to the other monkey
			if (item.isDivisableForMonkey(_index))
			{
				_targetMonkeyIfTrue.acceptItem(item);
			}
			else
			{
				_targetMonkeyIfFalse.acceptItem(item);
			}
		}
		
		_items.clear();
	}
	
	
}
