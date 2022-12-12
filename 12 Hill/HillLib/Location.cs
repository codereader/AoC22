using System;
using System.Collections.Generic;
using System.Linq;
using System.Numerics;
using System.Text;
using System.Threading.Tasks;

namespace HillLib
{
    public class Location
    {
        public Vector2 Position { get; set; }
        public char Letter { get;  private set; }

        public int Height { get; private set; }

        public List<Location> ShortestConnection { get; set; } = new List<Location>();
        public int ShortestConnectionLength { get; internal set; } = 0;

        public bool IsEvaluated { get; set; }

        public bool BelongsToPath { get; set; }


        public Location(Vector2 pos, char letter)
        {
            Position = pos;
            Letter = letter;

            Height = Letter - 97;

            if (Letter == 'S')
            {
                Height = 0;
            }
            else if (Letter == 'E')
            {
                Height = 25;
            }

            IsEvaluated = false;
        }


    }
}
