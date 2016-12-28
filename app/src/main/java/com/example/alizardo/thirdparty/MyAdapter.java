package com.example.alizardo.thirdparty;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Eduardo Silva on 27/12/2016.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Event> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cv;
        public TextView title;
        public TextView host;
        public TextView description;
        public TextView startDate;
        public TextView endDate;
        public TextView numGuests;
        public ImageView url;

        public ViewHolder(View v) {
            super(v);
            cv = (CardView) v.findViewById(R.id.card_view);
            title = (TextView) v.findViewById(R.id.title);
            host = (TextView) v.findViewById(R.id.host);
            description = (TextView) v.findViewById(R.id.description);
            startDate = (TextView) v.findViewById(R.id.startDate);
            endDate = (TextView) v.findViewById(R.id.endDate);
            numGuests = (TextView) v.findViewById(R.id.numGuests);
            url = (ImageView) v.findViewById(R.id.pic);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<Event> myDataset) {
        mDataset = myDataset;
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
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(mDataset.get(position).getTitle());
        holder.host.setText(mDataset.get(position).getHost());
        holder.description.setText(mDataset.get(position).getDescription());
        holder.startDate.setText(mDataset.get(position).getStartDate());
        holder.endDate.setText(mDataset.get(position).getEndDate());
        holder.numGuests.setText(mDataset.get(position).getNumGuests());

        // Load Image from URL
        Context context = holder.url.getContext();
        Picasso.with(context).load(mDataset.get(position).getUrl()).into(holder.url);
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}