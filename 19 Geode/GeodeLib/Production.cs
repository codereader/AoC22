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

        private State _startingState = new State();

        // currently available resources
        private Dictionary<Resource, int> _resources = new Dictionary<Resource, int>();

        // production of new resources each day
        private Dictionary<Resource, int> _production = new Dictionary<Resource, int>();

        private State _bestValue = new State();

        public Production(BluePrint bluePrint)
        {
            _bluePrint = bluePrint;

            _startingState.Minute = 0;
            foreach (var resource in (Resource[])Enum.GetValues(typeof(Resource)))
            {
                _startingState.Resources[(int)resource] = 0;
                _startingState.Production[(int)resource] = 0;
            }
            _startingState.Production[(int)Resource.Ore] = 1;
        }


        public void StartProduction()
        {
            RunProduction();

            Console.WriteLine($"Blueprint " + _bluePrint.Id + ": Geodes: " + _bestValue.Resources[(int)Resource.Geode]);

        }



        private void RunProduction()
        {
            // 
            var currentState = new State(_startingState);

            // try starting with clay robot
            BuildNextBot(Resource.Clay, currentState);

            // try starting with ore robot
            BuildNextBot(Resource.Ore, currentState);
        }

        private void BuildNextBot(Resource bot, State oldState)
        {
            var currentState = new State(oldState);
            var maxMinutes = 0;
            for (int i = 0; i < 4; i++)
            {
                if (_bluePrint.Cost[bot][i] > 0)
                {
                    var diff = _bluePrint.Cost[bot][i] - currentState.Resources[i];

                    // how many minutes till we can make a new robot
                    var minutes = (diff < 0) ? 0 :
                        (int)Math.Ceiling((float)diff / currentState.Production[i]);
                    if (minutes > maxMinutes)
                    {
                        maxMinutes = minutes;
                    }
                }
            }

            // let the current setup run for minMinutes + 1 (the last minute where we will create the new robot)
            // unless the 24 minutes are over before the new robot is finished
            if (currentState.Minute + maxMinutes + 1 >= 24)
            {
                // finish this test run
                var minutesLeft = 24 - currentState.Minute;
                currentState.UpdateResources(minutesLeft);
                currentState.Minute = 24;

                if (currentState.Resources[(int)Resource.Geode] > _bestValue.Resources[(int)Resource.Geode])
                {
                    _bestValue = currentState;
                }
                return;

            }
            else
            {
                // update state and start with new bot
                currentState.UpdateResources(maxMinutes + 1);
                currentState.Minute += maxMinutes + 1;

                CreateBot(bot, _bluePrint, currentState);

                // recursively build next robot
                if (currentState.Production[(int)Resource.Obsidian] > 0)
                {
                    BuildNextBot(Resource.Geode, currentState);
                }
                if (currentState.Production[(int)Resource.Clay] > 0)
                {
                    BuildNextBot(Resource.Obsidian, currentState);
                }
                BuildNextBot(Resource.Clay, currentState);
                BuildNextBot(Resource.Ore, currentState);

            }


        }

        private void CreateBot(Resource bot, BluePrint bluePrint, State currentState)
        {
            for (int i = 0; i < 4; i++)
            {
                currentState.Resources[i] -= bluePrint.Cost[bot][i];
            }
            currentState.Production[(int)bot] += 1;
            currentState.Robots.Add(bot);
        }
    }








}
