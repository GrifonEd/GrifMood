from typing import Optional, Iterable, List
from django.db.models import QuerySet
import matplotlib.pyplot as plt
from ..models import User, Condition , Profile,Test,Question,Answer,ResultTest,ResultDescription,Message,ConnectionProfile
from ..serializers import  UserSerializer1, ConditionSerializer, ProfileSerializer,TestSerializer,ResultTestSerializer,QuestionSerializer,AnswerSerializer,ConditionSerializerPost
import pandas 
from pandas import describe_option
import pandas_profiling
import numpy 
import scipy.stats 
from pandas_profiling import profile_report 
import django_pandas.io
import math
# CONDITION


def get_condition_by_id(condition_id: int) -> Optional[Condition]:
   
    condition = Condition.objects.filter(id=condition_id).first()
    return condition

def create_condition(assessment: int,    description: str,  userid: int, work: int, reading: int, workout: int, gaming: int, family: int, friend: int, 
study: int, music: int, movie: int, shopping: int, travel: int, cleaning: int, sleep: int, party: int, 
bar: int, leisure: int, rendezvous: int, TV: int) -> None:
    
    #print(profile.get('user').get('id'))
    #print((Profile.objects.filter(user=profile.get('user').get('id'))).first())
    profile=get_profile(userid)
    #print(type(profile))
    #print(profile)
    #prof=(Profile.objects.filter(user=profile.get('user').get('id'))).first()
    #print(description)
    condition = Condition.objects.create(assessment=assessment, description=description, profile=profile ,work=work, reading= reading, workout=workout, gaming=gaming, family=family, friend=friend, 
study=study, music=music, movie=movie, shopping=shopping, travel=travel, cleaning=cleaning,
    sleep=sleep, party=party, bar=bar, leisure=leisure, rendezvous=rendezvous, TV=TV)
   # print(condition)
    condition.save()

def get_all_condition() -> QuerySet:
   
    conditions = Condition.objects.all().order_by('date')
    return conditions

def get_all_condition_for_profile(id: int) -> Optional[Condition]:
    #print(profile)
    #print(type(profile))
    profile=get_profile(id)
    #print(id)
    #profile=Profile.objects.filter(id=id).first()
    #print(profile)
    if(profile!=None):
        conditions = Condition.objects.filter(profile=profile).all().order_by('date')
        #print(conditions)
        return conditions
    else:
        return None
    

def get_all_condition_for_profileID(id: int) -> Optional[Condition]:
    #print(profile)
    #print(type(profile))
    profile=Profile.objects.filter(id=id).first()
    #print(id)
    #profile=Profile.objects.filter(id=id).first()
    #print(profile)
    if(profile!=None):
        conditions = Condition.objects.filter(profile=profile).all().order_by('date')
        #print(conditions)
        return conditions
    else:
        return None


def delete_condition_by_id(id: int) -> None:
    """  """
    get_condition_by_id(id).delete()


def update_condition_by_id(id:int,assessment:int,description:str,work: int, reading: int, workout: int, gaming: int, family: int, friend: int, 
study: int, music: int, movie: int, shopping: int, travel: int, cleaning: int, sleep: int, party: int, 
bar: int, leisure: int, rendezvous: int, TV: int) -> None:
    """  """
    condition = get_condition_by_id(id)
    condition.assessment = assessment
    condition.description = description
    condition.work=work
    condition.reading=reading
    condition.workout=workout
    condition.gaming=gaming
    condition.family=family
    condition.friend=friend
    condition.study=study
    condition.music=music
    condition.movie=movie
    condition.shopping=shopping
    condition.travel=travel
    condition.cleaning=cleaning
    condition.sleep=sleep
    condition.party=party
    condition.bar=bar
    condition.leisure=leisure
    condition.rendezvous=rendezvous
    condition.TV=TV
    condition.save()
    







# Для тестов
def create_user(username: str,    email: str, password:str) -> None:
    
    user = User.objects.create(username=username, email=email, password=password)
   # print(user)
    user.save()


def create_profile(user: User,    first_name: str, second_name:str,sex:str,age:int) -> None:
    
    profile = Profile.objects.create(user=user, first_name=first_name, second_name=second_name,sex=sex,age=age)
   # print(profile)
    profile.save()

def get_profile_by_id(id: int) -> QuerySet:
   
    profile = Profile.objects.filter(id=id).first()
   
   # print(profile)
    return profile

def get_user_by_id(id: int) -> QuerySet:
   
    user = User.objects.filter(id=id).first()
   
   # print(user)
    return user





#TEST

def get_test_by_id(id: int) -> Optional[Test]:                 #ЕСТЬ
   
    test = Test.objects.filter(id_test=id).first()
    return test


def get_all_tests() -> QuerySet:                #ЕСТЬ
   
    test = Test.objects.all()
    return test



#QUESTION


def get_question_by_test_and_number(id_test: int,number:int) -> Optional[Question]:            #ЕСТЬ
    test=get_test_by_id(id_test)
    question = Question.objects.filter(test=test,number=number).first()
    return question


#ANSWER

def get_answers_by_question(id_test:int,number:int) -> Optional[Answer]:                #ЕСТЬ
    question=get_question_by_test_and_number(id_test,number)
    answer = Answer.objects.filter(question=question).all()
    return answer


#RESULTTEST

def create_result_test(description: str,    test_completion_time: int, score:int,user_id: int,test_id:int) -> None:
    profile=get_profile(user_id)
    test=get_test_by_id(test_id)
    result = ResultTest.objects.create(description=description, test_completion_time=test_completion_time,score=score, profile=profile,test=test)
    #print(result)
    result.save()



def get_result_test(test_id:int,user:int) -> Optional[ResultTest]:
    profile=get_profile(user)
    test=get_test_by_id(test_id)
    result = ResultTest.objects.filter(test=test,profile=profile).last()
    return result

def get_all_result_test_for_profile(profile:int,test_id:int) -> QuerySet:
    test = get_test_by_id(test_id)
    prof=get_profile(profile)
   # print(prof,test)
    result = ResultTest.objects.filter(profile=prof,test=test).all()
    return result


#PROFILE


def get_profile(id: int) -> Optional[Profile]:
   # print(id)
    user=User.objects.filter(id=id).first()
   # print(user)
    profile = Profile.objects.filter(user=user).first()
    #print(profile)
    return profile


def update_profile(first_name:str,second_name:str,sex:str,age:int,user:User) -> None:
    """  """
    profile = get_profile(user)
    profile.first_name = first_name
    profile.second_name = second_name
    profile.sex = sex
    profile.age = age
    profile.save()



def get_one_description_result(id_test: int,points:int) -> Optional[ResultDescription]:            #ЕСТЬ
    test=get_test_by_id(id_test)
    results_Description = ResultDescription.objects.filter(test=test)
    for i in results_Description:
        if(points>=i.pointsMin and points<=i.pointsMax):
            result_Description=ResultDescription.objects.filter(test=test,pointsMin=i.pointsMin).first()
    return result_Description



def lets_analysis(id_user:int,caseMy:str)-> QuerySet:
    profile=get_profile(id_user)
    #print(id)
    #profile=Profile.objects.filter(id=id).first()

    
    #print(profile)
    
    if(profile!=None):
        conditions = Condition.objects.filter(profile=profile).all()
        if(caseMy=="work"):
            conditionsNotZero=Condition.objects.filter(profile=profile).exclude(work=0).all()
        if(caseMy=="reading"):
            conditionsNotZero=Condition.objects.filter(profile=profile).exclude(reading=0).all()
        if(caseMy=="workout"):
            conditionsNotZero=Condition.objects.filter(profile=profile).exclude(workout=0).all()
        if(caseMy=="gaming"):
            conditionsNotZero=Condition.objects.filter(profile=profile).exclude(gaming=0).all()
        if(caseMy=="family"):
            conditionsNotZero=Condition.objects.filter(profile=profile).exclude(family=0).all()
        if(caseMy=="friend"):
            conditionsNotZero=Condition.objects.filter(profile=profile).exclude(friend=0).all()
        if(caseMy=="study"):
            conditionsNotZero=Condition.objects.filter(profile=profile).exclude(study=0).all()
        if(caseMy=="music"):
            conditionsNotZero=Condition.objects.filter(profile=profile).exclude(music=0).all()
        if(caseMy=="movie"):
            conditionsNotZero=Condition.objects.filter(profile=profile).exclude(movie=0).all()
        if(caseMy=="shopping"):
            conditionsNotZero=Condition.objects.filter(profile=profile).exclude(shopping=0).all()
        if(caseMy=="travel"):
            conditionsNotZero=Condition.objects.filter(profile=profile).exclude(travel=0).all()
        if(caseMy=="cleaning"):
            conditionsNotZero=Condition.objects.filter(profile=profile).exclude(cleaning=0).all()
        if(caseMy=="sleep"):
            conditionsNotZero=Condition.objects.filter(profile=profile).exclude(sleep=0).all()
        if(caseMy=="party"):
            conditionsNotZero=Condition.objects.filter(profile=profile).exclude(party=0).all()
        if(caseMy=="bar"):
            conditionsNotZero=Condition.objects.filter(profile=profile).exclude(bar=0).all()
        if(caseMy=="leisure"):
            conditionsNotZero=Condition.objects.filter(profile=profile).exclude(leisure=0).all()
        if(caseMy=="rendezvous"):
            conditionsNotZero=Condition.objects.filter(profile=profile).exclude(rendezvous=0).all()
        if(caseMy=="TV"):
            conditionsNotZero=Condition.objects.filter(profile=profile).exclude(TV=0).all()

                
        #print(conditionsNotZero)
        df = django_pandas.io.read_frame(conditions,fieldnames=['assessment', caseMy] )
        #reading','workout','gaming','family','friend','study','music','movie','shopping','travel','cleaning','sleep','party','bar','leisure','rendezvous','TV']
        # print(df)                                       
        print()
        df1 = django_pandas.io.read_frame(conditionsNotZero,fieldnames=['assessment', caseMy] )    
        for i in range(0,int(df1.size/2)):
            df1.at[i,caseMy]=int(df1.loc[i,caseMy]//60)
       # print(df1)                                          
       # print(df1.describe(include='all'))                                                                                                           
        arra=df1[caseMy].to_numpy()
        df1[caseMy].hist(bins=60)
       # plt.xlabel(caseMy)
       # plt.ylabel('Частота')
       # plt.show()
        rhowork, pwork=scipy.stats.shapiro(arra)
        print(rhowork)
        print(pwork)
        if(pwork>0.05):
            print("Приянть гипотезу о нормальности")
        else:
            print("Отклонить гипотезу о нормальности")

        arra1=df1['assessment'].to_numpy()
        df1['assessment'].hist(bins=60)
       # plt.xlabel('Оценка, раз.')
       # plt.ylabel('Частота')
       # plt.show()
        #scipy.stats.shapiro(dfassessment)
        #report = pandas_profiling.ProfileReport(df,title='Отчет о профилировании Pandas',explorative=True)
        #json_data = report.to_json() 
        #print(json_data)
        #report.to_file("output.html")
        rhoassessment, passesment=scipy.stats.shapiro(arra1)
        print(rhoassessment)
        print(passesment)
        if(passesment>0.05):
            print("Принять гипотезу о нормальности")
        else:
            print("Отклонить гипотезу о нормальности")


        Ox=df1[caseMy]
        Oy=df1['assessment']

       # pandas.DataFrame(numpy.array([Ox,Oy]).T).plot.scatter(0,1,s=12,grid=True)
       # plt.xlabel(caseMy)
       # plt.ylabel('Оценка')
       # plt.show()


        if(pwork>0.05 and passesment>0.05):
            correlPirson, pPirson = scipy.stats.pearsonr(df1[caseMy],df1['assessment'])
            print(correlPirson, pPirson)
            if(pPirson<0.05):
                print("Корреляция является статистически значимой")
                znachimost="Корреляция является статистически значимой"
            else:
                print("Корреляция не является статистически значимой")
                znachimost="Корреляция не является статистически значимой"
            if(correlPirson<-0.9):
                print("Связь обратная, очень сильная")
            elif(correlPirson<-0.7):
                print("Связь обратная, сильная")
            elif(correlPirson<-0.5):
                print("Связь обратная, заметная")
            elif(correlPirson<-0.3):
                print("Связь обратная, умеренная")    
            elif(correlPirson<-0.1):
                print("Связь обратная, слабая")
            elif(correlPirson<0.1):
                print("Связи нет")
            elif(correlPirson<0.3):
                print("Связь прямая, слабая")
            elif(correlPirson<0.5):
                print("Связь прямая, умеренная")
            elif(correlPirson<0.7):
                print("Связь прямая, заметная")
            elif(correlPirson<0.9):
                print("Связь прямая, сильная")
            else:
                print("Связь прямая, очень сильная")


            z = math.log((1+correlPirson)/(1-correlPirson))/2
            L= z - (1.96/((arra.size-3)**(1/2)))
            U = z + (1.96/((arra.size-3)**(1/2)))
            dovL=(math.e**(2*L)-1)/(math.e**(2*L)+1)
            dovR=(math.e**(2*U)-1)/(math.e**(2*U)+1)
            print("Доверительный диапазон ["+str(dovL)+" ; "+str(dovR)+"]")

            return {
	"znachimost": znachimost,
    "coef": correlPirson
}

        else:
            correlSpirmen, pSpirmen = scipy.stats.spearmanr(df1[caseMy],df1['assessment'])
            print(correlSpirmen, pSpirmen)
            if(pSpirmen<0.05):
                print("Корреляция является статистически значимой")
                znachimost="Корреляция является статистически значимой"
            else:
                print("Корреляция не является статистически значимой")
                znachimost="Корреляция не является статистически значимой"
            if(correlSpirmen<-0.9):
                print("Связь обратная, очень сильная")
            elif(correlSpirmen<-0.7):
                print("Связь обратная, сильная")
            elif(correlSpirmen<-0.5):
                print("Связь обратная, заметная")
            elif(correlSpirmen<-0.3):
                print("Связь обратная, умеренная")    
            elif(correlSpirmen<-0.1):
                print("Связь обратная, слабая")
            elif(correlSpirmen<0.1):
                print("Связи нет")
            elif(correlSpirmen<0.3):
                print("Связь прямая, слабая")
            elif(correlSpirmen<0.5):
                print("Связь прямая, умеренная")
            elif(correlSpirmen<0.7):
                print("Связь прямая, заметная")
            elif(correlSpirmen<0.9):
                print("Связь прямая, сильная")
            else:
                print("Связь прямая, очень сильная")

            z=math.log((1+correlSpirmen)/(1-correlSpirmen))/2
            L= z-(1.96/((arra.size-3)**(1/2)))
            U = z+(1.96/((arra.size-3)**(1/2)))
            dovL=(math.e**(2*L)-1)/(math.e**(2*L)+1)
            dovR=(math.e**(2*U)-1)/(math.e**(2*U)+1)
            print("Доверительный диапазон ["+str(dovL)+" ; "+str(dovR)+"]")

            return {
	"znachimost": znachimost,
    "coef": correlSpirmen
}
    else:
        return None

#удалить потом
def update_condition_by_id1(id:int,assessment:int,description:str,work: int, reading: int, workout: int, gaming: int, family: int, friend: int, 
study: int, music: int, movie: int, shopping: int, travel: int, cleaning: int, sleep: int, party: int, 
bar: int, leisure: int, rendezvous: int, TV: int,date:str) -> None:
    """  """
    condition = get_condition_by_id(id)
    condition.date=date
    
    condition.save()



def get_message_by_id(message_id: int) -> Optional[Message]:
   
    message = Message.objects.filter(id=message_id).first()
    return message

def delete_message_by_id(message_id: int) -> None:
    print(message_id)
    Message.objects.filter(id=message_id).first().delete()
    

def create_message(profile_send:int,profile_receive:int,message:str) -> None:
    print(profile_send)
    print(profile_receive)
    profileSend=get_profile(profile_send)
    profileReceive=Profile.objects.filter(id=profile_receive).first()
    print(profileSend)
    print(profileReceive)
    message = Message.objects.create(profileSend=profileSend,profileReceive=profileReceive,message=message)
    message.save()

def get_all_messages_by_profile(profile_receive:int,profile_send:int) -> QuerySet:
    profileSend=get_profile(profile_send)
    profileReceive=get_profile(profile_receive)
    messages = Message.objects.filter(profileReceive=profileReceive,profileSend=profileSend).all()
    return messages

def get_all_messages_by_profile_receive(profile_receive:int) -> QuerySet:
    profileReceive=get_profile(profile_receive)
    messages = Message.objects.filter(profileReceive=profileReceive).all()
    return messages

def get_all_connection_by_id(profile_watching: int) -> Optional[ConnectionProfile]:
    profile_watching1=get_profile(profile_watching)
    connection = ConnectionProfile.objects.filter(profileWatching=profile_watching1).all()
    return connection

def get_connection_by_id(connection_id: int) -> Optional[ConnectionProfile]:
   
    connection = ConnectionProfile.objects.filter(id=connection_id).first()
    return connection

def get_connection_by_id_watcher(watcher_id: int) -> Optional[ConnectionProfile]:
    watcher = Profile.objects.filter(id=watcher_id).first()
    connection = ConnectionProfile.objects.filter(profileWatching=watcher).first()
    return connection

def create_connection(profile_share:int,profile_watching:int) -> None:
  
    profileShare=get_profile(profile_share)
    profileWatching=get_profile(profile_watching)
    connection = ConnectionProfile.objects.create(profileWatching=profileWatching,profileShare=profileShare)
    connection.save()


def create_connection_profile_by_uuid(profile_id:int,uuid: str) -> None:

    profileWacht=get_profile(profile_id)
    profileShare = Profile.objects.filter(uuid=uuid).first()
    connection = ConnectionProfile.objects.create(profileWatching=profileWacht,profileShare=profileShare)
    connection.save()
