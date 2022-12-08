using CommonWPF;

namespace TreeHouse
{
    public class VisualTreePosition : ViewModelBase
    {
        public int Height
        {
            get => GetValue<int>();
            set => SetValue(value);
        }

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

        public bool Isvisible
        {
            get => GetValue<bool>();
            set => SetValue(value);
        }



    }
}