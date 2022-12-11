namespace MonkeyLib
{
    public class Item
    {
        private long _worryLevel;

        // part 2: keep track of the remainder for each divisor
        private Dictionary<int, long> _modulos = new Dictionary<int, long>();

        public Item(int num)
        {
            _worryLevel= num;
        }

        // part 1
        internal void PerformOperation1(Operation operation)
        {
            _worryLevel = operation.Perform(_worryLevel);
            _worryLevel /= 3;
        }

        internal bool Decide1(int testDivisor)
        {
            return _worryLevel % testDivisor == 0;
        }

        // part 2
        internal void SetupDivisors(List<int> divisors)
        {
            foreach (var divisor in divisors) 
            {
                _modulos.Add(divisor, (_worryLevel % divisor));
            }
        }

        internal void PerformOperation2(Operation operation)
        {
            foreach (var modulo in _modulos)
            {
                _modulos[modulo.Key] = operation.Perform(modulo.Value) % modulo.Key;
            }
        }

        internal bool Decide2(int testDivisor)
        {
            return _modulos[testDivisor] % testDivisor == 0;
        }


    }
}