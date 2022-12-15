using CommonWPF;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Numerics;
using System.Windows.Documents;

namespace SandLib
{
    public class CaveHandler : ViewModelBase
    {
        private Dictionary<Vector2, Location> _caveMap = new Dictionary<Vector2, Location>();
        public ObservableCollection<VisualLocation> VisualCaveMap { get; set; } = new ObservableCollection<VisualLocation>();
        public int MinX { get; set; }
        public int MinY { get; set; } = 0;
        public int MaxX { get; set; }
        public int MaxY { get; set; }

        public int SandCountAbyss
        {
            get => GetValue<int>();
            set => SetValue(value);
        }
        public int SandCountFloor
        {
            get => GetValue<int>();
            set => SetValue(value);
        }


        public void Parse(List<string> input)
        {
            foreach (var line in input)
            {
                // 493,23 -> 493,20 -> 493,23 -> 495,23
                var parts = line.Split("->");

                for (int partNum = 0; partNum < parts.Length - 1; partNum++)
                {
                    // fill each position between start and end point with rock
                    var start = parts[partNum].Trim();
                    var end = parts[partNum + 1].Trim();
                    var startCoords = start.Split(',');
                    var endCoords = end.Split(',');
                    var startX = int.Parse(startCoords[0]);
                    var startY = int.Parse(startCoords[1]);
                    var endX = int.Parse(endCoords[0]);
                    var endY = int.Parse(endCoords[1]);

                    var rangeX = Enumerable.Range(Math.Min(startX, endX), Math.Max(startX, endX) - Math.Min(startX, endX) + 1);
                    var rangeY = Enumerable.Range(Math.Min(startY, endY), Math.Max(startY, endY) - Math.Min(startY, endY) + 1);

                    foreach (var currentX in rangeX)
                    {
                        foreach (var currentY in rangeY)
                        {
                            var pos = new Vector2(currentX, currentY);
                            if (!_caveMap.TryGetValue(pos, out var currentLocation))
                            {
                                // add new rock location
                                currentLocation = new Location();
                                currentLocation.Position = pos;
                                currentLocation.Filling = Filling.Rock;
                                _caveMap[pos] = currentLocation;
                            }
                        }
                    }

                }
            }

            // Boundaries
            MinX = _caveMap.Min(p => (int)p.Key.X) - 1;
            MaxX = _caveMap.Max(p => (int)p.Key.X) + 1;
            MaxY = _caveMap.Max(p => (int)p.Key.Y) + 1;

            // Fill Visual Map
            for (int y = MinY; y <= MaxY; y++)
            {
                for (int x = MinX; x <= MaxX; x++)
                {
                    var currentVLocation = new VisualLocation();
                    currentVLocation.X = x - MinX;
                    currentVLocation.Y = y;
                    // fill everything with air
                    currentVLocation.Filling = (int)Filling.Air;
                    VisualCaveMap.Add(currentVLocation);
                }
            }

            // fill rock tiles from dict with rock
            UpdateVisualMap();
        }

        public void FillWithSandToAbyss()
        {
            SandCountAbyss = 0;
            var sandFallingIntoAbyss = false;

            var down = new Vector2(0, 1);
            var downLeft = new Vector2(-1, 1);
            var downRight = new Vector2(1, 1);

            while (!sandFallingIntoAbyss)
            {
                // start new sand unit
                var currentSandPosition = new Vector2(500, 0);
                var sandAtRest = false;

                while (!sandAtRest)
                {
                    if (PositionIsAvailable(currentSandPosition + down))
                    {
                        currentSandPosition = currentSandPosition + down;
                    }
                    else if (PositionIsAvailable(currentSandPosition + downLeft))
                    {
                        currentSandPosition = currentSandPosition + downLeft;
                    }
                    else if (PositionIsAvailable(currentSandPosition + downRight))
                    {
                        currentSandPosition = currentSandPosition + downRight;
                    }
                    else
                    {
                        sandAtRest = true;
                        var newSandLocation = new Location();
                        newSandLocation.Position = currentSandPosition;
                        newSandLocation.Filling = Filling.Sand;
                        _caveMap.Add(currentSandPosition, newSandLocation);
                        SandCountAbyss++;
                    }

                    if (currentSandPosition.Y >= MaxY)
                    {
                        // reached the bottom
                        sandFallingIntoAbyss = true;
                        break;
                    }
                }
            }
        }

        public void FillWithSandToFloor()
        {
            SandCountFloor = 0;
            RemoveSand();

            var down = new Vector2(0, 1);
            var downLeft = new Vector2(-1, 1);
            var downRight = new Vector2(1, 1);

            var topPositionFilled = false;

            while (!topPositionFilled)
            {
                var currentSandPosition = new Vector2(500, 0);

                if (!PositionIsAvailable(currentSandPosition))
                {
                    // top is filled with sand
                    topPositionFilled = true;
                    break;
                }

                var sandAtRest = false;

                while (!sandAtRest)
                {
                    if (currentSandPosition.Y == MaxY)
                    {
                        // reached floor, put to rest here
                        sandAtRest = true;
                        var newSandLocation = new Location();
                        newSandLocation.Position = currentSandPosition;
                        newSandLocation.Filling = Filling.Sand;
                        _caveMap[currentSandPosition] = newSandLocation;
                        SandCountFloor++;
                    }
                    if (PositionIsAvailable(currentSandPosition + down))
                    {
                        currentSandPosition = currentSandPosition + down;
                    }
                    else if (PositionIsAvailable(currentSandPosition + downLeft))
                    {
                        currentSandPosition = currentSandPosition + downLeft;
                    }
                    else if (PositionIsAvailable(currentSandPosition + downRight))
                    {
                        currentSandPosition = currentSandPosition + downRight;
                    }
                    else
                    {
                        sandAtRest = true;
                        var newSandLocation = new Location();
                        newSandLocation.Position = currentSandPosition;
                        newSandLocation.Filling = Filling.Sand;
                        _caveMap[currentSandPosition] = newSandLocation;
                        SandCountFloor++;
                    }

                }

            }
        }

        private void RemoveSand()
        {
            foreach (var location in _caveMap.Where(l => l.Value.Filling == Filling.Sand))
            {
                location.Value.Filling = Filling.Air;
            }
        }

        private bool PositionIsAvailable(Vector2 testPosition)
        {
            if (_caveMap.TryGetValue(testPosition, out var location))
            {
                // filled with air, is available
                return location.Filling == Filling.Air;
            }
            else
            {
                // not in dict, is filled with air
                return true;
            }
        }

        private void UpdateVisualMap()
        {
            foreach (var location in _caveMap)
            {
                var vLocation = VisualCaveMap.Single(l => l.X == location.Key.X - MinX && l.Y == location.Key.Y);
                vLocation.Filling = (int)location.Value.Filling;
            }
        }
    }
}