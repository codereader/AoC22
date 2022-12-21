namespace GeodeLib
{
    public class RobotHandler
    {
        private List<BluePrint> _bluePrints = new List<BluePrint>();
        public void Parse(List<string> input)
        {
            // Blueprint 2: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 2 ore and 18 clay. Each geode robot costs 4 ore and 20 obsidian.

            foreach ( var line in input)
            {
                var currentBluePrint = new BluePrint(line);
                _bluePrints.Add(currentBluePrint);
            }
        }

        public void RunSimulation()
        {
            var production = new Production(_bluePrints[1]);
            production.StartProduction();
        }
    }
}