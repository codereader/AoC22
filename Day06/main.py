def solve():
    import util

    input_string = util.input_as_strings()[0]
    index = 0
    for i in range(len(input_string)):
        if i < 3:
            continue
        part = input_string[i - 3:i+1]
        if len(part) == len(set(part)):
            index = i + 1
            break

    print(f'Part 1: {index}')


solve()
