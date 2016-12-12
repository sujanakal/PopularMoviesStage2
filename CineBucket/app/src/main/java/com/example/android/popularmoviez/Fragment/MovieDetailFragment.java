package com.example.android.popularmoviez.Fragment;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.*;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmoviez.Activity.MovieDetailActivity;
import com.example.android.popularmoviez.Model.Movie;
import com.example.android.popularmoviez.Model.Reviews;
import com.example.android.popularmoviez.Model.Trailers;
import com.example.android.popularmoviez.R;
import com.example.android.popularmoviez.Utility.Helper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieDetailFragment extends Fragment {
    SharedPreferences favoriteSharedPreferences ;
    Editor favEditor;
    final static String DETAIL_STATE = "detail_state";
    String TAG = MovieDetailFragment.class.getSimpleName();
    final static int FALSE = 0;
    final static int TRUE = 1;
    Movie getMovie;
    final static String FAVORITE_ADDED = "Added to favorites.";
    final static String FAVORITE_REMOVED = "Removed from favorites.";

    ArrayList<Trailers> movieTrailers = new ArrayList<>();
    ArrayList<Reviews> movieReviews = new ArrayList<>();
    static int movieId;

    public MovieDetailFragment(){}

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putParcelable(DETAIL_STATE, getMovie);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState){
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null){
            getMovie = savedInstanceState.getParcelable(DETAIL_STATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.collapsible_detail_fragment, container, false);

        DetailTask task = new DetailTask();

        getMovie = getArguments().getParcelable("Movie");
        if(getMovie != null){
            movieId = getMovie.getId();
            final Context context = getActivity();

            movieDetails(getMovie, rootView);
            favoriteSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            favEditor = favoriteSharedPreferences.edit();
            final FloatingActionButton floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.favoriteButton);

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(getMovie.isFavorite() == 0){
                        //adding to favorites list

                        floatingActionButton.setImageResource(R.drawable.ic_heart);
                        Helper.insertIntoFavoriteTable(getActivity(),getMovie.getId());
                        getMovie.setFavorite(TRUE);
                        Helper.updateMovieDetailFavColumn(context, getMovie.getId(), TRUE);

                        Helper.makeToast(context,FAVORITE_ADDED);
                    }
                    else{
                        //removing from favorites list

                        floatingActionButton.setImageResource(R.drawable.ic_heart_outline);
                        Helper.deleteFromFavoriteTable(getActivity(), getMovie.getId());
                        getMovie.setFavorite(FALSE);
                        Helper.updateMovieDetailFavColumn(context, getMovie.getId(), FALSE);

                        Helper.makeToast(context,FAVORITE_REMOVED);
                    }
                }
            });

            task.execute();
        }


        return rootView;

    }



    private void movieDetails(Movie getMovie,View rootView) {

        Log.d(TAG, "getMovie content: " + getMovie);

        CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar_layout);

        if (getMovie != null) {

            FloatingActionButton favButton = (FloatingActionButton) rootView.findViewById(R.id.favoriteButton);
            if(getMovie.isFavorite() == 1){
                favButton.setImageResource(R.drawable.ic_heart);
            }
            else{
                favButton.setImageResource(R.drawable.ic_heart_outline);
            }

            collapsingToolbarLayout.setTitle(getMovie.getTitle());
            collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.LargeText);

            ImageView moviePoster_c = (ImageView) rootView.findViewById(
                    R.id.detail_poster_collapsing);

            String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w780/";

            Picasso
                    .with(getActivity())
                    .load(IMAGE_BASE_URL + getMovie.getBackdrop_path())
                    .into(moviePoster_c);

            TextView movieCount = (TextView) rootView.findViewById(R.id.detail_count_c);
            movieCount.setText("Vote count: " + String.valueOf(getMovie.getVote_count()));

            TextView moviePopularity = (TextView) rootView.findViewById(R.id.detail_popularity_c);
            moviePopularity.setText(String.valueOf(getMovie.getPopularity()));

            TextView movieReleaseDate = (TextView) rootView.findViewById(
                    R.id.detail_release_date_c);
            movieReleaseDate.setText(getMovie.getRelease_date());

            TextView movieRating = (TextView) rootView.findViewById(R.id.detail_rating_c);
            movieRating.setText("Vote average: " + String.valueOf(getMovie.getVote_average()));

            TextView movieOverview = (TextView) rootView.findViewById(R.id.detail_overview_c);
            movieOverview.setText(getMovie.getOverview());

            ImageView moviePoster = (ImageView) rootView.findViewById(R.id.detail_poster);
            String IMAGE_BASE_URL1 = "http://image.tmdb.org/t/p/w185/";
            Picasso
                    .with(getActivity())
                    .load(IMAGE_BASE_URL1 + getMovie.getPoster_path())
                    .into(moviePoster);

        }

    }

    class DetailTask extends AsyncTask<String, Void, Void> {


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