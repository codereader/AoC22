using Common;
using CommonWPF;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Numerics;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using TreeHouseLib;

namespace TreeHouse
{
    class MainViewModel : ViewModelBase
    {
        private ForestRanger _ranger = new ForestRanger();

        public ObservableCollection<VisualTreePosition> VisualTreePositions { get; set; } = new ObservableCollection<VisualTreePosition>();


        public int XMax { get; set; }
        public int YMax { get; set; }


        public int VisibleTreeCount { get; set; }
        public int MaxVisibleScore { get; set; }

        public MainViewModel()
        {
            var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), $"TreeHouse.input.txt");

            _ranger.Parse( input );

            XMax = _ranger.XMax;
            YMax = _ranger.YMax;

            // part 1
            VisibleTreeCount = _ranger.GetVisibleTreeCount();

            // part 2
            MaxVisibleScore = _ranger.GetMaxScore();

            UpdateVisuals();
        }

        private void UpdateVisuals()
        {
            VisualTreePositions.Clear();
            for (int x = 0; x < XMax; x++)
            {
                for (int y = 0; y < YMax; y++)
                {
                    var pos = new Vector2(x, y);
                    var treePos = _ranger.GetTreePosition(pos);
                    var visualTreePos = new VisualTreePosition(pos, treePos);
                    VisualTreePositions.Add(visualTreePos);
                }
            }
        }
    }
}
