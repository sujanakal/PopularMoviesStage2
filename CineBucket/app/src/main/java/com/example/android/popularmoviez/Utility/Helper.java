package com.example.android.popularmoviez.Utility;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmoviez.Data.FavoriteMovies;
import com.example.android.popularmoviez.Data.FavoriteTable;
import com.example.android.popularmoviez.Data.MovieDetails;
import com.example.android.popularmoviez.Data.MovieReviews;
import com.example.android.popularmoviez.Data.MovieTrailers;
import com.example.android.popularmoviez.Data.MoviesTable;

import com.example.android.popularmoviez.Fragment.ReviewFragment;
import com.example.android.popularmoviez.Model.Movie;
import com.example.android.popularmoviez.Model.Reviews;
import com.example.android.popularmoviez.Model.Trailers;
import com.example.android.popularmoviez.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Suj🌠 on 01-12-2016.
 */

public class Helper {

    static ApiKey key = new ApiKey();
    static final  String RESULTS_JSON = "results";
    static final  String AUTHOR = "author";
    static final  String URL = "url";
    static final  String CONTENT = "content";
    static final  String NAME = "name";
    static final  String KEY = "key";
    static final  String TYPE = "type";
    static final  String ID = "id";
    static final  String SITE = "site";
    static final  String SIZE = "size";
    static final int TRUE = 1;
    static final int FALSE = 0;

    public static void getReviews(String reviews, ArrayList<Reviews> reviewList) {
        BufferedReader buf = null;
        HttpsURLConnection http = null;

        String input = null;
        try {
            URL url = new URL(reviews + key.getApiKey());

            http = (HttpsURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.connect();

            InputStream in = http.getInputStream();
            if (in == null){
                //No json response is got
            }

            buf = new BufferedReader(new InputStreamReader(in));

            String jsonData;
            StringBuilder sb = new StringBuilder();

            if ((jsonData = buf.readLine()) != null) {
                sb.append(jsonData + "\n");
            }

            if (sb.length() == 0) {
//              Empty stream
            }
            input = sb.toString();
            if (input != null) {
                JSONObject jsonObject = new JSONObject(input);
                JSONArray results = jsonObject.getJSONArray(RESULTS_JSON);

                for (int i = 0; i < results.length(); i++) {
                    JSONObject iObject = results.getJSONObject(i);
                    Reviews iReviews = new Reviews();

                    iReviews.setAuthor(iObject.getString(AUTHOR));
                    iReviews.setContent(iObject.getString(CONTENT));
                    iReviews.setUrl(iObject.getString(URL));

                    reviewList.add(iReviews);

                    //insertIntoReviewsTable(iObject);
                }
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {

            if (http != null)
                http.disconnect();

            if (buf != null) {
                try {
                    buf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void loadMovieReviews(Context context, ArrayList<Reviews> mRev, LinearLayout parent){
        Reviews mReviews;
        for (int i=0; i<mRev.size();i++){
            mReviews = mRev.get(i);
            LinearLayout child = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.reviews,null);
            TextView rAuthor = (TextView) child.findViewById(R.id.review_author);
            TextView rContent = (TextView) child.findViewById(R.id.review_content);
            rAuthor.setText(mReviews.getAuthor());
            rContent.setText(mReviews.getContent());
            parent.addView(child);
        }
    }

// TODO: 01-12-2016 Correct this!
    /*public static void insertIntoReviewsTable(JSONObject object){
        MovieReviews reviewEntry = new MovieReviews();
        try {
            reviewEntry.author = object.getString(AUTHOR);
            reviewEntry.url = object.getString(URL;
            reviewEntry.Content = o
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/



    public static boolean isOnline(Context context){
        String alertTitle = "Network Error!";
        String alertMessage = "Could not load the movies.\nPlease check your network settings and try again!";
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()){
            //Toast.makeText(getApplicationContext(),noInternerConnection, Toast.LENGTH_LONG).show();
            AlertDialog noInternetAlert = new AlertDialog.Builder(context).create();
            noInternetAlert.setTitle(alertTitle);
            noInternetAlert.setMessage(alertMessage);
            noInternetAlert.setCanceledOnTouchOutside(false);
            noInternetAlert.show();
            return false;
        }
        return true;
    }



    public static void getTrailers(String trailers, ArrayList<Trailers> trailerList){
        BufferedReader buf = null;
        HttpsURLConnection http = null;

        String input = null;
        try {
            URL url = new URL(trailers + key.getApiKey());

            http = (HttpsURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.connect();

            InputStream in = http.getInputStream();
            if (in == null){
                //No json response is got
            }

            buf = new BufferedReader(new InputStreamReader(in));

            String jsonData;
            StringBuilder sb = new StringBuilder();

            if ((jsonData = buf.readLine()) != null) {
                sb.append(jsonData + "\n");
            }

            if (sb.length() == 0) {
//              Empty stream
            }
            input = sb.toString();
            if (input != null) {
                JSONObject jsonObject = new JSONObject(input);
                JSONArray results = jsonObject.getJSONArray(RESULTS_JSON);

                for (int i = 0; i < results.length(); i++) {
                    JSONObject iObject = results.getJSONObject(i);
                    Trailers iTrailers = new Trailers();

                    iTrailers.setName(iObject.getString(NAME));
                    iTrailers.setKey(iObject.getString(KEY));
                    iTrailers.setType(iObject.getString(TYPE));
                    iTrailers.setSite(iObject.getString(SITE));
                    iTrailers.setSize(iObject.getInt(SIZE));
                    iTrailers.setId(iObject.getString(ID));

                    trailerList.add(iTrailers);
                }
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {

            if (http != null)
                http.disconnect();

            if (buf != null) {
                try {
                    buf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }






    public static void insertIntoMovieDetailsTable(Context context, Movie mObj){
        MovieDetails movieEntry = new MovieDetails();

        movieEntry.movie_id = mObj.getId();
        movieEntry.posterPath = mObj.getPoster_path();
        movieEntry.adult = mObj.isAdult();
        movieEntry.overview = mObj.getOverview();
        movieEntry.releaseDate = mObj.getRelease_date();
        movieEntry.originalTitle = mObj.getOriginal_title();
        movieEntry.originalLanguage = mObj.getOriginalLanguage();
        movieEntry.Title = mObj.getTitle();
        movieEntry.backdropPath = mObj.getBackdrop_path();
        movieEntry.popularity = mObj.getPopularity();
        movieEntry.voteCount = mObj.getVote_count();
        movieEntry.voteAverage = mObj.getVote_average();
        movieEntry.favorite = mObj.isFavorite();

        context.getContentResolver().insert(MoviesTable.CONTENT_URI,MoviesTable.getContentValues(movieEntry,false));
    }


    public static void insertIntoFavoriteTable(Context context, int movieId){
        FavoriteMovies favoriteMovies = new FavoriteMovies();
        favoriteMovies.movie_id = movieId;
        context.getContentResolver().insert(FavoriteTable.CONTENT_URI,FavoriteTable.getContentValues(favoriteMovies,false));
    }


    public static void deleteFromFavoriteTable(Context context,int movieId){
        String[] args = new String[1];
        args[0] = String.valueOf(movieId);
        context.getContentResolver().delete(FavoriteTable.CONTENT_URI,"movie_id=?", args);
    }

    public static void getFavoritesList(Context context, ArrayList<Movie> mFavList){
        ArrayList<Movie> movList = new ArrayList<>();
        Cursor cursorFav = context.getContentResolver().query(FavoriteTable.CONTENT_URI,null,null,null,null);
        List<FavoriteMovies> favM = FavoriteTable.getRows(cursorFav,false);
        Cursor cursorMovies;
        for(int i=0; i<favM.size(); i++){
            Movie movie = new Movie();
            FavoriteMovies objFav = favM.get(i);
            String[] selectionArgs = new String[1];
            selectionArgs[0] = String.valueOf(objFav.movie_id);

            cursorMovies = context.getContentResolver().query(MoviesTable.CONTENT_URI,null,"movie_id=?",selectionArgs,null);
            MovieDetails movieRow = MoviesTable.getRow(cursorMovies,false);

            movie.setBackdrop_path(movieRow.backdropPath);
            movie.setId(movieRow.movie_id);
            movie.setOriginal_title(movieRow.originalTitle);
            movie.setOverview(movieRow.overview);
            movie.setRelease_date(movieRow.releaseDate);
            movie.setPoster_path(movieRow.posterPath);
            movie.setPopularity(movieRow.popularity);
            movie.setTitle(movieRow.Title);
            movie.setVote_average(movieRow.voteAverage);
            movie.setVote_count(movieRow.voteCount);
            movie.setAdult(movieRow.adult);
            movie.setOriginalLanguage(movieRow.originalLanguage);
            movie.setFavorite(TRUE);

            mFavList.add(movie);
        }
    }


    public static void updateMovieDetailFavColumn(Context context, int movieId, int flag){
        String[] updateArg = new String[1];
        updateArg[0] = String.valueOf(movieId);
        ContentValues values = new ContentValues();
        values.put("favorite", flag);
// update the Table's favorite column.
        context.getContentResolver().update(MoviesTable.CONTENT_URI,values,"movie_id=?",updateArg);
    }


    public static void getMovies(Context context, Movie movie){
        String[] queryArg = new String[1];
        if(movie != null) {
            queryArg[0] = String.valueOf(movie.getId());
            //queryArg[0] = String.valueOf(284052);

            Cursor movieRow = context.getContentResolver().query(MoviesTable.CONTENT_URI,null,"movie_id=?",queryArg,null);
            MovieDetails movieResult = MoviesTable.getRow(movieRow,false);

            movie.setBackdrop_path(movieResult.backdropPath);
            movie.setId(movieResult.movie_id);
            movie.setOriginal_title(movieResult.originalTitle);
            movie.setOverview(movieResult.overview);
            movie.setRelease_date(movieResult.releaseDate);
            movie.setPoster_path(movieResult.posterPath);
            movie.setPopularity(movieResult.popularity);
            movie.setTitle(movieResult.Title);
            movie.setVote_average(movieResult.voteAverage);
            movie.setVote_count(movieResult.voteCount);
            movie.setAdult(movieResult.adult);
            movie.setOriginalLanguage(movieResult.originalLanguage);
            movie.setFavorite(movieResult.favorite);
        }
    }

    public static void makeToast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }


}
