using BlizzardLib;
using Common;
using CommonWPF;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Blizzards
{
    internal class MainViewModel : SimulationViewModel
    {
        BlizzardHandler Blizzardinator = new BlizzardHandler();

        public ObservableCollection<VisualLocation> Valley { get; set; } = new ObservableCollection<VisualLocation>();

        public int RoundsUntilFinished
        {
            get => GetValue<int>();
            set=> SetValue(value);
        }

        public bool RunTwice
        {
            get => GetValue<bool>();
            set => SetValue(value);
        }


        public MainViewModel()
        {
            var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), @"Blizzards.input.txt");

            Blizzardinator.Parse(input);
            RunTwice = false;
            ResetValley();
        }

        public override void OnSimulationStart()
        {
            base.OnSimulationStart();
            Blizzardinator.CalculatePathStartToEnd();

            if (RunTwice) 
            {
                Blizzardinator.CalculatePathEndToStart();
                Blizzardinator.CalculatePathStartToEnd();
            }

            RoundsUntilFinished = Blizzardinator.MaxRound;
        }

        public override void DoRound()
        {
            base.DoRound();

            Blizzardinator.DoRound();
            RoundsDone = Blizzardinator.Round;

            if (RoundsDone == Blizzardinator.MaxRound)
            {
                SimulationFinished = true;
            }

            App.Current.Dispatcher.Invoke(() =>
            {
                UpdateVisuals();
            }, System.Windows.Threading.DispatcherPriority.Background);

            Thread.Sleep(10);
        }

        public void UpdateVisuals()
        {
            foreach (var location in Valley) 
            { 
                location.UpdateVisuals();
            }
        }

        public override void DoReset()
        {
            base.DoReset();

            ResetValley();
        }

        private void ResetValley()
        {
            Blizzardinator.ResetVisuals();
            Valley.Clear();
            foreach (var location in Blizzardinator.ValleyGrid)
            {
                Valley.Add(new VisualLocation(location));
            }
        }


    }
}
