package AdventOfCode;

public class ValveCombination
{
	public final Valve StartValve;
	public int CombinedCostToOpen = 0;
	public int CombinedFlowRateIncrease = 0;
	public double FlowRatePerCost = 0;
	public int GainedPressureVolume = 0;
	public final String Name;
	public final Valve FirstValve;
	
	public ValveCombination(Valve currentValve, int timeLeft, Valve... valves)
	{
		StartValve = currentValve;
		FirstValve = valves[0];
		
		var name = new StringBuilder();
		currentValve = StartValve;
		for (var valve : valves)
		{
			name.append(valve.getName());
			name.append('|');
			
			var cost = currentValve.getCostToOpen(valve);

			timeLeft -= cost;
			CombinedCostToOpen += cost;
			CombinedFlowRateIncrease += valve.getFlowRate();
			GainedPressureVolume += timeLeft * valve.getFlowRate();
			currentValve = valve;
		}
		Name = name.toString();
		
		FlowRatePerCost = CombinedFlowRateIncrease / (double) + CombinedCostToOpen;
	}
    
    @Override
    public String toString()
    {
    	return String.format("Valve %s (increase: %d, cost: %d, ratio: %f) - Gain = %d", 
			Name, CombinedFlowRateIncrease, CombinedCostToOpen, 
			FlowRatePerCost, GainedPressureVolume);
    }
}
