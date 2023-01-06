using Common;
using System.Reflection;
using ValveLib;

var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), @"Valves.input.txt");

var valveHandler = new ValveHandler();
valveHandler.Parse(input);

// part 1
Console.WriteLine("Maximum pressure:");
Console.WriteLine(valveHandler.DetermineMaxPressure());

// part 2
Console.WriteLine("Maximum pressure part 2");
Console.WriteLine(valveHandler.DetermineMaxPressure2());

