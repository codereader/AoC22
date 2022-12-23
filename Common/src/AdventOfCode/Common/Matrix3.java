package AdventOfCode.Common;

public class Matrix3
{
	// Values are column-major
	private final int[] _m;
	
	// Construct a matrix using the given elements in column major
	public Matrix3(int xx, int yx, int zx, 
				   int xy, int yy, int zy, 
				   int xz, int yz, int zz)
	{
		_m = new int[]
		{
			xx, yx, zx,
			xy, yy, zy, 
		    xz, yz, zz
		};
	}
	
	// This matrix post-multiplied by other
	public Matrix3 getMultipliedBy(Matrix3 other)
	{
		return new Matrix3(
			// Column 1
			_m[0]*other._m[0] + _m[3]*other._m[1] + _m[6]*other._m[2],
			_m[1]*other._m[0] + _m[4]*other._m[1] + _m[7]*other._m[2],
			_m[2]*other._m[0] + _m[5]*other._m[1] + _m[8]*other._m[2],
			
			// Column 2
			_m[0]*other._m[3] + _m[3]*other._m[4] + _m[6]*other._m[5], 
			_m[1]*other._m[3] + _m[4]*other._m[4] + _m[7]*other._m[5], 
			_m[2]*other._m[3] + _m[5]*other._m[4] + _m[8]*other._m[5],
			
			// Column 3
			_m[0]*other._m[6] + _m[3]*other._m[7] + _m[6]*other._m[8], 
			_m[1]*other._m[6] + _m[4]*other._m[7] + _m[7]*other._m[8], 
			_m[2]*other._m[6] + _m[5]*other._m[7] + _m[8]*other._m[8] 
		);
	}
	
	// Transform the given Vec2 with the z component assumed to be 1
	public Vector2 transformPoint(Vector2 input)
	{
		var x = _m[0] * input.getX() + _m[3] * input.getY() + _m[6] * 1;
		var y = _m[1] * input.getX() + _m[4] * input.getY() + _m[7] * 1;

		return new Vector2(x, y);
	}
	
	public Vector2 transformDirection(Vector2 input)
	{
		var x = _m[0] * input.getX() + _m[3] * input.getY();
		var y = _m[1] * input.getX() + _m[4] * input.getY();

		return new Vector2(x, y);
	}
	
	public Matrix3 getTransposed()
	{
		return new Matrix3(
			_m[0], _m[3], _m[6],
			_m[1], _m[4], _m[7],
			_m[2], _m[5], _m[8]
		);
	}
	
	// Counter-clockwise rotation by 90 degrees
	public static Matrix3 RotationCcw90()
	{
		return new Matrix3(
			 0, -1, 0,
			 1, 0, 0,
			 0, 0, 1
		);
	}
	
	public static Matrix3 RotationCw90()
	{
		return RotationCcw90().getTransposed();
	}
	
	// 180 degrees rotation is the same as mirroring about both x,y axes
	public static Matrix3 Rotation180()
	{
		return new Matrix3(
			 -1, 0, 0,
			  0,-1, 0,
			  0, 0, 1
		);
	}
	
	public static Matrix3 Translation(Vector2 t)
	{
		return new Matrix3(
		    1, 0, 0,
		    0, 1, 0,
		    t.getX(), t.getY(), 1
		);
	}
	
	public static Matrix3 TranslationX(int x)
	{
		return Translation(new Vector2(x, 0));
	}
	
	public static Matrix3 TranslationY(int y)
	{
		return Translation(new Vector2(0, y));
	}
	
	public static Matrix3 MirrorX()
	{
		return new Matrix3(
		    -1, 0, 0,
		     0, 1, 0,
		     0, 0, 1
		);
	}
	
	public static Matrix3 MirrorY()
	{
		return new Matrix3(
		    1,  0, 0,
		    0, -1, 0,
		    0,  0, 1
		);
	}
	
	public static Matrix3 MirrorX(int anchorX)
	{
		return Translation(new Vector2(-anchorX, 0))
				.getMultipliedBy(MirrorX())
				.getMultipliedBy(Translation(new Vector2(anchorX, 0)));
	}
	
	public static Matrix3 MirrorY(int anchorY)
	{
		return Translation(new Vector2(0, -anchorY))
				.getMultipliedBy(MirrorY())
				.getMultipliedBy(Translation(new Vector2(0, anchorY)));
	}
	
	public static Matrix3 Identity()
	{
		return new Matrix3(
			1,0,0,
			0,1,0,
			0,0,1
		);
	}
	
	@Override
	public String toString()
	{
		var text = new StringBuilder();
		
		text.append(String.format("| %d %d %d |\n", _m[0], _m[3], _m[6]));
		text.append(String.format("| %d %d %d |\n", _m[1], _m[4], _m[7]));
		text.append(String.format("| %d %d %d |\n", _m[2], _m[5], _m[8]));
		
		return text.toString();
	}
}
