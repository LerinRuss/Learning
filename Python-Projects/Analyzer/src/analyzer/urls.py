from django.urls import path

from . import views

urlpatterns = [
    path("", views.index, name="index"),
    path("calculator/compound/accumulation", views.calculate_compound_interest_accumulation, name="accumulation")
]
