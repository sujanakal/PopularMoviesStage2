package com.example.android.popularmoviez.Fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.popularmoviez.Adapters.ReviewAdapter;
import com.example.android.popularmoviez.Util.ApiKey;
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
    LinearLayout parent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //View rootView = inflater.inflate(R.layout.review_fragment,container,false);
        View rootView = inflater.inflate(R.layout.review_dynamiclayout,container,false);
        parent = (LinearLayout) rootView.findViewById(R.id.reviewCustomLayout);
        return rootView;
    }
    @Override
    public void onStart()
    {
        super.onStart();
        DetailTask task = new DetailTask();
        task.execute();
    }
   /* public void movieReviews(Reviews reviews)
    {
        LinearLayout revFragmentLayout = (LinearLayout) getView().findViewById(R.id.Review_fragment_linear_layout);
        TextView authorName = new TextView(getContext());
        TextView content = new TextView(getContext());
        authorName.setText(reviews.getAuthor());
        content.setText(reviews.getContent());

        revFragmentLayout.addView(authorName);
        revFragmentLayout.addView(content);
        revFragmentLayout.setDividerPadding(10);
    }*/
    class DetailTask extends AsyncTask<String, Void, Void>
    {

        int MovieId = getArguments().getInt("MovieId");
        @Override
        protected Void doInBackground(String... params) {
            String url_reviews = "https://api.themoviedb.org/3/movie/"+MovieId+"/reviews?api_key=";
            getReviews(url_reviews,movieReviews);
            return null;
        }

        @Override
        protected void onPostExecute(Void v)
        {
            //loadReviewAdapter(movieReviews);
            loadMovieReviews(movieReviews);
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

        /*for(int i=0; i<mRev.size();i++){
            Reviews reviews = mRev.get(i);
            movieReviews(reviews);
        }*/
    }


    public void loadMovieReviews(ArrayList<Reviews> mRev){
        Reviews mReviews;
        for (int i=0; i<mRev.size();i++){
            mReviews = mRev.get(i);
            LinearLayout child = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.reviews,null);
            TextView rAuthor = (TextView) child.findViewById(R.id.review_author);
            TextView rContent = (TextView) child.findViewById(R.id.review_content);
            rAuthor.setText(mReviews.getAuthor());
            rContent.setText(mReviews.getContent());
            parent.addView(child);
        }
    }
}
