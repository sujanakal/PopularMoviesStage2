package com.example.android.popularmoviez.Activity;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.android.popularmoviez.Fragment.MovieDetailFragment;
import com.example.android.popularmoviez.Fragment.ReviewFragment;
import com.example.android.popularmoviez.Fragment.TrailerFragment;
import com.example.android.popularmoviez.Model.Movie;
import com.example.android.popularmoviez.Model.Reviews;
import com.example.android.popularmoviez.Model.Trailers;
import com.example.android.popularmoviez.R;
import com.example.android.popularmoviez.Utility.Helper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Suj🌠 on 21-08-2016.
 */
public class MovieDetailActivity extends AppCompatActivity {

    Movie getMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        getWindow().setStatusBarColor(getResources().getColor(R.color.statusBar));

        if(savedInstanceState == null){
            Intent getIn = getIntent();
            getMovies = getIn.getParcelableExtra("Movie");

            Bundle bundle = new Bundle();
            bundle.putParcelable("Movie", getMovies);

            MovieDetailFragment mFragment = new MovieDetailFragment();
            mFragment.setArguments(bundle);
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.movieDetails,mFragment)
                    .commit();
        }
    }
}