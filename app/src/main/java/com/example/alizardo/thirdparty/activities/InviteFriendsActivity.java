package com.example.alizardo.thirdparty.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alizardo.thirdparty.R;
import com.example.alizardo.thirdparty.adapters.InviteAdapter;
import com.example.alizardo.thirdparty.pojo.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InviteFriendsActivity extends AppCompatActivity {



    private ListView listView;
    private ArrayList<Event> postArrayList;

    JSONArray jsonArray;

    private JSONObject data;
    private String token;
    private TextView friendName;
    private ImageView friendPic;
    private ImageView url;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent() != null)
            try {
                this.token = getIntent().getStringExtra("token");
                this.data = new JSONObject(getIntent().getStringExtra("map"));
                this.id = getIntent().getIntExtra("id",0);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        setContentView(R.layout.activity_invite_friends);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Invite Friends");


        listView = (ListView) findViewById(R.id.listView);

        jsonArray = null;


        try {
            jsonArray = this.data.getJSONArray("Result");

        } catch (JSONException e) {
            e.printStackTrace();
        }

       listFriends();
    }

    private void listFriends() {
        InviteAdapter mAdapter;
        RecyclerView mRecyclerView;
        LinearLayoutManager mLayoutManager;

        mRecyclerView = (RecyclerView) findViewById(R.id.users_pending);

        // specify an adapter
        mAdapter = new InviteAdapter(token, id, jsonArray);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        onBackPressed();
        return true;
    }
}



