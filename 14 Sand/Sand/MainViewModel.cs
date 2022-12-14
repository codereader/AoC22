using Common;
using CommonWPF;
using SandLib;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;

namespace Sand
{
    internal class MainViewModel : ViewModelBase
    {
        public CaveHandler Cavinator { get; } = new CaveHandler();

        public MainViewModel()
        {
            var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), @"Sand.input.txt");
            Cavinator.Parse(input);
        }
    }
}
