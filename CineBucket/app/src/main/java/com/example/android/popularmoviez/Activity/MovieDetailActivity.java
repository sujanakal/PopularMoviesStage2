package com.example.android.popularmoviez.Activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.popularmoviez.Fragment.MovieDetailFragment;
import com.example.android.popularmoviez.Fragment.ReviewFragment;
import com.example.android.popularmoviez.Model.Movie;
import com.example.android.popularmoviez.R;

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
        //addFragments(mFragment,bundle);

        ReviewFragment rFragment = new ReviewFragment();
        bundle.putInt("MovieId",getMovies.getId());
        rFragment.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.movieReviews,rFragment)
                .commit();
        //addFragments(rFragment,bundle);
    }

    public void addFragments(Fragment fragment, Bundle bundle){

        fragment.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .add(R.id.detail_container,fragment)
                .commit();
    }
}