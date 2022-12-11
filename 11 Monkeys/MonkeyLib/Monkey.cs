using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MonkeyLib
{
    internal class Monkey
    {
        internal long InspectionCount { get; set; }
        public List<Item> Items { get; set; } = new List<Item>();

        public int Number { get; set; }
        public Operation Operation { get; set; }
        public int TestDivisor { get; set; }

        public int NextMonkeyIfTrue { get; set; }
        public int NextMonkeyIfFalse { get; set; }

        internal void DoRound1(List<Monkey> monkeys)
        {
            foreach (var item in Items)
            {
                InspectionCount++;
                item.PerformOperation1(Operation);
                if (item.Decide1(TestDivisor) == true)
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

        internal void DoRound2(List<Monkey> monkeys)
        {
            foreach (var item in Items)
            {
                InspectionCount++;
                item.PerformOperation2(Operation);
                if (item.Decide2(TestDivisor) == true)
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


        internal void SetupDivisors(List<int> divisors)
        {
            foreach (var item in Items)
            {
                item.SetupDivisors(divisors);
            }
        }
    }
}
