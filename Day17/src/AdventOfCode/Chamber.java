package AdventOfCode;

import java.util.ArrayList;
import java.util.Arrays;

import AdventOfCode.Common.Vector2;

// Chamber Y positions are measured from the bottom (0 == floor, 1 == the first row above it)
public class Chamber
{
	public final int Width = 7;
	public final int WallWidth = 1;
	public final int FloorThickness = 1;
	public final int PaddingToWall = 2;
	public final int VerticalSpawnPadding = 3;
	public final int WidthIncludingWalls = Width + 2;
	
	public Rock FallingRock;
	
	private ArrayList<char[]> _grid;
	
	public Chamber()
	{
		_grid = new ArrayList<>();
		
		for (int i = 0; i < FloorThickness; i++)
		{
			_grid.add("+-------+".toCharArray());
		}
		
		FallingRock = null;
	}
	
	public boolean rockCanMoveTo(Rock rock, Vector2 direction)
	{
		// Hypothetically move the rock to the given direction
		// Check if any rock substance is colliding with existing matter
		return !isCollidingAtPosition(rock, rock.Position.plus(direction));
	}
	
	private boolean isCollidingAtPosition(Rock rock, Vector2 testPosition)
	{
		for (var y = 0; y < rock.Height; ++y)
		{
			for (var x = 0; x < rock.Width; ++x)
			{
				var localRockPos = new Vector2(x, y);
				
				// Apply inverted Y rock position since higher chamber rows have higher Y coordinates
				if (rock.isSolidAt(localRockPos) && 
					isSolid(testPosition.plus(new Vector2(localRockPos.getX(), -localRockPos.getY()))))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean isSolid(Vector2 position)
	{
		// Out of vertical bounds?
		if (position.getY() >= _grid.size())
		{
			// Check X coordinates only
			return position.getX() <= 0 || position.getX() >= WallWidth + Width;
		}
	
		// Clipping into floor?
		if (position.getY() < 1) return true;
		
		// Position is within or beyond walls?
		if (position.getX() < 1 || position.getX() >= WallWidth + Width) return true;
		
		// Check the actual row, everything other than spaces is considered solid
		return _grid.get(position.getY())[position.getX()] != ' ';
	}
	
	public Vector2 getSpawnPosition(Rock rock)
	{
		return new Vector2(
			WallWidth + PaddingToWall, 
			FloorThickness + getMaximumRockHeight() + VerticalSpawnPadding + rock.Height - 1
		);
	}
	
	// Get amount of rocky lines (excluding the floor)
	public int getMaximumRockHeight()
	{
		return (int)_grid.stream()
			.skip(1)
			.filter(Chamber::containsRock)
			.count();
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
					var rockPosition = new Vector2(x, FallingRock.Position.getY() - y);
					
					if (FallingRock.isSolidAt(rockPosition))
					{
						row[FallingRock.Position.getX() + x] = '@';
					}
				}
			}
			
			// No falling rock, just output the line
			text.append(row);
			text.append("\n");
		}
		
		return text.toString();
	}
	
	private char[] createEmptyRow()
	{
		var row = new char[WallWidth*2 + Width];
		
		// Fill with space
		Arrays.fill(row, ' ');
		
		// add walls
		row[0] = '|';
		row[row.length - 1] = '|';
		
		return row;
	}
	
	private char[] getRowAsChars(int y)
	{
		return y >= _grid.size() ? createEmptyRow() : _grid.get(y).clone();
	}

	public void embedRock(Rock rock)
	{
		// Ensure grid is large enough
		while (_grid.size() < rock.Position.getY() + 1)
		{
			_grid.add(createEmptyRow());
		}
		
		for (var y = rock.Position.getY() - rock.Height + 1; y <= rock.Position.getY(); ++y)
		{
			var row = getRowAsChars(y);
			
			// Superimpose the rock shape with solid signs
			for (var x = 0; x < FallingRock.Width; ++x)
			{
				var rockPosition = new Vector2(x, FallingRock.Position.getY() - y);
				
				if (FallingRock.isSolidAt(rockPosition))
				{
					row[FallingRock.Position.getX() + x] = Rock.Solid;
				}
			}
			
			_grid.set(y, row);
		}
	}

	public void spawnRock(Rock rock)
	{
		rock.Position = getSpawnPosition(rock);
		FallingRock = rock;
	}
}
