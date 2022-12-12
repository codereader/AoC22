using System.Collections.Generic;
using System.Numerics;

namespace HillLib
{
    public class Geographer
    {
        private HashSet<Location> _availablePositions = new HashSet<Location>();

        public Dictionary<Vector2, Location> Heightmap { get; set; } = new Dictionary<Vector2, Location>();

        public Location StartPosition { get; private set; }
        public Location EndPosition { get; private set; }

        public int XMax { get; private set; }
        public int YMax { get; private set; }

        public void Parse(List<string> input)
        {
            YMax = input.Count;
            for (int y = 0; y < YMax; y++)
            {
                var line = input[y];
                XMax = line.Length;
                for (int x = 0; x < XMax; x++)
                {
                    var currentPos = new Vector2(x, y);
                    var location = new Location(currentPos, line[x]);
                    Heightmap.Add(currentPos, location);
                    if (line[x] == 'S')
                    {
                        StartPosition = location;
                    }
                    else if (line[x] == 'E')
                    {
                        EndPosition = location;
                    }
                }
            }
        }

        public void FindConnections()
        {
            _availablePositions.Add(StartPosition);

           while (true)
            {
                if (_availablePositions.Count == 0)
                {
                    break;
                }
                // choose next position to evaluate
                var minLength = _availablePositions.Min(p => p.ShortestConnectionLength);
                var nextPosition = _availablePositions.Where(p => p.ShortestConnectionLength == minLength).First();

                // FInd neighbors that have not been evaluated and can be reached
                var neighbors = ExamineNeighbors(nextPosition);
                _availablePositions.Remove(nextPosition);
                nextPosition.IsEvaluated = true;
            }
        }

        private List<Location> ExamineNeighbors(Location location)
        {
            var neighbors = new List<Location>();
            var testPositions = new List<Vector2>
            {
                new Vector2(1, 0),
                new Vector2(0, 1),
                new Vector2(-1, 0),
                new Vector2(0, -1)
            };

            foreach (var position in testPositions)
            {
                if (Heightmap.TryGetValue(location.Position + position, out var neighbor))
                {
                    if (!neighbor.IsEvaluated && CanReach(location, neighbor) && !_availablePositions.Contains(neighbor))
                    {
                        _availablePositions.Add(neighbor);
                        neighbor.ShortestConnectionLength = location.ShortestConnectionLength + 1;
                        neighbor.ShortestConnection = new List<Location>(location.ShortestConnection);
                        neighbor.ShortestConnection.Add(neighbor);
                        neighbors.Add(neighbor);
                    }
                }
            }
            return neighbors;
        }

        private bool CanReach(Location location, Location neighbor)
        {
            // new height can be lower or at most 1 higher
            return neighbor.Height <= location.Height + 1;
        }

        public int ShortestDistanceToDestination()
        {
            return EndPosition.ShortestConnectionLength;
        }
    }
}