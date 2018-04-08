
from django.conf.urls import url,include
from .import views 
from rest_framework import routers

router = routers.DefaultRouter()
router.register('calculator',views.CalculatorView)

urlpatterns = [
    url('',include(router.urls))
]
