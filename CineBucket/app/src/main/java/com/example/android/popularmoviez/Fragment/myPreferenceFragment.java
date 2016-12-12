package com.example.android.popularmoviez.Fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.android.popularmoviez.R;

/**
 * Created by Suj🌠 on 10-05-2016.
 */
public class myPreferenceFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}
