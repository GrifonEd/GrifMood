from rest_framework import serializers
from .models import User, Condition, Profile,Test,ResultTest,Question,Answer

class UserSerializer1(serializers.Serializer):
    id = serializers.IntegerField()
    username = serializers.CharField()
    email = serializers.CharField()
    date_joined = serializers.DateTimeField()
    is_active = serializers.BooleanField()
    is_staff= serializers.BooleanField()
    is_verified = serializers.BooleanField()

class ProfileSerializer(serializers.Serializer):
    user = UserSerializer1()
    id = serializers.IntegerField()
    first_name = serializers.CharField()
    second_name = serializers.CharField()
    sex = serializers.CharField()
    age= serializers.IntegerField()
    uuid=serializers.CharField()

class ConditionSerializer(serializers.Serializer):
    id = serializers.IntegerField()
    assessment = serializers.IntegerField()
    description = serializers.CharField(allow_blank=True)
    date = serializers.DateTimeField()
    profile = ProfileSerializer()
    work = serializers.IntegerField()
    reading = serializers.IntegerField()
    workout = serializers.IntegerField()
    gaming = serializers.IntegerField()
    family = serializers.IntegerField()
    friend = serializers.IntegerField()
    study = serializers.IntegerField()
    music = serializers.IntegerField()
    movie =  serializers.IntegerField()
    shopping = serializers.IntegerField()
    travel = serializers.IntegerField()
    cleaning = serializers.IntegerField()
    sleep = serializers.IntegerField()
    party = serializers.IntegerField()
    bar = serializers.IntegerField()
    leisure = serializers.IntegerField()
    rendezvous = serializers.IntegerField()
    TV = serializers.IntegerField()

class ConditionSerializerPost(ConditionSerializer):
    profile = serializers.IntegerField()

class TestSerializer(serializers.Serializer):
    id_test = serializers.IntegerField()
    test_name = serializers.CharField()
    description = serializers.CharField()
    test_completion_time = serializers.IntegerField()
    image = serializers.CharField()
    count_question=serializers.IntegerField()
    

class QuestionSerializer(serializers.Serializer):
    id = serializers.IntegerField()
    test = TestSerializer()
    number = serializers.IntegerField()
    question = serializers.CharField()
    

class AnswerSerializer(serializers.Serializer):
    id = serializers.IntegerField()
    question = QuestionSerializer()
    answer = serializers.CharField()
    points = serializers.IntegerField()

class ResultTestSerializer(serializers.Serializer):
    id = serializers.IntegerField()
    profile = ProfileSerializer()
    test = TestSerializer()
    description = serializers.CharField()
    test_completion_time = serializers.IntegerField()
    score = serializers.IntegerField()
    date=serializers.DateTimeField()

class ResultTestSerializerPost(ResultTestSerializer):
    profile = serializers.IntegerField()
    test = serializers.IntegerField()

class ResultDescriptionSerializer(serializers.Serializer):
    test = TestSerializer()
    pointsMin = serializers.IntegerField()
    pointsMax = serializers.IntegerField()
    description = serializers.CharField()

class MessageSerializer(serializers.Serializer):
    id = serializers.IntegerField()
    profileSend= ProfileSerializer()
    profileReceive =  ProfileSerializer()
    message= serializers.CharField()
    date_created=serializers.DateTimeField()



class MessageSerializerPost(serializers.Serializer):
    id = serializers.IntegerField()
    profileSend= serializers.IntegerField()
    profileReceive = serializers.IntegerField()
    message= serializers.CharField()
    date_created=serializers.DateTimeField()

class ConnectionProfileSerializer(serializers.Serializer):
    id = serializers.IntegerField()
    profileWatching=  ProfileSerializer()
    profileShare =  ProfileSerializer()


class ConnectionProfileSerializerPost(serializers.Serializer):
    id = serializers.IntegerField()
    profileWatching= serializers.IntegerField()
    profileShare = serializers.IntegerField()