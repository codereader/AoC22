using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Rucksack
{
    internal class Backpack
    {
        public List<char> Contents { get; private set; }

        private List<char> _compartment1= new List<char>();
        private List<char> _compartment2= new List<char>();

        private char _duplicate;

        public Backpack(string str)
        {
            Contents = str.ToList();

            var contentCount = Contents.Count;

            _compartment1 = str.Substring(0, contentCount / 2).ToList();
            _compartment2 = str.Substring(contentCount / 2, contentCount / 2).ToList();

            // Console.Write(_contents.Substring(0, contentCount / 2));
            // Console.WriteLine($", " + _contents.Substring(contentCount / 2, contentCount / 2));
        }

        public char FindDuplicate()
        {
            _duplicate =  _compartment1.Intersect(_compartment2).ToList().First();
            return _duplicate;
        }

    }
}
