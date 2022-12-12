using CommonWPF;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Data;

namespace Hill
{
    class BackgroundConverter : IValueConverter
    {
        private ColourGradient _colours = new ColourGradient();
        public BackgroundConverter()
        {
            _colours.AddToColourMappings(0, "#000000");
            _colours.AddToColourMappings(5, "#FF0000");
            _colours.AddToColourMappings(20, "#FFFF00");
            _colours.AddToColourMappings(25, "#FFFFFF");
        }

        public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
        {
            return _colours.GetColour((int)value);
        }

        public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
        {
            throw new NotImplementedException();
        }
    }
}
