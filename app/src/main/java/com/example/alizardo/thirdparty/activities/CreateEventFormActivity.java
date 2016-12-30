package com.example.alizardo.thirdparty.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.alizardo.thirdparty.R;
import com.example.alizardo.thirdparty.libs.Utils;

import java.util.HashMap;

public class CreateEventFormActivity extends AppCompatActivity {

    private Bundle basicInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("New Party");
        this.basicInfo = getIntent().getExtras();

        // listener to create button
        Button create = (Button) findViewById(R.id.createEventButton);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get all data and send to services

                String title = (String) ((TextView) findViewById(R.id.NewEventFormName)).getText();
                String description = (String) ((TextView) findViewById(R.id.NewEventFormDescription)).getText();
                String location = (String) ((TextView) findViewById(R.id.NewEventFormLocation)).getText();
                int price = Integer.parseInt((String) ((TextView) findViewById(R.id.NewEventFormPrice)).getText());

                boolean isPublic = (boolean) (((CheckBox) findViewById(R.id.NewEventFormPublicCheck)).isChecked());

                DatePicker dobPicker = (DatePicker) findViewById(R.id.NewEventFormStartDate);
                Integer dobYear = dobPicker.getYear();
                Integer dobMonth = dobPicker.getMonth();
                Integer dobDate = dobPicker.getDayOfMonth();
                StringBuilder sb = new StringBuilder();
                sb.append(dobYear.toString()).append("-").append(dobMonth.toString()).append("-").append(dobDate.toString());
                String startDateDay = sb.toString();

                dobPicker = (DatePicker) findViewById(R.id.NewEventFormEndDate);
                dobYear = dobPicker.getYear();
                dobMonth = dobPicker.getMonth();
                dobDate = dobPicker.getDayOfMonth();
                sb = new StringBuilder();
                sb.append(dobYear.toString()).append("-").append(dobMonth.toString()).append("-").append(dobDate.toString());
                String endDateDay = sb.toString();

                TimePicker timePicker = (TimePicker) findViewById(R.id.NewEventFormEndDateTime);
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();
                sb = new StringBuilder();
                sb.append(hour).append(":").append(minute);
                String endDateTime = sb.toString();

                sb = new StringBuilder();
                sb.append(endDateDay).append(" ").append(endDateTime);

                String endDate = sb.toString();

                timePicker = (TimePicker) findViewById(R.id.NewEventFormStartDateTime);
                hour = timePicker.getHour();
                minute = timePicker.getMinute();
                sb = new StringBuilder();
                sb.append(hour).append(":").append(minute);
                String startDateTime = sb.toString();

                sb = new StringBuilder();
                sb.append(startDateDay).append(" ").append(startDateTime);

                String startDate = sb.toString();


                HashMap<String, String> headers = new HashMap<>();
                HashMap<String, String> payload = new HashMap<>();

                headers.put("X-Auth-Token", basicInfo.getString("AccessToken"));
                payload.put("Title", title);
                payload.put("Description", description);
                payload.put("Location", location);
                payload.put("Price", String.valueOf(price));
                payload.put("StartDate", startDate);
                payload.put("EndDate", endDate);
                payload.put("Public", String.valueOf(isPublic));

                CreateEvent worker = new CreateEvent();
                worker.execute("/v1/event", "POST", headers, payload);
            }
        });

    }

    class CreateEvent extends AsyncTask<Object, Void, String> {

        protected void onPreExecute() {
            Toast.makeText(CreateEventFormActivity.this, "Creating event...", Toast.LENGTH_SHORT).show();
        }

        protected String doInBackground(Object... params) {
            Utils util = new Utils();
            return util.request((String) params[0], (String) params[1], (HashMap) params[2], (HashMap) params[3]);
        }


        protected void onPostExecute(String response) {
            if (response == null) {
                Toast.makeText(CreateEventFormActivity.this, "Error creating event. Please check all parameters.", Toast.LENGTH_SHORT).show();
            }
            Log.i("response: ", response);
            Utils util = new Utils();
            HashMap map = util.jsonToHashMap(response);

            // TREAT DATA IN map HERE
            Toast.makeText(CreateEventFormActivity.this, "Event created.", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        onBackPressed();
        return true;
    }

}
