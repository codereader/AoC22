def solve():
    import util

    def get_index(marker_length):
        input_string = util.input_as_strings()[0]
        index = 0
        for i in range(len(input_string)):
            if i < (marker_length - 1):  # skip until we have enough characters to work with
                continue
            part = input_string[i - (marker_length - 1):i+1]  # get the slice which we are going to inspect
            if len(part) == len(set(part)):  # if all characters are unique we remember that index and exit
                index = i + 1
                break
        return index

    print(f'Part 1: {get_index(4)}')  # 1848
    print(f'Part 2: {get_index(14)}')  # 2308


solve()
