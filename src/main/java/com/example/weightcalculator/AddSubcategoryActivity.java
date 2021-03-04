package com.example.weightcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddSubcategoryActivity extends AppCompatActivity {

    EditText liftName_input;
    EditText liftWeight_input;
    Button saveButton;

    String tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subcategory);

        tableName = getIntent().getStringExtra("tableName");

        liftName_input = findViewById(R.id.liftNameEditText);
        liftWeight_input = findViewById(R.id.liftWeightEditText);
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiftDatabase myDB = new LiftDatabase(AddSubcategoryActivity.this);
                myDB.addLift(tableName, liftName_input.getText().toString().trim(), Float.valueOf(liftWeight_input.getText().toString().trim()));
                Intent intent = new Intent();
                intent.putExtra("lift_category", tableName);
                finish();
            }
        });
    }
}
