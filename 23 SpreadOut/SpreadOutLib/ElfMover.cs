using CommonWPF;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Numerics;

namespace SpreadOutLib
{
    public class ElfMover : ViewModelBase
    {
        private List<string>? _input;
        private List<Elf> _elves = new List<Elf>();
        private Dictionary<Vector2, bool> _elfPositions = new Dictionary<Vector2, bool>();
        private int _elvesNotMoving;

        public ObservableCollection<VisualElf> VisualElves { get; set; } = new ObservableCollection<VisualElf>();

        public int Round = 0;

        public bool Finished { get; set; } = false;

        private Dictionary<string, Vector2> _moveDirections = new Dictionary<string, Vector2>()
        {
            { "NW", new Vector2(-1, -1) },
            { "N",  new Vector2( 0, -1) },
            { "NE", new Vector2( 1, -1) },
            { "W",  new Vector2(-1,  0) },
            { "E",  new Vector2( 1,  0) },
            { "SW", new Vector2(-1,  1) },
            { "S",  new Vector2( 0,  1) },
            { "SE", new Vector2( 1,  1) }
        };

        public int RectangleXMin { get; set; }
        public int RectangleXMax { get; set; }
        public int RectangleYMin { get; set; }
        public int RectangleYMax { get; set; }

        public void Parse(List<string> input)
        {
            _input = input;

            for (int y = 0; y < input.Count; y++)
            {
                var line = input[y];

                for (int x = 0; x < line.Length; x++)
                {
                    if (line[x] == '#')
                    {
                        var pos = new Vector2(x, y);
                        var elf = new Elf(pos);
                        _elves.Add(elf);
                        VisualElves.Add(new VisualElf(elf));
                        _elfPositions[pos] = true;
                    }
                }
            }

            FindRectangle();
        }

        public void DoRounds(int rounds)
        {
            for (int i = 0; i < rounds; i++)
            {
                DoRound();
            }
            // update rectangle borders
            FindRectangle();
        }

        public void RunUntilFinished()
        {
            while (Finished == false)
            {
                DoRound();
            }
            // update rectangle borders
            FindRectangle();
        }

        public void DoRound()
        {
            var startDir = Round % 4;
            _elvesNotMoving = 0;
            var proposedPositions = new Dictionary<Vector2, int>();

            foreach (var elf in _elves)
            {
                FindNeighbors(elf);

                // step1: find proposed next positions
                elf.ProposedPosition = elf.Position;

                // no neighbors, don't move at all
                if (elf.CountNeighbors() == 0)
                {
                    _elvesNotMoving++;
                }
                else
                {
                    var directionFound = false;

                    // cycle through possible directions
                    for (int i = 0; i < 4; i++)
                    {
                        var dir = (Direction)((startDir + i) % 4);

                        switch (dir)
                        {
                            case Direction.North:
                                if (elf.Neighbors["NW"] == false && elf.Neighbors["N"] == false && elf.Neighbors["NE"] == false)
                                {
                                    elf.ProposedPosition = elf.Position + _moveDirections["N"];
                                    directionFound = true;
                                }
                                break;

                            case Direction.South:
                                if (elf.Neighbors["SW"] == false && elf.Neighbors["S"] == false && elf.Neighbors["SE"] == false)
                                {
                                    elf.ProposedPosition = elf.Position + _moveDirections["S"];
                                    directionFound = true;
                                }
                                break;

                            case Direction.West:
                                if (elf.Neighbors["NW"] == false && elf.Neighbors["W"] == false && elf.Neighbors["SW"] == false)
                                {
                                    elf.ProposedPosition = elf.Position + _moveDirections["W"];
                                    directionFound = true;
                                }
                                break;

                            case Direction.East:
                                if (elf.Neighbors["NE"] == false && elf.Neighbors["E"] == false && elf.Neighbors["SE"] == false)
                                {
                                    elf.ProposedPosition = elf.Position + _moveDirections["E"];
                                    directionFound = true;
                                }
                                break;
                        }

                        if (directionFound)
                        {
                            break;
                        }
                    }
                }

                if (!proposedPositions.TryGetValue(elf.ProposedPosition, out var count))
                {
                    proposedPositions[elf.ProposedPosition] = 1;
                }
                else
                {
                    proposedPositions[elf.ProposedPosition] = count + 1;
                }
            }

            // step 2: move it
            foreach (var elf in _elves)
            {
                if (proposedPositions[elf.ProposedPosition] == 1)
                {
                    // only 1 elf wants to move here
                    if (elf.Position != elf.ProposedPosition)
                    {
                        elf.RoundsSinceLastMove = -1;
                    }
                    _elfPositions[elf.Position] = false;
                    elf.Position = elf.ProposedPosition;
                    _elfPositions[elf.Position] = true;
                }

                elf.RoundsSinceLastMove++;
            }

            if (_elvesNotMoving == _elves.Count)
            {
                // no more elves moving this round
                Finished = true;
            }
            // next round
            Round++;
        }


        private void FindNeighbors(Elf elf)
        {
            var elfPos = elf.Position;

            foreach (var dir in _moveDirections)
            {
                if (_elfPositions.TryGetValue(elfPos + dir.Value, out var result) && result == true)
                {
                    elf.Neighbors[dir.Key] = true;
                }
                else
                {
                    elf.Neighbors[dir.Key] = false;
                }
            }
        }

        private void FindRectangle()
        {
            RectangleXMin = (int)_elves.Min(e => e.Position.X);
            RectangleXMax = (int)_elves.Max(e => e.Position.X);
            RectangleYMin = (int)_elves.Min(e => e.Position.Y);
            RectangleYMax = (int)_elves.Max(e => e.Position.Y);
        }

        public int CountEmptyGroundTiles()
        {
            return (RectangleXMax - RectangleXMin + 1) * (RectangleYMax - RectangleYMin + 1) - _elves.Count;
        }

        public void UpdateVisuals()
        {
            foreach (var visualElf in VisualElves)
            {
                visualElf.UpdateVisuals();
            }
        }

        public void Reset()
        {
            _elves.Clear();
            _elfPositions.Clear();
            VisualElves.Clear();
            Round = 0;
            Finished = false;

            Parse(_input);
        }
    }
}
