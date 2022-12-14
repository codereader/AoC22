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
                    currentVLocation.X = x;
                    currentVLocation.Y = y;
                    // fill everything with air
                    currentVLocation.Filling = (int)Filling.Air;
                    VisualCaveMap.Add(currentVLocation);
                }
            }

            // fill rock tiles from dict with rock
            UpdateVisualMap();
        }

        private void UpdateVisualMap()
        {
            foreach (var location in _caveMap)
            {
                var vLocation = VisualCaveMap.Single(l => l.X == location.Key.X && l.Y == location.Key.Y);
                vLocation.Filling = (int)location.Value.Filling;
            }
        }
    }
}