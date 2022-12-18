package AdventOfCode;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

import AdventOfCode.Common.FileUtils;
import AdventOfCode.Common.LongVector2;

public class Day17
{
	final static LongVector2 _left = new LongVector2(-1, 0);
	final static LongVector2 _right = new LongVector2(1, 0);
	final static LongVector2 _down = new LongVector2(0, -1);
	 
	public static void main(String[] args)
	{
		final var jetDirections = FileUtils.readFile("./input.txt").get(0);
		
		final var rocks = new ArrayList<Rock>(); 

		rocks.add(new Rock("####"));
		rocks.add(new Rock(
			".#.",
			"###",
			".#.")
		);
		rocks.add(new Rock(
			"..#",
			"..#",
			"###")
		);
		rocks.add(new Rock(
			"#",
			"#",
			"#",
			"#")
		);
		rocks.add(new Rock(
			"##",
			"##")
		);
		
		runSimulation(1, 2022, rocks, jetDirections);
		runSimulation(2, 1000000000000L, rocks, jetDirections);
	}
	
	private static void runSimulation(int part, long numberOfRocks, ArrayList<Rock> rocks, String jetDirections)
	{
		var starts = Instant.now();
		
		final var chamber = new Chamber();
		
		var stoppedRocks = 0L;
		var nextRock = 0;
		var nextJetIndex = 0;
		var round = 0L;
		var situations = new HashMap<Integer, Situation>();
		var cycleFound = false;
		
		while (true)
		{
			//System.out.println(String.format("Round %d\n%s", round, chamber.toString()));
			if (++round % 500000 == 0)
			{
				System.out.println(String.format("Round %d, Dropped rocks = %d", round, stoppedRocks));
			}
			
			//System.out.println(String.format("Round %d: %d", round, chamber.getMaximumRockHeight()));
			
			if (chamber.FallingRock == null)
			{				
				if (!cycleFound && round > 150000)
				{
					// Check the current configuration against known states
					var situation = new Situation();
					
					situation.Checksum = chamber.getChecksum();
					situation.StoppedRocks = stoppedRocks;
					situation.HeadOfGrid = chamber.getHeadOfGrid();
					situation.NextRock = nextRock % rocks.size();
					situation.NextJetDirection = nextJetIndex % jetDirections.length();
					situation.StackHeight = chamber.getMaximumRockHeight();
					situation.FirstNonSolidRow = chamber.getFirstNonSolidRow();
					
					var hash = situation.hashCode();
					
					if (situations.containsKey(hash))
					{
						var storedSituation = situations.get(hash);
						
						if (storedSituation.equals(situation))
						{
							if (++storedSituation.HitCount > 2)
							{
								System.out.println(String.format("Found the same situation %d, first non-solid row = %d", hash, storedSituation.FirstNonSolidRow));
								
								var heightGrowth = chamber.getFirstNonSolidRow() - storedSituation.FirstNonSolidRow;
								
								if (heightGrowth > 0)
								{
									System.out.println(String.format("Growth: %d", heightGrowth));
									
									var rockGrowth = stoppedRocks - storedSituation.StoppedRocks;
									System.out.println(String.format("Additional stopped Rocks: %d", rockGrowth));
									
									// Fast-forward as far as we can
									var numberOfCycles = Math.floorDiv(numberOfRocks - stoppedRocks, rockGrowth);

									// Set new height and forward the rock count
									chamber.setFirstNonSolidRow(chamber.getFirstNonSolidRow() + heightGrowth * numberOfCycles);
									stoppedRocks += rockGrowth * numberOfCycles;
									
									System.out.println(String.format("Resuming at rock count: %d", stoppedRocks));
									System.out.println(String.format("Height is now at: %d", chamber.getMaximumRockHeight()));
									
									cycleFound = true;
								}
							}
							else
							{
								storedSituation.StackHeight = chamber.getMaximumRockHeight();
								storedSituation.FirstNonSolidRow = chamber.getFirstNonSolidRow();
								storedSituation.StoppedRocks = stoppedRocks;
							}
						}
					}
					else
					{
						situations.put(hash, situation);
					}
				}
				
				var rock = (Rock)rocks.get((nextRock++) % rocks.size()).clone();
				chamber.spawnRock(rock);
				continue;
			}
			
			// Get pushed by gas jet
			var direction = jetDirections.charAt((nextJetIndex++) % jetDirections.length()) == '>' ? _right : _left;
			
			if (chamber.rockCanMoveTo(chamber.FallingRock, direction))
			{
				chamber.FallingRock.Position = chamber.FallingRock.Position.plus(direction);
			}
			
			// Fall down one unit
			if (!chamber.rockCanMoveTo(chamber.FallingRock, _down))
			{
				// Come to rest
				chamber.embedRock(chamber.FallingRock);
				chamber.FallingRock = null;
				
				if (++stoppedRocks == numberOfRocks)
				{
					break;
				}
			}
			else
			{
				chamber.FallingRock.Position = chamber.FallingRock.Position.plus(_down);
			}
		}
		
		System.out.println(String.format("[Part%d]: Rock Height: %d", part, chamber.getMaximumRockHeight()));
		
		var ends = Instant.now();
		System.out.println(String.format("Duration: %s", Duration.between(starts, ends)));
	}
	
}
