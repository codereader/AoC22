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

        public Dictionary<Resource, int[]> Cost { get; set; } = new Dictionary<Resource, int[]>();

        public int[] MaxProduction = new int[4];


        public int BestResult { get; set; }
        public int QualityLevel { get; set; }

        public BluePrint(string line)
        {
            var parts = line.Split(' ');

            // remove ":"
            var idStr = parts[1].Split(':');
            Id = int.Parse(idStr[0]);

            foreach (var resource in (Resource[])Enum.GetValues(typeof(Resource)))
            {
                Cost.Add(resource, new int[] { 0, 0, 0, 0 });
            }

            Cost[Resource.Ore][(int)Resource.Ore] = int.Parse(parts[6]);
            Cost[Resource.Clay][(int)Resource.Ore] = int.Parse(parts[12]);
            Cost[Resource.Obsidian][(int)Resource.Ore] = int.Parse(parts[18]);
            Cost[Resource.Obsidian][(int)Resource.Clay] = int.Parse(parts[21]);
            Cost[Resource.Geode][(int)Resource.Ore] = int.Parse(parts[27]);
            Cost[Resource.Geode][(int)Resource.Obsidian] = int.Parse(parts[30]);

            // don't make more robots than needed to make a new one every minute
            for (int i = 0; i < 4; i++)
            {
                MaxProduction[i] = Cost.Values.Max(r => r[i]);
            }
        }

        public void CalculateQualityLevel()
        {
            QualityLevel = Id * BestResult;
        }


    }
}
