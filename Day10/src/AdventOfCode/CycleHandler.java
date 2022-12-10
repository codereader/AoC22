package AdventOfCode;

import java.util.ArrayList;

public class CycleHandler implements Processor.ICycleCompletedHandler
{
	private final ArrayList<Integer> _signalStrengths;
	
	public CycleHandler()
	{
		_signalStrengths = new ArrayList<Integer>();
	}
	
	@Override
	public void OnCycleComplete(int cycle, int currentRegisterValue)
	{
		if ((cycle - 20) % 40 == 0)
		{
			System.out.println(String.format("Signal strength at cycle %d = %d (register = %d)", cycle, cycle * currentRegisterValue, currentRegisterValue));
			_signalStrengths.add(cycle * currentRegisterValue);
		}
	}
	
	public int getTotalSignalStrength()
	{
		return _signalStrengths.stream().reduce(Integer::sum).get();
	}
}
