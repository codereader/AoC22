using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ParsingHellLib
{
    internal class Package
    {
        public string Line { get; set; }

        public ListElement Contents { get; private set; } = new ListElement();

        public Package(string line)
        {
            Line = line;
            Parse(line);
        }

        //[[7,[10,[1,8,0,4],7],8],[[1,[0,1],[3,2,1],5,[1,5,3,7,8]],3,7,[[],[10],[0],[9,8,3,2]],[]]]
        private void Parse(string line)
        {
            ListElement currentElement = Contents;

            // the first "[" is the package list element
            var currentPos = 1;
            while(currentPos < line.Length - 1) 
            {
                var currentChar = line[currentPos];

                if (currentChar == '[')
                {
                    // create new list element
                    var newElement = new ListElement();
                    newElement.Parent = currentElement;
                    currentElement.Contents.Add(newElement);
                    // move in to the child list element
                    currentElement = newElement;
                    currentPos++;
                }
                else if (currentChar == ']')
                {
                    currentElement = currentElement.Parent;
                    currentPos++;

                }
                else if (char.IsNumber(currentChar))
                {
                    // number, might have more than one digit
                    var testStr = line.Substring(currentPos);
                    // find first "," or "]" after the integer
                    var nextOtherChar = testStr.First(c => c == ',' || c == ']');
                    var nextPos = testStr.IndexOf(nextOtherChar);
                    var num = testStr.Substring(0, nextPos);
                    var numelement = new NumberElement(int.Parse(num));
                    numelement.Parent = currentElement;
                    currentElement.Contents.Add(numelement);
                    currentPos = currentPos + nextPos;
                }
                // on "," we just move on to the next element in the list
                else
                {
                    currentPos++;
                }
            }
        }
    }
}
