using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MonkeyLib
{
    internal class Operation
    {
        private List<string> _operations;

        public Operation(string opstr)
        {
            //  new = old * 13
            var parts = opstr.Split("=");
            var operationparts = parts[1].Split(" ", StringSplitOptions.RemoveEmptyEntries);

            _operations = operationparts.Select(p => p.Trim()).ToList();
        }

        public long Perform(long input)
        {
            long secondnumber;
            if (_operations[2] == "old")
            {
                secondnumber = input;
            }
            else
            {
                secondnumber = int.Parse(_operations[2]);
            }

            if (_operations[1] == "+")
            {
                return input + secondnumber;
            }
            else
            {
                return input * secondnumber;
            }
        }

    }
}
