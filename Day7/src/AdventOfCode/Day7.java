package AdventOfCode;

import AdventOfCode.Common.FileUtils;

public class Day7 {

	private static Long _part1Sum = 0L;
	private static Directory _part2Directory = null;
	
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
				System.out.println(String.format("Found %s with size: %d", dir.getName(), size));
				_part1Sum += size;
			}
		});
		
		System.out.println(String.format("[Part1]: Total sum: %d", _part1Sum));
		
		final Long TotalCapacity = 70000000L;
		final Long RequiredSpace = 30000000L;
		
		final Long freeSpace = TotalCapacity - fileSystem.getRootDirectory().getSizeIncludingChildren();
		final Long spaceToRelease = RequiredSpace - freeSpace;
		
		System.out.println(String.format("[Part2]: Required space %d", spaceToRelease));
		
		fileSystem.foreachDirectory(dir -> 
		{
			var size = dir.getSizeIncludingChildren();
			
			if (size >= spaceToRelease)
			{
				if (_part2Directory == null || _part2Directory.getSizeIncludingChildren() > size)
				{
					System.out.println(String.format("Found new best directory %s with size: %d", dir.getName(), size));
					_part2Directory = dir;
				}
			}
		});
		
		System.out.println(String.format("[Part2]: Total size of best directory %s: %d", 
				_part2Directory.getName(), _part2Directory.getSizeIncludingChildren()));
	}

}
