using CommonWPF;
using System.Numerics;
using TreeHouseLib;

namespace TreeHouse
{
    public class VisualTreePosition : ViewModelBase
    {
        private TreePosition _treePos;
        
        public int X { get; set; }
        public int Y { get; set; }

        public int Height =>_treePos.Height;
        public bool IsVisible => _treePos.IsVisible;
        public bool IsBestPosition => _treePos.IsBestPosition;

        public VisualTreePosition(Vector2 pos, TreePosition treePos)
        {
            _treePos = treePos;
            X = (int)pos.X;
            Y = (int)pos.Y;
        }

    }
}