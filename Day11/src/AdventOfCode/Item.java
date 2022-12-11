package AdventOfCode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Item {
	private List<Integer> _divisors;
	private List<Integer> _modulos;
	
	private int _initialValue;
	
	public Item(int initialValue)
	{
		_initialValue = initialValue;
	}
	
	public void setDivisors(List<Integer> divisors)
	{
		_divisors = divisors;
		
		_modulos = new ArrayList<Integer>();
		
		for (var divisor : _divisors)
		{
			_modulos.add(_initialValue % divisor);
		}
	}
	
	public boolean isDivisableForMonkey(int monkeyIndex)
	{
		return _modulos.get(monkeyIndex) == 0;
	}
	
	public void apply(Function<Integer, Integer> worryFunction)
	{
		for (int i = 0; i < _modulos.size(); i++)
		{
			var value = _modulos.get(i);
			value = worryFunction.apply(value);
			_modulos.set(i, value % _divisors.get(i));
		}
	}
}
