using System;
using System.Collections.Generic;
using System.Linq;
using System.Numerics;
using System.Text;
using System.Threading.Tasks;

namespace TilesLib
{
    internal class Connection
    {
        public CubeFace TargetFace { get; set; }

        public Direction TargetOrientation { get; set; }

        public State TransformState(State oldState)
        {
            return new State 
            { 
                Orientation = TargetOrientation,
                Position = TransformPosition(oldState.Position)
            };
        }

        public Func<Vector2, Vector2> TransformPosition { get; set; }
    }
}
