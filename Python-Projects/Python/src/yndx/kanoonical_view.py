import sys
from io import StringIO


def process_whitespace(ch: str, input_str: StringIO) -> str:
    while ch.isspace():
        ch = input_str.read(1)

    return ch


def process_parameter(ch: str, input_str: StringIO):
    while ch != "\"":
        ch = input_str.read(1)

    return input_str.read(1)


def process_in(ch: str, input_str: StringIO, builder: StringIO):
    while ch != "(":
        ch = input_str.read(1)

    ch = input_str.read(1)
    bracket_count = 1

    while bracket_count != 0:
        if ch == "\"":
            ch = process_parameter(input_str.read(1), input_str)
        if ch == "(":
            bracket_count = bracket_count + 1
        if ch == ")":
            bracket_count = bracket_count - 1
        ch = input_str.read(1)
    return ch


input_str = StringIO()

for line in sys.stdin.readlines():
    input_str.write(line)
input_str.seek(0)

builder = StringIO()

with input_str:
    ch = input_str.read(1)
    while ch != "":
        if ch.isspace():
            ch = process_whitespace(input_str.read(1), input_str)
            if ch != "" and ch != ")":
                builder.write(" ")
            continue
        if ch == "(":
            builder.write(ch)
            ch = input_str.read(1)
            if ch.isspace():
                ch = process_whitespace(input_str.read(1), input_str)
            continue
        if ch == "\"":
            ch = process_parameter(input_str.read(1), input_str)
            builder.write("?")
            continue
        if ch.isalnum():
            word = ""
            while ch.isalnum():
                word = word + ch
                ch = input_str.read(1)

            if word == "in":
                builder.write("in")
                ch = process_in(ch, input_str, builder)
                builder.write(" (...)")
                continue
            builder.write(word)
            continue
        builder.write(ch.lower())
        ch = input_str.read(1)

res = builder.getvalue()
print(res)
