package AdventOfCode;

public class CrtMonitor implements Processor.ICycleCompletedHandler {

	private String _screen; // all rows in one string
	
	public CrtMonitor()
	{
		_screen = "";
	}
	
	@Override
	public void OnCycleComplete(int cycle, int currentRegisterValue)
	{
		int col = (cycle - 1) % 40;
		int spritePosition = currentRegisterValue;
		
		if (col >= spritePosition - 1 && col <= spritePosition + 1)
		{
			_screen += '#';
		}
		else
		{
			_screen += '.';
		}
	}

	public String toString()
	{
		var result = new StringBuilder();
		
		for (int i = 0; i + 40 < _screen.length(); i += 40)
		{
			result.append(_screen.substring(i, i + 40));
			result.append("\n");
		}
		
		return result.toString();
	}
}
