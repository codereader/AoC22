import util
from functools import reduce


def solve():
    lines = util.input_as_strings()

    max_len = len(lines)  # row and column length (we just assume as many columns as rows)
    trees = [0] * len(lines)  # map of trees
    visibility_map = [0] * len(lines)  # map of visibility for each tree (0 == not visible from outside, 1 == visible)

    # Build tree matrix
    for i, line in enumerate(lines):
        trees[i] = [0] * len(line)
        visibility_map[i] = [0] * len(line)
        for j in range(len(line)):
            trees[i][j] = int(line[j])
            visibility_map[i][j] = 0

    # Calculate visibility from left and top, only with integer comparisons and without further nested loops
    def calc_visibility():
        visibility_vertical = {i: -1 for i in range(0, max_len)}  # highest tree for each column from top
        for i in range(max_len):
            max_left = -1  # Highest tree for the current row from the left
            for j in range(max_len):
                current = trees[i][j]
                if current > max_left:
                    max_left = current
                    visibility_map[i][j] = 1
                if current > visibility_vertical[j]:
                    visibility_vertical[j] = current
                    visibility_map[i][j] = 1

    # Reverses the whole matrix (rows and columns)
    def reverse_matrix(matrix):
        matrix.reverse()
        for i in range(max_len):
            matrix[i].reverse()

    # Visibility from left and top
    calc_visibility()

    # Reverse matrix, so we can use the same logic for the other two directions
    reverse_matrix(trees)
    reverse_matrix(visibility_map)

    # Visibility from right and bottom
    calc_visibility()

    visible_tree_count = sum(sum(visibility_map, []))  # Sums up all numbers in the whole matrix
    print(f'Part 1: {visible_tree_count}')  # 1763

    # Part 2
    reverse_matrix(trees)

    max_score = 0
    for i in range(max_len):
        for j in range(max_len):
            current = trees[i][j]
            visibilities = []

            # left
            visible = 0
            x = j - 1
            while x >= 0:
                visible += 1
                if trees[i][x] >= current:
                    break
                x -= 1
            visibilities.append(visible)

            # right
            visible = 0
            x = j + 1
            while x < max_len:
                visible += 1
                if trees[i][x] >= current:
                    break
                x += 1
            visibilities.append(visible)

            # top
            visible = 0
            y = i - 1
            while y >= 0:
                visible += 1
                if trees[y][j] >= current:
                    break
                y -= 1
            visibilities.append(visible)

            # bottom
            visible = 0
            y = i + 1
            while y < max_len:
                visible += 1
                if trees[y][j] >= current:
                    break
                y += 1
            visibilities.append(visible)

            score = reduce(lambda x, y: x*y, visibilities)  # Multiply all elements
            max_score = max(score, max_score)

    print(f'Part 2: {max_score}')  # 671160


solve()
