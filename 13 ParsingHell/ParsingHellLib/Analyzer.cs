namespace ParsingHellLib
{
    public class Analyzer
    {
        private List<Pair> _pairCollection = new List<Pair>();

        private List<Package> _packageCollection = new List<Package>();

        private CompareElements _compareElements = new CompareElements();


        public void Parse(List<string> input)
        {
            var index = 1;
            var currentPair = new Pair();
            currentPair.Index = index;
            _pairCollection.Add(currentPair);

            foreach(var line in input)
            {
                if (string.IsNullOrEmpty(line))
                {
                    currentPair = new Pair();
                    currentPair.Index = ++index;
                    _pairCollection.Add(currentPair);
                }
                else if (currentPair.Left == null)
                {
                    currentPair.Left = new Package(line);
                    _packageCollection.Add(currentPair.Left);
                }
                else
                {
                    currentPair.Right = new Package(line);
                    _packageCollection.Add(currentPair.Right);
                }
            }
        }

        public int ComparePairs()
        {
            foreach (var pair in _pairCollection)
            {
                if (_compareElements.Compare(pair.Left.Contents, pair.Right.Contents) > 0)
                {
                    pair.RightOrder = true;
                }
                else
                {
                    pair.RightOrder = false;
                }
            }

            return _pairCollection.Where(p => p.RightOrder).Sum(p => p.Index);
        }



    }
}