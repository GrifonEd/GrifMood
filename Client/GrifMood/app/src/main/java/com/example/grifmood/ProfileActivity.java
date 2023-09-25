package com.example.grifmood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.grifmood.databinding.ActivityProfileBinding;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity implements AdapterMessage.OnMessageListener{
    private ActivityProfileBinding binding;
    String id;
    String access;
    String name = "";
    String second_name = "";
    Integer age;
    String code = "";
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapterMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        access = intent.getStringExtra("access");
        checkAccess(access);
        viewPager = binding.viewPagerMessage;

        binding.anotherProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ProfileActivity.this, AnotherProfileActivity.class);
                intent1.putExtra("access", access);
                intent1.putExtra("profileWatching", id);
                startActivity(intent1);

            }
        });

        binding.offsystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(ProfileActivity.this, MainActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




        binding.invisibleText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String code = binding.invisibleText.getText().toString();
                Call<ConnectionResponse> callConnect = ApiClient.connectionServicePost().ConnectionServicePost(Integer.valueOf(id), code);
                callConnect.enqueue(new Callback<ConnectionResponse>() {
                    @Override
                    public void onResponse(Call<ConnectionResponse> call, Response<ConnectionResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(ProfileActivity.this, "Вы связались с профилем от сканируемого QR-кода", Toast.LENGTH_LONG).show();
                        } else {
                            if (response.code() == 500) {
                                Toast.makeText(ProfileActivity.this, "Вы уже были связаны с данным профилем", Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(ProfileActivity.this, "Действительный QR-код не был обнаружен", Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ConnectionResponse> call, Throwable t) {

                    }
                });

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.btnScan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(ProfileActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();

            }
        });
    }


    public void generateQRCode(String name) {

        QRCodeWriter writer = new QRCodeWriter();
        try {

            BitMatrix bitMatrix = writer.encode(name, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            ((ImageView) findViewById(R.id.QRcode)).setImageBitmap(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Действительный QR-код не был обнаружен", Toast.LENGTH_LONG).show();
            } else {
                //Toast.makeText(this, "Сканирование прошло успешно", Toast.LENGTH_LONG).show();
                binding.invisibleText.setText(result.getContents());
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void checkAccess(String access) {

        Call<AuthorizationResponse> call = ApiClient.AuthorizationUserService().getloginUser(access);
        Log.d("Norm123234235", call.request().header("Authorization").toString());
        call.enqueue(new Callback<AuthorizationResponse>() {
            @Override
            public void onResponse(Call<AuthorizationResponse> call, Response<AuthorizationResponse> response) {
                Log.d("Norm", response.headers().toString());
                Log.d("Norm", response.toString());
                if (response.isSuccessful()) {
                    id = response.body().getId().toString();
                    if (!id.equals("Bad") && !id.equals("")) {
                        loadProfile(id);
                        loadMessages(id);
                    }
                } else {
                    id = "Bad";
                }

            }


            @Override
            public void onFailure(Call<AuthorizationResponse> call, Throwable t) {
                Log.d("Norm", "hm");
                id = "Bad";
                Toast.makeText(ProfileActivity.this, "Проблемы с соединением!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void loadMessages(String id) {
        setupViewPagerAdapter(viewPager);

    }

    private void loadProfile(String id) {
        Call<ProfileResponse> call = ApiClient.profileServiceGet().ProfileServiceGet(Integer.valueOf(id));
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful()) {
                    code = response.body().getUuid();
                    name = response.body().getFirst_name();
                    second_name = response.body().getSecond_name();
                    age = response.body().getAge();
                    generateQRCode(code);
                    binding.uuidProfile.setText(code);

                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {

            }
        });


    }
    private void setupViewPagerAdapter(final ViewPager viewPager){
        //список фрагментов "квартир"
        viewPagerAdapterMessage = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,ProfileActivity.this);

        //его создание
        viewPagerAdapterMessage.addFragment(MessagesAllFragment.newInstance(
                2,
                "",
                1,Integer.valueOf(id)
        ));

        viewPagerAdapterMessage.notifyDataSetChanged();

        viewPager.setAdapter(viewPagerAdapterMessage);
    }



    public class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<MessagesAllFragment> fragmentList = new ArrayList<>();

        private Context context;

        private ArrayList<String> fragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm, int behavior, Context context) {
            super(fm, behavior);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        void addFragment(MessagesAllFragment fragment){
            fragmentList.add(fragment);

        }


        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);

        }

    }

    @Override
    public void onMessageListener(Intent intent) {
        setupViewPagerAdapter(viewPager);
    }

}
