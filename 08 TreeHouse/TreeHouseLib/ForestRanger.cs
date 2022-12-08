using System.Numerics;
using System.Security.Cryptography.X509Certificates;

namespace TreeHouseLib
{
    public class ForestRanger
    {
        private int _xMax;
        private int _yMax;

        private Dictionary<Vector2, TreePosition> _forestGrid = new Dictionary<Vector2, TreePosition>();

        public void Parse(List<string> input)
        {
            _yMax = input.Count;
            for (int y = 0; y < _yMax; y++)
            {
                var currentLine = input[y];
                _xMax = currentLine.Length;
                for (int x = 0; x < _xMax; x++)
                {
                    _forestGrid.Add(new Vector2(x, y), new TreePosition { Height = int.Parse(currentLine[x].ToString()) });
                }
            }
        }
        public void CalculateMaxHeights()
        {
            for (int y = 0; y < _yMax; y++)
            {
                // moving in from the left edge, the highest treee previously encountered
                var xLeftMax = 0;
                for (int x = 0; x < _xMax; x++)
                {
                    var posXL = new Vector2(x, y);
                    var height = _forestGrid[posXL].Height;
                    _forestGrid[posXL].MaxHeightXLeft = xLeftMax;
                    if (height > xLeftMax)
                    {
                        xLeftMax = height;
                    }
                }
                var xRightMax = 0;
                for (int x = _xMax - 1; x >= 0; x--)
                {
                    var posXR = new Vector2(x, y);
                    var height = _forestGrid[posXR].Height;
                    _forestGrid[posXR].MaxHeightXRight = xRightMax;
                    if (height > xRightMax)
                    {
                        xRightMax = height;
                    }
                }
            }
            for (int x = 0; x < _xMax; x++)
            {
                var yTopMax = 0;
                for (int y = 0; y < _yMax; y++)
                {
                    var posYT = new Vector2(x, y);
                    var height = _forestGrid[posYT].Height;
                    _forestGrid[posYT].MaxHeightYTop = yTopMax;
                    if (height > yTopMax)
                    {
                        yTopMax = height;
                    }
                }
                var yBottomMax = 0;
                for (int y = _yMax - 1; y >= 0; y--)
                {
                    var posYB = new Vector2(x, y);
                    var height = _forestGrid[posYB].Height;
                    _forestGrid[posYB].MaxHeightYBottom = yBottomMax;
                    if (height > yBottomMax)
                    {
                        yBottomMax = height;
                    }
                }
            }
        }

        public void DetermineVisibilities()
        {
            for (int x = 0; x < _xMax; x++)
            {
                for (int y = 0; y < _yMax; y++)
                {
                    var pos = new Vector2(x, y);
                    var treePos = _forestGrid[pos];

                    // edge trees are always visible
                    if (x == 0 || x == _xMax - 1 || y == 0 || y == _yMax - 1)
                    {
                        _forestGrid[pos].IsVisible = true;
                    }
                    // height is larger than the previous trees from any direction
                    else if (treePos.Height > treePos.MaxHeightXLeft 
                        || treePos.Height > treePos.MaxHeightXRight 
                        || treePos.Height > treePos.MaxHeightYTop 
                        || treePos.Height > treePos.MaxHeightYBottom)
                    {
                        treePos.IsVisible = true;
                    }
                    else
                    {
                        treePos.IsVisible = false;
                    }
                }
            }
        }

        public int GetVisibleTreeCount()
        {
            return _forestGrid.Count(t => t.Value.IsVisible);
        }


        public void DetermineVisibleTreeCounts()
        {
            for (int currentX = 0; currentX < _xMax; currentX++)
            {
                for (int currentY = 0; currentY < _yMax; currentY++)
                {
                    var pos = new Vector2(currentX, currentY);
                    var treePos = _forestGrid[pos];
                    var currentHeight = treePos.Height;

                    // move in all directions as long as the other trees are smaller
                    for (int x = currentX - 1; x >= 0; x--)
                    {
                        var testTreePos = _forestGrid[new Vector2(x, currentY)];
                        treePos.VisibleLeft++;
                        if (testTreePos.Height >= currentHeight)
                        {
                            break;
                        }
                    }
                    for (int x = currentX + 1; x  < _xMax; x++)
                    {
                        var testTreePos = _forestGrid[new Vector2(x, currentY)];
                        treePos.VisibleRight++;
                        if (testTreePos.Height >= currentHeight)
                        {
                            break;
                        }
                    }
                    for (int y = currentY - 1; y >= 0; y--)
                    {
                        var testTreePos = _forestGrid[new Vector2(currentX, y)];
                        treePos.VisibleTop++;
                        if (testTreePos.Height >= currentHeight)
                        {
                            break;
                        }
                    }
                    for (int y = currentY + 1; y< _yMax; y++)
                    {
                        var testTreePos = _forestGrid[new Vector2(currentX, y)];
                        treePos.VisibleBottom++;
                        if (testTreePos.Height >= currentHeight)
                        {
                            break;
                        }
                    }
                    treePos.VisibleScore = treePos.VisibleTop * treePos.VisibleBottom * treePos.VisibleLeft * treePos.VisibleRight;
                }
            }
        }

        public int GetMaxScore()
        {
            return _forestGrid.Max(t => t.Value.VisibleScore);
        }

        public int XMax => _xMax;
        public int YMax => _yMax;

    }
}