using Common;
using CommonWPF;
using CratesLib;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Diagnostics;
using System.DirectoryServices.ActiveDirectory;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Input;

namespace Crates
{
    class MainViewModel : ViewModelBase
    {
        private bool _simulationRunning = false;
        private CancellationTokenSource _tokenSource;

        private CrateOperator _crateOperator = new CrateOperator();

        public VisualStackCollection VStackCollection { get; set; } = new VisualStackCollection();

        public ObservableCollection<VisualInstruction> VInstructions { get; set; } = new ObservableCollection<VisualInstruction>();

        public int CurrentInstruction
        {
            get => GetValue<int>();
            set => SetValue(value);
        }

        public string TopCrateSequence
        {
            get => GetValue<string>();
            set => SetValue(value);
        }

        public MainViewModel()
        {
            // Parse
            var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), $"Crates.input.txt");
            _crateOperator.Parse(input);
            ParseVisualInstructions();
            CurrentInstruction = -1;

            UpdateVisuals();

            RunAll = new RelayCommand(CanRunAll, DoRunAll);
            SimpleSimulation = new RelayCommand(CanSimpleSimulation, DoSimpleSimulation);
            RunAllAdvanced = new RelayCommand(CanRunAllAdvanced, DoRunAllAdvanced);
            AdvancedSimulation = new RelayCommand(CanAdvancedSimulation, DoAdvancedSimulation);
            Reset = new RelayCommand(CanReset, DoReset);
            StopSimulation = new RelayCommand(CanStopSimulation, DoStopSimulation);

        }


        public RelayCommand RunAll { get; }
        public bool CanRunAll()
        {
            return !_simulationRunning && CurrentInstruction == -1;
        }
        public void DoRunAll()
        {
            _crateOperator.PerformAllInstructions();
            CurrentInstruction = VInstructions.Count;
            UpdateVisuals();
        }

        public RelayCommand RunAllAdvanced { get; }
        public bool CanRunAllAdvanced()
        {
            return !_simulationRunning && CurrentInstruction == -1;
        }
        public void DoRunAllAdvanced()
        {
            _crateOperator.PerformAllInstructionsAdvanced();
            CurrentInstruction = VInstructions.Count;
            UpdateVisuals();
        }


        public RelayCommand SimpleSimulation { get; }
        public bool CanSimpleSimulation()
        {
            return !_simulationRunning && CurrentInstruction < VInstructions.Count - 1;
        }
        public void DoSimpleSimulation()
        {
            StartSimulation(false);
        }

        public RelayCommand AdvancedSimulation { get; }
        public bool CanAdvancedSimulation()
        {
            return !_simulationRunning && CurrentInstruction < VInstructions.Count - 1;
        }
        public void DoAdvancedSimulation()
        {
            StartSimulation(true);
        }

        public async void StartSimulation(bool advanced)
        {
            _tokenSource = new CancellationTokenSource();

            _simulationRunning = true;

            RunAll.RaiseCanExecuteChanged();
            SimpleSimulation.RaiseCanExecuteChanged();
            RunAllAdvanced.RaiseCanExecuteChanged();
            AdvancedSimulation.RaiseCanExecuteChanged();
            Reset.RaiseCanExecuteChanged();
            StopSimulation.RaiseCanExecuteChanged();

            await Task.Run(() => RunSimulation(advanced, _tokenSource.Token));

            _simulationRunning = false;

            RunAll.RaiseCanExecuteChanged();
            SimpleSimulation.RaiseCanExecuteChanged();
            RunAllAdvanced.RaiseCanExecuteChanged();
            AdvancedSimulation.RaiseCanExecuteChanged();
            Reset.RaiseCanExecuteChanged();
            StopSimulation.RaiseCanExecuteChanged();

            _tokenSource.Dispose();
        }

        private void RunSimulation(bool advanced, CancellationToken token)
        {
            var currentInstruction = CurrentInstruction;
            try
            {
                while (true)
                {
                    currentInstruction++;
                    if (currentInstruction >= VInstructions.Count)
                    {
                        break;
                    }
                    if (advanced)
                    {
                        _crateOperator.PerformAdvancedInstructionId(currentInstruction);
                    }
                    else
                    {
                        _crateOperator.PerformInstructionId(currentInstruction);
                    }
                    App.Current.Dispatcher.Invoke(() =>
                    {
                        UpdateVisuals();
                        CurrentInstruction = currentInstruction;
                    });

                    token.ThrowIfCancellationRequested();

                    Thread.Sleep(50);
                }
            }
            catch (OperationCanceledException)
            { }
        }

        public RelayCommand StopSimulation { get; }
        public bool CanStopSimulation(object obj)
        {
            return _simulationRunning;
        }
        public void DoStopSimulation(object obj)
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
            _crateOperator.Reset();
            CurrentInstruction = -1;

            RunAll.RaiseCanExecuteChanged();
            SimpleSimulation.RaiseCanExecuteChanged();
            RunAllAdvanced.RaiseCanExecuteChanged();
            AdvancedSimulation.RaiseCanExecuteChanged();
            Reset.RaiseCanExecuteChanged();

            UpdateVisuals();
        }

        private void ParseVisualInstructions()
        {
            foreach (var instruction in _crateOperator.Instructions)
            {
                VInstructions.Add(new VisualInstruction(instruction));
            }
        }

        private void UpdateVisuals()
        {
            VStackCollection.UpdateVisuals(_crateOperator.StackColl);
            TopCrateSequence = _crateOperator.TopCrateSequence;
        }
    }
}
