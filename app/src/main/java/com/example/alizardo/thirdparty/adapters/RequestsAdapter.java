package com.example.alizardo.thirdparty.adapters;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alizardo.thirdparty.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.ViewHolder> {
    private JSONArray users;
    private int pos;
    private String token;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private View v;
        private JSONArray users;
        private String token;
        private ImageView url;
        private TextView name;
        private TextView email;
        private FloatingActionButton accept, reject;

        public ViewHolder(String t, View v, JSONArray u) {
            super(v);
            this.users = u;
            this.token = t;
            this.v = v;

            url = (ImageView) v.findViewById(R.id.users_layout_userDetailPic);
            name = (TextView) v.findViewById(R.id.users_layout_userDetailName);
            email = (TextView) v.findViewById(R.id.users_layout_userDetailEmail);
            accept = (FloatingActionButton) v.findViewById(R.id.accept_user);
            reject = (FloatingActionButton) v.findViewById(R.id.reject_user);

            //TODO colocar aqui os buttons



        }

        public View getV() {
            return v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RequestsAdapter(String token, JSONArray users) {
        this.token = token;
        this.users = users;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return users.length();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_layout, parent, false);
        ViewHolder vh = new ViewHolder(this.token, v, this.users);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            JSONObject u = (JSONObject) users.get(position);
            // Change pic
            Context context = holder.url.getContext();
            Picasso.with(context).load(u.getString("pic")).into(holder.url);
            holder.url.setTag(u.getString("pic"));
            // Change name
            holder.name.setText(u.getString("name"));
            // Change email
            holder.email.setText(u.getString("email"));

            this.pos = position;

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}