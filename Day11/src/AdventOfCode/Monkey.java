package AdventOfCode;

import java.util.ArrayList;
import java.util.function.Function;

public class Monkey {
	
	private final int _index;
	private final Function<Integer, Integer> _worryFunction;
	private final ArrayList<Integer> _items;
	private final int _divisor;
	private Monkey _targetMonkeyIfTrue;
	private Monkey _targetMonkeyIfFalse;
	private int _numInspections;
	
	public Monkey(int index, String items, Function<Integer, Integer> worryFunction, int divisor)
	{
		_index = index;
		_worryFunction = worryFunction;
		_divisor = divisor;
		_numInspections = 0;
		
		_items = new ArrayList<Integer>();
		
		for (var worry : items.split(", "))
		{
			_items.add(Integer.parseInt(worry));
		}
	}
	
	public int getIndex()
	{
		return _index;
	}
	
	public int getActivityIndex()
	{
		return _numInspections;
	}
	
	public void setTargetMonkeys(Monkey targetMonkeyIfTrue, Monkey targetMonkeyIfFalse)
	{
		_targetMonkeyIfTrue = targetMonkeyIfTrue;
		_targetMonkeyIfFalse = targetMonkeyIfFalse;
	}
	
	private void acceptItem(int item)
	{
		_items.add(item); // add to end
	}

	public void runRound()
	{
		for (var item : _items)
		{
			++_numInspections;
			
			var level = _worryFunction.apply(item);
			level = Math.floorDiv(level,  3);
			
			// The item's new level is thrown to the other monkey
			if (level % _divisor == 0)
			{
				_targetMonkeyIfTrue.acceptItem(level);
			}
			else
			{
				_targetMonkeyIfFalse.acceptItem(level);
			}
		}
		
		_items.clear();
	}
	
	
}
