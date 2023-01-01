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


        private VisualLocation _currentVisualLocation;


        public MainViewModel()
        {
            var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), @"Tiles.input.txt");

            Navigator.Parse(input);

            foreach (var location in Navigator.Map)
            {
                VisualMap.Add(new VisualLocation(location.Value));
            }

            Navigator.SetupStartPosition();
            CurrentMapPosition = Navigator.CurrentState.Position + Navigator.CurrentFace.ParseOffset;
            _currentVisualLocation = VisualMap.Where(l => l.PositionX == CurrentMapPosition.X && l.PositionY == CurrentMapPosition.Y).First();
            _currentVisualLocation.IsCurrentLocation = true;

            SimulationFlat = new RelayCommand(CanSimulationFlat, DoSimulationFlat);
            FinishSimulationCube = new RelayCommand(CanFinishSimulationCube, DoFinishSimulationCube);
            SimulationCube = new RelayCommand(CanSimulationCube, DoSimulationCube);

            MoveUp = new RelayCommand(CanMoveUp, DoMoveUp);
            MoveLeft = new RelayCommand(CanMoveLeft, DoMoveLeft);
            MoveRight = new RelayCommand(CanMoveRight, DoMoveRight);
            MoveDown = new RelayCommand(CanMoveDown, DoMoveDown);
        }

        public RelayCommand SimulationFlat { get; }
        public bool CanSimulationFlat()
        {
            return true;
        }
        public void DoSimulationFlat()
        {
            Navigator.SimulationFlat();
        }

        public RelayCommand SimulationCube { get; }
        public bool CanSimulationCube()
        {
            return true;
        }
        public void DoSimulationCube()
        {
            Navigator.SetupSimulationCube();

            CurrentMapPosition = Navigator.CurrentState.Position + Navigator.CurrentFace.ParseOffset;
            _currentVisualLocation = VisualMap.Where(l => l.PositionX == CurrentMapPosition.X && l.PositionY == CurrentMapPosition.Y).First();
            _currentVisualLocation.IsCurrentLocation = true;

            Task.Run(RunSimulationCube);
        }

        public RelayCommand FinishSimulationCube { get; }
        public bool CanFinishSimulationCube()
        {
            return true;
        }
        public void DoFinishSimulationCube()
        {
            Navigator.SetupSimulationCube();

            CurrentMapPosition = Navigator.CurrentState.Position + Navigator.CurrentFace.ParseOffset;
            _currentVisualLocation = VisualMap.Where(l => l.PositionX == CurrentMapPosition.X && l.PositionY == CurrentMapPosition.Y).First();
            _currentVisualLocation.IsCurrentLocation = true;

            Task.Run(Navigator.SimulationCube);
        }


        public void RunSimulationCube()
        {
            while (!Navigator.SimulationCubeDone)
            {
                App.Current.Dispatcher.Invoke(() =>
                {
                    _currentVisualLocation.IsCurrentLocation = false;
                });


                Navigator.SimulationCubeNextStep();

                CurrentMapPosition = Navigator.CurrentState.Position + Navigator.CurrentFace.ParseOffset;
                _currentVisualLocation = VisualMap.Where(l => l.PositionX == CurrentMapPosition.X && l.PositionY == CurrentMapPosition.Y).First();

                App.Current.Dispatcher.Invoke(() =>
                {
                    _currentVisualLocation.IsCurrentLocation = true;
                    CurrentFace = Navigator.CurrentFace;
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

            Navigator.CurrentState.Orientation = Direction.Up;

            Navigator.DoMove(new Vector2(0, -1));

            UpdateCurrentLocation();

        }


        public RelayCommand MoveLeft { get; }
        public bool CanMoveLeft()
        {
            return true;
        }
        public void DoMoveLeft()
        {

            Navigator.CurrentState.Orientation = Direction.Left;


            Navigator.DoMove(new Vector2(-1, 0));
            UpdateCurrentLocation();

        }

        public RelayCommand MoveRight { get; }
        public bool CanMoveRight()
        {
            return true;
        }
        public void DoMoveRight()
        {

            Navigator.CurrentState.Orientation = Direction.Right;

            Navigator.DoMove(new Vector2(1, 0));
            UpdateCurrentLocation();
        }

        public RelayCommand MoveDown { get; }
        public bool CanMoveDown()
        {
            return true;
        }
        public void DoMoveDown()
        {
            Navigator.CurrentState.Orientation = Direction.Down;

            Navigator.DoMove(new Vector2(0, 1));

            UpdateCurrentLocation();
        }

        private void UpdateCurrentLocation()
        {
            _currentVisualLocation.IsCurrentLocation = false;
            CurrentMapPosition = Navigator.CurrentState.Position + Navigator.CurrentFace.ParseOffset;
            _currentVisualLocation = VisualMap.Where(l => l.PositionX == CurrentMapPosition.X && l.PositionY == CurrentMapPosition.Y).First();
            _currentVisualLocation.IsCurrentLocation = true;
            CurrentFace = Navigator.CurrentFace;
            CurrentOrientation = Navigator.CurrentState.Orientation;
           
        }

    }
}
