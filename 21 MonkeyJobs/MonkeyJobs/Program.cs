using Common;
using MonkeyLib;
using System.Reflection;

var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), @"MonkeyJobs.input.txt");

var master = new MonkeyMaster();

master.Parse(input);

// part 1
//Console.WriteLine(master.DoMonkeyJobs());

// part 2
Console.WriteLine(master.FindNumberForEquality());

