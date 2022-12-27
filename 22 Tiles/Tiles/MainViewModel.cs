using Common;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using TilesLib;

namespace Tiles
{
    internal class MainViewModel
    {
        public MapNavigator Navigator { get; set; } = new MapNavigator();

        public MainViewModel()
        {
            var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), @"Tiles.input.txt");

            Navigator.Parse(input);

            Navigator.Simulation();

        }
    }
}
