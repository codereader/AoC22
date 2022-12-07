package AdventOfCode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Directory {
	
	private Long _size;
	private String _name;
	private List<Directory> _children;
	
	public Directory(String name)
	{
		_size = 0L;
		_name = name;
		_children = new ArrayList<Directory>();
	}	
	
	public Long getSizeIncludingChildren()
	{
		return _size + _children.stream()
				.mapToLong(child -> child.getSizeIncludingChildren())
				.reduce(Long::sum).orElseGet(() -> 0);
	}
	
	public String getName()
	{
		return _name;
	}
	
	public Directory addDirectory(String name)
	{
		var dir = new Directory(name);
		_children.add(dir);
		
		return dir;
	}
	
	public void addFile(String name, Long size)
	{
		_size += size;
	}

	public void foreachChildDirectory(Function<Directory, Boolean> functor)
	{
		for (var dir : _children)
		{
			if (functor.apply(dir))
			{
				dir.foreachChildDirectory(functor);
			}
		}
	}

	public Directory ensureDirectory(List<String> path)
	{
		if (path.size() == 0)
		{
			return this;
		}
		
		// Split off the first part of the path, enter recursion
		var childName = path.get(0);
		path.remove(0);
		
		return findOrCreateChildDirectory(childName).ensureDirectory(path);
	}
	
	private Directory findOrCreateChildDirectory(String name)
	{
		for (var child : _children)
		{
			if (child.getName().equals(name))
			{
				return child;
			}
		}
		
		return addDirectory(name);
	}
}
