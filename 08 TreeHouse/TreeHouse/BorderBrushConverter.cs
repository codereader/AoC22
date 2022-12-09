using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Data;

namespace TreeHouse
{
    class BorderBrushConverter : IValueConverter
    {
        public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
        {
            if (value is VisualTreePosition treePos)
            {
                if (treePos.IsBestPosition)
                {
                    return "#0000FF";
                }
                else if (treePos.IsVisible)
                {
                    return "#DDDDDD";
                }
            }

            return "#111111";
        }

        public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
        {
            throw new NotImplementedException();
        }
    }
}
