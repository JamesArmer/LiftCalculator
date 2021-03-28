package com.fitness.liftcalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddCategoryActivity extends AppCompatActivity {

    EditText liftCategory_input;
    Button saveButton;

    String inputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        liftCategory_input = findViewById(R.id.liftCategoryEditText);
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check that the input contains letters
                inputText = String.valueOf(liftCategory_input.getText());
                if(!inputText.matches("[a-zA-Z ]+")){
                    Toast.makeText(AddCategoryActivity.this, "Add text to add a category!", Toast.LENGTH_SHORT).show();
                } else {
                    LiftDatabase myDB = new LiftDatabase(AddCategoryActivity.this);
                    myDB.addCategory(inputText.trim());
                    finish();
                }
            }
        });
    }
}
