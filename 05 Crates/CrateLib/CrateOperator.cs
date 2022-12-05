using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CrateLib
{
    public class CrateOperator
    {
        private StackCollection _stackCollection = new StackCollection();


        public void Parse(List<string> input)
        {
            // first part of input
            // crate configuration

            // [V]         [T]         [J]        
            // ...
            // [C] [H] [F] [Z] [G] [L] [V] [Z] [H]

            // line length is 3 * number of stacks + (3 * number of stacks - 1) space in between = 4 * number of stacks - 1

            int i = 0;
            var line = input[i];
            var strLength = line.Length;
            var stacksCount = (strLength + 1) / 4;

            // create empty lists
            var listCollection = new List<List<char>>();
            for (int s = 0; s < stacksCount; s++)
            {
                listCollection.Add(new List<char>());
            }

            for (; i < input.Count; i++)
            {
                line = input[i];

                if (string.IsNullOrEmpty(line))
                {
                    // empty line, first part of input is over
                    i++;
                    break;
                }

                var startpos = 1;
                // crate letters are positioned at 1, 1 + 3, 1 + 3 + 3...
                for (int stackNum = 0; stackNum < stacksCount; stackNum++)
                {
                    var letterPos = startpos + 3 * stackNum;
                    if (char.IsLetter(line[letterPos]))
                    {
                        // add at the front
                        listCollection[stackNum].Insert(0, line[letterPos]);
                    }
                    else if (Char.IsDigit(line[letterPos]))
                    {
                        // we have reached the line with the stack numbers, create CrateStack object
                        _stackCollection.AddStack(new CrateStack
                        {
                            Id = line[letterPos],
                            Crates = listCollection[stackNum]
                        });
                    }
                    // else the position is empty
                }

            }

            // second part of input
            // moving instructions
            for (; i < input.Count; i++)
            {

            }



        }
    }
}
