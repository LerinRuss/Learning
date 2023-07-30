def solve(s: str):
    if s is None or len(s) == 0:
        return 0
    if len(s) == 1:
        return 1

    res = 1
    prev = s[0]
    cumulative = 1

    for ch_indx in range(1, len(s)):
        ch = s[ch_indx]

        if ch == prev:
            cumulative = cumulative + 1
        else:
            cumulative = 1
        prev = ch
        res = max(res, cumulative)
    return res

"""
aaa - 3
aaabb - 3
ababab - 1
aaabbb - 3
baaab - 3
bbaaa - 3

empty - 0
a - 1
ab - 1
"""

print(f"0: {solve('aaa')} - 3")
print(f"1: {solve('aaabb')} - 3")
print(f"2: {solve('ababab')} - 1")
print(f"3: {solve('aaabbb')} - 3")
print(f"4: {solve('baaab')} - 3")
print(f"5: {solve('bbaaa')} - 3")
print(f"6: {solve('')} - 0")
print(f"7: {solve('a')} - 1")
print(f"8: {solve('ab')} - 1")
