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

Console.WriteLine(pairCollection.Count);

// part 1
var count = pairCollection.Count(p => (p.First.Min <= p.Second.Min && p.First.Max >= p.Second.Max)
        || (p.First.Min >= p.Second.Min && p.First.Max <= p.Second.Max));

Console.WriteLine(count);

// part 2
var count2 = pairCollection.Count(p => (p.First.Range.Intersect(p.Second.Range)).Any());

Console.WriteLine(count2);