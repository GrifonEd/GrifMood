package com.example.grifmood;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.dynamicanimation.animation.FloatValueHolder;
import androidx.fragment.app.Fragment;

import android.service.autofill.Dataset;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grifmood.databinding.ActivityInfoTestBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import com.example.grifmood.databinding.FragmentStatisticsBinding;
import com.google.android.material.navigation.NavigationBarView;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticsFragment extends Fragment {
    String id="";
    LineChart chart1;
    LineChart chart2;
    LineChart chartPoint;
    LineChart chartAgesGender;
    BarChart barChart;
    private PieChart chartTest;
    FragmentStatisticsBinding binding;
    TextView textView ;
    TextView nodata;
    TextView sredNastr;
    TextView caseText;
    TextView descbar;
    TextView caseText3;
    TextView descGender;
    String gender="Все";
    String age="Все";
    Drawable iconCase;
    TextView sredNastrAll;
    TextView caseText2;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList ArrayList;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatisticsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticsFragment newInstance(String param1, String param2) {
        StatisticsFragment fragment = new StatisticsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_statistics, container, false);
        // Inflate the layout for this fragment
        String access = getArguments().getString("access","");
        Call<AuthorizationResponse> call = ApiClient.AuthorizationUserService().getloginUser(access);
        Log.d("Norm123234235",call.request().header("Authorization").toString());
        call.enqueue(new Callback<AuthorizationResponse>() {
            @Override
            public void onResponse(Call<AuthorizationResponse> call, Response<AuthorizationResponse> response) {
                Log.d("Norm",response.headers().toString());
                Log.d("Norm",response.toString());
                if(response.isSuccessful()){
                    id=response.body().getId().toString();
                    if(!id.equals("Bad")&&!id.equals("") ) {
                        loadAllCondition(id, "Все");
                        loadAllConditionByCase(id,"Работа");
                        loadAnalysisByCase(id,0,"Работа");
                        loadAllConditionByGenderAge(id,"Все","Все");
                        loadAllConditionBarChart(id);
                    }
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


        Log.d("Calendar",access);

        textView=(TextView)rootView.findViewById(R.id.ResultAnalysis);
        chart1 = (LineChart)rootView.findViewById(R.id.lineChart);
        chart2=(LineChart)rootView.findViewById(R.id.lineChartByCase);
        chartPoint = (LineChart) rootView.findViewById(R.id.PointChart);
        chartAgesGender = (LineChart) rootView.findViewById(R.id.ChartGender);
        barChart = (BarChart) rootView.findViewById(R.id.barChart);
        chart1.setNoDataText("График загружается..");
        chart2.setNoDataText("График загружается..");
        chartPoint.setNoDataText("График загружается..");
        chartAgesGender.setNoDataText("График загружается..");
        barChart.setNoDataText("График загружается..");
        nodata=(TextView)rootView.findViewById(R.id.noGenderData);
        sredNastr=(TextView)rootView.findViewById(R.id.sredNastroi);
        caseText=(TextView)rootView.findViewById(R.id.case1);
        descbar=(TextView)rootView.findViewById(R.id.descriptionBar);
        descGender=(TextView)rootView.findViewById(R.id.descriptionGender);
        caseText2=(TextView)rootView.findViewById(R.id.case2);
        caseText3 = (TextView)rootView.findViewById(R.id.case3);
        sredNastrAll=(TextView)rootView.findViewById(R.id.sredNastroiAll);

        Spinner spinner = rootView.findViewById(R.id.buttonChange);
        String[] srokii = { "Все", "Год", "Месяц", "Неделя"};
        ArrayAdapter<String> sroki  = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,srokii);
        sroki.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(sroki);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                ((TextView)adapterView.getChildAt(0)).setTextSize(15);
                if(!id.equals("Bad")&&!id.equals("") )
                loadAllCondition(id,adapterView.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        Spinner spinnerCase = rootView.findViewById(R.id.ChangeCase);
        String[] Case = { "Работа", "Отдых", "Сон", "Учеба","Спорт","Семья","Друзья","Свидания","Игры","Чтение","Музыка","Фильмы","Покупки","Путешествия","Уборка","Телевизор","Вечеринки","Бар"};
        ArrayAdapter<String> CaseAdapter  = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,Case);
        CaseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerCase.setAdapter(CaseAdapter);
        spinnerCase.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                ((TextView)adapterView.getChildAt(0)).setTextSize(15);
                if(!id.equals("Bad")&&!id.equals("") )
                    loadAllConditionByCase(id,adapterView.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        Spinner spinnerCaseAnalysis = rootView.findViewById(R.id.ChangeCaseAnalysis);
        String[] CaseAnalysis = { "Работа", "Отдых", "Сон", "Учеба","Спорт","Семья","Друзья","Свидания","Игры","Чтение","Музыка","Фильмы","Покупки","Путешествия","Уборка","Телевизор","Вечеринки","Бар"};
        ArrayAdapter<String> CaseAdapterAnalysis  = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,CaseAnalysis);
        CaseAdapterAnalysis.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerCaseAnalysis.setAdapter(CaseAdapter);
        spinnerCaseAnalysis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                ((TextView)adapterView.getChildAt(0)).setTextSize(15);
                if(!id.equals("Bad")&&!id.equals("") )
                    loadAnalysisByCase(id,adapterView.getSelectedItemPosition(),adapterView.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        Spinner spinnerAge = rootView.findViewById(R.id.ChangeAges);
        String[] spis_Ages = {"Все", "12-16 лет", "17-21 год", "22-35 лет", "36-60 лет","61-74 года","75 лет и выше"};
        ArrayAdapter<String> adapterAges  = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,spis_Ages);
        adapterAges.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerAge.setAdapter(adapterAges);
        spinnerAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                ((TextView)adapterView.getChildAt(0)).setTextSize(15);
                if(!id.equals("Bad")&&!id.equals("") ){
                    age=adapterView.getSelectedItem().toString();
                    loadAllConditionByGenderAge(id,gender,age);
            }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        Spinner spinnerGender = rootView.findViewById(R.id.ChangeGender);
        String[] spis_Gender = { "Все","Мужчины", "Женщины" };
        ArrayAdapter<String> adapterGender  = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,spis_Gender);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerGender.setAdapter(adapterGender);
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                ((TextView) adapterView.getChildAt(0)).setTextSize(15);
                if (!id.equals("Bad") && !id.equals("")) {
                    gender = adapterView.getSelectedItem().toString();
                    loadAllConditionByGenderAge(id, gender, age);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        return  rootView;

    }

    private void loadAllConditionByGenderAge(String id, String gender,String age) {

            ArrayList<Entry> visitors1 = new ArrayList<>();

            List<String> xAxisValues = new ArrayList<>();

            Date currentDate=new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd:MM:yy", Locale.getDefault());
            String dateText = dateFormat.format(currentDate);
            String[]wordsdate = dateText.split(":");
            int daysCurrent=Integer.valueOf(wordsdate[0])+Integer.valueOf(wordsdate[1])*30+Integer.valueOf(wordsdate[2])*365;

            Drawable iconverybad = getResources().getDrawable(R.drawable.verybadmal);
            Drawable iconbad = getResources().getDrawable(R.drawable.badmal);
            Drawable iconsred = getResources().getDrawable(R.drawable.sredmal);
            Drawable icongood = getResources().getDrawable(R.drawable.goodmal);
            Drawable iconkaif = getResources().getDrawable(R.drawable.kaifmal);
            Call<List<ConditionResponseAllProfile>> accessRespone = ApiClient.conditionAllProfileServiceGet().getAllProfileConditions();
            accessRespone.enqueue(new Callback<List<ConditionResponseAllProfile>>() {
                @Override
                public void onResponse(Call<List<ConditionResponseAllProfile>> call, Response<List<ConditionResponseAllProfile>> response) {
                    if (response.isSuccessful()) {
                        //для каждой квартиры из бд создаем model1 и добавляем ее в лист
                        Log.d("Otladka",response.body().toString());
                        for(int i=0;i<response.body().size();i++) {
                            ConditionResponseAllProfile model1 = new ConditionResponseAllProfile( 1, 2,"0" ,"0",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
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
                            String[]words = model1.getDate().split("-");
                            String year=words[0].substring(2);
                            String month=words[1];
                            String[]words1=words[2].split("T");
                            String day=words1[0];
                            String[] words2=words1[1].split(":");
                            String hour=words2[0];
                            String mins   =words2[1];
                            Date date2 = new Date();
                            int days = Integer.valueOf(year)*365+Integer.valueOf(month)*30+Integer.valueOf(day);
                            if(age.equals("12-16 лет")) {

                                if (model1.getProfile().getAge() >= 12 && model1.getProfile().getAge() <= 16) {
                                    if (gender.equals("Мужчины")) {
                                        if (model1.getProfile().getSex().equals("Мужской")) {
                                            xAxisValues.add(day + "." + month + "." + year);

                                            visitors1.add(new Entry(i, model1.getAssessment()));
                                        }
                                    } else if (gender.equals("Женщины")) {
                                        if (model1.getProfile().getSex().equals("Женский")) {
                                            xAxisValues.add(day + "." + month + "." + year);

                                            visitors1.add(new Entry(i, model1.getAssessment()));
                                        }
                                    } else if (gender.equals("Все")) {

                                        xAxisValues.add(day + "." + month + "." + year);

                                        visitors1.add(new Entry(i, model1.getAssessment()));
                                    }
                                }
                            }
                            else if(age.equals("17-21 год")) {

                                if (model1.getProfile().getAge() >= 17 && model1.getProfile().getAge() <= 21) {
                                    if (gender.equals("Мужчины")) {
                                        if (model1.getProfile().getSex().equals("Мужской")) {
                                            xAxisValues.add(day + "." + month + "." + year);

                                            visitors1.add(new Entry(i, model1.getAssessment()));
                                        }
                                    } else if (gender.equals("Женщины")) {
                                        if (model1.getProfile().getSex().equals("Женский")) {
                                            xAxisValues.add(day + "." + month + "." + year);

                                            visitors1.add(new Entry(i, model1.getAssessment()));
                                        }
                                    } else if (gender.equals("Все")) {

                                        xAxisValues.add(day + "." + month + "." + year);

                                        visitors1.add(new Entry(i, model1.getAssessment()));
                                    }
                                }
                            }
                            else if(age.equals("22-35 лет")) {

                                if (model1.getProfile().getAge() >= 22 && model1.getProfile().getAge() <= 35) {
                                    if (gender.equals("Мужчины")) {
                                        if (model1.getProfile().getSex().equals("Мужской")) {
                                            xAxisValues.add(day + "." + month + "." + year);

                                            visitors1.add(new Entry(i, model1.getAssessment()));
                                        }
                                    } else if (gender.equals("Женщины")) {
                                        if (model1.getProfile().getSex().equals("Женский")) {
                                            xAxisValues.add(day + "." + month + "." + year);

                                            visitors1.add(new Entry(i, model1.getAssessment()));
                                        }
                                    } else if (gender.equals("Все")) {

                                        xAxisValues.add(day + "." + month + "." + year);

                                        visitors1.add(new Entry(i, model1.getAssessment()));
                                    }
                                }
                            }
                            else if(age.equals("36-60 лет")) {

                                if (model1.getProfile().getAge() >= 36 && model1.getProfile().getAge() <= 60) {
                                    if (gender.equals("Мужчины")) {
                                        if (model1.getProfile().getSex().equals("Мужской")) {
                                            xAxisValues.add(day + "." + month + "." + year);

                                            visitors1.add(new Entry(i, model1.getAssessment()));
                                        }
                                    } else if (gender.equals("Женщины")) {
                                        if (model1.getProfile().getSex().equals("Женский")) {
                                            xAxisValues.add(day + "." + month + "." + year);

                                            visitors1.add(new Entry(i, model1.getAssessment()));
                                        }
                                    } else if (gender.equals("Все")) {

                                        xAxisValues.add(day + "." + month + "." + year);

                                        visitors1.add(new Entry(i, model1.getAssessment()));
                                    }
                                }
                            }
                            else if(age.equals("61-74 года")) {

                                if (model1.getProfile().getAge() >= 61 && model1.getProfile().getAge() <= 74) {
                                    if (gender.equals("Мужчины")) {
                                        if (model1.getProfile().getSex().equals("Мужской")) {
                                            xAxisValues.add(day + "." + month + "." + year);

                                            visitors1.add(new Entry(i, model1.getAssessment()));
                                        }
                                    } else if (gender.equals("Женщины")) {
                                        if (model1.getProfile().getSex().equals("Женский")) {
                                            xAxisValues.add(day + "." + month + "." + year);

                                            visitors1.add(new Entry(i, model1.getAssessment()));
                                        }
                                    } else if (gender.equals("Все")) {

                                        xAxisValues.add(day + "." + month + "." + year);

                                        visitors1.add(new Entry(i, model1.getAssessment()));
                                    }
                                }
                            }

                            else if(age.equals("75 лет и выше")) {

                                if (model1.getProfile().getAge() >= 75 ) {
                                    if (gender.equals("Мужчины")) {
                                        if (model1.getProfile().getSex().equals("Мужской")) {
                                            xAxisValues.add(day + "." + month + "." + year);

                                            visitors1.add(new Entry(i, model1.getAssessment()));
                                        }
                                    } else if (gender.equals("Женщины")) {
                                        if (model1.getProfile().getSex().equals("Женский")) {
                                            xAxisValues.add(day + "." + month + "." + year);

                                            visitors1.add(new Entry(i, model1.getAssessment()));
                                        }
                                    } else if (gender.equals("Все")) {

                                        xAxisValues.add(day + "." + month + "." + year);

                                        visitors1.add(new Entry(i, model1.getAssessment()));
                                    }
                                }
                            }
                            else if(age.equals("Все")) {


                                    if (gender.equals("Мужчины")) {
                                        if (model1.getProfile().getSex().equals("Мужской")) {
                                            xAxisValues.add(day + "." + month + "." + year);

                                            visitors1.add(new Entry(i, model1.getAssessment()));
                                        }
                                    } else if (gender.equals("Женщины")) {
                                        if (model1.getProfile().getSex().equals("Женский")) {
                                            xAxisValues.add(day + "." + month + "." + year);

                                            visitors1.add(new Entry(i, model1.getAssessment()));
                                        }
                                    } else if (gender.equals("Все")) {

                                        xAxisValues.add(day + "." + month + "." + year);

                                        visitors1.add(new Entry(i, model1.getAssessment()));
                                    }
                                }

                        }
                        ArrayList<String> dates = new ArrayList<>();
                        ArrayList<Entry> srednie = new ArrayList<>();
                        ArrayList<Entry> osi = new ArrayList<>();
                        osi.add(new Entry((float)-1.99,1,iconverybad));
                        osi.add(new Entry((float)-1.99,2,iconbad));
                        osi.add(new Entry((float)-1.99,3,iconsred));
                        osi.add(new Entry((float)-1.99,4,icongood));
                        osi.add(new Entry(-2,5,iconkaif));
                        int countRazn=0;
                        for(int k=0;k<xAxisValues.size()-1;k++){
                            int summ=Math.round(visitors1.get(k).getY());
                            int count=1;
                            int lastEqual=k;
                            for(int q=k+1;q<xAxisValues.size();q++){
                                if(xAxisValues.get(k).equals(xAxisValues.get(q))){
                                    lastEqual=q;
                                    count++;
                                    summ=summ+Math.round(visitors1.get(q).getY());
                                }

                            }
                            dates.add(xAxisValues.get(k));
                            float sredAr=Float.intBitsToFloat(summ)/Float.intBitsToFloat(count);
                            if (sredAr>=4.5)
                                srednie.add(new Entry(countRazn,sredAr));
                            else if(sredAr>=3.5)
                                srednie.add(new Entry(countRazn,sredAr));
                            else if(sredAr>=2.5)
                                srednie.add(new Entry(countRazn,sredAr));
                            else if(sredAr>=1.5)
                                srednie.add(new Entry(countRazn,sredAr));
                            else
                                srednie.add(new Entry(countRazn,sredAr));
                            countRazn++;
                            k=lastEqual;

                            if(k==xAxisValues.size()-2 && lastEqual!=xAxisValues.size()-1){
                                if (visitors1.get(k+1).getY()>=4.5)
                                    srednie.add(new Entry(countRazn,Math.round(visitors1.get(k+1).getY())));
                                else if(visitors1.get(k+1).getY()>=3.5)
                                    srednie.add(new Entry(countRazn,Math.round(visitors1.get(k+1).getY())));
                                else if(visitors1.get(k+1).getY()>=2.5)
                                    srednie.add(new Entry(countRazn,Math.round(visitors1.get(k+1).getY())));
                                else if(visitors1.get(k+1).getY()>=1.5)
                                    srednie.add(new Entry(countRazn,Math.round(visitors1.get(k+1).getY())));
                                else
                                    srednie.add(new Entry(countRazn,Math.round(visitors1.get(k+1).getY())));
                                dates.add(xAxisValues.get(k+1));
                            }

                        }
                        float sred=0;
                        for(int g=0;g<srednie.size();g++){
                            sred=sred+srednie.get(g).getY();
                        }
                        sred=sred/srednie.size();
                        String formattedDouble = new DecimalFormat("#0.00").format(sred);
                        Spannable spans2 = new SpannableString("Выбранная категория людей имеет среднюю оценку настроения: "+ formattedDouble+" баллов");
                        spans2.setSpan(new StyleSpan(Typeface.BOLD),59,64,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        spans2.setSpan(new ForegroundColorSpan(Color.RED),59, 64, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        if(srednie.size()!=0)
                            sredNastrAll.setText(spans2,TextView.BufferType.SPANNABLE);
                        else
                            sredNastrAll.setText("Нет данных по выбранным критериям.");

                        Log.d("prikol",srednie.toString());
                        chartAgesGender.setDragEnabled(true);
                        chartAgesGender.setScaleEnabled(true);
                        chartAgesGender.setPinchZoom(true);
                        chartAgesGender.setScaleYEnabled(false);
                        chartAgesGender.setScaleXEnabled(true);
                        YAxis yAxis =chartAgesGender.getAxisLeft();
                        chartAgesGender.setDrawMarkers(true);

                        chartAgesGender.getXAxis().setDrawGridLinesBehindData(true);
                        chartAgesGender.getXAxis().setDrawGridLines(true);
                        chartAgesGender.getAxisLeft().setDrawGridLines(true);
                        chartAgesGender.getAxisRight().setDrawGridLines(true);
                        XAxis xAxis = chartAgesGender.getXAxis();
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        YAxis yAxisLeft = chartAgesGender.getAxisLeft();
                        yAxis.setGranularity(1f);
                        yAxis.setAxisMinimum(0.8f);
                        yAxis.setAxisMaximum(5.2f);
                        YAxis yAxisRight = chartAgesGender.getAxisRight();
                        chartAgesGender.getDescription().setText("");
                        Log.e("графики",visitors1.toString());
                        yAxis.setDrawLabels(true); // no axis labels
                        yAxis.setDrawAxisLine(true); // no axis line
                        yAxis.setDrawGridLines(true); // no grid lines
                        yAxis.setDrawZeroLine(true); // draw a zero line
                        yAxis.setLabelCount(5);
                        xAxis.setLabelCount(6);
                        xAxis.setTextSize(8f);
                        chartAgesGender.getAxisRight().setEnabled(false); // no right axis
                        //chartAgesGender.setBorderColor(Color.GREEN);
                        chartAgesGender.setBorderWidth(1);
                        chartAgesGender.setNoDataText("Повторите попытку");
                        chartAgesGender.setDrawBorders(true);
                        chartAgesGender.setDrawGridBackground(true);
                        LineDataSet lineDataSet=new LineDataSet(srednie,"Настроение");
                        LineDataSet lineDataSetOsi = new LineDataSet(osi,"");
                        LineData lineDataOsi = new LineData(lineDataSetOsi);

                        lineDataSet.setLineWidth(2);
                        lineDataSet.setCircleRadius(5);
                        lineDataSet.setDrawValues(false);
                        lineDataSet.setCircleHoleRadius(5);
                        lineDataSet.setColors(Color.RED);
                        lineDataSet.setValueTextColor(Color.BLACK);
                        lineDataSet.setValueTextSize(16f);
                        chartAgesGender.getXAxis().setValueFormatter(new IndexAxisValueFormatter(dates));
                        LineData lineData=new LineData(lineDataSet);
                        ArrayList<ILineDataSet>datasets=new ArrayList<>();
                        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                        datasets.add(lineDataSet);
                        datasets.add(lineDataSetOsi);
                        LineData newLineData=new LineData(datasets);

                        chartAgesGender.animateY(500);
                        chartAgesGender.setData(newLineData);
                        if(xAxisValues.size()==0) {
                            chartAgesGender.setVisibility(View.GONE);
                        }
                        else{
                            chartAgesGender.setVisibility(View.VISIBLE);
                        }
                    }
                }

                //запрос неуспешный
                @Override
                public void onFailure(Call<List<ConditionResponseAllProfile>> call, Throwable t) {

                }
            });
            
        }

    private void loadAnalysisByCase(String id, int caseAnalysis,String item) {


        ArrayList<Entry> visitors1 = new ArrayList<>();

        List<Integer> xAxisValues = new ArrayList<>();


        Drawable iconverybad = getResources().getDrawable(R.drawable.verybadmal);
        Drawable iconbad = getResources().getDrawable(R.drawable.badmal);
        Drawable iconsred = getResources().getDrawable(R.drawable.sredmal);
        Drawable icongood = getResources().getDrawable(R.drawable.goodmal);
        Drawable iconkaif = getResources().getDrawable(R.drawable.kaifmal);
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


                        if(item.equals("Работа"))
                        {
                            if(model1.getWork()!=0){
                            xAxisValues.add(model1.getWork());
                            visitors1.add(new Entry(model1.getWork(), model1.getAssessment()));
                        }
                        }
                        else  if(item.equals("Сон")){
                            if(model1.getSleep()!=0) {
                                xAxisValues.add(model1.getSleep());
                                visitors1.add(new Entry(i, model1.getAssessment()));
                            }
                        }
                        else  if(item.equals("Отдых")){
                            if(model1.getLeisure()!=0) {
                                xAxisValues.add(model1.getLeisure());
                                visitors1.add(new Entry(i, model1.getAssessment()));
                            }

                        }
                        else  if(item.equals("Учеба")){
                            if(model1.getStudy()!=0) {
                                xAxisValues.add(model1.getStudy());
                                visitors1.add(new Entry(i, model1.getAssessment()));
                            }

                        }
                        else  if(item.equals("Фильмы")){
                            if(model1.getMovie()!=0) {
                                xAxisValues.add(model1.getMovie());
                                visitors1.add(new Entry(i, model1.getAssessment()));
                            }

                        }
                        else  if(item.equals("Музыка")){
                            if(model1.getMusic()!=0) {
                                xAxisValues.add(model1.getMusic());
                                visitors1.add(new Entry(i, model1.getAssessment()));
                            }

                        }
                        else  if(item.equals("Друзья")){
                            if(model1.getFriend()!=0) {
                                xAxisValues.add(model1.getFriend());
                                visitors1.add(new Entry(i, model1.getAssessment()));

                            }
                        }
                        else  if(item.equals("Семья")){
                            if(model1.getFamily()!=0) {
                                xAxisValues.add(model1.getFamily());
                                visitors1.add(new Entry(i, model1.getAssessment()));
                            }

                        }
                        else  if(item.equals("Покупки")){
                            if(model1.getShopping()!=0) {
                                xAxisValues.add(model1.getShopping());
                                visitors1.add(new Entry(i, model1.getAssessment()));
                            }

                        }
                        else  if(item.equals("Уборка")){
                            if(model1.getCleaning()!=0) {
                                xAxisValues.add(model1.getCleaning());
                                visitors1.add(new Entry(i, model1.getAssessment()));
                            }

                        }
                        else  if(item.equals("Вечеринки")){
                            if(model1.getParty()!=0) {
                                xAxisValues.add(model1.getParty());
                                visitors1.add(new Entry(i, model1.getAssessment()));
                            }

                        }
                        else  if(item.equals("Свидания")){
                            if(model1.getRendezvous()!=0) {
                                xAxisValues.add(model1.getRendezvous());
                                visitors1.add(new Entry(i, model1.getAssessment()));
                            }

                        }
                        else  if(item.equals("Чтение")){
                            if(model1.getReading()!=0) {
                                xAxisValues.add(model1.getReading());
                                visitors1.add(new Entry(i, model1.getAssessment()));
                            }

                        }
                        else  if(item.equals("Путешествия")){
                            if(model1.getTravel()!=0) {
                                xAxisValues.add(model1.getTravel());
                                visitors1.add(new Entry(i, model1.getAssessment()));
                            }

                        }
                        else  if(item.equals("Игры")){
                            if(model1.getGaming()!=0) {
                                xAxisValues.add(model1.getGaming());
                                visitors1.add(new Entry(i, model1.getAssessment()));
                            }

                        }
                        else  if(item.equals("Спорт")){
                            if(model1.getWorkout()!=0) {
                                xAxisValues.add(model1.getWorkout());
                                visitors1.add(new Entry(i, model1.getAssessment()));
                            }

                        }
                        else  if(item.equals("Бар")){
                            if(model1.getBar()!=0) {
                                xAxisValues.add(model1.getBar());
                                visitors1.add(new Entry(i, model1.getAssessment()));
                            }
                        }
                        else  if(item.equals("Телевизор")){
                            if(model1.getTV()!=0) {
                                xAxisValues.add(model1.getTV());
                                visitors1.add(new Entry(i, model1.getAssessment()));
                            }

                        }

                    }
                    //Collections.sort(xAxisValues);
                    ArrayList<String> cases = new ArrayList<>();
                    ArrayList<Entry> srednie = new ArrayList<>();
                    ArrayList<Entry> osi = new ArrayList<>();
                    osi.add(new Entry((float) -3,1,iconverybad));
                    osi.add(new Entry((float) -3,2,iconbad));
                    osi.add(new Entry((float) -3,3,iconsred));
                    osi.add(new Entry((float) -3,4,icongood));
                    osi.add(new Entry((float) -3,5,iconkaif));
                    int countRazn=0;
                    int neravno = Integer.MIN_VALUE;
                    for(int t=0;t<xAxisValues.size();t++){
                        xAxisValues.set(t,xAxisValues.get(t)/60);
                        srednie.add(new Entry(xAxisValues.get(t), visitors1.get(t).getY()));
                    }


                    for(int n=0;n<srednie.size()-1;n++){
                        for(int m=n+1;m<srednie.size();m++){
                            if(srednie.get(n).getX()>srednie.get(m).getX()){
                                Entry tmp=srednie.get(n);
                                srednie.set(n,srednie.get(m));
                                srednie.set(m,tmp);
                            }
                        }
                    }


                    Log.d("prikol",srednie.toString());
                    chartPoint.setTouchEnabled(true);
                    chartPoint.setDragEnabled(true);
                    chartPoint.setScaleEnabled(true);
                    chartPoint.setPinchZoom(true);
                    chartPoint.setScaleYEnabled(false);
                    chartPoint.setScaleXEnabled(true);
                    YAxis yAxis =chartPoint.getAxisLeft();
                    chartPoint.setDrawMarkers(true);

                    chartPoint.getXAxis().setDrawGridLinesBehindData(true);
                    chartPoint.getXAxis().setDrawGridLines(true);
                    chartPoint.getAxisLeft().setDrawGridLines(true);
                    chartPoint.getAxisRight().setDrawGridLines(true);
                    XAxis xAxis = chartPoint.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    YAxis yAxisLeft = chartPoint.getAxisLeft();
                    yAxis.setGranularity(1f);
                    yAxis.setAxisMinimum(0.8f);
                    yAxis.setAxisMaximum(5.2f);
                    YAxis yAxisRight = chartPoint.getAxisRight();
                    chartPoint.getDescription().setText("");
                    Log.e("графики",visitors1.toString());
                    yAxis.setDrawLabels(true); // no axis labels
                    yAxis.setDrawAxisLine(true); // no axis line
                    yAxis.setDrawGridLines(true); // no grid lines
                    yAxis.setDrawZeroLine(true); // draw a zero line
                    yAxis.setLabelCount(5);
                    //xAxis.setLabelCount(cases.size());
                    chartPoint.getAxisRight().setEnabled(false); // no right axis
                    //chartPoint.setBorderColor(Color.GREEN);
                    chartPoint.setBorderWidth(1);
                    chartPoint.setNoDataText("Повторите попытку");
                    chartPoint.setDrawBorders(true);
                    chartPoint.setDrawGridBackground(true);
                    LineDataSet lineDataSet=new LineDataSet(srednie,item+", час");
                    LineDataSet lineDataSetOsi = new LineDataSet(osi,"");
                    LineData lineDataOsi = new LineData(lineDataSetOsi);

                    lineDataSet.setLineWidth(2);
                    lineDataSet.setCircleRadius(4);
                    lineDataSet.setCircleColor(Color.RED);
                    lineDataSet.setDrawValues(false);
                    lineDataSet.setCircleHoleRadius(5);
                    lineDataSet.setColors(Color.TRANSPARENT);
                    lineDataSet.setValueTextColor(Color.BLACK);
                    lineDataSet.setValueTextSize(16f);
                    //chartPoint.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(cases));
                    LineData lineData=new LineData(lineDataSet);
                    ArrayList<ILineDataSet>datasets=new ArrayList<>();
                    //lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                    datasets.add(lineDataSet);
                    datasets.add(lineDataSetOsi);
                    LineData newLineData=new LineData(datasets);

                    chartPoint.animateY(500);
                    chartPoint.setData(newLineData);

                }
            }

            //запрос неуспешный
            @Override
            public void onFailure(Call<List<ConditionResponse>> call, Throwable t) {

            }
        });

        String[] CaseAnalysisenglish = { "work", "leisure", "sleep", "study","workout","family","friend","rendezvous","reading","music","movie","shopping","travel","cleaning","TV","party","bar"};
        Call<AnalysisResponce> analysis = ApiClient.analysisServiceGet().getAnalysis(Integer.valueOf(id),CaseAnalysisenglish[caseAnalysis]);
        analysis.enqueue(new Callback<AnalysisResponce>() {
            @Override
            public void onResponse(Call<AnalysisResponce> call, Response<AnalysisResponce> response) {
                if (response.isSuccessful()){
                    if(response.body().znachimost.equals("Корреляция не является статистически значимой")){
                        textView.setText("Недостаточно данных для анализа");
                    }
                    else if (response.body().znachimost.equals("Корреляция является статистически значимой")) {
                        if (response.body().coef < -0.9) {
                            Spannable spans3 = new SpannableString("Связь обратная, очень сильная\nРекомендуем вам меньше заниматься активностью " + "\"" + item + "\"" + ". Это поможет вам сохранить хорошее настроение.");
                            spans3.setSpan(new StyleSpan(Typeface.BOLD),0,29,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            spans3.setSpan(new ForegroundColorSpan(Color.RED),0, 29, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            textView.setText(spans3, TextView.BufferType.SPANNABLE);
                    }
                        else if(response.body().coef<-0.7) {
                            Spannable spans4 = new SpannableString("Связь обратная, сильная\nРекомендуем вам меньше заниматься активностью " + "\"" + item + "\"" + ". Это поможет вам сохранить хорошее настроение.");
                            spans4.setSpan(new StyleSpan(Typeface.BOLD),0,23,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            spans4.setSpan(new ForegroundColorSpan(Color.RED),0, 23, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            textView.setText(spans4, TextView.BufferType.SPANNABLE);
                        }
                        else if(response.body().coef<-0.5) {
                            Spannable spans5 = new SpannableString("Связь обратная, заметная\nРекомендуем вам немного снизить активность "+"\""+item+"\""+". Это поможет вам сохранить хорошее настроение.");
                            spans5.setSpan(new StyleSpan(Typeface.BOLD),0,24,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            spans5.setSpan(new ForegroundColorSpan(Color.RED),0, 24, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            textView.setText(spans5, TextView.BufferType.SPANNABLE);
                        }
                        else if(response.body().coef<-0.3) {
                            Spannable spans6 = new SpannableString("Связь обратная, умеренная\nРекомендуем вам продолжать отслеживать свое состояние!)");
                            spans6.setSpan(new StyleSpan(Typeface.BOLD),0,25,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            spans6.setSpan(new ForegroundColorSpan(Color.RED),0, 25, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            textView.setText(spans6, TextView.BufferType.SPANNABLE);
                        }
                        else if(response.body().coef<-0.1) {
                            Spannable spans7 = new SpannableString("Связь обратная, слабая\nАктивность "+"\""+item+"\""+" не оказывает существенного влияния на ваше состояние. Рекомендуется продолжить отслеживание и обратить внимание на связь с другими занятиями.");
                            spans7.setSpan(new StyleSpan(Typeface.BOLD),0,22,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            spans7.setSpan(new ForegroundColorSpan(Color.RED),0, 22, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            textView.setText(spans7, TextView.BufferType.SPANNABLE);
                            }
                        else if(response.body().coef<0.1) {
                            Spannable spans8 = new SpannableString("Связь практически отсутствует. Следует обратить внимание на другие занятия, которые могут влиять на ваше состояние.");
                            spans8.setSpan(new StyleSpan(Typeface.BOLD), 0, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            spans8.setSpan(new ForegroundColorSpan(Color.RED), 0, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            textView.setText(spans8, TextView.BufferType.SPANNABLE);
                        }
                        else if(response.body().coef<0.3) {
                            Spannable spans9 = new SpannableString("Связь прямая, слабая\nАктивность " + "\"" + item + "\"" + " не оказывает существенного влияния на ваше состояние. Рекомендуется продолжить отслеживание и обратить внимание на связь с другими занятиями.");
                            spans9.setSpan(new StyleSpan(Typeface.BOLD), 0, 20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            spans9.setSpan(new ForegroundColorSpan(Color.RED), 0, 20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            textView.setText(spans9, TextView.BufferType.SPANNABLE);
                        }
                        else if(response.body().coef<0.5) {
                            Spannable spans10 = new SpannableString("Связь прямая, умеренная\nРекомендуем вам продолжать отслеживать свое состояние!");
                            spans10.setSpan(new StyleSpan(Typeface.BOLD), 0, 23, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            spans10.setSpan(new ForegroundColorSpan(Color.RED), 0, 23, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            textView.setText(spans10, TextView.BufferType.SPANNABLE);
                        }
                        else if(response.body().coef<0.7) {
                            Spannable spans11 = new SpannableString("Связь прямая, заметная\nРекомендуем вам немного увеличить активность "+"\""+item+"\""+". Это поможет вам сохранить хорошее настроение.");
                            spans11.setSpan(new StyleSpan(Typeface.BOLD), 0, 22, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            spans11.setSpan(new ForegroundColorSpan(Color.RED), 0, 22, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            textView.setText(spans11, TextView.BufferType.SPANNABLE);
                        }
                        else if(response.body().coef<0.9) {
                            Spannable spans12 = new SpannableString("Связь прямая, сильная\nРекомендуем вам больше заниматься активностью "+"\""+item+"\""+". Это поможет вам сохранить хорошее настроение.");
                            spans12.setSpan(new StyleSpan(Typeface.BOLD), 0, 21, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            spans12.setSpan(new ForegroundColorSpan(Color.RED), 0, 21, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            textView.setText(spans12, TextView.BufferType.SPANNABLE);
                        }
                        else {
                            Spannable spans13 = new SpannableString("Связь прямая, очень сильная\nРекомендуем вам больше заниматься активностью "+"\""+item+"\""+". Это поможет вам сохранить хорошее настроение.");
                            spans13.setSpan(new StyleSpan(Typeface.BOLD), 0, 27, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            spans13.setSpan(new ForegroundColorSpan(Color.RED), 0, 27, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            textView.setText(spans13, TextView.BufferType.SPANNABLE);
                        }
                    }
                }
                else{
                    textView.setText("Недостаточно данных для анализа");
                }
                textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            }

            @Override
            public void onFailure(Call<AnalysisResponce> call, Throwable t) {
                textView.setText("Недостаточно данных для анализа");
            }
        });
    }

    private void loadAllCondition(String id,String srok) {
        ArrayList<Entry> visitors1 = new ArrayList<>();

        List<String> xAxisValues = new ArrayList<>();

        Date currentDate=new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd:MM:yy", Locale.getDefault());
        String dateText = dateFormat.format(currentDate);
        String[]wordsdate = dateText.split(":");
        int daysCurrent=Integer.valueOf(wordsdate[0])+Integer.valueOf(wordsdate[1])*30+Integer.valueOf(wordsdate[2])*365;

        Drawable iconverybad = getResources().getDrawable(R.drawable.ic_baseline_circle_24);
        Drawable iconbad = getResources().getDrawable(R.drawable.circle_bad);
        Drawable iconsred = getResources().getDrawable(R.drawable.circle_sred);
        Drawable icongood = getResources().getDrawable(R.drawable.circle_good);
        Drawable iconkaif = getResources().getDrawable(R.drawable.circlekaif);
        Drawable iconverybadosi = getResources().getDrawable(R.drawable.verybadmal);
        Drawable iconbadosi = getResources().getDrawable(R.drawable.badmal);
        Drawable iconsredosi = getResources().getDrawable(R.drawable.sredmal);
        Drawable icongoodosi = getResources().getDrawable(R.drawable.goodmal);
        Drawable iconkaifosi = getResources().getDrawable(R.drawable.kaifmal);

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
                        String[]words = model1.getDate().split("-");
                        String year=words[0].substring(2);
                        String month=words[1];
                        String[]words1=words[2].split("T");
                        String day=words1[0];
                        String[] words2=words1[1].split(":");
                        String hour=words2[0];
                        String mins   =words2[1];
                        Date date2 = new Date();
                        int days = Integer.valueOf(year)*365+Integer.valueOf(month)*30+Integer.valueOf(day);

                        Log.d("Sravnenie",String.valueOf(days)+" and "+ daysCurrent);
                        if(srok.equals("Все"))
                        {

                        xAxisValues.add(day+"."+month+"."+year);

                        visitors1.add(new Entry(i,model1.getAssessment()));
                        }
                        else  if(srok.equals("Год")){
                            if(daysCurrent-days<=365){
                                xAxisValues.add(day+"."+month+"."+year);

                                visitors1.add(new Entry(i,model1.getAssessment()));
                            }
                        }
                        else  if(srok.equals("Месяц")){
                            if(daysCurrent-days<=30){
                                xAxisValues.add(day+"."+month+"."+year);

                                visitors1.add(new Entry(i,model1.getAssessment()));
                            }
                        }
                        else  if(srok.equals("Неделя")){
                            if(daysCurrent-days<=7){
                                xAxisValues.add(day+"."+month+"."+year);

                                visitors1.add(new Entry(i,model1.getAssessment()));
                            }
                        }

                    }

                    ArrayList<String> dates = new ArrayList<>();
                    ArrayList<Entry> srednie = new ArrayList<>();
                    ArrayList<Entry> osi = new ArrayList<>();
                    osi.add(new Entry(-3,1,iconverybadosi));
                    osi.add(new Entry(-3,2,iconbadosi));
                    osi.add(new Entry(-3,3,iconsredosi));
                    osi.add(new Entry(-3,4,icongoodosi));
                    osi.add(new Entry(-3,5,iconkaifosi));
                    int countRazn=0;
                    if(xAxisValues.size()!=0)
                    for(int k=0;k<xAxisValues.size()-1;k++){
                        int summ=Math.round(visitors1.get(k).getY());
                        int count=1;
                        int lastEqual=k;
                        for(int q=k+1;q<xAxisValues.size();q++){
                        if(xAxisValues.get(k).equals(xAxisValues.get(q))){
                            lastEqual=q;
                            count++;
                            summ=summ+Math.round(visitors1.get(q).getY());
                        }

                        }
                        dates.add(xAxisValues.get(k));
                        float sredAr=Float.intBitsToFloat(summ)/Float.intBitsToFloat(count);
                        if (sredAr>=4.5)
                            srednie.add(new Entry(countRazn,sredAr,iconkaif));
                        else if(sredAr>=3.5)
                            srednie.add(new Entry(countRazn,sredAr,icongood));
                        else if(sredAr>=2.5)
                            srednie.add(new Entry(countRazn,sredAr,iconsred));
                        else if(sredAr>=1.5)
                            srednie.add(new Entry(countRazn,sredAr,iconbad));
                        else
                            srednie.add(new Entry(countRazn,sredAr,iconverybad));
                        countRazn++;
                        k=lastEqual;


                        if(k==xAxisValues.size()-2 && lastEqual!=xAxisValues.size()-1){
                            if (visitors1.get(k+1).getY()>=4.5)
                                srednie.add(new Entry(countRazn,Math.round(visitors1.get(k+1).getY()),iconkaif));
                            else if(visitors1.get(k+1).getY()>=3.5)
                                srednie.add(new Entry(countRazn,Math.round(visitors1.get(k+1).getY()),icongood));
                            else if(visitors1.get(k+1).getY()>=2.5)
                                srednie.add(new Entry(countRazn,Math.round(visitors1.get(k+1).getY()),iconsred));
                            else if(visitors1.get(k+1).getY()>=1.5)
                                srednie.add(new Entry(countRazn,Math.round(visitors1.get(k+1).getY()),iconbad));
                            else
                                srednie.add(new Entry(countRazn,Math.round(visitors1.get(k+1).getY()),iconverybad));
                            dates.add(xAxisValues.get(k+1));
                        }

                    }
                    if(xAxisValues.size()==1){
                            if (visitors1.get(0).getY()>=4.5)
                                srednie.add(new Entry(countRazn,Math.round(visitors1.get(0).getY()),iconkaif));
                            else if(visitors1.get(0).getY()>=3.5)
                                srednie.add(new Entry(countRazn,Math.round(visitors1.get(0).getY()),icongood));
                            else if(visitors1.get(0).getY()>=2.5)
                                srednie.add(new Entry(countRazn,Math.round(visitors1.get(0).getY()),iconsred));
                            else if(visitors1.get(0).getY()>=1.5)
                                srednie.add(new Entry(countRazn,Math.round(visitors1.get(0).getY()),iconbad));
                            else
                                srednie.add(new Entry(countRazn,Math.round(visitors1.get(0).getY()),iconverybad));
                            dates.add(xAxisValues.get(0));
                        }

                    float sredArif=0;
                    int count=0;
                    for(int p=0;p<srednie.size();p++) {
                        sredArif = sredArif + srednie.get(p).getY();
                        count++;
                    }
                    if(count!=0){
                        String formattedDouble = new DecimalFormat("#0.00").format(sredArif/count);
                        Spannable spans3 = new SpannableString("Средняя оценка вашего настроения за все время: "+ formattedDouble+" балла");
                        spans3.setSpan(new StyleSpan(Typeface.BOLD),47,51,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        spans3.setSpan(new ForegroundColorSpan(Color.RED),47, 51, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        if(srok.equals("Все"))
                            sredNastr.setText(spans3,TextView.BufferType.SPANNABLE);
                        else
                        sredNastr.setText("Средняя оценка вашего настроения за период времени '"+srok+"': "+ formattedDouble+" балла");
                    }
                    else
                        sredNastr.setText("У вас еще нечего анализировать");

                    Log.d("prikol",srednie.toString());
                    chart1.setTouchEnabled(true);
                    chart1.setDragEnabled(true);
                    chart1.setScaleEnabled(true);
                    chart1.setPinchZoom(true);
                    chart1.setScaleYEnabled(false);
                    chart1.setScaleXEnabled(true);
                    YAxis yAxis =chart1.getAxisLeft();
                    chart1.setDrawMarkers(true);

                    chart1.getXAxis().setDrawGridLinesBehindData(true);
                    chart1.getXAxis().setDrawGridLines(true);
                    chart1.getAxisLeft().setDrawGridLines(true);
                    chart1.getAxisRight().setDrawGridLines(true);
                    XAxis xAxis = chart1.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    YAxis yAxisLeft = chart1.getAxisLeft();
                    yAxis.setGranularity(1f);
                    yAxis.setAxisMinimum(0.8f);
                    yAxis.setAxisMaximum(5.2f);
                    YAxis yAxisRight = chart1.getAxisRight();
                    chart1.getDescription().setText("");
                    Log.e("графики",visitors1.toString());
                    yAxis.setDrawLabels(true); // no axis labels
                    yAxis.setDrawAxisLine(true); // no axis line
                    yAxis.setDrawGridLines(true); // no grid lines
                    yAxis.setDrawZeroLine(true); // draw a zero line
                    yAxis.setLabelCount(5);
                    xAxis.setLabelCount(6);
                    chart1.getAxisRight().setEnabled(false); // no right axis
                    //chart1.setBorderColor(Color.GREEN);
                    chart1.setBorderWidth(1);
                    xAxis.setTextSize(8f);
                    chart1.invalidate();
                    chart1.setDrawBorders(true);
                    chart1.setDrawGridBackground(true);
                    LineDataSet lineDataSet = null;
                    if(srednie.size()!=0)
                        lineDataSet=new LineDataSet(srednie,"Настроение");
                    LineDataSet lineDataSetOsi = new LineDataSet(osi,"");
                    LineData lineDataOsi = new LineData(lineDataSetOsi);
                    if(xAxisValues.size()!=0){

                    lineDataSet.setLineWidth(2);
                    lineDataSet.setCircleRadius(5);
                    lineDataSet.setDrawValues(false);
                    lineDataSet.setCircleHoleRadius(5);
                    lineDataSet.setColors(Color.RED);
                    lineDataSet.setValueTextColor(Color.BLACK);
                    lineDataSet.setValueTextSize(16f);


                    chart1.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(dates));


                    lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                    }
                    ArrayList<ILineDataSet>datasets=new ArrayList<>();
                    if(xAxisValues.size()!=0)
                    datasets.add(lineDataSet);
                    datasets.add(lineDataSetOsi);
                    LineData newLineData=new LineData(datasets);

                    chart1.animateY(500);
                    chart1.setData(newLineData);

                }
            }

            //запрос неуспешный
            @Override
            public void onFailure(Call<List<ConditionResponse>> call, Throwable t) {
                Toast.makeText(getContext(), "Проблемы с соединением!", Toast.LENGTH_SHORT).show();
            }
        });


    }



    private void loadAllConditionByCase(String id,String Case) {
        ArrayList<Entry> visitors1 = new ArrayList<>();

        List<Integer> xAxisValues = new ArrayList<>();

        Drawable iconverybad = getResources().getDrawable(R.drawable.verybadmal);
        Drawable iconbad = getResources().getDrawable(R.drawable.badmal);
        Drawable iconsred = getResources().getDrawable(R.drawable.sredmal);
        Drawable icongood = getResources().getDrawable(R.drawable.goodmal);
        Drawable iconkaif = getResources().getDrawable(R.drawable.kaifmal);

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


                        if(Case.equals("Работа"))
                        {
                            xAxisValues.add(model1.getWork());
                            visitors1.add(new Entry(model1.getWork(),model1.getAssessment()));
                            iconCase = getResources().getDrawable(R.drawable.ic_work3);
                        }
                        else  if(Case.equals("Сон")){
                                xAxisValues.add(model1.getSleep());
                            visitors1.add(new Entry(i,model1.getAssessment()));
                            iconCase = getResources().getDrawable(R.drawable.ic_sleep);
                        }
                        else  if(Case.equals("Отдых")){

                                xAxisValues.add(model1.getLeisure());
                            visitors1.add(new Entry(i,model1.getAssessment()));
                            iconCase = getResources().getDrawable(R.drawable.ic_resting);

                        }
                        else  if(Case.equals("Учеба")){

                                xAxisValues.add(model1.getStudy());
                            visitors1.add(new Entry(i,model1.getAssessment()));
                            iconCase = getResources().getDrawable(R.drawable.ic_study);

                        }
                        else  if(Case.equals("Фильмы")){

                            xAxisValues.add(model1.getMovie());
                            visitors1.add(new Entry(i,model1.getAssessment()));
                            iconCase = getResources().getDrawable(R.drawable.ic_movie);

                        }
                        else  if(Case.equals("Музыка")){

                            xAxisValues.add(model1.getMusic());
                            visitors1.add(new Entry(i,model1.getAssessment()));
                            iconCase = getResources().getDrawable(R.drawable.ic_music);

                        }
                        else  if(Case.equals("Друзья")){

                            xAxisValues.add(model1.getFriend());
                            visitors1.add(new Entry(i,model1.getAssessment()));
                            iconCase = getResources().getDrawable(R.drawable.ic_friends);

                        }
                        else  if(Case.equals("Семья")){

                            xAxisValues.add(model1.getFamily());
                            visitors1.add(new Entry(i,model1.getAssessment()));
                            iconCase = getResources().getDrawable(R.drawable.ic_family);

                        }
                        else  if(Case.equals("Покупки")){

                            xAxisValues.add(model1.getShopping());
                            visitors1.add(new Entry(i,model1.getAssessment()));
                            iconCase = getResources().getDrawable(R.drawable.ic_shopping);

                        }
                        else  if(Case.equals("Уборка")){

                            xAxisValues.add(model1.getCleaning());
                            visitors1.add(new Entry(i,model1.getAssessment()));
                            iconCase = getResources().getDrawable(R.drawable.ic_cleaning);

                        }
                        else  if(Case.equals("Вечеринки")){

                            xAxisValues.add(model1.getParty());
                            visitors1.add(new Entry(i,model1.getAssessment()));
                            iconCase = getResources().getDrawable(R.drawable.ic_party1);

                        }
                        else  if(Case.equals("Свидания")){

                            xAxisValues.add(model1.getRendezvous());
                            visitors1.add(new Entry(i,model1.getAssessment()));
                            iconCase = getResources().getDrawable(R.drawable.ic_love);

                        }
                        else  if(Case.equals("Чтение")){

                            xAxisValues.add(model1.getReading());
                            visitors1.add(new Entry(i,model1.getAssessment()));
                            iconCase = getResources().getDrawable(R.drawable.ic_book1);
                        }
                        else  if(Case.equals("Путешествия")){

                            xAxisValues.add(model1.getTravel());
                            visitors1.add(new Entry(i,model1.getAssessment()));
                            iconCase = getResources().getDrawable(R.drawable.ic_travel);

                        }
                        else  if(Case.equals("Игры")){

                            xAxisValues.add(model1.getGaming());
                            visitors1.add(new Entry(i,model1.getAssessment()));
                            iconCase = getResources().getDrawable(R.drawable.ic_game);

                        }
                        else  if(Case.equals("Спорт")){

                            xAxisValues.add(model1.getWorkout());
                            visitors1.add(new Entry(i,model1.getAssessment()));
                            iconCase = getResources().getDrawable(R.drawable.ic_workout);

                        }
                        else  if(Case.equals("Бар")){

                            xAxisValues.add(model1.getBar());
                            visitors1.add(new Entry(i,model1.getAssessment()));
                            iconCase = getResources().getDrawable(R.drawable.ic_bar);
                        }
                        else  if(Case.equals("Телевизор")){

                            xAxisValues.add(model1.getTV());
                            visitors1.add(new Entry(i,model1.getAssessment()));
                            iconCase = getResources().getDrawable(R.drawable.ic_tv);

                        }

                    }
                    //Collections.sort(xAxisValues);


                    ArrayList<String> cases = new ArrayList<>();
                    ArrayList<Entry> srednie = new ArrayList<>();
                    ArrayList<Entry> osi = new ArrayList<>();
                    osi.add(new Entry((float) -3,1,iconverybad));
                    osi.add(new Entry((float) -3,2,iconbad));
                    osi.add(new Entry((float) -3,3,iconsred));
                    osi.add(new Entry((float) -3,4,icongood));
                    osi.add(new Entry((float) -3,5,iconkaif));
                    int countRazn=0;
                    int neravno = Integer.MIN_VALUE;
                    for(int t=0;t<xAxisValues.size();t++){
                        xAxisValues.set(t,xAxisValues.get(t)/60);
                    }
                    if(xAxisValues.size()!=0)
                    for(int k=0;k<xAxisValues.size()-1;k++) {
                        if (xAxisValues.get(k) != neravno){
                            int summ = Math.round(visitors1.get(k).getY());
                        int count = 1;

                        for (int q = k + 1; q < xAxisValues.size(); q++) {
                            if (xAxisValues.get(k).equals(xAxisValues.get(q))) {

                                count++;
                                summ = summ + Math.round(visitors1.get(q).getY());
                                xAxisValues.set(q, neravno);
                            }

                        }
                        cases.add(xAxisValues.get(k).toString());
                        float sredAr = Float.intBitsToFloat(summ) / Float.intBitsToFloat(count);
                        if (sredAr >= 4.5)
                            srednie.add(new Entry(xAxisValues.get(k), sredAr, iconCase));
                        else if (sredAr >= 3.5)
                            srednie.add(new Entry(xAxisValues.get(k), sredAr, iconCase));
                        else if (sredAr >= 2.5)
                            srednie.add(new Entry(xAxisValues.get(k), sredAr, iconCase));
                        else if (sredAr >= 1.5)
                            srednie.add(new Entry(xAxisValues.get(k), sredAr, iconCase));
                        else
                            srednie.add(new Entry(xAxisValues.get(k), sredAr, iconCase));
                        countRazn++;

                    }

                    }
                    if(xAxisValues.size()!=0)
                    if (xAxisValues.get(xAxisValues.size()-1) != neravno) {
                        if (visitors1.get(xAxisValues.size()-1).getY() >= 4.5)
                            srednie.add(new Entry(xAxisValues.get(xAxisValues.size()-1), Math.round(visitors1.get(xAxisValues.size()-1).getY()), iconCase));
                        else if (visitors1.get(xAxisValues.size()-1).getY() >= 3.5)
                            srednie.add(new Entry(xAxisValues.get(xAxisValues.size()-1), Math.round(visitors1.get(xAxisValues.size()-1).getY()), iconCase));
                        else if (visitors1.get(xAxisValues.size()-1).getY() >= 2.5)
                            srednie.add(new Entry(xAxisValues.get(xAxisValues.size()-1), Math.round(visitors1.get(xAxisValues.size()-1).getY()), iconCase));
                        else if (visitors1.get(xAxisValues.size()-1).getY() >= 1.5)
                            srednie.add(new Entry(xAxisValues.get(xAxisValues.size()-1), Math.round(visitors1.get(xAxisValues.size()-1).getY()), iconCase));
                        else
                            srednie.add(new Entry(xAxisValues.get(xAxisValues.size()-1), Math.round(visitors1.get(xAxisValues.size()-1).getY()), iconCase));
                        cases.add(xAxisValues.get(xAxisValues.size()-1).toString());
                    }
                    if(xAxisValues.size()!=0)
                    for(int n=0;n<srednie.size()-1;n++){
                        for(int m=n+1;m<srednie.size();m++){
                            if(srednie.get(n).getX()>srednie.get(m).getX()){
                                Entry tmp=srednie.get(n);
                                srednie.set(n,srednie.get(m));
                                srednie.set(m,tmp);
                            }
                        }
                    }
                    float sred=0;
                    float max = 0;
                    for(int g=0;g<srednie.size();g++){
                        sred=sred+srednie.get(g).getX();
                        if(max<srednie.get(g).getX()){
                            max = srednie.get(g).getX();
                        }
                    }
                    if (srednie.size()!=0)
                    sred=sred/srednie.size();
                    String formattedMax = new DecimalFormat("#0.00").format(max);
                    String formattedDouble = new DecimalFormat("#0.00").format(sred);
                    Spannable spans1 = new SpannableString("В среднем вы занимаетесь активностью "+formattedDouble+" часов в день");
                    spans1.setSpan(new StyleSpan(Typeface.BOLD),37,42,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spans1.setSpan(new ForegroundColorSpan(Color.RED),37, 42, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    caseText.setText("Зависимость вашего настроения от длительности активности '"+Case+"'");
                    caseText.setTextColor(getResources().getColor(R.color.colorPrimary2));
                    caseText.setTypeface(caseText.getTypeface(), Typeface.BOLD_ITALIC);
                    caseText.setTextSize(16);
                    caseText2.setText(spans1, TextView.BufferType.SPANNABLE);
                    Spannable spans10 = new SpannableString("Максимальное количество часов в день: "+formattedMax);
                    spans10.setSpan(new StyleSpan(Typeface.BOLD),38,38+formattedMax.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spans10.setSpan(new ForegroundColorSpan(Color.RED),38, 38+formattedMax.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    caseText3.setText(spans10,TextView.BufferType.SPANNABLE);

                    Log.d("prikol",srednie.toString());
                    chart2.setTouchEnabled(true);
                    chart2.setDragEnabled(true);
                    chart2.setScaleEnabled(true);
                    chart2.setPinchZoom(true);
                    chart2.setScaleYEnabled(false);
                    chart2.setScaleXEnabled(true);
                    YAxis yAxis =chart2.getAxisLeft();
                    chart2.setDrawMarkers(true);

                    chart2.getXAxis().setDrawGridLinesBehindData(true);
                    chart2.getXAxis().setDrawGridLines(true);
                    chart2.getAxisLeft().setDrawGridLines(true);
                    chart2.getAxisRight().setDrawGridLines(true);
                    XAxis xAxis = chart2.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    YAxis yAxisLeft = chart2.getAxisLeft();
                    yAxis.setGranularity(1f);
                    yAxis.setAxisMinimum(0.8f);
                    yAxis.setAxisMaximum(5.2f);
                    YAxis yAxisRight = chart2.getAxisRight();
                    chart2.getDescription().setText("");
                    Log.e("графики",visitors1.toString());
                    yAxis.setDrawLabels(true); // no axis labels
                    yAxis.setDrawAxisLine(true); // no axis line
                    yAxis.setDrawGridLines(true); // no grid lines
                    yAxis.setDrawZeroLine(true); // draw a zero line
                    yAxis.setLabelCount(5);
                    //xAxis.setLabelCount(cases.size());
                    chart2.getAxisRight().setEnabled(false); // no right axis
                    //chart2.setBorderColor(Color.GREEN);
                    chart2.setBorderWidth(1);
                    chart2.setNoDataText("Повторите попытку");
                    chart2.setDrawBorders(true);
                    chart2.setDrawGridBackground(true);
                    LineDataSet lineDataSet=new LineDataSet(srednie,Case);
                    LineDataSet lineDataSetOsi = new LineDataSet(osi,"");
                    LineData lineDataOsi = new LineData(lineDataSetOsi);

                    lineDataSet.setDrawCircles(false);
                    lineDataSet.setLineWidth((float)1.5);
                    lineDataSet.setCircleRadius(0);
                    lineDataSet.setDrawValues(false);
                    lineDataSet.setCircleHoleRadius(0);
                    lineDataSet.setColors(Color.RED);
                    lineDataSet.setValueTextColor(Color.BLACK);
                    lineDataSet.setValueTextSize(16f);
                    //chart2.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(cases));
                    LineData lineData=new LineData(lineDataSet);
                    ArrayList<ILineDataSet>datasets=new ArrayList<>();
                    //lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                    datasets.add(lineDataSet);
                    datasets.add(lineDataSetOsi);
                    LineData newLineData=new LineData(datasets);

                    chart2.animateY(500);
                    chart2.setData(newLineData);

                }
            }

            //запрос неуспешный
            @Override
            public void onFailure(Call<List<ConditionResponse>> call, Throwable t) {

            }
        });

    }

    private void loadAllConditionBarChart(String id) {
        ArrayList<Entry> visitors1 = new ArrayList<>();

        List<String> xAxisValues = new ArrayList<>();

        Date currentDate=new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd:MM:yy", Locale.getDefault());
        String dateText = dateFormat.format(currentDate);
        String[]wordsdate = dateText.split(":");
        int daysCurrent=Integer.valueOf(wordsdate[0])+Integer.valueOf(wordsdate[1])*30+Integer.valueOf(wordsdate[2])*365;

        Drawable iconverybad = getResources().getDrawable(R.drawable.verybadmal);
        Drawable iconbad = getResources().getDrawable(R.drawable.badmal);
        Drawable iconsred = getResources().getDrawable(R.drawable.sredmal);
        Drawable icongood = getResources().getDrawable(R.drawable.goodmal);
        Drawable iconkaif = getResources().getDrawable(R.drawable.kaifmal);
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

                        visitors1.add(new Entry(i,model1.getAssessment()));

                    }
                    Integer count1=0;
                    Integer count2=0;
                    Integer count3=0;
                    Integer count4=0;
                    Integer count5=0;
                    for(int k=0;k<visitors1.size();k++){
                        if(visitors1.get(k).getY()==1)
                            count1++;
                        else if(visitors1.get(k).getY()==2)
                            count2++;
                        else if(visitors1.get(k).getY()==3)
                            count3++;
                        else if(visitors1.get(k).getY()==4)
                            count4++;
                        else if(visitors1.get(k).getY()==5)
                            count5++;
                    }
                    DecimalFormat formattedDouble = new DecimalFormat("#0.00");
                    String persent5="";
                    String persent4="";
                    String persent3="";
                    String persent2="";
                    String persent1="";
                    Float pers5;
                    Float pers4;
                    Float pers3;
                    Float pers2;
                    Float pers1;
                    if(count5!=0) {
                        pers5 = (float)count5/(float)visitors1.size()*100;
                        persent5=formattedDouble.format(pers5);
                    }
                    else{
                        persent5="0,00";
                    }
                    if(count4!=0) {
                        pers4 = (float)count4/(float)visitors1.size()*100;
                        persent4=formattedDouble.format(pers4);
                    }
                    else{
                        persent4="0,00";
                    }
                    if(count3!=0) {
                        pers3 = (float)count3/(float)visitors1.size()*100;
                        persent3=formattedDouble.format(pers3);
                    }
                    else{
                        persent3="0,00";
                    }
                    if(count2!=0) {
                        pers2 = (float)count2/(float)visitors1.size()*100;
                        persent2=formattedDouble.format(pers2);
                    }
                    else{
                        persent2="0,00";
                    }
                    if(count1!=0) {
                        pers1 = (float)count1/(float)visitors1.size()*100;
                        persent1=formattedDouble.format(pers1);
                    }
                    else{
                        persent1="0,00";
                    }
                    Spannable spans = new SpannableString("Счетчик настроения\nКоличество настроений всего: "+String.valueOf(visitors1.size())+"\nОтличное: "+String.valueOf(count5)+" раз - "+ persent5+"% от общего количества\nХорошее: "+String.valueOf(count4)+" раз - "+ persent4+"% от общего количества\nСреднее: "+String.valueOf(count3)+" раз - "+ persent3+"% от общего количества\nПлохое: "+String.valueOf(count2)+" раз - "+ persent2+"% от общего количества\nУжасное: "+String.valueOf(count1)+" раз - "+ persent1+"% от общего количества");
                    spans.setSpan(new AbsoluteSizeSpan(16,true), 0,19, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spans.setSpan(new AbsoluteSizeSpan(14,true), 19,50, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spans.setSpan(new StyleSpan(Typeface.BOLD),48,50,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spans.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary2)),0,19,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spans.setSpan(new StyleSpan(Typeface.BOLD_ITALIC),0,19,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spans.setSpan(new ForegroundColorSpan(Color.RED),48, 50, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spans.setSpan(new StyleSpan(Typeface.BOLD),61,67,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spans.setSpan(new ForegroundColorSpan(Color.RED),61, 67, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spans.setSpan(new StyleSpan(Typeface.BOLD),107,113,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spans.setSpan(new ForegroundColorSpan(Color.RED),107, 113, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spans.setSpan(new StyleSpan(Typeface.BOLD),153,159,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spans.setSpan(new ForegroundColorSpan(Color.RED),153, 159, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spans.setSpan(new StyleSpan(Typeface.BOLD),198,204,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spans.setSpan(new ForegroundColorSpan(Color.RED),198, 204, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spans.setSpan(new StyleSpan(Typeface.BOLD),243,249,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spans.setSpan(new ForegroundColorSpan(Color.RED),243, 249, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    //descbar.setText("Счетчик настроения\nКоличество настроений всего: "+String.valueOf(visitors1.size())+"\nОтличное: "+String.valueOf(count5)+" раз - "+ persent5+"% от общего количества\nХорошее: "+String.valueOf(count4)+" раз - "+ persent4+"% от общего количества\nСреднее: "+String.valueOf(count3)+" раз - "+ persent3+"% от общего количества\nПлохое: "+String.valueOf(count2)+" раз - "+ persent2+"% от общего количества\nУжасное: "+String.valueOf(count1)+" раз - "+ persent1+"% от общего количества");
                    descbar.setText(spans, TextView.BufferType.SPANNABLE);
                    ArrayList<BarEntry> bars = new ArrayList<>();
                    bars.add(new BarEntry(1,count1,iconverybad));
                    bars.add(new BarEntry(2,count2,iconbad));
                    bars.add(new BarEntry(3,count3,iconsred));
                    bars.add(new BarEntry(4,count4,icongood));
                    bars.add(new BarEntry(5,count5,iconkaif));
                    BarDataSet barDataSet = new BarDataSet(bars, "Настроения");
                    int[] MATERIAL_COLORS1 = {
                            rgb("7a47d5"),rgb("#2ecc71"), rgb("#f1c40f"), rgb("#e74c3c"), rgb("#3498db"),
                    };

                    barDataSet.setColors(MATERIAL_COLORS1);
                    barDataSet.setValueTextColor(Color.BLACK);
                    BarData barData = new BarData(barDataSet);
                    barChart.getDescription().setText("");
                    barChart.setData(barData);
                    barChart.animateX(2000);

                }
            }

            //запрос неуспешный
            @Override
            public void onFailure(Call<List<ConditionResponse>> call, Throwable t) {

            }
        });


    }

    public int rgb(String hex) {
        int color = (int) Long.parseLong(hex.replace("#", ""), 16);
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = (color >> 0) & 0xFF;
        return Color.rgb(r, g, b);
    }


    private Drawable resize(Drawable image) {
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 30, 30, false);
        return new BitmapDrawable(getResources(), bitmapResized);
    }


    private class MyAxisValueFormatter extends ValueFormatter implements  IAxisValueFormatter{
        @Override
        public  String getFormattedValue(float value,AxisBase axis){
            return "day"+value;
        }
    }

    public  void showPopup(View v,int position){
        PopupMenu popupMenu=new PopupMenu(getActivity(),v);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.allTime:
                      loadAllCondition(id,"Все");

                        return true;
                    case R.id.year:
                        loadAllCondition(id,"Год");
                        return true;
                    case R.id.month:
                        loadAllCondition(id,"Месяц");
                        return true;
                    case R.id.week:
                        loadAllCondition(id,"Неделя");
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.inflate(R.menu.spisok_date);
        popupMenu.show();
    }
}