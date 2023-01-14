using CommonWPF;
using System;
using System.Collections.Generic;
using System.Numerics;

namespace BlizzardLib
{
    public class BlizzardHandler : ViewModelBase
    {
        private int _fieldWidth;
        private int _fieldHeight;

        private Dictionary<Vector2, Location> _grid = new Dictionary<Vector2, Location>();
        private Dictionary<Vector2, List<Blizzard>> _blizzardPositions = new Dictionary<Vector2, List<Blizzard>>();



        // Blizzards moving to the left (y, blizzard)
        private Dictionary<int, Blizzard> _blizzardsLeft = new Dictionary<int, Blizzard>();
        private Dictionary<int, Blizzard> _blizzardsRight = new Dictionary<int, Blizzard>();

        // (x, blizzard)
        private Dictionary<int, Blizzard> _blizzardsUp = new Dictionary<int, Blizzard>();
        private Dictionary<int, Blizzard> _blizzardsDown = new Dictionary<int, Blizzard>();

        public int Round { get; private set; } = 0;

        public void Parse(List<string> input)
        {
            _fieldHeight = input.Count - 2;

            for (int y = 0; y < input.Count; y++)
            {
                var line = input[y];

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
                            _blizzardsLeft.Add(y, new Blizzard { StartX = x, StartY = y });
                        }

                        else if (character == '>')
                        {
                            _blizzardsRight.Add(y, new Blizzard { StartX = x, StartY = y });

                        }
                        else if (character == '^')
                        {
                            _blizzardsUp.Add(x, new Blizzard { StartX = x, StartY = y });
                        }
                        else
                        {
                            _blizzardsDown.Add(x, new Blizzard { StartX = x, StartY = y });
                        }
                    }
                }
            }
        }


    }
}
