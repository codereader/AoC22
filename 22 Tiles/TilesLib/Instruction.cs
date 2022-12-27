using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TilesLib
{
    internal class Instruction
    {
        public int Turn { get; set; }

        public int Move { get; set; }
        public Instruction(string str) 
        {
            if (str == "R")
            {
                Turn = 1;
            }
            else if (str == "L")
            {
                Turn = -1;
            }
            else
            {
                Move = int.Parse(str);
            }
        }
    }
}
