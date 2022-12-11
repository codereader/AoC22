package AdventOfCode;

import java.util.ArrayList;
import java.util.List;

import AdventOfCode.Common.FileUtils;

public class Day11 {

	public static void main(String[] args) {
		var lines = FileUtils.readFile("./input.txt");
		
		var monkeys = getMonkeySetupPart1();
		
		for (int i = 0; i < 20; i++)
		{
			monkeys.stream().forEach(m -> m.runRound());
		}
		
		var businessLevel = monkeys.stream().map(m -> m.getActivityIndex())
				.sorted((idx1, idx2) -> Integer.compare(idx2, idx1)) // reverse order
				.limit(2).reduce(1, (a, b) -> a * b);
		
		System.out.println(String.format("Monkey Business Level: %d", businessLevel));
	}

	private static List<Monkey> getMonkeySetupPart1()
	{
		var monkeys = new ArrayList<Monkey>();
		
		monkeys.add(new Monkey(0, "89, 73, 66, 57, 64, 80", w -> w * 3, 13));
		monkeys.add(new Monkey(1, "83, 78, 81, 55, 81, 59, 69", w -> w + 1, 3));
		monkeys.add(new Monkey(2, "76, 91, 58, 85", w -> w * 13, 7));
		monkeys.add(new Monkey(3, "71, 72, 74, 76, 68", w -> w * w, 2));
		monkeys.add(new Monkey(4, "98, 85, 84", w -> w + 7, 19));
		monkeys.add(new Monkey(5, "78", w -> w + 8, 5));
		monkeys.add(new Monkey(6, "86, 70, 60, 88, 88, 78, 74, 83", w -> w + 4, 11));
		monkeys.add(new Monkey(7, "81, 58", w -> w + 5, 17));
		
		monkeys.get(0).setTargetMonkeys(monkeys.get(6), monkeys.get(2));
		monkeys.get(1).setTargetMonkeys(monkeys.get(7), monkeys.get(4));
		monkeys.get(2).setTargetMonkeys(monkeys.get(1), monkeys.get(4));
		monkeys.get(3).setTargetMonkeys(monkeys.get(6), monkeys.get(0));
		monkeys.get(4).setTargetMonkeys(monkeys.get(5), monkeys.get(7));
		monkeys.get(5).setTargetMonkeys(monkeys.get(3), monkeys.get(0));
		monkeys.get(6).setTargetMonkeys(monkeys.get(1), monkeys.get(2));
		monkeys.get(7).setTargetMonkeys(monkeys.get(3), monkeys.get(5));
		
		return monkeys;
	}
	
	private static List<Monkey> getMonkeyTestSetup()
	{
		var monkeys = new ArrayList<Monkey>();
		
		monkeys.add(new Monkey(0, "79, 98", w -> w * 19, 23));
		monkeys.add(new Monkey(1, "54, 65, 75, 74", w -> w + 6, 19));
		monkeys.add(new Monkey(2, "79, 60, 97", w -> w * w, 13));
		monkeys.add(new Monkey(3, "74", w -> w + 3, 17));
		
		monkeys.get(0).setTargetMonkeys(monkeys.get(2), monkeys.get(3));
		monkeys.get(1).setTargetMonkeys(monkeys.get(2), monkeys.get(0));
		monkeys.get(2).setTargetMonkeys(monkeys.get(1), monkeys.get(3));
		monkeys.get(3).setTargetMonkeys(monkeys.get(0), monkeys.get(1));
		
		return monkeys;
	}
}
