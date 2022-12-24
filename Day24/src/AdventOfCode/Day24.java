package AdventOfCode;

import java.util.ArrayDeque;
import java.util.TreeMap;

import AdventOfCode.Common.FileUtils;

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
		
		var fieldsToInvestigate = new TreeMap<Integer, ArrayDeque<Field>>();
		var startStack = new ArrayDeque<Field>();
		startStack.push(startField);
		fieldsToInvestigate.put(startField.Time, startStack);
		
		int bestTime = Integer.MAX_VALUE;
		
		while (!fieldsToInvestigate.isEmpty())
		{
			var lowestEntry = fieldsToInvestigate.firstEntry();
			
			if (lowestEntry.getValue().isEmpty())
			{
				fieldsToInvestigate.remove(lowestEntry.getKey());
				continue;
			}
			
			var field = lowestEntry.getValue().pop();
			
			//System.out.println(String.format("Field at Time %d:\n%s\n-----", field.Time, field));
			
			// Did we reach the goal?
			if (field.targetReached())
			{
				if (field.Time < bestTime)
				{
					bestTime = field.Time;
				}
				continue;
			}
			
			// Discard this situation?
			if (field.Time > bestTime)
			{
				continue; // Taking too long already
			}
			
			// Consider waiting
			// Are we hit by a blizzard next move?
			if (!field.elfIsHitByBlizzardNextMove())
			{
				// We can safely stay here, check the next time frame
				var newField = new Field(field, field.Time + 1, null); // stay
				
				var stack = fieldsToInvestigate.computeIfAbsent(newField.Time, t -> new ArrayDeque<Field>());
				stack.push(newField);
			}
			
			// Consider moving
			var possibleDirections = field.getPossibleNextDirections();

			for (var direction : possibleDirections)
			{
				var newField = new Field(field, field.Time + 1, direction); // move
				
				var stack = fieldsToInvestigate.computeIfAbsent(newField.Time, t -> new ArrayDeque<Field>());
				stack.push(newField);
			}
		}
		
		System.out.println(String.format("[Part1]: Best time: %d", bestTime));
	}
}
