using Common;
using FilesLib;
using System.Reflection;

var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), $"Files.input.txt");

var filesys = new DeviceFilesystem();
filesys.Parse(input);

filesys.CalculateOverallSizes();

Console.WriteLine(filesys.GetSumOfSmallFolders());