class Path:
    def __init__(self, path, attractive):
        self.path = path
        self.attractive = attractive

    def __str__(self):
        return str(self.path)

    def length(self):
        return len(self.path) - 1


def attraction(heights, path):
    min_res = 0
    dirty = True
    for i in range(1, len(path)):
        min_res = abs(heights[path[i]] - heights[path[i-1]]) \
            if dirty \
            else min(min_res, abs(heights[path[i]] - heights[path[i-1]]))
        dirty = False

    return min_res


def dsf(graph, heights, start, end):
    res = []
    paths = [[start]]

    while len(paths) != 0:
        curr = paths.pop()

        if curr[-1] == end:
            res.append(Path(curr, attraction(heights, curr)))

        if not graph[curr[-1]]:
            continue

        for neighbor in graph[curr[-1]]:
            neighbor_path = curr.copy()
            neighbor_path.append(neighbor)
            paths.append(neighbor_path)

    return res


# n, m, s, t = 4, 4, 2, 4
# heights = [3, 4, 2, 1]
# roads = [(2, 4), (2, 1), (1, 3), (3, 4)]

n, m, s, t = map(int, input().strip().split())
graph = [[] for _ in range(n)]

heights = list(map(int, input().strip().split()))
roads = [tuple(map(int, input().strip().split())) for _ in range(m)]

for pair in roads:
    graph[pair[0] - 1].append(pair[1] - 1)

res = dsf(graph, heights, s-1, t-1)

for i in range(1, n):
    filtered = filter(lambda x: x.length() >= i, res)
    attractives = list(map(lambda x: x.attractive, filtered))
    print(max(attractives) if attractives else -1)
