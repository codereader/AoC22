namespace FilesLib
{
    public class DeviceDirectory
    {
        public string Name { get; internal set; }

        public List<DeviceFile> Files { get; internal set; } = new List<DeviceFile>();

        public DeviceDirectory Parent { get; internal set; }
        public List<DeviceDirectory> Children { get; internal set; } = new List<DeviceDirectory>();

        // size of files in this directory
        private int _size;

        // size of all files in this directory including subfolders
        public int OverallSize { get; internal set; } 

        internal int CalculateOverallSize()
        {
            if (OverallSize == 0)
            {
                // has (probably) not been calculated yet
                _size = Files.Sum(f => f.Size);
                OverallSize = _size;

                foreach (var child in Children)
                {
                    OverallSize += child.CalculateOverallSize();
                }
            }
            // if the overall size is not 0 it has already been caluclated so we just have to return it
            return OverallSize;
        }
    }
}