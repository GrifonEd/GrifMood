package com.example.grifmood;

import androidx.appcompat.app.AppCompatActivity;
import com.example.grifmood.databinding.ActivityLoginBinding;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.noAccountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(com.example.grifmood.LoginActivity.this, com.example.grifmood.RegisterActivity.class));
            }
        });

        //стрелка сверху "назад"
        binding.backloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //log in (вход)
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();

            }

        });

    }
    private String username = "",password = "";

    private void validateData() {
        //пароль и паспорт взятые из полей текстбоксов типа строка
        username = binding.Login.getText().toString();
        password = binding.passwordEt2.getText().toString();
        UserLoginResponse user = new UserLoginResponse(username,password);
        Call<UserLoginResponse> call=ApiClient.loginUserService().loginUser(user);
        call.enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Неправильный пароль или логин!", Toast.LENGTH_SHORT).show();
                }
                else
                //запрос верный
                {
                    String access=response.body().getAccess().toString();
                    String refresh  =response.body().getRefresh().toString();
                    Log.d("Norm",response.body().getAccess().toString());
                    AuthorizationResponse authorizationResponse = new AuthorizationResponse(response.body().getAccess());
                    Call<AuthorizationResponse> call1 = ApiClient.AuthorizationUserService().getloginUser("Bearer "+response.body().getAccess());
                    Log.d("Norm123234235",call1.request().header("Authorization").toString());
                    call1.enqueue(new Callback<AuthorizationResponse>() {
                        @Override
                        public void onResponse(Call<AuthorizationResponse> call, Response<AuthorizationResponse> response) {
                            Log.d("Norm",response.headers().toString());
                            Log.d("Norm",response.toString());
                            if(response.isSuccessful()){
                                Toast.makeText(LoginActivity.this, "Успешная авторизация!", Toast.LENGTH_SHORT).show();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        Intent intent = new Intent(LoginActivity.this,MenuActivity.class);
                                        intent.putExtra("access","Bearer "+access);
                                        intent.putExtra("refresh",refresh);
                                        startActivity(intent);
                                    }
                                }, 500);
                            }
                        }

                        @Override
                        public void onFailure(Call<AuthorizationResponse> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "Проблемы с соединением!", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }

            @Override
            public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Проблемы с соединением", Toast.LENGTH_SHORT).show();

            }
        });
    }
}