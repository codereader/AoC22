﻿using Common;
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
using System.Windows.Input;

namespace Crates
{
    class MainViewModel : ViewModelBase
    {
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
        }


        public ICommand RunAll { get; }
        public bool CanRunAll()
        {
            return true;
        }
        public void DoRunAll()
        {
            _crateOperator.PerformAllInstructions();
            UpdateVisuals();
        }

        public ICommand SimpleSimulation { get; }
        public bool CanSimpleSimulation()
        {
            return true;
        }
        public void DoSimpleSimulation()
        {
            StartSimpleSimulation();
        }
        public async Task StartSimpleSimulation()
        {
            await Task.Run(() => RunSimpleSimulation());
        }

        private void RunSimpleSimulation()
        {
            var currentInstruction = 0;

            while (true)
            {
                if (currentInstruction >= VInstructions.Count)
                {
                    break;
                }
                _crateOperator.PerformInstructionId(currentInstruction);
                 App.Current.Dispatcher.Invoke(() =>
                {
                    UpdateVisuals();
                    CurrentInstruction = currentInstruction;
                });

                Task.Delay(50).Wait();
                currentInstruction++;

            }
        }



        public ICommand RunAllAdvanced { get; }
        public bool CanRunAllAdvanced()
        {
            return true;
        }
        public void DoRunAllAdvanced()
        {
            _crateOperator.PerformAllInstructionsAdvanced();
            UpdateVisuals();
        }

        public ICommand AdvancedSimulation { get; }
        public bool CanAdvancedSimulation()
        {
            return true;
        }
        public void DoAdvancedSimulation()
        {
            StartAdvancedSimulation();
        }
        public async Task StartAdvancedSimulation()
        {
            await Task.Run(() => RunAdvancedSimulation());
        }

        private void RunAdvancedSimulation()
        {
            var currentInstruction = 0;

            while (true)
            {
                if (currentInstruction >= VInstructions.Count)
                {
                    break;
                }
                _crateOperator.PerformAdvancedInstructionId(currentInstruction);
                App.Current.Dispatcher.Invoke(() =>
                {
                    UpdateVisuals();
                    CurrentInstruction = currentInstruction;
                });

                Task.Delay(50).Wait();
                currentInstruction++;

            }
        }







        public ICommand Reset { get; }
        public bool CanReset()
        {
            return true;
        }
        public void DoReset()
        {
            _crateOperator.Reset();
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
