package AdventOfCode;

import java.util.Stack;

import AdventOfCode.Common.FileUtils;
import AdventOfCode.Common.Vector2;

public class Day24
{
	public static void main(String[] args)
	{
		var lines = FileUtils.readFile("./test.txt");
		
		var blizzards = BlizzardCollection.ParseFromInput(lines);
		
		int width = lines.get(0).length() - 2;
		int height = lines.size() - 2;
		var blizzardCache = new BlizzardCache(blizzards, width, height);
		
		var startField = new Field(blizzardCache, 0);
		
		System.out.println(String.format("Initial field:\n%s", startField));
		System.out.println(String.format("Target coords:\n%s", startField.getTargetCoords()));
		
		for (int time = 1; time < 20; time++)
		{
			var field = new Field(blizzardCache, time);
			
			System.out.println(String.format("Time: %d\n%s\n-----", time, field));
		}
		
		if (true) return;
		
		var fieldsToInvestigate = new Stack<Field>();
		fieldsToInvestigate.push(startField);
		
		startField.Time = 1;
		startField.ElfDecision = new Vector2(0, 1); // move down, it's safe
		
		Field bestField = null;
		
		while (!fieldsToInvestigate.isEmpty())
		{
			var field = fieldsToInvestigate.pop();
			
			// Execute the stored elf decision
			if (field.ElfDecision != null)
			{
				field.moveElf();
				
				// Did we reach the goal?
				if (field.targetReached())
				{
					if (bestField == null || field.Time < bestField.Time)
					{
						bestField = field;
					}
					continue;
				}
			}
			
			field.moveBlizzards();
			
			// Discard this situation?
			if (bestField != null && field.Time > bestField.Time)
			{
				continue; // Taking too long already
			}
			
			// Consider waiting
			// Are we hit by a blizzard next move?
			if (!field.elfIsHitByBlizzardNextMove())
			{
				// We can safely stay here, branch off
				var newField = new Field(field, field.Time + 1, null); // stay
				fieldsToInvestigate.push(newField);
			}
			
			// Consider moving
			var possibleDirections = field.getPossibleNextDirections();

			for (var direction : possibleDirections)
			{
				var newField = new Field(field, field.Time + 1, direction); // move
				fieldsToInvestigate.push(newField);
			}
		}
		
		System.out.println(String.format("[Part1]: Best time: %d", bestField.Time));
	}
}
