from django.shortcuts import render, redirect
from rest_framework.generics import CreateAPIView, RetrieveDestroyAPIView, GenericAPIView
from rest_framework.request import Request
from rest_framework.response import Response
from rest_framework.renderers import JSONRenderer
from rest_framework.views import APIView
from rest_framework import status
from drf_yasg.views import get_schema_view

import json
import collections


from .serializers import UserSerializer1,MessageSerializer,MessageSerializerPost,ConnectionProfileSerializer,ConnectionProfileSerializerPost, ConditionSerializer,ResultDescriptionSerializer, ProfileSerializer,TestSerializer,ResultTestSerializer,AnswerSerializer,QuestionSerializer,ConditionSerializerPost,ResultTestSerializerPost
from .services.mood_service import MoodService
# Create your views here.

service = MoodService()      # подключаем слой с бизнес-логикой

class PostCondition(GenericAPIView):
    serializer_class = ConditionSerializer   
    renderer_classes = [JSONRenderer]    
    
    
    def post(self, request: Request, *args, **kwargs) -> Response:
        """Метод для создания заметки """
        serializer = ConditionSerializerPost(data=request.data)
       # print(serializer)
        if serializer.is_valid():
            #print(serializer)
            service.add_new_condition(serializer)
            return Response(status=status.HTTP_201_CREATED)
       # print(serializer.errors)
        return Response(status=status.HTTP_500_INTERNAL_SERVER_ERROR)
    
class PutCondition(GenericAPIView):
    serializer_class = ConditionSerializer   
    renderer_classes = [JSONRenderer]                                        
    def put(self, request: Request, *args, **kwargs) -> Response:
        
        serializer = ConditionSerializerPost(data=request.data)

       # print(serializer)
        if serializer.is_valid():
            #print(serializer)
            service.put_condition(serializer)
            return Response(status=status.HTTP_201_CREATED)
        #print(serializer.errors)
        return Response(status=status.HTTP_500_INTERNAL_SERVER_ERROR)
    

class DeleteCondition(GenericAPIView):
    serializer_class = ConditionSerializer
    renderer_classes = [JSONRenderer]

    def delete(self, request: Request,id: int) -> Response:                                        
        service.delete_one_condition(id)
        return Response(status=status.HTTP_200_OK)
    
class GetCondition(GenericAPIView):
    serializer_class = ConditionSerializer
    renderer_classes = [JSONRenderer]
        
    def get(self, request: Request, id: int) -> Response:                                        
        response = service.get_condition_by_id(id)
        return Response(data=response.data)


class GetAllConditionByProfile(GenericAPIView):
    serializer_class = ConditionSerializer
    renderer_classes = [JSONRenderer]
        
    def get(self, request: Request, id: int) -> Response:     
        #print(id)                               
        response = service.get_all_condition_one_profile(id)
        return Response(data=response.data)


class GetAllConditionByProfileID(GenericAPIView):
    serializer_class = ConditionSerializer
    renderer_classes = [JSONRenderer]
        
    def get(self, request: Request, id: int) -> Response:     
        #print(id)                               
        response = service.get_all_condition_one_profileID(id)
        return Response(data=response.data)

class GetAllCondition(GenericAPIView):
    serializer_class = ConditionSerializer
    renderer_classes = [JSONRenderer]
        
    def get(self, request: Request) -> Response:             
        response = service.get_all_condition()
        return Response(data=response.data)

class GetAllTest(GenericAPIView):
    serializer_class = TestSerializer
    renderer_classes = [JSONRenderer]
        
    def get(self, request: Request) -> Response:             
        response = service.get_all_tests()
        return Response(data=response.data)
    
class GetTest(GenericAPIView):
    serializer_class = TestSerializer
    renderer_classes = [JSONRenderer]
        
    def get(self, request: Request,id:int) -> Response:             
        response = service.get_one_test(id)
        return Response(data=response.data)

class GetQuestion(GenericAPIView):
    serializer_class = TestSerializer
    renderer_classes = [JSONRenderer]
        
    def get(self, request: Request,id_test:int,number:int) -> Response:             
        response = service.get_one_question(id_test,number)
        return Response(data=response.data)

class GetAnswer(GenericAPIView):
    serializer_class = AnswerSerializer
    renderer_classes = [JSONRenderer]
        
    def get(self, request: Request,id_test:int,number:int) -> Response:             
        response = service.get_answers(id_test,number)
        return Response(data=response.data)
    

class PostResultTest(GenericAPIView):
    serializer_class = ResultTestSerializerPost   
    renderer_classes = [JSONRenderer]                                        
    def post(self, request: Request, *args, **kwargs) -> Response:
        
        serializer = ResultTestSerializerPost(data=request.data)

       # print(serializer)
        if serializer.is_valid():
            #print(serializer)
            service.add_new_result(serializer)
            return Response(status=status.HTTP_201_CREATED)
       # print(serializer.errors)
        return Response(status=status.HTTP_500_INTERNAL_SERVER_ERROR)

class GetResultTest(GenericAPIView):
    serializer_class = ResultTestSerializer
    renderer_classes = [JSONRenderer]
        
    def get(self, request: Request,id_test:int,id_user:int) -> Response:     
        response = service.get_one_result(test_id=id_test,id_user=id_user)
        return Response(data=response.data)
    
class GetAllResultTest(GenericAPIView):
    serializer_class = ResultTestSerializer
    renderer_classes = [JSONRenderer]
        
    def get(self, request: Request,id_test:int,id_user:int) -> Response:     
       # print(id_test,id_user)
        response = service.get_all_result(test_id=id_test,profile=id_user)
        #print(response)
        return Response(data=response.data)
    
class GetDescriptionResult(GenericAPIView):
    serializer_class = ResultDescriptionSerializer
    renderer_classes = [JSONRenderer]
        
    def get(self, request: Request,id_test:int,points:int) -> Response:     
        response = service.get_description_result(id_test=id_test,points=points)
        return Response(data=response.data)
    
    
class GetPutProfile(GenericAPIView):
    serializer_class = ProfileSerializer
    renderer_classes = [JSONRenderer]
        
    def get(self, request: Request, id: int) -> Response:     
        #print(id)                               
        response = service.get_one_profile(id)
        return Response(data=response.data)
    
class GetAnalysis(GenericAPIView):
    serializer_class = ConditionSerializer
    renderer_classes = [JSONRenderer]
        
    def get(self, request: Request, id_user: int,caseMy:str) -> Response:     
       # print(id_user)                               
        response = service.get_analysis(id_user,caseMy=caseMy)
        return Response(response)

class Docs(GenericAPIView):
    renderer_classes = [JSONRenderer]

    def get(self, request: Request) -> Response:                                        
        docs = {
	"Status": 'Скоро будет'
}

        response = docs
        return Response(response)

  #его нужно будет удалить  
class UpdateDateCondition(GenericAPIView):
    serializer_class = ConditionSerializer   
    renderer_classes = [JSONRenderer]                                        
    def put(self, request: Request, *args, **kwargs) -> Response:
        
        serializer = ConditionSerializerPost(data=request.data)

        #print(serializer)
        if serializer.is_valid():
            #print(serializer)
            service.put_condition1(serializer)
            return Response(status=status.HTTP_201_CREATED)
       # print(serializer.errors)
        return Response(status=status.HTTP_500_INTERNAL_SERVER_ERROR)

class PostMessage(GenericAPIView):
    serializer_class = MessageSerializerPost   
    renderer_classes = [JSONRenderer]    
    
    
    def post(self, request: Request, *args, **kwargs) -> Response:
        """Метод для создания заметки """
        serializer = MessageSerializerPost(data=request.data)
       
        if serializer.is_valid():
            
            service.add_new_message(serializer)
            return Response(status=status.HTTP_201_CREATED)
       
        return Response(status=status.HTTP_500_INTERNAL_SERVER_ERROR)
    
class PostConnection(GenericAPIView):
    serializer_class = ConnectionProfileSerializerPost   
    renderer_classes = [JSONRenderer]    
    
    
    def post(self, request: Request, *args, **kwargs) -> Response:
        """Метод для создания заметки """
        serializer = ConnectionProfileSerializerPost(data=request.data)
       
        if serializer.is_valid():
            
            service.add_new_connection(serializer)
            return Response(status=status.HTTP_201_CREATED)
       
        return Response(status=status.HTTP_500_INTERNAL_SERVER_ERROR)
    
class GetMessage(GenericAPIView):
    serializer_class = MessageSerializer
    renderer_classes = [JSONRenderer]
        
    def get(self, request: Request, id: int) -> Response:                                        
        response = service.get_message_by_id(id)
        return Response(data=response.data)

    def delete(self, request: Request, id: int) -> Response:                                        
        service.delete_message_by_id(id)
        return Response(status=status.HTTP_200_OK)
        

class GetAllMessageByProfile(GenericAPIView):
    serializer_class = MessageSerializer
    renderer_classes = [JSONRenderer]
        
    def get(self, request: Request, receive_id: int,send_id:int) -> Response:                                  
        response = service.get_all_message_one_profile(receive_id,send_id)
        return Response(data=response.data)
    


class GetAllMessageByProfileReceive(GenericAPIView):
    serializer_class = MessageSerializer
    renderer_classes = [JSONRenderer]
        
    def get(self, request: Request, receive_id: int) -> Response:                                  
        response = service.get_all_message_one_profile_receive(receive_id)
        return Response(data=response.data)
    
    

    
class GetConnection(GenericAPIView):
    serializer_class = ConnectionProfileSerializer
    renderer_classes = [JSONRenderer]
        
    def get(self, request: Request, id: int) -> Response:                                        
        response = service.get_connection_by_id(id)
        return Response(data=response.data)

    
class GetConnectionByWatcher(GenericAPIView):
    serializer_class = ConnectionProfileSerializer
    renderer_classes = [JSONRenderer]
        
    def get(self, request: Request, watcher_id: int) -> Response:                                        
        response = service.get_connection_by_id_watcher(watcher_id)
        return Response(data=response.data)

        


class GetAllConnection(GenericAPIView):
    serializer_class = ConnectionProfileSerializer
    renderer_classes = [JSONRenderer]
        
    def get(self, request: Request, watching_id: int) -> Response:                                  
        response = service.get_all_connection_one_profile(watching_id)
        return Response(data=response.data)
    

        
class PostConnectionByUuid(GenericAPIView):
    serializer_class = ConnectionProfileSerializer   
    renderer_classes = [JSONRenderer]    
    
    def post(self, request: Request, profile_id:int,uuid:str) -> Response:
            service.add_new_connection_by_uuid(profile_id=profile_id,uuid=uuid)
            return Response(status=status.HTTP_201_CREATED)