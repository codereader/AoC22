namespace ParsingHellLib
{
    public class Analyzer
    {
        private List<Pair> _collection = new List<Pair>();


        public void Parse(List<string> input)
        {
            var index = 1;
            var currentPair = new Pair();
            currentPair.Index = index;
            _collection.Add(currentPair);

            foreach(var line in input)
            {
                if (string.IsNullOrEmpty(line))
                {
                    currentPair = new Pair();
                    currentPair.Index = ++index;
                    _collection.Add(currentPair);
                }
                else if (currentPair.Left == null)
                {
                    currentPair.Left = new Package(line);
                }
                else
                {
                    currentPair.Right = new Package(line);
                }
            }
        }

        public int Compare()
        {
            foreach (var pair in _collection)
            {
                if (pair.Compare())
                {
                    pair.RightOrder = true;
                }
                else
                {
                    pair.RightOrder = false;
                }
            }

            return _collection.Where(p => p.RightOrder).Sum(p => p.Index);
        }

    }
}