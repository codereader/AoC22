package AdventOfCode;

import java.util.stream.Collectors;
import AdventOfCode.Common.FileUtils;

public class Day21
{
	public static void main(String[] args)
	{
		var lines = FileUtils.readFile("./input.txt");
		
		var monkeys = lines.stream().map(line -> new Monkey(line)).collect(Collectors.toMap(m -> m.getName(), m -> m));
		
		var root = monkeys.get("root");
		
		while (!root.hasValue())
		{
			for (var entry : monkeys.entrySet())
			{
				var monkey = entry.getValue();
				
				if (monkey.hasValue()) continue;
				
				var left = monkeys.get(monkey.getOperandLeft());
				
				if (!left.hasValue()) continue;
				
				var right = monkeys.get(monkey.getOperandRight());
				
				if (!right.hasValue()) continue;
				
				System.out.println(String.format("Calculating monkey %s", monkey.getName()));
				monkey.calculateValue(left.getValue(), right.getValue());
			}
		}
		
		System.out.println(String.format("[Part1]: Root has value %d", root.getValue()));
	}

}
