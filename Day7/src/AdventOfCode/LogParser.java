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
			
			if (line.startsWith("$ cd "))
			{
				var dirName = line.substring("$ cd ".length());
				
				switch (dirName)
				{
				case "/":
					currentPath.clear();
					break;
				case "..":
					currentPath.remove(currentPath.size() - 1);
					break;
				default:
					currentPath.add(dirName);				
				}
			}
			else if (line.equals("$ ls"))
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
			}
			else
			{
				throw new IllegalArgumentException("Unrecognised line: " + line);
			}
		}
		
		return fileSystem;
	}
}
