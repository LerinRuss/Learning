def solve(text: str):
    x_indx: int = -1
    y_indx: int = -1
    min_dist: int = -1

    for i, ch in enumerate(text):
        if ch == 'O':
            continue

        dist = -1
        if ch == 'X':
            x_indx = i

            if y_indx != -1:
                dist = x_indx - y_indx

        if ch == 'Y':
            y_indx = i

            if x_indx != -1:
                dist = y_indx - x_indx

        if min_dist == -1:
            min_dist = dist

        if dist < min_dist:
            min_dist = dist

    return min_dist if min_dist != -1 else 0
