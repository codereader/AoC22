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

        public int x
        {
            get => GetValue<int>();
            set => SetValue(value);
        }
        public int y
        {
            get => GetValue<int>();
            set => SetValue(value);
        }

        public bool Isvisble
        {
            get => GetValue<bool>();
            set => SetValue(value);
        }



    }
}