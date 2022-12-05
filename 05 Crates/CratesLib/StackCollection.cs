using CommonWPF;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CratesLib
{
    public class StackCollection
    {
        public List<CrateStack> Stacks { get; private set; } = new List<CrateStack>();

        public StackCollection(StackCollection stacks)
        {
            Stacks = new List<CrateStack>(stacks.Stacks);
        }

        public StackCollection() {}

        internal void AddStack(CrateStack crateStack)
        {
            Stacks.Add(crateStack);
        }
    }
}
