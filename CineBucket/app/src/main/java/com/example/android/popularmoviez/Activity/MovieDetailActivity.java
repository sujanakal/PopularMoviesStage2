package com.example.android.popularmoviez.Activity;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.android.popularmoviez.Fragment.MovieDetailFragment;
import com.example.android.popularmoviez.Fragment.ReviewFragment;
import com.example.android.popularmoviez.Fragment.TrailerFragment;
import com.example.android.popularmoviez.Model.Movie;
import com.example.android.popularmoviez.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Suj🌠 on 21-08-2016.
 */
public class MovieDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.statusBar));
        setContentView(R.layout.movie_detail);
        Intent getin = getIntent();
        Movie getMovies = getin.getParcelableExtra("Movie");
        Bundle bundle = new Bundle();
        bundle.putParcelable("Movie", getMovies);

        MovieDetailFragment mFragment = new MovieDetailFragment();
        mFragment.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.movieDetails,mFragment)
                .commit();

        bundle.putInt("MovieId",getMovies.getId());

        TrailerFragment tFragment = new TrailerFragment();
        tFragment.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.movieTrailers,tFragment)
                .commit();

        ReviewFragment rFragment = new ReviewFragment();
        rFragment.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.movieReviews,rFragment)
                .commit();

    }

}