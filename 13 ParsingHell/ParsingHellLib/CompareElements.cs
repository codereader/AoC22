using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ParsingHellLib
{
    internal class CompareElements : Comparer<Element>
    {
        public override int Compare(Element left, Element right)
        {
            if (left is NumberElement leftNum)
            {
                if (right is NumberElement rightNum)
                {
                    if (leftNum.Number < rightNum.Number)
                    {
                        return 1;
                    }
                    else if (leftNum.Number > rightNum.Number)
                    {
                        return -1;
                    }
                    else
                    {
                        return 0;
                    }
                }
                else if (right is ListElement rightList)
                {
                    var newLeftList = new ListElement();
                    newLeftList.Contents.Add(leftNum);
                    return Compare(newLeftList, rightList);
                }
            }
            else if (left is ListElement leftList)
            {
                if (right is NumberElement rightNum)
                {
                    var newRightList = new ListElement();
                    newRightList.Contents.Add(rightNum);
                    return Compare(leftList, newRightList);
                }
                else if (right is ListElement rightList)
                {
                    var leftCount = leftList.Contents.Count;
                    var rightCount = rightList.Contents.Count;

                    for (int i = 0; i < Math.Min(leftCount, rightCount); i++)
                    {
                        var result = Compare(leftList.Contents[i], rightList.Contents[i]);
                        if (result != 0)
                        {
                            return result;
                        }
                    }
                    if (leftCount < rightCount)
                    {
                        return 1;
                    }
                    else if (leftCount > rightCount)
                    {
                        return -1;
                    }
                    else
                    {
                        return 0;
                    }

                }
            }

            throw new InvalidOperationException();
        }

    }
}
