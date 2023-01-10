using Common;
using CommonWPF;
using SpreadOutLib;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;
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
            Movinator.UpdateVisuals();

            GroundTiles = Movinator.CountEmptyGroundTiles();

            RoundsToDo = 10;


            RunRounds = new RelayCommand(CanRunRounds, DoRunRounds);
            RunUntilFinished = new RelayCommand(CanRunUntilFinished, DoRunUntilFinished);
        }

        public RelayCommand RunRounds { get; }
        public bool CanRunRounds()
        {
            return true;
        }
        public void DoRunRounds()
        {
            Movinator.DoRounds(RoundsToDo);
            GroundTiles = Movinator.CountEmptyGroundTiles();
            RoundsDone = Movinator.Round;
            Movinator.UpdateVisuals();
        }

        public RelayCommand RunUntilFinished { get; }
        public bool CanRunUntilFinished()
        {
            return true;
        }
        public void DoRunUntilFinished()
        {
            Movinator.RunUntilFinished();
            GroundTiles = Movinator.CountEmptyGroundTiles();
            RoundsDone = Movinator.Round;
            Movinator.UpdateVisuals();
        }

    }
}
