package AdventOfCode;

import java.util.function.BiFunction;

public class Monkey
{
	private final String _name;
	public Long _value = null;
	public String _operandLeft;
	public String _operandRight;
	public BiFunction<Long, Long, Long> _evaluation;
	
	public Monkey(String line)
	{
		_name = line.substring(0, 4);
		
		var rest = line.substring(6).split(" ");
		
		if (rest.length == 1)
		{
			_value = Long.parseLong(rest[0]);
		}
		else if (rest.length == 3)
		{
			_operandLeft = rest[0];
			_operandRight = rest[2];
			
			switch (rest[1].charAt(0))
			{
			case '+': _evaluation = (a,b) -> a + b; break;
			case '-': _evaluation = (a,b) -> a - b; break;
			case '*': _evaluation = (a,b) -> a * b; break;
			case '/': _evaluation = (a,b) -> a / b; break;
			default: throw new IllegalArgumentException("Operand " + rest[1] + " not supported");
			}
		}
	}
	
	public String getName()
	{
		return _name;
	}
	
	public String getOperandLeft()
	{
		return _operandLeft;
	}
	
	public String getOperandRight()
	{
		return _operandRight;
	}
	
	public boolean hasValue()
	{
		return _value != null;
	}
	
	public long getValue()
	{
		return _value;
	}
	
	public void calculateValue(Long value1, Long value2)
	{
		_value = _evaluation.apply(value1, value2);
	}
}
