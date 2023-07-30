def solve(arr: list[int], x: int) -> bool:
    if arr is None or len(arr) == 0:
        return False

    for i in range(len(arr)):
        for j in range(i, len(arr)):
            sub_arr_sum = sum(arr[i:j+1])

            if sub_arr_sum == x:
                return True

    return False


"""
[1, 2, 3, 4, 5], X = 7 - True
[3, 6, -1, 3, 4, 10], X = 8 - True
[3, 6, -1, 3, 4, 10], X = 100 - False
[3, 6, -1, 3, 4, 10], X = 13 - False
[], X = 1 - False
[], X = 0 - False
None, X = any - False
[1, 1, 1, 1, 1], X = 5 - True
[5, -5, 4, -4], X = 0 - True
[3, 1, 2, 10, -50], X = 6 - True
[10, -50, 3, 1, 2], X = 6 - True
"""

print(f"0: {solve([1, 2, 3, 4, 5], x= 7)} - True")
print(f"1: {solve([3, 6, -1, 3, 4, 10], x= 8)} - True")
print(f"2: {solve([3, 6, -1, 3, 4, 10], x= 100)} - False")
print(f"3: {solve([3, 6, -1, 3, 4, 10], x= 13)} - False")
print(f"4: {solve([1, 1, 1, 1, 1], x= 5)} - True")
print(f"5: {solve([5, -5, 4, -4], x= 0)} - True")
print(f"6: {solve([3, 1, 2, 10, -50], x= 6)} - True")
print(f"7: {solve([10, -50, 3, 1, 2], x= 6)} - True")
print(f"8: {solve([], x= 1)} - False")
print(f"9: {solve([], x= 0)} - False")
print(f"10: {solve(None, x= 1)}, X = any - False")
