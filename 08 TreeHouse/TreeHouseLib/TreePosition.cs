using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TreeHouseLib
{
    internal class TreePosition
    {
        public int Height { get; internal set; }

        internal int MaxHeightXLeft { get; set; }
        internal int MaxHeightXRight { get; set; }
        internal int MaxHeightYTop { get; set; }
        internal int MaxHeightYBottom { get; set; }

        internal bool IsVisible { get; set; }


        internal int VisibleLeft { get; set; }
        internal int VisibleRight { get; set; }
        internal int VisibleTop { get; set; }
        internal int VisibleBottom { get; set; }
        internal int VisibleScore { get; set; }

    }
}
