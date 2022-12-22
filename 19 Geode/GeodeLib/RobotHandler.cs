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

        public int RunSimulation1()
        {

            foreach ( var bluePrint in _bluePrints)
            {
                var production = new Production(bluePrint, 24);
                production.StartProduction();
                bluePrint.CalculateQualityLevel();
            }

            return _bluePrints.Sum(b => b.QualityLevel);
        }

        // part 2
        public int RunSimulation2() 
        {
            var mult = 1;
            for (int i = 0; i < 3; i++)
            {

                var production = new Production(_bluePrints[i], 32);
                production.StartProduction();
                mult *= _bluePrints[i].BestResult;
            }
            return mult;
        }
    }
}