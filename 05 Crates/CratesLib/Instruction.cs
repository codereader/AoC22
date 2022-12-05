using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace CratesLib
{
    public class Instruction
    {
        internal int NumberOfCrates { get; set; }
        internal int SourceStack { get; set; }
        internal int DestinationStack { get; set; }
        public string InstructionStr { get; private set; }

        public Instruction(string line)
        {
            InstructionStr = line;

            // move 10 from 7 to 6
            var expression = @"move (\d+) from (\d+) to (\d+)";
            var match = Regex.Match(line, expression);

            NumberOfCrates = int.Parse(match.Groups[1].Value);
            SourceStack = int.Parse(match.Groups[2].Value);
            DestinationStack = int.Parse(match.Groups[3].Value);
        }
    }
}
