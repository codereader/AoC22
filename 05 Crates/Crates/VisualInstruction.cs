using CommonWPF;
using CratesLib;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Crates
{

    public class VisualInstruction : ViewModelBase
    {
        public VisualInstruction(Instruction instruction)
        {
            InstructionStr = instruction.InstructionStr;
        }

        public string InstructionStr
        {
            get => GetValue<string>();
            set => SetValue(value);
        }

    }
}
