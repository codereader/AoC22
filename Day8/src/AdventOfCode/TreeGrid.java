package AdventOfCode;

import java.util.List;

// Represents a quadratic tree grid of equal width and height
public class TreeGrid
{
	// Tree matrix storing the heights
	private final int[][] _tree;
	
	public TreeGrid(int length)
	{
		_tree = new int[length][length];
	}
	
	public int getVisibleTreeCount()
	{
		int numVisibleTrees = 0;
		
		for (int row = 0; row < _tree.length; ++row)
		{
			for (int column = 0; column < _tree[row].length; ++column)
			{
				if (treeIsVisible(row, column))
				{
					++numVisibleTrees;
				}
			} 
		} 
		
		return numVisibleTrees;
	}
	
	private boolean treeIsVisible(int row, int column)
	{
		// If there's no higher tree from any directions this tree is visible
		return treeIsVisibleFrom(row, column, 0, -1) ||
			treeIsVisibleFrom(row, column, 0, +1) ||
			treeIsVisibleFrom(row, column, -1, 0) ||
			treeIsVisibleFrom(row, column, +1, 0);
	}

	private boolean treeIsVisibleFrom(int row, int column, int rowIncrement, int columnIncrement) 
	{
		if (rowIncrement + columnIncrement == 0) throw new IllegalArgumentException("One increment must be != 0");
		
		int treeHeight = _tree[row][column];
		
		for (int r = row + rowIncrement, c = column + columnIncrement;
			 r >= 0 && r < _tree.length && c >= 0 && c < _tree[r].length;
			 r += rowIncrement, c += columnIncrement)
		{
			if (_tree[r][c] >= treeHeight)
			{
				return false;
			}
		}
		
		// No higher tree encountered
		return true;
	}

	// Named constructor parsing the grid from the given set of lines
	public static TreeGrid ParseFromLines(List<String> lines)
	{
		if (lines.isEmpty()) throw new IllegalArgumentException("No lines given");
		
		var firstLine = lines.get(0);
		
		if (firstLine.length() != lines.size()) throw new IllegalArgumentException("Dimensions do not match");
		
		var grid = new TreeGrid(firstLine.length());
		
		for (int row = 0; row < lines.size(); ++row)
		{
			var line = lines.get(row);
			
			for (int column = 0; column < line.length(); column++)
			{
				grid._tree[row][column] = line.charAt(column) - '0'; 
			} 
		} 
		
		return grid;
	}
}
