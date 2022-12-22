using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GeodeLib
{
    internal class BluePrint
    {
        // Blueprint 2: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 2 ore and 18 clay. Each geode robot costs 4 ore and 20 obsidian.

        public int Id { get; set; }

        public int OreRobotCostOre { get; set; }
        public int ClayRobotCostOre { get; set; }
        public int ObsidianRobotCostOre { get; set; }
        public int ObsidianRobotCostClay { get; set; }
        public int GeodeRobotCostOre { get; set; }
        public int GeodeRobotCostObsidian { get; set; }

        public int BestResult { get; set; }
        public int QualityLevel { get; set; }

        public BluePrint(string line)
        {
            var parts = line.Split(' ');

            // remove ":"
            var idStr = parts[1].Split(':');
            Id = int.Parse(idStr[0]);

            OreRobotCostOre = int.Parse(parts[6]);
            ClayRobotCostOre = int.Parse(parts[12]);
            ObsidianRobotCostOre = int.Parse(parts[18]);
            ObsidianRobotCostClay = int.Parse(parts[21]);
            GeodeRobotCostOre = int.Parse(parts[27]);
            GeodeRobotCostObsidian = int.Parse(parts[30]);
        }

        public void CalculateQualityLevel()
        {
            QualityLevel = Id * BestResult;
        }


    }
}
