using System.Collections.Generic;
using System.Linq;
using System.Numerics;

namespace DropletLib
{
    public class DropletHandler
    {
        private Dictionary<Vector3, Location> _locations = new Dictionary<Vector3, Location>();

        private List<Vector3> _directions = new List<Vector3>
            {
                new Vector3(0, 0, 1),
                new Vector3(0, 0, -1),
                new Vector3(1, 0, 0),
                new Vector3(-1, 0, 0),
                new Vector3(0, 1, 0),
                new Vector3(0, -1, 0)
            };

        public void Parse(List<string> input)
        {
            foreach (var line in input)
            {
                var parts = line.Split(',');
                var pos = new Vector3(int.Parse(parts[0]), int.Parse(parts[1]), int.Parse(parts[2]));
                _locations.Add(pos, new Location(pos));
            }
            CountNeighbors();
        }

        private void CountNeighbors()
        {
            foreach (var location in _locations)
            {
                foreach (var dir in _directions)
                {
                    if (_locations.TryGetValue(location.Key + dir, out var _))
                    {
                        location.Value.NeighborCount++;
                    }
                }
            }
        }

        public int GetSurfaceArea()
        {
            return 6 * _locations.Count - _locations.Sum(l => l.Value.NeighborCount);
        }

        public int GetOutsideSurfaceArea()
        {
            // water cube surrounding droplet
            var minX = _locations.Min(l => l.Key.X) - 1;
            var maxX = _locations.Max(l => l.Key.X) + 1;
            var minY = _locations.Min(l => l.Key.Y) - 1;
            var maxY = _locations.Max(l => l.Key.Y) + 1;
            var minZ = _locations.Min(l => l.Key.Z) - 1;
            var maxZ = _locations.Max(l => l.Key.Z) + 1;

            // flood fill
            var availablePositions = new List<Vector3>();
            var waterPositions = new HashSet<Vector3>();

            var minXPos = _locations.First(l => l.Key.X == minX + 1);
            var startPos = new Vector3(minX, minXPos.Key.Y, minXPos.Key.Z);

            availablePositions.Add(startPos);

            while (availablePositions.Count > 0)
            {
                var currentPos = availablePositions.First();

                foreach (var dir in _directions)
                {
                    var testPos = currentPos + dir;

                    if (!_locations.TryGetValue(testPos, out var _) &&
                        !waterPositions.Contains(testPos) &&
                        testPos.X >= minX && testPos.X <= maxX &&
                        testPos.Y >= minY && testPos.Y <= maxY &&
                        testPos.Z >= minZ && testPos.Z <= maxZ)
                    {
                        waterPositions.Add(testPos);
                        availablePositions.Add(testPos);
                    }
                }
                availablePositions.Remove(currentPos);
            }

            // count outside faces on droplet
            foreach (var location in _locations)
            {
                foreach (var dir in _directions)
                {
                    if (waterPositions.Contains(location.Key + dir))
                    {
                        location.Value.OutsideFaceCount++;
                    }
                }
            }

            return _locations.Sum(l => l.Value.OutsideFaceCount);
        }
    }
}