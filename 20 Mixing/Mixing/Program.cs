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

Console.WriteLine(x+y+z);


