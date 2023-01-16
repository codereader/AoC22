using CommonWPF;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Numerics;

namespace BlizzardLib
{
    public class BlizzardHandler : ViewModelBase
    {
        private int _maxX;
        private int _maxY;

        private int _fieldWidth;
        private int _fieldHeight;

        private Vector2 _startPosition;
        private Vector2 _endPosition;
        private int _round;

        // start configuration
        private Dictionary<Vector2, Location> _grid = new Dictionary<Vector2, Location>();

        private List<Vector2> _directions = new List<Vector2>
        {
            new Vector2(0,1),
            new Vector2(1,0),
            new Vector2(0,0),
            new Vector2(-1,0),
            new Vector2(0,-1),
        };

        private Dictionary<Vector2, List<Blizzard>> _blizzardPositions = new Dictionary<Vector2, List<Blizzard>>();
        private Dictionary<Vector2, List<Blizzard>> _simulationBlizzardPositions = new Dictionary<Vector2, List<Blizzard>>();

        // Blizzards moving to the different directions
        private List<Blizzard> _blizzardsLeft = new List<Blizzard>();
        private List<Blizzard> _blizzardsRight = new List<Blizzard>();
        private List<Blizzard> _blizzardsUp = new List<Blizzard>();
        private List<Blizzard> _blizzardsDown = new List<Blizzard>();

        // contains list of movements
        private List<State> _bestPaths = new List<State>();


        public int Round { get; private set; } = 0;
        public int MaxRound { get; private set; } = 0;
        public List<Location> ValleyGrid { get; private set; }

        public void Parse(List<string> input)
        {
            _maxY = input.Count;
            _fieldHeight = input.Count - 2;

            for (int y = 0; y < input.Count; y++)
            {
                var line = input[y];

                _maxX = line.Length;
                _fieldWidth = line.Length - 2;

                for (int x = 0; x < line.Length; x++)
                {
                    var pos = new Vector2(x, y);
                    var character = line[x];

                    if (character == '#')
                    {
                        _grid.Add(pos, new Location { Position = pos, Fill = Filling.Wall });
                    }
                    else if (character == '.')
                    {
                        _grid.Add(pos, new Location { Position = pos, Fill = Filling.Clear });
                    }
                    else
                    {
                        // blizzard
                        _grid.Add(pos, new Location { Position = pos, Fill = Filling.Blizzard });

                        if (character == '<')
                        {
                            _blizzardsLeft.Add(new Blizzard { StartX = x, StartY = y });
                        }
                        else if (character == '>')
                        {
                            _blizzardsRight.Add(new Blizzard { StartX = x, StartY = y });
                        }
                        else if (character == '^')
                        {
                            _blizzardsUp.Add(new Blizzard { StartX = x, StartY = y });
                        }
                        else
                        {
                            _blizzardsDown.Add(new Blizzard { StartX = x, StartY = y });
                        }
                    }
                }
            }

            _startPosition = new Vector2(1, 0);
            _endPosition = new Vector2(_fieldWidth, _fieldHeight + 1);

            ResetVisuals();
        }


        public void CalculatePathStartToEnd()
        {
            CalculatePath(_startPosition, _endPosition);
        }

        public void CalculatePathEndToStart() 
        {
            CalculatePath(_endPosition, _startPosition);
        }

        public void CalculatePath(Vector2 startPosition, Vector2 endPosition)
        {
            var availablePositions = new List<State>();

            var start = new State();
            start.CurrentPosition = startPosition;
            start.Positions.Add(_round, startPosition);
            availablePositions.Add(start);

            var destinationReached = false;

            while (!destinationReached)
            {
                _round++;

                // calculate blizzard positions for round
                CalculateBlizzardPositions(_round, _blizzardPositions);

                var nextAvailablePositions = new List<State>();

                foreach (var currentState in availablePositions)
                {
                    var reachablePositions = FindReachablePositions(currentState);

                    foreach (var reachablePos in reachablePositions)
                    {
                        if (!nextAvailablePositions.Any(s => s.CurrentPosition == reachablePos))
                        {
                            var newState = new State(currentState);
                            newState.CurrentPosition = reachablePos;
                            newState.Positions.Add(_round, reachablePos);
                            nextAvailablePositions.Add(newState);

                            if (reachablePos == endPosition)
                            {
                                // reached destination
                                destinationReached = true;
                                _bestPaths.Add(newState);
                                MaxRound = _round;
                                break;
                            }
                        }
                    }
                }

                availablePositions = nextAvailablePositions;
            }
        }

        private void CalculateBlizzardPositions(int round, Dictionary<Vector2, List<Blizzard>> positions)
        {
            positions.Clear();

            foreach (var blizzard in _blizzardsRight)
            {
                var currentX = (blizzard.StartX - 1 + round) % _fieldWidth + 1;
                AddToBlizzardPositions(positions, blizzard, new Vector2(currentX, blizzard.StartY));
            }
            foreach (var blizzard in _blizzardsLeft)
            {
                var currentX = _fieldWidth - (_fieldWidth - blizzard.StartX + round) % _fieldWidth;
                AddToBlizzardPositions(positions, blizzard, new Vector2(currentX, blizzard.StartY));
            }
            foreach (var blizzard in _blizzardsDown)
            {
                var currentY = (blizzard.StartY - 1 + round) % _fieldHeight + 1;
                AddToBlizzardPositions(positions, blizzard, new Vector2(blizzard.StartX, currentY));
            }
            foreach (var blizzard in _blizzardsUp)
            {
                var currentY = _fieldHeight - (_fieldHeight - blizzard.StartY + round) % _fieldHeight;
                AddToBlizzardPositions(positions, blizzard, new Vector2(blizzard.StartX, currentY));
            }
        }

        private void AddToBlizzardPositions(Dictionary<Vector2, List<Blizzard>> positions, Blizzard blizzard, Vector2 pos)
        {
            if (!positions.TryGetValue(pos, out var blizzardList))
            {
                blizzardList = new List<Blizzard>();
                positions[pos] = blizzardList;
            }
            blizzardList.Add(blizzard);
        }

        private List<Vector2> FindReachablePositions(State pos)
        {
            var positions = new List<Vector2>();

            foreach (var dir in _directions)
            {
                var testPos = pos.CurrentPosition + dir;

                if (testPos.X >= 0 && testPos.X < _maxX &&
                    testPos.Y >= 0 && testPos.Y < _maxY &&
                    _grid[testPos].Fill != Filling.Wall &&
                    (!_blizzardPositions.TryGetValue(testPos, out var blzzardList) || blzzardList.Count == 0))
                {
                    positions.Add(testPos);
                }
            }

            return positions;
        }

        public void DoRound()
        {
            Round++;

            // calculate blizzard positions for round
            CalculateBlizzardPositions(Round, _simulationBlizzardPositions);

            var playerPos = new Vector2(-1, -1);
            // find player position
            var stateContainingPlayerpos = _bestPaths.FirstOrDefault(s => s.Positions.ContainsKey(Round));
            if (stateContainingPlayerpos != null)
            {
                playerPos = stateContainingPlayerpos.Positions[Round];
            }

            // set visual locations
            foreach (var location in ValleyGrid)
            {
                var pos = location.Position;
                if (_grid[pos].Fill != Filling.Wall)
                {
                    if (!_simulationBlizzardPositions.TryGetValue(pos, out var blizzardlist) || blizzardlist.Count == 0)
                    {
                        if (pos == playerPos)
                        {
                            location.Fill = Filling.Player;
                        }
                        else
                        {
                            location.Fill = Filling.Clear;
                        }
                    }
                    else
                    {
                        location.Fill = Filling.Blizzard;
                    }
                }

            }
        }

        public void ResetVisuals()
        {
            _round = 0;
            _bestPaths.Clear();
            Round = 0;
            ValleyGrid = new List<Location>(_grid.Values.ToList());
        }
    }
}
