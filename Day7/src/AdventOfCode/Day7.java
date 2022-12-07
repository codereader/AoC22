package AdventOfCode;

import AdventOfCode.Common.FileUtils;

public class Day7 {

	private static Long _part1Sum = 0L;
	
	public static void main(String[] args) {
		var lines = FileUtils.readFile("./input.txt");
		
		var walker = new LogWalker(lines);
		walker.ProcessLines();
		
		var fileSystem = walker.getFileSystem();
		
		fileSystem.foreachDirectory(dir -> 
		{
			var size = dir.getSizeIncludingChildren();
			
			if (size <= 100000)
			{
				System.out.println(String.format("Found %s with size: %d", dir.getName(), dir.getSizeIncludingChildren()));
				_part1Sum += size;
			}
			
			return true; // inspect further
		});
		
		System.out.println(String.format("[Part1]: Total sum: %d", _part1Sum));
	}

}
