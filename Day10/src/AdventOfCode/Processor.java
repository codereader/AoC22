package AdventOfCode;

import java.util.ArrayList;
import java.util.List;

public class Processor {

	public interface ICycleCompletedHandler
	{
		void OnCycleComplete(int cycle, int currentRegisterValue);
	}
	
	private final ArrayList<ICycleCompletedHandler> _cycleHandlers;
	
	public Processor(ICycleCompletedHandler... handlers)
	{
		_cycleHandlers = new ArrayList<ICycleCompletedHandler>();
		
		for (var handler : handlers)
		{
			_cycleHandlers.add(handler);
		}
	}
	
	public void processInstructions(List<String> instructions)
	{
		int registerValue = 1;
		int cycle = 0;
		
		for (var instruction : instructions)
		{
			if (instruction.startsWith("noop"))
			{
				++cycle;
				notifyCycleHandlers(cycle, registerValue);
				continue;
			}
			
			if (instruction.startsWith("addx "))
			{
				var value = Integer.parseInt(instruction.substring("addx ".length()));
				
				// First cycle doesn't assign this register yet
				++cycle;
				notifyCycleHandlers(cycle, registerValue);
				
				// Second cycle still doesn't assigns the variable just yet
				++cycle;
				notifyCycleHandlers(cycle, registerValue);
				
				// Full two cycles are complete, now assign the value
				registerValue += value;
				continue;
			}
			
			throw new IllegalArgumentException("Unknown instruction: " + instruction);
		}
	}
	
	public void notifyCycleHandlers(int cycle, int registerValue)
	{
		for (var handler : _cycleHandlers)
		{
			handler.OnCycleComplete(cycle, registerValue);
		}
	}
}
