package com.example.grifmood;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.CheckedOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;


public class AdapterAnswer extends RecyclerView.Adapter<AdapterAnswer.HolderOneTest> {
    private String access;
    private Context context;
    private  int number__question;
    private int current_answer;
    public java.util.ArrayList<AnswerResponce> ArrayList;
    private  float pointsForQuest=-2;
    private com.example.grifmood.databinding.ActivityOneAnswerBinding binding;
    java.util.ArrayList <Integer> ListId = new java.util.ArrayList<>();
    private int currentPosition = -1;
    boolean OnOrOff=true;
    private  OnAnswerListener onAnswerListener;


    public AdapterAnswer(Context context, java.util.ArrayList<AnswerResponce> arrayList, String access,int number__question) {
        this.context = context;
        this.ArrayList = arrayList;
        this.access=access;
        this.number__question=number__question;
        try {
            this.onAnswerListener = ((OnAnswerListener)context);
        }
        catch (ClassCastException e){
            throw new ClassCastException(e.getMessage());
        }
    }

    public AdapterAnswer.HolderOneTest onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = com.example.grifmood.databinding.ActivityOneAnswerBinding.inflate(LayoutInflater.from(context),parent,false);

        return new AdapterAnswer.HolderOneTest(binding.getRoot());
    }



    @Override
    public void onBindViewHolder(AdapterAnswer.HolderOneTest holder, int position) {
        //из листа всех квартир берем один конкретный экземпляр
        AnswerResponce model = ArrayList.get(position);
        //заполнение переменных каждого экз-ра на форме
        ListId.add(model.getId());
        int answer_id = model.getId();
        String answer = model.getAnswer();
        float points = model.getPoints();

        holder.button.setText(answer.toString());
        //запись значений в поля textbox
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(OnOrOff==true){
                    holder.button.setBackgroundResource(R.drawable.selected_answer);
                    OnOrOff=false;
                    current_answer=position;
                    pointsForQuest=points;
                    Intent intent2 = new Intent();
                    intent2.putExtra("points",pointsForQuest);
                    intent2.putExtra("otvet",OnOrOff);
                    onAnswerListener.onAnswerListener(intent2);

                }
                else if(position==current_answer){
                    pointsForQuest=-pointsForQuest;
                    holder.button.setBackgroundResource(R.drawable.round_option_back);
                    OnOrOff=true;
                    current_answer=-1;
                    Intent intent2 = new Intent();
                    intent2.putExtra("points",pointsForQuest);
                    intent2.putExtra("otvet",OnOrOff);
                    onAnswerListener.onAnswerListener(intent2);
                }

            }
        });

    }

    public void setItemSelected(int position){
        int tmp = currentPosition;
        currentPosition = position;
        if (tmp != -1) notifyItemChanged(tmp); // восстанавливаем цвет ранее выделенного
        if (position != -1) notifyItemChanged(position); // выделяем новый
    }


    @Override
    public int getItemCount() {
        return ArrayList.size();
    }

    public void setOnItemClickListener() {
    }


    class HolderOneTest extends RecyclerView.ViewHolder{


        androidx.appcompat.widget.AppCompatButton button;

        public HolderOneTest( View itemView) {
            super(itemView);
            button=binding.option1;

        }
    }
    public int get_number__question(){
        return number__question;
    }
    public float get_pointsForQuest(){
        Log.d("Для суммы баллов",String.valueOf(pointsForQuest));
        return pointsForQuest;
    }

    public int getCurrent_answer() {
        return current_answer;
    }

    public boolean isOnOrOff() {
        return OnOrOff;
    }

    public  interface  OnAnswerListener{
        public void onAnswerListener(Intent intent);
    }
}