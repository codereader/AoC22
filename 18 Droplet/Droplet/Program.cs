using Common;
using DropletLib;
using System.Diagnostics;
using System.Reflection;

var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), @"Droplet.input.txt");

var dropletinator = new DropletHandler();

dropletinator.Parse(input);

var stopWatch = new Stopwatch();

stopWatch.Start();

// part 1
Console.WriteLine("Surface area:");
Console.WriteLine(dropletinator.GetSurfaceArea());

// part 2
Console.WriteLine("Outside surface area");
Console.WriteLine(dropletinator.GetOutsideSurfaceArea());

stopWatch.Stop();
Console.WriteLine($"Time: {stopWatch.ElapsedMilliseconds}");
