using CommonWPF;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TreeHouse
{
    class VisualForest : ViewModelBase
    {
        public ObservableCollection<VisualTreePosition> TreePositions { get; set; } = new ObservableCollection<VisualTreePosition>();
    }
}
