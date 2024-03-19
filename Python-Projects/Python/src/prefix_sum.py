def prefix_sum_array(arr: list[int]):
    if len(arr) == 0:
        return []

    res: list[int] = [arr[0]]

    for i in range(1, len(arr)):
        res.append(arr[i] + res[i - 1])

    return res


print(prefix_sum_array([1, 2, -5, 10, 12, 1]))
