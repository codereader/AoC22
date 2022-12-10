using Common;
using System.Reflection;

var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), $"Cathode.input.txt");

var x = 1;

var cycles = new List<int>();
cycles.Add(x);

foreach (var line in input)
{
    if (line == "noop")
    {
        cycles.Add(x);
    }
    else
    {
        cycles.Add(x);
        cycles.Add(x);
        var add = int.Parse(line.Substring(5));
        x += add;
    }
}

var strength = 0;

for (int i = 20; i < cycles.Count; i += 40)
{
    strength += i * cycles[i];
}

// part 1
Console.WriteLine(strength);

// part 2
var lines = new List<List<string>>();

var maxPos = 40;

for (int line = 0; line < (cycles.Count - 1) / maxPos; line++)
{
    var currentLine = new List<string>();
    for (int pos = 0; pos < maxPos; pos++)
    {
        var currentCycle = line * maxPos + pos + 1;
        var registerX = cycles[currentCycle];

        if (Math.Abs(pos - registerX) < 2)
        {
            currentLine.Add("#");
        }
        else
        {
            currentLine.Add(".");
        }
    }
    lines.Add(currentLine);
    Console.WriteLine(string.Join("", currentLine));
}

