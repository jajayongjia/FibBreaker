# -*- coding: utf-8 -*-
from __future__ import unicode_literals
from rest_framework.response import Response
from django.shortcuts import render
from django.http import HttpResponse
from django.shortcuts import render
from rest_framework import viewsets
from .models import Calculator
from .serializers import CalculatorSerializer

class CalculatorView(viewsets.ModelViewSet):
    queryset = Calculator.objects.all() 
    serializer_class = CalculatorSerializer

    #def get(self, request):
        #return Response(serializer_class.data)
    