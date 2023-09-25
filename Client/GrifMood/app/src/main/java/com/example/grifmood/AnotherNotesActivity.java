package com.example.grifmood;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.grifmood.databinding.ActivityAnotherNotesBinding;
import com.example.grifmood.databinding.ActivityAnotherProfileBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnotherNotesActivity extends AppCompatActivity {
    public ViewPagerAdapter viewPagerAdapter ;
    private ActivityAnotherNotesBinding binding;
    private String access;
    private int watcher_id;
    private  int share_id;
    private Integer id_test;
    private String name_test;
    private String id;
    private int id_send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAnotherNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        access = intent.getStringExtra("access");
        share_id = intent.getIntExtra("profileShare",0);
        checkAccess(access);
        setupViewPagerAdapter(binding.viewPagerAnotherNotes);

        //значок "выкл"
        binding.logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(AnotherNotesActivity.this, MainActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
            }
        });

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageResponsePost message= new MessageResponsePost(0,id_send,share_id,binding.descriptionText.getText().toString(),"2023-04-22 01:17:12.409");
                Call<MessageResponsePost> call = ApiClient.messageServicePost().MessageServicePost(message);
                call.enqueue(new Callback<MessageResponsePost>() {
                    @Override
                    public void onResponse(Call<MessageResponsePost> call, Response<MessageResponsePost> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(AnotherNotesActivity.this, "Рекомендация отправлена!", Toast.LENGTH_SHORT).show();
                            binding.descriptionText.setText("");
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageResponsePost> call, Throwable t) {

                    }
                });

            }
        });

    }

    private void checkAccess(String access) {

        Call<AuthorizationResponse> call = ApiClient.AuthorizationUserService().getloginUser(access);
        Log.d("Norm123234235", call.request().header("Authorization").toString());
        call.enqueue(new Callback<AuthorizationResponse>() {
            @Override
            public void onResponse(Call<AuthorizationResponse> call, Response<AuthorizationResponse> response) {
                Log.d("Norm", response.headers().toString());
                Log.d("Norm", response.toString());
                if (response.isSuccessful()) {
                    id = response.body().getId().toString();
                    if (!id.equals("Bad") && !id.equals("")) {
                        id_send=Integer.valueOf(id);
                    }
                } else {
                    id = "Bad";
                }
            }


            @Override
            public void onFailure(Call<AuthorizationResponse> call, Throwable t) {
                Log.d("Norm", "hm");
                id = "Bad";
                //Toast.makeText(ProfileActivity.this, "Проблемы с соединением!", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void setupViewPagerAdapter(final ViewPager viewPager){
        //список фрагментов "квартир"
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,this);

        //его создание
        viewPagerAdapter.addFragment(AnotherNotesAllFragment.newInstance(2,
                "","",access,share_id
        ));

        viewPagerAdapter.notifyDataSetChanged();
        viewPager.setAdapter(viewPagerAdapter);
    }


    public class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<AnotherNotesAllFragment> fragmentListTest = new ArrayList<>();
        private Context context;
        private ArrayList<String> fragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm, int behavior, Context context) {
            super(fm, behavior);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentListTest.get(position);
        }

        @Override
        public int getCount() {
            return fragmentListTest.size();
        }

        void addFragment(AnotherNotesAllFragment fragment){
            fragmentListTest.add(fragment);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);

        }

    }

    public String getAccess() {
        return access;

    }
    public Integer getIdTest() {
        return id_test;

    }
}