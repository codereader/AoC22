using Common;
using DropletLib;
using System.Reflection;

var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), @"Droplet.input.txt");

var dropletinator = new DropletHandler();

dropletinator.Parse(input);
