using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Rucksack
{
    internal class Backpack
    {
        private List<char> _compartment1;
        private List<char> _compartment2;

        public List<char> Contents { get; private set; }

        public Backpack(string str)
        {
            Contents = str.ToList();

            var contentCount = Contents.Count;

            // first half
            _compartment1 = str.Substring(0, contentCount / 2).ToList();

            // second half
            _compartment2 = str.Substring(contentCount / 2, contentCount / 2).ToList();
        }

        public char FindDuplicate()
        {
            // only one element is contained in both compartments
            return _compartment1.Intersect(_compartment2).First();
        }

    }
}
