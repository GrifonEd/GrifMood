package com.example.grifmood;
import androidx.core.content.ContextCompat;
import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grifmood.databinding.ActivityOneMessageBinding;
import com.example.grifmood.databinding.FragmentAllMessagesBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdapterMessage extends RecyclerView.Adapter<AdapterMessage.HolderOneMessage> {
    private String access;
    private Context context;
    private  int number__question;
    private int current_answer;
    public java.util.ArrayList<MessageResponse> ArrayList;
    private  float pointsForQuest=-2;
    private ActivityOneMessageBinding binding;
    java.util.ArrayList <Integer> ListId = new ArrayList<>();
    private OnMessageListener listener;


    public AdapterMessage(Context context, java.util.ArrayList<MessageResponse> arrayList, String access) {
        this.context = context;
        this.ArrayList = arrayList;
        this.access=access;

        try {
            this.listener = ((AdapterMessage.OnMessageListener)context);
        }
        catch (ClassCastException e){
            throw new ClassCastException(e.getMessage());
        }



    }

    public HolderOneMessage onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = ActivityOneMessageBinding.inflate(LayoutInflater.from(context),parent,false);

        this.context=parent.getContext();
        return new HolderOneMessage(binding.getRoot());
    }



    @Override
    public void onBindViewHolder(HolderOneMessage holder, int position) {
        //из листа всех квартир берем один конкретный экземпляр
        MessageResponse model = ArrayList.get(position);
        int currentPosition = ArrayList.size()-position;

        //заполнение переменных каждого экз-ра на форме
        ListId.add(model.getId());
        int answer_id = model.getId();

        String date = model.getDate_created();
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

        holder.fio.setText("Пользователь\n"+model.getProfileSend().getSecond_name()+" "+model.getProfileSend().getFirst_name());
        holder.number.setText(currentPosition+".");
        holder.message.setText(model.getMessage());
        holder.date.setText(day+" "+month+" "+ year+", "+hour+":"+mins);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v.getContext());
                builder.setTitle("Вы точно хотите удалить заметку?").setMessage("");
                builder.setPositiveButton("ДА", new DialogInterface.OnClickListener() { // Кнопка Да
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Call<MessageResponse> call1 = ApiClient.messageServiceDelete().deleteMessage(model.getId());
                        call1.enqueue(new Callback<MessageResponse>() {
                            @Override
                            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                                Log.d("Norm", response.headers().toString());
                                Log.d("Norm", response.toString());
                                if (response.isSuccessful()) {
                                    Intent intent2 = new Intent();
                                      listener.onMessageListener(intent2);

                                    Toast.makeText(v.getContext(), "Заметка успешно удалена!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<MessageResponse> call, Throwable t) {
                                Log.d("Norm", "hm");
                            }
                        });
                    }
                });
                builder.setNegativeButton("нет", new DialogInterface.OnClickListener() { // Кнопка Нет
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Отпускает диалоговое окно
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }




    @Override
    public int getItemCount() {
        return ArrayList.size();
    }

    public void setOnItemClickListener() {
    }


    class HolderOneMessage extends RecyclerView.ViewHolder{


        TextView date,message,number,fio;
        ImageButton delete;
        androidx.appcompat.widget.AppCompatButton button;

        public HolderOneMessage( View itemView) {
            super(itemView);
            fio=binding.textFIO;
            number=binding.numberMessage;
            delete = binding.deleteMessage;
           date=binding.textofrecomm;
           message=binding.messageText;

        }
    }
    public  interface  OnMessageListener{
        public void onMessageListener(Intent intent);
    }

}