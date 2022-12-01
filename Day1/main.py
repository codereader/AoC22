def int_or_none(s):
    return None if s == '' else int(s)


def get_input_as(mapping_func):
    with open("Day1/input.txt") as inputFile:
        return list(map(mapping_func, (inputFile.read().splitlines())))


def solve():
    numbers = get_input_as(int_or_none)
    i = 0
    sums = {}
    for number in numbers:
        if number is None:
            i += 1
        else:
            sums[i] = sums[i] + number if i in sums else number
    result = sorted(sums.values(), reverse=True)
    print(f'Part 1: {result[0]}')
    print(f'Part 2: {sum(result[:3])}')


solve()
