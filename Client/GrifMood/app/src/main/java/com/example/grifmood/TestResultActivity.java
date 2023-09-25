package com.example.grifmood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.grifmood.databinding.ActivityTestResultBinding;
import com.example.grifmood.databinding.ActivityUpdateNoteBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestResultActivity extends AppCompatActivity {
    private String time;
    private int countQuest;
    private int points;
    private int id_test;
    private String test_name;
    private int userId;
    private ActivityTestResultBinding binding;
    private String access;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        time=intent.getStringExtra("time");
        countQuest=intent.getIntExtra("countQuest", 0);
        access=intent.getStringExtra("access");
        points=intent.getIntExtra("points",0);
        Log.e("Проверка баллов 2",String.valueOf(points));
        id_test=intent.getIntExtra("id_test", 1);
        test_name=intent.getStringExtra("test_name");
        userId=intent.getIntExtra("userId", -1);

        getResultDescription(id_test,points);

        binding.anotherTests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(TestResultActivity.this,MenuActivity.class);
                intent2.putExtra("access", access);
                intent2.putExtra("fragment", "tests");
                startActivity(intent2);
            }
        });


        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(TestResultActivity.this,MenuActivity.class);
                intent2.putExtra("access", access);
                intent2.putExtra("fragment", "calendar");
                startActivity(intent2);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        Intent intent2 = new Intent(TestResultActivity.this, MenuActivity.class);
        intent2.putExtra("access", access);
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent2);
        super.onBackPressed();  // optional depending on your needs
    }


    private void getResultDescription(int id_test,int pointsUser) {
        Log.e("Проверка баллов 3",String.valueOf(points));
        Call<ResultDescriptionResponce> call = ApiClient.descriptionServiceGet().getDescription(id_test,pointsUser);
        call.enqueue(new Callback<ResultDescriptionResponce>() {
            @Override
            public void onResponse(Call<ResultDescriptionResponce> call, Response<ResultDescriptionResponce> response) {

                Log.d("Norm", response.toString());
                if (response.isSuccessful()) {
                    binding.testName.setText("Результаты \n"+String.valueOf(test_name));
                    binding.txtkolquest.setText(String.valueOf(countQuest));
                    binding.txtkoltime.setText(String.valueOf(time));
                    binding.txtkolcoins.setText(String.valueOf(pointsUser));
                    binding.resultdesc.setText(String.valueOf(response.body().getDescription()));
                    int timeInSec=Integer.valueOf(time.substring(0,2))*60+Integer.valueOf(time.substring(3,5));
                    ResultTestResponcePost result = new ResultTestResponcePost(0,id_test,response.body().getDescription(),timeInSec,pointsUser,userId,"2022-12-10 17:26:04.456470");
                    Call<ResultTestResponcePost> callres = ApiClient.resultTestServicePost().postResult(result);
                    callres.enqueue(new Callback<ResultTestResponcePost>() {
                        @Override
                        public void onResponse(Call<ResultTestResponcePost> call, Response<ResultTestResponcePost> response) {
                            if(response.isSuccessful()) {
                                Log.e("Proverka otvet result test",response.headers().toString());
                            }

                        }

                        @Override
                        public void onFailure(Call<ResultTestResponcePost> call, Throwable t) {
                            Toast.makeText(TestResultActivity.this,"Результат не был записан",Toast.LENGTH_SHORT);
                        }
                    });

                }

            }

            @Override
            public void onFailure(Call<ResultDescriptionResponce> call, Throwable t) {
                Log.d("Norm", "hm");

            }
        });
    }
}