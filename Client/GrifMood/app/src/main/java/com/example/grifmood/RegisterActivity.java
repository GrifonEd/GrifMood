package com.example.grifmood;

import androidx.appcompat.app.AppCompatActivity;
import com.example.grifmood.databinding.ActivityRegisterBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData(); //проверка данных введенных в форму регистрации
            }
        });
        //стрелочка назад
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.sexEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( binding.sexEt.getText().toString().equals("Мужской"))
                binding.sexEt.setText("Женский");
                else
                    binding.sexEt.setText("Мужской");
            }
        });

    }
    private String email="";
    private int id=1;
    private String password="";
    private String re_password="";
    private String username="";
    private int age;
    private String first_name="";
    private String second_name="";
    private String sex="";

    private void validateData() {
        first_name = binding.nameEt.getText().toString().trim(); //вносим в переменные данные из полей формы
        second_name = binding.secondName.getText().toString().trim();
        password = binding.passwordEt.getText().toString().trim();
        re_password = binding.rePasswordEt.getText().toString().trim();
        username = binding.usernameEt.getText().toString().trim();
        sex=binding.sexEt.getText().toString().trim();
        email=binding.emailEt.getText().toString().trim();
        if(!binding.ageEt.getText().toString().equals("")) {
            age = Integer.valueOf(binding.ageEt.getText().toString().trim());
            if (age > 100 || age < 0) {
                Toast.makeText(RegisterActivity.this, "Введите свой действительный возраст", Toast.LENGTH_SHORT).show();
            }
        }

        if(TextUtils.isEmpty(first_name)){
            Toast.makeText(RegisterActivity.this,"Введите свое имя", Toast.LENGTH_SHORT).show();
        }
        //пустая ли фамилия?
        else if(TextUtils.isEmpty(second_name)){
            Toast.makeText(RegisterActivity.this,"Введите фамилию", Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(username)){
            Toast.makeText(RegisterActivity.this,"Введите имя пользователя", Toast.LENGTH_SHORT).show();

        }

        else if(TextUtils.isEmpty(email)){
            Toast.makeText(RegisterActivity.this,"Введите адрес электронной почты", Toast.LENGTH_SHORT).show();

        }
        // и т.д.
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(RegisterActivity.this,"Введите пароль", Toast.LENGTH_SHORT).show();
        }
        else if(password.length()<8){
            Toast.makeText(RegisterActivity.this,"Пароль должен содержать не меньше 8-ми символов", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(re_password)){
            Toast.makeText(RegisterActivity.this,"Повторите пароль", Toast.LENGTH_SHORT).show();

        }

        //если подтверждение пароля не совпало с паролем
        else if(!password.equals((re_password))) {
            Toast.makeText(RegisterActivity.this, "Ваши пароли не совпадают!", Toast.LENGTH_SHORT).show();

        }
        else if (binding.ageEt.getText().toString().equals(""))
            Toast.makeText(RegisterActivity.this, "Введите свой возраст!", Toast.LENGTH_SHORT).show();


        else  //если нет такого же пользователя, создаем аккаунт нового пользователя
        {

            createUserAccount(); //создание нового аккаунта
        }

    }

    private void createUserAccount() {
        String uuid= UUID.randomUUID().toString().replace("-", "");
        UserResponse user = new UserResponse(email,id,password,re_password,username,age,first_name,second_name,sex,uuid);
        Call<UserResponse> call = ApiClient.postUserService().createUser(user);
        Log.d("Error",user.toString());
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                //если запрос неверный
                if (!response.isSuccessful()) {
                    Log.e("Тест занятого пользователя2",response.headers().toString());
                    Log.e("Тест занятого пользователя3",response.toString());
                    Gson gson = new Gson();
                    Type type = new TypeToken<ErrorResponse>() {}.getType();
                    ErrorResponse errorResponse = gson.fromJson(response.errorBody().charStream(),type);
                    if(errorResponse.getUsername()!=null)
                    if(errorResponse.getUsername().get(0).equals("user with this username already exists."))
                        Toast.makeText(RegisterActivity.this, "Пользователь с таким именем пользователя уже существует!", Toast.LENGTH_SHORT).show();
                    if(errorResponse.getPassword()!=null)
                    if(errorResponse.getPassword().get(0).equals("This password is entirely numeric.") || errorResponse.getPassword().get(0).equals("password is too common."))
                        Toast.makeText(RegisterActivity.this, "Пароль должен содержать не только цифры!", Toast.LENGTH_SHORT).show();
                    if(errorResponse.getPassword()!=null)
                        if(errorResponse.getPassword().get(1).equals("This password is entirely numeric.")|| errorResponse.getPassword().get(1).equals("password is too common."))
                            Toast.makeText(RegisterActivity.this, "Пароль должен содержать не только цифры!", Toast.LENGTH_SHORT).show();
                }
                else
                //запрос верный
                {
                    Toast.makeText(RegisterActivity.this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();

                }
            }

            //неуспешный запрос
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Отсутствует соединение с сервером", Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }
}