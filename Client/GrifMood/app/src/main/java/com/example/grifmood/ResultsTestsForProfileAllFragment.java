package com.example.grifmood;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.grifmood.databinding.FragmentTestsAllBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultsTestsForProfileAllFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultsTestsForProfileAllFragment extends Fragment {


    String id="";
    private String description="";
    private String access="";
    private int id_test;
    private int test_completion_time;
    private String accessToken="";
    private java.util.ArrayList<ResultTestResponce> ArrayList;
    private FragmentTestsAllBinding binding;
    private AdapterResultsTests adapterTest; //одна квартира
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ResultsTestsForProfileAllFragment() {
        // Required empty public constructor
    }


    public static ResultsTestsForProfileAllFragment newInstance(Integer id_test,  String access) {
        ResultsTestsForProfileAllFragment fragment = new ResultsTestsForProfileAllFragment();
        Bundle args = new Bundle();
        args.putString("access",access);
        args.putInt("id_test",id_test);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id_test=getArguments().getInt("id_test");
            access = getArguments().getString("access");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTestsAllBinding.inflate(LayoutInflater.from(getContext()),container,false);
        ResultsTestsForProfileActivity activity = (ResultsTestsForProfileActivity)getActivity();
        String  myaccess =activity.getAccess();
        id_test = activity.getIdTest();
        Log.e("FRAGMENT",myaccess);
        checkAccess(myaccess);
        accessToken=myaccess;

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
                        loadAllResultsTests(id_test,id);
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
    //запрос всех доступных квартир
    private void loadAllResultsTests(int id_test,String id) {

        ArrayList = new ArrayList<>();

        Call<List<ResultTestResponce>> testResponce = ApiClient.resultTestAllServiceGet().getAllResult(id_test,Integer.valueOf(id));
        testResponce.enqueue(new Callback<List<ResultTestResponce>>() {
            @Override
            public void onResponse(Call<List<ResultTestResponce>> call, Response<List<ResultTestResponce>> response) {
                if (response.isSuccessful()) {

                    if(response.body().size()!=0)
                        binding.noResults.setVisibility(View.GONE);
                    //для каждой квартиры из бд создаем model1 и добавляем ее в лист
                    Log.d("Otladka",response.body().toString());
                    for(int i=0;i<response.body().size();i++) {
                        ResultTestResponce model1 = new ResultTestResponce(1,"",10,10,"");
                        model1.setDescription(response.body().get(i).getDescription());
                        model1.setId(response.body().get(i).getId());
                        model1.setScore(response.body().get(i).getScore());
                        model1.setTest_completion_time(response.body().get(i).getTest_completion_time());
                        model1.setDate(response.body().get(i).getDate());

                        ArrayList.add(model1); //формирование листа

                        adapterTest = new AdapterResultsTests(getContext(),ArrayList,accessToken); //лист с экз-ами доступных кв.

                        binding.allTests.setAdapter(adapterTest); //вставка в форму фрагментов

                    }

                    if(response.body().size()==0)
                        binding.noResults.setVisibility(View.VISIBLE);
                }
            }

            //запрос неуспешный
            @Override
            public void onFailure(Call<List<ResultTestResponce>> call, Throwable t) {

            }
        });

    }

}