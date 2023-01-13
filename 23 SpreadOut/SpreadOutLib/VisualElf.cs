using CommonWPF;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Media;

namespace SpreadOutLib
{
    public class VisualElf : ViewModelBase, IGridItem
    {
        private Elf _elf;

        private ColourGradient _colours = new ColourGradient();

        public int PositionX => (int)_elf.Position.X + 50;
        public int PositionY => (int)_elf.Position.Y + 50;

        public Brush BackGroundColor => new SolidColorBrush((Color)ColorConverter.ConvertFromString(_colours.GetColour(RoundsSinceLastMove / (double)Elf.MaxRoundsSinceLastMove)));
        public int RoundsSinceLastMove => _elf.RoundsSinceLastMove;

        public VisualElf(Elf elf)
        {
            _elf = elf;

            _colours.AddToColourMappings(0, "#FFFF00");
            _colours.AddToColourMappings(0.4, "#FF0000");
            _colours.AddToColourMappings(1, "#000000");
        }

        public void UpdateVisuals()
        {
            RaisePropertyChanged(nameof(PositionX));
            RaisePropertyChanged(nameof(PositionY));
            RaisePropertyChanged(nameof(BackGroundColor));
        }

    }
}
