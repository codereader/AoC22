using Common;
using CommonWPF;
using HillLib;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;

namespace Hill
{
    class MainViewModel : ViewModelBase
    {
        public Geographer Geo { get; set; } = new Geographer();

        public int ShortestDist
        {
            get => GetValue<int>();
            set => SetValue(value);
        }

        public MainViewModel()
        {
            var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), @"Hill.input.txt");

            Geo.Parse(input);
            Geo.FindConnections();
            ShortestDist = Geo.ShortestDistanceToDestination();

        }
    }
}
