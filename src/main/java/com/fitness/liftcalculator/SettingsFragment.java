package com.fitness.liftcalculator;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    Switch poundsToKilosSwitch;
    Switch roundKilosSwitch;

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
        roundKilosSwitch = getView().findViewById(R.id.roundKilos);
        poundsToKilosSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(poundsToKilosSwitch.isChecked()){
                    LiftDatabase.convertToPounds = true;
                    roundKilosSwitch.setClickable(false);
                    editor.putBoolean("convertToPounds",true);
                } else {
                    LiftDatabase.convertToPounds = false;
                    roundKilosSwitch.setClickable(true);
                    editor.putBoolean("convertToPounds",false);
                }
                editor.apply();
            }
        });
        roundKilosSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(roundKilosSwitch.isChecked()){
                    LiftDatabase.roundKilos = true;
                    poundsToKilosSwitch.setClickable(false);
                    editor.putBoolean("roundKilos", true);
                } else {
                    LiftDatabase.roundKilos = false;
                    poundsToKilosSwitch.setClickable(true);
                    editor.putBoolean("roundKilos", false);
                }
                editor.apply();
            }
        });

        //Load settings
        LiftDatabase.convertToPounds = settings.getBoolean("convertToPounds", false);
        LiftDatabase.roundKilos = settings.getBoolean("roundKilos", false);
        poundsToKilosSwitch.setChecked(LiftDatabase.convertToPounds);
        roundKilosSwitch.setChecked(LiftDatabase.roundKilos);

        if(poundsToKilosSwitch.isChecked()){
            roundKilosSwitch.setClickable(false);
        }
        if(roundKilosSwitch.isChecked()){
            poundsToKilosSwitch.setClickable(false);
        }
    }
}
