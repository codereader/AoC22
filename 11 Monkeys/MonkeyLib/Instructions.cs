using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace MonkeyLib
{
    public class Instructions
    {
        internal List<Monkey> Monkeys { get; set; } = new List<Monkey>();

        internal List<int> Divisors { get; set; } = new List<int>();

        Monkey currentMonkey;

        public void Parse(List<string> input)
        {
            foreach (var line in input)
            {
                // Monkey 0:
                if (line.Contains("Monkey"))
                {
                    currentMonkey = new Monkey();
                    Monkeys.Add(currentMonkey);

                    var numstr = line.Substring(7);
                    // number without the ":"
                    numstr = numstr.Remove(numstr.Length - 1);
                    currentMonkey.Number = int.Parse(numstr.ToString());
                }

                //   Starting items: 52, 78, 79, 63, 51, 94
                else if (line.Contains("Starting items"))
                {
                    // separate text from item list
                    var parts = line.Split(":");
                    // separate numbers
                    var nums = parts[1].Split(",");
                    foreach (var num in nums)
                    {
                        var currentItem = new Item(int.Parse(num.Trim()));
                        currentMonkey.Items.Add(currentItem);
                    }
                }

                // Operation: new = old * 13
                else if (line.Contains("Operation"))
                {
                    var parts = line.Split(":");
                    var currentOperation = new Operation(parts[1]);
                    currentMonkey.Operation = currentOperation;
                }

                //   Test: divisible by 5
                else if (line.Contains("Test"))
                {
                    var parts = line.Split(" ", StringSplitOptions.RemoveEmptyEntries);
                    currentMonkey.TestDivisor = int.Parse(parts.Last());
                    Divisors.Add(currentMonkey.TestDivisor);
                }

                //     If true: throw to monkey 0
                else if (line.Contains("If true"))
                {
                    var parts = line.Split(" ", StringSplitOptions.RemoveEmptyEntries);
                    currentMonkey.NextMonkeyIfTrue = int.Parse(parts.Last());
                }

                //     If false: throw to monkey 6
                else if (line.Contains("If false"))
                {
                    var parts = line.Split(" ", StringSplitOptions.RemoveEmptyEntries);
                    currentMonkey.NextMonkeyIfFalse = int.Parse(parts.Last());
                }
            }

            foreach (var monkey in Monkeys)
            {
                monkey.SetupDivisors(Divisors);
            }

        }

        public void DoRounds1(int rounds)
        {
            ResetMonkeyCounts();
            for (int i = 0; i < rounds; i++)
            {
                DoRound1();
            }
        }
        private void DoRound1()
        {
            foreach (var monkey in Monkeys)
            {
                monkey.DoRound1(Monkeys);
            }
        }

        public void DoRounds2(long rounds)
        {
            ResetMonkeyCounts();
            for (long i = 0; i < rounds; i++)
            {
                DoRound2();
            }
        }
        public void DoRound2()
        {
            foreach (var monkey in Monkeys)
            {
                monkey.DoRound2(Monkeys);
            }
        }

        private void ResetMonkeyCounts()
        {
            foreach (var monkey in Monkeys)
            {
                monkey.InspectionCount = 0;
            }
        }


        public long GetProduct()
        {
            var orderedList = Monkeys.OrderByDescending(m => m.InspectionCount).Select(m => m.InspectionCount).ToList();
            return orderedList[0] * orderedList[1];
        }

    }
}
