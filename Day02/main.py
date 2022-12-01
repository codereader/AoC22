def solve():
    import util

    points_for_shape = {'X': 1, 'Y': 2, 'Z': 3}
    points_for_play = {'A Y': 6, 'A Z': 0, 'B Z': 6, 'B X': 0, 'C X': 6, 'C Y': 0, 'A X': 3, 'B Y': 3, 'C Z': 3}
    points = sum((points_for_play[line] + points_for_shape[line[2]] for line in util.input_as_strings()))
    print(f'Part 1: {points}')  # 12794


solve()
