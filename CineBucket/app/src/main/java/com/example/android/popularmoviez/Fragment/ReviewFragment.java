package com.example.android.popularmoviez.Fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.popularmoviez.Adapters.ReviewAdapter;
import com.example.android.popularmoviez.ApiKey;
import com.example.android.popularmoviez.Model.Reviews;
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
 * Created by Suj🌠 on 25-08-2016.
 */
public class ReviewFragment extends Fragment {

    String TAG = ReviewFragment.class.getSimpleName();
    ArrayList<Reviews> movieReviews = new ArrayList<>();
    ApiKey key = new ApiKey();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.review_fragment,container,false);
    }
    @Override
    public void onStart()
    {
        super.onStart();
        DetailTask task = new DetailTask();
        task.execute();
        movieReviews();
    }
    public void movieReviews()
    {


    }
    class DetailTask extends AsyncTask<String, Void, Void>
    {

        int MovieId = getArguments().getInt("MovieId");
        @Override
        protected Void doInBackground(String... params) {
            String url_reviews = "https://api.themoviedb.org/3/movie/"+MovieId+"/reviews?api_key=";
           // String url_trailers = "https://api.themoviedb.org/3/movie/"+MovieId+"/videos?api_key=";
           // getTrailers(url_trailers,movieTrailers);
            getReviews(url_reviews,movieReviews);
            return null;
        }

        @Override
        protected void onPostExecute(Void v)
        {
            loadReviewAdapter(movieReviews);
            //loadTrailersAdapter(movieTrailers);
        }
    }

    public void getReviews(String reviews, ArrayList<Reviews> reviewList) {
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
                final  String RESULTS_JSON = "results";
                final  String AUTHOR = "author";
                final  String URL = "url";
                final  String CONTENT = "content";

                JSONObject jsonObject = new JSONObject(input);
                JSONArray results = jsonObject.getJSONArray(RESULTS_JSON);

                for (int i = 0; i < results.length(); i++) {
                    JSONObject iObject = results.getJSONObject(i);
                    Reviews iReviews = new Reviews();

                    iReviews.setAuthor(iObject.getString(AUTHOR));
                    iReviews.setContent(iObject.getString(CONTENT));
                    iReviews.setUrl(iObject.getString(URL));

                    reviewList.add(iReviews);
                }
               Log.d(TAG, "getReviews: "+results);
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

    public void loadReviewAdapter(ArrayList<Reviews> mRev)
    {
        ListView reviewList = (ListView) getView().findViewById(R.id.review_list);

        ReviewAdapter reviewAdapter = new ReviewAdapter(getActivity(),mRev);
        reviewList.setAdapter(reviewAdapter);
    }






}