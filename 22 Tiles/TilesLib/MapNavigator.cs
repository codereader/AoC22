using CommonWPF;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Numerics;

namespace TilesLib
{
    public class MapNavigator : ViewModelBase
    {
        private Dictionary<Vector2, Location> _map = new Dictionary<Vector2, Location>();

        public ObservableCollection<Location> VisualMap { get; private set; } = new ObservableCollection<Location>();

        private List<Instruction> _instructions = new List<Instruction>();

        public int MaxX { get; set; }
        public int MaxY { get; set; }

        public int Password
        {
            get => GetValue<int>();
            set => SetValue(value);
        }


        public void Parse(List<string> input)
        {
            // first part of input
            var y = 0;
            for (; y < input.Count; y++)
            {
                var line = input[y];

                if (string.IsNullOrEmpty(line))
                {
                    // first part of input is done
                    y++;
                    MaxY = _map.Max(p => (int)p.Key.Y);
                    break;
                }

                if (line.Length > MaxX)
                {
                    MaxX = line.Length;
                }

                for (int x = 0; x < line.Length; x++)
                {
                    var pos = new Vector2(x, y);
                    var currentLocation = new Location(pos, line[x]);
                    _map[pos] = currentLocation;
                    VisualMap.Add(currentLocation);
                }
            }

            // second part of input
            // movement instructions
            for (; y < input.Count; y++)
            {
                var line = input[y];
                if (!string.IsNullOrEmpty(line))
                {
                    var i = 0;

                    while (i < line.Length)
                    {
                        var currentChar = line[i];
                        if (char.IsLetter(currentChar))
                        {
                            // turn instruction
                            _instructions.Add(new Instruction(currentChar.ToString()));
                            i++;
                        }
                        else
                        {
                            // number
                            var numstr = "";
                            var testLine = line.Substring(i);
                            var nextLetterPos = testLine.IndexOfAny(new[] { 'R', 'L' });
                            if (nextLetterPos != -1)
                            {
                                // remove the rest of the string at the next turn instruction
                                numstr = testLine.Remove(nextLetterPos);
                            }
                            else
                            {
                                // end of line, last num instruction
                                numstr = testLine;
                            }
                            _instructions.Add(new Instruction(numstr));
                            i += numstr.Length;
                            numstr = "";
                        }
                    }
                }

            }
        }


        public void Simulation()
        {
            var currentState = FindStartState();

            foreach (var instruction in _instructions)
            {
                if (instruction.Turn != 0)
                {
                    // turn instruction
                    currentState.Orientation = (Direction)(((int)currentState.Orientation + instruction.Turn + 4) % 4);
                }
                else
                {
                    // move instruction
                    var move = GetMoveVector(currentState.Orientation);
                    for (int i = 0; i < instruction.Move; i++)
                    {
                        var nextPosition = currentState.Position + move;
                        if (!_map.TryGetValue(nextPosition, out var nextLocation) || nextLocation.Fill == Filling.Off)
                        {
                            // off, wrap around
                            var pos = currentState.Position;
                            while (_map.TryGetValue(pos, out var testLocation) && testLocation.Fill != Filling.Off)
                            {
                                pos -= move;
                            }

                            nextPosition = pos + move;
                        }

                        nextLocation = _map[nextPosition];
                        if (nextLocation.Fill == Filling.Path)
                        {
                            // path, next position
                            currentState.Position = nextPosition;
                        }
                        else if (nextLocation.Fill == Filling.Wall)
                        {
                            // wall, stop move here
                            break;
                        }
                    }
                }
            }

            Password = (int)(1000 * (currentState.Position.Y + 1) + 4 * (currentState.Position.X + 1) + (int)currentState.Orientation);

        }



        private State FindStartState()
        {
            var state = new State();

            // first row y = 0
            var firstRow = _map.Where(p => p.Key.Y == 0 && p.Value.Fill != Filling.Off);
            var x = firstRow.Min(p => p.Key.X);

            state.Position = new Vector2(x, 0);
            state.Orientation = Direction.Right;

            return state;
        }

        private Vector2 GetMoveVector(Direction dir)
        {
            return dir switch
            {
                Direction.Right => new Vector2(1, 0),
                Direction.Down => new Vector2(0, 1),
                Direction.Left => new Vector2(-1, 0),
                Direction.Up => new Vector2(0, -1)
            };
        }
    }
}
