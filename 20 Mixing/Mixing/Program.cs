using Common;
using MixingLib;
using System.Reflection;

var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), @"Mixing.input.txt");

var mixer = new Mixer();

mixer.Parse(input);

// part 1
mixer.Mix();

var x = mixer.GetNumAtPosition(1000);
var y = mixer.GetNumAtPosition(2000);
var z = mixer.GetNumAtPosition(3000);

Console.WriteLine("Part 1");
Console.WriteLine(x + y + z);

// part 2
mixer.ResetChain();

mixer.MultiplyNumbers(811589153);
mixer.Mix();

var x2 = mixer.GetNumAtPosition(1000);
var y2 = mixer.GetNumAtPosition(2000);
var z2 = mixer.GetNumAtPosition(3000);

Console.WriteLine("Part 2");
Console.WriteLine(x2 + y2 + z2);


