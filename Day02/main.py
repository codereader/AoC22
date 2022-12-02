def solve():
    import util

    points_for_shape = {'X': 1, 'Y': 2, 'Z': 3}
    points_for_play = {'A Y': 6, 'A Z': 0, 'B Z': 6, 'B X': 0, 'C X': 6, 'C Y': 0, 'A X': 3, 'B Y': 3, 'C Z': 3}
    def calculate_points(game): return sum((points_for_play[line] + points_for_shape[line[2]] for line in game))

    print(f'Part 1: {calculate_points(util.input_as_strings())}')  # 12794

    to_play = {'A Y': 'X', 'A Z': 'Y', 'B Z': 'Z', 'B X': 'X', 'C X': 'Y', 'C Y': 'Z', 'A X': 'Z', 'B Y': 'Y', 'C Z': 'X'}
    print(f'Part 2: {calculate_points(f"{line[0]} {to_play[line]}" for line in util.input_as_strings())}')  # 14979


solve()
