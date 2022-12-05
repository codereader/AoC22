using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CrateLib
{
    internal class StackCollection
    {

        private List<CrateStack> _stacks = new List<CrateStack>();

        internal void AddStack(CrateStack crateStack)
        {
            _stacks.Add(crateStack);
        }
    }
}
