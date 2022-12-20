import util


def solve():
    lines = util.input_as_strings()

    values = {20: 0, 60: 0, 100: 0, 140: 0, 180: 0, 220: 0}

    cycle = 1
    x = 1

    m = util.create_matrix(6, 40)

    def increase_and_draw(cycle, x):
        cycle += 1
        if cycle in values:
            values[cycle] = x * cycle
        draw(cycle, x)
        return cycle

    # draws the current pixel to the screen
    def draw(cycle, x):
        zero_based_cycle = cycle - 1
        row = zero_based_cycle // 40
        col = zero_based_cycle % 40

        if row >= len(m) or col >= len(m[0]):
            return

        if x - 1 <= col <= x + 1:
            m[row][col] = "#"
        else:
            m[row][col] = "."

    draw(cycle, x)  # draw very first cycle

    for line in lines:
        parts = line.split(' ')
        operation = parts[0]
        if operation == "noop":
            cycle = increase_and_draw(cycle, x)
            continue
        else:
            number = int(parts[1])
            cycle = increase_and_draw(cycle, x)
            x += number
            cycle = increase_and_draw(cycle, x)

    # Part 1
    print(f'Part 1: {sum(values.values())}')  # 12460

    # Part 2
    util.print_matrix(m)  # EZFPRAKL


solve()
