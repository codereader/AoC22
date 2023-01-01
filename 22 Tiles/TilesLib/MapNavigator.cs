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
        public Dictionary<Vector2, Location> Map { get; set; } = new Dictionary<Vector2, Location>();

        private Dictionary<string, CubeFace> _faces = new Dictionary<string, CubeFace>();


        private List<Instruction> _instructions = new List<Instruction>();

        public State CurrentState { get; set; }
        public CubeFace CurrentFace { get; set; }

        public int CurrentInstructionNum { get; set; }
        public int CurrentMoveNum { get; set; }
        public bool SimulationCubeDone { get; set; }




        public int MaxX { get; set; }
        public int MaxY { get; set; }

        public int PasswordFlat
        {
            get => GetValue<int>();
            set => SetValue(value);
        }
        public int PasswordCube
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
                    MaxY = Map.Max(p => (int)p.Key.Y) + 1;
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
                    Map[pos] = currentLocation;
                }
            }

            // parse cubes
            var parsePosition = new Vector2(0, 0);
            for (int i = 0; i < 6; i++)
            {
                // look for next face position
                while (!Map.TryGetValue(parsePosition, out var location) || location.Fill == Filling.Off)
                {
                    parsePosition += new Vector2(50, 0);
                    if (parsePosition.X >= MaxX)
                    {
                        parsePosition = new Vector2(0, parsePosition.Y + 50);
                    }
                }

                // position found
                var currentFace = new CubeFace();
                currentFace.Name = ((char)(65 + i)).ToString();
                currentFace.ParseOffset = parsePosition;
                for (int cubeY = 0; cubeY < 50; cubeY++)
                {
                    for (int cubeX = 0; cubeX < 50; cubeX++)
                    {
                        var cubePosition = new Vector2(cubeX, cubeY);
                        var currentLocation = Map[parsePosition + cubePosition];
                        currentFace.Map.Add(cubePosition, new Location() { PositionX = cubeX, PositionY = cubeY, Fill = currentLocation.Fill });
                    }
                }
                _faces.Add(currentFace.Name, currentFace);
                parsePosition += new Vector2(50, 0);
            }

            // face connections

            _faces["A"].Connections.Add(Direction.Right, new Connection
            {
                TargetFace = _faces["B"],
                TargetOrientation = Direction.Right,
                TransformPosition = v => new Vector2(0, v.Y)
            });
            _faces["B"].Connections.Add(Direction.Left, new Connection
            {
                TargetFace = _faces["A"],
                TargetOrientation = Direction.Left,
                TransformPosition = v => new Vector2(49, v.Y)
            });

            _faces["A"].Connections.Add(Direction.Down, new Connection
            {
                TargetFace = _faces["C"],
                TargetOrientation = Direction.Down,
                TransformPosition = v => new Vector2(v.X, 0)
            });
            _faces["C"].Connections.Add(Direction.Up, new Connection
            {
                TargetFace = _faces["A"],
                TargetOrientation = Direction.Up,
                TransformPosition = v => new Vector2(v.X, 49)
            });

            _faces["A"].Connections.Add(Direction.Left, new Connection
            {
                TargetFace = _faces["D"],
                TargetOrientation = Direction.Right,
                TransformPosition = v => new Vector2(0, 49 - v.Y)
            });
            _faces["D"].Connections.Add(Direction.Left, new Connection
            {
                TargetFace = _faces["A"],
                TargetOrientation = Direction.Right,
                TransformPosition = v => new Vector2(0, 49 - v.Y)
            });

            _faces["A"].Connections.Add(Direction.Up, new Connection
            {
                TargetFace = _faces["F"],
                TargetOrientation = Direction.Right,
                TransformPosition = v => new Vector2(0, v.X)
            });
            _faces["F"].Connections.Add(Direction.Left, new Connection
            {
                TargetFace = _faces["A"],
                TargetOrientation = Direction.Down,
                TransformPosition = v => new Vector2(v.Y, 0)
            });

            _faces["B"].Connections.Add(Direction.Down, new Connection
            {
                TargetFace = _faces["C"],
                TargetOrientation = Direction.Left,
                TransformPosition = v => new Vector2(49, v.X)
            });
            _faces["C"].Connections.Add(Direction.Right, new Connection
            {
                TargetFace = _faces["B"],
                TargetOrientation = Direction.Up,
                TransformPosition = v => new Vector2(v.Y, 49)
            });

            _faces["B"].Connections.Add(Direction.Right, new Connection
            {
                TargetFace = _faces["E"],
                TargetOrientation = Direction.Left,
                TransformPosition = v => new Vector2(49, 49 - v.Y)

            });
            _faces["E"].Connections.Add(Direction.Right, new Connection
            {
                TargetFace = _faces["B"],
                TargetOrientation = Direction.Left,
                TransformPosition = v => new Vector2(49, 49 - v.Y)
            });

            _faces["B"].Connections.Add(Direction.Up, new Connection
            {
                TargetFace = _faces["F"],
                TargetOrientation = Direction.Up,
                TransformPosition = v => new Vector2(v.X, 49)
            });
            _faces["F"].Connections.Add(Direction.Down, new Connection
            {
                TargetFace = _faces["B"],
                TargetOrientation = Direction.Down,
                TransformPosition = v => new Vector2(v.X, 0)
            });

            _faces["C"].Connections.Add(Direction.Down, new Connection
            {
                TargetFace = _faces["E"],
                TargetOrientation = Direction.Down,
                TransformPosition = v => new Vector2(v.X, 0)
            });
            _faces["E"].Connections.Add(Direction.Up, new Connection
            {
                TargetFace = _faces["C"],
                TargetOrientation = Direction.Up,
                TransformPosition = v => new Vector2(v.X, 49)
            });

            _faces["C"].Connections.Add(Direction.Left, new Connection
            {
                TargetFace = _faces["D"],
                TargetOrientation = Direction.Down,
                TransformPosition = v => new Vector2(v.Y, 0)
            });
            _faces["D"].Connections.Add(Direction.Up, new Connection
            {
                TargetFace = _faces["C"],
                TargetOrientation = Direction.Right,
                TransformPosition = v => new Vector2(0, v.X)
            });

            _faces["D"].Connections.Add(Direction.Right, new Connection
            {
                TargetFace = _faces["E"],
                TargetOrientation = Direction.Right,
                TransformPosition = v => new Vector2(0, v.Y)
            });
            _faces["E"].Connections.Add(Direction.Left, new Connection
            {
                TargetFace = _faces["D"],
                TargetOrientation = Direction.Left,
                TransformPosition = v => new Vector2(49, v.Y)
            });

            _faces["D"].Connections.Add(Direction.Down, new Connection
            {
                TargetFace = _faces["F"],
                TargetOrientation = Direction.Down,
                TransformPosition = v => new Vector2(v.X, 0)
            });
            _faces["F"].Connections.Add(Direction.Up, new Connection
            {
                TargetFace = _faces["D"],
                TargetOrientation = Direction.Up,
                TransformPosition = v => new Vector2(v.X, 49)
            });

            _faces["E"].Connections.Add(Direction.Down, new Connection
            {
                TargetFace = _faces["F"],
                TargetOrientation = Direction.Left,
                TransformPosition = v => new Vector2(49, v.X)
            });
            _faces["F"].Connections.Add(Direction.Right, new Connection
            {
                TargetFace = _faces["E"],
                TargetOrientation = Direction.Up,
                TransformPosition = v => new Vector2(v.Y, 49)
            });


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


        public void SimulationFlat()
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
                        if (!Map.TryGetValue(nextPosition, out var nextLocation) || nextLocation.Fill == Filling.Off)
                        {
                            // off, wrap around
                            var pos = currentState.Position;
                            while (Map.TryGetValue(pos, out var testLocation) && testLocation.Fill != Filling.Off)
                            {
                                pos -= move;
                            }

                            nextPosition = pos + move;
                        }

                        nextLocation = Map[nextPosition];
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

            PasswordFlat = (int)(1000 * (currentState.Position.Y + 1) + 4 * (currentState.Position.X + 1) + (int)currentState.Orientation);

        }

        private State FindStartState()
        {
            var state = new State();

            // first row y = 0
            var firstRow = Map.Where(p => p.Key.Y == 0 && p.Value.Fill != Filling.Off);
            var x = firstRow.Min(p => p.Key.X);

            state.Position = new Vector2(x, 0);
            state.Orientation = Direction.Right;

            return state;
        }


        public void SetupSimulationCube()
        {
            SetupStartPosition();
            CurrentInstructionNum = 0;
            CurrentMoveNum = 0;

            SimulationCubeDone = false;
        }

        public void SetupStartPosition()
        {
            CurrentFace = _faces["A"];
            CurrentState = new State();
            CurrentState.Position = new Vector2(0, 0);
            CurrentState.Orientation = Direction.Right;
        }

        public void SimulationCubeNextStep()
        {
            if (CurrentInstructionNum >= _instructions.Count)
            {
                SimulationCubeDone = true;
                return;
            }
            var instruction = _instructions[CurrentInstructionNum];

            if (instruction.Turn != 0)
            {
                // turn instruction
                CurrentState.Orientation = (Direction)(((int)CurrentState.Orientation + instruction.Turn + 4) % 4);
                CurrentInstructionNum++;
            }
            else
            {
                // move instruction

                if (CurrentMoveNum < instruction.Move)
                {
                    var move = GetMoveVector(CurrentState.Orientation);

                    DoMove(move);
                }
                else
                {
                    // move done
                    CurrentInstructionNum++;
                    CurrentMoveNum = 0;
                }
            }

            PasswordCube = (int)(1000 * (CurrentState.Position.Y + CurrentFace.ParseOffset.Y + 1) + 4 * (CurrentState.Position.X + CurrentFace.ParseOffset.X + 1) + (int)CurrentState.Orientation);



        }

        public void DoMove(Vector2 move)
        {
            var nextFace = CurrentFace;
            var nextOrientation = CurrentState.Orientation;

            var nextPosition = CurrentState.Position + move;
            if (!CurrentFace.Map.TryGetValue(nextPosition, out var nextLocation) || nextLocation.Fill == Filling.Off)
            {
                // change to new face
                var connection = CurrentFace.Connections[CurrentState.Orientation];
                nextFace = connection.TargetFace;
                nextPosition = connection.TransformPosition(CurrentState.Position);
                nextOrientation = connection.TargetOrientation;
            }

            nextLocation = nextFace.Map[nextPosition];
            if (nextLocation.Fill == Filling.Path)
            {
                // path, next position

                CurrentState.Position = nextPosition;
                CurrentState.Orientation = nextOrientation;
                CurrentFace = nextFace;
                CurrentMoveNum++;
            }
            else if (nextLocation.Fill == Filling.Wall)
            {
                // wall, stop move here
                CurrentInstructionNum++;
                CurrentMoveNum = 0;
            }
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

        public void SimulationCube()
        {
            SetupSimulationCube();

            while (!SimulationCubeDone)
            {
                SimulationCubeNextStep();
            }
        }
    }
}
