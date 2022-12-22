package AdventOfCode;

import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class NumberSet
{
	private class Slot
	{
		public Slot(int value)
		{
			Value = value;
		}
		
		public final Integer Value;
		
		public Slot Previous;
		public Slot Next;
		
		public int Position;
	}
	
	// Stores the original order, pointing to the slot where the number currently is
	private final List<Slot> _index;
	private final TreeMap<Integer, Slot> _slotPositions;
	
	public NumberSet(List<Integer> numbers)
	{
		// Create a slot for each original number, retaining the order
		_index = numbers.stream().map(i -> new Slot(i)).collect(Collectors.toList());
		_slotPositions = new TreeMap<>();
		
		// Connect the links between the numbers, assign positions
		for (int j = 0; j < _index.size(); j++)
		{
			var nextIndex = (j + 1) % _index.size();
			
			_index.get(j).Position = j;
			_slotPositions.put(j, _index.get(j));
			
			_index.get(j).Next = _index.get(nextIndex);
			_index.get(nextIndex).Previous = _index.get(j);
		}
	}
	
	public int getNumberCount()
	{
		return _index.size();
	}
	
	public void moveNthNumber(int originalIndex)
	{
		var slot = _index.get(originalIndex);
	
		//System.out.println(String.format("Move %d", slot.Value));
		
		if (slot.Value == 0) return; // 0 does not move
		
		if (slot.Value > 0)
		{
			for (var i = 0; i < slot.Value; ++i)
			{
				// slot.Next is going to be reassigned in swapPositions
				swapPositions(slot, slot.Next);
			}
		}
		else // slot.Value < 0
		{
			for (var i = 0; i < Math.abs(slot.Value); ++i)
			{
				// slot.Next is going to be reassigned in swapPositions
				swapPositions(slot.Previous, slot);
			}
		}
	}
	
	private void swapPositions(Slot first, Slot second)
	{
		first.Previous.Next = second;
		second.Previous = first.Previous;
		
		first.Next = second.Next;
		second.Next.Previous = first;
		
		first.Previous = second;
		second.Next = first;
		
		// Reassign the position mapping
		var currentPosition = first.Position;
		var newPosition = second.Position;
		
		_slotPositions.put(newPosition, first);
		_slotPositions.put(currentPosition, second);
		
		first.Position = newPosition;
		second.Position = currentPosition;
	}
	
	// Position will be wrapping around
	public int getNumberAt(int position)
	{
		return _slotPositions.get(position % _index.size()).Value;
	}
	
	public int getPosition(int value)
	{
		return _index.stream().filter(slot -> slot.Value == value).findFirst().get().Position;
	}
	
	@Override
	public String toString()
	{
		return _slotPositions.entrySet().stream()
			.map(slot -> slot.getValue().Value.toString())
			.collect(Collectors.joining(","));
	}
}
