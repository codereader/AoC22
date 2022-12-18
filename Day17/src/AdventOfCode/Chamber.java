package AdventOfCode;

import java.util.ArrayList;
import java.util.Arrays;

import AdventOfCode.Common.LongVector2;

// Chamber Y positions are measured from the bottom (0 == floor, 1 == the first row above it)
public class Chamber
{
	public static final int Width = 7;
	public static final int WallWidth = 1;
	public static final int FloorThickness = 1;
	public static final int PaddingToWall = 2;
	public static final int VerticalSpawnPadding = 3;
	public static final int WidthIncludingWalls = Width + 2;
	
	// Distance from floor to the first grid row
	private long _firstNonSolidRow = 1L;
	
	private long[] _firstNonSolidBlockPerColumn;
	
	public Rock FallingRock;
	
	private ArrayList<char[]> _grid;
	
	public Chamber()
	{
		_grid = new ArrayList<>();
		_firstNonSolidBlockPerColumn = new long[Width];
		Arrays.fill(_firstNonSolidBlockPerColumn, _firstNonSolidRow);
		FallingRock = null;
	}
	
	public boolean rockCanMoveTo(Rock rock, LongVector2 direction)
	{
		// Hypothetically move the rock to the given direction
		// Check if any rock substance is colliding with existing matter
		return !isCollidingAtPosition(rock, rock.Position.plus(direction));
	}
	
	private boolean isCollidingAtPosition(Rock rock, LongVector2 testPosition)
	{
		for (var y = 0; y < rock.Height; ++y)
		{
			for (var x = 0; x < rock.Width; ++x)
			{
				var localRockPos = new LongVector2(x, y);
				
				// Apply inverted Y rock position since higher chamber rows have higher Y coordinates
				if (rock.isSolidAt(localRockPos) && 
					isSolid(testPosition.plus(new LongVector2(localRockPos.getX(), -localRockPos.getY()))))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean isSolid(LongVector2 position)
	{
		var physicalRowIndex = (int)(position.getY() - _firstNonSolidRow); 
		
		// Out of vertical bounds?
		if (physicalRowIndex >= _grid.size())
		{
			// Check X coordinates only
			return position.getX() <= 0 || position.getX() >= WallWidth + Width;
		}
	
		// Clipping into floor?
		if (physicalRowIndex < 0) return true;
		
		// Position is within or beyond walls?
		if (position.getX() < 1 || position.getX() >= WallWidth + Width) return true;
		
		// Check the actual row, everything other than spaces is considered solid
		return _grid.get(physicalRowIndex)[(int)position.getX()] != ' ';
	}
	
	public LongVector2 getSpawnPosition(Rock rock)
	{
		return new LongVector2(
			WallWidth + PaddingToWall, 
			FloorThickness + getMaximumRockHeight() + VerticalSpawnPadding + rock.Height - 1
		);
	}
	
	// Get amount of rocky lines (excluding the floor)
	public long getMaximumRockHeight()
	{
		return _grid.stream()
			.filter(Chamber::containsRock)
			.count() + _firstNonSolidRow - FloorThickness; // deduct the floor height
	}
	
	public static boolean containsRock(char[] row)
	{
		for (var ch : row)
		{
			if (ch == '#') return true;
		}
		
		return false;
	}
	
	@Override
	public String toString()
	{
		var text = new StringBuilder();
		
		var fallingRockYPosition = FallingRock != null ? FallingRock.Position.getY() - _firstNonSolidRow : 0;
		var maxHeight = (int)Math.max(_grid.size(), fallingRockYPosition + 1);
		
		for (var y = maxHeight - 1; y >= 0; --y)
		{
			var row = y < _grid.size() ? _grid.get(y).clone() : createEmptyRow();
			
			if (FallingRock != null &&
				y <= fallingRockYPosition && 
				y > fallingRockYPosition - FallingRock.Height)
			{
				// Superimpose the rock shape with @ signs
				for (var x = 0; x < FallingRock.Width; ++x)
				{
					var rockPosition = new LongVector2(x, fallingRockYPosition - y);
					
					if (FallingRock.isSolidAt(rockPosition))
					{
						row[(int)FallingRock.Position.getX() + x] = '@';
					}
				}
			}
			
			// No falling rock, just output the line
			text.append(String.format("%4d", y + _firstNonSolidRow));
			text.append(' ');
			text.append(row);
			text.append('\n');
		}
		
		return text.toString();
	}
	
	private static char[] createEmptyRow()
	{
		var row = new char[WallWidth*2 + Width];
		
		// Fill with space
		Arrays.fill(row, ' ');
		
		// add walls
		row[0] = '|';
		row[row.length - 1] = '|';
		
		return row;
	}
	
	private char[] getRowAsChars(long y)
	{
		var actualIndex = (int)(y - _firstNonSolidRow);
		return actualIndex >= _grid.size() ? createEmptyRow() : _grid.get(actualIndex).clone();
	}

	public void embedRock(Rock rock)
	{
		// Ensure the physical grid is large enough
		var requiredGridSize = (int)(rock.Position.getY() - _firstNonSolidRow) + 1;
		while (_grid.size() < requiredGridSize)
		{
			_grid.add(createEmptyRow());
		}
		
		for (var y = rock.Position.getY() - rock.Height + 1; y <= rock.Position.getY(); ++y)
		{
			var row = getRowAsChars(y);
			
			// Superimpose the rock shape with solid signs
			for (var x = 0; x < FallingRock.Width; ++x)
			{
				var rockPosition = new LongVector2(x, FallingRock.Position.getY() - y);
				
				if (FallingRock.isSolidAt(rockPosition))
				{
					row[(int)FallingRock.Position.getX() + x] = Rock.Solid;
				}
			}
			
			var physicalRowIndex = (int)(y - _firstNonSolidRow);
			_grid.set(physicalRowIndex, row);
		}
		
		// Update the solid heights and reduce the grid if necessary
		// Check the first solid block in that column
		for (var y = 0; y < _grid.size(); ++y)
		{
			for (var x = WallWidth; x < Width + WallWidth; ++x)
			{
				var index = x - WallWidth;
				
				if (_grid.get(y)[x] == Rock.Solid)
				{
					_firstNonSolidBlockPerColumn[index] = Math.max(_firstNonSolidBlockPerColumn[index], y + _firstNonSolidRow + 1);
				}
			}
		}
		
		long minimumFirstNonSolidRow = Long.MAX_VALUE;
		
		for (int x = 0; x < _firstNonSolidBlockPerColumn.length; x++)
		{
			minimumFirstNonSolidRow = Math.min(_firstNonSolidBlockPerColumn[x], minimumFirstNonSolidRow);
		}
		
		// Keep a few lines to workaround the problem of stones stopping too early
		minimumFirstNonSolidRow -= 200;
		
		// Check the minimum row, below of which everything is solid
		// Cut off a certain amount of rows to get rid of the solids
		for (int i = 0; i < minimumFirstNonSolidRow - _firstNonSolidRow; i++)
		{
			_grid.remove(0);
		}
		
		_firstNonSolidRow = Math.max(_firstNonSolidRow, minimumFirstNonSolidRow);
	}

	public void spawnRock(Rock rock)
	{
		rock.Position = getSpawnPosition(rock);
		FallingRock = rock;
	}
	
	public long getFirstNonSolidRow()
	{
		return _firstNonSolidRow;
	}
	
	public void setFirstNonSolidRow(long height)
	{
		_firstNonSolidRow = height;
	}
	
	public String getHeadOfGrid()
	{
		var numRows = Math.min(_grid.size(), 20);
		var list = new ArrayList<String>();
		
		for (int i = 0; i < numRows; ++i)
		{
			list.add(new String(_grid.get(_grid.size() - i - 1)));
		}
		
		return String.join("\n", list);
	}
}
