package com.example.android.popularmoviez.Fragment;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.*;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmoviez.Model.Movie;
import com.example.android.popularmoviez.R;
import com.squareup.picasso.Picasso;

import static android.content.Context.MODE_PRIVATE;

public class MovieDetailFragment extends Fragment {
    SharedPreferences favoriteSharedPreferences ;
    Editor favEditor;
    boolean favoriteButtonState;
    String TAG = MovieDetailFragment.class.getSimpleName();
    static String FAV_STATE = "favState";


    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putBoolean(FAV_STATE,favoriteButtonState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        if(savedInstanceState!= null){
            favoriteButtonState = savedInstanceState.getBoolean(FAV_STATE);
            saveInSharedPreference(favoriteButtonState);
        }
        super.onActivityCreated(savedInstanceState);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.collapsible_detail_fragment, container, false);

    }

    @Override
    public void onStart() {
        super.onStart();
        movieDetails();
        favoriteSharedPreferences = getContext().getSharedPreferences("FavoritePref", MODE_PRIVATE);
        favEditor = favoriteSharedPreferences.edit();
        final FloatingActionButton floatingActionButton = (FloatingActionButton) getView().findViewById(R.id.favoriteButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!favoriteButtonState){
                    floatingActionButton.setImageResource(R.drawable.ic_heart);
                    favoriteButtonState = true;
                    saveInSharedPreference(favoriteButtonState);
                }
                else{
                    floatingActionButton.setImageResource(R.drawable.ic_heart_outline);
                    favoriteButtonState = false;
                    saveInSharedPreference(favoriteButtonState);
                }
            }
        });
    }

    public void saveInSharedPreference(boolean val){
        favEditor.putBoolean("favorite",val);
        favEditor.commit();
    }

    private void movieDetails() {
        // trailerList = (ListView) getView().findViewById(R.id.trailer_list);
        //Intent getIn = getIntent();
        Movie getMovie = getArguments().getParcelable("Movie");

        Log.d(TAG, "getMovie content: " + getMovie);
        CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) getView().findViewById(R.id.collapsing_toolbar_layout);


        if (getMovie != null) {

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

       /* class DetailTask extends AsyncTask<String, Void, Void>
        {

            @Override
            protected Void doInBackground(String... params) {
                Movie mv = new Movie();

                String url_trailers = "https://api.themoviedb.org/3/movie/"+movieId+"/videos?api_key=";
                getTrailers(url_trailers,movieTrailers);
                getReviews(url_reviews,movieReviews);
                return null;
            }

            @Override
            protected void onPostExecute(Void v)
            {

                loadTrailersAdapter(movieTrailers);
            }
        }

        DetailTask task = new DetailTask();
        task.execute();
    }*/







   /* public void getTrailers(String trailers, ArrayList<Trailers> trailerList)
    {
        BufferedReader buf = null;
        HttpsURLConnection http = null;

        String input = null;

        try {
            URL url = new URL(trailers + apiKey);

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
                final  String RESULTS_JSON = "results";
                final  String ISO_3166_1 = "iso_3166_1";
                final  String ISO_639_1 = "iso_639_1";
                final  String KEY = "key";
                final  String NAME = "name";
                final  String SITE = "site";
                final  String SIZE = "size";
                final  String TYPE = "type";

                JSONObject jsonObject = new JSONObject(input);
                JSONArray results = jsonObject.getJSONArray(RESULTS_JSON);

                for (int i = 0; i < results.length(); i++) {
                    JSONObject iObject = results.getJSONObject(i);
                    Trailers iTrailers = new Trailers();

                    iTrailers.setIso_3166_1(iObject.getString(ISO_3166_1));
                    iTrailers.setIso_639_1(iObject.getString(ISO_639_1));
                    iTrailers.setKey(iObject.getString(KEY));
                    iTrailers.setName(iObject.getString(NAME));
                    iTrailers.setSite(iObject.getString(SITE));
                    iTrailers.setSize(iObject.getInt(SIZE));
                    iTrailers.setType(iObject.getString(TYPE));
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




    public class TrailerAdapter extends ArrayAdapter<Trailers>
    {
        Context context;
        ArrayList<Trailers> trailersArrayList = new ArrayList<Trailers>();
        Trailers trailers = new Trailers();

        public TrailerAdapter(Context context,ArrayList<Trailers> trailersArrayList) {
            super(context,R.layout.trailers);
            this.context=context;
            this.trailersArrayList=trailersArrayList;
        }
        @Override
        public Trailers getItem(int position)
        {
            return trailersArrayList.get(position);
        }


        @Override
//      Getting the size of the arrayList
        public int getCount(){
            return trailersArrayList.size();
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            myTrailerHolder viewHolder;

            if (convertView == null){

                convertView = LayoutInflater.from(context).inflate(R.layout.reviews,parent,false);

                viewHolder = new myTrailerHolder();
                viewHolder.text = (TextView) convertView.findViewById(R.id.trailer_text);
                viewHolder.icon = (ImageView) convertView.findViewById(R.id.trailer_icon);
                convertView.setTag(viewHolder);
            }
            else
            {
                viewHolder = (myTrailerHolder) convertView.getTag();
            }

            String type;
            if((type=trailers.getType())=="Trailer")
            viewHolder.text.setText(trailers.getName());
           // viewHolder.content.setText(reviews.getContent());

            return convertView;
        }

    }




    public void loadTrailersAdapter(ArrayList<Trailers> mTrail)
    {
        TrailerAdapter trailerAdapter = new TrailerAdapter(getContext(),mTrail);
       // trailerList.setAdapter(trailerAdapter);
    }



    public class myTrailerHolder{
        TextView text;
        ImageView icon;
    }*/


    }
}