using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ParsingHellLib
{
    internal class PackageComparer : IComparer<Package>
    {
        ElementComparer _elementComparer = new ElementComparer();

        public int Compare(Package left, Package right)
        {
            return _elementComparer.Compare(left.Contents, right.Contents);
        }

    }
}
