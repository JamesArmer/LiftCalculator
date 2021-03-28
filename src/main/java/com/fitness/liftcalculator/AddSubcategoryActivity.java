package com.fitness.liftcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddSubcategoryActivity extends AppCompatActivity {

    EditText liftName_input;
    EditText liftWeight_input;
    Button saveButton;

    String tableName, inputText, inputNumber;

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
                //Check that the input contains letters or numbers
                inputText = String.valueOf(liftName_input.getText());
                inputNumber = String.valueOf(liftWeight_input.getText());
                if ((!inputText.matches("[a-zA-Z ]+")) || (!inputNumber.matches("[0-9]+"))) {
                    Toast.makeText(AddSubcategoryActivity.this, "Add text/numbers to add a subcategory!", Toast.LENGTH_SHORT).show();
                } else {
                    LiftDatabase myDB = new LiftDatabase(AddSubcategoryActivity.this);
                    myDB.addLift(tableName, liftName_input.getText().toString().trim(), Float.parseFloat(liftWeight_input.getText().toString().trim()));
                    Intent intent = new Intent();
                    intent.putExtra("lift_category", tableName);
                    finish();
                }
            }
        });
    }
}
