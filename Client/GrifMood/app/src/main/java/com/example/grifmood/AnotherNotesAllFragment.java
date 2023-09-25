package com.example.grifmood;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.grifmood.databinding.FragmentAllAnotherNotesBinding;
import com.example.grifmood.databinding.FragmentAllNotesBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnotherNotesAllFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class AnotherNotesAllFragment extends Fragment {

    // поля каждого фрагмента квартир
    String filteeeer="";
    private Integer assessment;
    private String description="";
    private String date="";
    String id="";
    int share_id;
    private String accessToken="";
    private java.util.ArrayList<ConditionResponse> ArrayList;
    private FragmentAllAnotherNotesBinding binding;
    private AdapterAnotherNote adapterNote; //одна квартира

    public AnotherNotesAllFragment() {
        // Required empty public constructor
    }


    public static AnotherNotesAllFragment newInstance(Integer assessment, String description, String date, String access, int share_id) {
        AnotherNotesAllFragment fragment = new AnotherNotesAllFragment();
        Bundle args = new Bundle();
        args.putInt("share_id",share_id);
        args.putString("access",access);
        args.putString("description",description);
        args.putInt("assessment",assessment);
        args.putString("date",date);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            description=getArguments().getString("description");
            assessment = getArguments().getInt("assessment");
            date = getArguments().getString("date");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAllAnotherNotesBinding.inflate(LayoutInflater.from(getContext()),container,false);


        binding.searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    //для всех доступных квартир применяем фу-ию filter (строка,введенная польз)
                    adapterNote.getFilter().filter(charSequence);
                    filteeeer=charSequence.toString(); //
                }
                catch (Exception e){
                    Log.d("Error","onTetChanged: search"+e.getMessage());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        share_id = getArguments().getInt("share_id",0);
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
                        loadAllCondition(id);
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
    private void loadAllCondition(String id) {

        ArrayList = new ArrayList<>();

        Call<List<ConditionResponse>> accessRespone = ApiClient.conditionAllServiceGet().getConditions(share_id);
        accessRespone.enqueue(new Callback<List<ConditionResponse>>() {
            @Override
            public void onResponse(Call<List<ConditionResponse>> call, Response<List<ConditionResponse>> response) {
                if (response.isSuccessful()) {
                    //для каждой квартиры из бд создаем model1 и добавляем ее в лист
                    Log.d("Otladka",response.body().toString());
                    for(int i=0;i<response.body().size();i++) {
                        binding.noNotes.setVisibility(View.GONE);
                        ConditionResponse model1 = new ConditionResponse( 1, 2,"0" ,"0",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
                        model1.setAssessment(response.body().get(i).getAssessment());
                        model1.setId(response.body().get(i).getId());
                        model1.setDescription(response.body().get(i).getDescription());
                        model1.setBar(response.body().get(i).getBar());
                        model1.setCleaning(response.body().get(i).getCleaning());
                        model1.setGaming(response.body().get(i).getGaming());
                        model1.setDate(response.body().get(i).getDate());
                        model1.setFamily(response.body().get(i).getFamily());
                        model1.setWork(response.body().get(i).getWork());
                        model1.setWorkout(response.body().get(i).getWorkout());
                        model1.setLeisure(response.body().get(i).getLeisure());
                        model1.setReading(response.body().get(i).getReading());
                        model1.setRendezvous(response.body().get(i).getRendezvous());
                        model1.setTravel(response.body().get(i).getTravel());
                        model1.setTV(response.body().get(i).getTV());
                        model1.setParty(response.body().get(i).getParty());
                        model1.setShopping(response.body().get(i).getShopping());
                        model1.setSleep(response.body().get(i).getSleep());
                        model1.setStudy(response.body().get(i).getStudy());
                        model1.setFriend(response.body().get(i).getFriend());
                        model1.setMovie(response.body().get(i).getMovie());
                        model1.setMusic(response.body().get(i).getMusic());
                        model1.setProfile(response.body().get(i).getProfile());
                        ArrayList.add(model1); //формирование листа

                        adapterNote = new AdapterAnotherNote(getContext(),ArrayList,accessToken); //лист с экз-ами доступных кв.

                        binding.allAnotherNotes.setAdapter(adapterNote); //вставка в форму фрагментов

                    }
                    if (response.body().size()==0)
                        binding.noNotes.setVisibility(View.VISIBLE);

                }
            }

            //запрос неуспешный
            @Override
            public void onFailure(Call<List<ConditionResponse>> call, Throwable t) {
                Toast.makeText(getContext(), "Проблемы с соединением!", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
