using System.Numerics;

namespace DropletLib
{
    internal class Location
    {
        public Vector3 Position { get; private set; }

        public int NeighborCount { get; set; }

        public Location(string line)
        {
            var parts = line.Split(',');

            Position = new Vector3(int.Parse(parts[0]), int.Parse(parts[1]), int.Parse(parts[2]));
        }
    }
}