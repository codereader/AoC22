using Common;
using GeodeLib;
using System.Reflection;

var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), @"Geode.input.txt");

var robotinator = new RobotHandler();

robotinator.Parse(input);

Console.WriteLine(robotinator.RunSimulation());

