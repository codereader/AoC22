// See https://aka.ms/new-console-template for more information

using Common;
using Rucksack;
using System.Reflection;
using System.Text;

var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), $"Rucksack.rucksack.txt");

// Console.WriteLine(input.Count);

var backpackCollection = new List<Backpack>();

foreach (var line in input)
{
    var currentBackpack = new Backpack(line);
    backpackCollection.Add(currentBackpack);
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
    // Lowercase item types a through z have priorities 1 through 26.
    // Uppercase item types A through Z have priorities 27 through 52.
    var priority = 0;

    var asciiByte = Encoding.ASCII.GetBytes(currentchar.ToString()).First();

    // Console.Write(currentchar);
    //Console.WriteLine($", " + string.Join(" ", asciiByte));

    if (Char.IsLower(currentchar))
    {
        priority = asciiByte - 96;
    }
    else
    {
        priority = asciiByte - 38;
    }

    return priority;
}