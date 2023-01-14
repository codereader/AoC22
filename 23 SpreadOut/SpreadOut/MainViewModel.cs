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
    class MainViewModel : SimulationViewModel
    {
        private int _startRound = 0;
        public ElfMover Movinator { get; set; } = new ElfMover();

        public bool RunRoundsNumber
        {
            get => GetValue<bool>();
            set => SetValue(value);
        }

        public int RoundsToDo
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

            Elf.MaxRoundsSinceLastMove = Constants.MaxRoundsSinceLastMove;
            Movinator.Parse(input);
            Update();
            RoundsToDo = 10;
            RunRoundsNumber = false;

            ShowResult = new RelayCommand(CanShowResult, DoShowResult);
        }

        public RelayCommand ShowResult { get; }
        public bool CanShowResult()
        {
            return !SimulationRunning;
        }
        public void DoShowResult()
        {
            if (RunRoundsNumber)
            {
                Movinator.DoRounds(RoundsToDo);
            }
            else 
            {
                Movinator.RunUntilFinished();   
            }
            Update();
        }

        public override void OnSimulationStart()
        { 
            base.OnSimulationStart();
            _startRound = Movinator.Round;
        }

        public override void OnSimulationStop()
        {
            base.OnSimulationStop();
            SimulationFinished = false;
        }

        public override void DoRound()
        {
            base.DoRound();
            Movinator.DoRounds(1);

            if (RunRoundsNumber)
            {
                if (Movinator.Round >= _startRound + RoundsToDo)
                {
                    SimulationFinished = true;
                }
            }
            else if (Movinator.Finished)
            {
                SimulationFinished = true;
            }

            App.Current.Dispatcher.Invoke(() =>
            {
                Update();
            }, System.Windows.Threading.DispatcherPriority.Background);

        }

        public override void DoReset()
        {
            base.DoReset();
            _startRound = 0;
            SimulationFinished = false;
            Movinator.Reset();
            Update();
        }


        public override void Update()
        {
            base.Update();
            GroundTiles = Movinator.CountEmptyGroundTiles();
            RoundsDone = Movinator.Round;
            Movinator.UpdateVisuals();
        }

        public override void CanExecuteChanged()
        {
            base.CanExecuteChanged();
            ShowResult.RaiseCanExecuteChanged();
        }

    }
}
