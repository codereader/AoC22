using System.Numerics;
using System.Runtime.CompilerServices;
using System.Text.RegularExpressions;

namespace SignalLib
{
    public class SignalHandler
    {
        private List<Pair> _pairs = new List<Pair>();

        public void Parse(List<string> input)
        {
            for (int i = 0; i < input.Count; i++)
            {
                var line = input[i];

                //Sensor at x=1638847, y=3775370: closest beacon is at x=2498385, y=3565515
                var expression = @"Sensor at x=(\d+), y=(\d+): closest beacon is at x=(\d+), y=(\d+)";
                var match = Regex.Match(line, expression);
                if (match.Success)
                {
                    var currentPair = new Pair();
                    currentPair.Id = i;
                    currentPair.SignalPosition = new Vector2(int.Parse(match.Groups[1].Value), int.Parse(match.Groups[2].Value));
                    currentPair.BeaconPosition = new Vector2(int.Parse(match.Groups[3].Value), int.Parse(match.Groups[4].Value));

                    currentPair.Distance = (int)(Math.Abs(currentPair.SignalPosition.X - currentPair.BeaconPosition.X) +
                                                Math.Abs(currentPair.SignalPosition.Y - currentPair.BeaconPosition.Y));

                    _pairs.Add(currentPair);
                }
            }

        }

        public int CountNonBeacons(int rowNum)
        {
            var newPositions = new List<List<int>>();
            foreach (var pair in _pairs) 
            {
                var ydist = (int)(Math.Abs(pair.SignalPosition.Y - rowNum));

                // signal is close enough 
                if (ydist <= pair.Distance) 
                {
                    var xMin = pair.SignalPosition.X - (pair.Distance - ydist);
                    var xMax = pair.SignalPosition.X + (pair.Distance - ydist);
                    var borders = new List<int>()
                    {
                        (int)xMin, 
                        (int)xMax
                    };
                    newPositions.Add(borders);
                }
            }


            var oldPositions = new List<List<int>>();

            while (newPositions.Count != 1 && oldPositions.Count != newPositions.Count)
            {
                oldPositions = new List<List<int>> (newPositions);
                newPositions.Clear();
                newPositions.Add(oldPositions[0]);

                for (int i = 1; i < oldPositions.Count; i++)
                {
                    var testPos = oldPositions[i];
                    var combined = false;

                    for (int j = 0; j < newPositions.Count; j++)
                    {
                        var newPos = newPositions[j];
                        if (testPos[0] < newPos[0] && testPos[1] >= newPos[0] )
                        {
                            newPos[0] = testPos[0];
                            // combine ranges
                            if (testPos[1] > newPos[1])
                            {
                                newPos[1] = testPos[1];
                            }
                            combined = true;
                            break;
                        }
                        else if (newPos[0] <= testPos[1] && newPos[1] < testPos[1])
                        {
                            newPos[1] = testPos[1];
                            // combine ranges
                            if (testPos[0] < newPos[0])
                            {
                                newPos[0] = testPos[0];
                            }
                            combined = true;
                            break;
                        }
                        else if (newPos[0] <= testPos[0] && testPos[1] <= newPos[1])
                        {
                            // completely inside
                            combined = true;
                            break;

                        }
                    }
                    if (!combined)
                    {
                        newPositions.Add(testPos);
                    }
                }
                


            }


            var numpos =  newPositions.Select(p => p[1] - p[0] + 1).Sum();

            var beacons = _pairs.Count(p => p.BeaconPosition.Y == rowNum);

            var pairswithbeacon = _pairs.Where(p => p.BeaconPosition.Y == rowNum);
            var beaconxpositions = pairswithbeacon.Select(p => p.BeaconPosition.X);
            var distinctxpositions = beaconxpositions.Distinct();
            var beaconscount = distinctxpositions.Count();

            //Console.WriteLine(beaconscount);
            //Console.WriteLine();


            return numpos - beaconscount;
        }

        public int CountNonBeacons2(int rowNum)
        {
            var minx = (int)_pairs.Min(p => p.SignalPosition.X - p.Distance);
            //Console.WriteLine(minx);

            var maxx = (int)_pairs.Max(p => p.SignalPosition.X + p.Distance);
            //Console.WriteLine(maxx);

            var nonbeaconpositions = 0;
            var beaconcount = 0;

            for (int x = minx; x <= maxx; x++)
            {
                foreach (var pair in _pairs)
                {
                    if (pair.BeaconPosition.X == x && pair.BeaconPosition.Y == rowNum)
                    {
                        // don't count beacons
                        beaconcount++;
                        break;
                    }
                    else if (Math.Abs(x - pair.SignalPosition.X) + Math.Abs(rowNum - pair.SignalPosition.Y) <= pair.Distance)
                    {
                        // within range
                        nonbeaconpositions++;
                        break;
                    }
                }
            }
            //Console.Write($"beacons: ");
            //Console.WriteLine(beaconcount);
            return nonbeaconpositions;

        }

        public void FindPossibleBeacons()
        {
            for (var y = 0; y <= 4000000; y++) 
            {

            }
        }


    }
}
