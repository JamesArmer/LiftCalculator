package com.example.weightcalculator;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PercentageActivity extends AppCompatActivity {
    RecyclerView liftPercentageRecyclerView;

    LiftDatabase myDB;

    ArrayList<Integer> lift_percentage;
    ArrayList<Float> lift_weight;

    String Category, liftName;
    float Weight;

    CustomAdapterPercentage customAdapterPercentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percentage);

        getAndSetIntentData();

        liftPercentageRecyclerView = findViewById(R.id.liftPercentageRecyclerView);

        myDB = new LiftDatabase(PercentageActivity.this);
        lift_percentage = new ArrayList<>();
        lift_weight = new ArrayList<>();

        storeDataInArrays();

        customAdapterPercentage = new CustomAdapterPercentage(PercentageActivity.this, PercentageActivity.this, lift_percentage, lift_weight);
        liftPercentageRecyclerView.setAdapter(customAdapterPercentage);
        liftPercentageRecyclerView.setLayoutManager(new LinearLayoutManager(PercentageActivity.this));

        ActionBar ab = getSupportActionBar();
        if(ab != null){
            ab.setTitle(liftName);
        }
    }

    void storeDataInArrays(){
        int basePercentage = 50;
        int numberOfPercentages = 11;
        for(int i = 0; i< numberOfPercentages; i++){
            lift_percentage.add(basePercentage);
            lift_weight.add(basePercentage*Weight);
            basePercentage += 5;
        }
    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("lift_name")) {
            liftName = getIntent().getStringExtra("lift_name");
        }
        if(getIntent().hasExtra("lift_weight")) {
            Weight = Float.parseFloat(getIntent().getStringExtra("lift_weight"))/100;
        }
        if(getIntent().hasExtra("lift_category")){
            Category = getIntent().getStringExtra("lift_category");
        }
    }
}