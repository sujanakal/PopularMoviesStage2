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
            Helper.getTrailers(url_trailers,movieTrailers);
            return null;
        }

        @Override
        protected void onPostExecute(Void v){
            //loadTrailersAdapter(movieTrailers);
            loadMovieTrailers(movieTrailers);
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


    // TODO: 01-12-2016 move this method to Helper class in Utility package. 
    
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
