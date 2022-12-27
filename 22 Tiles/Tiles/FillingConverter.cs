using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Controls;
using System.Windows.Data;
using TilesLib;

namespace Tiles
{
    internal class FillingConverter : IValueConverter
    {
        public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
        {
            if (value is Filling fill)
            {
                if (fill == Filling.Wall) 
                {
                    return "#BBBBDD";
                }
                else if (fill == Filling.Path)
                {
                    return "#FFFFFF";
                }
            }
            // filling "off"
            return "#444444";
        }

        public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
        {
            throw new NotImplementedException();
        }
    }
}
