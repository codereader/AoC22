package AdventOfCode;

/**
 * Represents a single round of Rock/Paper/Scissors
 * 
 * Pass the original line (like "A Y") to the constructor.
 * Use the getRoundScore() method to get the score of this round.
 */
public class Round {
	
	private final String _inputLine;
	
	// Enumeration of possible outcomes, with the score attached
	public enum Outcome
	{
		Win(6),
		Tie(3),
		Loss(0);
		
		private final int _score;
		
		Outcome(int score)
		{
			_score = score;
		}
		
		public final int getScore()
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
	
	// Gets or calculates the outcome of this round
	protected Outcome getOutcome()
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
	
	// Gets or calculate the player's choice
	protected char getPlayerChoice()
	{
		return _inputLine.charAt(2);
	}
	
	// Returns the opponent's choice (A, B or C)
	protected final char getOpponentChoice()
	{
		return _inputLine.charAt(0);
	}
	
	private final int getOutcomeScore()
	{
		return getOutcome().getScore();
	}
	
	private final int getPlayerChoiceScore()
	{
		switch (getPlayerChoice())
		{
		case 'X': return 1; // Rock
		case 'Y': return 2; // Paper
		case 'Z': return 3; // Scissor
		default: throw new IllegalArgumentException("This is rock, paper, scissors");
		}
	}
	
	/**
	 * @return the total score of this round,
	 * which is adding the score of the outcome to the one for the player's choice.
	 */
	public final int getRoundScore()
	{
		return getOutcomeScore() + getPlayerChoiceScore();
	}
}
