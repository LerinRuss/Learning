import itertools
from queue import PriorityQueue


class PeekableSequence:
    def __init__(self, generator: itertools.count):
        self._generator = generator
        self._peaked: int | None = None

    def peek(self) -> int:
        if self._peaked is None:
            self._peaked = next(self._generator)

        return self._peaked

    def __iter__(self):
        return self

    def __next__(self) -> int:
        if self._peaked is not None:
            value = self._peaked
            self._peaked = None
        else:
            value = next(self._generator)

        return value

    def __str__(self):
        return f"Generator: {str(self._generator)} and Peaked value: {self._peaked}"


class Node:
    def __init__(self, id: int, seq: PeekableSequence):
        self.id = id
        self.seq = seq
        self.left = None
        self.right = None


class BinaryTree:
    def __init__(self):
        self.root = None

    def insert(self, id: int, seq: PeekableSequence):
        if self.root is None:
            self.root = Node(id, seq)
        else:
            self._insert_recursive(self.root, id, seq)

    def _insert_recursive(self, node: Node, val_id: int, val_seq: PeekableSequence):
        if val_seq.peek() < node.seq.peek():
            if node.left is None:
                node.left = Node(val_id, val_seq)
            else:
                self._insert_recursive(node.left, val_id, val_seq)
        else:
            if node.right is None:
                node.right = Node(val_id, val_seq)
            else:
                self._insert_recursive(node.right, val_id, val_seq)

    def get_min(self) -> Node | None:
        if self.root is None:
            return None
        res: Node = self._get_min_recursive(self.root)
        res.left = res.right

        return res

    def _get_min_recursive(self, node: Node):
        if node.left is None:
            return node
        return self._get_min_recursive(node.left)

    def remove(self, id: int):
        self.root = self._remove_recursive(self.root, id)

    def _remove_recursive(self, node: Node, id: int):
        if node is None:
            return None

        if node.id == id:
            if node.left is None and node.right is None:
                return None
            elif node.left is None:
                return node.right
            elif node.right is None:
                return node.left
            else:
                min_value: Node = self._get_min_recursive(node.right)
                node.id = min_value.id
                node.seq = min_value.seq

                node.right = self._remove_recursive(node.right, min_value.id)
                return node

        node.left = self._remove_recursive(node.left, id)
        node.right = self._remove_recursive(node.right, id)

        return node


class SequenceStorage:
    def __init__(self):
        self._data: BinaryTree = BinaryTree()

    def __setitem__(self, index: int, value: PeekableSequence):
        self._data.insert(index, value)
        # while index >= len(self._data):
        #     self._data.append(None)
        # self._data[index] = value

    def __delitem__(self, key):
        self._data.remove(key)

    def __str__(self):
        return str(self._data)

    def min(self) -> int | None:
        # res: list[tuple[int, PeekableSequence]] = []

        # for seq in self._data:
            # if seq is None:
            #     continue

            # val = seq.peek()
            #
            # if not res:
            #     res.append((val, seq))
            # elif res[0][0] > val:
            #     res.clear()
            #     res.append((val, seq))
        #
        # if not res:
        #     return None

        # next(res[0][1])
        # return res[0][0]
        item = self._data.get_min()
        res = item.seq.peek()
        next(item.seq)
        self._data.remove(item.id)

        return res


storage = SequenceStorage()
n = int(input().strip())

# ops = [
#     [1, 3, -4, 1],
#     [1, -5, 4, 3],
#     [1, -2, 10, 2],
#     [3],
#     [3],
#     [2, 3],
#     [3],
#     [3],
#     [2, 2],
#     [1, -5, 4, 4],
#     [3],
#     [2, 1],
#     [3],
#     [3],
#     [3],
# ]

for _ in range(n):
    op = input().strip().split(' ')

    if op[0] == "1":
        storage[int(op[3]) - 1] = PeekableSequence(itertools.count(int(op[1]), int(op[2])))
    elif op[0] == "2":
        del storage[int(op[1]) - 1]
    else:
        print(storage.min())

"""
Example 1:
Input:
15
1 3 -4 1
1 -5 4 3
1 -2 10 2
3
3
2 3
3
3
2 3
3
3
2 2
1 -5 4 4
3
2 1
3
3
3

Output:
-5
-2
3
-1
-5
-5
-1
3
"""
