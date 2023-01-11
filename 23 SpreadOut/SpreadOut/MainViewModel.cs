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
        private CancellationTokenSource? _tokenSource;
        private bool _simulationRunning;

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
            _simulationRunning = false;

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
            return !_simulationRunning;
        }
        public void DoRunRounds()
        {
            Movinator.DoRounds(RoundsToDo);
            Update();
        }

        public RelayCommand RunRoundsSimulation { get; }
        public bool CanRunRoundsSimulation()
        {
            return !_simulationRunning;
        }
        public async void DoRunRoundsSimulation()
        {
            _tokenSource = new CancellationTokenSource();
            _simulationRunning = true;

            CanExecuteChanged();

            await Task.Run(() => RoundsSimulation(_tokenSource.Token));
            _simulationRunning = false;
            CanExecuteChanged();

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
                    }, System.Windows.Threading.DispatcherPriority.Background);
                    token.ThrowIfCancellationRequested();
                }
            }
            catch (OperationCanceledException) { }
        }

        public RelayCommand RunUntilFinished { get; }
        public bool CanRunUntilFinished()
        {
            return !_simulationRunning;
        }
        public void DoRunUntilFinished()
        {
            Movinator.RunUntilFinished();
            Update();
        }

        public RelayCommand RunSimulationUntilFinished { get; }
        public bool CanRunSimulationUntilFinished()
        {
            return !_simulationRunning;
        }
        public async void DoRunSimulationUntilFinished()
        {
            _simulationRunning = true;
            CanExecuteChanged();

            await Task.Run(StartSimulationUntilFinished);

            _simulationRunning = false;
            CanExecuteChanged();
        }

        private void StartSimulationUntilFinished()
        {
            _tokenSource = new CancellationTokenSource();

            SimulationUntilFinished(_tokenSource.Token);

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
                    }, System.Windows.Threading.DispatcherPriority.Background);
                    
                    token.ThrowIfCancellationRequested();
                }
            }
            catch (OperationCanceledException) { }
        }

        public RelayCommand Stop { get; }
        public bool CanStop()
        {
            return _simulationRunning;
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
            return !_simulationRunning;
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

        private void CanExecuteChanged()
        {
            RunRounds.RaiseCanExecuteChanged();
            RunRoundsSimulation.RaiseCanExecuteChanged();
            RunUntilFinished.RaiseCanExecuteChanged();
            RunSimulationUntilFinished.RaiseCanExecuteChanged();
            Stop.RaiseCanExecuteChanged();
            Reset.RaiseCanExecuteChanged();
        }

    }
}
