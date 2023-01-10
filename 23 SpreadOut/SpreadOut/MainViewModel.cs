using Common;
using CommonWPF;
using SpreadOutLib;
using System;
using System.Collections.Generic;
using System.DirectoryServices.ActiveDirectory;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace SpreadOut
{
    class MainViewModel : ViewModelBase
    {
        public ElfMover Movinator { get; set; } = new ElfMover();

        public int RoundsToDo
        {
            get => GetValue<int>();
            set => SetValue(value);
        }
        public int RoundsDone
        {
            get => GetValue<int>();
            set => SetValue(value);
        }

        public int GroundTiles
        {
            get => GetValue<int>();
            set => SetValue(value);
        }

        public MainViewModel()
        {
            var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), @"SpreadOut.input.txt");

            Movinator.Parse(input);
            Update();

            RoundsToDo = 10;

            RunRounds = new RelayCommand(CanRunRounds, DoRunRounds);
            RunRoundsSimulation = new RelayCommand(CanRunRoundsSimulation, DoRunRoundsSimulation);
            RunUntilFinished = new RelayCommand(CanRunUntilFinished, DoRunUntilFinished);
            RunSimulationUntilFinished = new RelayCommand(CanRunSimulationUntilFinished, DoRunSimulationUntilFinished);
            Reset = new RelayCommand(CanReset, DoReset);
        }

        public RelayCommand RunRounds { get; }
        public bool CanRunRounds()
        {
            return true;
        }
        public void DoRunRounds()
        {
            Movinator.DoRounds(RoundsToDo);
            Update();
        }

        public RelayCommand RunRoundsSimulation { get; }
        public bool CanRunRoundsSimulation()
        {
            return true;
        }
        public void DoRunRoundsSimulation()
        {
            Task.Run(RoundsSimulation);
        }
        private void RoundsSimulation()
        {
            for (int i = 0; i < RoundsToDo; i++)
            {
                Thread.Sleep(20);
                Movinator.DoRounds(1);
                App.Current.Dispatcher.Invoke(() =>
                {
                    Update();

                });
            }
        }

        public RelayCommand RunUntilFinished { get; }
        public bool CanRunUntilFinished()
        {
            return true;
        }
        public void DoRunUntilFinished()
        {
            Movinator.RunUntilFinished();
            Update();
        }

        public RelayCommand RunSimulationUntilFinished { get; }
        public bool CanRunSimulationUntilFinished()
        {
            return true;
        }
        public void DoRunSimulationUntilFinished()
        {
            Task.Run(SimulationUntilFinished);
        }
        private void SimulationUntilFinished()
        {
            while (!Movinator.Finished)
            {
                Thread.Sleep(20);
                Movinator.DoRounds(1);
                App.Current.Dispatcher.Invoke(() =>
                {
                    Update();
                });
            }
        }

        public RelayCommand Reset { get; }
        public bool CanReset()
        {
            return true;
        }
        public void DoReset()
        {
            Movinator.Reset();
            Update();
        }

        public void Update()
        {
            RoundsDone = Movinator.Round;
            GroundTiles = Movinator.CountEmptyGroundTiles();
            Movinator.UpdateVisuals();
        }
    }
}
