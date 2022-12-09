import util


def solve():
    lines = util.input_as_strings()

    head = [0, 0]
    tail = [0, 0]
    tail_visited = ["0,0"]

    for line in lines:
        parts = line.split(' ')
        direction = parts[0]
        steps = int(parts[1])
        for _ in range(steps):
            if direction == "L":
                head[0] -= 1
            if direction == "R":
                head[0] += 1
            if direction == "D":
                head[1] -= 1
            if direction == "U":
                head[1] += 1

            tail_moves = False if abs(head[0] - tail[0]) <= 1 and abs(head[1] - tail[1]) <= 1 else True
            if tail_moves:
                if direction == "L":
                    tail = (head[0] + 1, head[1])
                if direction == "R":
                    tail = (head[0] - 1, head[1])
                if direction == "D":
                    tail = (head[0], head[1] + 1)
                if direction == "U":
                    tail = (head[0], head[1] - 1)
                tail_visited.append(f'{tail[0]},{tail[1]}')

    print(f'Part 1: {len(set(tail_visited))}')  # 5513


solve()
