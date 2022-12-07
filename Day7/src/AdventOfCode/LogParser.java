package AdventOfCode;

import java.util.ArrayList;
import java.util.List;

public class LogParser {
	
	public static FileSystem ProcessLog(List<String> lines)
	{
		var fileSystem = new FileSystem();
		var currentPath = new ArrayList<String>();
		
		var currentLine = 0;
		
		while (currentLine < lines.size())
		{
			if (lines.get(currentLine).equals("$ cd /"))
			{
				currentPath = new ArrayList<String>();
				++currentLine;
				continue;
			}
			
			if (lines.get(currentLine).startsWith("$ cd "))
			{
				var dirName = lines.get(currentLine).substring("$ cd ".length());
				
				if (dirName.equals(".."))
				{
					currentPath.remove(currentPath.size() - 1);
				}
				else
				{
					currentPath.add(dirName);
				}
				++currentLine;
				continue;
			}
				
			if (lines.get(currentLine).equals("$ ls"))
			{
				// Proceed until next $ command is found
				++currentLine;
				
				while (currentLine < lines.size() && !lines.get(currentLine).startsWith("$"))
				{
					if (lines.get(currentLine).startsWith("dir "))
					{
						++currentLine;
						continue;
					}
					
					var parts = lines.get(currentLine).split(" ");
					var size = Long.parseLong(parts[0]);
					var fileName = parts[1];
					
					fileSystem.registerFile((List<String>)currentPath.clone(), fileName, size);
					++currentLine;
				}
				
				continue;
			}
			
			throw new IllegalArgumentException("Unrecognised line: " + lines.get(currentLine));
		}
		
		return fileSystem;
	}
}
