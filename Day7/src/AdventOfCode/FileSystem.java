package AdventOfCode;

import java.util.List;
import java.util.function.Function;

public class FileSystem {
	
	private final Directory _root;
	
	public FileSystem()
	{
		_root = new Directory("/");
	}
	
	public void registerFile(List<String> currentPath, String fileName, Long size)
	{
		var directory = _root.ensureDirectory(currentPath);
		directory.addFile(fileName, size);
	}
	
	// Functor should return false to not go any deeper
	public void foreachDirectory(Function<Directory, Boolean> functor)
	{
		_root.foreachChildDirectory(functor);
	}

	public Directory getRootDirectory() {
		return _root;
	}
}
