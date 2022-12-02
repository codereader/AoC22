package AdventOfCode;

/**
 * Special Round implementation used by Part 2.
 * Since the outcome is already predetermined, the player choice
 * is the moving part here. Overrides two superclass methods to
 * implement the logic.
 */
public class RoundWithRequiredOutcome extends Round {

	RoundWithRequiredOutcome(String inputLine)
	{
		super(inputLine);
	}
	
	@Override
	public Outcome getOutcome()
	{
		// In part 2, the outcome is determined by the strategy guide
		switch (getInputLine().charAt(2))
		{
		case 'X': return Outcome.Loss;
		case 'Y': return Outcome.Tie;
		case 'Z': return Outcome.Win;
		}
		
		throw new IllegalArgumentException("Invalid Outcome");
	}
	
	@Override
	protected char getPlayerChoice()
	{
		// Player choice depends on the opponent's choice and the required outcome
		switch (getOutcome())
		{
		case Loss:
			switch (getOpponentChoice())
			{
			case 'A': return 'Z'; // Rock should beat scissor
			case 'B': return 'X'; // Paper should beat rock
			case 'C': return 'Y'; // Scissor should beat paper
			}
			break;
			
		case Win:
			switch (getOpponentChoice())
			{
			case 'A': return 'Y'; // Rock is beaten by paper
			case 'B': return 'Z'; // Paper is beaten by scissors
			case 'C': return 'X'; // Scissor is beaten by rock
			}
			break;
			
		case Tie:
			switch (getOpponentChoice())
			{
			case 'A': return 'X'; // Rock => Rock
			case 'B': return 'Y'; // Paper => Paper
			case 'C': return 'Z'; // Scissor => Scissor
			}
			break;
		}
		
		throw new IllegalArgumentException("Cannot determine player choice");
	}
	
	
}
