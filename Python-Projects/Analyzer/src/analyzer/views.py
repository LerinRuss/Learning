from django.http import HttpResponse, HttpRequest


def index(request: HttpRequest):
    rubles_to_save = 10_000
    months_to_save = 30 * 12
    annual_deposit_rate = 0.1
    annual_inflation = 0.07
    monthly_inflation = (1 + annual_inflation) ** (1 / 12) - 1

    summurized_koef = 0

    for i in range(0, months_to_save):
        summurized_koef += (1 + annual_deposit_rate / 12) ** (months_to_save - i) - monthly_inflation

    print('Accumulated in deposit: ' + str(summurized_koef * rubles_to_save))
    print('Accumulated under pillow: ' + str(rubles_to_save * months_to_save))
    print('Difference: ' + str(summurized_koef * rubles_to_save - (rubles_to_save * months_to_save)))
    print('Result per month: ' + str(summurized_koef * rubles_to_save / months_to_save))
    print('-----------------')
    
    return HttpResponse("Hello, world. You're at the analyzer index.")

class Info:
    deposit_accumulation: int
    pillow_accumulation: int

