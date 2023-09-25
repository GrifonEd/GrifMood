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

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grifmood.databinding.ActivityOneNoteBinding;
import com.example.grifmood.databinding.ActivityOneProfileBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterProfile extends  RecyclerView.Adapter<AdapterProfile.HolderOneProfile> implements Filterable  {

    private String access;
    private Context context;
    public java.util.ArrayList<ProfileResponse> ArrayList,filterList;
    private ActivityOneProfileBinding binding;
    java.util.ArrayList <Integer> ListId = new ArrayList<>();
    private  FilterNote filter;

    //фрагмент одной кв.
    public AdapterProfile(Context context, java.util.ArrayList<ProfileResponse> pdfArrayList, String access) {
        this.context = context;
        this.ArrayList = pdfArrayList;
        this.access=access;
        this.filterList =ArrayList;
    }


    @Override
    public HolderOneProfile onCreateViewHolder( ViewGroup parent, int viewType) {
        binding = ActivityOneProfileBinding.inflate(LayoutInflater.from(context),parent,false);

        this.context=parent.getContext();
        return new HolderOneProfile(binding.getRoot());
    }


    @Override
    public void onBindViewHolder( HolderOneProfile holder, int position) {
        //из листа всех квартир берем один конкретный экземпляр
        ProfileResponse model = ArrayList.get(position);
        //заполнение переменных каждого экз-ра на форме
        ListId.add(model.getId());
        String name = model.getFirst_name();
        String second_name = model.getSecond_name();
        int age = model.getAge();
        int id=model.getId();

        String gender ="";
        if(model.getSex().equals("Женский"))
            gender="Женщина";
        else
            gender="Мужчина";
        if(age%10==2 || age%10==3 || age%10==4 )
        holder.age.setText(gender+", "+String.valueOf(age) + " года");
        else if(age%10==1)
            holder.age.setText(gender+", "+String.valueOf(age) + " год");
        else
            holder.age.setText(gender+", "+String.valueOf(age) + " лет");
        holder.name.setText(name+" "+ second_name);


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(context, AnotherNotesActivity.class);
                intent1.putExtra("access", access);
                intent1.putExtra("profileShare", model.getId());
                context.startActivity(intent1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return ArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }


    class HolderOneProfile extends RecyclerView.ViewHolder{
        de.hdodenhof.circleimageview.CircleImageView circleImageView;
        TextView name, age;
        CardView cardView;
        ImageView imageTv;
        Spinner Settings;
        ImageButton buttonTv;
        public HolderOneProfile( View itemView) {
            super(itemView);
            circleImageView=binding.profileimage;
            name =binding.Firstname;
            age = binding.ageProfile;
            cardView = binding.cardView;
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

