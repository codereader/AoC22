def solve():
    import util
    import re

    def get_stacks_and_remaining_lines():
        lines = util.input_as_strings()
        stacks = list((list() for _ in range(0, 9)))  # 9 crates hard-coded

        while True:
            line = lines.pop(0)
            if '1' in line:  # stop if line contains a number, as we are in the line with crate indices
                lines.pop(0)  # also remove the blank line below
                break
            for i in range(len(stacks)):
                crate = line[1 + 4 * i]  # starting at index 1; every fourth character is a crate identifier
                if crate != " ":  # empty string means no crate -> skip
                    stacks[i].insert(0, crate)
                i += 1
        return stacks, lines

    def move_crates(stacks, lines, pop_index_func):
        for line in lines:
            match = re.match("move (\\d+) from (\\d+) to (\\d+)", line)  # just extract the numbers
            number_of_crates_to_move = int(match.group(1))
            from_stack = stacks[int(match.group(2)) - 1]
            to_stack = stacks[int(match.group(3)) - 1]
            for i in range(number_of_crates_to_move):
                pop_index = pop_index_func(from_stack, i, number_of_crates_to_move)
                to_stack.append(from_stack.pop(pop_index))

    def get_stacks_and_move(part_number, pop_index_func):
        stacks, lines = get_stacks_and_remaining_lines()
        move_crates(stacks, lines, pop_index_func)
        print(f'Part {part_number}: {"".join((stack[-1] for stack in stacks))}')

    # Part 1
    get_stacks_and_move(1, lambda stack, i, number_of_crates_to_move: len(stack)-1)   # JCMHLVGMG

    # Part 2
    get_stacks_and_move(1, lambda stack, i, number_of_crates_to_move: len(stack)-(number_of_crates_to_move-i))  # LVMRWSSPZ


solve()
