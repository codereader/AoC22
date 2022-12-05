﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CratesLib
{
    public class CrateOperator
    {
        private StackCollection _startConfig;
        public StackCollection StackColl { get; private set; } = new StackCollection();
        public List<Instruction> Instructions { get; private set; } = new List<Instruction>();



        public void Parse(List<string> input)
        {
            // first part of input
            // crate configuration

            // [V]         [T]         [J]        
            // [C] [H] [F] [Z] [G] [L] [V] [Z] [H]

            // line length is 3 * number of stacks + (3 * number of stacks - 1) * space in between = 4 * number of stacks - 1

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
                // crate letters are positioned at 1, 1 + 4, 1 + 4 + 4...
                for (int stackNum = 0; stackNum < stacksCount; stackNum++)
                {
                    var letterPos = startpos + 4 * stackNum;
                    var currentChar = line[letterPos];
                    if (char.IsLetter(currentChar))
                    {
                        // add at the front
                        listCollection[stackNum].Add(currentChar);
                    }
                    else if (Char.IsDigit(currentChar))
                    {
                        // we have reached the line with the stack numbers, create CrateStack object
                        StackColl.AddStack(new CrateStack(int.Parse(currentChar.ToString()), listCollection[stackNum]));
                    }
                    // else the position is empty
                }

            }
            _startConfig = new StackCollection(StackColl);

            // second part of input
            // moving instructions
            for (; i < input.Count; i++)
            {
                line = input[i];

                Instructions.Add(new Instruction(line));
            }
        }

        public void PerformAllInstructions()
        {
            foreach (var inst in Instructions)
            {
                PerformInstruction(inst);
            }
        }

        public void PerformInstruction(Instruction instruction)
        {
            var source = StackColl.Stacks.Single(s => s.Id == instruction.SourceStack);
            var destination = StackColl.Stacks.Single(s => s.Id == instruction.DestinationStack);

            for (int i = 0; i < instruction.NumberOfCrates; i++)
            {
                MoveCrate(source, destination);
            }
        }

        public void PerformAllInstructionsAdvanced()
        {
            foreach (var inst in Instructions)
            {
                PerformInstructionAdvanced(inst);
            }
        }

        public void PerformInstructionAdvanced(Instruction instruction)
        {
            // all crates need to be moved at the same time
            var source = StackColl.Stacks.Single(s => s.Id == instruction.SourceStack);
            var destination = StackColl.Stacks.Single(s => s.Id == instruction.DestinationStack);

            var cratesToBeMoved = source.Crates.Take(instruction.NumberOfCrates);
            destination.Crates.InsertRange(0, cratesToBeMoved);
            source.Crates.RemoveRange(0, instruction.NumberOfCrates);
        }



        private void MoveCrate(CrateStack source, CrateStack destination)
        {
            // Lists are sorted from top to bottom so we have to remove the first crate and add it at the front of the other list
            var moveCrate = source.Crates.First();
            destination.Crates.Insert(0, moveCrate);
            source.Crates.RemoveAt(0);
        }
    }
}
