def solve():
    import util

    def get_prio(first, second):
        return next((ord(c)-38 if c.isupper() else ord(c)-96 for c in first if c in second))
    print(f'Part 1: {sum((get_prio(s[:len(s)//2], s[len(s)//2:]) for s in util.input_as_strings()))}')  # 7980


solve()
