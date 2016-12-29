package com.example.alizardo.thirdparty;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class EventOverviewActivity extends AppCompatActivity {

    private TextView title;
    private TextView host;
    private TextView description;
    private TextView startDate;
    private TextView endDate;
    private TextView numGuests;
    private ImageView url;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_overview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View v = (View) findViewById(R.id.content_event_overview);

        Bundle b = getIntent().getExtras();

        this.title = (TextView) v.findViewById(R.id.title);
        this.host = (TextView) v.findViewById(R.id.host);
        this.description = (TextView) v.findViewById(R.id.description);
        this.startDate = (TextView) v.findViewById(R.id.startDate);
        this.endDate = (TextView) v.findViewById(R.id.endDate);
        this.numGuests = (TextView) v.findViewById(R.id.numGuests);
        this.url = (ImageView) v.findViewById(R.id.pic);

        this.host.setText(b.getString("host"));
        this.description.setText(b.getString("description"));
        this.startDate.setText(b.getString("startDate"));
        this.endDate.setText(b.getString("endDate"));
        this.numGuests.setText(b.getString("numGuests"));

        Context context = getApplicationContext();
        Picasso.with(context).load(b.getString("url")).into(this.url);

        setTitle(b.getString("title"));

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
    }
}
