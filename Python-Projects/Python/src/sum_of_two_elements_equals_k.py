def solve(arr: list[int], k: int):
    numbers: dict[int, int] = dict()
    res: list[tuple[int, int]] = []

    for i, elem in enumerate(arr):
        candidate = k - elem

        if candidate in numbers:
            res.append((numbers[candidate], i))

        numbers[elem] = i

    return res
