import util


def solve():
    lines = util.input_as_strings()

    values = {20:0, 60:0, 100:0, 140:0, 180:0, 220:0}

    cycle = 1
    x = 1

    def increase(cycle, x):
        cycle += 1
        if cycle in values:
            values[cycle] = x * cycle
        return cycle

    for line in lines:
        parts = line.split(' ')
        operation = parts[0]
        if operation == "noop":
            cycle = increase(cycle, x)
            continue
        else:
            number = int(parts[1])
            cycle = increase(cycle, x)
            x += number
            cycle = increase(cycle, x)

    print(f'Part 1: {sum(values.values())}')  # 12460


solve()
