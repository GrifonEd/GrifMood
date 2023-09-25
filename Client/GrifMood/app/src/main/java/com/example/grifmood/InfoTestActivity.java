package com.example.grifmood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.example.grifmood.databinding.ActivityInfoTestBinding;
import com.example.grifmood.databinding.ActivityMenuBinding;
import com.example.grifmood.databinding.ActivityOneTestBinding;

import java.io.IOException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InfoTestActivity extends AppCompatActivity {
    String access="";
    int test_id;
    String id;
    String golos;
    ActivityInfoTestBinding binding;
    String test_name ;
    String image ;
    int test_completion_time ;
    String description ;
    int count_question;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInfoTestBinding.inflate(getLayoutInflater());
        Intent intent = getIntent();
        String access = intent.getStringExtra("access");
        String golos = intent.getStringExtra("golos");
        test_id = intent.getIntExtra("test_id", -1);

        if(golos.equals("yes"))
        {
            Call<TestResponce> callTest=ApiClient.testsServiceGetOne().getTest(test_id);
            callTest.enqueue(new Callback<TestResponce>() {
                @Override
                public void onResponse(Call<TestResponce> call, Response<TestResponce> response) {
                    test_name= response.body().getTest_name();
                    image = response.body().getImage();
                    test_completion_time = response.body().getTest_completion_time();
                    description = response.body().getDescription();
                    count_question = response.body().getCount_question();

                    try(InputStream inputStream = getApplicationContext().getAssets().open(image)){
                        Drawable drawable = Drawable.createFromStream(inputStream, null);
                        binding.iconIv.setImageDrawable(drawable);
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                    binding.testDes.setText(description);
                    binding.testDes.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                    binding.testName.setText(test_name);
                    binding.testTime.setText("Время прохождения теста: "+String.valueOf(test_completion_time)+" минут");

                }

                @Override
                public void onFailure(Call<TestResponce> call, Throwable t) {

                }
            });
        }
        else {
            test_name = intent.getStringExtra("test_name");
            image = intent.getStringExtra("image");
            test_completion_time = intent.getIntExtra("test_completion_time", 10);
            description = intent.getStringExtra("description");

            count_question = intent.getIntExtra("count_question", 10);

            try(InputStream inputStream = getApplicationContext().getAssets().open(image)){
                Drawable drawable = Drawable.createFromStream(inputStream, null);
                binding.iconIv.setImageDrawable(drawable);
            }
            catch (IOException e){
                e.printStackTrace();
            }
            binding.testDes.setText(description);
            binding.testDes.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            binding.testName.setText(test_name);
            binding.testTime.setText("Время прохождения теста: "+String.valueOf(test_completion_time)+" минут");

        }
        setContentView(binding.getRoot());

        binding.goTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<AuthorizationResponse> call = ApiClient.AuthorizationUserService().getloginUser(access);
                Log.d("Norm123234235",call.request().header("Authorization").toString());
                call.enqueue(new Callback<AuthorizationResponse>() {
                    @Override
                    public void onResponse(Call<AuthorizationResponse> call, Response<AuthorizationResponse> response) {
                        Log.d("Norm",response.headers().toString());
                        Log.d("Norm",response.toString());
                        if(response.isSuccessful()){
                            id=response.body().getId().toString();
                            if(!id.equals("Bad")&&!id.equals("") ) {
                                Intent intent1 = new Intent(InfoTestActivity.this,TestPassActivity.class) ;
                                intent1.putExtra("access", access);
                                intent1.putExtra("count_question", count_question);
                                intent1.putExtra("test_id", test_id);
                                intent1.putExtra("test_name", test_name);
                                intent1.putExtra("id_user", Integer.valueOf(id));
                                startActivity(intent1);
                            }
                        }
                        else {
                            id="Bad";
                        }

                    }

                    @Override
                    public void onFailure(Call<AuthorizationResponse> call, Throwable t) {
                        Log.d("Norm","hm");
                        id="Bad";
                    }
                });

            }
        });

        binding.backloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}