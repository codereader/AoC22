using Common;
using ParsingHellLib;
using System.Reflection;

var analyzer = new Analyzer();

var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), $"ParsingHell.input.txt");
analyzer.Parse(input);

int sum = analyzer.Compare();
Console.WriteLine(sum);
Console.ReadLine();


