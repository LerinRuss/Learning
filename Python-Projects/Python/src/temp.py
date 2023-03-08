from __future__ import annotations

from typing import Union

arr: list[Union[list, int]] = [1, 2, 3, [4, [11, 12], 6], 7, 8, [9, 10]]
stack: list[tuple[list, int]] = []

i = 0
curr_arr = arr
res = []

while curr_arr:
    if i >= len(curr_arr) and not stack:
        break

    if i >= len(curr_arr):
        curr_arr, i = stack.pop()
        i += 1

        continue

    if isinstance(curr_arr[i], list):
        stack.append((curr_arr, i))
        curr_arr = curr_arr[i]
        i = 0

        continue

    res.append(curr_arr[i])
    i += 1

print(res)
