using System;
using System.Collections.Generic;
using System.Linq;
using System.Numerics;
using System.Text;
using System.Threading.Tasks;

namespace TetrisLib
{
    internal class Rock
    {
        public List<bool[]> Positions = new List<bool[]>();

        public Rock(Rock rock)
        {
            Positions = new List<bool[]>(rock.Positions);
            MinX = rock.MinX;
            MaxX = rock.MaxX;
            Height = rock.Height;
        }
        public Rock() {}

        public int MinX { get; set; }
        public int MaxX { get; set; }
        public int Height { get; set; }
    }
}
