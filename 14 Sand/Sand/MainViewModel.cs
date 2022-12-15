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

            FillToAbyss = new RelayCommand(CanFillToAbyss, DoFillToAbyss);
            FillToFloor = new RelayCommand(CanFillToFloor, DoFillToFloor);
        }

        public RelayCommand FillToAbyss { get; }
        public bool CanFillToAbyss()
        {
            return true;
        }
        public void DoFillToAbyss()
        {
            Cavinator.FillWithSandToAbyss();
        }

        public RelayCommand FillToFloor { get; }
        public bool CanFillToFloor()
        {
            return true;
        }
        public void DoFillToFloor()
        {
            Cavinator.FillWithSandToFloor();
        }

    }
}
