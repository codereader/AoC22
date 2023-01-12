using CommonWPF;
using SpreadOutLib;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Data;

namespace SpreadOut
{
    class ElfColorConverter : IValueConverter
    {
        private ColourGradient _colours = new ColourGradient();

        public ElfColorConverter()
        {
            _colours.AddToColourMappings(0, "#FFFF00");
            _colours.AddToColourMappings(0.4, "#FF0000");
            _colours.AddToColourMappings(1, "#000000");
        }

        public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
        {
            return _colours.GetColour((int)value / (double)Elf.MaxRoundsSinceLastMove);
        }

        public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
        {
            throw new NotImplementedException();
        }
    }
}
