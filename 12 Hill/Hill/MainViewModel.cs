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

        public int ShortestPathStartToEnd
        {
            get => GetValue<int>();
            set => SetValue(value);
        }
        public int ShortestPathBestPosToEnd
        {
            get => GetValue<int>();
            set => SetValue(value);
        }


        public MainViewModel()
        {
            var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), @"Hill.input.txt");

            Geo.Parse(input);

            CreateVisuals();

            FindShortestPathStartToEnd = new RelayCommand(CanFindShortestPathStartToEnd, DoFindShortestPathStartToEnd);
            FindShortestPathBestPosToEnd = new RelayCommand(CanFindShortestPathBestPosToEnd, DoFindShortestPathBestPosToEnd);
        }

        public RelayCommand FindShortestPathStartToEnd { get; }
        public bool CanFindShortestPathStartToEnd()
        {
            return true;
        }
        public void DoFindShortestPathStartToEnd()
        {
            ShortestPathStartToEnd = Geo.FindShortestPathfromStartToEnd();
            CreateVisuals();
        }

        public RelayCommand FindShortestPathBestPosToEnd { get; }
        public bool CanFindShortestPathBestPosToEnd()
        {
            return true;
        }
        public void DoFindShortestPathBestPosToEnd()
        {
            ShortestPathBestPosToEnd = Geo.FindShortestPathBestPosToEnd();
            CreateVisuals();
        }


        private void CreateVisuals()
        {
            VisualLocations.Clear();
            foreach (var location in Geo.Heightmap)
            {
                VisualLocations.Add(new VisualLocation(location.Value));
            }
        }
    }
}
