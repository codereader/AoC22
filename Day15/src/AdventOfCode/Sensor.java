package AdventOfCode;

import java.util.regex.Pattern;
import AdventOfCode.Common.Vector2;

public class Sensor
{
	public Vector2 _position;
	public Vector2 _beacon;
	public int _beaconDistance;
	
	public Sensor(String line)
	{
		// Sensor at x=2, y=18: closest beacon is at x=-2, y=15
		var pattern = Pattern.compile("Sensor at x=([\\d\\-]+), y=([\\d\\-]+): closest beacon is at x=([\\d\\-]+), y=([\\d\\-]+)", 
				Pattern.CASE_INSENSITIVE);
	    var matcher = pattern.matcher(line);
	    
	    if (!matcher.find()) throw new IllegalArgumentException("Line not supported: " + line);
	    
	    _position = new Vector2(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
	    _beacon = new Vector2(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));
	    
	    _beaconDistance = getDistance(_position, _beacon);
	}
	
	public Vector2 getMin()
	{
		return new Vector2(
			Math.min(_position.getX() - _beaconDistance, _position.getX() + _beaconDistance),
			Math.min(_position.getY() - _beaconDistance, _position.getY() + _beaconDistance)
		);
	}
	
	public Vector2 getMax()
	{
		return new Vector2(
			Math.max(_position.getX() - _beaconDistance, _position.getX() + _beaconDistance),
			Math.max(_position.getY() - _beaconDistance, _position.getY() + _beaconDistance)
		);
	}
	
	public Vector2 getBeaconPosition()
	{
		return _beacon;
	}
	
	public int getBeaconDistance()
	{
		return _beaconDistance;
	}
	
	public boolean pointCanHaveBeacon(Vector2 point)
	{
		return getDistance(point,  _position) > _beaconDistance;
	}
	
	public Vector2 getPosition()
	{
		return _position;
	}
	
	private static int getDistance(Vector2 a, Vector2 b)
	{
		return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
	}
	
	@Override
	public String toString()
	{
		return String.format("Sensor at %d,%d", _position.getX(), _position.getY());
	}
}
