using Common;
using System.Reflection;
using ValveLib;

var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), @"DistressSignal.input.txt");

var valveHandler = new ValveHandler();
valveHandler.Parse(input);
