using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MonkeyLib
{
    internal class Monkey
    {
        internal int InspectionCount { get; set; }
        public List<Item> Items { get; set; } = new List<Item>();

        public int Number { get; set; }
        public Operation Operation { get; set; }
        public int TestDivisor { get; set; }

        public int NextMonkeyIfTrue { get; set; }
        public int NextMonkeyIfFalse { get; set; }

        internal void DoRound(List<Monkey> monkeys)
        {
            foreach (var item in Items)
            {
                InspectionCount++;
                item.PerformOperation(Operation);
                if (item.Decide(TestDivisor) == true)
                {
                    monkeys[NextMonkeyIfTrue].Items.Add(item);
                }
                else
                {
                    monkeys[NextMonkeyIfFalse].Items.Add(item);
                }
            }

            Items.Clear();
        }
    }
}
