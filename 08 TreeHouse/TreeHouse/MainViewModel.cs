using Common;
using CommonWPF;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using TreeHouseLib;

namespace TreeHouse
{
    class MainViewModel : ViewModelBase
    {
        private ForestRanger _ranger = new ForestRanger();

        public VisualForest Forest = new VisualForest();

        public int XMax
        {
            get => GetValue<int>();
            set => SetValue(value);
        }

        public int YMax
        {
            get => GetValue<int>();
            set => SetValue(value);
        }



        public int VisibleTreeCount
        {
            get => GetValue<int>();
            set => SetValue(value);
        }
        public int MaxVisibleScore
        {
            get => GetValue<int>();
            set => SetValue(value);
        }



        public MainViewModel()
        {
            var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), $"TreeHouse.input.txt");

            _ranger.Parse( input );

            XMax = _ranger.XMax;
            YMax = _ranger.YMax;

            // part 1
            _ranger.CalculateMaxHeights();
            _ranger.DetermineVisibilities();
            VisibleTreeCount = _ranger.GetVisibleTreeCount();

            // part 2
            _ranger.DetermineVisibleTreeCounts();
            MaxVisibleScore = _ranger.GetMaxScore();


            UpdateVisuals();



        }

        private void UpdateVisuals()
        {
            for (int x = 0; x < XMax; x++)
            {
                for (int y = 0; y < YMax; y++)
                {

                }
            }
        }
    }
}
