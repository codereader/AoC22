namespace Cleanup
{
    internal class Elf
    {
        public int Min { get; private set; }
        public int Max { get; private set; }

        public List<int> Range { get; private set; } = new List<int>();

        public Elf(string range)
        {
            var sections = range.Split('-');

            Min = int.Parse(sections[0]);
            Max = int.Parse(sections[1]);

            Range = Enumerable.Range(Min, Max - Min + 1).ToList();
        }
    }
}