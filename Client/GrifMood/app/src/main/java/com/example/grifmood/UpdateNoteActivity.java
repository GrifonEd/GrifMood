package com.example.grifmood;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grifmood.databinding.ActivityUpdateNoteBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateNoteActivity extends AppCompatActivity {
    private ActivityUpdateNoteBinding binding;
    private String id="";
    String access="";
    Integer idCondition=0;
    private SpeechRecognizer sr;
    ConditionResponse model1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        idCondition=intent.getIntExtra("idCondition",0);
        access=intent.getStringExtra("access");

        binding.logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });

        Call<ConditionResponse> condition = ApiClient.getConditionServiceOne().getCondition(Integer.valueOf(idCondition));
        condition.enqueue(new Callback<ConditionResponse>() {
            @Override
            public void onResponse(Call<ConditionResponse> call, Response<ConditionResponse>response) {
                if (response.isSuccessful()) {
                    //для каждой квартиры из бд создаем model1 и добавляем ее в лист
                    Log.d("Otladka",response.body().toString());
                        model1 = new ConditionResponse( 1, 2,"0" ,"0",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
                        model1.setAssessment(response.body().getAssessment());
                        model1.setId(response.body().getId());
                        model1.setDescription(response.body().getDescription());
                        model1.setBar(response.body().getBar());
                        model1.setCleaning(response.body().getCleaning());
                        model1.setGaming(response.body().getGaming());
                        model1.setDate(response.body().getDate());
                        model1.setFamily(response.body().getFamily());
                        model1.setWork(response.body().getWork());
                        model1.setWorkout(response.body().getWorkout());
                        model1.setLeisure(response.body().getLeisure());
                        model1.setReading(response.body().getReading());
                        model1.setRendezvous(response.body().getRendezvous());
                        model1.setTravel(response.body().getTravel());
                        model1.setTV(response.body().getTV());
                        model1.setParty(response.body().getParty());
                        model1.setShopping(response.body().getShopping());
                        model1.setSleep(response.body().getSleep());
                        model1.setStudy(response.body().getStudy());
                        model1.setFriend(response.body().getFriend());
                        model1.setMovie(response.body().getMovie());
                        model1.setMusic(response.body().getMusic());
                        model1.setProfile(response.body().getProfile());


                    binding.seekBar.setProgress(model1.getAssessment());
                    binding.workSeek.setProgress(model1.getWork());
                    binding.LeisureSeek.setProgress(model1.getLeisure());
                    binding.SleepSeek.setProgress(model1.getSleep());
                    binding.WorkoutSeek.setProgress(model1.getWorkout());
                    binding.StudySeek.setProgress(model1.getStudy());
                    binding.FriendSeek.setProgress(model1.getFriend());
                    binding.FamilySeek.setProgress(model1.getFamily());
                    binding.RendezvousSeek.setProgress(model1.getRendezvous());
                    binding.TVSeek.setProgress(model1.getTV());
                    binding.MusicSeek.setProgress(model1.getMusic());
                    binding.MovieSeek.setProgress(model1.getMovie());
                    binding.GamingSeek.setProgress(model1.getGaming());
                    binding.CleaningSeek.setProgress(model1.getCleaning());
                    binding.PartySeek.setProgress(model1.getParty());
                    binding.ReadingSeek.setProgress(model1.getReading());
                    binding.ShoppingSeek.setProgress(model1.getShopping());
                    binding.TravelSeek.setProgress(model1.getTravel());
                    binding.BarSeek.setProgress(model1.getBar());
                    binding.descriptionText.setText(model1.getDescription());


                    String[]words = model1.getDate().split("-");
                    String year=words[0];
                    String month=words[1];
                    String[]words1=words[2].split("T");
                    String day=words1[0];
                    String[] words2=words1[1].split(":");
                    String hour=words2[0];
                    String mins   =words2[1];
                    Log.d("Privet",year+","+month+","+day+","+hour+","+mins);

                    Character first=day.charAt(0);
                    if (first=='0') {
                        day=day.substring(1);
                    }

                    switch (month){
                        case("01"):
                            month="Января";
                            break;
                        case("02"):
                            month="Февраля";
                            break;
                        case("03"):
                            month="Марта";
                            break;
                        case("04"):
                            month="Апреля";
                            break;
                        case("05"):
                            month="Мая";
                            break;
                        case("06"):
                            month="Июня";
                            break;
                        case("07"):
                            month="Июля";
                            break;
                        case("08"):
                            month="Августа";
                            break;
                        case("09"):
                            month="Сентября";
                            break;
                        case("10"):
                            month="Октября";
                            break;
                        case("11"):
                            month="Ноября";
                            break;
                        case("12"):
                            month="Декабря";
                            break;

                    }
                    binding.dateNow.setText(day+" "+month+" "+ year+"       "+hour+":"+mins);
                    }

                }

            //запрос неуспешный
            @Override
            public void onFailure(Call<ConditionResponse> call, Throwable t) {

            }
        });


        sr=SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(new listener());
        binding.microfon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"voice.recognition.test");
                intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"voice.recognition.test");
                //intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5);
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5);
                sr.startListening(intent);
            }
        });

        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i == 5) {
                    binding.moodText.setText("Прекрасно!");
                    binding.mood.setImageResource(R.drawable.kaif);
                } else if (i == 4) {
                    binding.moodText.setText("Хорошо!");
                    binding.mood.setImageResource(R.drawable.good);
                } else if (i == 3) {
                    binding.moodText.setText("Нормально!");
                    binding.mood.setImageResource(R.drawable.sred);
                } else if (i == 2) {
                    binding.moodText.setText("Плохо!");
                    binding.mood.setImageResource(R.drawable.bad);
                } else if (i == 1) {
                    binding.moodText.setText("Ужасно!");
                    binding.mood.setImageResource(R.drawable.verybad);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.workSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.workHour.setText(String.valueOf(i / 60));
                binding.workMin.setText(String.valueOf(i % 60));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        binding.workHour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.workHour.getText().toString().equals("")) {
                    binding.workSeek.setProgress((Integer.parseInt(String.valueOf(binding.workHour.getText()))) * 60 + (Integer.parseInt(String.valueOf(binding.workMin.getText()))));
                } else {
                    binding.workHour.setText("0");
                }

                if (Integer.parseInt(String.valueOf(binding.workHour.getText())) > 23) {
                    binding.workHour.setText("23");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.workMin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.workMin.getText().toString().equals("")) {
                    binding.workSeek.setProgress((Integer.parseInt(String.valueOf(binding.workHour.getText()))) * 60 + (Integer.parseInt(String.valueOf(binding.workMin.getText()))));
                } else {
                    binding.workMin.setText("0");
                }

                if (Integer.parseInt(String.valueOf(binding.workMin.getText())) > 59) {
                    binding.workMin.setText("59");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.LeisureSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.LeisureHour.setText(String.valueOf(i / 60));
                binding.LeisureMin.setText(String.valueOf(i % 60));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        binding.LeisureHour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.LeisureHour.getText().toString().equals("")) {
                    binding.LeisureSeek.setProgress((Integer.parseInt(String.valueOf(binding.LeisureHour.getText()))) * 60 + (Integer.parseInt(String.valueOf(binding.LeisureMin.getText()))));
                } else {
                    binding.LeisureHour.setText("0");
                }

                if (Integer.parseInt(String.valueOf(binding.LeisureHour.getText())) > 23) {
                    binding.LeisureHour.setText("23");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.LeisureMin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.LeisureMin.getText().toString().equals("")) {
                    binding.LeisureSeek.setProgress((Integer.parseInt(String.valueOf(binding.LeisureHour.getText()))) * 60 + (Integer.parseInt(String.valueOf(binding.LeisureMin.getText()))));
                } else {
                    binding.LeisureMin.setText("0");
                }

                if (Integer.parseInt(String.valueOf(binding.LeisureMin.getText())) > 59) {
                    binding.LeisureMin.setText("59");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.ReadingSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.ReadingHour.setText(String.valueOf(i / 60));
                binding.ReadingMin.setText(String.valueOf(i % 60));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        binding.ReadingHour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.ReadingHour.getText().toString().equals("")) {
                    binding.ReadingSeek.setProgress((Integer.parseInt(String.valueOf(binding.ReadingHour.getText()))) * 60 + (Integer.parseInt(String.valueOf(binding.ReadingMin.getText()))));
                } else {
                    binding.ReadingHour.setText("0");
                }

                if (Integer.parseInt(String.valueOf(binding.ReadingHour.getText())) > 23) {
                    binding.ReadingHour.setText("23");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.ReadingMin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.ReadingMin.getText().toString().equals("")) {
                    binding.ReadingSeek.setProgress((Integer.parseInt(String.valueOf(binding.ReadingHour.getText()))) * 60 + (Integer.parseInt(String.valueOf(binding.ReadingMin.getText()))));
                } else {
                    binding.ReadingMin.setText("0");
                }

                if (Integer.parseInt(String.valueOf(binding.ReadingMin.getText())) > 59) {
                    binding.ReadingMin.setText("59");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.WorkoutSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.WorkoutHour.setText(String.valueOf(i / 60));
                binding.WorkoutMin.setText(String.valueOf(i % 60));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        binding.WorkoutHour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.WorkoutHour.getText().toString().equals("")) {
                    binding.WorkoutSeek.setProgress((Integer.parseInt(String.valueOf(binding.WorkoutHour.getText()))) * 60 + (Integer.parseInt(String.valueOf(binding.WorkoutMin.getText()))));
                } else {
                    binding.WorkoutHour.setText("0");
                }

                if (Integer.parseInt(String.valueOf(binding.WorkoutHour.getText())) > 23) {
                    binding.WorkoutHour.setText("23");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.WorkoutMin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.WorkoutMin.getText().toString().equals("")) {
                    binding.WorkoutSeek.setProgress((Integer.parseInt(String.valueOf(binding.WorkoutHour.getText()))) * 60 + (Integer.parseInt(String.valueOf(binding.WorkoutMin.getText()))));
                } else {
                    binding.WorkoutMin.setText("0");
                }

                if (Integer.parseInt(String.valueOf(binding.WorkoutMin.getText())) > 59) {
                    binding.WorkoutMin.setText("59");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.GamingSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.GamingHour.setText(String.valueOf(i / 60));
                binding.GamingMin.setText(String.valueOf(i % 60));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        binding.GamingHour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.GamingHour.getText().toString().equals("")) {
                    binding.GamingSeek.setProgress((Integer.parseInt(String.valueOf(binding.GamingHour.getText()))) * 60 + (Integer.parseInt(String.valueOf(binding.GamingMin.getText()))));
                } else {
                    binding.GamingHour.setText("0");
                }

                if (Integer.parseInt(String.valueOf(binding.GamingHour.getText())) > 23) {
                    binding.GamingHour.setText("23");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.GamingMin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.GamingMin.getText().toString().equals("")) {
                    binding.GamingSeek.setProgress((Integer.parseInt(String.valueOf(binding.GamingHour.getText()))) * 60 + (Integer.parseInt(String.valueOf(binding.GamingMin.getText()))));
                } else {
                    binding.GamingMin.setText("0");
                }

                if (Integer.parseInt(String.valueOf(binding.GamingMin.getText())) > 59) {
                    binding.GamingMin.setText("59");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.FamilySeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.FamilyHour.setText(String.valueOf(i / 60));
                binding.FamilyMin.setText(String.valueOf(i % 60));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        binding.FamilyHour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.FamilyHour.getText().toString().equals("")) {
                    binding.FamilySeek.setProgress((Integer.parseInt(String.valueOf(binding.FamilyHour.getText()))) * 60 + (Integer.parseInt(String.valueOf(binding.FamilyMin.getText()))));
                } else {
                    binding.FamilyHour.setText("0");
                }

                if (Integer.parseInt(String.valueOf(binding.FamilyHour.getText())) > 23) {
                    binding.FamilyHour.setText("23");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.FamilyMin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.FamilyMin.getText().toString().equals("")) {
                    binding.FamilySeek.setProgress((Integer.parseInt(String.valueOf(binding.FamilyHour.getText()))) * 60 + (Integer.parseInt(String.valueOf(binding.FamilyMin.getText()))));
                } else {
                    binding.FamilyMin.setText("0");
                }

                if (Integer.parseInt(String.valueOf(binding.FamilyMin.getText())) > 59) {
                    binding.FamilyMin.setText("59");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.FriendSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.FriendHour.setText(String.valueOf(i / 60));
                binding.FriendMin.setText(String.valueOf(i % 60));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        binding.FriendHour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.FriendHour.getText().toString().equals("")) {
                    binding.FriendSeek.setProgress((Integer.parseInt(String.valueOf(binding.FriendHour.getText()))) * 60 + (Integer.parseInt(String.valueOf(binding.FriendMin.getText()))));
                } else {
                    binding.FriendHour.setText("0");
                }

                if (Integer.parseInt(String.valueOf(binding.FriendHour.getText())) > 23) {
                    binding.FriendHour.setText("23");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.FriendMin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.FriendMin.getText().toString().equals("")) {
                    binding.FriendSeek.setProgress((Integer.parseInt(String.valueOf(binding.FriendHour.getText()))) * 60 + (Integer.parseInt(String.valueOf(binding.FriendMin.getText()))));
                } else {
                    binding.FriendMin.setText("0");
                }

                if (Integer.parseInt(String.valueOf(binding.FriendMin.getText())) > 59) {
                    binding.FriendMin.setText("59");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.StudySeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.StudyHour.setText(String.valueOf(i / 60));
                binding.StudyMin.setText(String.valueOf(i % 60));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        binding.StudyHour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.StudyHour.getText().toString().equals("")) {
                    binding.StudySeek.setProgress((Integer.parseInt(String.valueOf(binding.StudyHour.getText()))) * 60 + (Integer.parseInt(String.valueOf(binding.StudyMin.getText()))));
                } else {
                    binding.StudyHour.setText("0");
                }

                if (Integer.parseInt(String.valueOf(binding.StudyHour.getText())) > 23) {
                    binding.StudyHour.setText("23");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.StudyMin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.StudyMin.getText().toString().equals("")) {
                    binding.StudySeek.setProgress((Integer.parseInt(String.valueOf(binding.StudyHour.getText()))) * 60 + (Integer.parseInt(String.valueOf(binding.StudyMin.getText()))));
                } else {
                    binding.StudyMin.setText("0");
                }

                if (Integer.parseInt(String.valueOf(binding.StudyMin.getText())) > 59) {
                    binding.StudyMin.setText("59");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.MusicSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.MusicHour.setText(String.valueOf(i / 60));
                binding.MusicMin.setText(String.valueOf(i % 60));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        binding.MusicHour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.MusicHour.getText().toString().equals("")) {
                    binding.MusicSeek.setProgress((Integer.parseInt(String.valueOf(binding.MusicHour.getText()))) * 60 + (Integer.parseInt(String.valueOf(binding.MusicMin.getText()))));
                } else {
                    binding.MusicHour.setText("0");
                }

                if (Integer.parseInt(String.valueOf(binding.MusicHour.getText())) > 23) {
                    binding.MusicHour.setText("23");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.MusicMin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.MusicMin.getText().toString().equals("")) {
                    binding.MusicSeek.setProgress((Integer.parseInt(String.valueOf(binding.MusicHour.getText()))) * 60 + (Integer.parseInt(String.valueOf(binding.MusicMin.getText()))));
                } else {
                    binding.MusicMin.setText("0");
                }

                if (Integer.parseInt(String.valueOf(binding.MusicMin.getText())) > 59) {
                    binding.MusicMin.setText("59");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.MovieSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.MovieHour.setText(String.valueOf(i / 60));
                binding.MovieMin.setText(String.valueOf(i % 60));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        binding.MovieHour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.MovieHour.getText().toString().equals("")) {
                    binding.MovieSeek.setProgress((Integer.parseInt(String.valueOf(binding.MovieHour.getText()))) * 60 + (Integer.parseInt(String.valueOf(binding.MovieMin.getText()))));
                } else {
                    binding.MovieHour.setText("0");
                }

                if (Integer.parseInt(String.valueOf(binding.MovieHour.getText())) > 23) {
                    binding.MovieHour.setText("23");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.MovieMin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.MovieMin.getText().toString().equals("")) {
                    binding.MovieSeek.setProgress((Integer.parseInt(String.valueOf(binding.MovieHour.getText()))) * 60 + (Integer.parseInt(String.valueOf(binding.MovieMin.getText()))));
                } else {
                    binding.MovieMin.setText("0");
                }

                if (Integer.parseInt(String.valueOf(binding.MovieMin.getText())) > 59) {
                    binding.MovieMin.setText("59");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.ShoppingSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.ShoppingHour.setText(String.valueOf(i / 60));
                binding.ShoppingMin.setText(String.valueOf(i % 60));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        binding.ShoppingHour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.ShoppingHour.getText().toString().equals("")) {
                    binding.ShoppingSeek.setProgress((Integer.parseInt(String.valueOf(binding.ShoppingHour.getText()))) * 60 + (Integer.parseInt(String.valueOf(binding.ShoppingMin.getText()))));
                } else {
                    binding.ShoppingHour.setText("0");
                }

                if (Integer.parseInt(String.valueOf(binding.ShoppingHour.getText())) > 23) {
                    binding.ShoppingHour.setText("23");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.ShoppingMin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.ShoppingMin.getText().toString().equals("")) {
                    binding.ShoppingSeek.setProgress((Integer.parseInt(String.valueOf(binding.ShoppingHour.getText()))) * 60 + (Integer.parseInt(String.valueOf(binding.ShoppingMin.getText()))));
                } else {
                    binding.ShoppingMin.setText("0");
                }

                if (Integer.parseInt(String.valueOf(binding.ShoppingMin.getText())) > 59) {
                    binding.ShoppingMin.setText("59");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.TravelSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.TravelHour.setText(String.valueOf(i / 60));
                binding.TravelMin.setText(String.valueOf(i % 60));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        binding.TravelHour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.TravelHour.getText().toString().equals("")) {
                    binding.TravelSeek.setProgress((Integer.parseInt(String.valueOf(binding.TravelHour.getText()))) * 60 + (Integer.parseInt(String.valueOf(binding.TravelMin.getText()))));
                } else {
                    binding.TravelHour.setText("0");
                }

                if (Integer.parseInt(String.valueOf(binding.TravelHour.getText())) > 23) {
                    binding.TravelHour.setText("23");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.TravelMin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.TravelMin.getText().toString().equals("")) {
                    binding.TravelSeek.setProgress((Integer.parseInt(String.valueOf(binding.TravelHour.getText()))) * 60 + (Integer.parseInt(String.valueOf(binding.TravelMin.getText()))));
                } else {
                    binding.TravelMin.setText("0");
                }

                if (Integer.parseInt(String.valueOf(binding.TravelMin.getText())) > 59) {
                    binding.TravelMin.setText("59");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.CleaningSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.CleaningHour.setText(String.valueOf(i / 60));
                binding.CleaningMin.setText(String.valueOf(i % 60));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        binding.CleaningHour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.CleaningHour.getText().toString().equals("")) {
                    binding.CleaningSeek.setProgress((Integer.parseInt(String.valueOf(binding.CleaningHour.getText()))) * 60 + (Integer.parseInt(String.valueOf(binding.CleaningMin.getText()))));
                } else {
                    binding.CleaningHour.setText("0");
                }

                if (Integer.parseInt(String.valueOf(binding.CleaningHour.getText())) > 23) {
                    binding.CleaningHour.setText("23");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.CleaningMin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.CleaningMin.getText().toString().equals("")) {
                    binding.CleaningSeek.setProgress((Integer.parseInt(String.valueOf(binding.CleaningHour.getText()))) * 60 + (Integer.parseInt(String.valueOf(binding.CleaningMin.getText()))));
                } else {
                    binding.CleaningMin.setText("0");
                }

                if (Integer.parseInt(String.valueOf(binding.CleaningMin.getText())) > 59) {
                    binding.CleaningMin.setText("59");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.SleepSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.SleepHour.setText(String.valueOf(i / 60));
                binding.SleepMin.setText(String.valueOf(i % 60));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        binding.SleepHour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.SleepHour.getText().toString().equals("")) {
                    binding.SleepSeek.setProgress((Integer.parseInt(String.valueOf(binding.SleepHour.getText()))) * 60 + (Integer.parseInt(String.valueOf(binding.SleepMin.getText()))));
                } else {
                    binding.SleepHour.setText("0");
                }

                if (Integer.parseInt(String.valueOf(binding.SleepHour.getText())) > 23) {
                    binding.SleepHour.setText("23");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.SleepMin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.SleepMin.getText().toString().equals("")) {
                    binding.SleepSeek.setProgress((Integer.parseInt(String.valueOf(binding.SleepHour.getText()))) * 60 + (Integer.parseInt(String.valueOf(binding.SleepMin.getText()))));
                } else {
                    binding.SleepMin.setText("0");
                }

                if (Integer.parseInt(String.valueOf(binding.SleepMin.getText())) > 59) {
                    binding.SleepMin.setText("59");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.PartySeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.PartyHour.setText(String.valueOf(i / 60));
                binding.PartyMin.setText(String.valueOf(i % 60));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        binding.PartyHour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.PartyHour.getText().toString().equals("")) {
                    binding.PartySeek.setProgress((Integer.parseInt(String.valueOf(binding.PartyHour.getText()))) * 60 + (Integer.parseInt(String.valueOf(binding.PartyMin.getText()))));
                } else {
                    binding.PartyHour.setText("0");
                }

                if (Integer.parseInt(String.valueOf(binding.PartyHour.getText())) > 23) {
                    binding.PartyHour.setText("23");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.PartyMin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.PartyMin.getText().toString().equals("")) {
                    binding.PartySeek.setProgress((Integer.parseInt(String.valueOf(binding.PartyHour.getText()))) * 60 + (Integer.parseInt(String.valueOf(binding.PartyMin.getText()))));
                } else {
                    binding.PartyMin.setText("0");
                }

                if (Integer.parseInt(String.valueOf(binding.PartyMin.getText())) > 59) {
                    binding.PartyMin.setText("59");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.BarSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.BarHour.setText(String.valueOf(i / 60));
                binding.BarMin.setText(String.valueOf(i % 60));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        binding.BarHour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.BarHour.getText().toString().equals("")) {
                    binding.BarSeek.setProgress((Integer.parseInt(String.valueOf(binding.BarHour.getText()))) * 60 + (Integer.parseInt(String.valueOf(binding.BarMin.getText()))));
                } else {
                    binding.BarHour.setText("0");
                }

                if (Integer.parseInt(String.valueOf(binding.BarHour.getText())) > 23) {
                    binding.BarHour.setText("23");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.BarMin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.BarMin.getText().toString().equals("")) {
                    binding.BarSeek.setProgress((Integer.parseInt(String.valueOf(binding.BarHour.getText()))) * 60 + (Integer.parseInt(String.valueOf(binding.BarMin.getText()))));
                } else {
                    binding.BarMin.setText("0");
                }

                if (Integer.parseInt(String.valueOf(binding.BarMin.getText())) > 59) {
                    binding.BarMin.setText("59");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.RendezvousSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.RendezvousHour.setText(String.valueOf(i / 60));
                binding.RendezvousMin.setText(String.valueOf(i % 60));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        binding.RendezvousHour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.RendezvousHour.getText().toString().equals("")) {
                    binding.RendezvousSeek.setProgress((Integer.parseInt(String.valueOf(binding.RendezvousHour.getText()))) * 60 + (Integer.parseInt(String.valueOf(binding.RendezvousMin.getText()))));
                } else {
                    binding.RendezvousHour.setText("0");
                }

                if (Integer.parseInt(String.valueOf(binding.RendezvousHour.getText())) > 23) {
                    binding.RendezvousHour.setText("23");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.RendezvousMin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.RendezvousMin.getText().toString().equals("")) {
                    binding.RendezvousSeek.setProgress((Integer.parseInt(String.valueOf(binding.RendezvousHour.getText()))) * 60 + (Integer.parseInt(String.valueOf(binding.RendezvousMin.getText()))));
                } else {
                    binding.RendezvousMin.setText("0");
                }

                if (Integer.parseInt(String.valueOf(binding.RendezvousMin.getText())) > 59) {
                    binding.RendezvousMin.setText("59");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.TVSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.TVHour.setText(String.valueOf(i / 60));
                binding.TVMin.setText(String.valueOf(i % 60));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        binding.TVHour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.TVHour.getText().toString().equals("")) {
                    binding.TVSeek.setProgress((Integer.parseInt(String.valueOf(binding.TVHour.getText()))) * 60 + (Integer.parseInt(String.valueOf(binding.TVMin.getText()))));
                } else {
                    binding.TVHour.setText("0");
                }

                if (Integer.parseInt(String.valueOf(binding.TVHour.getText())) > 23) {
                    binding.TVHour.setText("23");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.TVMin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.TVMin.getText().toString().equals("")) {
                    binding.TVSeek.setProgress((Integer.parseInt(String.valueOf(binding.TVHour.getText()))) * 60 + (Integer.parseInt(String.valueOf(binding.TVMin.getText()))));
                } else {
                    binding.TVMin.setText("0");
                }

                if (Integer.parseInt(String.valueOf(binding.TVMin.getText())) > 59) {
                    binding.TVMin.setText("59");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Call<AuthorizationResponse> call = ApiClient.AuthorizationUserService().getloginUser(access);
                Log.d("Отладка для апдейт",call.request().header("Authorization").toString());
                call.enqueue(new Callback<AuthorizationResponse>() {
                    @Override
                    public void onResponse(Call<AuthorizationResponse> call, Response<AuthorizationResponse> response) {
                        Log.d("Norm",response.headers().toString());
                        Log.d("Отладка для апдейт",response.toString());
                        if(response.isSuccessful()){
                            id=response.body().getId().toString();
                            if(!id.equals("Bad")&&!id.equals("") )
                                Log.e("Отладка для апдейтID",id);
                                updateCondtidion(id);
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
        });


    }
    private void updateCondtidion(String Userid) {

        Log.e("userid",Userid);
        ConditionResponsePost condition = new ConditionResponsePost(idCondition,binding.seekBar.getProgress(),binding.descriptionText.getText().toString(),"2022-12-10 17:26:04.456470",binding.workSeek.getProgress(),binding.ReadingSeek.getProgress(),binding.WorkoutSeek.getProgress(),binding.GamingSeek.getProgress(),binding.FamilySeek.getProgress(),binding.FriendSeek.getProgress(),binding.StudySeek.getProgress(),binding.MusicSeek.getProgress(),binding.MovieSeek.getProgress(),binding.ShoppingSeek.getProgress(),binding.TravelSeek.getProgress(),binding.CleaningSeek.getProgress(),binding.SleepSeek.getProgress(),binding.PartySeek.getProgress(),binding.BarSeek.getProgress(),binding.LeisureSeek.getProgress(),binding.RendezvousSeek.getProgress(),binding.TVSeek.getProgress(),Integer.valueOf(Userid));
        Call<ConditionResponsePost> call = ApiClient.putConditionService().putConditions(condition);
        call.enqueue(new Callback<ConditionResponsePost>() {
            @Override
            public void onResponse(Call<ConditionResponsePost> call, Response<ConditionResponsePost> response) {
                if(response.isSuccessful()) {
                    Log.e("Отладка для апдейтНе грузит заметки", "ok");
                    Intent intent2 = new Intent(UpdateNoteActivity.this, MenuActivity.class);
                    intent2.putExtra("access", access);
                    intent2.putExtra("fragment", "notes");
                    startActivity(intent2);
                }
                Log.e("Proverka otvet",response.headers().toString());

            }

            @Override
            public void onFailure(Call<ConditionResponsePost> call, Throwable t) {
                Toast.makeText(UpdateNoteActivity.this,"Заметка не была создана",Toast.LENGTH_SHORT);
            }
        });


    }

    class listener implements RecognitionListener
    {
        public void onReadyForSpeech(Bundle params)
        {
            Log.d("Отладка звук", "onReadyForSpeech");
        }
        public void onBeginningOfSpeech()
        {
            Log.d("Отладка звук", "onBeginningOfSpeech");
        }
        public void onRmsChanged(float rmsdB)
        {
            Log.d("Отладка звук", "onRmsChanged");
        }
        public void onBufferReceived(byte[] buffer)
        {
            Log.d("Отладка звук", "onBufferReceived");
        }
        public void onEndOfSpeech()
        {
            Log.d("Отладка звук", "onEndofSpeech");
        }
        public void onError(int error)
        {
            Log.d("Отладка звук",  "error " +  error);
            binding.descriptionText.setText("error " + error);
        }
        public void onResults(Bundle results)
        {
            String str = new String();
            Log.d("Отладка звук", "onResults " + results);
            ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for (int i = 0; i < data.size(); i++)
            {
                Log.d("Отладка звук", "result " + data.get(i));
                str += data.get(i);
            }
            //text.setText("results: "+String.valueOf(data.size()));
            String upperString = data.get(0).toString().substring(0, 1).toUpperCase() + data.get(0).toString().substring(1).toLowerCase();
            binding.descriptionText.setText(String.valueOf(upperString)+".");
        }
        public void onPartialResults(Bundle partialResults)
        {
            Log.d("Отладка звук", "onPartialResults");
        }
        public void onEvent(int eventType, Bundle params)
        {
            Log.d("Отладка звук", "onEvent " + eventType);
        }
    }

}