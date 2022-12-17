package AdventOfCode;


public class Player
{
	public enum State
	{
		Idle,
		ProcessingValve,
	}
	
	public State CurrentState;
	public Valve CurrentValve;
	public int FinishTime; // calculated in minutes left
	public Valve TargetValve;
	
	public Player(Valve currentValve)
	{
		CurrentState = State.Idle;
		CurrentValve = currentValve;
		FinishTime = 0;
		TargetValve = null;
	}
	
	public void AssignValve(Valve valve, int finishTime)
	{
		CurrentState = State.ProcessingValve;
		FinishTime = finishTime;
		TargetValve = valve;
	}
}
