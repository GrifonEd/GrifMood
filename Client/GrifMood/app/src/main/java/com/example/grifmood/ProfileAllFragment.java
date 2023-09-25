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

import com.example.grifmood.databinding.FragmentAllNotesBinding;
import com.example.grifmood.databinding.FragmentAllProfilesBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileAllFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class ProfileAllFragment extends Fragment {

    // поля каждого фрагмента квартир
    String filteeeer="";
    private Integer assessment;
    private String description="";
    private String date="";
    String id="";
    private String accessToken="";
    private java.util.ArrayList<ProfileResponse> ArrayList;
    private FragmentAllProfilesBinding binding;
    private AdapterProfile adapterNote; //одна квартира

    public ProfileAllFragment() {
        // Required empty public constructor
    }


    public static ProfileAllFragment newInstance(Integer assessment, String description, String date, String access) {
        ProfileAllFragment fragment = new ProfileAllFragment();
        Bundle args = new Bundle();
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
        binding = FragmentAllProfilesBinding.inflate(LayoutInflater.from(getContext()),container,false);




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

        Call<List<ConnectionResponse>> accessRespone = ApiClient.allConnectionServiceGet().AllConnectionServiceGet(Integer.valueOf(id));
        accessRespone.enqueue(new Callback<List<ConnectionResponse>>() {
            @Override
            public void onResponse(Call<List<ConnectionResponse>> call, Response<List<ConnectionResponse>> response) {
                if (response.isSuccessful()) {
                    //для каждой квартиры из бд создаем model1 и добавляем ее в лист
                    Log.d("Otladka", response.body().toString());
                    for (int i = 0; i < response.body().size(); i++) {
                        binding.noNotes.setVisibility(View.GONE);
                        ProfileResponse model1 = new ProfileResponse();
                        model1.setId(response.body().get(i).getProfileShare().getId());
                        model1.setFirst_name(response.body().get(i).getProfileShare().getFirst_name());
                        model1.setSecond_name(response.body().get(i).getProfileShare().getSecond_name());
                        model1.setAge(response.body().get(i).getProfileShare().getAge());
                        model1.setSex(response.body().get(i).getProfileShare().getSex());

                        ArrayList.add(model1); //формирование листа

                        adapterNote = new AdapterProfile(getContext(), ArrayList, accessToken); //лист с экз-ами доступных кв.

                        binding.allProfiles.setAdapter(adapterNote); //вставка в форму фрагментов

                    }
                    if (response.body().size() == 0)
                        binding.noNotes.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(Call<List<ConnectionResponse>> call, Throwable t) {

            }
        });

    }

}
