package com.example.android.popularmoviez.Fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.example.android.popularmoviez.Adapters.ReviewAdapter;
import com.example.android.popularmoviez.Model.Reviews;
import com.example.android.popularmoviez.R;
import com.example.android.popularmoviez.Utility.Helper;
import java.util.ArrayList;

/**
 * Created by Suj🌠 on 25-08-2016.
 */

public class ReviewFragment extends Fragment {

    String TAG = ReviewFragment.class.getSimpleName();
    ArrayList<Reviews> movieReviews = new ArrayList<>();
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
            Helper.getReviews(url_reviews,movieReviews);
            return null;
        }

        @Override
        protected void onPostExecute(Void v)
        {
            //loadReviewAdapter(movieReviews);
            Helper.loadMovieReviews(getActivity(), movieReviews, parent);
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


    /*public void loadMovieReviews(ArrayList<Reviews> mRev){
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
    }*/
}
