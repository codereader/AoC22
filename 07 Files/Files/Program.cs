using Common;
using FilesLib;
using System.Reflection;

var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), $"Files.input.txt");

var filesys = new DeviceFilesystem();
filesys.Parse(input);

filesys.CalculateOverallSizes();

// part 1
Console.WriteLine(filesys.GetSumOfSmallFolders());

// part2
Console.WriteLine(filesys.DetermineSizeOfDirToDelete());

Console.ReadLine();