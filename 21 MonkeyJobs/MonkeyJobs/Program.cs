using Common;
using MonkeyLib;
using System.Reflection;

var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), @"MonkeyJobs.input.txt");

var master = new MonkeyMaster();

master.Parse(input);

Console.WriteLine(master.DoMonkeyJobs());
