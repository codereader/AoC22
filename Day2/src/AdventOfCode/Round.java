package AdventOfCode;

public class Round {
	
	private String _inputLine;
	
	public enum Outcome
	{
		Win(6),
		Tie(3),
		Loss(0);
		
		private int _score;
		
		Outcome(int score)
		{
			_score = score;
		}
		
		public int getScore()
		{
			return _score;
		}
	}
	
	public Round(String inputLine) {
		
		_inputLine = inputLine;
		
		if (_inputLine.length() != 3)
		{
			throw new IllegalArgumentException("inputLine must be of length 3");
		}
	}
	
	protected final String getInputLine()
	{
		return _inputLine;
	}
	
	public Outcome getOutcome()
	{
		switch (getOpponentChoice())
		{
		case 'A': // Rock
			switch (getPlayerChoice())
			{
			case 'X': return Outcome.Tie; // Rock
			case 'Y': return Outcome.Win; // Paper
			case 'Z': return Outcome.Loss; // Scissor
			}
			break;
			
		case 'B': // Paper
			switch (getPlayerChoice())
			{
			case 'X': return Outcome.Loss; // Rock
			case 'Y': return Outcome.Tie; // Paper
			case 'Z': return Outcome.Win; // Scissor
			}
			break;
			
		case 'C': // Scissor
			switch (getPlayerChoice())
			{
			case 'X': return Outcome.Win; // Rock
			case 'Y': return Outcome.Loss; // Paper
			case 'Z': return Outcome.Tie; // Scissor
			}
			break;
		}
		
		throw new IllegalArgumentException("This is rock, paper, scissors");
	}
	
	protected char getPlayerChoice()
	{
		return _inputLine.charAt(2);
	}
	
	protected char getOpponentChoice()
	{
		return _inputLine.charAt(0);
	}
	
	public final int getOutcomeScore()
	{
		return getOutcome().getScore();
	}
	
	public final int getPlayerChoiceScore()
	{
		switch (getPlayerChoice())
		{
		case 'X': return 1; // Rock
		case 'Y': return 2; // Paper
		case 'Z': return 3; // Scissor
		default: throw new IllegalArgumentException("This is rock, paper, scissors");
		}
	}
	
	public final int getRoundScore()
	{
		return getOutcomeScore() + getPlayerChoiceScore();
	}
}
