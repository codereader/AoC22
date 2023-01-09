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
        public string Name { get; set; }

        public long? Number { get; set; } = null;


        public String Partner1Name { get; set; }
        public String Partner2Name { get; set; }

        public Monkey? Partner1 { get; set; }
        public Monkey? Partner2 { get; set; }

        public String? Operation { get; set; }
    }
}
