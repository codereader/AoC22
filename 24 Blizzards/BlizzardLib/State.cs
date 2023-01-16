using System;
using System.Collections.Generic;
using System.Linq;
using System.Numerics;
using System.Text;
using System.Threading.Tasks;

namespace BlizzardLib
{
    internal class State
    {
        public Vector2 CurrentPosition { get; set; }

        // (round, position)
        public Dictionary<int,Vector2> Positions { get; set; } = new Dictionary<int,Vector2>();

        public State() {}

        public State(State oldState)
        {
            CurrentPosition = oldState.CurrentPosition;
            Positions = new Dictionary<int, Vector2>(oldState.Positions);
        }
    }
}
