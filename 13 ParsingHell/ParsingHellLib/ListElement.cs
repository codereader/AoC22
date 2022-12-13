using System;
using System.Collections.Generic;
using System.Linq;
using System.Numerics;
using System.Text;
using System.Threading.Tasks;

namespace ParsingHellLib
{
    internal class ListElement : Element
    {
        public List<Element> Contents { get; private set; } = new List<Element>();

    }
}
