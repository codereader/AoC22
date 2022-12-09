using Common;
using System.Numerics;
using System.Reflection;

var input = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), $"Rope.input.txt");

var moveVectors = new Dictionary<string, Vector2>
{
    { "U", new Vector2(0, 1) },
    { "D", new Vector2(0, -1) },
    { "L", new Vector2(-1, 0) },
    { "R", new Vector2(1, 0) }
};

var ropePositions = new List<Vector2>();
for (int i = 0; i < 10; i++)
{
    ropePositions.Add(new Vector2(0, 0));
}
var secondPositions = new HashSet<Vector2>();
var lastPositions = new HashSet<Vector2>();

foreach (var line in input)
{
    // U 13
    var dir = line[0];
    var amount = int.Parse(line.Substring(2));
    var moveDir = moveVectors[dir.ToString()];

    for (int i = 0; i < amount; i++)
    {
        ropePositions[0] += moveDir;

        for (int pos = 1; pos < ropePositions.Count; pos++)
        {
            var currentPos = ropePositions[pos];
            var lastPos = ropePositions[pos - 1];

            var dist = lastPos - currentPos;

            if (Math.Abs(dist.X) > 1 && Math.Abs(dist.Y) < 2)
            {
                // move 1 in the direction of dist
                // Y moves in line with headpos in case it was diagonal
                ropePositions[pos] = new Vector2(currentPos.X + dist.X / Math.Abs(dist.X), lastPos.Y);
            }
            else if (Math.Abs(dist.Y) > 1 && Math.Abs(dist.X) < 2)
            {
                ropePositions[pos] = new Vector2(lastPos.X, currentPos.Y + dist.Y / Math.Abs(dist.Y));
            }
            // middle pieces can move diagonally
            else if (Math.Abs(dist.X) > 1 && Math.Abs(dist.Y) > 1)
            {
                ropePositions[pos] = new Vector2(currentPos.X + dist.X / Math.Abs(dist.X), currentPos.Y + dist.Y / Math.Abs(dist.Y));
            }
        }
        secondPositions.Add(ropePositions[1]);
        lastPositions.Add(ropePositions.Last());
    }

}
Console.WriteLine(secondPositions.Count);
Console.WriteLine(lastPositions.Count);
