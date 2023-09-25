package com.example.grifmood;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.grifmood.databinding.ActivityMenuBinding;
import com.example.grifmood.databinding.FragmentTestsBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TestsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestsFragment extends Fragment {
    ViewPagerAdapterTest viewPagerAdapterTest ;
    FragmentTestsBinding binding;
    String access;
    Context thiscontext;
    ViewPager viewPager;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TestsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TestsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TestsFragment newInstance(String param1, String param2) {
        TestsFragment fragment = new TestsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        access = getArguments().getString("access","");
        Log.d("Calendar",access);
        thiscontext = container.getContext();
        View rootView = inflater.inflate(R.layout.fragment_tests, container, false);
        viewPager = rootView.findViewById(R.id.viewPager);
        setupViewPagerAdapterTests(viewPager);
        return rootView;
    }

    private void setupViewPagerAdapterTests(final ViewPager viewPager){
        //список фрагментов "квартир"
        viewPagerAdapterTest = new ViewPagerAdapterTest(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,getActivity());

        //его создание
        viewPagerAdapterTest.addFragment(TestsAllFragment.newInstance(2,
                "",
                "",
                0,access
        ));

        viewPagerAdapterTest.notifyDataSetChanged();

        viewPager.setAdapter(viewPagerAdapterTest);
    }


    public class ViewPagerAdapterTest extends FragmentPagerAdapter {

        private ArrayList<TestsAllFragment> fragmentListTest = new ArrayList<>();
        private Context context;

        private ArrayList<String> fragmentTitleList = new ArrayList<>();

        public ViewPagerAdapterTest(FragmentManager fm, int behavior, Context context) {
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

        void addFragment(TestsAllFragment fragment){
            fragmentListTest.add(fragment);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);

        }

    }
}