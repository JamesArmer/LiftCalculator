package com.example.weightcalculator;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    Switch poundsToKilosSwitch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences settings = getActivity().getSharedPreferences("UserInfo", 0);
        SharedPreferences.Editor editor = settings.edit();

        poundsToKilosSwitch = getView().findViewById(R.id.switchKGtoLBS);
        poundsToKilosSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(poundsToKilosSwitch.isChecked()){
                    LiftDatabase.convertToPounds = true;
                    editor.putBoolean("convertToPounds",true);
                } else {
                    LiftDatabase.convertToPounds = false;
                    editor.putBoolean("convertToPounds",false);
                }
                editor.apply();
            }
        });

        LiftDatabase.convertToPounds = settings.getBoolean("convertToPounds", false);
        poundsToKilosSwitch.setChecked(LiftDatabase.convertToPounds);
    }
}
