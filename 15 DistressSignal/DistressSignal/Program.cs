using Common;
using SignalLib;
using System.Reflection;

var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), @"DistressSignal.input.txt");

var signalHandler = new SignalHandler();
signalHandler.Parse(input);

Console.WriteLine("method 1");
Console.WriteLine(signalHandler.CountNonBeacons(2000000));
Console.WriteLine("method 2");
Console.WriteLine(signalHandler.CountNonBeacons2(2000000));

