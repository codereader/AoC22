using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ParsingHellLib
{
    internal class Pair
    {
        public int Index { get; set; }
        public Package? Left { get; set; }
        public Package? Right { get; set; }
        public bool RightOrder { get; internal set; }

        internal bool Compare()
        {
            return CompareElements(Left.Contents, Right.Contents) == Result.ResultTrue;
        }

        private Result CompareElements(Element left, Element right)
        {
            if (left is NumberElement leftNum)
            {
                if (right is NumberElement rightNum)
                {
                    if (leftNum.Number < rightNum.Number)
                    {
                        return Result.ResultTrue;
                    }
                    else if (leftNum.Number > rightNum.Number)
                    {
                        return Result.ResultFalse;
                    }
                    else
                    {
                        return Result.ResultEqual;
                    }
                }
                else if (right is ListElement rightList)
                {
                    var newLeftList = new ListElement();
                    newLeftList.Contents.Add(leftNum);
                    return CompareElements(newLeftList, rightList);
                }
            }
            else if (left is ListElement leftList)
            {
                if (right is NumberElement rightNum)
                {
                    var newRightList = new ListElement();
                    newRightList.Contents.Add(rightNum);
                    return CompareElements(leftList, newRightList);
                }
                else if (right is ListElement rightList)
                {
                    var leftCount = leftList.Contents.Count;
                    var rightCount = rightList.Contents.Count;

                    for (int i = 0; i < Math.Min(leftCount, rightCount); i++)
                    {
                        var result = CompareElements(leftList.Contents[i], rightList.Contents[i]);
                        if (result != Result.ResultEqual)
                        {
                            return result;
                        }
                    }
                    if (leftCount < rightCount)
                    {
                        return Result.ResultTrue;
                    }
                    else if (leftCount > rightCount)
                    {
                        return Result.ResultFalse;
                    }
                    else
                    {
                        return Result.ResultEqual;
                    }

                }
            }

            throw new InvalidOperationException();

        }
    }
}
