package com.example.weightcalculator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    Context context;
    Activity activity;
    ArrayList lift_id, lift_category;

    CustomAdapter(Context context,
                  Activity activity,
                  ArrayList lift_id,
                  ArrayList lift_category){
        this.context = context;
        this.activity = activity;
        this.lift_id = lift_id;
        this.lift_category = lift_category;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.lift_id_txt.setText(String.valueOf(lift_id.get(position)));
        holder.lift_category_txt.setText(String.valueOf(lift_category.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("lift_id", String.valueOf(lift_id.get(position)));
                intent.putExtra("lift_category", String.valueOf(lift_category.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lift_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView lift_id_txt, lift_category_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            lift_id_txt = itemView.findViewById(R.id.lift_id_txt);
            lift_category_txt = itemView.findViewById(R.id.lift_category_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
