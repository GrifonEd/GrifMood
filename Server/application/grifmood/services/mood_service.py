from ..serializers import MessageSerializer,MessageSerializerPost,ConnectionProfileSerializer,ConnectionProfileSerializerPost, UserSerializer1, ConditionSerializer, ProfileSerializer,TestSerializer,ResultTestSerializer,QuestionSerializer,AnswerSerializer,ConditionSerializerPost,ResultTestSerializerPost, ResultDescriptionSerializer
from .repository_service import *
import rest_framework
import json
import collections

class MoodService:

#CONDITION

    def get_condition_by_id(self, id: int) -> Optional[ConditionSerializer]:            #ЕСТЬ
        result = get_condition_by_id(id)
        if result is not None:
            return ConditionSerializer(result)
        return result
    

    def get_all_condition(self) -> Optional[ConditionSerializer]:             #ЕСТЬ
        result = get_all_condition()
        if result is not None:
            return ConditionSerializer(result,many=True)
        return result
    
    def get_all_condition_one_profile(self,id: int) -> Optional[ConditionSerializer]:            #ЕСТЬ
       # print(id)
        result = get_all_condition_for_profile(id)
       # print(result)
        if result is not None:
            #print(ConditionSerializer(result))
            return ConditionSerializer(result,many=True)
        return result
    

    def get_all_condition_one_profileID(self,id: int) -> Optional[ConditionSerializer]:            #ЕСТЬ
       # print(id)
        result = get_all_condition_for_profileID(id)
       # print(result)
        if result is not None:
            #print(ConditionSerializer(result))
            return ConditionSerializer(result,many=True)
        return result

    
    def add_new_condition(self,condition:ConditionSerializerPost) -> None:            #ЕСТЬ
        cond=json.dumps(condition.data,ensure_ascii=False,default=ConditionSerializerPost)
        condition_data=json.loads(cond)
        #print(condition_data)
       # print(condition_data.get('profile'))
        #print(condition_data.get('profile').get('id'))
        create_condition(assessment=condition_data.get('assessment'),
                         description=condition_data.get('description'),
                         userid=condition_data.get('profile'),
                         work=condition_data.get('work'),
                         reading=condition_data.get('reading'),
                         workout=condition_data.get('workout'),
                         travel=condition_data.get('travel'),
                         TV=condition_data.get('TV'),
                         shopping=condition_data.get('shopping'),
                         sleep=condition_data.get('sleep'),
                         study=condition_data.get('study'),
                         family=condition_data.get('family'),
                         friend=condition_data.get('friend'),
                         gaming=condition_data.get('gaming'),
                         leisure=condition_data.get('leisure'),
                         cleaning=condition_data.get('cleaning'),
                         bar=condition_data.get('bar'),
                         movie=condition_data.get('movie'),
                         music=condition_data.get('music'),
                         party=condition_data.get('party'),
                         rendezvous=condition_data.get('rendezvous')
                         )
    
    def delete_one_condition(self,id:int)-> None:            #ЕСТЬ
        delete_condition_by_id(id)

    def put_condition(self,condition:ConditionSerializerPost)-> None:            #ЕСТЬ
        cond=json.dumps(condition.data,ensure_ascii=False,default=ConditionSerializerPost)
        condition_data=json.loads(cond)
        update_condition_by_id(id=condition_data.get('id'),
                         assessment=condition_data.get('assessment'),
                         description=condition_data.get('description'),
                         work=condition_data.get('work'),
                         reading=condition_data.get('reading'),
                         workout=condition_data.get('workout'),
                         travel=condition_data.get('travel'),
                         TV=condition_data.get('TV'),
                         shopping=condition_data.get('shopping'),
                         sleep=condition_data.get('sleep'),
                         study=condition_data.get('study'),
                         family=condition_data.get('family'),
                         friend=condition_data.get('friend'),
                         gaming=condition_data.get('gaming'),
                         leisure=condition_data.get('leisure'),
                         cleaning=condition_data.get('cleaning'),
                         bar=condition_data.get('bar'),
                         movie=condition_data.get('movie'),
                         music=condition_data.get('music'),
                         party=condition_data.get('party'),
                         rendezvous=condition_data.get('rendezvous')
        )
        
        #Test

    def get_one_test(self, id: int) -> Optional[TestSerializer]:           #ЕСТЬ
        result = get_test_by_id(id)
        if result is not None:
            return TestSerializer(result)
        return result
    

    def get_all_tests(self) -> Optional[TestSerializer]:               #ЕСТЬ
        result = get_all_tests()
        if result is not None:
            return TestSerializer(result,many=True)
        return result
    

           #QUESTION

    def get_one_question(self,id_test:int,number:int)-> Optional[QuestionSerializer]: #ЕСТЬ
        result = get_question_by_test_and_number(id_test=id_test,number=number)
        if result is not None:
            return QuestionSerializer(result)
        return result
    
    #ANSWER

    def get_answers(self,id_test:int,number:int)-> Optional[AnswerSerializer]: #ЕСТЬ
        result = get_answers_by_question(id_test=id_test,number=number)
        if result is not None:
            return AnswerSerializer(result,many=True)
        return result
    
    #RESULTTEST

    def add_new_result(self,resulttest:ResultTestSerializerPost) -> None:
        resultTest=json.dumps(resulttest.data,ensure_ascii=False,default=ResultTestSerializer)
        result_data=json.loads(resultTest)
       # print(result_data)
        #print(type(result_data))
        create_result_test(
                         description=result_data.get('description'),
                         test_completion_time=result_data.get('test_completion_time'),
                         score=result_data.get('score'),
                         user_id=result_data.get('profile'),
                         test_id=result_data.get('test')
                         )
        
    def get_one_result(self,test_id:int,id_user:int)-> Optional[ResultTestSerializer]:
        result = get_result_test(test_id=test_id,user=id_user)
        if result is not None:
            return ResultTestSerializer(result)
        return result


    def get_all_result(self,profile:int,test_id:int)-> Optional[ResultTestSerializer]:
        result = get_all_result_test_for_profile(profile=profile,test_id=test_id)
        #print(result)
        if result is not None:
            return ResultTestSerializer(result,many=True)
        return result


#PROFILEEEEEEEEEEEEEEEEEEEEE



    def get_one_profile(self,userid:int)-> Optional[ProfileSerializer]:
        result = get_profile(id=userid)
        if result is not None:
            return ProfileSerializer(result)
        return result


    def put_profile(self,user:UserSerializer1)-> None:
        profile=get_profile(user)
        update_profile(first_name=profile.get('id'),
                               second_name=profile.get('assessment'),
                               sex=profile.get('description'),
                               age=profile.get('age'),
                               user=user
                               )
        

    def get_description_result(self,id_test:int,points:int)-> Optional[ResultDescriptionSerializer]:
        result = get_one_description_result(id_test=id_test,points=points)
        if result is not None:
            return ResultDescriptionSerializer(result)
        return result
    

    def get_analysis(self,id_user:int,caseMy:str)-> QuerySet:
        result = lets_analysis(id_user=id_user,caseMy=caseMy)
        if result is not None:
            return result
        return result

#потом удалить
    def put_condition1(self,condition:ConditionSerializerPost)-> None:            #ЕСТЬ
        cond=json.dumps(condition.data,ensure_ascii=False,default=ConditionSerializerPost)
        condition_data=json.loads(cond)
        update_condition_by_id1(id=condition_data.get('id'),
                         date=condition_data.get('date'),
                         assessment=condition_data.get('assessment'),
                         description=condition_data.get('description'),
                         work=condition_data.get('work'),
                         reading=condition_data.get('reading'),
                         workout=condition_data.get('workout'),
                         travel=condition_data.get('travel'),
                         TV=condition_data.get('TV'),
                         shopping=condition_data.get('shopping'),
                         sleep=condition_data.get('sleep'),
                         study=condition_data.get('study'),
                         family=condition_data.get('family'),
                         friend=condition_data.get('friend'),
                         gaming=condition_data.get('gaming'),
                         leisure=condition_data.get('leisure'),
                         cleaning=condition_data.get('cleaning'),
                         bar=condition_data.get('bar'),
                         movie=condition_data.get('movie'),
                         music=condition_data.get('music'),
                         party=condition_data.get('party'),
                         rendezvous=condition_data.get('rendezvous')
        )


 
    def get_message_by_id(self, id: int) -> Optional[MessageSerializer]:            #ЕСТЬ
        result = get_message_by_id(id)
        if result is not None:
            return MessageSerializer(result)
        return result
    
    def delete_message_by_id(self, id: int) -> None:            #ЕСТЬ
        delete_message_by_id(id)
        
    
    def get_all_message_one_profile(self,profile_receive: int,profile_send:int) -> Optional[MessageSerializer]:            #ЕСТ
        result = get_all_messages_by_profile(profile_receive,profile_send)
        if result is not None:
            return MessageSerializer(result,many=True)
        return result
    
    def get_all_message_one_profile_receive(self,profile_receive: int) -> Optional[MessageSerializer]:            #ЕСТ
        result = get_all_messages_by_profile_receive(profile_receive)
        if result is not None:
            return MessageSerializer(result,many=True)
        return result
    
    def add_new_message(self,message:MessageSerializerPost) -> None:            #ЕСТЬ
        messag=json.dumps(message.data,ensure_ascii=False,default=MessageSerializerPost)
        message_data=json.loads(messag)
        create_message(profile_send=message_data.get('profileSend'),
                       profile_receive=message_data.get('profileReceive'),
                       message=message_data.get('message')
                         )
        
    
    def get_connection_by_id(self, id: int) -> Optional[ConnectionProfileSerializer]:            #ЕСТЬ
        result = get_connection_by_id(id)
        if result is not None:
            return ConnectionProfileSerializer(result)
        return result
    

    def get_connection_by_id_watcher(self, watcher_id: int) -> Optional[ConnectionProfileSerializer]:            #ЕСТЬ
        result = get_connection_by_id_watcher(watcher_id)
        if result is not None:
            return ConnectionProfileSerializer(result)
        return result



    
    def get_all_connection_one_profile(self,profile_watching: int) -> Optional[ConnectionProfileSerializer]:            #ЕСТ
        result = get_all_connection_by_id(profile_watching)
        if result is not None:
            return ConnectionProfileSerializer(result,many=True)
        return result
    
    def add_new_connection(self,connection:ConnectionProfileSerializerPost) -> None:            #ЕСТЬ
        conn=json.dumps(connection.data,ensure_ascii=False,default=ConnectionProfileSerializerPost)
        connection_data=json.loads(conn)
        create_connection(profile_share=connection_data.get('profileShare'),
                       profile_watching=connection_data.get('profileWatching')
                         )
        
    def add_new_connection_by_uuid(self,profile_id:int,uuid:str) -> None:            #ЕСТЬ
        create_connection_profile_by_uuid(profile_id=profile_id,uuid=uuid
                         )