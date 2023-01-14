using BlizzardLib;
using Common;
using CommonWPF;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;

namespace Blizzards
{
    internal class MainViewModel : SimulationViewModel
    {
        BlizzardHandler Blizzardinator = new BlizzardHandler();

        public MainViewModel()
        {
            var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), @"Blizzards.input.txt");

            Blizzardinator.Parse(input);

        }


    }
}
