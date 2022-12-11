namespace MonkeyLib
{
    public class Item
    {
        private long _worryLevel;
        public Item(int num)
        {
            _worryLevel= num;
        }

        internal void PerformOperation(Operation operation)
        {
            _worryLevel = operation.Perform(_worryLevel);
            _worryLevel /= 3;
        }

        internal bool Decide(int testDivisor)
        {
            return _worryLevel % testDivisor == 0;
        }

    }
}