using Common;
using GeodeLib;
using System.Reflection;

var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), @"Geode.input.txt");

var robotinator = new RobotHandler();

robotinator.Parse(input);

// part 1
//Console.WriteLine(robotinator.RunSimulation1());

// part 2
Console.WriteLine(robotinator.RunSimulation2());

