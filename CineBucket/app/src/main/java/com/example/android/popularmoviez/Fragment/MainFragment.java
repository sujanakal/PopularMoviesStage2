package com.example.android.popularmoviez.Fragment;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.android.popularmoviez.Activity.MainActivity;
import com.example.android.popularmoviez.Activity.MovieDetailActivity;
import com.example.android.popularmoviez.Activity.myPreferenceActivity;
import com.example.android.popularmoviez.Utility.ApiKey;
import com.example.android.popularmoviez.Model.Movie;
import com.example.android.popularmoviez.R;
import com.example.android.popularmoviez.Utility.Helper;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;


public class MainFragment extends Fragment implements AdapterView.OnItemClickListener {


    GridView myGrid;
    ArrayList<Movie> mPopularList = new ArrayList<>();
    ArrayList<Movie> mTopVotedList = new ArrayList<>();
    ArrayList<Movie> mFavoriteList = new ArrayList<>();
    final static String POPULAR_LIST = "popularList";
    final static String TOP_VOTE_LIST = "topVoteList";
    final static String FAVORITE_LIST = "favoriteList";
    String progressDialogMessage = "Loading... Please wait...";
    ApiKey key = new ApiKey();
    final static int FALSE = 0;

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putSerializable(POPULAR_LIST,mPopularList);
        outState.putSerializable(TOP_VOTE_LIST,mTopVotedList);
        outState.putSerializable(FAVORITE_LIST,mFavoriteList);
        super.onSaveInstanceState(outState);

    }


    @Override
    public void onViewStateRestored(Bundle savedInstanceState){

        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null){
            mPopularList = (ArrayList<Movie>) savedInstanceState.getSerializable(POPULAR_LIST);
            mTopVotedList = (ArrayList<Movie>) savedInstanceState.getSerializable(TOP_VOTE_LIST);
            mFavoriteList = (ArrayList<Movie>) savedInstanceState.getSerializable(FAVORITE_LIST);
            loadMovies();
        }
    }


    @Override
    public void onResume(){
        super.onResume();

        if (mPopularList == null || mTopVotedList == null)
        {
            MovieTask task = new MovieTask();
            task.execute();
        }
        else
        {
            loadMovies();
        }

        myGrid.setOnItemClickListener(MainFragment.this);



    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment_xml);

        myGrid = (GridView) findViewById(R.id.movie_grid);

        *//*Toolbar appbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(appbar);
        getWindow().setStatusBarColor(getResources().getColor(R.color.statusBar));*//*

        MovieTask task = new MovieTask();

        if(Helper.isOnline(getApplicationContext())){
            task.execute();
        }

    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.main_fragment_xml, container, false);
        myGrid = (GridView) rootView.findViewById(R.id.movie_grid);
        MovieTask task = new MovieTask();

        if(Helper.isOnline(getActivity())){
            task.execute();
        }
        return rootView;
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        Movie movie = (Movie) parent.getAdapter().getItem(position);
        /*Toast.makeText(MainFragment.this, "" + movie.getTitle(),
                Toast.LENGTH_SHORT).show();*/
        Helper.getMovies(getActivity(), movie);
//              To open the new activity (detailed activity)
//              when a image in the gridview is clicked using Intents

        Intent intent = new Intent(getActivity() , MovieDetailActivity.class);
        intent.putExtra("Movie", movie);
        startActivity(intent);

           /* Bundle bundle = new Bundle();
            bundle.putParcelable("Movie",movie);
            MovieDetailFragment fragment = new MovieDetailFragment();
           fragment.setArguments(bundle);*/

    }


    public class MovieTask extends AsyncTask<String, Void, Void> {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        @Override
        protected void onPreExecute(){

            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage(progressDialogMessage);
            progressDialog.setIndeterminate(true);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {


//          string variables to hold the api request urls for popular movies and top rated movies

            String url_popularity = "https://api.themoviedb.org/3/movie/popular?api_key=";
            String url_topvote = "https://api.themoviedb.org/3/movie/top_rated?api_key=";

            // TODO: 01-12-2016 add the code to insert the values into the MovieDetail table.
            getJson(url_popularity,mPopularList);
            getJson(url_topvote,mTopVotedList);


            return null;

        }


            @Override
        protected void onPostExecute(Void v) {
                progressDialog.dismiss();
//              Calling function to load from the sharedpreferences
                loadMovies();
        }
    }



//  Function to get the value of the setting stored
//  in the default Sharedpreference file  and then to load the adapter
    private void loadMovies() {

        if((mPopularList == null) || mTopVotedList == null){
            MovieTask movieTask = new MovieTask();
            movieTask.execute();
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
//      If the sort by option choosen by the user is Popularity load the adapter with the mPopularList arraylist
        if (sharedPreferences.getString("PREF_FILE","Popularity").equals("Popularity"))
        {
             loadMovieImageAdapter(mPopularList);
        }
        else if(sharedPreferences.getString("PREF_FILE","Popularity").equals("Favorite")){
            mFavoriteList.clear();
            Helper.getFavoritesList(getActivity(),mFavoriteList);
            loadMovieImageAdapter(mFavoriteList);
        }
//      If the sort by option choosen by the user is Ratings load the adapter with the mTopVotedList arraylist
        else
        {
             loadMovieImageAdapter(mTopVotedList);
        }
    }


//  Function to load the array adapter for given setting
    private void loadMovieImageAdapter(ArrayList<Movie> mArrayList) {
        MovieImageAdapter adapter = new MovieImageAdapter(getActivity(),mArrayList);
//      Setting the adapter to display the items on the gridview using the gridview object myGrid
        myGrid.setAdapter(adapter);

    }


//  Function to do the JSON parsing
    public  void getJson(String web_address, ArrayList<Movie> list){

//      The API key

        BufferedReader buf = null;
        HttpsURLConnection http = null;

//      This will hold the parsed json response
        String input = null;

//      To set up the the http connection does to get connected to internet
        try{
            URL url = new URL(web_address + key.getApiKey());

            http = (HttpsURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.connect();


            InputStream in = http.getInputStream();

            if (in == null){
             //No json response is got
;
            }

            buf = new BufferedReader(new InputStreamReader(in));

            String jsonData;
            StringBuilder sb = new StringBuilder();

//          To append the new line or to distinguish each json object for each movie
            if ((jsonData = buf.readLine()) != null) {
                sb.append(jsonData + "\n");
            }

            if (sb.length() == 0) {
//              Empty stream

            }

            input = sb.toString();


            if (input != null) {

                final  String ID = "id";
                final  String RESULTS_JSON = "results";
                final  String POSTER_PATH = "poster_path";
                final  String OVERVIEW = "overview";
                final  String MOVIE_TITLE = "title";
                final  String RELEASE_D = "release_date";
                final  String POPULARITY = "popularity";
                final  String BACKDROP_PATH = "backdrop_path";
                final  String ORIGINAL_TITLE = "original_title";
                final  String V_AVERAGE = "vote_average";
                final  String V_COUNT = "vote_count";
                final  String ADULT = "adult";
                final  String ORIGINAL_LANGUAGE = "original_language";

//              To get the main json object
                JSONObject jsonObject = new JSONObject(input);

//              To get the "results" json array which has all the movie details
                JSONArray results = jsonObject.getJSONArray(RESULTS_JSON);

//              To get the details of each movie within the "results" json array
                for (int i = 0; i < results.length(); i++) {

                    JSONObject iObject = results.getJSONObject(i);

//                  To store the details of each movie in objects of class Movie

                    Movie iMovie = new Movie();

                    iMovie.setBackdrop_path(iObject.getString(BACKDROP_PATH));
                    iMovie.setId(iObject.getInt(ID));
                    iMovie.setOriginal_title(iObject.getString(ORIGINAL_TITLE));
                    iMovie.setOverview(iObject.getString(OVERVIEW));
                    iMovie.setRelease_date(iObject.getString(RELEASE_D));
                    iMovie.setPoster_path(iObject.getString(POSTER_PATH));
                    iMovie.setPopularity(iObject.getDouble(POPULARITY));
                    iMovie.setTitle(iObject.getString(MOVIE_TITLE));
                    iMovie.setVote_average(iObject.getDouble(V_AVERAGE));
                    iMovie.setVote_count(iObject.getInt(V_COUNT));
                    iMovie.setAdult(iObject.getBoolean(ADULT));
                    iMovie.setOriginalLanguage(iObject.getString(ORIGINAL_LANGUAGE));
                    iMovie.setFavorite(FALSE);

                    list.add(iMovie);

                    Helper.insertIntoMovieDetailsTable(getActivity(),iMovie);
                }
            }


        }

        catch (MalformedURLException e) {
            e.printStackTrace();
        }

//      catch block for getting json response
        catch (IOException e) {
            e.printStackTrace();
        }

        catch (JSONException e) {
            e.printStackTrace();
        }

//      for disconnecting the connection
        finally {

            if (http != null)
                http.disconnect();

            if (buf != null){
                try {
                    buf.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public class MovieImageAdapter extends ArrayAdapter<Movie> {

        private  Context context;
        private final ArrayList<Movie> movieObject;


        public MovieImageAdapter(Context context,ArrayList<Movie> movieObject) {

            super(context,R.layout.popular_movies);
            this.context = context;
            this.movieObject = movieObject;

        }
        @Override
        public Movie getItem(int position)
        {
            return movieObject.get(position);
        }


        @Override
//      Getting the size of the arrayList
        public int getCount(){
            return movieObject.size();
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185/";
            myViewHolder viewHolder;

            Movie img = movieObject.get(position);

            if (convertView == null){

                convertView = LayoutInflater.from(context).inflate(R.layout.popular_movies,parent,false);

                viewHolder = new myViewHolder();
                viewHolder.imageview = (ImageView) convertView.findViewById(R.id.popularMovies);
                convertView.setTag(viewHolder);
            }
            else
            {
               viewHolder = (myViewHolder) convertView.getTag();
            }


//          Loading the image or the poster of the movie to image views in the gridview using Picasso
            Picasso
                    .with(context)
                    .load(IMAGE_BASE_URL+img.getPoster_path())
                    .into(viewHolder.imageview);


            return convertView;
        }


        }

        public class myViewHolder{
            ImageView imageview;
        }
}

