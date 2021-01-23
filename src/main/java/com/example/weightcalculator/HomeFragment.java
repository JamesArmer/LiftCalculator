package com.example.weightcalculator;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    RecyclerView liftCategoryRecyclerView;
    FloatingActionButton addButton;

    LiftDatabase myDB;
    ArrayList<String> lift_id, lift_category;

    CustomAdapter customAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        liftCategoryRecyclerView = getView().findViewById(R.id.liftCategoryRecyclerView);
        addButton = getView().findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddActivity.class);
                getActivity().startActivityForResult(intent, 2);
            }
        });

        myDB = new LiftDatabase(getActivity());
        lift_id = new ArrayList<>();
        lift_category = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(getActivity(), getActivity(), lift_id, lift_category);
        liftCategoryRecyclerView.setAdapter(customAdapter);
        liftCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(getActivity(), "No data.", Toast.LENGTH_SHORT).show();
        } else {
            while(cursor.moveToNext()){
                lift_id.add(cursor.getString(0));
                lift_category.add(cursor.getString(1));
            }
        }

    }
}
