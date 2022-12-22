using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GeodeLib
{
    internal class State
    {
        public int Minute { get; set; }

        // currently available resources
        public int[] Resources { get; set; } = new int[4];

        // production of new resources each day
        public int[] Production { get; set; } = new int[4];

        public List<Resource> Robots= new List<Resource>();

        public State() {}

        public State(State otherState)
        {
            Minute = otherState.Minute;
            otherState.Resources.CopyTo(Resources, 0);
            otherState.Production.CopyTo(Production, 0);

            Robots = new List<Resource>(otherState.Robots);
        }

        public void UpdateResources(int minutes)
        {
            for (int i = 0; i < 4; i++)
            {
                Resources[i] += minutes * Production[i];
            }
        }

    }
}
