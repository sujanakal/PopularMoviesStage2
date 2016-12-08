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
import android.widget.Toast;

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
        movieReviews = getArguments().getParcelableArrayList("ReviewArrayList");

        if(movieReviews.isEmpty()){
            Toast.makeText(getActivity(),"No Reviews found.",Toast.LENGTH_LONG).show();
        }
        else{
            Helper.loadMovieReviews(getActivity(), movieReviews, parent);
        }

        return rootView;
    }
    /*@Override
    public void onStart()
    {
        super.onStart();
        movieReviews = getArguments().getParcelableArrayList("ReviewArrayList");
        Helper.loadMovieReviews(getActivity(), movieReviews, parent);
    }*/

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

   /* public void loadReviewAdapter(ArrayList<Reviews> mRev)
    {
        ListView reviewList = (ListView) getView().findViewById(R.id.review_list);

        ReviewAdapter reviewAdapter = new ReviewAdapter(getActivity(),mRev);
        reviewList.setAdapter(reviewAdapter);

        *//*for(int i=0; i<mRev.size();i++){
            Reviews reviews = mRev.get(i);
            movieReviews(reviews);
        }*//*
    }*/

}
