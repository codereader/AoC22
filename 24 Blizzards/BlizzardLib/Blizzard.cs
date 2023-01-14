using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BlizzardLib
{
    internal class Blizzard
    {
        public int StartX { get; set; }
        public int StartY { get; set; }

        public int CurrentX { get; set; }
        public int CurrentY { get; set; }

        public Blizzard()
        {
            CurrentX = StartX;
            CurrentY = StartY;
        }
    }
}
