package com.example.grifmood;

import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {
    CalendarView calendarView;
    private  String id;
    private  ArrayList<ConditionResponse> arrayList;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Calendar.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
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
        String access = getArguments().getString("access", "");
        Log.d("Calendar", access);
        View view;
        view = inflater.inflate(R.layout.fragment_calendar, container, false);
        int myColor = Color.argb(127, 255, 0, 255);
        calendarView = (CalendarView) view.findViewById(R.id.calendarView);

        checkAccess(access);

        return view;
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

    private void loadAllCondition(String id) {

        ArrayList<Calendar> mcalendar = new ArrayList<>();
        List<EventDay> events = new ArrayList<>();
        Call<List<ConditionResponse>> accessRespone = ApiClient.getConditionService().getConditions(Integer.valueOf(id));
        accessRespone.enqueue(new Callback<List<ConditionResponse>>() {
            @Override
            public void onResponse(Call<List<ConditionResponse>> call, Response<List<ConditionResponse>> response) {
                if (response.isSuccessful()) {
                    //для каждой квартиры из бд создаем model1 и добавляем ее в лист
                    Log.d("Otladka",response.body().toString());
                    for(int i=0;i<response.body().size();i++) {
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
                        //формирование листа

                        String date = model1.getDate();
                        String[]words = date.split("-");
                        String year=words[0];
                        String month=words[1];
                        int monthInt=Integer.valueOf(month)-1;
                        String[]words1=words[2].split("T");
                        String day=words1[0];

                        mcalendar.add(Calendar.getInstance());
                        mcalendar.get(i).set(Integer.valueOf(year),Integer.valueOf(monthInt),Integer.valueOf(day));

                        try {
                            calendarView.setDate(mcalendar.get(i));
                        } catch (OutOfDateRangeException e) {
                            e.printStackTrace();
                        }

                        if(model1.getAssessment()==1)
                        events.add(new EventDay(mcalendar.get(i), R.drawable.verybad));
                        else if(model1.getAssessment()==2)
                            events.add(new EventDay(mcalendar.get(i), R.drawable.bad));
                        else if(model1.getAssessment()==3)
                            events.add(new EventDay(mcalendar.get(i), R.drawable.sred));
                        else if(model1.getAssessment()==4)
                            events.add(new EventDay(mcalendar.get(i), R.drawable.good));
                        else if(model1.getAssessment()==5)
                            events.add(new EventDay(mcalendar.get(i), R.drawable.kaif));

                    }
                    calendarView.setEvents(events);

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