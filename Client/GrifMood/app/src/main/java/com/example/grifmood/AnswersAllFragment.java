package com.example.grifmood;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.grifmood.databinding.FragmentAnswersAllBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnswersAllFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class AnswersAllFragment extends Fragment {

    Integer test_id;
    Integer number__question;
    private Integer id;
    private String answer="";
    private Integer points;
    private String accessToken="";
    public java.util.ArrayList<AnswerResponce> ArrayList;
    private FragmentAnswersAllBinding binding;
    float point =0;
    AdapterAnswer adapterAnswer; //одна квартира

    public AnswersAllFragment(Integer test_id,Integer number__question) {
        this.test_id=test_id;
        this.number__question=number__question;
        // Required empty public constructor
    }
    public AnswersAllFragment() {

        // Required empty public constructor
    }



    public static AnswersAllFragment newInstance(Integer id, String answer, Integer points,Integer test_id,Integer number__question) {
        AnswersAllFragment fragment = new AnswersAllFragment( );
        Bundle args = new Bundle();
        args.putString("answer",answer);
        args.putInt("id",id);
        args.putInt("test_id",test_id);
        args.putInt("number__question",number__question);
        fragment.setArguments(args);
        fragment.test_id=test_id;
        fragment.number__question=number__question;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            test_id=getArguments().getInt("test_id");
            number__question = getArguments().getInt("number__question");
            accessToken = getArguments().getString("access");

        }



    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAnswersAllBinding.inflate(LayoutInflater.from(getContext()),container,false);
        TestPassActivity activity = (TestPassActivity)getActivity();
        Integer myCurrentNumber=activity.getCurrent_number_question();

        GetAllAnswer(test_id,myCurrentNumber); //получение доступных квартир каждые 10 сек

        return binding.getRoot();
    }
    private void GetAllAnswer(int id_test,int number__question) {

        ArrayList = new ArrayList<>();

        Call<List<AnswerResponce>> answer = ApiClient.answerServiceGet().getAnswer(id_test,number__question);
        answer.enqueue(new Callback<List<AnswerResponce>>() {
            @Override
            public void onResponse(Call<List<AnswerResponce>> call, Response<List<AnswerResponce>> response) {
                if (response.isSuccessful()) {
                    //для каждой квартиры из бд создаем model1 и добавляем ее в лист
                    Log.d("Answwer",response.headers().toString());
                    Log.d("Otladka",response.body().toString());
                    for(int i=0;i<response.body().size();i++) {
                        AnswerResponce model1 = new AnswerResponce( 1, "2", (float)1.0);
                        model1.setId(response.body().get(i).getId());
                        model1.setAnswer(response.body().get(i).getAnswer());
                        model1.setPoints(response.body().get(i).getPoints());
                        ArrayList.add(model1); //формирование листа
                    }

                    adapterAnswer= new AdapterAnswer(getContext(),ArrayList,accessToken,number__question); //лист с экз-ами доступных кв.

                    binding.allAnswers.setAdapter(adapterAnswer); //вставка в форму фрагментов

                }
            }

            //запрос неуспешный
            @Override
            public void onFailure(Call<List<AnswerResponce>> call, Throwable t) {

            }
        });
        binding.getRoot();
    }

    public float getPoint() {
        if (adapterAnswer!=null)
        point=adapterAnswer.get_pointsForQuest();
        else
            point=0;
        return point;
    }

    @Override
    public void onDestroyView() {
        point=adapterAnswer.get_pointsForQuest();
        Log.e("Отладка для удаления фрагмента",String.valueOf(point));
        super.onDestroyView();
    }


}
