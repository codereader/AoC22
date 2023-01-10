using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using System.Text;

namespace CommonWPF
{
    public class ViewModelBase : INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler PropertyChanged;
        private void OnPropertyChanged(string propertyName)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }

        private Dictionary<string, object> _values = new Dictionary<string, object>();

        protected T GetValue<T>([CallerMemberName]string propertyName = null)
        {
            if (_values.TryGetValue(propertyName, out var value))
            {
                return (T)value;
            }
            return default(T);
        }

        protected void SetValue<T>(T value, [CallerMemberName]string propertyName = null)
        {
            SetValue(value, () => { }, propertyName);
        }
        protected void SetValue<T>(T value, Action changedCallback, [CallerMemberName] string propertyName = null)
        {
            if (!_values.TryGetValue(propertyName, out var oldValue) || !Equals(oldValue, value))
            {
                _values[propertyName] = value;
                OnPropertyChanged(propertyName);
                changedCallback();
            }

        }

        protected void RaisePropertyChanged(string propertyName)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }


    }
}
