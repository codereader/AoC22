using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ParsingHellLib
{
    internal class Pair
    {
        public int Index { get; set; }
        public Package? Left { get; set; }
        public Package? Right { get; set; }
        public bool RightOrder { get; internal set; }

    }
}
