import os


def write(f):
    f.write('my bro\n')
    f.write('first line\n')
    f.write('second line\n')


with open('test.txt', 'r+') as f:
    write(f)
    data = f.read(4)
    print(data)
