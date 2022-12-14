using Common;
using ParsingHellLib;
using System.Reflection;

var analyzer = new Analyzer();

var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), $"ParsingHell.input.txt");
analyzer.Parse(input);

int sum = analyzer.ComparePairs();
Console.WriteLine(sum);

int part2= analyzer.SortPackages();
Console.WriteLine(part2);
Console.ReadLine();


