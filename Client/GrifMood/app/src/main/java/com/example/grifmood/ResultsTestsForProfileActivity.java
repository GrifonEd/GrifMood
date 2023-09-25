package com.example.grifmood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.grifmood.databinding.ActivityResultsTestsForProfileBinding;

import java.util.ArrayList;

public class ResultsTestsForProfileActivity extends AppCompatActivity {
    public ViewPagerAdapter viewPagerAdapter ;
    private ActivityResultsTestsForProfileBinding binding;
    private String access;
    private Integer id_test;
    private String name_test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultsTestsForProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupViewPagerAdapter(binding.viewPager);

        Intent intent = getIntent();
        id_test=intent.getIntExtra("id_test",1);
        access = intent.getStringExtra("access");
        name_test=intent.getStringExtra("name_test");

        binding.nameTest.setText(name_test);
        //значок "выкл"
        binding.logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(ResultsTestsForProfileActivity.this, MainActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
            }
        });

    }


    private void setupViewPagerAdapter(final ViewPager viewPager){
        //список фрагментов "квартир"
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,this);

        //его создание
        viewPagerAdapter.addFragment(ResultsTestsForProfileAllFragment.newInstance(2,
                ""
        ));

        viewPagerAdapter.notifyDataSetChanged();

        viewPager.setAdapter(viewPagerAdapter);
    }


    public class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<ResultsTestsForProfileAllFragment> fragmentListTest = new ArrayList<>();
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

        void addFragment(ResultsTestsForProfileAllFragment fragment){
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