from django.contrib import admin
from .models import *
#from django.contrib import admin
admin.site.register(User)
admin.site.register(Condition)
admin.site.register(Profile)
admin.site.register(Test)
admin.site.register(ResultTest)
admin.site.register(Question)
admin.site.register(Answer)
admin.site.register(ResultDescription)
admin.site.register(Message)
admin.site.register(ConnectionProfile)