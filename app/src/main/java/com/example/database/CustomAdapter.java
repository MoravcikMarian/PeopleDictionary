package com.example.database;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> person_id, persons_name, persons_gender;

    CustomAdapter(Context context, ArrayList<String> person_id, ArrayList<String> persons_name, ArrayList<String> persons_gender) {
        this.context = context;
        this.person_id = person_id;
        this.persons_name = persons_name;
        this.persons_gender = persons_gender;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.id = String.valueOf(person_id.get(position));
        holder.persons_name_id.setText(String.valueOf(persons_name.get(position)));
        if(String.valueOf(persons_gender.get(position)).equals("man")) {
            holder.persons_gender_id.setBackgroundResource(R.drawable.baseline_man_40);
        } else
            holder.persons_gender_id.setBackgroundResource(R.drawable.baseline_woman_40);
    }

    @Override
    public int getItemCount() {
        return persons_name.size();
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
                    context.startActivity(intent);
                }
            });
        }
    }
}
