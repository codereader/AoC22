using Common;
using MonkeyLib;
using System.Reflection;

var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), $"Monkeys.input.txt");

var instructions = new Instructions();

instructions.Parse(input);

instructions.DoRounds(20);

Console.WriteLine(instructions.GetProduct());


