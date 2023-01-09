using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MonkeyLib
{
    public class Monkey
    {
        internal string Name { get; set; }
        
        internal long? Number { get; set; } = null;
        
        internal String? Partner1Name { get; set; }
        internal String? Partner2Name { get; set; }
        
        internal Monkey? Partner1 { get; set; }
        internal Monkey? Partner2 { get; set; }
        
        internal String? Operation { get; set; }
    }
}
