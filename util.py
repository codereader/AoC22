def input_as_integers():
    return get_input_as(lambda s: None if s == '' else int(s))


def input_as_strings():
    return get_input_as(lambda s: s)


def get_input_as(mapping_func):
    with open("input.txt") as inputFile:
        return list(map(mapping_func, (inputFile.read().splitlines())))


def create_matrix(rows, cols):
    m = [0] * rows
    for i in range(rows):
        m[i] = [0] * cols
    return m


def print_matrix(m):
    for row in m:
        for val in row:
            print(val, end="")
        print()


# Could and should be a regex probably, but this was faster for the moment
# Only works with positive integers
def get_integers_in_string(s):
    ints = list()
    ints.append("")
    for c in s:
        if c.isdigit():
            ints[-1] += c
        else:
            if len(ints[-1]) > 0:
                ints.append("")
    return list(int(i) for i in ints if len(i) > 0)
