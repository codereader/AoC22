package AdventOfCode;

import java.util.stream.Collectors;

import AdventOfCode.Common.FileUtils;
import AdventOfCode.Common.Vector2;

public class Day15 {

	public static void main(String[] args)
	{
		var lines = FileUtils.readFile("./input.txt");
		
		var sensors = lines.stream().map(line -> new Sensor(line)).collect(Collectors.toList());
		
		var minPoint = new Vector2(
			sensors.stream().map(s -> s.getMin().getX()).min(Integer::compare).get(),
			sensors.stream().map(s -> s.getMin().getY()).min(Integer::compare).get()
		);
			
		var maxPoint = new Vector2(
			sensors.stream().map(s -> s.getMax().getX()).max(Integer::compare).get(),
			sensors.stream().map(s -> s.getMax().getY()).max(Integer::compare).get()
		);
		
		var numPositionsThatCannotContainBeacon = 0;
		for (var x = minPoint.getX() - 700; x <= maxPoint.getX() + 700; ++x)
		{
			var point = new Vector2(x, 2000000);
			
			if (sensors.stream().anyMatch(s -> s.getBeaconPosition().equals(point)))
			{
				continue;
			}
			
			if (sensors.stream().anyMatch(s -> !s.pointCanHaveBeacon(point)))
			{
				//System.out.println(String.format("Position %d,%d cannot contain a beacon", point.getX(), point.getY()));
				
				++numPositionsThatCannotContainBeacon;
			}
			else
			{
				//System.out.println(String.format("Position %d,%d can contain a beacon", point.getX(), point.getY()));
			}
		}
		
		System.out.println(String.format("[Part1] Number of positions that cannot contain any beacon: %d", numPositionsThatCannotContainBeacon));
	}
}
