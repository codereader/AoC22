package AdventOfCode;

import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class NumberSet
{
	private class Slot
	{
		public Slot(long value)
		{
			Value = value;
		}
		
		public final Long Value;
		
		public Slot Previous;
		public Slot Next;
		
		public int Position;
	}
	
	// Stores the original order, pointing to the slot where the number currently is
	private final List<Slot> _index;
	private final TreeMap<Integer, Slot> _slotPositions;
	
	public NumberSet(List<Long> numbers)
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
			// Subtract the full cycles before doing the rest of the steps
			var numMoves = slot.Value % (getNumberCount() - 1);  
			
			for (var i = 0; i < numMoves; ++i)
			{
				// slot.Next is going to be reassigned in swapPositions
				swapPositions(slot, slot.Next);
			}
		}
		else // slot.Value < 0
		{
			var numMoves = Math.abs(slot.Value) % (getNumberCount() - 1);
			
			for (var i = 0; i < numMoves; ++i)
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
	public long getNumberAt(int position)
	{
		return _slotPositions.get(position % _index.size()).Value;
	}
	
	public int getPosition(long value)
	{
		return _index.stream().filter(slot -> slot.Value == value).findFirst().get().Position;
	}
	
	public long getGroveCoordinates()
	{
		var zeroIndex = getPosition(0);
		
		var first = getNumberAt(zeroIndex + 1000);
		var second = getNumberAt(zeroIndex + 2000);
		var third = getNumberAt(zeroIndex + 3000);
		
		System.out.println(String.format("1000th: %d, 2000th: %d, 3000th: %d", first, second, third));
		
		return first + second + third;
	}
	
	@Override
	public String toString()
	{
		return _slotPositions.entrySet().stream()
			.map(slot -> slot.getValue().Value.toString())
			.collect(Collectors.joining(","));
	}
}
