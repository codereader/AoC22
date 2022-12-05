using Common;
using CommonWPF;
using CratesLib;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.DirectoryServices.ActiveDirectory;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace Crates
{
    class MainViewModel : ViewModelBase
    {
        private CrateOperator _crateOperator = new CrateOperator();

        public VisualStackCollection VStackCollection { get; set; } = new VisualStackCollection();


        public MainViewModel()
        {
            // Parse
            var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), $"Crates.input.txt");
            _crateOperator.Parse(input);

            UpdateVisuals();

            RunAll = new RelayCommand(CanRunAll, DoRunAll);
            RunAllAdvanced = new RelayCommand(CanRunAllAdvanced, DoRunAllAdvanced);
            Reset = new RelayCommand(CanReset, DoReset);

        }

        public ICommand RunAll { get; }
        public bool CanRunAll()
        {
            return true;
        }
        public void DoRunAll()
        {
            _crateOperator.PerformAllInstructions();
            UpdateVisuals();
        }

        public ICommand RunAllAdvanced { get; }
        public bool CanRunAllAdvanced()
        {
            return true;
        }
        public void DoRunAllAdvanced()
        {
            _crateOperator.PerformAllInstructionsAdvanced();
            UpdateVisuals();
        }

        public ICommand Reset { get; }
        public bool CanReset()
        {
            return true;
        }
        public void DoReset()
        {
            _crateOperator.Reset();
            UpdateVisuals();
        }


        private void UpdateVisuals()
        {
            VStackCollection.UpdateVisuals(_crateOperator.StackColl);
        }
    }
}
