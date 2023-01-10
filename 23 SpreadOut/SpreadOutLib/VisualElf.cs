﻿using CommonWPF;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SpreadOutLib
{
    public class VisualElf : ViewModelBase
    {
        private Elf _elf;

        public int PositionX => (int)_elf.Position.X;
        public int PositionY => (int)_elf.Position.Y;

        public VisualElf(Elf elf)
        {
            _elf = elf;
        }

        public void UpdateVisuals()
        {
            RaisePropertyChanged(nameof(PositionX));
            RaisePropertyChanged(nameof(PositionY));
        }

    }
}
