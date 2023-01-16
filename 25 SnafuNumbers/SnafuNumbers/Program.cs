using Common;
using SnafuLib;
using System.Reflection;

var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), @"SnafuNumbers.input.txt");

var snafu = new SnafuHandler();

snafu.Parse(input);
