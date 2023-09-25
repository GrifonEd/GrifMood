from django.db.models import*
from django.db import models
from django.contrib.auth.models import PermissionsMixin
from django.contrib.auth.base_user import AbstractBaseUser
from django.utils import timezone
from django.utils.translation import gettext_lazy as _
from django.conf import settings
from grifmood.managers import UserManager
import uuid
import pandas

class User(AbstractBaseUser, PermissionsMixin):
    username = models.CharField(_('username'), max_length=255, unique=True)
    email = models.EmailField(_('email address'),\
        null=True, blank=True)
    date_joined = models.DateTimeField(_('date joined'),auto_now_add=True)
    is_active = models.BooleanField(_('active'), default=False)
    is_staff = models.BooleanField(_('staff'), default=False)

    is_verified = models.BooleanField(_('verified'), default=False)

    objects = UserManager()

    USERNAME_FIELD = 'username'
    REQUIRED_FIELDS = ['email']

    class Meta:
        verbose_name = _('user')
        verbose_name_plural = _('users')
        unique_together = ('username', 'email')
    
    def __str__(self):
        return f"{self.username}, {self.email}"

    
class Profile(models.Model):
    id = AutoField(primary_key=True)
    user = models.OneToOneField(
        settings.AUTH_USER_MODEL, on_delete=models.CASCADE
    )
    first_name = models.CharField("first_name", max_length=500)
    second_name = models.CharField("second_name", max_length=500)
    sex = models.CharField("sex", max_length=500)
    age = models.IntegerField("age",null=False)
    uuid = models.CharField("uuid", max_length=500,default=str(uuid.uuid1()))

    class Meta:
        """ Установка названия таблицы """
        db_table = 'profile'

    def __str__(self):
        return f"{self.first_name} {self.second_name}, {self.user.email}, {self.user.id}, 'profile_id':{self.id}"
    
class Condition(Model):
    id = AutoField(primary_key=True)
    assessment = IntegerField(null=False)
    description = CharField(max_length=255,blank=True)
    date = DateTimeField(auto_now_add=True)
    profile = ForeignKey('Profile', null=False, on_delete=CASCADE)
    work = IntegerField()
    reading = IntegerField()
    workout = IntegerField()
    gaming = IntegerField()
    family = IntegerField()
    friend = IntegerField()
    study = IntegerField()
    music = IntegerField()
    movie = IntegerField()
    shopping = IntegerField()
    travel = IntegerField()
    cleaning = IntegerField()
    sleep = IntegerField()
    party = IntegerField()
    bar = IntegerField()
    leisure = IntegerField()
    rendezvous = IntegerField()
    TV = IntegerField()
    class Meta:
        """ Установка названия таблицы """
        db_table = 'condition'

    def __str__(self):
        return str({'assessment': self.assessment,'description': self.description,'date':self.date,'id':self.id,'profile':self.profile.user.id})
    
    def pricing_dataframe(self) -> pandas.DataFrame:
        """
        Get all pricing data, as a Pandas DataFrame object, for a given Stock.
        Returns:
            Pandas DataFrame
        """
        return pandas.DataFrame.from_records(Condition.objects.filter(profile=self).values())
    

class Test(Model):
    id_test = AutoField(primary_key=True)
    test_name = CharField(max_length=255)
    description = CharField(max_length=5000)
    test_completion_time = IntegerField()
    image = CharField(max_length=255)
    count_question=IntegerField()
    
    class Meta:
        """ Установка названия таблицы """
        db_table = 'test'

    def __str__(self):
        return str({'test_name': self.test_name,'description': self.description,'test_completion_time':self.test_completion_time,'id_test':self.id_test})
    
class Question(Model):
    id = AutoField(primary_key=True)
    test = ForeignKey('test', null=False, on_delete=CASCADE)
    number = IntegerField()
    question = CharField(max_length=255)
   
    class Meta:
        """ Установка названия таблицы """
        db_table = 'question'

    def __str__(self):
        return str({'question': self.question})
      
class Answer(Model):
    id = AutoField(primary_key=True)
    question = ForeignKey('question', null=False, on_delete=CASCADE)
    answer = CharField(max_length=255)
    points = IntegerField()
    
    class Meta:
        """ Установка названия таблицы """
        db_table = 'answer'

    def __str__(self):
        return str({'answer': self.answer,'points': self.points})
 
class ResultTest(Model):
    profile = ForeignKey('profile', null=False, on_delete=CASCADE)
    test = ForeignKey('test', null=False, on_delete=CASCADE)
    description = CharField(max_length=5000)
    test_completion_time = IntegerField()
    score = IntegerField()
    date = DateTimeField(auto_now_add=True,blank=True,null=True)
    
    
    class Meta:
        """ Установка названия таблицы """
        db_table = 'resulttest'

    def __str__(self):
        return str({'profile': self.profile.first_name,'test_id':self.test.id_test,'test':self.test.test_name, 'score':self.score,'date':self.date})


class ResultDescription(Model):
    test = ForeignKey('test', null=False, on_delete=CASCADE)
    pointsMin = IntegerField()
    pointsMax = IntegerField()
    description = CharField(max_length=5000)
    
    def get_pointsMin(self):
        return str(self.pointsMin())
    
    def get_pointsMax(self):
        return str(self.pointsMax())
    
    class Meta:
        """ Установка названия таблицы """
        db_table = 'resultdescription'

    def __str__(self):
        return str({'test': self.test,'pointsMin': self.pointsMin,'pointsMax':self.pointsMax})  


class Message(Model):
    id=  AutoField(primary_key=True)
    profileSend=  ForeignKey('Profile', null=False, on_delete=CASCADE,related_name="profileSend")
    profileReceive =  ForeignKey('Profile', null=False, on_delete=CASCADE,related_name="profileReceive")
    message= CharField(max_length=5000)
    date_created=DateTimeField(auto_now_add=True,blank=True,null=True)
    
    class Meta:
        """ Установка названия таблицы """
        db_table = 'message'

    def __str__(self):
        return str({'receive': self.profileReceive.first_name,'send': self.profileSend.first_name,'message':self.message})  

class ConnectionProfile(Model):
    id=  AutoField(primary_key=True)
    profileWatching=  ForeignKey('Profile', null=False, on_delete=CASCADE,related_name="profileWatching")
    profileShare =  ForeignKey('Profile', null=False, on_delete=CASCADE,related_name="profileShare")
    
    class Meta:
        """ Установка названия таблицы """
        db_table = 'ConnectionProfile'
        unique_together = ('profileWatching', 'profileShare')

    def __str__(self):
        return str({'profileWatching': self.profileWatching.first_name,'profileShare': self.profileShare.first_name})  

