package com.example.android.popularmoviez.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.popularmoviez.R;
import com.example.android.popularmoviez.Fragment.myPreferenceFragment;

/**
 * Created by Suj🌠 on 10-05-2016.
 */
public class myPreferenceActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(getResources().getColor(R.color.statusBar));

        //setContentView(R.layout.settings_preference);
        getFragmentManager().beginTransaction().replace(android.R.id.content,new myPreferenceFragment()).commit();
    }
}
