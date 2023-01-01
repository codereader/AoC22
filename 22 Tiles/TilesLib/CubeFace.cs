using System;
using System.Collections.Generic;
using System.Linq;
using System.Numerics;
using System.Text;
using System.Threading.Tasks;

namespace TilesLib
{
    public class CubeFace
    {
        internal Dictionary<Vector2, Location> Map { get; set; } = new Dictionary<Vector2, Location>();

        public String Name { get; set; }

        public Vector2 ParseOffset { get; set; }

        internal Dictionary<Direction, Connection> Connections { get; set; } = new Dictionary<Direction, Connection>();
    }
}
