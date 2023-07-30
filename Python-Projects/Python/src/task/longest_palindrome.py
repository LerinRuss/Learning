def find_palindromic(s: str, left: int, right: int):
    while left >= 0 and right < len(s):
        if s[left] != s[right]:
            break

        left = left - 1
        right = right + 1

    return s[left + 1:right]


def solve(s: str):
    if s is None:
        return ""
    if len(s) == 1:
        return s

    res = ""

    for i, ch in enumerate(s):
        found_even = find_palindromic(s, i, i + 1)
        found_odd = find_palindromic(s, i, i)

        if len(found_even) > len(res):
            res = found_even
        elif len(found_odd) > len(res):
            res = found_odd

    return res


"""
abcba - abcba
abccba - abccba
1abccba4 - abccba
abcbabcba - abcbabcba
abaca - aba | aca
abcba1dod - abcba
12345 - empty

123aba - aba
aba123 - aba
None or empty - empty
a - empty
"""

print(f"{solve('abcba')} - abcba")
print(f"{solve('abccba')} - abccba")
print(f"{solve('1abccba4')} - abccba")
print(f"{solve('abcbabcba')} - abcbabcba")
print(f"{solve('abaca')} - aba | aca")
print(f"{solve('abcba1dod')} - abcba")
print(f"{solve('12345')} - 1")
print(f"{solve('123aba')} - aba")
print(f"{solve('aba123')} - aba")
print(f"{solve(None)} - empty")
print(f"{solve('')} - empty")
print(f"{solve('a')} - a")
