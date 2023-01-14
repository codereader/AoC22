using System;
using System.Collections.Generic;
using System.DirectoryServices.ActiveDirectory;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace CommonWPF
{
    public class SimulationViewModel : ViewModelBase
    {
        private CancellationTokenSource? _tokenSource;

        public bool SimulationRunning { get; private set; }

        public int RoundsDone
        {
            get => GetValue<int>();
            set => SetValue(value);
        }

        public bool SimulationFinished { get; set; } = false;

        public SimulationViewModel()
        {
            RoundsDone = 0;

            RunSimulation = new RelayCommand(CanRunSimulation, DoRunSimulation);
            Stop = new RelayCommand(CanStop, DoStop);
            Reset = new RelayCommand(CanReset, DoReset);
        }

        public RelayCommand RunSimulation { get; }
        public bool CanRunSimulation()
        {
            return !SimulationRunning;
        }
        public async void DoRunSimulation()
        {
            OnSimulationStart();

            await Task.Run(StartSimulation);

            OnSimulationStop();
        }

        public virtual void OnSimulationStart()
        {
            SimulationRunning = true;
            CanExecuteChanged();
        }
        public virtual void OnSimulationStop()
        {
            SimulationRunning = false;
            CanExecuteChanged();
        }

        private void StartSimulation()
        {
            _tokenSource = new CancellationTokenSource();

            Simulation(_tokenSource.Token);

            _tokenSource.Dispose();
            _tokenSource = null;
        }

        public void Simulation(CancellationToken token)
        {
            try
            {
                while (!SimulationFinished)
                {
                    Thread.Sleep(20);
                    DoRound();

                    token.ThrowIfCancellationRequested();
                }
            }
            catch (OperationCanceledException) { }
        }

        public virtual void DoRound()
        {}

        public virtual void Update()
        { }

        public RelayCommand Stop { get; }
        public bool CanStop()
        {
            return SimulationRunning;
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
            return !SimulationRunning;
        }
        public virtual void DoReset()
        {
            RoundsDone = 0;
        }

        public virtual void CanExecuteChanged()
        {
            RunSimulation.RaiseCanExecuteChanged();
            Stop.RaiseCanExecuteChanged();
            Reset.RaiseCanExecuteChanged();
        }

    }
}
