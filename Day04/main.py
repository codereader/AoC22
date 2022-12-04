def solve():
    import util

    def get_range(line):
        ranges = [int(part) for part in line.replace(',', '-').split('-')]
        return set(range(ranges[0], ranges[1] + 1)), set(range(ranges[2], ranges[3] + 1))

    def are_subsets(ranges): return ranges[0].issubset(ranges[1]) or ranges[1].issubset(ranges[0])

    ranges_as_sets = list((get_range(s) for s in util.input_as_strings()))
    print(f'Part 1: {len(list(filter(are_subsets, ranges_as_sets)))}')  # 513


solve()
