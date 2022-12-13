using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ParsingHellLib
{
    internal class NumberElement : Element
    {
        public int Number { get; private set; }

        public NumberElement(int num)
        {
            Number = num;
        }
    }
}
