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
        public int RoundsSinceLastMove { get; set; }
        public static int MaxRoundsSinceLastMove { get; set; }

        public Vector2 Position { get; set; }
        public Vector2 ProposedPosition { get; set; }

        public Elf(Vector2 pos)
        {
            Position = pos;
            RoundsSinceLastMove = MaxRoundsSinceLastMove;
        }

        public Dictionary<string, bool> Neighbors { get; set; } = new Dictionary<string, bool>();

        internal int CountNeighbors()
        {
            return Neighbors.Count(n => n.Value == true);
        }

    }
}
