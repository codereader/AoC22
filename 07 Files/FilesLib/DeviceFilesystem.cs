using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace FilesLib
{
    public class DeviceFilesystem
    {
        private DeviceDirectory _root = new DeviceDirectory();
        private List<DeviceDirectory> _directories = new List<DeviceDirectory>();

        private int _totalSpace = 70000000;
        private int _requiredFreeSpace = 30000000;

        public void Parse(List<string> input)
        {
            _root.Name = @"/";
            _directories.Add(_root);

            var currentDir = _root;

            foreach (var line in input)
            {
                // command lines start with $
                if (line.StartsWith("$"))
                {
                    // change directory
                    //$ cd /
                    if (line.Substring(2, 2) == "cd")
                    {
                        var newdirstr = line.Substring(5);
                        if (newdirstr == "..")
                        {
                            // move out one directory
                            currentDir = currentDir.Parent;
                        }
                        else if (newdirstr == @"/")
                        {
                            currentDir = _root;
                        }
                        else
                        {
                            currentDir = currentDir.Children.Single(c => c.Name == newdirstr);
                        }
                    }

                    // ls means list files and directories inside the current directory

                }
                else if (line.Substring(0, 3) == "dir")
                {
                    // listing a directory
                    //dir a
                    var newDir = new DeviceDirectory();
                    newDir.Name= line.Substring(4);
                    newDir.Parent = currentDir;
                    currentDir.Children.Add(newDir);
                    _directories.Add(newDir);
                }
                else
                {
                    // listing a file
                    //14848514 b.txt
                    var expression = @"(\d+)\s(.+)";
                    var match = Regex.Match(line, expression);

                    var newFile = new DeviceFile();
                    newFile.Size = int.Parse(match.Groups[1].Value);
                    newFile.Name = match.Groups[2].Value;
                    currentDir.Files.Add(newFile);
                }
            }
        }

        public void CalculateOverallSizes()
        {
            _root.CalculateOverallSize();
        }

        // part 1: sum of folders with overall size <= 100000
        public int GetSumOfSmallFolders()
        {
            var smallFolders = _directories.Where(d => d.OverallSize <= 100000);
            var sum = smallFolders.Sum(f => f.OverallSize);
            return sum;
        }

        public int DetermineSizeOfDirToDelete()
        {
            var freeSpace = _totalSpace - _root.OverallSize;
            var freeSpaceDifference = _requiredFreeSpace - freeSpace;

            // the smallest folder increasing free space over required free space
            var applicableFolders = _directories.Where(d => d.OverallSize >= freeSpaceDifference);
            return applicableFolders.Min(f => f.OverallSize);
        }

    }
}
