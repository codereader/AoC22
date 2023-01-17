using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SnafuLib
{
    internal class Number
    {
        private string _line;

        private List<char> _snafuDigits = new List<char>();

        // converted to int and reversed
        public List<long> Digits { get; set; } = new List<long>();

        public long DecimalNumber { get; set; }

        public Number(string line)
        {
            _line = line;

            foreach(var c in line)
            {
                _snafuDigits.Add(c);
            }
            CreateTransformedNumber();
            CreateDecimalNumber();
        }

        public void CreateTransformedNumber()
        {
            Digits = _snafuDigits.Select(d => GetDigitNumber(d)).ToList();
            Digits.Reverse();
        }

        public void CreateDecimalNumber()
        {
            long sum = 0;
            for (int i = 0; i < Digits.Count; i++)
            {
                sum += Digits[i] * (long)Math.Pow(5, i);
            }
            DecimalNumber = sum;
        }

        private long GetDigitNumber(char d)
        {
            return d switch
            {
                '0' => 0,
                '1' => 1,
                '2' => 2,
                '-' => -1,
                '=' => -2,
                _ => throw new IndexOutOfRangeException()
            };
        }
    }
}
