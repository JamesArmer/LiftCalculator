package com.example.weightcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddCategoryActivity extends AppCompatActivity {

    EditText liftCategory_input;
    Button saveButton;

    String tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        liftCategory_input = findViewById(R.id.liftCategoryEditText);
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiftDatabase myDB = new LiftDatabase(AddCategoryActivity.this);
                myDB.addCategory(liftCategory_input.getText().toString().trim());
                finish();
            }
        });
    }
}
