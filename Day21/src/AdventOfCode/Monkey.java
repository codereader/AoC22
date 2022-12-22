package AdventOfCode;

import java.util.function.BiFunction;

public class Monkey
{
	private final String _name;
	public Long _value = null;
	public String _operandLeft;
	public String _operandRight;
	public String _operator;
	public BiFunction<Long, Long, Long> _evaluation;
	
	Monkey _leftMonkey;
	Monkey _rightMonkey;
	
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
			_operator = rest[1];
			
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
	
	public Monkey getLeft()
	{
		return _leftMonkey;
	}
	
	public Monkey getRight()
	{
		return _rightMonkey;
	}
	
	public void setLeft(Monkey left)
	{
		_leftMonkey = left;
	}
	
	public void setRight(Monkey right)
	{
		_rightMonkey = right;
	}
	
	public String getOperandLeft()
	{
		return _operandLeft;
	}
	
	public String getOperandRight()
	{
		return _operandRight;
	}
	
	public String getOperator()
	{
		return _operator;
	}
	
	public boolean hasValue()
	{
		return _value != null;
	}
	
	public boolean isHuman()
	{
		return getName().equals("humn");
	}
	
	public long getValue()
	{
		return _value;
	}
	
	public void clearValue()
	{
		_value = null;
	}
	
	public void calculateValue(Long value1, Long value2)
	{
		_value = _evaluation.apply(value1, value2);
	}
}
