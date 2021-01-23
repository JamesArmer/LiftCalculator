package com.example.weightcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText liftCategoryUpdateEditText;
    Button updateButton;

    String Id, Category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        liftCategoryUpdateEditText = findViewById(R.id.liftCategoryUpdateEditText);
        updateButton = findViewById(R.id.updateButton);

        getAndSetIntentData();
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiftDatabase myDB = new LiftDatabase(UpdateActivity.this);
                Category = liftCategoryUpdateEditText.getText().toString().trim();
                myDB.updateData(Id, Category);
                finish();
            }
        });
    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("lift_id") && getIntent().hasExtra("lift_category")){
            Id = getIntent().getStringExtra("lift_id");
            Category = getIntent().getStringExtra("lift_category");
            liftCategoryUpdateEditText.setText(Category);
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }
}
