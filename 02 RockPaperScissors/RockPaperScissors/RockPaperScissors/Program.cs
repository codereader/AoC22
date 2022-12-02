// See https://aka.ms/new-console-template for more information

using Common;
using System.Reflection;

var txtFile = ResourceUtils.GetDataFromResource(Assembly.GetExecutingAssembly(), $"RockPaperScissors.rockpaperscissors.txt");

// A Y
// A for Rock, B for Paper, and C for Scissors
// X for Rock, Y for Paper, and Z for Scissors

// (1 for Rock, 2 for Paper, and 3 for Scissors
// 0 if you lost, 3 if the round was a draw, and 6 if you won

var score = 0;

foreach (var round in txtFile)
{
    var items = round.Split(" ");

    // I choose rock
    if (items[1] == "X")
    {
        // choosing rock gives 1 extra point
        score++;

        if (items[0] == "A")
        {
            // opponent chooses rock, draw
            score += 3;
        }
        else if (items[0] == "C") 
        {
            // opponent chooses scissors, I win
            score += 6;
        }
        // else opponent chooses paper, I lose
    }

    // I choose paper
    else if (items[1] == "Y")
    {
        // choosing paper gives 2 extra points
        score += 2;

        if (items[0] == "B")
        {
            // opponent chooses paper, draw
            score += 3;
        }
        else if (items[0] == "A")
        {
            // opponent chooses rock, I win
            score += 6;
        }
        // else opponent chooses scissors, I lose
    }

    // I choose scissors
    else 
    {
        // choosing scissors gives 3 extra points
        score += 3;

        if (items[0] == "C")
        {
            // opponent chooses scissors, draw
            score += 3;
        }
        else if (items[0] == "B")
        {
            // opponent chooses paper, I win
            score += 6;
        }
        // else opponent chooses rock, I lose
    }
}

Console.WriteLine("Part 1");
Console.WriteLine(score);

// part 2
// X means you need to lose, Y means you need to end the round in a draw, and Z means you need to win
// (1 for Rock, 2 for Paper, and 3 for Scissors
// 0 if you lost, 3 if the round was a draw, and 6 if you won

var score2 = 0;
foreach (var round in txtFile)
{
    var items = round.Split(" ");

    // opponent uses rock
    if (items[0] == "A")
    {
        if (items[1] == "X")
        {
            // I lose, scissors
            score2 += 3;
        }
        else if (items[1] == "Y")
        {
            // draw, rock
            score2 += 4;
        }
        else
        {
            // I win, paper
            score2 += 8;
        }
    }

    // opponent uses paper
    else if (items[0] == "B")
    {
        if (items[1] == "X")
        {
            // I lose, rock
            score2 += 1;
        }
        else if (items[1] == "Y")
        {
            // draw, paper
            score2 += 5;
        }
        else
        {
            // I win, scissors
            score2 += 9;
        }
    }

    // opponent uses scissors
    else
    {
        if (items[1] == "X")
        {
            // I lose, paper
            score2 += 2;
        }
        else if (items[1] == "Y")
        {
            // draw, scissors
            score2 += 6;
        }
        else
        {
            // I win, rock
            score2 += 7;
        }

    }
}

Console.WriteLine("Part 2");
Console.WriteLine(score2);

Console.ReadLine();