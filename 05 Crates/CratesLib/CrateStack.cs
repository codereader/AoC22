using System.Collections.Generic;
using System.Collections.ObjectModel;
using CommonWPF;
using Microsoft.Win32.SafeHandles;

namespace CratesLib
{
    public class CrateStack
    {
        public int Id { get; set; }

        public List<char> Crates { get; set; }

        public CrateStack(int id, List<char> crates)
        {
            Id= id;
            Crates = crates;
        }   
    }
}