package AdventOfCode;

import AdventOfCode.Common.FileUtils;

public class Day25
{
	public static void main(String[] args)
	{
		var lines = FileUtils.readFile("./input.txt");

		var sum = "00000000000000000000000000".toCharArray();
		for (int line = 0; line < lines.size(); line++)
		{
			var snafu = lines.get(line);
			
			for (int i = 0; i < sum.length; ++i)
			{
				var sumDigitIndex = sum.length - i - 1;
				var incomingDigitIndex = snafu.length() - i - 1;
				
				if (incomingDigitIndex < 0) break;
				
				var incomingDigit = snafu.charAt(incomingDigitIndex);
				addDigit(sum, sumDigitIndex, incomingDigit);
			}
		}
		
		System.out.println(String.format("[Part1]: Final SNAFU: %s", new String(sum)));
	}
	
	private static void addDigit(char[] number, int index, char incoming)
	{
		if (incoming == '0') return;
		
		var value = getValue(number[index]) + getValue(incoming);
		
		if (value < -2)
		{
			// Underflow
			number[index] = getSnafu(value + 5);
			addDigit(number, index - 1, getSnafu(-1));
		}
		else if (value > 2)
		{
			// Overflow
			number[index] = getSnafu(value - 5);
			addDigit(number, index - 1, getSnafu(+1));
		}
		else
		{
			number[index] = getSnafu(value);
		}
	}
	
	private static int getValue(char ch)
	{
		switch (ch)
		{
		case '0': return 0;
		case '1': return 1;
		case '2': return 2;
		case '-': return -1;
		case '=': return -2;
		default: throw new IllegalArgumentException("Unknown digit " + ch);
		}
	}
	
	private static char getSnafu(int value)
	{
		switch (value)
		{
		case 0: return '0';
		case 1: return '1';
		case 2: return '2';
		case -1: return '-';
		case -2: return '=';
		default: throw new IllegalArgumentException(String.format("Unsupported number %d", value));
		}
	}
}
