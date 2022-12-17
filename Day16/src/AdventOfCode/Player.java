package AdventOfCode;


public class Player
{
	public enum State
	{
		Idle,
		ProcessingValve,
	}
	
	public final String Name;
	public State CurrentState;
	public Valve CurrentValve;
	public int FinishTime; // calculated in minutes left
	public Valve TargetValve;
	
	public Player(String name, Valve currentValve)
	{
		Name = name;
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
