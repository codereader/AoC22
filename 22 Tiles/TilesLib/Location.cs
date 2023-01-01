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
        public int PositionX { get; set; }
        public int PositionY { get; set; }

        public  Filling Fill {get; set;}


        public Location(Vector2 pos, char fill)
        {
            PositionX = (int)pos.X;
            PositionY = (int)pos.Y;

            if (fill == ' ')
            {
                Fill = Filling.Off;
            }
            else if (fill == '.')
            {
                Fill = Filling.Path;
            }
            else
            {
                Fill = Filling.Wall;
            }
        }

        public Location()
        {

        }
    }
}
