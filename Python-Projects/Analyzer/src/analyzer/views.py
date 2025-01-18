from django.http import HttpResponse, HttpRequest
from src.mongo import smsCollection

def index(request: HttpRequest):
    rubles_to_save: int = 10_000
    months_to_save: int = 30 * 12
    annual_deposit_rate: float = 0.1
    annual_inflation: float = 0.07
    monthly_inflation: float = (1 + annual_inflation) ** (1 / 12) - 1

    summurized_koef:float = 0.

    for i in range(0, months_to_save):
        summurized_koef += (1 + annual_deposit_rate / 12) ** (months_to_save - i) - monthly_inflation

    print('Accumulated in deposit: ' + str(summurized_koef * rubles_to_save))
    print('Accumulated under pillow: ' + str(rubles_to_save * months_to_save))
    print('Difference: ' + str(summurized_koef * rubles_to_save - (rubles_to_save * months_to_save)))
    print('Result per month: ' + str(summurized_koef * rubles_to_save / months_to_save))
    print('-----------------')

    sms = {
        "sms": "Test Sms"
    }

    smsCollection.insert_one(sms)

    return HttpResponse("Hello, world. You're at the analyzer index.")

# compound_periods_in_year - how many times the accumulation happens, for example 12 for monthly deposits
# and 4 times for quarter deposits
def calculate_compound_interest_accumulation(request: HttpRequest):
    principle_amount = float(request.GET.get("principle"))
    annual_interest_rate = float(request.GET.get("rate"))
    compound_periods_in_year = float(request.GET.get("compound"))
    years = float(request.GET.get("years"))

    res = principle_amount * (1 + annual_interest_rate / compound_periods_in_year) ** (compound_periods_in_year * years)

    return HttpResponse(f"{res:_.2f}")

class Info:
    deposit_accumulation: float
    deposit_accumulation_per_month: float
    pillow_accumulation: float
    pillow_accumulation_per_month: float
    profit: float
    profit_per_month: float
    months: int

    def __init__(self, deposit_accumulation: float, pillow_accumulation: float, months: int):
        self.deposit_accumulation = deposit_accumulation
        self.deposit_accumulation_per_month = deposit_accumulation / months
        self.pillow_accumulation = pillow_accumulation
        self.pillow_accumulation_per_month = pillow_accumulation / months
        self.profit = deposit_accumulation - pillow_accumulation
        self.profit_per_month = self.deposit_accumulation_per_month - self.pillow_accumulation_per_month
        self.months = months
