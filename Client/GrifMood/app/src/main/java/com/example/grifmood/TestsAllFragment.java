package com.example.grifmood;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.grifmood.databinding.FragmentAllNotesBinding;
import com.example.grifmood.databinding.FragmentTestsAllBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TestsAllFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestsAllFragment extends Fragment {


    String id="";
    private String description="";
    private String test_name="";
    private int id_test;
    private int test_completion_time;
    private String accessToken="";
    private java.util.ArrayList<TestResponce> ArrayList;
    private FragmentTestsAllBinding binding;
    private AdapterTest adapterTest; //одна квартира
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TestsAllFragment() {
        // Required empty public constructor
    }


    public static TestsAllFragment newInstance(Integer id_test,String description,String test_name,int test_completion_time,String access) {
        TestsAllFragment fragment = new TestsAllFragment();
        Bundle args = new Bundle();
        args.putString("access",access);
        args.putString("description",description);
        args.putInt("id_test",id_test);
        args.putInt("test_completion_time",test_completion_time);
        args.putString("test_name",test_name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            description=getArguments().getString("description");
            test_name = getArguments().getString("test_name");
            id_test = getArguments().getInt("id_test");
            test_completion_time = getArguments().getInt("test_completion_time");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTestsAllBinding.inflate(LayoutInflater.from(getContext()),container,false);

        String access = getArguments().getString("access","");
        Log.e("FRAGMENT",access);
        checkAccess(access);
        accessToken=access;

        return binding.getRoot();
    }
    private void checkAccess(String access) {

        Call<AuthorizationResponse> call = ApiClient.AuthorizationUserService().getloginUser(access);
        Log.d("Norm123234235",call.request().header("Authorization").toString());
        call.enqueue(new Callback<AuthorizationResponse>() {
            @Override
            public void onResponse(Call<AuthorizationResponse> call, Response<AuthorizationResponse> response) {
                Log.d("Norm",response.headers().toString());
                Log.d("Norm",response.toString());
                if(response.isSuccessful()){
                    id=response.body().getId().toString();
                    if(!id.equals("Bad")&&!id.equals("") )
                        loadAllTests(id);
                }
                else {
                    id="Bad";
                }

            }

            @Override
            public void onFailure(Call<AuthorizationResponse> call, Throwable t) {
                Log.d("Norm","hm");
                id="Bad";
                Toast.makeText(getContext(), "Проблемы с соединением!", Toast.LENGTH_SHORT).show();
            }
        });




    }
    //запрос всех доступных квартир
    private void loadAllTests(String id) {

        ArrayList = new ArrayList<>();

        Call<List<TestResponce>> testResponce = ApiClient.testsServiceGetAll().getTests();
        testResponce.enqueue(new Callback<List<TestResponce>>() {
            @Override
            public void onResponse(Call<List<TestResponce>> call, Response<List<TestResponce>> response) {
                if (response.isSuccessful()) {
                    //для каждой квартиры из бд создаем model1 и добавляем ее в лист
                    Log.d("Otladka",response.body().toString());
                    for(int i=0;i<response.body().size();i++) {
                        TestResponce model1 = new TestResponce( 1, "2","0" ,0,"",15);
                        model1.setId_test(response.body().get(i).getId_test());
                        model1.setDescription(response.body().get(i).getDescription());
                        model1.setTest_name(response.body().get(i).getTest_name());
                        model1.setTest_completion_time(response.body().get(i).getTest_completion_time());
                        model1.setCount_question(response.body().get(i).getCount_question());
                        model1.setImage(response.body().get(i).getImage());
                        ArrayList.add(model1); //формирование листа

                        adapterTest = new AdapterTest(getContext(),ArrayList,accessToken); //лист с экз-ами доступных кв.

                        binding.allTests.setAdapter(adapterTest); //вставка в форму фрагментов

                    }

                }
            }

            //запрос неуспешный
            @Override
            public void onFailure(Call<List<TestResponce>> call, Throwable t) {
                Toast.makeText(getContext(), "Проблемы с соединением!", Toast.LENGTH_SHORT).show();
            }
        });


    }

}