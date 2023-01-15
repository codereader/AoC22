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
        private int _fieldWidth;
        private int _fieldHeight;

        public Dictionary<Vector2, Location> _grid = new Dictionary<Vector2, Location>();

        public List<Location> ValleyGrid { get; private set; }

        private Dictionary<Vector2, List<Blizzard>> _blizzardPositions = new Dictionary<Vector2, List<Blizzard>>();

        // Blizzards moving to the left (y, blizzard)
        private List<Blizzard> _blizzardsLeft = new List<Blizzard>();
        private List<Blizzard> _blizzardsRight = new List<Blizzard>();

        // (x, blizzard)
        private List<Blizzard> _blizzardsUp = new List<Blizzard>();
        private List<Blizzard> _blizzardsDown = new List<Blizzard>();

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

            ValleyGrid = new List<Location>(_grid.Values.ToList());
        }


    }
}
