namespace ValveLib
{
    public class ValveHandler
    {
        private List<Valve> _valves;

        private List<Valve> _flowValves;

        private Valve _startValve;

        private State _bestPressureState = new State();

        private State _bestPressure2SetA;
        private State _bestPressure2SetB;

        private int _bestPressure2;

        public void Parse(List<string> input)
        {
            _valves = input.Select(l => new Valve(l)).ToList();
            _flowValves = _valves.Where(v => v.FlowRate > 0).ToList();

            foreach (var valve in _valves)
            {
                foreach (var str in valve.ConnectedValveStrings)
                {
                    valve.ConnectedValves.Add(_valves.Where(v => v.Name == str).First());
                }
            }

            _startValve = _valves.Where(v => v.Name == "AA").First();

            DetermineShortestPaths();

        }

        public void DetermineShortestPaths()
        {
            FindShortestPaths(_startValve);

            foreach (var valve in _flowValves)
            {
                FindShortestPaths(valve);
            }
        }

        private void FindShortestPaths(Valve valve)
        {
            var availableValves = new Dictionary<Valve, int>();
            var visitedValves = new Dictionary<Valve, int>();
            availableValves.Add(valve, 0);
            while (valve.ShortestPaths.Count < _flowValves.Count)
            {
                var minDist = availableValves.Values.Min();
                var currentValve = availableValves.Where(v => v.Value == minDist).First();

                // add to shortest paths
                if (currentValve.Key.FlowRate > 0 && !valve.ShortestPaths.ContainsKey(currentValve.Key))
                {
                    valve.ShortestPaths.Add(currentValve.Key, currentValve.Value);
                }
                if (valve.FlowRate > 0 && !currentValve.Key.ShortestPaths.ContainsKey(valve))
                {
                    currentValve.Key.ShortestPaths.Add(valve, currentValve.Value);
                }

                // add neighbors to available valves
                foreach (var neighbor in currentValve.Key.ConnectedValves)
                {
                    if (!visitedValves.ContainsKey(neighbor) && !availableValves.ContainsKey(neighbor))
                    {
                        availableValves.Add(neighbor, currentValve.Value + 1);
                    }
                }

                visitedValves.Add(currentValve.Key, currentValve.Value);
                availableValves.Remove(currentValve.Key);
            }
        }

        public int DetermineMaxPressure()
        {
            return FindMaxPressure(_flowValves, 30).Pressure;
        }

        


        public State FindMaxPressure(List<Valve> flowValves, int remainingTime)
        {
            var currentState = new State();

            currentState.CurrentValve = _startValve;
            currentState.RemainingMinutes = remainingTime;

            foreach (var valve in flowValves)
            {
                NextValve(_startValve, valve, currentState, flowValves);
            }

            return _bestPressureState;

        }

        private void NextValve(Valve lastValve, Valve currentValve, State previousState, List<Valve> flowValves)
        {
            var currentState = new State(previousState);

            currentState.CurrentValve = currentValve;

            // walk shortest path plus 1 min for opening the valve
            currentState.RemainingMinutes = previousState.RemainingMinutes - lastValve.ShortestPaths[currentValve] - 1;

            if (currentState.RemainingMinutes < 0)
            {
                if (currentState.Pressure > _bestPressureState.Pressure)
                {
                    _bestPressureState = currentState;
                }
                return;
            }

            // open valve 
            currentState.Pressure += currentState.RemainingMinutes * currentValve.FlowRate;
            currentState.OpenedValves.Add(currentValve);

            // next unopenend valves
            var remainingValves = flowValves.Except(currentState.OpenedValves).ToList();

            if (remainingValves.Count == 0)
            {
                if (currentState.Pressure > _bestPressureState.Pressure)
                {
                    _bestPressureState = currentState;
                }
                return;
            }

            foreach (var valve in remainingValves)
            {
                NextValve(currentValve, valve, currentState, flowValves);
            }

        }
    }
}