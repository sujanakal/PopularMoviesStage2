package com.example.android.popularmoviez.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import com.example.android.popularmoviez.Fragment.MainFragment;
import com.example.android.popularmoviez.Fragment.MovieDetailFragment;
import com.example.android.popularmoviez.Model.Movie;
import com.example.android.popularmoviez.R;
import com.example.android.popularmoviez.Utility.Helper;

/**
 * Created by Suj🌠 on 07-12-2016.
 */

public class MainActivity extends AppCompatActivity implements MainFragment.MovieCallback {
    static boolean mTwoPane;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//      Handle action bar item clicks here. The action bar will
//      automatically handle clicks on the Home/Up button, so long
//      as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//      noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

//          To open the preference activity when settings
//          option in the overflow menu is clicked, using Intents
            Intent settingsIntent = new Intent(MainActivity.this,myPreferenceActivity.class);
            startActivity(settingsIntent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_xml);

        if(findViewById(R.id.movieDetails) != null){
            if(savedInstanceState == null){
                mTwoPane = true;
                onGridItemSelected(0);
            }
        }
        else
            mTwoPane = false;
    }

    @Override
    /*public void onGridItemSelected(Movie movie) {
        *//*GridView gridView = (GridView) findViewById(R.id.movie_grid);
        Movie movie = (Movie) gridView.getItemAtPosition(position);*//*
        Helper.getMovies(getApplicationContext(), movie);
        View detailFragmentView = findViewById(R.id.movieDetails);
        if(movie!=null){
            if(mTwoPane){
                //detailFragmentView.setVisibility(View.VISIBLE);

                Bundle bundle = new Bundle();
                bundle.putParcelable("Movie", movie);
                MovieDetailFragment mFragment = new MovieDetailFragment();
                mFragment.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.movieDetails,mFragment)
                        .commit();
            }
            else{
                Intent intent = new Intent(getApplicationContext() , MovieDetailActivity.class);
                intent.putExtra("Movie", movie);
                startActivity(intent);
            }
        }
    }*/
    public void onGridItemSelected(int position) {
        GridView gridView = (GridView) findViewById(R.id.movie_grid);
        Movie movie = (Movie) gridView.getItemAtPosition(position);
        Helper.getMovies(getApplicationContext(), movie);
        //View detailFragmentView = findViewById(R.id.movieDetails);
        if(movie!=null){
            if(mTwoPane){
                //detailFragmentView.setVisibility(View.VISIBLE);

                Bundle bundle = new Bundle();
                bundle.putParcelable("Movie", movie);
                MovieDetailFragment mFragment = new MovieDetailFragment();
                mFragment.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.movieDetails,mFragment)
                        .commit();
            }
            else{
                Intent intent = new Intent(getApplicationContext() , MovieDetailActivity.class);
                intent.putExtra("Movie", movie);
                startActivity(intent);
            }
        }
    }
}

