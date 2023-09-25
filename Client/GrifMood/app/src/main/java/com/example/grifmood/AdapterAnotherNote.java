package com.example.grifmood;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grifmood.databinding.ActivityOneNoteBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterAnotherNote extends  RecyclerView.Adapter<AdapterAnotherNote.HolderOneAnotherNote> implements Filterable  {


    private String access;
    private Context context;
    public java.util.ArrayList<ConditionResponse> ArrayList,filterList;
    private ActivityOneNoteBinding binding;
    java.util.ArrayList <Integer> ListId = new ArrayList<>();
    private  FilterNote filter;

    //фрагмент одной кв.
    public AdapterAnotherNote(Context context, java.util.ArrayList<ConditionResponse> pdfArrayList, String access) {
        this.context = context;
        this.ArrayList = pdfArrayList;
        this.access=access;

        this.filterList =ArrayList;
    }


    @Override
    public HolderOneAnotherNote onCreateViewHolder( ViewGroup parent, int viewType) {
        binding = ActivityOneNoteBinding.inflate(LayoutInflater.from(context),parent,false);

        this.context=parent.getContext();
        return new HolderOneAnotherNote(binding.getRoot());
    }
    public  void showPopup(View v,int idCondition,int position){
        PopupMenu popupMenu=new PopupMenu(context,v);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.change:
                        Intent intentPut = new Intent(context,UpdateNoteActivity.class);
                        intentPut.putExtra("idCondition", idCondition);
                        intentPut.putExtra("access", access);
                        context.startActivity(intentPut);

                        return true;
                    case R.id.delete1:
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setTitle("Вы точно хотите удалить заметку?").setMessage("");
                        builder.setPositiveButton("ДА", new DialogInterface.OnClickListener() { // Кнопка Да
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(v.getContext(), "Заметка успешно удалена!", Toast.LENGTH_SHORT).show();
                                Call<ConditionResponse> call1 = ApiClient.DeleteConditionService().deleteConditions(idCondition);
                                call1.enqueue(new Callback<ConditionResponse>() {
                                    @Override
                                    public void onResponse(Call<ConditionResponse> call, Response<ConditionResponse> response) {
                                        Log.d("Norm", response.headers().toString());
                                        Log.d("Norm", response.toString());
                                        if (response.isSuccessful()) {
                                            Intent intent2 = new Intent();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ConditionResponse> call, Throwable t) {
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
    public void onBindViewHolder( HolderOneAnotherNote holder, int position) {
        //из листа всех квартир берем один конкретный экземпляр
        ConditionResponse model = ArrayList.get(position);
        //заполнение переменных каждого экз-ра на форме
        ListId.add(model.getId());
        Integer assesment = model.getAssessment();
        String description = model.getDescription();
        String date = model.getDate();
        Log.e("data",date);
        String[]words = date.split("-");
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

        String nastroi="";
        if(assesment==5){
            nastroi="Прекрасно!";
            holder.circleImageView.setImageResource(R.drawable.kaif);
        }
        else if(assesment==4){
            nastroi="Хорошо!";
            holder.circleImageView.setImageResource(R.drawable.good);
        }
        else if(assesment==3){
            nastroi="Нормально!";
            holder.circleImageView.setImageResource(R.drawable.sred);
        }
        else if(assesment==2){
            nastroi="Плохо!";
            holder.circleImageView.setImageResource(R.drawable.bad);
        }
        else if(assesment==1){
            nastroi="Ужасно!";
            holder.circleImageView.setImageResource(R.drawable.verybad);
        }

        //запись значений в поля textbox
        holder.NoteDescription.setText(description);
        holder.NoteDate.setText(day+" "+month+" "+ year+"       "+hour+":"+mins);
        holder.NoteAssesment.setText(nastroi);
        ArrayList<ImageView> images=new ArrayList<>();
        images.add(holder.imageView1);
        images.add(holder.imageView2);
        images.add(holder.imageView3);
        images.add(holder.imageView4);
        images.add(holder.imageView5);
        images.add(holder.imageView6);
        images.add(holder.imageView7);
        images.add(holder.imageView8);
        images.add(holder.imageView9);
        images.add(holder.imageView10);
        images.add(holder.imageView11);
        images.add(holder.imageView12);
        images.add(holder.imageView13);
        images.add(holder.imageView14);
        images.add(holder.imageView15);
        images.add(holder.imageView16);
        images.add(holder.imageView17);
        images.add(holder.imageView18);

        if(model.getAssessment()>0) {
            ArrayList<String>uzje=new ArrayList<>();
            if (model.getWork() != 0) {

                uzje.add("Работа");
                //holder.imageView1.setImageDrawable(tintIcon(context, icon, R.color.teal_700));
            }

            if (model.getLeisure() != 0) {

                uzje.add("Отдых");
                //  holder.imageView2.setImageDrawable(tintIcon(context, icon, R.color.teal_700));
            }
            if (model.getSleep() != 0) {

                uzje.add("Сон");
                //  holder.imageView3.setImageDrawable(tintIcon(context, icon, R.color.teal_700));
            }
            if (model.getWorkout() != 0) {

                uzje.add("Спорт");
                // holder.imageView4.setImageDrawable(tintIcon(context, icon, R.color.teal_700));
            }
            if (model.getStudy() != 0) {

                uzje.add("Учеба");
                //  holder.imageView5.setImageDrawable(tintIcon(context, icon, R.color.teal_700));
            }
            if (model.getFamily() != 0) {

                uzje.add("Семья");
                //  holder.imageView6.setImageDrawable(tintIcon(context, icon, R.color.teal_700));
            }
            if (model.getFriend() != 0) {

                uzje.add("Друзья");
                // holder.imageView7.setImageDrawable(tintIcon(context, icon, R.color.teal_700));
            }
            if (model.getRendezvous() != 0) {

                uzje.add("Свидания");
                //   holder.imageView8.setImageDrawable(tintIcon(context, icon, R.color.teal_700));
            }
            if (model.getGaming() != 0) {

                uzje.add("Игры");
                //  holder.imageView9.setImageDrawable(tintIcon(context, icon, R.color.teal_700));
            }

            if (model.getReading() != 0) {

                uzje.add("Чтение");
                //  holder.imageView10.setImageDrawable(tintIcon(context, icon, R.color.teal_700));
            }
            if (model.getMusic() != 0) {

                uzje.add("Музыка");
                // holder.imageView11.setImageDrawable(tintIcon(context, icon, R.color.teal_700));
            }
            if (model.getMovie() != 0) {

                uzje.add("Фильмы");

            }
            if (model.getTV() != 0) {

                uzje.add("Телевизор");
                //  holder.imageView13.setImageDrawable(tintIcon(context, icon, R.color.teal_700));

            }
            if (model.getParty() != 0) {

                uzje.add("Вечеринки");
                //   holder.imageView14.setImageDrawable(tintIcon(context, icon, R.color.teal_700));

            }
            if (model.getBar() != 0) {

                uzje.add("Бар");
                //  holder.imageView15.setImageDrawable(tintIcon(context, icon, R.color.teal_700));

            }
            if (model.getTravel() != 0) {

                uzje.add("Путешествия");
                //  holder.imageView16.setImageDrawable(tintIcon(context, icon, R.color.teal_700));

            }
            if (model.getCleaning() != 0) {

                uzje.add("Уборка");
                // holder.imageView17.setImageDrawable(tintIcon(context, icon, R.color.teal_700));

            }
            if (model.getShopping() != 0) {

                uzje.add("Покупки");
                //   holder.imageView18.setImageDrawable(tintIcon(context, icon, R.color.teal_700));

            }



            Drawable icon = null;
            if(uzje.size()==0){
                Glide.with(context).clear(holder.imageView1);
                holder.imageView1.setImageDrawable(null);
                Glide.with(context).clear(holder.imageView2);
                holder.imageView2.setImageDrawable(null);
                Glide.with(context).clear(holder.imageView3);
                holder.imageView3.setImageDrawable(null);
                Glide.with(context).clear(holder.imageView4);
                holder.imageView4.setImageDrawable(null);
                Glide.with(context).clear(holder.imageView5);
                holder.imageView5.setImageDrawable(null);
                Glide.with(context).clear(holder.imageView6);
                holder.imageView6.setImageDrawable(null);
                Glide.with(context).clear(holder.imageView7);
                holder.imageView7.setImageDrawable(null);
                Glide.with(context).clear(holder.imageView8);
                holder.imageView8.setImageDrawable(null);
                Glide.with(context).clear(holder.imageView9);
                holder.imageView9.setImageDrawable(null);
                Glide.with(context).clear(holder.imageView10);
                holder.imageView10.setImageDrawable(null);
                Glide.with(context).clear(holder.imageView11);
                holder.imageView11.setImageDrawable(null);
                Glide.with(context).clear(holder.imageView12);
                holder.imageView12.setImageDrawable(null);
                Glide.with(context).clear(holder.imageView13);
                holder.imageView13.setImageDrawable(null);
                Glide.with(context).clear(holder.imageView14);
                holder.imageView14.setImageDrawable(null);
                Glide.with(context).clear(holder.imageView15);
                holder.imageView15.setImageDrawable(null);
                Glide.with(context).clear(holder.imageView16);
                holder.imageView16.setImageDrawable(null);
                Glide.with(context).clear(holder.imageView17);
                holder.imageView17.setImageDrawable(null);
                Glide.with(context).clear(holder.imageView18);
                holder.imageView18.setImageDrawable(null);
            }
            else {

            }
            for (int b = 0; b < uzje.size(); b++) {
                icon = null;
                if (uzje.get(b).equals("Работа")) {
                    icon = context.getResources().getDrawable(R.drawable.work);

                } else if (uzje.get(b).equals("Отдых")) {
                    icon = context.getResources().getDrawable(R.drawable.leisure);
                    icon=tintIcon(context, icon, R.color.teal_700);

                } else if (uzje.get(b).equals("Сон")) {
                    icon = context.getResources().getDrawable(R.drawable.sleep);
                    icon=tintIcon(context, icon, R.color.teal_700);

                    //  holder.imageView3.setImageDrawable(tintIcon(context, icon, R.color.teal_700));

                } else if (uzje.get(b).equals("Спорт")) {
                    icon = context.getResources().getDrawable(R.drawable.workout);
                    icon=tintIcon(context, icon, R.color.teal_700);

                } else if (uzje.get(b).equals("Учеба")) {
                    icon = context.getResources().getDrawable(R.drawable.study);
                    icon=tintIcon(context, icon, R.color.teal_700);

                } else if (uzje.get(b).equals("Семья")) {
                    icon = context.getResources().getDrawable(R.drawable.family);
                    icon=tintIcon(context, icon, R.color.teal_700);

                } else if (uzje.get(b).equals("Друзья")) {
                    icon = context.getResources().getDrawable(R.drawable.friends);
                    icon=tintIcon(context, icon, R.color.teal_700);

                } else if (uzje.get(b).equals("Свидания")) {
                    icon = context.getResources().getDrawable(R.drawable.rendezvous);
                    icon=tintIcon(context, icon, R.color.teal_700);

                } else if (uzje.get(b).equals("Игры")) {
                    icon = context.getResources().getDrawable(R.drawable.game);
                    icon=tintIcon(context, icon, R.color.teal_700);

                } else if (uzje.get(b).equals("Чтение")) {
                    icon = context.getResources().getDrawable(R.drawable.reading);
                    icon=tintIcon(context, icon, R.color.teal_700);

                } else if (uzje.get(b).equals("Музыка")) {
                    icon = context.getResources().getDrawable(R.drawable.music);
                    icon=tintIcon(context, icon, R.color.teal_700);

                } else if (uzje.get(b).equals("Фильмы")) {
                    icon = context.getResources().getDrawable(R.drawable.movie);
                    icon=tintIcon(context, icon, R.color.teal_700);
                } else if (uzje.get(b).equals("Телевизор")) {
                    icon = context.getResources().getDrawable(R.drawable.tv);
                    icon=tintIcon(context, icon, R.color.teal_700);

                } else if (uzje.get(b).equals("Вечеринки")) {
                    icon = context.getResources().getDrawable(R.drawable.party);
                    icon=tintIcon(context, icon, R.color.teal_700);

                } else if (uzje.get(b).equals("Бар")) {
                    icon = context.getResources().getDrawable(R.drawable.bar);
                    icon=tintIcon(context, icon, R.color.teal_700);

                } else if (uzje.get(b).equals("Путешествия")) {
                    icon = context.getResources().getDrawable(R.drawable.travel);
                    icon=tintIcon(context, icon, R.color.teal_700);

                } else if (uzje.get(b).equals("Уборка")) {
                    icon = context.getResources().getDrawable(R.drawable.cleaning);
                    icon=tintIcon(context, icon, R.color.teal_700);

                } else if (uzje.get(b).equals("Покупки")) {
                    icon = context.getResources().getDrawable(R.drawable.shopping);
                    icon=tintIcon(context, icon, R.color.teal_700);

                }

                holder.imageView1.setImageDrawable(null);
                holder.imageView2.setImageDrawable(null);
                holder.imageView3.setImageDrawable(null);
                holder.imageView4.setImageDrawable(null);
                holder.imageView5.setImageDrawable(null);
                holder.imageView6.setImageDrawable(null);
                holder.imageView7.setImageDrawable(null);
                holder.imageView8.setImageDrawable(null);
                holder.imageView9.setImageDrawable(null);
                holder.imageView10.setImageDrawable(null);
                holder.imageView11.setImageDrawable(null);
                holder.imageView12.setImageDrawable(null);
                holder.imageView13.setImageDrawable(null);
                holder.imageView14.setImageDrawable(null);
                holder.imageView15.setImageDrawable(null);
                holder.imageView16.setImageDrawable(null);
                holder.imageView17.setImageDrawable(null);
                holder.imageView18.setImageDrawable(null);

                if (b == 0) {
                    // holder.imageView1.setImageDrawable(tintIcon(context, icon, R.color.teal_700));
                    if(uzje.get(b)!=null) {
                        Glide
                                .with(holder.imageView1.getContext())
                                .load(tintIcon(context, icon, R.color.teal_700))
                                .into(holder.imageView1);
                    }
                    else {
                        Glide.with(context).clear(holder.imageView1);
                        holder.imageView1.setImageDrawable(null);
                    }
                }
                if (b == 1) {
                    if(uzje.get(b)!=null) {
                        Glide
                                .with(holder.imageView2.getContext())
                                .load(tintIcon(context, icon, R.color.teal_700))
                                .into(holder.imageView2);
                    }
                    else {
                        Glide.with(context).clear(holder.imageView2);
                        holder.imageView2.setImageDrawable(null);
                    }
                }
                if (b == 2) {
                    if(uzje.get(b)!=null) {
                        Glide
                                .with(holder.imageView3.getContext())
                                .load(tintIcon(context, icon, R.color.teal_700))
                                .into(holder.imageView3);
                    }
                    else {
                        Glide.with(context).clear(holder.imageView3);
                        holder.imageView3.setImageDrawable(null);
                    }
                }
                if (b == 3) {
                    if(uzje.get(b)!=null) {
                        Glide
                                .with(holder.imageView4.getContext())
                                .load(tintIcon(context, icon, R.color.teal_700))
                                .into(holder.imageView4);
                    }
                    else {
                        Glide.with(context).clear(holder.imageView4);
                        holder.imageView4.setImageDrawable(null);
                    }
                }
                if (b == 4) {
                    if(uzje.get(b)!=null) {
                        Glide
                                .with(holder.imageView5.getContext())
                                .load(tintIcon(context, icon, R.color.teal_700))
                                .into(holder.imageView5);
                    }
                    else {
                        Glide.with(context).clear(holder.imageView5);
                        holder.imageView5.setImageDrawable(null);
                    }
                }
                if (b == 5) {
                    if(uzje.get(b)!=null) {
                        Glide
                                .with(holder.imageView6.getContext())
                                .load(tintIcon(context, icon, R.color.teal_700))
                                .into(holder.imageView6);
                    }
                    else {
                        Glide.with(context).clear(holder.imageView6);
                        holder.imageView6.setImageDrawable(null);
                    }
                }
                if (b == 6) {
                    if(uzje.get(b)!=null) {
                        Glide
                                .with(holder.imageView7.getContext())
                                .load(tintIcon(context, icon, R.color.teal_700))
                                .into(holder.imageView7);
                    }
                    else {
                        Glide.with(context).clear(holder.imageView7);
                        holder.imageView7.setImageDrawable(null);
                    }
                }
                if (b == 7) {
                    if(uzje.get(b)!=null) {
                        Glide
                                .with(holder.imageView8.getContext())
                                .load(tintIcon(context, icon, R.color.teal_700))
                                .into(holder.imageView8);
                    }
                    else {
                        Glide.with(context).clear(holder.imageView8);
                        holder.imageView8.setImageDrawable(null);
                    }
                }
                if (b == 8) {
                    if(uzje.get(b)!=null) {
                        Glide
                                .with(holder.imageView9.getContext())
                                .load(tintIcon(context, icon, R.color.teal_700))
                                .into(holder.imageView9);
                    }
                    else {
                        Glide.with(context).clear(holder.imageView9);
                        holder.imageView9.setImageDrawable(null);
                    }
                }
                if (b == 9) {
                    if(uzje.get(b)!=null) {
                        Glide
                                .with(holder.imageView10.getContext())
                                .load(tintIcon(context, icon, R.color.teal_700))
                                .into(holder.imageView10);
                    }
                    else {
                        Glide.with(context).clear(holder.imageView10);
                        holder.imageView10.setImageDrawable(null);
                    }
                }
                if (b == 10) {
                    if(uzje.get(b)!=null) {
                        Glide
                                .with(holder.imageView11.getContext())
                                .load(tintIcon(context, icon, R.color.teal_700))
                                .into(holder.imageView11);
                    }
                    else {
                        Glide.with(context).clear(holder.imageView11);
                        holder.imageView11.setImageDrawable(null);
                    }
                }
                if (b == 11) {
                    if(uzje.get(b)!=null) {
                        Glide
                                .with(holder.imageView12.getContext())
                                .load(tintIcon(context, icon, R.color.teal_700))
                                .into(holder.imageView12);
                    }
                    else {
                        Glide.with(context).clear(holder.imageView12);
                        holder.imageView12.setImageDrawable(null);
                    }
                }
                if (b == 12) {
                    if(uzje.get(b)!=null) {
                        Glide
                                .with(holder.imageView13.getContext())
                                .load(tintIcon(context, icon, R.color.teal_700))
                                .into(holder.imageView13);
                    }
                    else {
                        Glide.with(context).clear(holder.imageView13);
                        holder.imageView13.setImageDrawable(null);
                    }
                }
                if (b == 13) {
                    if(uzje.get(b)!=null) {
                        Glide
                                .with(holder.imageView14.getContext())
                                .load(tintIcon(context, icon, R.color.teal_700))
                                .into(holder.imageView14);
                    }
                    else {
                        Glide.with(context).clear(holder.imageView14);
                        holder.imageView14.setImageDrawable(null);
                    }
                }
                if (b == 14) {
                    if(uzje.get(b)!=null) {
                        Glide
                                .with(holder.imageView15.getContext())
                                .load(tintIcon(context, icon, R.color.teal_700))
                                .into(holder.imageView15);
                    }
                    else {
                        Glide.with(context).clear(holder.imageView15);
                        holder.imageView15.setImageDrawable(null);
                    }
                }
                if (b == 15) {
                    if(uzje.get(b)!=null) {
                        Glide
                                .with(holder.imageView16.getContext())
                                .load(tintIcon(context, icon, R.color.teal_700))
                                .into(holder.imageView16);
                    }
                    else {
                        Glide.with(context).clear(holder.imageView16);
                        holder.imageView16.setImageDrawable(null);
                    }
                }
                if (b == 16) {
                    if(uzje.get(b)!=null) {
                        Glide
                                .with(holder.imageView17.getContext())
                                .load(tintIcon(context, icon, R.color.teal_700))
                                .into(holder.imageView17);
                    }
                    else {
                        Glide.with(context).clear(holder.imageView17);
                        holder.imageView17.setImageDrawable(null);
                    }
                }
                if (b == 17) {
                    if(uzje.get(b)!=null) {
                        Glide
                                .with(holder.imageView18.getContext())
                                .load(tintIcon(context, icon, R.color.teal_700))
                                .into(holder.imageView18);
                    }
                    else {
                        Glide.with(context).clear(holder.imageView18);
                        holder.imageView18.setImageDrawable(null);
                    }
                }

            }

        }

    }



    @Override
    public int getItemCount() {
        return ArrayList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter==null){
           // filter = new FilterNote(filterList,this);
        }
        return filter;
    }


    class HolderOneAnotherNote extends RecyclerView.ViewHolder{
        ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9,imageView10,imageView11,imageView12,imageView13,imageView14,imageView15,imageView16,imageView17,imageView18;
        de.hdodenhof.circleimageview.CircleImageView circleImageView;
        TextView NoteDescription,NoteDate,NoteAssesment;
        ImageView imageTv;
        Spinner Settings;

        public HolderOneAnotherNote( View itemView) {
            super(itemView);
            imageView1 = binding.Image1;
            imageView2 = binding.Image2;
            imageView3 = binding.Image3;
            imageView4= binding.Image4;
            imageView5 = binding.Image5;
            imageView6 = binding.Image6;
            imageView7 = binding.Image7;
            imageView8 = binding.Image8;
            imageView9 = binding.Image9;
            imageView10 = binding.Image10;
            imageView11 = binding.Image11;
            imageView12 = binding.Image12;
            imageView13 = binding.Image13;
            imageView14 = binding.Image14;
            imageView15 = binding.Image15;
            imageView16= binding.Image16;
            imageView17 = binding.Image17;
            imageView18 = binding.Image18;
            circleImageView=binding.profileimage;
            NoteAssesment=binding.assesment;
            NoteDate=binding.dataCondition;
            NoteDescription=binding.descriptionCondition;

        }
    }

    public  interface  OnNoteListener{
        public void onNoteListener(Intent intent);
    }

    public static Drawable tintIcon(Context context,  Drawable icon, int color) {
        icon = DrawableCompat.wrap(icon).mutate();
        DrawableCompat.setTintList(icon, ContextCompat.getColorStateList(context, color));
        DrawableCompat.setTintMode(icon, PorterDuff.Mode.SRC_IN);
        return icon;
    }

}

