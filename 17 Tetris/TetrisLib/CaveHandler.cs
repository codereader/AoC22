using System;
using System.Collections.Generic;
using System.DirectoryServices;
using System.Linq;
using System.Numerics;
using System.Windows.Documents;

namespace TetrisLib
{
    public class CaveHandler
    {
        private List<int> _jetStreams = new List<int>();

        private Dictionary<long, Rock> _shapes = new Dictionary<long, Rock>();

        private List<bool[]> _fallenRocks = new List<bool[]>();

        private bool _atRest = false;

        private List<Configuration> _configurations = new List<Configuration>();
        private bool _cycleFound;

        public long TowerHeight { get; set; } = 0;


        public CaveHandler()
        {
            // Create shapes
            // start at the bottom

            // ####
            var rock0 = new Rock();
            var arr1111 = new bool[4] { true, true, true, true };
            rock0.Positions.Add(arr1111);
            _shapes.Add(0, rock0);

            // .#.
            // ###
            // .#.
            var rock1 = new Rock();
            var arr010 = new bool[3] { false, true, false };
            var arr111 = new bool[3] { true, true, true };
            rock1.Positions.Add(arr010);
            rock1.Positions.Add(arr111);
            rock1.Positions.Add(arr010);
            _shapes.Add(1, rock1);

            // ..#
            // ..#
            // ###
            var rock2 = new Rock();
            var arr001 = new bool[3] { false, false, true };
            rock2.Positions.Add(arr111);
            rock2.Positions.Add(arr001);
            rock2.Positions.Add(arr001);
            _shapes.Add(2, rock2);

            //   #
            //   #
            //   #
            //   #
            var rock3 = new Rock();
            var arr1 = new bool[1] { true };
            rock3.Positions.Add(arr1);
            rock3.Positions.Add(arr1);
            rock3.Positions.Add(arr1);
            rock3.Positions.Add(arr1);
            _shapes.Add(3, rock3);

            //   ##
            //   ##
            var rock4 = new Rock();
            var arr11 = new bool[2] { true, true };
            rock4.Positions.Add(arr11);
            rock4.Positions.Add(arr11);

            _shapes.Add(4, rock4);


            foreach (var rock in _shapes.Values)
            {
                // into start x position
                rock.MinX = 2;
                rock.MaxX = rock.MinX + rock.Positions.Max(p => p.Length) - 1;
                rock.Height = rock.Positions.Count();
            }

        }

        public void Parse(List<string> input)
        {
            foreach (var line in input)
            {
                foreach (var character in line)
                {
                    if (character == '<')
                    {
                        // left
                        _jetStreams.Add(-1);
                    }
                    else
                    {
                        // right
                        _jetStreams.Add(1);
                    }
                }
            }
        }

        public void Simulation(long maxRockCount)
        {
            var minX = 0;
            var maxX = 6;

            long rockCount = 0;

            var currentJetPos = 0;

            _cycleFound= false;

            while (rockCount < maxRockCount)
            {
                var rockId = rockCount % 5;
                var currentRock = new Rock(_shapes[rockId]);
                rockCount++;

                // startposition
                for (int i = 0; i < 3; i++)
                {
                    // can do the first 3 moves without looking at resting stones
                    var currentJet = _jetStreams[currentJetPos];

                    if (currentJet < 0)
                    {
                        // left
                        if (currentRock.MinX - 1 >= minX)
                        {
                            currentRock.MinX -= 1;
                            currentRock.MaxX -= 1;
                        }
                    }
                    else
                    {
                        // right
                        if (currentRock.MaxX + 1 <= maxX)
                        {
                            currentRock.MinX += 1;
                            currentRock.MaxX += 1;
                        }
                    }
                    currentJetPos++;
                    if (currentJetPos >= _jetStreams.Count)
                    {
                        currentJetPos = 0;
                    }
                }

                // directly above tower now
                _atRest = false;
                var y = _fallenRocks.Count;

                while (!_atRest)
                {
                    // side move
                    var currentJet = _jetStreams[currentJetPos];
                    if (currentJet == -1)
                    {
                        // left
                        if (currentRock.MinX - 1 >= minX)
                        {
                            var testPos = currentRock.MinX - 1;
                            if (!CheckBlocked(currentRock, y, testPos))
                            {
                                // not blocked, move
                                currentRock.MinX -= 1;
                                currentRock.MaxX -= 1;
                            }
                        }
                    }
                    else
                    {
                        // right
                        if (currentRock.MaxX + 1 <= maxX)
                        {
                            var testPos = currentRock.MinX + 1;
                            if (!CheckBlocked(currentRock, y, testPos))
                            {
                                // not blocked, move
                                currentRock.MinX += 1;
                                currentRock.MaxX += 1;
                            }
                        }
                    }
                    currentJetPos++;
                    if (currentJetPos >= _jetStreams.Count)
                    {
                        currentJetPos = 0;
                    }

                    // down move

                    if (y == 0)
                    {
                        // reached the ground, no rocks here, come to rest
                        var testPos = currentRock.MinX;
                        ComeToRest(currentRock, y, testPos);
                    }
                    else
                    {
                        // check if we can fall down
                        var testPos = currentRock.MinX;
                        if (!CheckBlocked(currentRock, y - 1, testPos))
                        {
                            // can move down
                            y--;
                        }
                        else
                        {
                            // can not move down, come to rest
                            ComeToRest(currentRock, y, testPos);
                        }

                    }

                }

                if (!_cycleFound)
                {
                    var config = new Configuration();
                    config.CurrentJetPos = currentJetPos;
                    config.RockId = rockId;
                    config.TowerHeight = TowerHeight;
                    config.RockCount = rockCount;

                    if (_fallenRocks.Count > 30)
                    {
                        var lastrows = _fallenRocks.TakeLast(30).ToList();
                        config.CreateFallenRockConfig(lastrows);
                    }
                    else
                    {
                        config.CreateFallenRockConfig(_fallenRocks);
                    }

                    foreach (var oldConfig in _configurations)
                    {
                        if (oldConfig.Equals(config))
                        {
                            // this is the same configuration as the one at oldConfig, repeats from here
                            _cycleFound = true;
                            var oldRockCount = oldConfig.RockCount;
                            var rockDiff = rockCount - oldRockCount;
                            var towerHeightDiff = TowerHeight - oldConfig.TowerHeight;
                            var repeatCount = (maxRockCount - rockCount) / rockDiff;
                            TowerHeight += repeatCount * towerHeightDiff;
                            rockCount += repeatCount * rockDiff;
                            break;
                        }
                    }
                    _configurations.Add(config);
                }

            }

        }

        private void ComeToRest(Rock currentRock, int y, int testPos)
        {
            var lineToRemove = -1;
            _atRest = true;
            for (int i = 0; i < currentRock.Positions.Count; i++)
            {
                var testY = y + i;
                if (testY < _fallenRocks.Count)
                {
                    // already rocks at this height, add the new ones
                    var currentLine = _fallenRocks[testY];
                    PlaceRocks(currentRock, testPos, i, currentLine);

                    // if the line is completely filled, remove it and everything below
                    if (currentLine.Count(e => e == true) == 7)
                    {
                        lineToRemove = testY;
                    }
                }
                else
                {
                    // Create new line
                    TowerHeight++;
                    var currentLine = new bool[7] { false, false, false, false, false, false, false };
                    PlaceRocks(currentRock, testPos, i, currentLine);
                    _fallenRocks.Add(currentLine);
                }

            }

            if (lineToRemove >= 0)
            {
                _fallenRocks.RemoveRange(0, lineToRemove + 1);
            }
        }

        private static void PlaceRocks(Rock currentRock, int testPos, int i, bool[] currentLine)
        {
            for (int x = 0; x < currentRock.Positions[i].Length; x++)
            {
                // position is blocked when both the fallenrocks and the new rock have true at this position
                currentLine[testPos + x] = (currentLine[testPos + x] | currentRock.Positions[i][x]);
            }
        }

        private bool CheckBlocked(Rock currentRock, int y, int testPos)
        {
            var blocked = false;
            for (int i = 0; i < currentRock.Positions.Count; i++)
            {
                var testY = y + i;
                if (testY < _fallenRocks.Count)
                {
                    var testLine = _fallenRocks[testY];
                    for (int x = 0; x < currentRock.Positions[i].Length; x++)
                    {
                        // position is blocked when both the fallenrocks and the new rock have true at this position
                        if (testLine[testPos + x] & currentRock.Positions[i][x])
                        {
                            blocked = true;
                            break;
                        }
                    }
                }
            }
            return blocked;
        }
    }
}
