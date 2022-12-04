using Cleanup;
using Common;
using System.Reflection;

var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), $"Cleanup.input.txt");

var pairCollection = new List<Pair>();

// 77-81,78-97
foreach (var line in input)
{
    pairCollection.Add(new Pair(line));
}

// part 1
var count = pairCollection.Count(p => p.RangesContained());

Console.WriteLine(count);

// part 2
var count2 = pairCollection.Count(p => p.RangesOverlap());

Console.WriteLine(count2);