package AdventOfCode;

import AdventOfCode.Common.FileUtils;

public class Day10 {

	public static void main(String[] args) {
		var lines = FileUtils.readFile("./input.txt");
		
		var cycleHandler = new CycleHandler();
		
		var cpu = new Processor(cycleHandler);
		cpu.processInstructions(lines);
		
		System.out.println(String.format("[Part1]: Total Signal Strength = %d", cycleHandler.getTotalSignalStrength()));
	}

	
}
