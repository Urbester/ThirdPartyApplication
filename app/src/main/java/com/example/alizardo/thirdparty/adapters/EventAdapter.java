package com.example.alizardo.thirdparty.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alizardo.thirdparty.R;
import com.example.alizardo.thirdparty.activities.EventOverviewActivity;
import com.example.alizardo.thirdparty.pojo.Event;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Eduardo Silva on 27/12/2016.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private List<Event> mDataset;
    private int pos;
    private String token;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cv;
        public TextView title;
        public ImageView url;
        private View v;
        private int pos;
        private List<Event> mDataset;
        private String token;

        public ViewHolder(String t, View v, List<Event> ds) {
            super(v);
            mDataset = ds;
            this.token = t;
            title = (TextView) v.findViewById(R.id.title);
            url = (ImageView) v.findViewById(R.id.pic);
            cv = (CardView) v.findViewById(R.id.card_view);
            this.v = v;

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    Bundle b = new Bundle();
                    int pos = (int) v.findViewById(R.id.title).getTag();
                    b.putInt("id", Integer.parseInt(mDataset.get(pos).getId()));
                    b.putString("title", mDataset.get(pos).getTitle());
                    b.putSerializable("evt",mDataset.get(pos).isHost());
                    b.putString("description", mDataset.get(pos).getDescription());
                    b.putString("startDate", mDataset.get(pos).getStartDate());
                    b.putString("endDate", mDataset.get(pos).getEndDate());
                    b.putString("maxGuests", mDataset.get(pos).getMaxGuests());
                    b.putString("slotsLeft", mDataset.get(pos).getSlotsLeft());
                    b.putString("host_name", mDataset.get(pos).getHostName());
                    b.putString("host_email", mDataset.get(pos).getHostEmail());
                    b.putString("host_URL", mDataset.get(pos).getHostURL());
                    b.putString("url", mDataset.get(pos).getUrl());
                    Intent i = new Intent(activity, EventOverviewActivity.class);
                    i.putExtras(b);
                    i.putExtra("token", token);
                    activity.startActivity(i);
                }
            });

        }

        public View getV() {
            return v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public EventAdapter(String token, List<Event> myDataset) {
        this.token = token;
        this.mDataset = myDataset;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        ViewHolder vh = new ViewHolder(this.token, v, this.mDataset);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(mDataset.get(position).getTitle());
        holder.title.setTag(position);
        Context context = holder.url.getContext();
        Picasso.with(context).load(mDataset.get(position).getUrl()).into(holder.url);
        holder.url.setTag(mDataset.get(position).getUrl());
        this.pos = position;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}