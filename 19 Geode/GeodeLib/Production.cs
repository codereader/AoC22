using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GeodeLib
{
    internal class Production
    {
        private BluePrint _bluePrint;

        // currently available resources
        private Dictionary<Resource, int> _resources = new Dictionary<Resource, int>();

        // production of new resources each day
        private Dictionary<Resource, int> _production = new Dictionary<Resource, int>();

        private Resource _buildNewRobot = Resource.None;

        public Production(BluePrint bluePrint)
        {
            _bluePrint = bluePrint;
        }

        public void StartProduction()
        {
            var bestFound = false;

            var results = new List<int>();

            var targetOreProduction = 0;

            while (!bestFound)
            {
                targetOreProduction++;
                SetStartValues();
                RunProduction(targetOreProduction);
                var currentResult = _resources[Resource.Geode];
                results.Add(currentResult);
                //Console.WriteLine($"Target ore production: " + targetOreProduction + ", result: " + currentResult);

                if (currentResult < results.Max()) 
                {
                    // this is worse than a previous result, we have found the best option before
                    bestFound = true;
                    _bluePrint.BestResult = results.Max();
                }
            }

        }

        private void RunProduction(int targetOreProduction)
        {
            for (int minute = 1; minute <= 24; minute++)
            {
                //Console.WriteLine($"Minute " + minute);

                // check if we can create a new robot
                _buildNewRobot = Resource.None;

                if (_production[Resource.Ore] < targetOreProduction) 
                {
                    if (CanBuildRobot(Resource.Ore))
                    {
                        StartBuildingRobot(Resource.Ore);
                        _buildNewRobot = Resource.Ore;

                    }
                }
                // always build geode robot when possible
                else if (CanBuildRobot(Resource.Geode))
                {
                    StartBuildingRobot(Resource.Geode);
                    _buildNewRobot = Resource.Geode;
                }
                else if (CanBuildRobot(Resource.Obsidian))
                {
                    // look ahead if we have to save ore for the next geode robot
                    if (_production[Resource.Obsidian] > 0)
                    {
                        if (LookAheadGeode())
                        {
                            StartBuildingRobot(Resource.Obsidian);
                            _buildNewRobot = Resource.Obsidian;
                        }
                    }
                    else
                    {
                        // build obsidian robot
                        StartBuildingRobot(Resource.Obsidian);
                        _buildNewRobot = Resource.Obsidian;
                    }
                }
                else if (CanBuildRobot(Resource.Clay))
                {
                    // look ahead if we have to save ore for the next obsidian robot
                    if (_production[Resource.Clay] > 0)
                    {
                        if (LookAheadObsidian())
                        {
                            // look ahead if we have to save ore for the next geode robot
                            if (_production[Resource.Obsidian] > 0)
                            {
                                if (LookAheadGeode())
                                {
                                    StartBuildingRobot(Resource.Clay);
                                    _buildNewRobot = Resource.Clay;
                                }
                            }
                            else
                            {
                                StartBuildingRobot(Resource.Clay);
                                _buildNewRobot = Resource.Clay;
                            }
                        }
                    }
                    else
                    {
                        // build clay robot
                        StartBuildingRobot(Resource.Clay);
                        _buildNewRobot = Resource.Clay;
                    }
                }


                // robots produce new resources
                Console.WriteLine("Resources after production:");
                foreach (var resource in _resources.Keys)
                {
                    if (resource != Resource.None)
                    {
                        _resources[resource] += _production[resource];
                        //Console.WriteLine($"" + resource + ": " + _resources[resource]);
                    }
                }

                // create new robot
                if (_buildNewRobot != Resource.None)
                {
                    _production[_buildNewRobot] += 1;
                }

                /*
                Console.WriteLine("Production after building new robot:");
                foreach (var resource in _production.Keys)
                {
                    if (resource != Resource.None)
                    {
                        Console.WriteLine($"" + resource + ": " + _production[resource]);
                    }
                }
                */
                Console.WriteLine();

            }
        }

        private bool LookAheadObsidian()
        {
            // how many days till we can make a new obsidian robot
            float diff = _bluePrint.ObsidianRobotCostClay - _resources[Resource.Clay];
            var daysTillObsidianRobot = Math.Ceiling(diff / _production[Resource.Clay]);
            var oreAtThatDay = _resources[Resource.Ore] + daysTillObsidianRobot * _production[Resource.Ore];

            // enough ore to build clay robot now and obsidian robot later?
            return oreAtThatDay >= _bluePrint.ObsidianRobotCostOre + _bluePrint.ClayRobotCostOre;
        }

        private bool LookAheadGeode()
        {
            // how many days till we can make a new geode robot
            float diff = _bluePrint.GeodeRobotCostObsidian - _resources[Resource.Obsidian];
            var daysTillGeodeRobot = Math.Ceiling(diff / _production[Resource.Obsidian]);
            var oreAtThatDay = _resources[Resource.Ore] + daysTillGeodeRobot * _production[Resource.Ore];

            // enough ore to build obsidian robot now and geode robot later?
            return oreAtThatDay >= _bluePrint.GeodeRobotCostOre + _bluePrint.ObsidianRobotCostOre;
        }

        private bool CanBuildRobot(Resource resource)
        {
            // check if there are enough resources
            if (resource == Resource.Geode)
            {
                return _resources[Resource.Ore] >= _bluePrint.GeodeRobotCostOre &&
                     _resources[Resource.Obsidian] >= _bluePrint.GeodeRobotCostObsidian;
            }
            else if (resource == Resource.Obsidian)
            {
                return _resources[Resource.Ore] >= _bluePrint.ObsidianRobotCostOre &&
                     _resources[Resource.Clay] >= _bluePrint.ObsidianRobotCostClay;
            }
            else if (resource == Resource.Clay)
            {
                return _resources[Resource.Ore] >= _bluePrint.ClayRobotCostOre;
            }
            else if (resource == Resource.Ore)
            {
                return _resources[Resource.Ore] >= _bluePrint.OreRobotCostOre;
            }
            return false;
        }

        private void StartBuildingRobot(Resource resource)
        {
            //Console.WriteLine($"Start building robot " + resource);

            // remove the necessary resources
            if (resource == Resource.Geode)
            {
                _resources[Resource.Ore] -= _bluePrint.GeodeRobotCostOre;
                _resources[Resource.Obsidian] -= _bluePrint.GeodeRobotCostObsidian;
            }
            else if (resource == Resource.Obsidian)
            {
                _resources[Resource.Ore] -= _bluePrint.ObsidianRobotCostOre;
                _resources[Resource.Clay] -= _bluePrint.ObsidianRobotCostClay;
            }
            else if (resource == Resource.Clay)
            {
                _resources[Resource.Ore] -= _bluePrint.ClayRobotCostOre;
            }
            else if (resource == Resource.Ore)
            {
                _resources[Resource.Ore] -= _bluePrint.OreRobotCostOre;
            }
        }



        private void SetStartValues()
        {
            _resources.Clear();
            _production.Clear();

            foreach (var resource in (Resource[])Enum.GetValues(typeof(Resource)))
            {
                _resources[resource] = 0;
                _production[resource] = 0;
            }
            // 1 ore production at start
            _production[Resource.Ore] = 1;
        }
    }
}
