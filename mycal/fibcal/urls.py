from django.urls import path
from . import views
urlpatterns = [
    path('fibcal/<int:number1>/<int:number2>',views.calfib),
    ]