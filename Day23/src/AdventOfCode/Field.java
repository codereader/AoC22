package AdventOfCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import AdventOfCode.Common.Bounds;
import AdventOfCode.Common.Vector2;

public class Field
{
	private final List<Elf> _elves;
	private final HashMap<Vector2, Elf> _positions;
	
	private LinkedList<List<Vector2>> _surroundingsToCheck;
	private List<Vector2> _northRow;
	private List<Vector2> _southRow;
	private List<Vector2> _westRow;
	private List<Vector2> _eastRow;
	
	public Field(List<String> lines)
	{
		_elves = new ArrayList<>();
		_positions = new HashMap<>();
		
		_northRow = new ArrayList<Vector2>();
		_northRow.add(new Vector2(-1, -1));
		_northRow.add(new Vector2(0, -1));
		_northRow.add(new Vector2(1, -1));
		
		_southRow = new ArrayList<Vector2>();
		_southRow.add(new Vector2(-1, +1));
		_southRow.add(new Vector2(0, +1));
		_southRow.add(new Vector2(1, +1));
		
		_westRow = new ArrayList<Vector2>();
		_westRow.add(new Vector2(-1, +1));
		_westRow.add(new Vector2(-1, 0));
		_westRow.add(new Vector2(-1, -1));
		
		_eastRow = new ArrayList<Vector2>();
		_eastRow.add(new Vector2(+1, +1));
		_eastRow.add(new Vector2(+1, 0));
		_eastRow.add(new Vector2(+1, -1));
		
		_surroundingsToCheck = new LinkedList<>();
		_surroundingsToCheck.add(_northRow);
		_surroundingsToCheck.add(_southRow);
		_surroundingsToCheck.add(_westRow);
		_surroundingsToCheck.add(_eastRow);
		
		for (int y = 0; y < lines.size(); y++)
		{
			for (int x = 0; x < lines.get(y).length(); ++x)
			{
				if (lines.get(y).charAt(x) == '#')
				{
					var elf = new Elf();
					elf.Position = new Vector2(x,y);
					
					_elves.add(elf);
					_positions.put(elf.Position, elf);
				}
			}
		}
	}
	
	// Returns true if the game continues
	public boolean runRound()
	{
		var anyElfMoved = false;
		var movePositions = new HashMap<Vector2, List<Elf>>();
		
		// Propose the direction each elf is moving
		for (var elf : _elves)
		{
			var anyRowOccupied = false;
			elf.ProposedPosition = null;
			
			for (var set : _surroundingsToCheck)
			{
				var occupied = rowIsOccupied(elf, set);
				anyRowOccupied |= occupied;
				
				if (!occupied && elf.ProposedPosition == null)
				{
					elf.ProposedPosition = elf.Position.plus(set.get(1)); // middle position indicates direction to move
				}
			}
			
			if (!anyRowOccupied)
			{
				// Elf doesn't need to move at all
				elf.ProposedPosition = null;
			}
			else if (elf.ProposedPosition != null)
			{
				var list = movePositions.computeIfAbsent(elf.ProposedPosition, v -> new ArrayList<Elf>());
				list.add(elf);
			}
		}
		
		// Move time
		for (var elf : _elves)
		{
			if (elf.ProposedPosition != null && movePositions.get(elf.ProposedPosition).size() == 1)
			{
				// It's the only elf wanting to move there
				_positions.remove(elf.Position);
				elf.Position = elf.ProposedPosition;
				_positions.put(elf.Position, elf);
				
				elf.ProposedPosition = null;
				anyElfMoved = true;
			}
		}
		
		// Move the first direction set to the end of the list
		_surroundingsToCheck.addLast(_surroundingsToCheck.pollFirst());
		
		if (!anyElfMoved)
		{
			return false; // stop the game
		}
		
		return true;
	}
	
	public boolean rowIsOccupied(Elf elf, List<Vector2> positions)
	{
		for (var position : positions)
		{
			if (_positions.containsKey(elf.Position.plus(position)))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public Bounds getElfBounds()
	{
		return new Bounds(
			new Vector2(
				_positions.entrySet().stream().mapToInt(e -> e.getKey().getX()).min().getAsInt(),
				_positions.entrySet().stream().mapToInt(e -> e.getKey().getY()).min().getAsInt()
			),
			new Vector2(
				_positions.entrySet().stream().mapToInt(e -> e.getKey().getX()).max().getAsInt(),
				_positions.entrySet().stream().mapToInt(e -> e.getKey().getY()).max().getAsInt()
			)
		);
	}
	
	public int getNumEmptySpacesInBounds()
	{
		var bounds = getElfBounds();
		var emptySpaces = 0;
		
		for (int y = bounds.Min.getY(); y <= bounds.Max.getY(); y++)
		{
			for (int x = bounds.Min.getX(); x <= bounds.Max.getX(); x++)
			{
				if (!_positions.containsKey(new Vector2(x,y)))
				{
					emptySpaces++;
				}
			}
		}
		
		return emptySpaces;
	}
	
	@Override
	public String toString()
	{
		var text = new StringBuilder();
		var bounds = getElfBounds().expandBy(1);
		
		for (int y = bounds.Min.getY(); y <= bounds.Max.getY(); y++)
		{
			for (int x = bounds.Min.getX(); x <= bounds.Max.getX(); x++)
			{
				text.append(_positions.containsKey(new Vector2(x,y)) ? '#' : '.');
			}
			
			text.append('\n');
		}
		
		return text.toString();
	}
}
