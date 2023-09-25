package com.example.grifmood;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterResultsTests  extends RecyclerView.Adapter<AdapterResultsTests.HolderOneResult> {

    private String access;
    private Context context;
    public java.util.ArrayList<ResultTestResponce> ArrayList;
    private com.example.grifmood.databinding.ActivityOneResultTestBinding binding;
    java.util.ArrayList <Integer> ListId = new java.util.ArrayList<>();

    public AdapterResultsTests(Context context, java.util.ArrayList<ResultTestResponce> arrayList,String access) {
        this.context = context;
        this.ArrayList = arrayList;
        this.access=access;
    }

    public AdapterResultsTests.HolderOneResult onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = com.example.grifmood.databinding.ActivityOneResultTestBinding.inflate(LayoutInflater.from(context),parent,false);

        return new AdapterResultsTests.HolderOneResult(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(AdapterResultsTests.HolderOneResult holder, int position) {
        //из листа всех квартир берем один конкретный экземпляр


        ResultTestResponce model = ArrayList.get(position);
        //заполнение переменных каждого экз-ра на форме
        ListId.add(model.getId());
        int test_id = model.getId();
        String score = String.valueOf(model.getScore());
        String description = model.getDescription();
        Integer test_completion_time = model.getTest_completion_time();
        String date = model.getDate();
        Log.e("Проверка даты",date);
        String[]words = date.split("-");
        Log.e("Проверка даты2",words[0]);
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

        holder.Date.setText(day+" "+month+" "+ year+"       "+hour+":"+mins);
        //запись значений в поля textbox
        holder.TestDescription.setText(description);
        holder.score.setText("Балл: "+score);
        Integer min = test_completion_time/60;
        Integer sec = test_completion_time%60;
        holder.TestCompletionTime.setText("Время прохождения: "+min.toString()+" мин "+sec.toString()+" сек.");

    }

    public  void showPopup(View v,int id_test,String access){
        PopupMenu popupMenu=new PopupMenu(context,v);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.results:
                        Intent intentPut = new Intent(context,ResultsTestsForProfileActivity.class);
                        intentPut.putExtra("id_test", id_test);
                        intentPut.putExtra("access", access);
                        context.startActivity(intentPut);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.inflate(R.menu.spisok_menu);
        popupMenu.show();
    }

    @Override
    public int getItemCount() {
        return ArrayList.size();
    }

    class HolderOneResult extends RecyclerView.ViewHolder{

        TextView TestDescription,TestCompletionTime,Date,score;

        androidx.cardview.widget.CardView cardView;
        public HolderOneResult( View itemView) {
            super(itemView);
            cardView=binding.cardView;
            TestDescription=binding.desRes;
            TestCompletionTime=binding.resTime;
            Date=binding.DateRes;
            score=binding.score;
        }
    }
}
