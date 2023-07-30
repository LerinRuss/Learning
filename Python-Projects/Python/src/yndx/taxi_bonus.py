n = int(input().strip())

rates = [int(input().strip()) for _ in range(n)]
bonuses = [0 for _ in rates]

sorted_indexes = sorted(range(len(rates)), key=lambda k: rates[k])

total_sum = 0
for curr in sorted_indexes:
    max_found_bonus = 0
    max_found_neighbor = 0

    left_neighbor = curr - 1
    right_neighbor = curr + 1

    if left_neighbor >= 0 and rates[left_neighbor] < rates[curr] and bonuses[left_neighbor] > max_found_bonus:
        max_found_bonus = bonuses[left_neighbor]
        max_found_neighbor = left_neighbor

    if right_neighbor < len(bonuses) and rates[right_neighbor] < rates[curr] \
            and bonuses[right_neighbor] > max_found_bonus:
        max_found_bonus = bonuses[right_neighbor]
        max_found_neighbor = right_neighbor

    if max_found_bonus == 0:
        bonuses[curr] = 500
    else:
        bonuses[curr] = max_found_bonus + 500

    total_sum = total_sum + bonuses[curr]

print(total_sum)
