package com.example.android.popularmoviez.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmoviez.Model.Trailers;
import com.example.android.popularmoviez.R;

import java.util.ArrayList;

/**
 * Created by Suj🌠 on 21-11-2016.
 */

public class TrailerRecyclerAdapter extends RecyclerView.Adapter<TrailerRecyclerAdapter.tRecyclerViewHolder> {

    ArrayList<Trailers> trailers;
    Trailers trailerItem;

    public TrailerRecyclerAdapter(Context context, ArrayList<Trailers> trailers){
        this.trailers = trailers;
    }

    @Override
    public TrailerRecyclerAdapter.tRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailers_recyclerview,parent,false);
        tRecyclerViewHolder viewHolder = new tRecyclerViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrailerRecyclerAdapter.tRecyclerViewHolder holder, int position) {
        trailerItem = trailers.get(position);
        holder.icon.setImageResource(R.drawable.ic_heart);
        holder.text.setText(trailerItem.getName());
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    class tRecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView text;

        public tRecyclerViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.trailer_icon);
            text = (TextView) itemView.findViewById(R.id.trailer_text);
        }
    }
}


