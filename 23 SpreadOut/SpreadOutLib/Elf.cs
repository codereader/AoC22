using System;
using System.Collections.Generic;
using System.Linq;
using System.Numerics;
using System.Text;
using System.Threading.Tasks;

namespace SpreadOutLib
{
    public class Elf
    {
        public Elf(Vector2 pos)
        {
            Position = pos;
        }

        public Vector2 Position { get; set; }

        public Vector2 ProposedPosition { get; set; }

        public Dictionary<string, bool> Neighbors { get; set; } = new Dictionary<string, bool>();

        internal int CountNeighbors()
        {
            return Neighbors.Count(n => n.Value == true);
        }

    }
}
