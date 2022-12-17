using System;
using System.Collections.Generic;
using System.Linq;
using System.Numerics;
using System.Text;
using System.Threading.Tasks;

namespace SignalLib
{
    internal class Pair
    {
        // signal - beacon pair
        public int Id { get; set; }

        public Vector2 SignalPosition { get; set; }
        public Vector2 BeaconPosition { get; set; }
        public int Distance { get; set; }
    }
}
