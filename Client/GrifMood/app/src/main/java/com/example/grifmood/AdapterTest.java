package com.example.grifmood;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdapterTest extends RecyclerView.Adapter<AdapterTest.HolderOneTest> {
    private String access;
    private Context context;
    public java.util.ArrayList<TestResponce> ArrayList;
    private com.example.grifmood.databinding.ActivityOneTestBinding binding;
    java.util.ArrayList <Integer> ListId = new java.util.ArrayList<>();
    public AdapterTest(Context context, java.util.ArrayList<TestResponce> arrayList,String access) {
        this.context = context;
        this.ArrayList = arrayList;
        this.access=access;
    }

    public AdapterTest.HolderOneTest onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = com.example.grifmood.databinding.ActivityOneTestBinding.inflate(LayoutInflater.from(context),parent,false);

        return new AdapterTest.HolderOneTest(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(AdapterTest.HolderOneTest holder, int position) {
        //из листа всех квартир берем один конкретный экземпляр


        TestResponce model = ArrayList.get(position);
        //заполнение переменных каждого экз-ра на форме
        ListId.add(model.getId_test());
        int test_id = model.getId_test();
        String test_name = model.getTest_name();
        String description = model.getDescription();
        Integer test_completion_time = model.getTest_completion_time();
        int count_question = model.getCount_question();

        //запись значений в поля textbox
        String[]words = description.split("\\. ");
        String filename = model.getImage();
        try(InputStream inputStream = context.getApplicationContext().getAssets().open(filename)){
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            holder.imageTv.setImageDrawable(drawable);
        }
        catch (IOException e){
            e.printStackTrace();
        }

        holder.TestDescription.setText(words[0]);
        holder.TestName.setText(test_name);
        holder.TestCompletionTime.setText("~"+test_completion_time.toString()+" мин.");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPut = new Intent(context,InfoTestActivity.class);
                intentPut.putExtra("test_id", test_id);
                intentPut.putExtra("golos", "no");
                intentPut.putExtra("access", access);
                intentPut.putExtra("test_name", test_name);
                intentPut.putExtra("description", description);
                intentPut.putExtra("image",filename);
                intentPut.putExtra("test_completion_time", test_completion_time);
                intentPut.putExtra("count_question", count_question);
                context.startActivity(intentPut);
            }
        });
        //Кнопка Open нажатие

        holder.buttonTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view,test_id,access,test_name);

            }
        });


    }

    public  void showPopup(View v,int id_test,String access,String test_name){
        PopupMenu popupMenu=new PopupMenu(context,v);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.results:
                        Intent intentPut = new Intent(context,ResultsTestsForProfileActivity.class);
                        intentPut.putExtra("id_test", id_test);
                        intentPut.putExtra("access", access);
                        intentPut.putExtra("name_test",test_name);
                        context.startActivity(intentPut);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.inflate(R.menu.spisok_test_results);
        popupMenu.show();
    }


    @Override
    public int getItemCount() {
        return ArrayList.size();
    }

    class HolderOneTest extends RecyclerView.ViewHolder{

        TextView TestName,TestDescription,TestCompletionTime;
        CircleImageView imageTv;
        androidx.cardview.widget.CardView cardView;
        ImageButton buttonTv;
        public HolderOneTest( View itemView) {
            super(itemView);
            cardView=binding.cardView;
            TestName=binding.NameTest;
            TestDescription=binding.TestDes;
            TestCompletionTime=binding.TestTime;
            imageTv=binding.profileimage;
            buttonTv = binding.buttonNext;
        }
    }

}