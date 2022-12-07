package AdventOfCode;

import java.util.ArrayList;
import java.util.List;

public class LogWalker {
	
	private int _currentLine;
	private final List<String> _lines;
	private ArrayList<String> _currentPath;
	private FileSystem _fileSystem;
	
	public LogWalker(List<String> lines)
	{
		_lines = lines;
		_fileSystem = new FileSystem();
	}
	
	public FileSystem getFileSystem()
	{
		return _fileSystem;
	}
	
	public void ProcessLines()
	{
		switchToRootDir();
		
		_currentLine = 0;
		
		while (_currentLine < _lines.size())
		{
			if (_lines.get(_currentLine).equals("$ cd /"))
			{
				switchToRootDir();
				++_currentLine;
				continue;
			}
			
			if (_lines.get(_currentLine).startsWith("$ cd "))
			{
				var dirName = _lines.get(_currentLine).substring("$ cd ".length());
				
				if (dirName.equals(".."))
				{
					_currentPath.remove(_currentPath.size() - 1);
				}
				else
				{
					_currentPath.add(dirName);
				}
				++_currentLine;
				continue;
			}
				
			if (_lines.get(_currentLine).equals("$ ls"))
			{
				// Proceed until next $ command is found
				++_currentLine;
				
				while (_currentLine < _lines.size() && !_lines.get(_currentLine).startsWith("$"))
				{
					if (_lines.get(_currentLine).startsWith("dir "))
					{
						//_fileSystem.registerDirectory(_currentPath, _lines.get(_currentLine).substring("dir ".length()));
						++_currentLine;
						continue;
					}
					
					var parts = _lines.get(_currentLine).split(" ");
					var size = Long.parseLong(parts[0]);
					var fileName = parts[1];
					
					_fileSystem.registerFile((List<String>)_currentPath.clone(), fileName, size);
					++_currentLine;
				}
				
				continue;
			}
			
			throw new IllegalArgumentException("Unrecognised line: " + _lines.get(_currentLine));
		}
	}
	
	private void switchToRootDir()
	{
		_currentPath = new ArrayList<String>();
	}
}
