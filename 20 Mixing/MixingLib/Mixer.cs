using System.Collections.Generic;

namespace MixingLib
{
    public class Mixer
    {
        private List<LinkedListNode<long>> _numbers = new List<LinkedListNode<long>>();

        private LinkedList<long> _chain = new LinkedList<long>();
        public void Parse(List<string> input)
        {
            for (int i = 0; i < input.Count; i++)
            {
                var node = _chain.AddLast(int.Parse(input[i]));
                _numbers.Add(node);
            }
        }

        public void Mix()
        {
            for (int i = 0; i < _numbers.Count; i++)
            {
                var currentNode = _numbers[i];
                var currentNum = currentNode.Value;

                // might wrap around to original position
                var move = Math.Abs(currentNum) % (_numbers.Count - 1);

                if (currentNum < 0)
                {
                    // move to the left
                    var targetNode = FindPreviousNode(currentNode);
                    _chain.Remove(currentNode);

                    for (int j = 0; j < move; j++)
                    {
                        targetNode = FindPreviousNode(targetNode);
                    }
                    _chain.AddAfter(targetNode, currentNode);
                }
                else
                {
                    // move to the right
                    var targetNode = FindNextNode(currentNode);
                    _chain.Remove(currentNode);

                    for (int j = 0; j < move; j++)
                    {
                        targetNode = FindNextNode(targetNode);
                    }
                    _chain.AddBefore(targetNode, currentNode);
                }
            }
        }


        private LinkedListNode<long> FindPreviousNode(LinkedListNode<long> currentNode)
        {
            var prev = currentNode.Previous;

            if (prev == null)
            {
                prev = _chain.Last;
            }
            return prev;
        }

        private LinkedListNode<long> FindNextNode(LinkedListNode<long> currentNode)
        {
            var next = currentNode.Next;

            if (next == null)
            {
                next = _chain.First;
            }
            return next;
        }

        public long GetNumAtPosition(long position)
        {
            var targetNode = _numbers.First(n => n.Value == 0);

            var move = position % _numbers.Count;

            for (int i = 0; i < move; i++)
            {
                targetNode = FindNextNode(targetNode);
            }
            return targetNode.Value;
        }


    }
}