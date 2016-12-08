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
    ArrayList<Trailers> movieTrailers = new ArrayList<>();
    ArrayList<Reviews> movieReviews = new ArrayList<>();
    static int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Intent getin = getIntent();
        getMovies = getin.getParcelableExtra("Movie");
        movieId = getMovies.getId();

        DetailTask task = new DetailTask();
        task.execute();

        getWindow().setStatusBarColor(getResources().getColor(R.color.statusBar));
        setContentView(R.layout.movie_detail);

        /*Bundle bundle = new Bundle();

        bundle.putParcelable("Movie", getMovies);
        MovieDetailFragment mFragment = new MovieDetailFragment();
        mFragment.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.movieDetails,mFragment)
                .commit();


        bundle.putParcelableArrayList("TrailerArrayList",movieTrailers);
        TrailerFragment tFragment = new TrailerFragment();
        tFragment.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.movieTrailers,tFragment)
                .commit();


        bundle.putParcelableArrayList("ReviewArrayList",movieReviews);
        ReviewFragment rFragment = new ReviewFragment();
        rFragment.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.movieReviews,rFragment)
                .commit();
*/
    }

    class DetailTask extends AsyncTask<String, Void, Void>{


        @Override
        protected Void doInBackground(String... params) {
            String url_reviews = "https://api.themoviedb.org/3/movie/"+movieId+"/reviews?api_key=";
            String url_trailers = "https://api.themoviedb.org/3/movie/"+movieId+"/videos?api_key=";

            Helper.getReviews(url_reviews,movieReviews);
            Helper.getTrailers(url_trailers,movieTrailers);
            return null;
        }

        @Override
        protected void onPostExecute(Void v){
            Bundle bundle = new Bundle();

            bundle.putParcelable("Movie", getMovies);
            MovieDetailFragment mFragment = new MovieDetailFragment();
            mFragment.setArguments(bundle);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.movieDetails,mFragment)
                    .commit();


            bundle.putParcelableArrayList("TrailerArrayList",movieTrailers);
            TrailerFragment tFragment = new TrailerFragment();
            tFragment.setArguments(bundle);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.movieTrailers,tFragment)
                    .commit();


            bundle.putParcelableArrayList("ReviewArrayList",movieReviews);
            ReviewFragment rFragment = new ReviewFragment();
            rFragment.setArguments(bundle);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.movieReviews,rFragment)
                    .commit();
        }
    }


}