count = int(input())


def print_brackets(count: int, open: int, closed: int, curr: str):
    if len(curr) == 2 * count:
        print(curr)
    if open < count:
        print_brackets(count, open + 1, closed, curr + '(')
    if closed < open:
        print_brackets(count, open, closed + 1, curr + ')')


print_brackets(count, 0, 0, '')
