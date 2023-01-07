using System.Numerics;

namespace DropletLib
{
    internal class Location
    {
        public Vector3 Position { get; private set; }

        public int NeighborCount { get; set; }

        public int OutsideFaceCount { get; set; }

        public Location(Vector3 pos)
        {
            Position = pos;
        }
    }
}