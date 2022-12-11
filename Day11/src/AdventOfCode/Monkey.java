package AdventOfCode;

import java.util.ArrayList;
import java.util.function.Function;

public class Monkey {
	
	private final int _index;
	private final Function<Long, Long> _worryFunction;
	private final ArrayList<Long> _items;
	private final Long _divisor;
	private Monkey _targetMonkeyIfTrue;
	private Monkey _targetMonkeyIfFalse;
	private long _numInspections;
	private Long _worryDivisor;
	
	public Monkey(int index, String items, Function<Long, Long> worryFunction, int divisor)
	{
		_index = index;
		_worryFunction = worryFunction;
		_divisor = Long.valueOf(divisor);
		_numInspections = 0;
		_worryDivisor = Long.valueOf(3L); // by default
		
		_items = new ArrayList<Long>();
		
		for (var worry : items.split(", "))
		{
			_items.add(Long.valueOf(Long.parseLong(worry)));
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
	
	public void setTargetMonkeys(Monkey targetMonkeyIfTrue, Monkey targetMonkeyIfFalse)
	{
		_targetMonkeyIfTrue = targetMonkeyIfTrue;
		_targetMonkeyIfFalse = targetMonkeyIfFalse;
	}
	
	private void acceptItem(Long item)
	{
		_items.add(item); // add to end
	}
	
	public void disableWorryDivision()
	{
		_worryDivisor = Long.valueOf(1L);
	}

	public void runRound()
	{
		for (var item : _items)
		{
			++_numInspections;
			
			var level = _worryFunction.apply(item);
			
			level = Math.floorDiv(level, _worryDivisor);
			
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
