package com.example.weightcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SubcategoryActivity extends AppCompatActivity {
    RecyclerView editCategoryRecyclerView;
    FloatingActionButton addButton;
    ImageView emptyImage;
    TextView emptyText;

    LiftDatabase myDB;
    ArrayList<String> lift_id, lift_name, lift_weight;

    CustomAdapterSubcategory customAdapterSubcategory;

    String Id;
    private static String Category;
    private static int parentActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategory);

        getAndSetIntentData();

        editCategoryRecyclerView = findViewById(R.id.editCategoryRecyclerView);
        emptyImage = findViewById(R.id.emptyImageView3);
        emptyText = findViewById(R.id.emptyTextView3);
        addButton = findViewById(R.id.addButton3);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubcategoryActivity.this, AddSubcategoryActivity.class);
                intent.putExtra("tableName", Category);
                startActivityForResult(intent, 2);
            }
        });

        //home fragment == 0
        if(parentActivity == 0){
            addButton.setVisibility(View.GONE);
        }

        myDB = new LiftDatabase(SubcategoryActivity.this);
        lift_id = new ArrayList<>();
        lift_name = new ArrayList<>();
        lift_weight = new ArrayList<>();

        storeDataInArrays();

        customAdapterSubcategory = new CustomAdapterSubcategory(SubcategoryActivity.this, SubcategoryActivity.this, lift_id, lift_name, lift_weight, Category, parentActivity);
        editCategoryRecyclerView.setAdapter(customAdapterSubcategory);
        editCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(SubcategoryActivity.this));
    }

    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData(Category);
        if(cursor.getCount() == 0){
            emptyImage.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.VISIBLE);
        } else {
            while(cursor.moveToNext()){
                lift_id.add(cursor.getString(0));
                lift_name.add(cursor.getString(1));
                lift_weight.add(cursor.getString(2));
            }
            emptyImage.setVisibility(View.GONE);
            emptyText.setVisibility(View.GONE);
        }
    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("lift_id")) {
            Id = getIntent().getStringExtra("lift_id");
        }
        if(getIntent().hasExtra("lift_category")){
            Category = getIntent().getStringExtra("lift_category");
        }
        if(getIntent().hasExtra("parent_activity")){
            parentActivity = getIntent().getIntExtra("parent_activity", 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        recreate();
    }
}
