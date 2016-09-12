package com.example.android.popularmoviez.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.popularmoviez.Model.Reviews;
import com.example.android.popularmoviez.R;

import java.util.ArrayList;

/**
 * Created by Suj🌠 on 12-09-2016.
 */
public class ReviewAdapter extends ArrayAdapter<Reviews> {
    private Context context;
    private ArrayList<Reviews> mReviews;

    public ReviewAdapter(Context context, ArrayList<Reviews> mReviews) {
        super(context, R.layout.reviews);
        this.context=context;
        this.mReviews=mReviews;
    }
    @Override
    public Reviews getItem(int position)
    {
        return mReviews.get(position);
    }


    @Override
//      Getting the size of the arrayList
    public int getCount(){
        return mReviews.size();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Reviews reviews = mReviews.get(position);
        myReviewHolder viewHolder;

        if (convertView == null){

            convertView = LayoutInflater.from(context).inflate(R.layout.reviews,parent,false);

            viewHolder = new myReviewHolder();
            viewHolder.author = (TextView) convertView.findViewById(R.id.review_author);
            viewHolder.content = (TextView) convertView.findViewById(R.id.review_content);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (myReviewHolder) convertView.getTag();
        }


        viewHolder.author.setText(reviews.getAuthor());
       // viewHolder.author.setText("can you see me?");
        viewHolder.content.setText(reviews.getContent());

        return convertView;
    }
}

class myReviewHolder{
    TextView author;
    TextView content;
}
