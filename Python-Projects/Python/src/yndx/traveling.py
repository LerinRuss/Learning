count = int(input().strip())
cities = []

for _ in range(count):
    city = input().strip().split(' ')
    cities.append((int(city[0]), int(city[1])))

limit = int(input().strip())

start_and_end = input().strip().split(' ')
start = int(start_and_end[0]) - 1
end = int(start_and_end[1]) - 1


def travel(cities: [tuple[int, int]], start_idx: int, end_idx: int, limit: int) -> int:
    if start_idx == end_idx:
        return 0

    mem = cities[start_idx]
    cities[start_idx] = cities[0]
    cities[0] = mem

    dirty = True
    min_path = 0
    jumps = 0
    for curr in cities:
        for next_idx, next in enumerate(cities):
            if curr == next or length(curr, next) > limit:
                continue
            if next_idx == end_idx:
                min_path = min(min_path, jumps + 1) if not dirty else jumps + 1
                dirty = False
        jumps = jumps + 1

    return min_path if not dirty else -1


def length(x: tuple[int, int], y: tuple[int, int]):
    return abs(y[0] - x[0]) + abs(y[1] - x[1])


print(str(travel(cities, start, end, limit)))


"""
8 6 4 3 1

1 -> 5 = 1L & 7b
1 -> 2 -> 5 = 2L & 2b

1 -> 3 -> 5 = 2L & 3b
1 -> 2 -> 3 -> 5 = 3L & 2b

1 -> 4 -> 5 = 2L & 2b
1 -> 2 -> 4 -> 5 = 3L & 2b
1 -> 3 -> 4 - > 5 = 3L & 1b
1 -> 2 -> 3 -> 4 -> 5 = 4L & 1b

i = 1 -> 7
i = 2 -> 3
i = 3 -> 2
i = 4 -> 1
"""


