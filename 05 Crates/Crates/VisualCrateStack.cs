using CommonWPF;
using System;
using System.CodeDom;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Crates
{
    public class VisualCrateStack : ViewModelBase
    {

        public int Id
        {
            get => GetValue<int>();
            set => SetValue(value);
        }

        public ObservableCollection<char> Crates { get; set; } = new ObservableCollection<char>();

        public VisualCrateStack(int id, List<char> crates)
        {
            Id= id;
            foreach (var item in crates) 
            {
                Crates.Add(item);
            }
        }

    }
}
