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
        public Location BestStartPosition { get; private set; }

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

        public int FindShortestPathfromStartToEnd()
        {
            FindConnections(StartPosition, true);
            HighlightPath(EndPosition);
            return EndPosition.ShortestConnectionLength;
        }

        public int FindShortestPathBestPosToEnd()
        {
            // find connections starting from the end position, moving down
            FindConnections(EndPosition, false);
            var candidates = Heightmap.Where(l => l.Value.Height == 0 && l.Value.ShortestConnectionLength > 0);
            var bestDist = candidates.Min(l => l.Value.ShortestConnectionLength);
            BestStartPosition = candidates.First(l => l.Value.ShortestConnectionLength == bestDist).Value;
            HighlightPath(BestStartPosition);
            return bestDist;
        }


        public void FindConnections(Location startLocation, bool movingUp)
        {
            _availablePositions.Clear();
            ClearLocationConnections();

            _availablePositions.Add(startLocation);

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
                ExamineNeighbors(nextPosition, movingUp);
                _availablePositions.Remove(nextPosition);
                nextPosition.IsEvaluated = true;
            }
        }

        private void ExamineNeighbors(Location location, bool movingUp)
        {
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
                    if (!neighbor.IsEvaluated && CanReach(location, neighbor, movingUp) && !_availablePositions.Contains(neighbor))
                    {
                        _availablePositions.Add(neighbor);
                        neighbor.ShortestConnectionLength = location.ShortestConnectionLength + 1;
                        neighbor.ShortestConnection = new List<Location>(location.ShortestConnection);
                        neighbor.ShortestConnection.Add(neighbor);
                    }
                }
            }
        }

        private bool CanReach(Location location, Location neighbor, bool movingUp)
        {
            if (movingUp)
            {
                // new height can be lower or at most 1 higher
                return neighbor.Height <= location.Height + 1;
            }
            else
            {
                return location.Height <= neighbor.Height + 1;
            }
        }

        private void ClearLocationConnections()
        {
            foreach (var location in Heightmap)
            {
                location.Value.ShortestConnection = new List<Location>();
                location.Value.ShortestConnectionLength = 0;
                location.Value.IsEvaluated = false;
                location.Value.BelongsToPath = false;
            }
        }

        public void HighlightPath(Location startLocation)
        {
            foreach (var location in startLocation.ShortestConnection)
            {
                location.BelongsToPath = true;
            }
        }
    }
}