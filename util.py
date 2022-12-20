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