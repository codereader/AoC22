namespace Cleanup
{
    internal class Pair
    {
        public Elf First { get; private set; }
        public Elf Second { get; private set; }

        public Pair(string line)
        {
            var pair = line.Split(',');

            First = new Elf(pair[0]);
            Second = new Elf(pair[1]);
        }

        internal bool RangesContained()
        {
            return (First.Min <= Second.Min && First.Max >= Second.Max)
                || (First.Min >= Second.Min && First.Max <= Second.Max);
        }

        internal bool RangesOverlap()
        {
            return (First.Min >= Second.Min && First.Min <= Second.Max)
                || (Second.Min >= First.Min && Second.Min <= First.Max);
        }

    }
}
