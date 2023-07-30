count = int(input())


def result(count: int):
    if count == 0:
        return

    prev = input()

    print(prev)

    if count == 1:
        return

    for _ in range(1, count):
        curr = input()

        if prev != curr:
            prev = curr
            print(curr)


result(count)

