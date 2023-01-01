using CommonWPF;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TilesLib;

namespace Tiles
{
    public class VisualLocation : ViewModelBase
    {
        private Location _location;

        public bool IsCurrentLocation
        {
            get => GetValue<bool>();
            set => SetValue(value);
        }

        public int PositionX => _location.PositionX;
        public int PositionY => _location.PositionY;
        public Filling Fill => _location.Fill;

        public VisualLocation(Location location)
        {
            _location = location;
            IsCurrentLocation = false;
        }
    }
}
