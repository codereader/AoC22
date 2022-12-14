using CommonWPF;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SandLib
{
    public class VisualLocation : ViewModelBase
    {
        public int X
        {
            get => GetValue<int>();
            set => SetValue(value);
        }
        public int Y
        {
            get => GetValue<int>();
            set => SetValue(value);
        }

        public int Filling
        {
            get => GetValue<int>();
            set => SetValue(value);
        }

    }
}
