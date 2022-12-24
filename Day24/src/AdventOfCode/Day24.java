package AdventOfCode;

import java.util.HashSet;
import java.util.TreeMap;

import AdventOfCode.Common.FileUtils;

public class Day24
{
	public static void main(String[] args)
	{
		var lines = FileUtils.readFile("./input.txt");
		
		var blizzards = BlizzardCollection.ParseFromInput(lines);
		
		int width = lines.get(0).length() - 2;
		int height = lines.size() - 2;
		var blizzardCache = new BlizzardCache(blizzards, width, height);
		
		var startField = new Field(blizzardCache, 0);
		
		System.out.println(String.format("Initial field:\n%s", startField));
		System.out.println(String.format("Target coords:\n%s", startField.getTargetPosition()));
		
		var field = runSimulation(startField);
		System.out.println(String.format("[Part1]: Best time: %d", field.Time));
		
		var firstRunToTarget = field.Time;
		
		// Part2: Go back to the start
		field.setTargetPosition(field.getStartCoords());
		
		var fieldBackHome = runSimulation(field);
		System.out.println(String.format("Back at the start at time: %d", fieldBackHome.Time));
		
		fieldBackHome.setTargetPosition(fieldBackHome.getDefaultTargetCoords());
		var finalField = runSimulation(fieldBackHome);
		System.out.println(String.format("[Part2]: At the target position again: %d", finalField.Time));
	}
	
	private static Field runSimulation(Field startField)
	{
		int bestTime = Integer.MAX_VALUE;
		Field finalField = null;
		
		var fieldsToInvestigate = new TreeMap<Integer, HashSet<Field>>();
		var startStack = new HashSet<Field>();
		startStack.add(startField);
		fieldsToInvestigate.put(startField.Time, startStack);
		
		while (!fieldsToInvestigate.isEmpty())
		{
			var lowestEntry = fieldsToInvestigate.firstEntry();
			
			if (lowestEntry.getValue().isEmpty())
			{
				fieldsToInvestigate.remove(lowestEntry.getKey());
				continue;
			}
			
			var field = lowestEntry.getValue().stream().findFirst().get();
			lowestEntry.getValue().remove(field);
			
			//System.out.println(String.format("Field at Time %d:\n%s\n-----", field.Time, field));
			
			// Did we reach the goal?
			if (field.targetReached())
			{
				bestTime = field.Time;
				finalField = field;
				break;
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
				
				var set = fieldsToInvestigate.computeIfAbsent(newField.Time, t -> new HashSet<Field>());
				set.add(newField);
			}
			
			// Consider moving
			var possibleDirections = field.getPossibleNextDirections();

			for (var direction : possibleDirections)
			{
				var newField = new Field(field, field.Time + 1, direction); // move
				
				var set = fieldsToInvestigate.computeIfAbsent(newField.Time, t -> new HashSet<Field>());
				set.add(newField);
			}
		}
		
		return finalField;
	}
}
