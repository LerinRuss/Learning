def solve(arr: list[int], X: int) -> tuple[int, int]:
    if len(arr) == 0 or len(arr) == 1:
        return ()

    complements = dict()

    for i, num in enumerate(arr):
        complement = X - num

        if complement in complements:
            return i, complements[complement]

        complements[num] = i

    return ()


"""
[0, -1, 1, 7, 4] X=8 - True
[0, -1, 1, 7, 4, 9] X=8 - True
[0, 1, 7, 4] X=8 - True
[0, -1, -7, -10] X=-8 - True
[0, 0, 0, 0] X=0 - True
[0] X=0 - False
[] X=0 - False
[0, 1, 8, 1, 0] X=8 - False
[0, 8, -1, 2] X=8 - False
[5, 4, 3, 1] X=4 - True
[3, 1, 5, 4] X=4 - True
[1, 3, 4, 5] X=4 - True
[-1, -1, -1] X=3 - False
[1, 1, 1] X=3 - False
"""

print(f"1: {solve([0, -1, 1, 7, 4], X=8)} - True")
print(f"2: {solve([0, -1, 1, 7, 4, 9], X=8)} - True")
print(f"3: {solve([0, 1, 7, 4], X=8)} - True")
print(f"4: {solve([0, -1, -7, -10], X=-8)} - True")
print(f"5: {solve([0, 0, 0, 0], X=0)} - True")
print(f"6: {solve([0], X=0)} - False")
print(f"7: {solve([], X=0)} - False")
print(f"8: {solve([-1, 1, 8, 1, -1], X=8)} - False")
print(f"9: {solve([-1, 8, -1, 2], X=8)} - False")
print(f"10: {solve([5, 4, 3, 1], X=4)} - True")
print(f"11: {solve([3, 1, 5, 4], X=4)} - True")
print(f"12: {solve([1, 3, 4, 5], X=4)} - True")
print(f"13: {solve([-1, -1, -1], X=3)} - False")
print(f"14: {solve([1, 1, 1], X=3)} - False")
