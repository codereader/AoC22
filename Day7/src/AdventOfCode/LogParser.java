package AdventOfCode;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class LogParser {
	
	public static FileSystem ProcessLog(Deque<String> lines)
	{
		var fileSystem = new FileSystem();
		var currentPath = new ArrayList<String>();
		
		while (!lines.isEmpty())
		{
			var line = lines.pollFirst();
			
			if (line.equals("$ cd /"))
			{
				currentPath = new ArrayList<String>();
				continue;
			}
			
			if (line.startsWith("$ cd "))
			{
				var dirName = line.substring("$ cd ".length());
				
				if (dirName.equals(".."))
				{
					currentPath.remove(currentPath.size() - 1);
				}
				else
				{
					currentPath.add(dirName);
				}
				continue;
			}
				
			if (line.equals("$ ls"))
			{
				// Proceed until next $ command is found
				while (!lines.isEmpty() && !lines.peekFirst().startsWith("$"))
				{
					line = lines.pollFirst();
					if (line.startsWith("dir "))
					{
						continue;
					}
					
					var parts = line.split(" ");
					var size = Long.parseLong(parts[0]);
					var fileName = parts[1];
					
					fileSystem.registerFile((List<String>)currentPath.clone(), fileName, size);
				}
				
				continue;
			}
			
			throw new IllegalArgumentException("Unrecognised line: " + line);
		}
		
		return fileSystem;
	}
}
