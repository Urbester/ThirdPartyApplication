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
import java.util.Objects;

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
                try {
                    String title = (String) ((TextView) findViewById(R.id.NewEventFormName)).getText().toString();

                    if(title.length() == 0){
                        Toast.makeText(CreateEventFormActivity.this, "Bad title.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String url = (String) ((TextView) findViewById(R.id.NewEventFormURL)).getText().toString();
                    String description = (String) ((TextView) findViewById(R.id.NewEventFormDescription)).getText().toString();
                    if(description.length() == 0){
                        Toast.makeText(CreateEventFormActivity.this, "Bad description.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String location = (String) ((TextView) findViewById(R.id.NewEventFormLocation)).getText().toString();
                    if(location.length() == 0){
                        Toast.makeText(CreateEventFormActivity.this, "Bad location.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int price;
                    try {
                        price = Integer.parseInt(((TextView) findViewById(R.id.NewEventFormPrice)).getText().toString());
                    } catch (Exception e){
                        price = 0;
                    }

                    int max_guests;
                    try {
                        max_guests = Integer.parseInt(((TextView) findViewById(R.id.NewEventFormMaxGuests)).getText().toString());
                    } catch (Exception e){
                        max_guests = 0;
                    }

                    boolean isPublic = (boolean) ((CheckBox) findViewById(R.id.NewEventFormPublicCheck)).isChecked();

                    DatePicker dobPicker = (DatePicker) findViewById(R.id.NewEventFormStartDate);
                    Integer dobYear = dobPicker.getYear();
                    Integer dobMonth = dobPicker.getMonth() + 1;
                    Integer dobDate = dobPicker.getDayOfMonth();
                    StringBuilder sb = new StringBuilder();
                    sb.append(dobYear.toString()).append("-").append(dobMonth.toString()).append("-").append(dobDate.toString());
                    String startDateDay = sb.toString();

                    dobPicker = (DatePicker) findViewById(R.id.NewEventFormEndDate);
                    dobYear = dobPicker.getYear();
                    dobMonth = dobPicker.getMonth() + 1;
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
                    sb.append(endDateDay).append(" ").append(endDateTime).append(":00");

                    String endDate = sb.toString();

                    timePicker = (TimePicker) findViewById(R.id.NewEventFormStartDateTime);
                    hour = timePicker.getHour();
                    minute = timePicker.getMinute();
                    sb = new StringBuilder();
                    sb.append(hour).append(":").append(minute);
                    String startDateTime = sb.toString();

                    sb = new StringBuilder();
                    sb.append(startDateDay).append(" ").append(startDateTime).append(":00");

                    String startDate = sb.toString();


                    HashMap<String, String> headers = new HashMap<>();
                    HashMap<String, String> payload = new HashMap<>();

                    headers.put("X-Auth-Token", basicInfo.getString("AccessToken"));

                    payload.put("Title", title);
                    payload.put("Description", description);
                    payload.put("Local", location);
                    payload.put("Price", String.valueOf(price));
                    payload.put("StartDate", startDate);
                    payload.put("EndDate", endDate);
                    payload.put("MaxGuests", String.valueOf(max_guests));
                    payload.put("Public", String.valueOf(isPublic));
                    payload.put("URL", url);

                    CreateEvent worker = new CreateEvent();
                    worker.execute("/v1/event", "POST", headers, payload);
                } catch (Exception e) {
                    Toast.makeText(CreateEventFormActivity.this, "Ops! Bad form.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    class CreateEvent extends AsyncTask<Object, Void, String> {



        protected void onPreExecute() {
            Toast.makeText(CreateEventFormActivity.this, "Creating event...", Toast.LENGTH_SHORT).show();
        }

        protected String doInBackground(Object... params) {
            try{
            Utils util = new Utils();
            return util.request((String) params[0], (String) params[1], (HashMap) params[2], (HashMap) params[3]);}
            catch (Exception e){
                return null;
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast.makeText(CreateEventFormActivity.this, "Something wrong happenned. Please try again later.", Toast.LENGTH_SHORT).show();
        }

        protected void onPostExecute(String response) {
            if (Objects.equals(response, "") || response == null) {
                Toast.makeText(CreateEventFormActivity.this, "Service is unavailable. Try again later.", Toast.LENGTH_SHORT).show();
                onBackPressed();
                return;
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
