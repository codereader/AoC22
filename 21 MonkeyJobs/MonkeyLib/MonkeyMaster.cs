using System.Xml.Linq;

namespace MonkeyLib
{
    public class MonkeyMaster
    {
        private List<Monkey> _monkeys = new List<Monkey>();

        public void Parse(List<string> input)
        {
            foreach (var line in input)
            {
                var monkey = new Monkey();

                var parts = line.Split(':');
                monkey.Name = parts[0];

                var jobparts = parts[1].Split(" ", StringSplitOptions.RemoveEmptyEntries);
                if (jobparts.Length < 2)
                {
                    // only 1 part, number
                    monkey.Number = long.Parse(jobparts[0]);
                }
                else
                {
                    // math operation
                    monkey.Partner1Name = jobparts[0];
                    monkey.Operation = jobparts[1];
                    monkey.Partner2Name = jobparts[2];
                }
                _monkeys.Add(monkey);
            }

            foreach (var monkey in _monkeys)
            {
                if (!string.IsNullOrEmpty(monkey.Partner1Name))
                {
                    monkey.Partner1 = _monkeys.First(m => m.Name == monkey.Partner1Name);
                }
                if (!string.IsNullOrEmpty(monkey.Partner2Name))
                {
                    monkey.Partner2 = _monkeys.First(m => m.Name == monkey.Partner2Name);
                }
            }
        }

        public long? DoMonkeyJobs()
        {
            var rootNum = _monkeys.First(m => m.Name == "root").Number;

            while (rootNum == null)
            {
                foreach (var monkey in _monkeys)
                {
                    if (monkey.Number == null)
                    {
                        if (monkey.Partner1.Number != null && monkey.Partner2.Number != null)
                        {
                            monkey.Number = DoOperation(monkey.Partner1.Number, monkey.Partner2.Number, monkey.Operation);
                        }
                    }
                }

                rootNum = _monkeys.First(m => m.Name == "root").Number;
            }

            return rootNum;
        }

        private long? DoOperation(long? number1, long? number2, string? operation)
        {
            return operation switch
            {
                "+" => number1 + number2,
                "-" => number1 - number2,
                "*" => number1 * number2,
                "/" => number1 / number2,
                _ => null
            };
        }

        // part 2
        public long FindNumberForEquality()
        {
            var human = _monkeys.First(m => m.Name == "humn");
            human.Number = null;

            // fill in numbers that don't depend on humn
            var unknownNumberCount = _monkeys.Count(m => m.Number == null);
            var previousUnknownNumberCount = 0;

            while (unknownNumberCount != previousUnknownNumberCount)
            {
                foreach (var monkey in _monkeys)
                {
                    if (monkey.Number == null && monkey.Name != "root" && monkey.Name != "humn")
                    {
                        if (monkey.Partner1.Number != null && monkey.Partner1.Name != "humn" &&
                            monkey.Partner2.Number != null && monkey.Partner2.Name != "humn")
                        {
                            monkey.Number = DoOperation(monkey.Partner1.Number, monkey.Partner2.Number, monkey.Operation);
                        }
                    }
                }
                previousUnknownNumberCount = unknownNumberCount;
                unknownNumberCount = _monkeys.Count(m => m.Number == null);
            }

            // reverse operations on both sides
            var rootMonkey = _monkeys.First(m => m.Name == "root");

            var unknownNumberMonkey = new Monkey();
            long? otherSideNumber = 0;

            if (rootMonkey.Partner1.Number == null)
            {
                unknownNumberMonkey = rootMonkey.Partner1;
                otherSideNumber = rootMonkey.Partner2.Number;
            }
            else
            {
                unknownNumberMonkey = rootMonkey.Partner2;
                otherSideNumber = rootMonkey.Partner1.Number;
            }

            while (true)
            {
                if (unknownNumberMonkey.Partner1.Number == null)
                {
                    // b = x + a    =>  b - a = x 
                    // b = x - a    =>  b + a = x
                    // b = x * a    =>  b / a = x
                    // b = x / a    =>  b * a = x

                    otherSideNumber = DoInverseOperation(otherSideNumber, unknownNumberMonkey.Partner2.Number, unknownNumberMonkey.Operation);
                    unknownNumberMonkey = unknownNumberMonkey.Partner1;
                }
                else
                {
                    // b = a + x  =>  b - a = x 
                    // b = a - x  =>  a - b = x
                    // b = a * x  =>  b / a = x
                    // b = a / x  =>  a / b = x

                    otherSideNumber = DoSecondInverseOperation(otherSideNumber, unknownNumberMonkey.Partner1.Number, unknownNumberMonkey.Operation);
                    unknownNumberMonkey = unknownNumberMonkey.Partner2;
                }

                if (unknownNumberMonkey == human)
                {
                    break;
                }
            }

            return (long)otherSideNumber;

        }

        private long? DoInverseOperation(long? number1, long? number2, string? operation)
        {
            return operation switch
            {
                "+" => number1 - number2,
                "-" => number1 + number2,
                "*" => number1 / number2,
                "/" => number1 * number2,
                _ => null
            };
        }

        private long? DoSecondInverseOperation(long? number1, long? number2, string? operation)
        {
            return operation switch
            {
                "+" => number1 - number2,
                "-" => number2 - number1,
                "*" => number1 / number2,
                "/" => number2 / number1,
                _ => null
            };
        }

    }
}