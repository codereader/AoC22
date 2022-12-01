// See https://aka.ms/new-console-template for more information

using System.Reflection;


using var stream = Assembly.GetExecutingAssembly().GetManifestResourceStream($"Calories.Calories.txt");
using var reader = new StreamReader(stream);

string line;
var txtFile = new List<string>();
while ((line = reader.ReadLine()) != null)
{
    txtFile.Add(line);
}

var calories = new Dictionary<int, List<int>>();

var elfCounter = 0;
var currentCalories = new List<int>();
calories[elfCounter] = currentCalories;

// Parse
foreach (var item in txtFile)
{
    if (string.IsNullOrWhiteSpace(item))
    {
        // next elf
        elfCounter++;
        currentCalories = new List<int>();
        calories[elfCounter] = currentCalories;
    }

    else
    {
        // add calories to current elf
        currentCalories.Add(int.Parse(item));
    }

}

var sums = new Dictionary<int, int>();


for (int i = 0; i < calories.Count; i++)
{
   // Console.WriteLine($"Elf "+i+":");
    // Console.WriteLine(string.Join(", ", calories[i]));

    sums[i] = calories[i].Sum();
}

// part 1
Console.WriteLine($"Max calories: " + sums.Values.Max());

// part 2
var resortedSums = sums.Values.OrderByDescending(v => v);
var sumOf3 = resortedSums.Take(3).Sum();
Console.WriteLine($"Max calories of 3 elves: " + sumOf3);



Console.ReadLine();
