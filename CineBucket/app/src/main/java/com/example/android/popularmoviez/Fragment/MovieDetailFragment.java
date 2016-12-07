package com.example.android.popularmoviez.Fragment;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.*;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmoviez.Activity.MainActivity;
import com.example.android.popularmoviez.Activity.MovieDetailActivity;
import com.example.android.popularmoviez.Model.Movie;
import com.example.android.popularmoviez.R;
import com.example.android.popularmoviez.Utility.Helper;
import com.squareup.picasso.Picasso;

import static android.content.Context.MODE_PRIVATE;

public class MovieDetailFragment extends Fragment {
    SharedPreferences favoriteSharedPreferences ;
    Editor favEditor;
    boolean favoriteButtonState;
    String TAG = MovieDetailFragment.class.getSimpleName();
    final static int FALSE = 0;
    final static int TRUE = 1;
    Movie getMovie;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.collapsible_detail_fragment, container, false);

    }

    @Override
    public void onStart() {
        super.onStart();
        getMovie = getArguments().getParcelable("Movie");
        final Context context = getActivity();

        movieDetails(getMovie);
        favoriteSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        favEditor = favoriteSharedPreferences.edit();
        final FloatingActionButton floatingActionButton = (FloatingActionButton) getView().findViewById(R.id.favoriteButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getMovie.isFavorite() == 0){
                    //adding to favorites list

                    floatingActionButton.setImageResource(R.drawable.ic_heart);
                    Helper.insertIntoFavoriteTable(getActivity(),getMovie.getId());
                    Helper.updateMovieDetailFavColumn(context, getMovie.getId(), TRUE);
                }
                else{
                    //removing from favorites list

                    floatingActionButton.setImageResource(R.drawable.ic_heart_outline);
                    Helper.deleteFromFavoriteTable(getActivity(), getMovie.getId());
                    Helper.updateMovieDetailFavColumn(context, getMovie.getId(), FALSE);
                }
            }
        });
    }

    private void movieDetails(Movie getMovie) {

        Log.d(TAG, "getMovie content: " + getMovie);
        CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) getView().findViewById(R.id.collapsing_toolbar_layout);

        if (getMovie != null) {

            FloatingActionButton favButton = (FloatingActionButton) getView().findViewById(R.id.favoriteButton);
            if(getMovie.isFavorite() == 1){
                favButton.setImageResource(R.drawable.ic_heart);
            }
            else{
                favButton.setImageResource(R.drawable.ic_heart_outline);
            }

            collapsingToolbarLayout.setTitle(getMovie.getTitle());
            collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.LargeText);

            ImageView moviePoster_c = (ImageView) getView().findViewById(
                    R.id.detail_poster_collapsing);

            String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w780/";

            Picasso
                    .with(getActivity())
                    .load(IMAGE_BASE_URL + getMovie.getBackdrop_path())
                    .into(moviePoster_c);

            TextView movieCount = (TextView) getView().findViewById(R.id.detail_count_c);
            movieCount.setText("Vote count: " + String.valueOf(getMovie.getVote_count()));

            TextView moviePopularity = (TextView) getView().findViewById(R.id.detail_popularity_c);
            moviePopularity.setText(String.valueOf(getMovie.getPopularity()));

            TextView movieReleaseDate = (TextView) getView().findViewById(
                    R.id.detail_release_date_c);
            movieReleaseDate.setText(getMovie.getRelease_date());

            TextView movieRating = (TextView) getView().findViewById(R.id.detail_rating_c);
            movieRating.setText("Vote average: " + String.valueOf(getMovie.getVote_average()));

            TextView movieOverview = (TextView) getView().findViewById(R.id.detail_overview_c);
            movieOverview.setText(getMovie.getOverview());

            ImageView moviePoster = (ImageView) getView().findViewById(R.id.detail_poster);
            String IMAGE_BASE_URL1 = "http://image.tmdb.org/t/p/w185/";
            Picasso
                    .with(getActivity())
                    .load(IMAGE_BASE_URL1 + getMovie.getPoster_path())
                    .into(moviePoster);

        }

    }
}