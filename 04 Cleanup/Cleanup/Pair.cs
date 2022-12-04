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
    }
}
