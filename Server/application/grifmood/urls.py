from django.urls import include, path,re_path
from drf_yasg.views import get_schema_view
from drf_yasg import openapi
from rest_framework import permissions
from . import views



schema_view = get_schema_view(
    openapi.Info(
        title=" Mood API",
        default_version='v1',
        description="Mood API",
        terms_of_service="https://example.com",
        ontact=openapi.Contact(email="contact@mail.local"),
        license=openapi.License(name="BSD License"),
    ),
    public=True,
    permission_classes=[permissions.AllowAny],
)


urlpatterns = [
    
    path('condition/all', views.GetAllCondition.as_view()),
    path('condition/create', views.PostCondition.as_view()),
    path('condition/update', views.PutCondition.as_view()),
    path('condition/get/<int:id>', views.GetCondition.as_view()),
    path('condition/delete/<int:id>', views.DeleteCondition.as_view()),
    path('condition/profile/<int:id>', views.GetAllConditionByProfile.as_view()),
    path('condition/profileID/<int:id>', views.GetAllConditionByProfileID.as_view()),


    path('test/<int:id>', views.GetTest.as_view()),
    path('test/all', views.GetAllTest.as_view()),
    path('question/<int:id_test>/<int:number>', views.GetQuestion.as_view()),
    path('answer/<int:id_test>/<int:number>', views.GetAnswer.as_view()),
    path('resultTest/create', views.PostResultTest.as_view()),
    path('resultTest/<int:id_test>/<int:id_user>', views.GetResultTest.as_view()),
    path('resultTest/all/<int:id_test>/<int:id_user>', views.GetAllResultTest.as_view()),
    path('descriptionTest/<int:id_test>/<int:points>', views.GetDescriptionResult.as_view()),
    path('profile/<int:id>', views.GetPutProfile.as_view()),
    path('analysis/<int:id_user>/<str:caseMy>', views.GetAnalysis.as_view()),
    path('docs', views.Docs.as_view()),

    path('message/create', views.PostMessage.as_view()),
    path('message/get/<int:id>', views.GetMessage.as_view()),
    path('message/profile/<int:receive_id>/<int:send_id>', views.GetAllMessageByProfile.as_view()),
    path('message/profile/<int:receive_id>', views.GetAllMessageByProfileReceive.as_view()),

    path('connection/create', views.PostConnection.as_view()),
    path('connection/get/<int:id>', views.GetConnection.as_view()),

    path('connection/watcher/<int:watcher_id>', views.GetConnectionByWatcher.as_view()),


    path('connection/profile/<int:watching_id>', views.GetAllConnection.as_view()),

    path('connection/<int:profile_id>/<str:uuid>', views.PostConnectionByUuid.as_view()),

    
    path('condition/updatedate', views.UpdateDateCondition.as_view()),



    re_path(r'^swagger(?P<format>\.json|\.yaml)$',schema_view.without_ui(cache_timeout=0), name='schema-json'),
    re_path(r'^swagger/$', schema_view.with_ui('swagger', cache_timeout=0),name='schema-swagger-ui'),
]


