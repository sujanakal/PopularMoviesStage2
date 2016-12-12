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
import android.widget.Toast;

import com.example.android.popularmoviez.Model.Trailers;
import com.example.android.popularmoviez.R;
import com.example.android.popularmoviez.Utility.Helper;
import java.util.ArrayList;

/**
 * Created by Suj🌠 on 20-11-2016.
 */

public class TrailerFragment extends Fragment {
    ArrayList<Trailers> movieTrailers = new ArrayList<>();
    String TAG = "TrailerFragment";
    LinearLayout parent;
    String YOUTUBE_URL = "https://www.youtube.com/watch?v=";

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstantState){
        View rootView = layoutInflater.inflate(R.layout.trailers_dynamiclayout,container,false);
        parent = (LinearLayout) rootView.findViewById(R.id.trailerCustomParent);
        movieTrailers = getArguments().getParcelableArrayList("TrailerArrayList");

        if(movieTrailers.isEmpty()){
            Toast.makeText(getActivity(),"No Trailers found.",Toast.LENGTH_LONG).show();
        }
        else
            loadMovieTrailers(movieTrailers);
        return rootView;
    }
    
    public void loadMovieTrailers(ArrayList<Trailers> movieTrailers){
        Trailers mTrailers;
        for(int i=0;i<movieTrailers.size();i++){
            mTrailers=movieTrailers.get(i);
            final String youTubeUrl = YOUTUBE_URL+mTrailers.getKey();
            View child = LayoutInflater.from(getActivity()).inflate(R.layout.trailers,null);
            TextView tName = (TextView) child.findViewById(R.id.trailer_text);
            ImageView tIcon = (ImageView) child.findViewById(R.id.trailer_icon);
            tName.setText(mTrailers.getName());
            tIcon.setImageResource(R.drawable.trailer_icon_shadow_mdpi);
            Log.d("TrailerFragment","Key got: "+mTrailers.getKey());

            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youTubeUrl)));
                }
            });
            parent.addView(child);
        }
    }
}
