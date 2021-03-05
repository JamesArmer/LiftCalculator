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

public class CustomAdapterPercentage extends RecyclerView.Adapter<CustomAdapterPercentage.MyViewHolder> {

    Context context;
    Activity activity;

    ArrayList lift_percentage, lift_weight;

    Animation translate_anim;

    CustomAdapterPercentage(Context context,
                            Activity activity,
                            ArrayList lift_percentage,
                            ArrayList lift_weight) {
        this.context = context;
        this.activity = activity;
        this.lift_percentage = lift_percentage;
        this.lift_weight = lift_weight;
    }

    @NonNull
    @Override
    public CustomAdapterPercentage.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.percentage_row, parent, false);
        return new CustomAdapterPercentage.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterPercentage.MyViewHolder holder, final int position) {
        holder.percentage_txt.setText(String.valueOf(lift_percentage.get(position)) + "%");
        if(LiftDatabase.convertToPounds){
            int poundsConversion = (int) (Double.parseDouble(String.valueOf(lift_weight.get(position)))*2.2);
            holder.weight_txt.setText(String.valueOf(poundsConversion) + "lbs");
        } else{
            holder.weight_txt.setText(String.valueOf(lift_weight.get(position)) + "kg");
        }
    }

    @Override
    public int getItemCount() {
        return lift_percentage.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView percentage_txt;
        TextView weight_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            percentage_txt = itemView.findViewById(R.id.percentage_txt);
            weight_txt = itemView.findViewById(R.id.weight_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }
}