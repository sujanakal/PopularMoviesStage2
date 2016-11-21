package com.example.android.popularmoviez.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmoviez.Model.Trailers;
import com.example.android.popularmoviez.R;

import java.util.ArrayList;

/**
 * Created by Suj🌠 on 21-11-2016.
 */

public class TrailerAdapter extends ArrayAdapter<Trailers> {
    private Context context;
    private ArrayList<Trailers> mTrailers;
    int count=0;

    public TrailerAdapter(Context context,ArrayList<Trailers> mTrailers){
        super(context, R.layout.trailers);
        this.context = context;
        this.mTrailers = mTrailers;
    }

    @Override
    public Trailers getItem(int position){
        return mTrailers.get(position);
    }


    @Override
    public int getCount(){
        return mTrailers.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Trailers trailers = mTrailers.get(position);
        myTrailerHolder viewHolder;
        String TYPE = "Trailer";
        String trailerText = "Trailer ";

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.trailers,parent,false);

            viewHolder = new myTrailerHolder();
            viewHolder.tDisplayPicture = (ImageView) convertView.findViewById(R.id.trailer_icon);
            viewHolder.tText = (TextView) convertView.findViewById(R.id.trailer_text);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (myTrailerHolder) convertView.getTag();
        }

        if(trailers.getType() == TYPE){
            count++;
            viewHolder.tText.setText(trailerText + count);
        }
        viewHolder.tDisplayPicture.setImageResource(R.drawable.ic_heart);
        return convertView;
    }
}

class myTrailerHolder{
    ImageView tDisplayPicture;
    TextView tText;
}
