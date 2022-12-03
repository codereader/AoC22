using Common;
using Rucksack;
using System.Reflection;

var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), $"Rucksack.rucksack.txt");

var backpackCollection = new List<Backpack>();

foreach (var line in input)
{
    backpackCollection.Add(new Backpack(line));
}

// part1 
var sum = backpackCollection.Select(e => e.FindDuplicate()).Select(d => GetPriority(d)).Sum();
Console.WriteLine(sum);

// part2
var sum2 = 0;

for (int i = 0; i < backpackCollection.Count; i += 3)
{
    var r1 = backpackCollection[i];
    var r2 = backpackCollection[i+1];
    var r3 = backpackCollection[i+2];

    var commonItem = r1.Contents.Intersect(r2.Contents).Intersect(r3.Contents).First();

    sum2 += GetPriority(commonItem);
}

Console.WriteLine(sum2);
Console.ReadLine();


static int GetPriority(char currentchar)
{
    var priority = 0;

    if (char.IsLower(currentchar))
    {
        priority = currentchar - 'a' + 1;
    }
    else
    {
        priority = currentchar - 'A' + 27;
    }

    return priority;
}