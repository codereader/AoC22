def input_as_integers():
    return get_input_as(lambda s: None if s == '' else int(s))


def get_input_as(mapping_func):
    with open("input.txt") as inputFile:
        return list(map(mapping_func, (inputFile.read().splitlines())))
