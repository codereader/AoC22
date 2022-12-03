def solve():
    import util

    def get_prio(first, second, third):
        return next((ord(c)-38 if c.isupper() else ord(c)-96 for c in first if c in second and (third is None or c in third)))

    # Part 1
    input_list = util.input_as_strings()
    print(f'Part 1: {sum((get_prio(s[:len(s)//2], s[len(s)//2:], None) for s in input_list))}')  # 7980

    # Part 2
    result = 0
    while len(input_list) > 0:
        result += get_prio(input_list.pop(0), input_list.pop(0), input_list.pop(0))
    print(f'Part 2: {result}')  # 2881


solve()
