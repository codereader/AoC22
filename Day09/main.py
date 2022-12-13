import util


def solve():
    lines = util.input_as_strings()

    def solve_with_number_of_knots(number_of_knots):
        head = [0, 0]
        knots = [[0, 0] for _ in range(number_of_knots-1)]
        knots.insert(0, head)
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

                for i in range(1, number_of_knots):
                    current_knot = knots[i]
                    knot_before = knots[i-1]
                    delta_x = knot_before[0] - current_knot[0]
                    delta_y = knot_before[1] - current_knot[1]
                    knot_moves = False if abs(knot_before[0] - current_knot[0]) <= 1 and abs(knot_before[1] - current_knot[1]) <= 1 else True
                    if knot_moves:
                        x = 0
                        y = 0
                        if abs(delta_x) > 1 and abs(delta_y) > 1:  # diagonal in the direction of the knot in front
                            x = (delta_x // abs(delta_x)) * -1
                            y = (delta_y // abs(delta_y)) * -1
                        elif abs(delta_x) > 1:  # to the left or right of the node in front
                            x = -1 if delta_x > 0 else 1
                        elif abs(delta_y) > 1:  # below or above of the node in front
                            y = -1 if delta_y > 0 else 1
                        current_knot = (knot_before[0] + x, knot_before[1] + y)
                        knots[i] = current_knot
                        if i == number_of_knots - 1:
                            tail_visited.append(f'{current_knot[0]},{current_knot[1]}')
        return tail_visited

    print(f'Part 1: {len(set(solve_with_number_of_knots(2)))}')  # 5513
    print(f'Part 2: {len(set(solve_with_number_of_knots(10)))}')  # 2427


solve()
