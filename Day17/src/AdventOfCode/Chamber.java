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
	
	public Rock FallingRock;
	
	private ArrayList<char[]> _grid;
	
	public Chamber()
	{
		_grid = new ArrayList<>();
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
		
		var maxHeight = FallingRock != null ? Math.max(_grid.size(), FallingRock.Position.getY() + 1) : _grid.size();
		
		for (var y = maxHeight - 1; y >= 0; --y)
		{
			var row = getRowAsChars(y);
			
			if (FallingRock != null &&
				y <= FallingRock.Position.getY() && 
				y > FallingRock.Position.getY() - FallingRock.Height)
			{
				// Superimpose the rock shape with @ signs
				for (var x = 0; x < FallingRock.Width; ++x)
				{
					var rockPosition = new LongVector2(x, FallingRock.Position.getY() - y);
					
					if (FallingRock.isSolidAt(rockPosition))
					{
						row[(int)FallingRock.Position.getX() + x] = '@';
					}
				}
			}
			
			// No falling rock, just output the line
			text.append(row);
			text.append("\n");
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
		if (y < _firstNonSolidRow)
		{
			return "+++++++++".toCharArray();
		}
		
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
	}

	public void spawnRock(Rock rock)
	{
		rock.Position = getSpawnPosition(rock);
		FallingRock = rock;
	}
}
