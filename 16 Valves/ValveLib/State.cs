using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ValveLib
{
    public class State
    {
        public List<Valve> OpenedValves { get; set; } = new List<Valve>();

        public int Pressure { get; set; } = 0;

        public int RemainingMinutes { get; set; }

        public State() { }

        public State(State oldState) 
        {
            OpenedValves = new List<Valve>(oldState.OpenedValves);
            Pressure = oldState.Pressure;
            RemainingMinutes = oldState.RemainingMinutes;
        }
    }
}
