package com.example.grifmood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grifmood.databinding.ActivityMainBinding;
import com.example.grifmood.databinding.ActivityNewNoteBinding;

import android.content.Intent;
import android.location.GnssAntennaInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewNoteActivity extends AppCompatActivity {
    private ActivityNewNoteBinding binding;
    private String id="";
    String access="";
    private SpeechRecognizer sr;
    ArrayList<String> activity;
    ArrayList<String> hours;
    ArrayList<String> mins;
    String auto="";
    String nastroenie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityNewNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        auto=intent.getStringExtra("auto");
        access=intent.getStringExtra("access");
        if(auto.equals("yes")) {
            activity = intent.getStringArrayListExtra("activity");
            hours = intent.getStringArrayListExtra("hours");
            mins = intent.getStringArrayListExtra("mins");
            nastroenie = intent.getStringExtra("nastroenie");
        }
        //String access = getArguments().getString("access","");
        //Log.d("Calendar",access);

        binding.logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(NewNoteActivity.this, MainActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
            }
        });

        Date currentDate=new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String dateText = dateFormat.format(currentDate);
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String timeText = timeFormat.format(currentDate);
        binding.dateNow.setText(dateText+" "+timeText);
        EditText editText = new EditText(this);
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
                Log.d("Norm123234235",call.request().header("Authorization").toString());
                call.enqueue(new Callback<AuthorizationResponse>() {
                    @Override
                    public void onResponse(Call<AuthorizationResponse> call, Response<AuthorizationResponse> response) {
                        Log.d("Norm",response.headers().toString());
                        Log.d("Norm",response.toString());
                        if(response.isSuccessful()){
                            id=response.body().getId().toString();
                            if(!id.equals("Bad")&&!id.equals("") )
                                Log.e("ID",id);
                                createCondtidion(id);
                        }
                        else {
                            id="Bad";
                        }

                    }

                    @Override
                    public void onFailure(Call<AuthorizationResponse> call, Throwable t) {
                        Log.d("Norm","hm");
                        id="Bad";
                        Toast.makeText(NewNoteActivity.this, "Проблемы с соединением!", Toast.LENGTH_SHORT).show();
                    }
                });

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

        if(auto.equals("yes")){

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
                            Log.e("ID", id);
                            if (nastroenie.equals("ОТЛИЧНОЕ"))
                                binding.seekBar.setProgress(5);
                            else if (nastroenie.equals("ХОРОШЕЕ"))
                                binding.seekBar.setProgress(4);
                            else if (nastroenie.equals("НОРМАЛЬНОЕ"))
                                binding.seekBar.setProgress(3);
                            else if (nastroenie.equals("ПЛОХОЕ"))
                                binding.seekBar.setProgress(2);
                            else if (nastroenie.equals("УЖАСНОЕ"))
                                binding.seekBar.setProgress(1);

                            for(int k=0;k<activity.size();k++) {

                                Integer timeHour = 0;
                                if (hours.get(k).equals("0"))
                                    timeHour = 0;
                                else if (hours.get(k).equals("1"))
                                    timeHour = 1;
                                else if (hours.get(k).equals("2"))
                                    timeHour = 2;
                                else if (hours.get(k).equals("3"))
                                    timeHour = 3;
                                else if (hours.get(k).equals("4"))
                                    timeHour = 4;
                                else if (hours.get(k).equals("5"))
                                    timeHour = 5;
                                else if (hours.get(k).equals("6"))
                                    timeHour = 6;
                                else if (hours.get(k).equals("7"))
                                    timeHour = 7;
                                else if (hours.get(k).equals("8"))
                                    timeHour = 8;
                                else if (hours.get(k).equals("9"))
                                    timeHour = 9;
                                else if (hours.get(k).equals("10"))
                                    timeHour = 10;
                                else if (hours.get(k).equals("11"))
                                    timeHour = 11;
                                else if (hours.get(k).equals("12"))
                                    timeHour = 12;
                                else if (hours.get(k).equals("13"))
                                    timeHour = 13;
                                else if (hours.get(k).equals("14"))
                                    timeHour = 14;
                                else if (hours.get(k).equals("15"))
                                    timeHour = 15;
                                else if (hours.get(k).equals("16"))
                                    timeHour = 16;
                                else if (hours.get(k).equals("17"))
                                    timeHour = 17;
                                else if (hours.get(k).equals("18"))
                                    timeHour = 18;
                                else if (hours.get(k).equals("19"))
                                    timeHour = 19;
                                else if (hours.get(k).equals("20"))
                                    timeHour = 20;
                                else if (hours.get(k).equals("21"))
                                    timeHour = 21;
                                else if (hours.get(k).equals("22"))
                                    timeHour = 22;
                                else if (hours.get(k).equals("23"))
                                    timeHour = 23;


                                Integer timeMins = 0;
                                if (mins.get(k).equals("0"))
                                    timeMins = 0;
                                else if (mins.get(k).equals("1"))
                                    timeMins = 1;
                                else if (mins.get(k).equals("2"))
                                    timeMins = 2;
                                else if (mins.get(k).equals("3"))
                                    timeMins = 3;
                                else if (mins.get(k).equals("4"))
                                    timeMins = 4;
                                else if (mins.get(k).equals("5"))
                                    timeMins = 5;
                                else if (mins.get(k).equals("6"))
                                    timeMins = 6;
                                else if (mins.get(k).equals("7"))
                                    timeMins = 7;
                                else if (mins.get(k).equals("8"))
                                    timeMins = 8;
                                else if (mins.get(k).equals("9"))
                                    timeMins = 9;
                                else if (mins.get(k).equals("10"))
                                    timeMins = 10;
                                else if (mins.get(k).equals("11"))
                                    timeMins = 11;
                                else if (mins.get(k).equals("12"))
                                    timeMins = 12;
                                else if (mins.get(k).equals("13"))
                                    timeMins = 13;
                                else if (mins.get(k).equals("14"))
                                    timeMins = 14;
                                else if (mins.get(k).equals("15"))
                                    timeMins = 15;
                                else if (mins.get(k).equals("16"))
                                    timeMins = 16;
                                else if (mins.get(k).equals("17"))
                                    timeMins = 17;
                                else if (mins.get(k).equals("18"))
                                    timeMins = 18;
                                else if (mins.get(k).equals("19"))
                                    timeMins = 19;
                                else if (mins.get(k).equals("20"))
                                    timeMins = 20;
                                else if (mins.get(k).equals("21"))
                                    timeMins = 21;
                                else if (mins.get(k).equals("22"))
                                    timeMins = 22;
                                else if (mins.get(k).equals("23"))
                                    timeMins = 23;
                                else if (mins.get(k).equals("24"))
                                    timeMins = 24;
                                else if (mins.get(k).equals("25"))
                                    timeMins = 25;
                                else if (mins.get(k).equals("26"))
                                    timeMins = 26;
                                else if (mins.get(k).equals("27"))
                                    timeMins = 27;
                                else if (mins.get(k).equals("28"))
                                    timeMins = 28;
                                else if (mins.get(k).equals("29"))
                                    timeMins = 29;
                                else if (mins.get(k).equals("30"))
                                    timeMins = 30;
                                else if (mins.get(k).equals("31"))
                                    timeMins = 31;
                                else if (mins.get(k).equals("32"))
                                    timeMins = 32;
                                else if (mins.get(k).equals("33"))
                                    timeMins = 33;
                                else if (mins.get(k).equals("34"))
                                    timeMins = 34;
                                else if (mins.get(k).equals("35"))
                                    timeMins = 35;
                                else if (mins.get(k).equals("36"))
                                    timeMins = 36;
                                else if (mins.get(k).equals("37"))
                                    timeMins = 37;
                                else if (mins.get(k).equals("38"))
                                    timeMins = 38;
                                else if (mins.get(k).equals("39"))
                                    timeMins = 39;
                                else if (mins.get(k).equals("40"))
                                    timeMins = 40;
                                else if (mins.get(k).equals("41"))
                                    timeMins = 41;
                                else if (mins.get(k).equals("42"))
                                    timeMins = 42;
                                else if (mins.get(k).equals("43"))
                                    timeMins = 43;
                                else if (mins.get(k).equals("44"))
                                    timeMins = 44;
                                else if (mins.get(k).equals("45"))
                                    timeMins = 45;
                                else if (mins.get(k).equals("46"))
                                    timeMins = 46;
                                else if (mins.get(k).equals("47"))
                                    timeMins = 47;
                                else if (mins.get(k).equals("48"))
                                    timeMins = 48;
                                else if (mins.get(k).equals("49"))
                                    timeMins = 49;
                                else if (mins.get(k).equals("50"))
                                    timeMins = 50;
                                else if (mins.get(k).equals("51"))
                                    timeMins = 51;
                                else if (mins.get(k).equals("52"))
                                    timeMins = 52;
                                else if (mins.get(k).equals("53"))
                                    timeMins = 53;
                                else if (mins.get(k).equals("54"))
                                    timeMins = 54;
                                else if (mins.get(k).equals("55"))
                                    timeMins =55;
                                else if (mins.get(k).equals("56"))
                                    timeMins = 56;
                                else if (mins.get(k).equals("57"))
                                    timeMins = 57;
                                else if (mins.get(k).equals("58"))
                                    timeMins = 58;
                                else if (mins.get(k).equals("59"))
                                    timeMins = 59;


                                Integer timee = timeHour * 60 + timeMins;

                                if (activity.get(k).equals("РАБОТА"))
                                    binding.workSeek.setProgress(timee);
                                else if (activity.get(k).equals("УЧЁБА"))
                                    binding.StudySeek.setProgress(timee);
                                else if (activity.get(k).equals("СОН"))
                                    binding.SleepSeek.setProgress(timee);
                                else if (activity.get(k).equals("УЧЁБА"))
                                    binding.StudySeek.setProgress(timee);
                                else if (activity.get(k).equals("ОТДЫХ"))
                                    binding.LeisureSeek.setProgress(timee);
                                else if (activity.get(k).equals("ТЕЛЕВИЗОР"))
                                    binding.TVSeek.setProgress(timee);
                                else if (activity.get(k).equals("ФИЛЬМЫ"))
                                    binding.MovieSeek.setProgress(timee);
                                else if (activity.get(k).equals("СВИДАНИЯ"))
                                    binding.RendezvousSeek.setProgress(timee);
                                else if (activity.get(k).equals("ДРУЗЬЯ"))
                                    binding.FriendSeek.setProgress(timee);
                                else if (activity.get(k).equals("СЕМЬЯ"))
                                    binding.FamilySeek.setProgress(timee);
                                else if (activity.get(k).equals("СПОРТ"))
                                    binding.WorkoutSeek.setProgress(timee);
                                else if (activity.get(k).equals("УБОРКА"))
                                    binding.CleaningSeek.setProgress(timee);
                                else if (activity.get(k).equals("ЧТЕНИЕ"))
                                    binding.CleaningSeek.setProgress(timee);
                                else if (activity.get(k).equals("ИГРЫ"))
                                    binding.GamingSeek.setProgress(timee);
                                else if (activity.get(k).equals("МУЗЫКА"))
                                    binding.MusicSeek.setProgress(timee);
                                else if (activity.get(k).equals("ПОКУПКИ"))
                                    binding.ShoppingSeek.setProgress(timee);
                                else if (activity.get(k).equals("ПУТЕШЕСТВИЯ"))
                                    binding.TravelSeek.setProgress(timee);
                                else if (activity.get(k).equals("ВЕЧЕРИНКИ"))
                                    binding.PartySeek.setProgress(timee);
                                else if (activity.get(k).equals("БАР"))
                                    binding.BarSeek.setProgress(timee);

                            }

                            ConditionResponsePost condition = new ConditionResponsePost(0,binding.seekBar.getProgress(),"","2022-12-10 17:26:04.456470",binding.workSeek.getProgress(),binding.ReadingSeek.getProgress(),binding.WorkoutSeek.getProgress(),binding.GamingSeek.getProgress(),binding.FamilySeek.getProgress(),binding.FriendSeek.getProgress(),binding.StudySeek.getProgress(),binding.MusicSeek.getProgress(),binding.MovieSeek.getProgress(),binding.ShoppingSeek.getProgress(),binding.TravelSeek.getProgress(),binding.CleaningSeek.getProgress(),binding.SleepSeek.getProgress(),binding.PartySeek.getProgress(),binding.BarSeek.getProgress(),binding.LeisureSeek.getProgress(),binding.RendezvousSeek.getProgress(),binding.TVSeek.getProgress(),Integer.valueOf(id));
                            Call<ConditionResponsePost> call1 = ApiClient.postConditionService().PostConditions(condition);
                            call1.enqueue(new Callback<ConditionResponsePost>() {
                                @Override
                                public void onResponse(Call<ConditionResponsePost> call, Response<ConditionResponsePost> response) {
                                    if(response.isSuccessful()) {
                                        Log.e("Не грузит заметки", "ok");
                                        Handler handler = new Handler();
                                        Toast.makeText(NewNoteActivity.this,"Заметка успешно создана",Toast.LENGTH_SHORT).show();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                        Intent intent2 = new Intent(NewNoteActivity.this, MenuActivity.class);
                                        intent2.putExtra("access", access);
                                        intent2.putExtra("fragment", "notes");
                                        startActivity(intent2);
                                            }
                                            }, 500);
                                    }
                                    Log.e("Proverka otvet",response.headers().toString());

                                }

                                @Override
                                public void onFailure(Call<ConditionResponsePost> call, Throwable t) {
                                    Toast.makeText(NewNoteActivity.this, "Проблемы с соединением!", Toast.LENGTH_SHORT).show();
                                }
                            });

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
                }
            });

        }

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


    private void createCondtidion(String Userid) {

        Log.e("userid",Userid);
        ConditionResponsePost condition = new ConditionResponsePost(0,binding.seekBar.getProgress(),binding.descriptionText.getText().toString(),"2022-12-10 17:26:04.456470",binding.workSeek.getProgress(),binding.ReadingSeek.getProgress(),binding.WorkoutSeek.getProgress(),binding.GamingSeek.getProgress(),binding.FamilySeek.getProgress(),binding.FriendSeek.getProgress(),binding.StudySeek.getProgress(),binding.MusicSeek.getProgress(),binding.MovieSeek.getProgress(),binding.ShoppingSeek.getProgress(),binding.TravelSeek.getProgress(),binding.CleaningSeek.getProgress(),binding.SleepSeek.getProgress(),binding.PartySeek.getProgress(),binding.BarSeek.getProgress(),binding.LeisureSeek.getProgress(),binding.RendezvousSeek.getProgress(),binding.TVSeek.getProgress(),Integer.valueOf(Userid));
        Call<ConditionResponsePost> call = ApiClient.postConditionService().PostConditions(condition);
        call.enqueue(new Callback<ConditionResponsePost>() {
            @Override
            public void onResponse(Call<ConditionResponsePost> call, Response<ConditionResponsePost> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(NewNoteActivity.this, "Заметка успешно сохранена!", Toast.LENGTH_SHORT).show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                    Log.e("Не грузит заметки", "ok");
                    Intent intent2 = new Intent(NewNoteActivity.this, MenuActivity.class);
                    intent2.putExtra("access", access);
                    intent2.putExtra("fragment", "notes");
                    startActivity(intent2);
                        }
                    }, 500);
                        }
                Log.e("Proverka otvet",response.headers().toString());

            }

            @Override
            public void onFailure(Call<ConditionResponsePost> call, Throwable t) {
                Toast.makeText(NewNoteActivity.this, "Проблемы с соединением!", Toast.LENGTH_SHORT).show();
            }
        });

    }

}