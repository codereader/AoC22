from util import *

numbers = input_as_integers()
i = 0
sums = {}
for number in numbers:
    if number is None:
        i += 1
    else:
        sums[i] = sums[i] + number if i in sums else number
result = sorted(sums.values(), reverse=True)
print(f'Part 1: {result[0]}')  # 70374
print(f'Part 2: {sum(result[:3])}')  # 204610
