package com.fitness.liftcalculator;

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

public class CustomAdapterCategory extends RecyclerView.Adapter<CustomAdapterCategory.MyViewHolder> {

    Context context;
    Activity activity;
    ArrayList<String> lift_id, lift_category;
    int parentActivity;

    Animation translate_anim;

    CustomAdapterCategory(Context context,
                          Activity activity,
                          ArrayList<String> lift_id,
                          ArrayList<String> lift_category,
                          int parentActivity){
        this.context = context;
        this.activity = activity;
        this.lift_id = lift_id;
        this.lift_category = lift_category;
        this.parentActivity = parentActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.category_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        String displayCategory = String.valueOf(lift_category.get(position));
        displayCategory = displayCategory.replaceAll("_", " ");
        holder.lift_category_txt.setText(displayCategory);
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SubcategoryActivity.class);
                intent.putExtra("lift_id", String.valueOf(lift_id.get(position)));
                intent.putExtra("lift_category", String.valueOf(lift_category.get(position)));
                intent.putExtra("parent_activity", parentActivity);
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lift_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView lift_category_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            lift_category_txt = itemView.findViewById(R.id.lift_category_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }
}
