package com.example.database;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList<PersonalDetails> peopleList;

    CustomAdapter(Activity activity, Context context, ArrayList<PersonalDetails> peopleList) {
        this.context = context;
        this.activity = activity;
        this.peopleList = peopleList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.id = String.valueOf(peopleList.get(position).id);
        holder.persons_name_id.setText(String.valueOf(peopleList.get(position).name));
        if(String.valueOf(peopleList.get(position).gender).equals("man")) {
            Drawable unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.baseline_man_40);
            Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
            DrawableCompat.setTint(wrappedDrawable, R.color.colorPrimaryDark);
            holder.persons_gender_id.setBackgroundResource(R.drawable.baseline_man_40);
        }
        else {
            Drawable unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.baseline_woman_40);
            Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
            DrawableCompat.setTint(wrappedDrawable, R.color.colorPrimaryDark);
            holder.persons_gender_id.setBackgroundResource(R.drawable.baseline_woman_40);
        }
    }

    @Override
    public int getItemCount() {
        return peopleList.size();
    }

    public void updateAdapter(ArrayList<PersonalDetails> filterList) {
        // below line is to add our filtered
        // list in our course array list.
        peopleList = filterList;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        String id;
        TextView persons_name_id;
        ImageView persons_gender_id;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            persons_name_id = itemView.findViewById(R.id.name_txt);
            persons_gender_id = itemView.findViewById(R.id.gender_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id", id);
                    intent.putExtras(bundle);
                    activity.startActivityForResult(intent, 1);
                }
            });
        }
    }
}
