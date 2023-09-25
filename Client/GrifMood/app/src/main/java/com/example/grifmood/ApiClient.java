package com.example.grifmood;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    private static Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.167:8000/") //первичная ссылка
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create()) //преобразование json-объектов
                .build();

        return retrofit;
    }
    public  static  ConditionServiceGet getConditionService(){
        ConditionServiceGet conditionService = getRetrofit().create(ConditionServiceGet.class);
        return conditionService;
    }

    public  static  ConditionAllProfileServiceGet conditionAllProfileServiceGet(){
        ConditionAllProfileServiceGet conditionAllProfileServiceGet = getRetrofit().create(ConditionAllProfileServiceGet.class);
        return conditionAllProfileServiceGet;
    }
    public  static  ConditionServiceGetOne getConditionServiceOne(){
        ConditionServiceGetOne conditionService = getRetrofit().create(ConditionServiceGetOne.class);
        return conditionService;
    }

    public  static  ConditionServicePost postConditionService(){
        ConditionServicePost conditionService = getRetrofit().create(ConditionServicePost.class);
        return conditionService;
    }
    public  static  ConditionServicePut putConditionService(){
        ConditionServicePut conditionService = getRetrofit().create(ConditionServicePut.class);
        return conditionService;
    }

    public  static  UserServicePost postUserService(){
        UserServicePost userService = getRetrofit().create(UserServicePost.class);
        return userService;
    }
    public  static  UserServiceLogin loginUserService(){
        UserServiceLogin userService = getRetrofit().create(UserServiceLogin.class);
        return userService;
    }
    public  static  Authorization AuthorizationUserService(){
        Authorization userService = getRetrofit().create(Authorization.class);
        return userService;
    }
    public  static  ConditionDelete DeleteConditionService(){
        ConditionDelete ConditionService = getRetrofit().create(ConditionDelete.class);
        return ConditionService;
    }

    public  static  TestsServiceGetAll testsServiceGetAll(){
        TestsServiceGetAll testService = getRetrofit().create(TestsServiceGetAll.class);
        return testService;
    }

    public  static  QuestionServiceGet questionServiceGet(){
        QuestionServiceGet questionService = getRetrofit().create(QuestionServiceGet.class);
        return questionService;
    }
    public  static  AnswerServiceGet answerServiceGet(){
        AnswerServiceGet answerService = getRetrofit().create(AnswerServiceGet.class);
        return answerService;
    }
    public  static  TestDescriptionServiceGet descriptionServiceGet(){
        TestDescriptionServiceGet descriptionServiceGet = getRetrofit().create(TestDescriptionServiceGet.class);
        return descriptionServiceGet;
    }

    public  static  ResultTestServicePost resultTestServicePost(){
        ResultTestServicePost resultTestServicePost = getRetrofit().create(ResultTestServicePost.class);
        return resultTestServicePost;
    }
    public  static  AnalysisServiceGet analysisServiceGet(){
        AnalysisServiceGet analysisServiceGet = getRetrofit().create(AnalysisServiceGet.class);
        return analysisServiceGet;
    }

    public  static  ResultTestAllServiceGet resultTestAllServiceGet(){
        ResultTestAllServiceGet resultTestAllServiceGet = getRetrofit().create(ResultTestAllServiceGet.class);
        return resultTestAllServiceGet;
    }

    public static TestsServiceGetOne testsServiceGetOne() {
        TestsServiceGetOne testsServiceGetOne = getRetrofit().create(TestsServiceGetOne.class);
        return testsServiceGetOne;
    }

    public static ProfileServiceGet profileServiceGet() {
        ProfileServiceGet profileServiceGet = getRetrofit().create(ProfileServiceGet.class);
        return profileServiceGet;
    }


    public static ConnectionServicePost connectionServicePost() {
        ConnectionServicePost connectionServicePost = getRetrofit().create(ConnectionServicePost.class);
        return connectionServicePost;
    }

    public static MessagesServiceGet messagesServiceGet() {
        MessagesServiceGet messagesServiceGet = getRetrofit().create(MessagesServiceGet.class);
        return messagesServiceGet;
    }

    public static ConnectionServiceGet connectionServiceGet() {
        ConnectionServiceGet connectionServiceGet = getRetrofit().create(ConnectionServiceGet.class);
        return connectionServiceGet;
    }

    public static AllConnectionServiceGet allConnectionServiceGet() {
        AllConnectionServiceGet allConnectionServiceGet = getRetrofit().create(AllConnectionServiceGet.class);
        return allConnectionServiceGet;
    }

    public static ConditionAllServiceGet conditionAllServiceGet() {
        ConditionAllServiceGet conditionAllServiceGet = getRetrofit().create(ConditionAllServiceGet.class);
        return conditionAllServiceGet;
    }

    public static MessageServicePost messageServicePost() {
        MessageServicePost messageServicePost = getRetrofit().create(MessageServicePost.class);
        return messageServicePost;
    }

    public static MessageServiceDelete messageServiceDelete() {
        MessageServiceDelete messageServiceDelete = getRetrofit().create(MessageServiceDelete.class);
        return messageServiceDelete;
    }

}
