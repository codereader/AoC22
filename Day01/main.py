from util import *

numbers = input_as_integers()
sums = [0]
for number in numbers:
    if number is None:
        sums.append(0)
    else:
        sums[-1] += number
result = sorted(sums, reverse=True)
print(f'Part 1: {result[0]}')  # 70374
print(f'Part 2: {sum(result[:3])}')  # 204610
