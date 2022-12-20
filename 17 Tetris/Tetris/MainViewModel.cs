using Common;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using TetrisLib;

namespace Tetris
{
    internal class MainViewModel
    {
        public CaveHandler Cavinator { get; set; } = new CaveHandler();

        public MainViewModel()
        {
            var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), @"Tetris.input.txt");
            Cavinator.Parse(input);

            Cavinator.Simulation(1000000000000);

        }
    }
}
