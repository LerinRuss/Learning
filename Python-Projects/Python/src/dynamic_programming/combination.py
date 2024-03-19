def solve(combinations: list[list[int]], possible_numbers: list[int], res: list[list[int]], goal: int) -> None:
    if len(combinations) == 0:
        return

    seq: list[int] = combinations.pop(0)
    calc_sum = sum(seq)

    if calc_sum == goal:
        res.append(seq)

    elif calc_sum < goal:
        for num in possible_numbers:
            copy = seq.copy()
            copy.append(num)

            combinations.append(copy)

    solve(combinations, possible_numbers, res, goal)


possible_numbers = [1, 3, 5]
res = []
combinations = [[i] for i in possible_numbers]

solve(combinations, possible_numbers, res, 6)

print(res)
