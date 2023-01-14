using BlizzardLib;
using CommonWPF;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Media;
using System.Windows.Navigation;

namespace Blizzards
{
    internal class VisualLocation : IGridItem
    {
        private Location _location;
        public int PositionX => (int)_location.Position.X;

        public int PositionY => (int)_location.Position.Y;

        public Brush BackGroundColor
        {
            get
            {
                return Fill switch
                {
                    Filling.Wall => new SolidColorBrush(Colors.DarkGray),
                    Filling.Blizzard => new SolidColorBrush(Colors.Azure),
                    Filling.Clear => new SolidColorBrush(Colors.White),
                    Filling.Player => new SolidColorBrush(Colors.Red),
                    _ => new SolidColorBrush(Colors.White),
                };
            }
        }

        public Filling Fill => _location.Fill;

        public VisualLocation(Location location)
        {
            _location = location;
        }
    }
}
