namespace ValveLib
{
    public class Valve
    {
        private string _line;
        internal List<string> ConnectedValveStrings { get; set; } = new List<string>();

        internal List<Valve> ConnectedValves { get; set; } = new List<Valve>();

        internal Dictionary<Valve, int> ShortestPaths { get; set; } = new Dictionary<Valve, int>();

        public Valve(string line)
        {
            _line = line;

            //Valve UF has flow rate=18; tunnels lead to valves QQ, AN, YE, GY
            var parts = line.Split(" ");

            Name = parts[1];

            var flowstr = parts[4].Split("=");
            var flownum = flowstr[1].Split(";");
            FlowRate = int.Parse(flownum[0]);

            for (int i = 9; i < parts.Length; i++)
            {
                var neighborstr = parts[i].Split(",");
                ConnectedValveStrings.Add(neighborstr[0]);
            }
        }

        public string Name { get; set; }

        public int FlowRate { get; set; }

    }
}
