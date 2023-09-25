from django.test import TestCase
import random
from .services.repository_service import *


class TestMoodRepositoryService(TestCase):

    def setUp(self):
        
        user =create_user(username = "grifon",    email="daukaev.eduard@mail.ru", password="norm")
        
        profile = create_profile(get_user_by_id(1),first_name = "Edik",    second_name="daukaev", sex="very",age=22)
        
        condition = create_condition(assessment=10, description='Все здорово, сегодня радовалась жизни!', profile=get_profile_by_id(1), work=3, reading=0, workout=0, gaming=5, family=3, friend=8, study=6, music=1, movie=2, 
        shopping=1, travel=6, cleaning=0, sleep=0, party=2, bar=0, leisure=2, rendezvous=0, TV=2)

        


        """
        create_user(phone_number = 89467778456,first_name='Igor',second_name = 'Bobrov',password='567jd')
        create_activity(work=3, reading=0, workout=0, gaming=5, family=3, friend=8, study=6, music=1, movie=2, 
        shopping=1, travel=6, cleaning=0, sleep=0, party=2, bar=0, leisure=2, date=0, TV=2)
        create_activity(work=4, reading=0, workout=0, gaming=5, family=3, friend=8, study=6, music=1, movie=2, 
        shopping=1, travel=6, cleaning=0, sleep=0, party=2, bar=0, leisure=2, date=0, TV=1)
        create_condition(assessment=10, description='Все здорово, сегодня радовалась жизни!', user_id = 89467778456, activity_id = 1)
        create_condition(assessment=5, description='Среднячок,ничего особенного!', user_id = 89467778456, activity_id = 2)
        """

    def test_get_user(self):

        
        """
        user = get_user_by_phone_number(user_phone_number = 89467778456)
        condition = get_condition_by_id(condition_id=1)
        activity = get_activity_by_id(activity_id=1)
        print(user,"\n", condition,"\n", activity,"\n")
        update_condition (assessment = 8, description = 'Ухууу', activity_id = 1, id = 1)
        condition_up = get_condition_by_id(condition_id=1)
        print(condition_up,"\n")
        delete_condition_by_id (id=1)
        condition_del = get_condition_by_id(condition_id=1)
        print(condition_del,"\n")
        """

def tearDown(self):
     
        pass
# Create your tests here.
