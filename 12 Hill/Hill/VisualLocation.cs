﻿using CommonWPF;
using HillLib;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Numerics;
using System.Text;
using System.Threading.Tasks;

namespace Hill
{
    public class VisualLocation : ViewModelBase
    {
        private Location _location;

        public string Str => _location.Letter.ToString();

        public int Height => _location.Height;

        public bool BelongsToPath
        {
            get => GetValue<bool>();
            set => SetValue(value);
        }

        public int X => (int)_location.Position.X;
        public int Y => (int)_location.Position.Y;

        public VisualLocation(Location location)
        {
            _location = location;
        }
    }
}