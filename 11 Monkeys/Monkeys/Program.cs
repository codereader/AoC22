using Common;
using MonkeyLib;
using System.Reflection;

var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), $"Monkeys.input.txt");

var instructions = new Instructions();

instructions.Parse(input);

// part 1
// instructions.DoRounds1(20);
//Console.WriteLine(instructions.GetProduct());

instructions.DoRounds2(10000);
Console.WriteLine(instructions.GetProduct());


