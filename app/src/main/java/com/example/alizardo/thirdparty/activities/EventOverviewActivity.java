package com.example.alizardo.thirdparty.activities;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alizardo.thirdparty.R;
import com.squareup.picasso.Picasso;


public class EventOverviewActivity extends AppCompatActivity {

    private TextView host;
    private TextView description;
    private TextView startDate;
    private TextView endDate;
    private ImageView url;
    private TextView maxGuests;
    private TextView slotsLeft;
    private ImageView userPic;
    private TextView userName;
    private TextView userEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_overview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                FloatingActionButton btn = (FloatingActionButton) view.findViewById(R.id.fab);
                btn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.accept)));
                btn.setImageResource(R.drawable.check_circle);
            }
        });


        View v = (View) findViewById(R.id.content_event_overview);

        Bundle b = getIntent().getExtras();

        this.host = (TextView) v.findViewById(R.id.host);
        this.description = (TextView) v.findViewById(R.id.description);
        this.startDate = (TextView) v.findViewById(R.id.startDate);
        this.endDate = (TextView) v.findViewById(R.id.endDate);
        this.maxGuests = (TextView) v.findViewById(R.id.maxGuests);
        this.slotsLeft = (TextView) v.findViewById(R.id.slotsLeft);
        this.userName = (TextView) v.findViewById(R.id.userDetailName);
        this.userEmail = (TextView) v.findViewById(R.id.userDetailEmail);

        this.userPic = (ImageView) v.findViewById(R.id.userDetailPic);

        this.url = (ImageView) v.findViewById(R.id.pic);

        this.host.setText(b.getString("host"));
        this.description.setText(b.getString("description"));
        this.startDate.setText(b.getString("startDate"));
        this.endDate.setText(b.getString("endDate"));
        this.maxGuests.setText(b.getString("maxGuests"));
        this.slotsLeft.setText(b.getString("slotsLeft"));
        this.userName.setText(b.getString("host_name"));
        this.userEmail.setText(b.getString("host_email"));

        Context context = getApplicationContext();
        Picasso.with(context).load(b.getString("host_URL")).into(this.userPic);
        Picasso.with(context).load(b.getString("url")).into(this.url);

        setTitle(b.getString("title"));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        onBackPressed();
        return true;
    }

}
