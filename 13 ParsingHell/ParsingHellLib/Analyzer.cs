using System.Collections;
using System.Collections.Immutable;

namespace ParsingHellLib
{
    public class Analyzer
    {
        private List<Pair> _pairCollection = new List<Pair>();

        private List<Package> _packageCollection = new List<Package>();

        private ElementComparer _compareElements = new ElementComparer();


        public void Parse(List<string> input)
        {
            var index = 1;
            var currentPair = new Pair();
            currentPair.Index = index;
            _pairCollection.Add(currentPair);

            foreach (var line in input)
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


        public int SortPackages()
        {
            _packageCollection.Add(new Package("[[2]]"));
            _packageCollection.Add(new Package("[[6]]"));

            ElementComparer compareElements = new ElementComparer();
            _packageCollection.Sort(new PackageComparer());
            _packageCollection.Reverse();

            foreach (var package in _packageCollection)
            {
                Console.WriteLine(package.Line);
            }
            return (_packageCollection.FindIndex(p => p.Line == "[[2]]") + 1) *
                     (_packageCollection.FindIndex(p => p.Line == "[[6]]") + 1);
        }

    }
}