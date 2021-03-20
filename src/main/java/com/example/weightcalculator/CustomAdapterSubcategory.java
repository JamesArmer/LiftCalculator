package com.example.weightcalculator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class CustomAdapterSubcategory extends RecyclerView.Adapter<CustomAdapterSubcategory.MyViewHolder> {

    Context context;
    Activity activity;
    ArrayList lift_id, lift_name, lift_weight;
    String Category;
    int parentActivity;

    Class nextActivity;

    LiftDatabase myDB;

    Animation translate_anim;

    CustomAdapterSubcategory(Context context,
                             Activity activity,
                             ArrayList lift_id,
                             ArrayList lift_name,
                             ArrayList lift_weight,
                             String Category,
                             int parentActivity){
        this.context = context;
        this.activity = activity;
        this.lift_id = lift_id;
        this.lift_name = lift_name;
        this.lift_weight = lift_weight;
        this.Category = Category;
        this.parentActivity = parentActivity;

        if(parentActivity == 0){
            nextActivity = PercentageActivity.class;
        } else {
            nextActivity = UpdateActivity.class;
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.subcategory_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.lift_name_txt.setText(String.valueOf(lift_name.get(position)));
        if(LiftDatabase.convertToPounds){
            int poundsConversion = (int) (Double.parseDouble(String.valueOf(lift_weight.get(position)))*2.2);
            holder.lift_weight_txt.setText(String.valueOf(poundsConversion) + "lbs");
        } else{
            holder.lift_weight_txt.setText(String.valueOf(lift_weight.get(position)) + "kg");
        }
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, nextActivity);
                intent.putExtra("lift_id", String.valueOf(lift_id.get(position)));
                intent.putExtra("lift_name", String.valueOf(lift_name.get(position)));
                intent.putExtra("lift_weight", String.valueOf(lift_weight.get(position)));
                intent.putExtra("lift_category", Category);
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lift_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView lift_name_txt;
        TextView lift_weight_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            lift_name_txt = itemView.findViewById(R.id.lift_name_txt);
            lift_weight_txt = itemView.findViewById(R.id.lift_weight_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }
}
