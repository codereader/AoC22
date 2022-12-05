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

        public StackCollection(StackCollection otherStacks)
        {
            foreach (var crateStack in otherStacks.Stacks)
            {

                Stacks.Add(new CrateStack(crateStack));
            }
        }

        public StackCollection() {}

        internal void AddStack(CrateStack crateStack)
        {
            Stacks.Add(crateStack);
        }
    }
}
