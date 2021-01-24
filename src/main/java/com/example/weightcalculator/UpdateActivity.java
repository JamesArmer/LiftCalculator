package com.example.weightcalculator;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText liftCategoryUpdateEditText;
    Button updateButton, deleteButton;

    String Id, Category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        liftCategoryUpdateEditText = findViewById(R.id.liftCategoryUpdateEditText);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);

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
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialogue();
            }
        });

        ActionBar ab = getSupportActionBar();
        if(ab != null){
            ab.setTitle(Category);
        }
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

    void confirmDialogue(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + Category + " ?");
        builder.setMessage("Are you sure you want to delete " + Category + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LiftDatabase myDB = new LiftDatabase(UpdateActivity.this);
                myDB.deleteOneRecord(Id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}
