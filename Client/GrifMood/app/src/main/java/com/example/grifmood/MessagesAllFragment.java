package com.example.grifmood;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.grifmood.databinding.FragmentAllMessagesBinding;
import com.example.grifmood.databinding.FragmentAnswersAllBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessagesAllFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class MessagesAllFragment extends Fragment {

    Integer test_id;
    Integer number__question;
    private Integer id;
    private String answer="";
    private Integer points;
    private String accessToken="";
    public java.util.ArrayList<MessageResponse> ArrayList;
    private FragmentAllMessagesBinding binding;
    float point =0;
    AdapterMessage adapterMessage; //одна квартира

    public MessagesAllFragment(Integer test_id, Integer number__question) {
        this.test_id=test_id;
        this.number__question=number__question;
        // Required empty public constructor
    }
    public MessagesAllFragment() {

        // Required empty public constructor
    }

    public static MessagesAllFragment newInstance(Integer id, String answer, Integer points,Integer profile_id) {
        MessagesAllFragment fragment = new MessagesAllFragment( );
        Bundle args = new Bundle();
        args.putString("answer",answer);
        args.putInt("id",id);
        args.putInt("profile_id",profile_id);
        fragment.setArguments(args);
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
        binding = FragmentAllMessagesBinding.inflate(LayoutInflater.from(getContext()),container,false);

        Bundle bundle = getArguments();
        Integer profile_id=bundle.getInt("profile_id");

                GetAllMessages(profile_id); //получение доступных квартир каждые 10 сек

        return binding.getRoot();
    }
    private void GetAllMessages(int profile_id) {

        ArrayList = new ArrayList<>();

        Call<List<MessageResponse>> messages = ApiClient.messagesServiceGet().getMessages(profile_id);
        messages.enqueue(new Callback<List<MessageResponse>>() {
            @Override
            public void onResponse(Call<List<MessageResponse>> call, Response<List<MessageResponse>> response) {
                if (response.isSuccessful()) {
                    if(response.body().size()!=0) {
                        //binding.nameMedic.setText("Рекомендации от пользователя\n " + response.body().get(0).getProfileSend().getSecond_name() +" "+ response.body().get(0).getProfileSend().getFirst_name());
                       // binding.nameMedic.setVisibility(View.VISIBLE);
                       // binding.nameMedic.setTextSize(20);
                       // binding.nameMedic.setTextColor(Color.parseColor("#FF018786"));
                       // binding.nameMedic.setTypeface(Typeface.DEFAULT_BOLD);
                        binding.noNotes.setVisibility(View.GONE);
                    }//для каждой квартиры из бд создаем model1 и добавляем ее в лист
                    else{
                        binding.noNotes.setVisibility(View.VISIBLE);
                        binding.nameMedic.setVisibility(View.GONE);
                    }
                    Log.d("Answwer",response.headers().toString());
                    Log.d("Otladka",response.body().toString());
                    for(int i=0;i<response.body().size();i++) {
                        MessageResponse model1 = new MessageResponse();
                        model1.setId(response.body().get(i).getId());
                        model1.setMessage(response.body().get(i).getMessage());
                        model1.setProfileReceive(response.body().get(i).getProfileReceive());
                        model1.setDate_created(response.body().get(i).getDate_created());
                        model1.setProfileSend(response.body().get(i).getProfileSend());
                        ArrayList.add(model1); //формирование листа
                    }

                    if(response.body().size()!=0) {
                        adapterMessage = new AdapterMessage(getContext(), ArrayList, accessToken); //лист с экз-ами доступных кв.

                        binding.allMessages.setAdapter(adapterMessage); //вставка в форму фрагментов
                    }
                }
            }

            //запрос неуспешный
            @Override
            public void onFailure(Call<List<MessageResponse>> call, Throwable t) {

            }
        });
        binding.getRoot();
    }

}
