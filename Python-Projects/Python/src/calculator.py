import math


class Mortgage:
    def __init__(self, initial_pay):
        self.initial_pay = initial_pay


# what is better mortgage or rent?

mortgage = Mortgage(initial_pay=3_000_000)
result_sum = 9_500_000
month_pay = 150_000

months = (result_sum - initial_pay) / month_pay
print(f'Months {months}')


def months_to_human_readable_string(months: float):
    month_round = lambda l_months: round(l_months, 1)

    if months < 12:
        return f'{month_round(months)} months'

    years = int(months / 12)
    months = months % 12

    if int(months) == 0:
        return f'{years} years'

    return f'{years} years and {month_round(months)} months'


print(months_to_human_readable_string(months))
