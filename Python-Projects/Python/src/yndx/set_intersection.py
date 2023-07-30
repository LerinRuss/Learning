# def count(jewelery: str, stones: str):
#     if not jewelery or not stones:
#         return str(0)
#     return str(len(set(jewelery) & set(stones)))
#
#
# print("j = qwerd s = qwerd - 5, res is " + count("qwerd", "qwerd"))
# print("j = 123qwerd456 s = qwerd - 5, res is " + count("123qwerd456", "qwerd"))
# print("j = 123 s = 456 - 0, res is " + count("123", "456"))
# print("j = 1q2w3e s = qwe - 3, res is " + count("1q2w3e", "qwe"))
# print("j = qwe s = rqwet - 3, res is " + count("qwe", "rqwet"))
# print("one of them or both is empty - 0, res is " + count("213", ""))
# print("one of them or both is empty - 0, res is " + count("", "213"))
# print("one of them or both is empty - 0, res is " + count("", ""))
# print("one of them or both is null - 0, res is " + count(None, "123"))
# print("one of them or both is null - 0, res is " + count("123", None))
# print("one of them or both is null - 0, res is " + count(None, None))


import sys

jewelries = input().strip()
stones = input().strip()

print('Work')

if not jewelries or not stones:
    print(str(0))
else:
    print(str(len(set(jewelries) & set(stones))))

# exit(0)
#