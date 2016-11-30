package com.example.android.popularmoviez.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.popularmoviez.Utility.ApiKey;
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

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Suj🌠 on 20-11-2016.
 */

public class TrailerFragment extends Fragment {
    ArrayList<Trailers> movieTrailers = new ArrayList<>();
    ApiKey key = new ApiKey();
    String TAG = "TrailerFragment";
    LinearLayout parent;
    String YOUTUBE_URL = "https://www.youtube.com/watch?v=";

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstantState){
       //return layoutInflater.inflate(R.layout.trailer_fragment,container,false);
       // return layoutInflater.inflate(R.layout.trailers_recyclerview,container,false);
        View rootView = layoutInflater.inflate(R.layout.trailers_dynamiclayout,container,false);
        parent = (LinearLayout) rootView.findViewById(R.id.trailerCustomParent);
        return rootView;
    }
    @Override
    public void onStart(){
        super.onStart();
        TrailerTask trailerTask = new TrailerTask();
        trailerTask.execute();
    }

    class TrailerTask extends AsyncTask<String,Void, Void>{

        int MovieId = getArguments().getInt("MovieId");
        @Override
        protected Void doInBackground(String... params) {
            String url_trailers = "https://api.themoviedb.org/3/movie/"+MovieId+"/videos?api_key=";
            getTrailers(url_trailers,movieTrailers);
            return null;
        }

        @Override
        protected void onPostExecute(Void v){
            //loadTrailersAdapter(movieTrailers);
            loadMovieTrailers(movieTrailers);
        }
    }

    public void getTrailers(String trailers, ArrayList<Trailers> trailerList){
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
                final  String RESULTS_JSON = "results";
                final  String NAME = "name";
                final  String KEY = "key";
                final  String TYPE = "type";
                final  String ID = "id";
                final  String SITE = "site";
                final  String SIZE = "size";

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
                Log.d(TAG, "getTrailers: "+results);
                for(int i=0;i<trailerList.size();i++)
                    Log.d(TAG,"index " +i + ": " + trailerList.get(i).getName());
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


    public void loadTrailersAdapter(ArrayList<Trailers> movieTrailers){
        /*ListView trailerListView = (ListView) getView().findViewById(R.id.trailersListView);
        TrailerAdapter trailerAdapter = new TrailerAdapter(getContext(),movieTrailers);
        trailerListView.setNestedScrollingEnabled(false);
        trailerListView.setAdapter(trailerAdapter);*/


        /*RecyclerView trailerRecyclerView = (RecyclerView) getView().findViewById(R.id.trailersRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        trailerRecyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new TrailerRecyclerAdapter(getContext(),movieTrailers);
        trailerRecyclerView.setAdapter(adapter);*/
    }

    public void loadMovieTrailers(ArrayList<Trailers> movieTrailers){
        Trailers mTrailers;
        for(int i=0;i<movieTrailers.size();i++){
            mTrailers=movieTrailers.get(i);
            final String youTubeUrl = YOUTUBE_URL+mTrailers.getKey();
            View child = LayoutInflater.from(getContext()).inflate(R.layout.trailers,null);
            TextView tName = (TextView) child.findViewById(R.id.trailer_text);
            ImageView tIcon = (ImageView) child.findViewById(R.id.trailer_icon);
            tName.setText(mTrailers.getName());
            tIcon.setImageResource(R.drawable.trailer_icon_shadow_mdpi);
            Log.d("TrailerFragment","Key got: "+mTrailers.getKey());
            tIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youTubeUrl)));
                }
            });
            parent.addView(child);
        }
    }
}
