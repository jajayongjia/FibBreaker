from django.shortcuts import render
from django.http import HttpResponse

# Create your views here.
def calfib(request,number1,number2):
    sums = number1+number2
    return HttpResponse('{}'.format(sums))