using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TreeHouseLib
{
    public class TreePosition
    {
        public int Height { get; internal set; }

        internal int MaxHeightXLeft { get; set; }
        internal int MaxHeightXRight { get; set; }
        internal int MaxHeightYTop { get; set; }
        internal int MaxHeightYBottom { get; set; }

        public bool IsVisible { get; internal set; }


        internal int VisibleLeft { get; set; }
        internal int VisibleRight { get; set; }
        internal int VisibleTop { get; set; }
        internal int VisibleBottom { get; set; }
        public int VisibleScore { get; internal set; }

        public bool IsBestPosition { get; internal set; }

    }
}
