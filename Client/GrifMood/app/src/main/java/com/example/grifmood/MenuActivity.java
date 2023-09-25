package com.example.grifmood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.biometrics.BiometricManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grifmood.databinding.ActivityMenuBinding;

import java.util.ArrayList;

import kotlin.text.StringsKt;

public class MenuActivity extends AppCompatActivity implements AdapterNote.OnNoteListener {
    ActivityMenuBinding binding;
    String access;
    private SpeechRecognizer sr;
    Bundle bundle;
    String frag;
    int check;
    private static final int REQUEST_CALL = 1;
    Dialog dialog;
    private final int REQUEST_PERMISSION_PHONE_STATE=1;
    private Boolean firstMikrofon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        Intent intent = getIntent();
        access = intent.getStringExtra("access");
        frag = intent.getStringExtra("fragment");

        bundle = new Bundle();
        setContentView(binding.getRoot());
        //setupViewPagerAdapter(binding.viewPager);
        Fragment frg = new CalendarFragment();
        bundle.putString("access", access);
        frg.setArguments(bundle);
        replaceFragment(frg,bundle);
        binding.all.setBackgroundColor(getResources().getColor(R.color.teal_phone));
        sr=SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(new listener());

        if (frag!=null)
            if(frag.equals("tests"))
                replaceFragment(new TestsFragment(), bundle);
            else  if(frag.equals("notes"))
                replaceFragment(new NotesFragment(), bundle);

            binding.account.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(MenuActivity.this, ProfileActivity.class);
                    intent1.putExtra("access", access);
                    startActivity(intent1);
                }
            });

        binding.sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //binding.mikrofonVvod.setVisibility(View.VISIBLE);
                ActivityCompat.requestPermissions(
                        MenuActivity.this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        777
                        );

                Toast.makeText(MenuActivity.this,"Говорите",Toast.LENGTH_SHORT).show();
                Intent intentSr = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intentSr.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intentSr.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"voice.recognition.test");
                intentSr.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"voice.recognition.test");
                //intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5);
                intentSr.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5);
                sr.startListening(intentSr);

            }
        });

        binding.logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MenuActivity.this, MainActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
            }
        });



        binding.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragmentMy dialogFragmentMy=new DialogFragmentMy();
                dialogFragmentMy.show(getSupportFragmentManager(),"custom");
            }
        });

        binding.telephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setCancelable(false);
                final String [] items = new String[] {"Телефоны горячей линии", "Онлайн-сервисы"};

                builder.setTitle("Обратитесь за бесплатной психологической помощью:");
                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      if (items[which].equals("Телефоны горячей линии")){
                          check=0;
                      }
                      else {
                          check=1;
                      }
                    }
                });
                builder.setPositiveButton("Выбрать", new DialogInterface.OnClickListener() { // Кнопка Да

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (check==0){
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext(),R.style.AlertDialogMy);
                            final String [] items1 = new String[] {"1.Единый российский телефон доверия:\n8 (800) 333-44-34", "2.Москва:\n8 (495) 051", "3.Санкт-Петербург:\n8 (812) 323-43-43",
                            "4. Казань:\n8 (843) 571-35-71","5. Воронеж:\n8 (473) 207-06-27","6. Ростов-на-Дону:\n8 (863) 267-93-04", "7. Новосибирск:\n8 (383) 204-90-95",
                                    "8. Екатеринбург:\n8 (800) 300-11-00","9. Нижний Новгород:\n8 (831) 419-50-00","10. Челябинск:\n8 (351) 735-02-14","11. Самара:\n8 (351) 269-77-77",
                            "12. Омск:\n8 (3812) 56-56-65", "13. Уфа:\n8 (347) 295-02-36", "14. Красноярск:\n8-800-100-34-94", "15. Владивосток:\n8 (423) 220-65-73",
                            "16. Пермь:\n8-800-2008-911","17. Волгоград:\n8 (8442) 38-03-03"};
                            builder1.setTitle("Список городов");
                            builder1.setIcon(R.drawable.ic_list1);
                            builder1.setItems(items1, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int item) {
                                    String st = "88003334434";
                                    String st2 = "8495051";
                                    String st3 = "88123234343";
                                    String st4 = "88435713571";
                                    String st5 = "84732070627";
                                    String st6 = "88632679304";
                                    String st7 = "83832049095";
                                    String st8 = "88003001100";
                                    String st9 = "88314195000";
                                    String st10 = "83517350214";
                                    String st11 = "83512697777";
                                    String st12 = "83812565665";
                                    String st13 = "83472950236";
                                    String st14 = "88001003494";
                                    String st15 = "84232206573";
                                    String st16 = "88002008911";
                                    String st17 = "88442380303";

                                        if (item == 0)
                                        // if (stringArray.toString().equals("1"))
                                        {
                                            if (ContextCompat.checkSelfPermission(MenuActivity.this,
                                                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                ActivityCompat.requestPermissions(MenuActivity.this,
                                                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                                            } else {
                                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: "+ st));
                                                startActivity(intent);
                                            }
                                            }
                                        else if (item == 1)
                                        // else if (stringArray.toString().contains("2"))
                                        {
                                            if (ContextCompat.checkSelfPermission(MenuActivity.this,
                                                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                ActivityCompat.requestPermissions(MenuActivity.this,
                                                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                                            } else {
                                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: "+ st2));
                                                startActivity(intent);
                                            }
                                        }
                                        else if (item == 2)
                                        // else if (stringArray.toString().contains("2"))
                                        {
                                            if (ContextCompat.checkSelfPermission(MenuActivity.this,
                                                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                ActivityCompat.requestPermissions(MenuActivity.this,
                                                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                                            } else {
                                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: "+ st3));
                                                startActivity(intent);
                                            }
                                        }
                                        else if (item == 3)
                                        // else if (stringArray.toString().contains("2"))
                                        {
                                            if (ContextCompat.checkSelfPermission(MenuActivity.this,
                                                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                ActivityCompat.requestPermissions(MenuActivity.this,
                                                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                                            } else {
                                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: "+ st4));
                                                startActivity(intent);
                                            }
                                        }
                                        else if (item == 4)
                                        // else if (stringArray.toString().contains("2"))
                                        {
                                            if (ContextCompat.checkSelfPermission(MenuActivity.this,
                                                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                ActivityCompat.requestPermissions(MenuActivity.this,
                                                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                                            } else {
                                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: "+ st5));
                                                startActivity(intent);
                                            }
                                        }
                                        else if (item == 5)
                                        // else if (stringArray.toString().contains("2"))
                                        {
                                            if (ContextCompat.checkSelfPermission(MenuActivity.this,
                                                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                ActivityCompat.requestPermissions(MenuActivity.this,
                                                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                                            } else {
                                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: "+ st6));
                                                startActivity(intent);
                                            }
                                        }
                                        else if (item == 6)
                                        // else if (stringArray.toString().contains("2"))
                                        {
                                            if (ContextCompat.checkSelfPermission(MenuActivity.this,
                                                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                ActivityCompat.requestPermissions(MenuActivity.this,
                                                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                                            } else {
                                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: "+ st7));
                                                startActivity(intent);
                                            }
                                        }

                                    if (item == 7)
                                    // if (stringArray.toString().equals("1"))
                                    {
                                        if (ContextCompat.checkSelfPermission(MenuActivity.this,
                                                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                            ActivityCompat.requestPermissions(MenuActivity.this,
                                                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                                        } else {
                                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: "+ st8));
                                            startActivity(intent);
                                        }
                                    }
                                    else if (item == 8)
                                    // else if (stringArray.toString().contains("2"))
                                    {
                                        if (ContextCompat.checkSelfPermission(MenuActivity.this,
                                                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                            ActivityCompat.requestPermissions(MenuActivity.this,
                                                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                                        } else {
                                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: "+ st9));
                                            startActivity(intent);
                                        }
                                    }
                                    else if (item == 9)
                                    // else if (stringArray.toString().contains("2"))
                                    {
                                        if (ContextCompat.checkSelfPermission(MenuActivity.this,
                                                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                            ActivityCompat.requestPermissions(MenuActivity.this,
                                                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                                        } else {
                                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: "+ st10));
                                            startActivity(intent);
                                        }
                                    }
                                    else if (item == 10)
                                    // else if (stringArray.toString().contains("2"))
                                    {
                                        if (ContextCompat.checkSelfPermission(MenuActivity.this,
                                                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                            ActivityCompat.requestPermissions(MenuActivity.this,
                                                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                                        } else {
                                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: "+ st11));
                                            startActivity(intent);
                                        }
                                    }
                                    else if (item == 11)
                                    // else if (stringArray.toString().contains("2"))
                                    {
                                        if (ContextCompat.checkSelfPermission(MenuActivity.this,
                                                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                            ActivityCompat.requestPermissions(MenuActivity.this,
                                                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                                        } else {
                                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: "+ st12));
                                            startActivity(intent);
                                        }
                                    }
                                    else if (item == 12)
                                    // else if (stringArray.toString().contains("2"))
                                    {
                                        if (ContextCompat.checkSelfPermission(MenuActivity.this,
                                                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                            ActivityCompat.requestPermissions(MenuActivity.this,
                                                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                                        } else {
                                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: "+ st13));
                                            startActivity(intent);
                                        }

                                    }
                                    else if (item == 13)
                                    // else if (stringArray.toString().contains("2"))
                                    {
                                        if (ContextCompat.checkSelfPermission(MenuActivity.this,
                                                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                            ActivityCompat.requestPermissions(MenuActivity.this,
                                                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                                        } else {
                                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: "+ st14));
                                            startActivity(intent);
                                        }
                                    }
                                    else if (item == 14)
                                    // else if (stringArray.toString().contains("2"))
                                    {
                                        if (ContextCompat.checkSelfPermission(MenuActivity.this,
                                                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                            ActivityCompat.requestPermissions(MenuActivity.this,
                                                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                                        } else {
                                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: "+ st15));
                                            startActivity(intent);
                                        }
                                    }
                                    else if (item == 15)
                                    // else if (stringArray.toString().contains("2"))
                                    {
                                        if (ContextCompat.checkSelfPermission(MenuActivity.this,
                                                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                            ActivityCompat.requestPermissions(MenuActivity.this,
                                                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                                        } else {
                                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: "+ st16));
                                            startActivity(intent);
                                        }
                                    }
                                    else if (item == 16)
                                    // else if (stringArray.toString().contains("2"))
                                    {
                                        if (ContextCompat.checkSelfPermission(MenuActivity.this,
                                                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                            ActivityCompat.requestPermissions(MenuActivity.this,
                                                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                                            builder1.create();
                                        } else {
                                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: "+ st17));
                                            startActivity(intent);
                                        }
                                    }

                                }

                            });

                            builder1.setNegativeButton("Отмена", new DialogInterface.OnClickListener() { // Кнопка Нет
                                @Override
                                public void onClick(DialogInterface dialog1, int which) {
                                    dialog1.cancel(); // Отпускает диалоговое окно
                                }
                            });

                            AlertDialog dialog1 = builder1.create();
                            dialog1.show();
                            dialog1.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.teal_700));
                        }

                        else {
                            AlertDialog.Builder builder2 = new AlertDialog.Builder(v.getContext(),R.style.AlertDialogMy);
                            final String [] items2 = new String[] {"1. Интернет‑служба экстренной психологической помощи МЧС России:\nhttps://psi.mchs.gov.ru/", "2. «Помощь рядом». Поддержка семей и защита прав детей:\nhttps://pomoschryadom.ru/",
                                    "3. Фонд «Твоя территория», специализирующийся на защите прав детей:\nhttps://www.твоятерритория.онлайн/", "4. Фонд «Я родитель». Поддержка детей, находящихся в трудной жизненной ситуации:\nhttps://www.ya-roditel.ru/parents/consultation/",
                                    "5. Психологическая помощь «Ярославна». Бесплатная психологическая консультация для всех желающих:\nhttps://yaroslavna.help/", "6. Онлайн-консультации магистрантов НИУ ВШЭ программы «Психоанализ и психоаналитическая психотерапия»:\nhttps://www.hse.ru/ma/therapy/news/352754028.html",
                                    "7. Гуманитарный проект Ассоциации EMDR России. Бесплатные групповые сессии для всех, кому нужна поддержка:\nhttps://t.me/+IuWFnFLwV6E5Yjgy"};

                            builder2.setTitle("Список сервисов");
                            builder2.setIcon(R.drawable.ic_list1);
                            builder2.setItems(items2, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int item) {

                                    String st = "https://psi.mchs.gov.ru/";
                                    String st2 = "https://pomoschryadom.ru/";
                                    String st3 = "https://www.твоятерритория.онлайн/";
                                    String st4 = "https://www.ya-roditel.ru/parents/consultation/";
                                    String st5 = "https://yaroslavna.help/";
                                    String st6 = "https://www.hse.ru/ma/therapy/news/352754028.html";
                                    String st7 = "https://t.me/+IuWFnFLwV6E5Yjgy";
                                    if (item == 0)
                                    // if (stringArray.toString().equals("1"))
                                    {
                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(st));
                                        startActivity(intent);
                                    } else if (item == 1)
                                    // else if (stringArray.toString().contains("2"))
                                    {

                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(st2));
                                        startActivity(intent);
                                    }
                                    else if (item == 2)
                                    // else if (stringArray.toString().contains("2"))
                                    {

                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(st3));
                                        startActivity(intent);
                                    }
                                    else if (item == 3)
                                    // else if (stringArray.toString().contains("2"))
                                    {

                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(st4));
                                        startActivity(intent);
                                    }
                                    else if (item == 4)
                                    // else if (stringArray.toString().contains("2"))
                                    {

                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(st5));
                                        startActivity(intent);
                                    }
                                    else if (item == 5)
                                    // else if (stringArray.toString().contains("2"))
                                    {

                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(st6));
                                        startActivity(intent);
                                    }
                                    else if (item == 6)
                                    // else if (stringArray.toString().contains("2"))
                                    {

                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(st7));
                                        startActivity(intent);
                                    }

                                }
                            });


                            builder2.setNegativeButton("Отмена", new DialogInterface.OnClickListener() { // Кнопка Нет
                                @Override
                                public void onClick(DialogInterface dialog2, int which) {
                                    dialog2.cancel(); // Отпускает диалоговое окно
                                }
                            });
                            AlertDialog dialog2 = builder2.create();
                            dialog2.show();

                            dialog2.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.teal_700));
                        }

                    }
                });

                builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() { // Кнопка Нет
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel(); // Отпускает диалоговое окно
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.teal_700));
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.teal_700));
            }

        });


        binding.mikrofonVvod.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.mikrofonVvod.getText().toString().equals("ОТКРОЙ ЗАМЕТКИ")) {
                    Toast.makeText(MenuActivity.this,"Перенаправляем вас на страницу с заметками",Toast.LENGTH_SHORT).show();
                    replaceFragment(new NotesFragment(), bundle);
                    binding.all.setBackgroundColor(getResources().getColor(R.color.teal_phone));
                }
                else if (binding.mikrofonVvod.getText().toString().equals("ОТКРОЙ КАЛЕНДАРЬ")) {
                    Toast.makeText(MenuActivity.this,"Перенаправляем вас на страницу с календарем",Toast.LENGTH_SHORT).show();
                    replaceFragment(new CalendarFragment(), bundle);
                    binding.all.setBackgroundColor(getResources().getColor(R.color.teal_phone));
                }
                else if (binding.mikrofonVvod.getText().toString().equals("ОТКРОЙ СТАТИСТИКУ")) {
                    Toast.makeText(MenuActivity.this,"Перенаправляем вас на страницу со статистикой",Toast.LENGTH_SHORT).show();
                    replaceFragment(new StatisticsFragment(), bundle);
                    binding.all.setBackgroundColor(Color.WHITE);
                }
                else if (binding.mikrofonVvod.getText().toString().equals("ОТКРОЙ ТЕСТЫ")) {
                    Toast.makeText(MenuActivity.this,"Перенаправляем вас на страницу с тестами",Toast.LENGTH_SHORT).show();
                    replaceFragment(new TestsFragment(), bundle);
                    binding.all.setBackgroundColor(Color.WHITE);
                }
                else if (binding.mikrofonVvod.getText().toString().equals("ОТКРОЙ ТЕСТ НА ДИАГНОСТИКУ НЕВРОЗА")) {
                    Toast.makeText(MenuActivity.this,"Перенаправляем вас на страницу с тестом Хека-Хесс",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MenuActivity.this, InfoTestActivity.class);
                    intent.putExtra("access", access);
                    intent.putExtra("test_id", 3);
                    intent.putExtra("golos","yes");
                    startActivity(intent);
                    binding.all.setBackgroundColor(Color.WHITE);
                }
                else if (binding.mikrofonVvod.getText().toString().equals("ОТКРОЙ ТЕСТ НА ДИАГНОСТИКУ ТРЕВОГИ")) {
                    Toast.makeText(MenuActivity.this,"Перенаправляем вас на страницу с тестом А.Бека",Toast.LENGTH_SHORT).show();
                    Intent intent2 = new Intent(MenuActivity.this, InfoTestActivity.class);
                    intent2.putExtra("access", access);
                    intent2.putExtra("test_id", 2);
                    intent2.putExtra("golos","yes");
                    startActivity(intent2);
                    binding.all.setBackgroundColor(Color.WHITE);
                }
                else if (binding.mikrofonVvod.getText().toString().equals("ОТКРОЙ ТЕСТ НА ОЦЕНКУ ЭМОЦИОНАЛЬНОГО ИНТЕЛЛЕКТА")) {
                    Toast.makeText(MenuActivity.this,"Перенаправляем вас на страницу с тестом Г.С.Холла",Toast.LENGTH_SHORT).show();
                    Intent intent3 = new Intent(MenuActivity.this, InfoTestActivity.class);
                    intent3.putExtra("access", access);
                    intent3.putExtra("test_id", 4);
                    intent3.putExtra("golos","yes");
                    startActivity(intent3);
                    binding.all.setBackgroundColor(Color.WHITE);
                }
                else if (binding.mikrofonVvod.getText().toString().equals("ОТКРОЙ ТЕСТ НА ОЦЕНКУ СОЗАВИСИМОСТИ")) {
                    Toast.makeText(MenuActivity.this,"Перенаправляем вас на страницу с тестом Уайнхолд",Toast.LENGTH_SHORT).show();
                    Intent intent4 = new Intent(MenuActivity.this, InfoTestActivity.class);
                    intent4.putExtra("access", access);
                    intent4.putExtra("test_id", 5);
                    intent4.putExtra("golos","yes");
                    startActivity(intent4);
                    binding.all.setBackgroundColor(Color.WHITE);
                }
                else if (binding.mikrofonVvod.getText().toString().equals("ОТКРОЙ ТЕСТ НА ДИАГНОСТИКУ ДЕПРЕССИИ")) {
                    Toast.makeText(MenuActivity.this,"Перенаправляем вас на страницу с тестом Цунга",Toast.LENGTH_SHORT).show();
                    Intent intent5 = new Intent(MenuActivity.this, InfoTestActivity.class);
                    intent5.putExtra("access", access);
                    intent5.putExtra("test_id", 2);
                    intent5.putExtra("golos","yes");
                    startActivity(intent5);
                    binding.all.setBackgroundColor(Color.WHITE);
                }
                else if (binding.mikrofonVvod.getText().toString().equals("ОТКРОЙ ТЕСТ НА ОЦЕНКУ УРОВНЯ ОБЩИТЕЛЬНОСТИ")) {
                    Toast.makeText(MenuActivity.this,"Перенаправляем вас на страницу с тестом В.Ф.Ряховского",Toast.LENGTH_SHORT).show();
                    Intent intent6 = new Intent(MenuActivity.this, InfoTestActivity.class);
                    intent6.putExtra("access", access);
                    intent6.putExtra("test_id", 6);
                    intent6.putExtra("golos","yes");
                    startActivity(intent6);
                    binding.all.setBackgroundColor(Color.WHITE);
                }
                else if (binding.mikrofonVvod.getText().toString().equals("ДОБАВЬ ЗАМЕТКУ")) {
                    Toast.makeText(MenuActivity.this,"Перенаправляем вас на страницу для добавления заметок",Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(MenuActivity.this, NewNoteActivity.class);
                    intent1.putExtra("access", access);
                    intent1.putExtra("auto", "no");
                    startActivity(intent1);
                }
                else {
                    try {

                    ArrayList<String> nastroenies = new ArrayList<String>();
                    nastroenies.add("ОТЛИЧНОЕ");
                    nastroenies.add("ХОРОШЕЕ");
                    nastroenies.add("НОРМАЛЬНОЕ");
                    nastroenies.add("ПЛОХОЕ");
                    nastroenies.add("УЖАСНОЕ");
                    ArrayList<String> activities = new ArrayList<String>();
                    activities.add("РАБОТА");
                    activities.add("УЧЁБА");
                    activities.add("СОН");
                    activities.add("ОТДЫХ");
                    activities.add("ТЕЛЕВИЗОР");
                    activities.add("ФИЛЬМЫ");
                    activities.add("СВИДАНИЯ");
                    activities.add("ДРУЗЬЯ");
                    activities.add("СЕМЬЯ");
                    activities.add("СПОРТ");
                    activities.add("УБОРКА");
                    activities.add("ЧТЕНИЕ");
                    activities.add("ИГРЫ");
                    activities.add("МУЗЫКА");
                    activities.add("ПОКУПКИ");
                    activities.add("ПУТЕШЕСТВИЯ");
                    activities.add("ВЕЧЕРИНКИ");
                    activities.add("БАР");


                    ArrayList<String> hours = new ArrayList<String>();
                    hours.add("0");
                    hours.add("1");
                    hours.add("2");
                    hours.add("3");
                    hours.add("4");
                    hours.add("5");
                    hours.add("6");
                    hours.add("7");
                    hours.add("8");
                    hours.add("9");
                    hours.add("10");
                    hours.add("11");
                    hours.add("12");
                    hours.add("13");
                    hours.add("14");
                    hours.add("15");
                    hours.add("16");
                    hours.add("17");
                    hours.add("18");
                    hours.add("19");
                    hours.add("20");
                    hours.add("21");
                    hours.add("22");
                    hours.add("23");

                    ArrayList<String> mins = new ArrayList<String>();
                    mins.add("0");
                    mins.add("1");
                    mins.add("2");
                    mins.add("3");
                    mins.add("4");
                    mins.add("5");
                    mins.add("6");
                    mins.add("7");
                    mins.add("8");
                    mins.add("9");
                    mins.add("10");
                    mins.add("11");
                    mins.add("12");
                    mins.add("13");
                    mins.add("14");
                    mins.add("15");
                    mins.add("16");
                    mins.add("17");
                    mins.add("18");
                    mins.add("19");
                    mins.add("20");
                    mins.add("21");
                    mins.add("22");
                    mins.add("23");
                    mins.add("24");
                    mins.add("25");
                    mins.add("25");
                    mins.add("27");
                    mins.add("28");
                    mins.add("29");
                    mins.add("30");
                    mins.add("31");
                    mins.add("32");
                    mins.add("33");
                    mins.add("34");
                    mins.add("35");
                    mins.add("36");
                    mins.add("37");
                    mins.add("38");
                    mins.add("39");
                    mins.add("40");
                    mins.add("41");
                    mins.add("42");
                    mins.add("43");
                    mins.add("44");
                    mins.add("45");
                    mins.add("46");
                    mins.add("47");
                    mins.add("48");
                    mins.add("49");
                    mins.add("50");
                    mins.add("51");
                    mins.add("52");
                    mins.add("53");
                    mins.add("54");
                    mins.add("55");
                    mins.add("56");
                    mins.add("57");
                    mins.add("58");
                    mins.add("59");
                    String currentNastroi = "";
                    ArrayList<String> currentActivitys = new ArrayList<String>();
                    ArrayList<String> currentHours = new ArrayList<String>();
                    ArrayList<String> currentMins = new ArrayList<String>();
                    Log.e("Строка с записи", charSequence.toString());
                    String[] words = charSequence.toString().split(" ");
                    int countActiv=0;
                    if (words.length > 8) {
                        for (String elementNastroi : nastroenies) {
                            if (words[3].equals(elementNastroi)) {
                                currentNastroi = elementNastroi;
                                break;
                            }
                        }

                        for (int count = 4; count< words.length; count = count + 5) {
                            countActiv++;

                            for (String elementActivities : activities) {
                                if (words[count].equals(elementActivities)) {
                                    currentActivitys.add(elementActivities);
                                    break;
                                }
                            }
                            for (String elemenHours : hours) {
                                if (words[count+1].equals(elemenHours)) {
                                    currentHours.add(elemenHours);
                                    break;
                                }
                            }
                            for (String elementMins : mins) {
                                if (words[count+3].equals(elementMins)) {
                                    currentMins.add(elementMins);
                                    break;
                                }
                            }
                        }
                    }

                    String itog="ДОБАВЬ ЗАМЕТКУ НАСТРОЕНИЕ "+currentNastroi+" ";

                    for(int k =0;k<countActiv;k++){
                         itog += currentActivitys.get(k) + " " + currentHours.get(k) + " ЧАСОВ " + currentMins.get(k) + " МИНУТ ";
                        // itog1 += currentActivitys.get(k) + " " + currentHours.get(k) + " "+ЧАСОВ+" " + currentMins.get(k) + " "+МИНУТ+" ";

                    }

                    Log.e("Попробую строка",charSequence.toString());
                    Log.e("Попробую несколько",itog);
                    String otredakt=charSequence.toString().replace("МИНУТЫ","МИНУТ");
                    otredakt=otredakt.replace("МИНУТА","МИНУТ");
                    otredakt=otredakt.replace("ЧАСА","ЧАСОВ");
                    otredakt=otredakt.replace("ЧАС ","ЧАСОВ ");
                    Log.e("Попробую строка",otredakt.toString());
                    if ((otredakt+" ").equals(itog)) {
                        Toast.makeText(MenuActivity.this,"Сейчас будет создана заметка!",Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(MenuActivity.this, NewNoteActivity.class);
                        intent1.putExtra("access", access);

                        intent1.putStringArrayListExtra("activity", currentActivitys);
                        intent1.putStringArrayListExtra("hours", currentHours);
                        intent1.putStringArrayListExtra("mins", currentMins);
                        intent1.putExtra("auto", "yes");
                        intent1.putExtra("nastroenie", currentNastroi);
                        startActivity(intent1);
                    }
                    else
                        Toast.makeText(MenuActivity.this,"Мы не распознали вашу команду",Toast.LENGTH_SHORT).show();

                }
                    catch (Exception e){
                        Toast.makeText(MenuActivity.this,"Мы не распознали вашу команду",Toast.LENGTH_SHORT).show();
                    }
                }

            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        binding.bottomNavigationView.setSelectedItemId(R.id.Date);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {


            switch (item.getItemId()){
                case R.id.Date: {
                   // binding.viewPager.setVisibility(View.INVISIBLE);
                    replaceFragment(new CalendarFragment(), bundle);
                    binding.all.setBackgroundColor(getResources().getColor(R.color.teal_phone));
                    break;
                }
                case R.id.Notes: {

                    replaceFragment(new NotesFragment(), bundle);
                    binding.all.setBackgroundColor(getResources().getColor(R.color.teal_phone));
                    break;
                }
                case R.id.Statistics:
                   // binding.viewPager.setVisibility(View.INVISIBLE);

                    replaceFragment(new StatisticsFragment(),bundle);
                    binding.all.setBackgroundColor(Color.WHITE);
                    break;
                case R.id.Test:
                    //setupViewPagerAdapterTests(binding.viewPager);
                  //  binding.viewPager.setVisibility(View.INVISIBLE);
                    replaceFragment(new TestsFragment(),bundle);
                    binding.all.setBackgroundColor(getResources().getColor(R.color.teal_phone));
                    break;
                case R.id.NewNote:
                   // binding.viewPager.setVisibility(View.INVISIBLE);
                    Intent intent1 = new Intent(MenuActivity.this,NewNoteActivity.class) ;
                    intent1.putExtra("access", access);
                    intent1.putExtra("auto", "no");
                    startActivity(intent1);
                    break;
            }

            return true;
        });

    }

    private void replaceFragment(Fragment fragment,Bundle bundle){
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onNoteListener(Intent intent) {
        replaceFragment(new NotesFragment(), bundle);
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
            binding.mikrofonVvod.setText("error " + error);
            Toast.makeText(MenuActivity.this, "Вас плохо слышно",Toast.LENGTH_SHORT).show();
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
            String upperString = data.get(0).toString().toUpperCase();
            binding.mikrofonVvod.setText(String.valueOf(upperString));
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