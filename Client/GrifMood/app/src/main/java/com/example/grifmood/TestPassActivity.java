package com.example.grifmood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.example.grifmood.databinding.ActivityInfoTestBinding;
import com.example.grifmood.databinding.ActivityTestPassBinding;
import com.example.grifmood.databinding.FragmentTestsBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestPassActivity extends AppCompatActivity implements AdapterAnswer.OnAnswerListener {
    private float point_for_question=0;
    ViewPagerAdapter viewPagerAdapter ;
    private boolean On=true;
    Chronometer chronometer;
    CountDownTimer countDownTimer;
    String access="";
    String test_name;
    int test_id;
    int count_question;
    String id;
    private int userId;
    ActivityTestPassBinding binding;
    private int totalTime;
    private int totalTimeInMins = 1;
    private int seconds = 0;
    float summaPoints=0;
    ViewPager viewPager;
    int current_number_question=1;
    float[] ochki;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestPassBinding.inflate(getLayoutInflater());
        Intent intent = getIntent();
        String access = intent.getStringExtra("access");
        test_id = intent.getIntExtra("test_id",-1);
        test_name = intent.getStringExtra("test_name");
        count_question = intent.getIntExtra("count_question",10);
        userId=intent.getIntExtra("id_user", -1);
        setContentView(binding.getRoot());
        ochki = new float[count_question];
        binding.timer.start();

        loadQuestion(current_number_question);

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.predQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(current_number_question>1){
                    current_number_question=current_number_question-1;
                    summaPoints=summaPoints-ochki[current_number_question];
                    if(point_for_question>0){
                        summaPoints=summaPoints-point_for_question;
                    }
                    binding.progressbar.setProgress(current_number_question-1);
                    loadQuestion(current_number_question);
                    On=true;
                }
                if(current_number_question!=count_question){

                }
            }
        });

        binding.progressbar.setMax(count_question);
        binding.progressbar.setProgress(0);

        binding.nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(On==false){
                if(current_number_question<count_question) {
                    ochki[current_number_question]=point_for_question;

                    current_number_question = current_number_question + 1;

                    binding.progressbar.setProgress(current_number_question-1);

                        loadQuestion(current_number_question);
                    On=true;
                }
                else if(current_number_question==count_question){


                    Intent intent = new Intent(TestPassActivity.this,TestResultActivity.class);
                    intent.putExtra("time", binding.timer.getText().toString());
                    intent.putExtra("countQuest", count_question);

                    intent.putExtra("points",Math.round(summaPoints));
                    intent.putExtra("id_test",test_id);
                    intent.putExtra("test_name",test_name);
                    Log.e("Проверка user", String.valueOf(userId));
                    intent.putExtra("access",access);
                    intent.putExtra("userId",userId);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }
            }
                else{
                    Toast.makeText(TestPassActivity.this,"Выберите один вариант ответа!",Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    private void loadQuestion(int question_number) {
        Call<QuestionResponce> call = ApiClient.questionServiceGet().getQuestion(test_id, question_number);
        call.enqueue(new Callback<QuestionResponce>() {
            @Override
            public void onResponse(Call<QuestionResponce> call, Response<QuestionResponce> response) {

                Log.d("Norm", response.toString());
                if (response.isSuccessful()) {
                    Log.d("question", response.body().getQuestion().toString());
                    binding.textofquest.setText(response.body().getQuestion());
                    binding.questions.setText(String.valueOf(response.body().getNumber()) + "/" + String.valueOf(count_question));
                    setupViewPagerAdapter(binding.viewPager);

                }

            }

            @Override
            public void onFailure(Call<QuestionResponce> call, Throwable t) {
                Log.d("Norm", "hm");
                id = "Bad";
            }
        });
    }

    private void setupViewPagerAdapter(final ViewPager viewPager){
        //список фрагментов "квартир"

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,this);



      //  viewPagerAdapter.addFragment(
      //          new AnswersAllFragment(test_id,current_number_question));
        //его создание
        viewPagerAdapter.addFragment(
                AnswersAllFragment.newInstance( 2,
                        "",
                        1,test_id,current_number_question));

        viewPagerAdapter.notifyDataSetChanged();
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public void onAnswerListener(Intent intent) {
        summaPoints+=(intent.getFloatExtra("points",0));
        point_for_question=intent.getFloatExtra("points",0);
        On=intent.getBooleanExtra("otvet",true);
        Log.e("Пробую интерфейс",String.valueOf(summaPoints));
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter{

        private ArrayList<AnswersAllFragment> fragmentList = new ArrayList<>();

        private Context context;

        private ArrayList<String> fragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm, int behavior, Context context) {
            super(fm, behavior);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        void addFragment(AnswersAllFragment fragment){
            Bundle bundle = new Bundle();
            bundle.putInt("number__question", current_number_question);
            bundle.putInt("test_id", test_id);
            fragment.setArguments(bundle);
            fragmentList.add(fragment);

        }

        public void refresh(){
            viewPagerAdapter.notifyDataSetChanged();
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);

        }

        @Override
        public int getItemPosition(final Object object)
        {
            return POSITION_NONE;
        }

        public void removeLastFragment() {
            fragmentList.remove(fragmentList.size() - 1);
            notifyDataSetChanged();
        }

        public void destroyItem(int i) {
            fragmentList.remove(i);
        }
    }
    public  int getCurrent_number_question(){
        return current_number_question;
    }
    public  void setCurrent_number_question(int number){
        current_number_question=number;
    }

}