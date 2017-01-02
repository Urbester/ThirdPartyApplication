package com.example.alizardo.thirdparty.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.alizardo.thirdparty.R;
import com.example.alizardo.thirdparty.adapters.RequestsAdapter;

import org.json.JSONArray;
import org.json.JSONException;

public class EventRequestsActivity extends AppCompatActivity {

    private String token;
    private JSONArray usersAccepted;
    private JSONArray usersInvited;
    private JSONArray usersPending;
    private JSONArray usersRejected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_requests);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Requests");

        this.token = getIntent().getStringExtra("token");
        try {
            this.usersAccepted = new JSONArray(getIntent().getStringExtra("usersAccepted"));
            this.usersInvited = new JSONArray(getIntent().getStringExtra("usersInvited"));
            this.usersPending = new JSONArray(getIntent().getStringExtra("usersPending"));
            this.usersRejected = new JSONArray(getIntent().getStringExtra("usersRejected"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        listUsersRejected();
        listUsersPending();
        listUsersInvited();
        listUsersAccepted();
    }


    private void listUsersAccepted() {
        RequestsAdapter mAdapter;
        RecyclerView mRecyclerView;
        LinearLayoutManager mLayoutManager;

        mRecyclerView = (RecyclerView) findViewById(R.id.users_accepted);

        // specify an adapter
        mAdapter = new RequestsAdapter(this.token, usersAccepted);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }


    private void listUsersInvited() {
        RequestsAdapter mAdapter;
        RecyclerView mRecyclerView;
        LinearLayoutManager mLayoutManager;

        mRecyclerView = (RecyclerView) findViewById(R.id.users_accepted);

        // specify an adapter
        mAdapter = new RequestsAdapter(this.token, usersInvited);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void listUsersRejected() {
        RequestsAdapter mAdapter;
        RecyclerView mRecyclerView;
        LinearLayoutManager mLayoutManager;

        mRecyclerView = (RecyclerView) findViewById(R.id.users_rejected);

        // specify an adapter
        mAdapter = new RequestsAdapter(this.token, usersRejected);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void listUsersPending() {
        RequestsAdapter mAdapter;
        RecyclerView mRecyclerView;
        LinearLayoutManager mLayoutManager;

        mRecyclerView = (RecyclerView) findViewById(R.id.users_pending);

        // specify an adapter
        mAdapter = new RequestsAdapter(this.token, usersPending);

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
