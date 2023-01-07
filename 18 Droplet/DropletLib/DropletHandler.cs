using System.Numerics;

namespace DropletLib
{
    public class DropletHandler
    {
        private List<Location> _locations = new List<Location>();
        public void Parse(List<string> input)
        {
            _locations = input.Select(l => new Location(l)).ToList();
            CountNeighbors();
        }

        private void CountNeighbors()
        {
            var directions = new List<Vector3>
            {
                new Vector3(0, 0, 1),
                new Vector3(0, 0, -1),
                new Vector3(1, 0, 0),
                new Vector3(-1, 0, 0),
                new Vector3(0, 1, 0),
                new Vector3(0, -1, 0)
            };

            foreach (var location in _locations)
            {
                foreach (var dir in directions) 
                {
                    if (_locations.Any(l => l.Position == location.Position + dir)) 
                    {
                        location.NeighborCount++;
                    }
                }
            }
        }

        public int GetSurfaceArea()
        {
            return 6 * _locations.Count - _locations.Sum(l => l.NeighborCount);
        }

    }
}