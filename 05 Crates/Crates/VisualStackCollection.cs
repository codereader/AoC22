using CratesLib;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Crates
{
    public class VisualStackCollection
    {
        public ObservableCollection<VisualCrateStack> VisualStacks { get; set; } = new ObservableCollection<VisualCrateStack>();

        internal void UpdateVisuals(StackCollection stacks)
        {
            VisualStacks.Clear();
            foreach (var stack in stacks.Stacks)
            {
                VisualStacks.Add(new VisualCrateStack(stack.Id, stack.Crates));
            }
        }
    }
}
