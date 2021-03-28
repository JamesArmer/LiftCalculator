package com.fitness.liftcalculator;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {

    EditText liftNameUpdateEditText;
    EditText liftWeightUpdateEditText;
    Button updateButton, deleteButton;

    String Id, liftName, liftWeight, tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        liftNameUpdateEditText = findViewById(R.id.liftNameUpdateEditText);
        liftWeightUpdateEditText = findViewById(R.id.liftWeightUpdateEditText);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);

        getAndSetIntentData();
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiftDatabase myDB = new LiftDatabase(UpdateActivity.this);
                liftName = liftNameUpdateEditText.getText().toString().trim();
                liftWeight = liftWeightUpdateEditText.getText().toString().trim();
                myDB.updateData(Id, tableName, liftName, liftWeight);
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
            ab.setTitle(liftName);
        }
    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("lift_name") && getIntent().hasExtra("lift_weight")){
            Id = getIntent().getStringExtra("lift_id");
            liftName = getIntent().getStringExtra("lift_name");
            liftWeight = getIntent().getStringExtra("lift_weight");
            tableName = getIntent().getStringExtra("lift_category");
            liftNameUpdateEditText.setText(liftName);
            liftWeightUpdateEditText.setText(liftWeight);
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialogue(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + liftName + " ?");
        builder.setMessage("Are you sure you want to delete " + liftName + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LiftDatabase myDB = new LiftDatabase(UpdateActivity.this);
                myDB.deleteOneRecord(Id, tableName);
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
