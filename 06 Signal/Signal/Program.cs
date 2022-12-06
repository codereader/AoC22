using Common;
using System.Net.Http.Headers;
using System.Reflection;

var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), $"Signal.input.txt");
var testStr = input[0].ToList();

var startPos = 0;
var packageStart = -1;
var messageStart = -1;

while (true)
{
    // 4 different characters
    if (testStr.GetRange(startPos, 4).Distinct().Count() == 4)
    {
        if (packageStart == -1)
        {
            packageStart = startPos + 4;
        }
    }

    // 14 different characters
    if (testStr.GetRange(startPos, 14).Distinct().Count() == 14)
    {
        messageStart = startPos + 14;
        break;
    }

    startPos++;
}

Console.WriteLine("Start of package:");
Console.WriteLine(packageStart);

Console.WriteLine("Start of message:");
Console.WriteLine(messageStart);

Console.ReadLine();