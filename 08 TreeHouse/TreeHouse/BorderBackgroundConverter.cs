using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Data;

namespace TreeHouse
{
    class BorderBackgroundConverter : IValueConverter
    {
        public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
        {
            return (int)value switch
            {
                0 => "#0b170f",
                1 => "#122919",
                2 => "#174024",
                3 => "#195d2e",
                4 => "#177835",
                5 => "#109c3b",
                6 => "#0bc042",
                7 => "#07da20",
                8 => "#00ff1e",
                9 => "#77ff53",

                _ => "#000000"
            };
        }

        public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
        {
            throw new NotImplementedException();
        }
    }
}
