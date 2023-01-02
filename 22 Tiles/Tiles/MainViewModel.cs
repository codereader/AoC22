using Common;
using CommonWPF;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Numerics;
using System.Printing;
using System.Reflection;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading;
using System.Threading.Tasks;
using TilesLib;

namespace Tiles
{
    internal class MainViewModel : ViewModelBase
    {
        private VisualLocation _currentVisualLocation;

        public MapNavigator Navigator { get; set; } = new MapNavigator();
        public ObservableCollection<VisualLocation> VisualMap { get; private set; } = new ObservableCollection<VisualLocation>();
        public Vector2 CurrentMapPosition
        {
            get => GetValue<Vector2>();
            set => SetValue(value);
        }
        public Direction CurrentOrientation
        {
            get => GetValue<Direction>();
            set => SetValue(value);
        }
        public CubeFace CurrentFace
        {
            get => GetValue<CubeFace>();
            set => SetValue(value);
        }


        public MainViewModel()
        {
            var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), @"Tiles.input.txt");

            Navigator.Parse(input);

            foreach (var location in Navigator.Map)
            {
                VisualMap.Add(new VisualLocation(location.Value));
            }

            Navigator.SetupStartPosition();
            UpdateCurrentLocation();

            CalculatePasswordFlat = new RelayCommand(CanCalculatePasswordFlat, DoCalculatePasswordFlat);
            CalculatePasswordCube = new RelayCommand(CanCalculatePasswordCube, DoCalculatePasswordCube);
            SimulationCube = new RelayCommand(CanSimulationCube, DoSimulationCube);

            MoveUp = new RelayCommand(CanMoveUp, DoMoveUp);
            MoveLeft = new RelayCommand(CanMoveLeft, DoMoveLeft);
            MoveRight = new RelayCommand(CanMoveRight, DoMoveRight);
            MoveDown = new RelayCommand(CanMoveDown, DoMoveDown);
        }

        public RelayCommand CalculatePasswordFlat { get; }
        public bool CanCalculatePasswordFlat()
        {
            return true;
        }
        public void DoCalculatePasswordFlat()
        {
            Navigator.CalculatePasswordFlat();
        }

        public RelayCommand CalculatePasswordCube { get; }
        public bool CanCalculatePasswordCube()
        {
            return true;
        }
        public void DoCalculatePasswordCube()
        {
            Navigator.SetupSimulationCube();
            UpdateCurrentLocation();
            Task.Run(Navigator.CalculatePasswordCube);
        }

        public RelayCommand SimulationCube { get; }
        public bool CanSimulationCube()
        {
            return true;
        }
        public void DoSimulationCube()
        {
            Navigator.SetupSimulationCube();
            UpdateCurrentLocation();
            Task.Run(RunSimulationCube);
        }
        public void RunSimulationCube()
        {
            while (!Navigator.SimulationCubeDone)
            {
                Navigator.SimulationCubeNextStep();

                App.Current.Dispatcher.Invoke(() =>
                {
                    UpdateCurrentLocation();
                });

                Thread.Sleep(50);
            }
        }


        public RelayCommand MoveUp { get; }
        public bool CanMoveUp()
        {
            return true;
        }
        public void DoMoveUp()
        {
            DoSingleMove(Direction.Up);
        }

        public RelayCommand MoveLeft { get; }
        public bool CanMoveLeft()
        {
            return true;
        }
        public void DoMoveLeft()
        {
            DoSingleMove(Direction.Left);
        }

        public RelayCommand MoveRight { get; }
        public bool CanMoveRight()
        {
            return true;
        }
        public void DoMoveRight()
        {
            DoSingleMove(Direction.Right);
        }

        public RelayCommand MoveDown { get; }
        public bool CanMoveDown()
        {
            return true;
        }
        public void DoMoveDown()
        {
            DoSingleMove(Direction.Down);
        }

        private void DoSingleMove(Direction dir)
        {
            Navigator.CurrentState.Orientation = dir;
            Navigator.DoMove();
            UpdateCurrentLocation();
        }


        private void UpdateCurrentLocation()
        {
            if (_currentVisualLocation != null)
            {
                _currentVisualLocation.IsCurrentLocation = false;
            }

            CurrentMapPosition = Navigator.CurrentState.Position + Navigator.CurrentFace.ParseOffset;
            _currentVisualLocation = VisualMap.Where(l => l.PositionX == CurrentMapPosition.X && l.PositionY == CurrentMapPosition.Y).First();
            _currentVisualLocation.IsCurrentLocation = true;

            CurrentFace = Navigator.CurrentFace;
            CurrentOrientation = Navigator.CurrentState.Orientation;
        }

    }
}
