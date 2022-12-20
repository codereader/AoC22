namespace ValveLib
{
    public class ValveHandler
    {
        public void Parse(List<string> input)
        {
            for (int i = 0; i < input.Count; i++)
            {
                var line = input[i];
                //Valve UF has flow rate=18; tunnels lead to valves QQ, AN, YE, GY
                var parts = line.Split(" ");


            }
        }