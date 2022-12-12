using Common;
using CommonWPF;
using HillLib;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;

namespace Hill
{
    class MainViewModel : ViewModelBase
    {
        public Geographer Geo { get; set; } = new Geographer();

        public ObservableCollection<VisualLocation> VisualLocations { get; set; } = new ObservableCollection<VisualLocation>();

        public int ShortestDist
        {
            get => GetValue<int>();
            set => SetValue(value);
        }

        public MainViewModel()
        {
            var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), @"Hill.input.txt");

            Geo.Parse(input);

            CreateVisuals();
            //Geo.FindConnections();
            //ShortestDist = Geo.ShortestDistanceToDestination();

        }

        private void CreateVisuals()
        {
            foreach (var location in Geo.Heightmap)
            {
                VisualLocations.Add(new VisualLocation(location.Value));
            }
        }
    }
}
