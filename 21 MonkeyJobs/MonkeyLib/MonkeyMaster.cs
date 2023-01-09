namespace MonkeyLib
{
    public class MonkeyMaster
    {
        public List<Monkey> _monkeys = new List<Monkey>();


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
                _   => null
            };
        }
    }
}