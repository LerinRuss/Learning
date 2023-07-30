def solve(arr: list[int], x: int):
    if arr is None or len(arr) == 0:
        return False

    sum = 0
    sub_arr = []
    indx = 0

    while indx < len(arr):
        if sum == x and len(sub_arr) != 0:
            return True

        if sum > x:
            first = sub_arr.pop(0)
            sum -= first
            continue

        sum += arr[indx]
        sub_arr.append(arr[indx])

        if sum < x:
            indx += 1

    return False


"""
x >= 0
[4, 3, 2, 1], x=5 - True
[4, 3, 2, 1], x=10 - False
[4, 3, 2, 1], x=-1 - False
[1, 1, 1, 1], x=2 - True
[1, 1, 1, 1], x=5 - False
[4, 3, 2, 1], x=7 - True
[4, 3, 2, 1], x=3 - True
[3, 2, 2, 1], x=4 - True
[3, 2, 2, 1], x=3 - True
[3, 2, 2, 1], x=5 - True
[3, 2, 2, 1], x=10 - False
[0, 0, 0, 0], x=1 - False
[3, 2, 1], x=5 - True
[6, 3, 1], x=6 - True
[6], x=6 - True 
[6, 5], x=0 - False
[], x=0 - False
[], x=1 - False
None, x=2 - False
"""

print(f"0: {solve([4, 3, 2, 1], x=5)} - True")
print(f"1: {solve([4, 3, 2, 1], x=11)} - False")
print(f"3: {solve([1, 1, 1, 1], x=2)} - True")
print(f"4: {solve([1, 1, 1, 1], x=5)} - False")
print(f"5: {solve([4, 3, 2, 1], x=7)} - True")
print(f"6: {solve([4, 3, 2, 1], x=3)} - True")
print(f"7: {solve([3, 2, 2, 1], x=4)} - True")
print(f"8: {solve([3, 2, 2, 1], x=3)} - True")
print(f"9: {solve([3, 2, 2, 1], x=5)} - True")
print(f"10: {solve([3, 2, 2, 1], x=10)} - False")
print(f"11: {solve([0, 0, 0, 0], x=1)} - False")
print(f"12: {solve([3, 2, 1], x=5)} - True")
print(f"13: {solve([6, 3, 1], x=6)} - True")
print(f"14: {solve([6], x=6)} - True")
print(f"15: {solve([6, 5], x=0)} - False")
print(f"16: {solve([], x=0)} - False")
print(f"17: {solve([], x=1)} - False")
print(f"18: {solve(None, x=2)} - False")
print(f"19: {solve([2, 1, 1], x=2)} - True")
