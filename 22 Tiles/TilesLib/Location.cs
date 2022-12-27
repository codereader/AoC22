using System;
using System.Collections.Generic;
using System.Linq;
using System.Numerics;
using System.Text;
using System.Threading.Tasks;

namespace TilesLib
{
    public class Location
    {
        public int PositionX { get; private set; }
        public int PositionY { get; private set; }

        public  Filling Fill {get; private set;}


        public Location(Vector2 pos, char v)
        {
            PositionX = (int)pos.X;
            PositionY = (int)pos.Y;

            if (v == ' ')
            {
                Fill = Filling.Off;
            }
            else if (v == '.')
            {
                Fill = Filling.Path;
            }
            else
            {
                Fill = Filling.Wall;
            }
        }
    }
}
