package com.fitness.liftcalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        ActionBar ab = getSupportActionBar();
        if(ab != null){
            ab.setTitle(Category + " subcategories");
        }
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

    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.delete_category_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_category){
            confirmDialogue();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialogue(){
        AlertDialog.Builder builder = new AlertDialog.Builder(SubcategoryActivity.this);
        builder.setTitle("Delete Category");
        builder.setMessage("Are you sure you want to delete this category?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(SubcategoryActivity.this, "Category deleted", Toast.LENGTH_SHORT).show();
                LiftDatabase myDB = new LiftDatabase(SubcategoryActivity.this);
                myDB.deleteCategory(Category);
                Intent intent = new Intent(SubcategoryActivity.this, MainActivity.class);
                startActivity(intent);
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
