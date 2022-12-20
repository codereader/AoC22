import util


class Monkey:
    def __init__(self, id):
        self.id = id
        self.items = list()
        self.operation = None
        self.divide_by = 0
        self.target_1 = 0
        self.target_2 = 0
        self.inspections = 0

    def set_starting_items(self, s):
        self.items = util.get_integers_in_string(s)

    def set_operation(self, s):
        self.operation = s.replace("  Operation: new = ", "")

    def set_divisible_by(self, s):
        self.divide_by = util.get_integers_in_string(s)[0]

    def set_target(self, s):
        target = util.get_integers_in_string(s)[0]
        if "true" in s:
            self.target_1 = target
        else:
            self.target_2 = target


def solve():
    lines = util.input_as_strings()

    monkeys = dict()

    m = Monkey("dummy")
    for line in lines:
        if line.startswith("Monkey "):
            m = Monkey(line)
            monkeys[util.get_integers_in_string(line)[0]] = m
        elif line.strip().startswith("Starting"):
            m.set_starting_items(line)
        elif line.strip().startswith("Operation"):
            m.set_operation(line)
        elif line.strip().startswith("Test"):
            m.set_divisible_by(line)
        elif line.strip().startswith("If true") or line.strip().startswith("If false"):
            m.set_target(line)

    def play(rounds):
        for i in range(rounds):
            for m in monkeys.values():
                item_count = len(m.items)
                for _ in range(item_count):
                    item = m.items.pop(0)
                    op = m.operation.replace("old", str(item))
                    result = eval(op)
                    result = result // 3
                    target = m.target_1 if result % m.divide_by == 0 else m.target_2
                    monkeys[target].items.append(result)
                    m.inspections += 1

    play(20)

    inspections = sorted((m.inspections for m in monkeys.values()), reverse=True)

    # Part 1
    print(f'Part 1: {inspections[0] * inspections[1]}')  # 113220


solve()
