package AdventOfCode;

import java.util.List;

public class Processor {

	public interface ICycleCompletedHandler
	{
		void OnCycleComplete(int cycle, int currentRegisterValue);
	}
	
	private final ICycleCompletedHandler _cycleHandler;
	
	public Processor(ICycleCompletedHandler handler)
	{
		_cycleHandler = handler;
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
				_cycleHandler.OnCycleComplete(cycle, registerValue);
				continue;
			}
			
			if (instruction.startsWith("addx "))
			{
				var value = Integer.parseInt(instruction.substring("addx ".length()));
				
				// First cycle doesn't assign this register yet
				++cycle;
				_cycleHandler.OnCycleComplete(cycle, registerValue);
				
				// Second cycle still doesn't assigns the variable just yet
				++cycle;
				_cycleHandler.OnCycleComplete(cycle, registerValue);
				
				// Full two cycles are complete, now assign the value
				registerValue += value;
				continue;
			}
			
			throw new IllegalArgumentException("Unknown instruction: " + instruction);
		}
	}
}
