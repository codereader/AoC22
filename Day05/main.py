def solve():
    import util
    import re

    lines = util.input_as_strings()
    stacks = list((list() for _ in range(0, 9)))  # 9 crates hard-coded

    while True:
        line = lines.pop(0)
        i = 0
        if '1' in line:  # stop if line contains a number, as we are in the line with crate indices
            lines.pop(0)
            break
        while i < len(stacks):
            crate = line[1 + 4 * i]
            if crate != " ":
                stacks[i].insert(0, crate)
            i += 1

    for line in lines:
        match = re.match("move (\\d+) from (\\d+) to (\\d+)", line)
        count = int(match.group(1))
        from_stack = stacks[int(match.group(2)) - 1]
        to_stack = stacks[int(match.group(3)) - 1]
        for i in range(count):
            pop_index = len(from_stack)-1
            to_stack.append(from_stack.pop(pop_index))

    print(f'Part 1: {"".join((stack[-1] for stack in stacks))}')  # JCMHLVGMG
    

solve()
