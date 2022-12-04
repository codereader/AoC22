namespace Cleanup
{
    internal class Elf
    {
        public int Min { get; private set; }
        public int Max { get; private set; }

        public Elf(string range)
        {
            var sections = range.Split('-');

            Min = int.Parse(sections[0]);
            Max = int.Parse(sections[1]);
        }
    }
}