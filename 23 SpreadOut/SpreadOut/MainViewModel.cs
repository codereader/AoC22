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
        private CancellationTokenSource _tokenSource;

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
            Stop = new RelayCommand(CanStop, DoStop);
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
        public async void DoRunRoundsSimulation()
        {
            _tokenSource = new CancellationTokenSource();

            await Task.Run(() => RoundsSimulation(_tokenSource.Token));

            _tokenSource.Dispose();
            _tokenSource = null;
        }
        private void RoundsSimulation(CancellationToken token)
        {
            try
            {
                for (int i = 0; i < RoundsToDo; i++)
                {
                    Thread.Sleep(20);
                    Movinator.DoRounds(1);
                    App.Current.Dispatcher.Invoke(() =>
                    {
                        Update();
                    });
                    token.ThrowIfCancellationRequested();
                }
            }
            catch (OperationCanceledException) { }
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
        public async void DoRunSimulationUntilFinished()
        {
            _tokenSource = new CancellationTokenSource();

            await Task.Run(() => SimulationUntilFinished(_tokenSource.Token));

            _tokenSource.Dispose();
            _tokenSource = null;
        }
        private void SimulationUntilFinished(CancellationToken token)
        {
            try
            {
                while (!Movinator.Finished)
                {
                    Thread.Sleep(20);
                    Movinator.DoRounds(1);
                    App.Current.Dispatcher.Invoke(() =>
                    {
                        Update();
                    });
                    token.ThrowIfCancellationRequested();
                }
            }
            catch (OperationCanceledException) { }

        }

        public RelayCommand Stop { get; }
        public bool CanStop()
        {
            return true;
        }
        public void DoStop()
        {
            if (_tokenSource != null)
            {
                _tokenSource.Cancel();
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
