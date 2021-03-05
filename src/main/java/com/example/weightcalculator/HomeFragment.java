package com.example.weightcalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    RecyclerView liftCategoryRecyclerView;
    FloatingActionButton addButton;
    ImageView emptyImage;
    TextView emptyText;

    LiftDatabase myDB;
    ArrayList<String> lift_id, lift_category;

    CustomAdapterCategory customAdapterCategory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        liftCategoryRecyclerView = getView().findViewById(R.id.liftCategoryRecyclerView);
        emptyImage = getView().findViewById(R.id.emptyImageView);
        emptyText = getView().findViewById(R.id.emptyTextView);

        myDB = new LiftDatabase(getActivity());
        lift_id = new ArrayList<>();
        lift_category = new ArrayList<>();

        storeDataInArrays();

        customAdapterCategory = new CustomAdapterCategory(getActivity(), getActivity(), lift_id, lift_category, 0);
        liftCategoryRecyclerView.setAdapter(customAdapterCategory);
        liftCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData("Categories");
        if(cursor.getCount() == 0){
            emptyImage.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.VISIBLE);
        } else {
            while(cursor.moveToNext()){
                lift_id.add(cursor.getString(0));
                lift_category.add(cursor.getString(1));
            }
            emptyImage.setVisibility(View.GONE);
            emptyText.setVisibility(View.GONE);
        }

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.delete_all_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            confirmDialogue();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialogue(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete All");
        builder.setMessage("Are you sure you want to delete all data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "All data deleted.", Toast.LENGTH_SHORT).show();
                LiftDatabase myDB = new LiftDatabase(getActivity());
                myDB.deleteAllData();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
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
