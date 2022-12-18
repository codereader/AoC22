package AdventOfCode;

import java.util.ArrayList;
import AdventOfCode.Common.FileUtils;
import AdventOfCode.Common.Vector2;

public class Day17
{
	final static Vector2 _left = new Vector2(-1, 0);
	final static Vector2 _right = new Vector2(1, 0);
	final static Vector2 _down = new Vector2(0, -1);
	 
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
		
		final var chamber = new Chamber();
		
		var stoppedRocks = 0;
		var nextRock = 0;
		var nextJetIndex = 0;
		
		while (true)
		{
			//System.out.println(String.format("Round %d\n%s", round, chamber.toString()));
			
			if (chamber.FallingRock == null)
			{
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
				
				if (++stoppedRocks == 2022)
				{
					break;
				}
			}
			else
			{
				chamber.FallingRock.Position = chamber.FallingRock.Position.plus(_down);
			}
		}
		
		System.out.println(String.format("[Part1]: Rock Height: %d", chamber.getMaximumRockHeight()));
	}
}
