package AdventOfCode;

import java.util.Map;
import java.util.stream.Collectors;
import AdventOfCode.Common.FileUtils;

public class Day21
{
	public static void main(String[] args)
	{
		var lines = FileUtils.readFile("./input.txt");
		
		var monkeys = lines.stream().map(line -> new Monkey(line)).collect(Collectors.toMap(m -> m.getName(), m -> m));
		linkMonkeys(monkeys);
		
		var root = monkeys.get("root");
		
		while (!root.hasValue())
		{
			var madeProgress = false;
			
			for (var entry : monkeys.entrySet())
			{
				var monkey = entry.getValue();
				
				if (monkey.hasValue()) continue;
				
				if (!monkey.getLeft().hasValue()) continue;
				if (!monkey.getRight().hasValue()) continue;
				
				monkey.calculateValue(monkey.getLeft().getValue(), monkey.getRight().getValue());
				madeProgress = true;
			}
			
			if (!madeProgress) break;
		}
		
		System.out.println(String.format("[Part1]: Root has value %d", root.getValue()));
		
		// Part 2: Human Monkey
		
		monkeys = lines.stream().map(line -> new Monkey(line)).collect(Collectors.toMap(m -> m.getName(), m -> m));
		linkMonkeys(monkeys);
		root = monkeys.get("root");
		
		// Precalculate as many values as possible
		while (true)
		{
			var madeProgress = false;
			
			for (var entry : monkeys.entrySet())
			{
				var monkey = entry.getValue();
				
				if (monkey.hasValue() || monkey.isHuman()) continue;
				
				if (!monkey.getLeft().hasValue() || monkey.getLeft().isHuman()) continue;
				if (!monkey.getRight().hasValue() || monkey.getRight().isHuman()) continue;
				
				monkey.calculateValue(monkey.getLeft().getValue(), monkey.getRight().getValue());
				madeProgress = true;
			}
			
			if (!madeProgress) break;
		}
		
		var desiredValue = root.getLeft().hasValue() ? root.getLeft().getValue() : root.getRight().getValue();
		var startMonkey = root.getLeft().hasValue() ? root.getRight() : root.getLeft();
		
		System.out.println(String.format("Desired value at root level: %d", desiredValue));
		
		monkeys.get("humn").clearValue();
		
		var result = findHumanNumber(startMonkey, desiredValue);
		System.out.println(String.format("[Part2]: Found human value: %d", result));
	}

	private static long findHumanNumber(Monkey monkey, long desiredValue)
	{
		if (monkey.isHuman()) return desiredValue;
		
		var unknownMonkey = monkey.getRight().hasValue() ? monkey.getLeft() : monkey.getRight();
		var knownNumber = monkey.getRight().hasValue() ? monkey.getRight().getValue() : monkey.getLeft().getValue();
		
		switch (monkey.getOperator())
		{
		case "+":
			desiredValue -= knownNumber;
			break;
			
		case "*":
			desiredValue /= knownNumber;
			break;
			
		case "-":
			if (unknownMonkey == monkey.getLeft())
			{
				desiredValue += knownNumber;
			}
			else
			{
				desiredValue = knownNumber - desiredValue;
			}
			break;
			
		case "/":
			if (unknownMonkey == monkey.getLeft())
			{
				desiredValue *= knownNumber;
			}
			else
			{
				desiredValue = knownNumber / desiredValue;
			}
			break;
		}
		
		return findHumanNumber(unknownMonkey, desiredValue);
	}

	private static void linkMonkeys(Map<String, Monkey> monkeys)
	{
		for (var entry : monkeys.entrySet())
		{
			if (entry.getValue().getOperandLeft() != null)
			{
				entry.getValue().setLeft(monkeys.get(entry.getValue().getOperandLeft()));
			}
			
			if (entry.getValue().getOperandRight() != null)
			{
				entry.getValue().setRight(monkeys.get(entry.getValue().getOperandRight()));
			}
		}
	}
}
