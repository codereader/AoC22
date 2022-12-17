using System;
using System.Collections.Generic;
using System.Linq;
using System.Numerics;
using System.Text;
using System.Threading.Tasks;

namespace SignalLib
{
    internal class Location
    {
        public Vector2 Position { get; set; }
        public Pair? SignalPosition { get; set; }
        public Pair? BeaconPosition { get; set; }

        public Pair? ClosestSignal { get; set; }
    }
}
