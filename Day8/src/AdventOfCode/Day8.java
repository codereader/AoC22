package AdventOfCode;

import AdventOfCode.Common.FileUtils;

public class Day8 {

	public static void main(String[] args) {
		var lines = FileUtils.readFile("./input.txt");
		
		var treeGrid = TreeGrid.ParseFromLines(lines);
		
		System.out.println(String.format("[Part1]: Number of visible trees: %d", treeGrid.getVisibleTreeCount()));
		System.out.println(String.format("[Part2]: Highest scenic score: %d", treeGrid.getHighestScenicScore()));
	}

}
