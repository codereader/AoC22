def solve():
    import util

    # Returns a set for both ranges in the line.
    # Actually a waste of resources, as we could also just compare a few integers,
    # to get to the correct result, but by using sets we don't have to re-invent subset- and overlapping-logic.
    def get_ranges(line):
        ranges = [int(part) for part in line.replace(',', '-').split('-')]
        return set(range(ranges[0], ranges[1] + 1)), set(range(ranges[2], ranges[3] + 1))

    def are_subsets(ranges): return ranges[0].issubset(ranges[1]) or ranges[1].issubset(ranges[0])
    def are_overlapping(ranges): return len(ranges[0].intersection(ranges[1])) > 0

    ranges_as_sets = list((get_ranges(s) for s in util.input_as_strings()))
    print(f'Part 1: {len(list(filter(are_subsets, ranges_as_sets)))}')  # 513
    print(f'Part 2: {len(list(filter(are_overlapping, ranges_as_sets)))}')  # 878


solve()
