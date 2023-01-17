namespace SnafuLib
{
    public class SnafuHandler
    {
        private List<Number> _numbers = new List<Number>();
        public void Parse(List<string> input)
        {
            foreach (var line in input)
            {
                _numbers.Add(new Number(line));
            }
        }

        public string AddNumbers()
        {
            var sum = _numbers.Sum(n => n.DecimalNumber);

            Console.WriteLine(sum);

            var resultDigits = new List<char>();

            while (sum > 0) 
            {
                var lastDigit = sum % 5;
                sum = sum / 5;

                (var resultSnafuDigit, sum) = ConvertToSnafu(lastDigit, sum);

                resultDigits.Add(resultSnafuDigit);

            }

            resultDigits.Reverse();

            return string.Join("", resultDigits);



                   
        }

        private (char resultSnafuDigit, long overFlow) ConvertToSnafu(long lastDigit, long overFlow)
        {
            var result = new char();

            switch (lastDigit)
            {
                case 0:
                    result = '0';
                    break;

                case 1:
                    result = '1';
                    break;

                case 2:
                    result = '2';
                    break;

                case 3:
                    // 5 - 2
                    result = '=';
                    overFlow++;
                    break;

                case 4:
                    // 5 - 1
                    result = '-';
                    overFlow++;
                    break;

            }

            return (result, overFlow);
        }
    }
}