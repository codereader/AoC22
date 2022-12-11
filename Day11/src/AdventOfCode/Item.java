package AdventOfCode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Item {
	private List<Integer> _divisors;
	private List<Integer> _modulos;
	
	private int _rawValue;
	
	public Item(int initialValue)
	{
		_rawValue = initialValue;
	}
	
	// Initialises the modulus array with the given set of divisors
	public void setDivisors(List<Integer> divisors)
	{
		_divisors = divisors;
		
		_modulos = new ArrayList<Integer>();
		
		for (var divisor : _divisors)
		{
			_modulos.add(_rawValue % divisor);
		}
	}
	
	public int getRawValue()
	{
		return _rawValue;
	}
	
	// Setting the raw value, needed for part1 with no worry divisors 
	public void applyToRawValue(Function<Integer, Integer> function)
	{
		_rawValue = function.apply(_rawValue);
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
