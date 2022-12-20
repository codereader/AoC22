using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TetrisLib
{
    internal class Configuration
    {
        public long RockId { get; set; }
        public int CurrentJetPos { get; set; }

        public long TowerHeight { get; set; }

        public long RockCount { get; set; }

        public List<int> FallenRockConfig { get; set; } = new List<int>();

        public void CreateFallenRockConfig (List<bool[]> fallenRocks)
        {
            foreach (var line in fallenRocks) 
            {
                var num = 0;
                for (int i = 0; i < line.Length; i++)
                {
                    num += ((int)Math.Pow(2, (i))) * (line[i] == true ? 1 : 0);
                }
                FallenRockConfig.Add(num);
            }
        }

        public override bool Equals(object? obj)
        {
            if (obj is Configuration conf)
            {
                // same rock shape
                if (RockId != conf.RockId)
                {
                    return false;
                }
                // same jet stream
                if (CurrentJetPos != conf.CurrentJetPos)
                {
                    return false;
                }
                // same fallen rock configuration
                if (FallenRockConfig.Count != conf.FallenRockConfig.Count)
                {
                    return false;
                }

                for (int i = 0; i < FallenRockConfig.Count; i++)
                {
                    if (FallenRockConfig[i] != conf.FallenRockConfig[i])
                    {
                        return false;
                    }
                }
                return true;


            }
            return false;

        }
    }
}
