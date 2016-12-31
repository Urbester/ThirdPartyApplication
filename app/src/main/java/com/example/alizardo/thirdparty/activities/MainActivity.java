package com.example.alizardo.thirdparty.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alizardo.thirdparty.R;
import com.example.alizardo.thirdparty.fragments.DiscoverFragment;
import com.example.alizardo.thirdparty.fragments.EventFragment;
import com.example.alizardo.thirdparty.libs.Utils;
import com.facebook.login.LoginManager;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private String facebook_user_token;
    private String email;
    private String name;
    private String user_image_url;
    private Bundle basicInfo;

    private JSONObject pending, rejected, invited, hosting;

    // Make sure to be using android.support.v7.app.ActionBarDrawerToggle version.
    // The android.support.v4.app.ActionBarDrawerToggle has been deprecated.
    private ActionBarDrawerToggle drawerToggle;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        basicInfo = getIntent().getExtras();
        this.facebook_user_token = basicInfo.getString("AccessToken");
        this.email = basicInfo.getString("Email");
        this.name = basicInfo.getString("Name");
        this.user_image_url = basicInfo.getString("PhotoLink");

        Button loginButtonOnDrawer = (Button) findViewById(R.id.login_button);
        loginButtonOnDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                finish();
                System.exit(0);
            }
        });

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Find our drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nvView);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        setupDrawerContent(nvDrawer);

        FloatingActionButton createEventBtn = (FloatingActionButton) findViewById(R.id.newEvent);
        createEventBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplication(), CreateEventFormActivity.class);
                i.putExtras(basicInfo);
                startActivity(i);
            }
        });

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);

        // Inflate the header view at runtime
        View headerLayout = nvDrawer.inflateHeaderView(R.layout.nav_header);

        /*Loads nav_header logo*/
        ImageView img = (ImageView) headerLayout.findViewById(R.id.nav_header_logo);
        Picasso.with(getApplicationContext()).load(this.user_image_url).resize(300, 300).into(img);

        TextView name = (TextView) headerLayout.findViewById(R.id.name_text_view);
        name.setText(this.name);

        discover(nvDrawer.getMenu().getItem(0), mDrawer);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }


    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        switch (menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                discover(menuItem, mDrawer);
                break;
            case R.id.nav_second_fragment:
                showEvents(menuItem, mDrawer);
                break;
            case R.id.nav_third_activity:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                return;

            default:
                discover(menuItem, mDrawer);

                break;
        }
        return;
    }

    // TODO: Not implemented
    private void showEvents(MenuItem m, DrawerLayout d) {
        HashMap<String, String> headers = new HashMap<>();
        HashMap<String, String> payload = new HashMap<>();

        headers.put("X-Auth-Token", facebook_user_token);
        new GetEvents(m, d).execute("/v1/event/list/public", "GET", headers, payload);
    }


    private void discover(MenuItem m, DrawerLayout d) {
        HashMap<String, String> headers = new HashMap<>();
        HashMap<String, String> payload = new HashMap<>();

        headers.put("X-Auth-Token", facebook_user_token);
        new GetPublicEvents(m, d).execute("/v1/event/list/public", headers, payload);
    }

    private void search(MenuItem m, DrawerLayout d) {
        HashMap<String, String> headers = new HashMap<>();
        HashMap<String, String> payload = new HashMap<>();

        headers.put("X-Auth-Token", facebook_user_token);
        new GetSearchEvents(m, d).execute("/v1/event/list/public", headers, payload);
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            LoginManager.getInstance().logOut();
            finish();
            System.exit(0);
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;

            }
        }, 2000);
    }

    class GetPublicEvents extends AsyncTask<Object, Void, String> {
        private DiscoverFragment fragment;
        private MenuItem menuItem;
        private DrawerLayout mDrawer;

        public GetPublicEvents(MenuItem m, DrawerLayout d) {
            super();
            this.menuItem = m;
            this.mDrawer = d;
        }

        @Override
        protected void onPreExecute() {
            Toast.makeText(MainActivity.this, "Discovering", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(Object... params) {
            try {
                Utils util = new Utils();
                return util.requestGET((String) params[0], (HashMap) params[1]);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String response) {
            if (response == null || Objects.equals(response, "")) {
                response = "THERE WAS AN ERROR";
                Toast.makeText(MainActivity.this, "Service is unavailable. Try again later.", Toast.LENGTH_SHORT).show();
                LoginManager.getInstance().logOut();
                return;
            }

            Log.i("response: ", response);
            Utils util = new Utils();


            JSONObject map = null;
            try {
                map = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            fragment = DiscoverFragment.newInstance(map);

            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

            // Highlight the selected item has been done by NavigationView
            menuItem.setChecked(true);
            // Set action bar title
            setTitle(menuItem.getTitle());
            // Close the navigation drawer
            mDrawer.closeDrawers();
            return;
        }
    }

    class GetEvents extends AsyncTask<Object, Void, String> {
        private EventFragment fragment;
        private MenuItem menuItem;
        private DrawerLayout mDrawer;

        public GetEvents(MenuItem m, DrawerLayout d) {
            super();
            this.menuItem = m;
            this.mDrawer = d;
        }

        @Override
        protected void onPreExecute() {
            Toast.makeText(MainActivity.this, "Getting events.", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(Object... params) {
            return "";
        }

        @Override
        protected void onPostExecute(String response) {
            pending(facebook_user_token);
        }
    }

    class GetSearchEvents extends AsyncTask<Object, Void, String> {
        private SearchActivity searchActivity;
        private MenuItem menuItem;
        private DrawerLayout mDrawer;

        public GetSearchEvents(MenuItem m, DrawerLayout d) {
            super();
            this.menuItem = m;
            this.mDrawer = d;
        }

        @Override
        protected void onPreExecute() {
            Toast.makeText(MainActivity.this, "Getting set", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(Object... params) {
            try {
                Utils util = new Utils();
                return util.requestGET((String) params[0], (HashMap) params[1]);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String response) {
            if (response == null || Objects.equals(response, "")) {
                response = "THERE WAS AN ERROR";
                Toast.makeText(MainActivity.this, "Service is unavailable. Try again later.", Toast.LENGTH_SHORT).show();
                LoginManager.getInstance().logOut();
                return;
            }

            Log.i("response: ", response);
            Utils util = new Utils();


            JSONObject map = null;
            try {
                map = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }



            // Highlight the selected item has been done by NavigationView
            menuItem.setChecked(true);
            // Set action bar title
            setTitle(menuItem.getTitle());
            // Close the navigation drawer
            mDrawer.closeDrawers();
            return;
        }
    }


    private void pending(String accessToken) {

        HashMap<String, String> headers = new HashMap<>();
        HashMap<String, String> payload = new HashMap<>();

        headers.put("X-Auth-Token", accessToken);
        new GetPendingEvents().execute("/v1/event/list/pending", headers, payload);
    }

    class GetPendingEvents extends AsyncTask<Object, Void, String> {

        public GetPendingEvents() {
            super();
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(Object... params) {
            try {
                Utils util = new Utils();
                return util.requestGET((String) params[0], (HashMap) params[1]);
            } catch (Exception e) {
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if (response == null || Objects.equals(response, "")) {
                return;
            }

            JSONObject map = null;
            try {
                pending = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("Pending", response);

            invited(facebook_user_token);
        }
    }


    private void invited(String accessToken) {

        HashMap<String, String> headers = new HashMap<>();
        HashMap<String, String> payload = new HashMap<>();

        headers.put("X-Auth-Token", accessToken);
        new GetInvitedEvents().execute("/v1/event/list/invited", headers, payload);
    }

    class GetInvitedEvents extends AsyncTask<Object, Void, String> {

        public GetInvitedEvents() {
            super();
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(Object... params) {
            try {
                Utils util = new Utils();
                return util.requestGET((String) params[0], (HashMap) params[1]);
            } catch (Exception e) {
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if (response == null || Objects.equals(response, "")) {
                return;
            }

            JSONObject map = null;
            try {
                invited = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.e("Invited", response);
            hosting(facebook_user_token);

        }
    }


    private void hosting(String accessToken) {

        HashMap<String, String> headers = new HashMap<>();
        HashMap<String, String> payload = new HashMap<>();

        headers.put("X-Auth-Token", accessToken);
        new GetHostingEvents().execute("/v1/event/list/hosting", headers, payload);
    }

    class GetHostingEvents extends AsyncTask<Object, Void, String> {

        public GetHostingEvents() {
            super();
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(Object... params) {
            try {
                Utils util = new Utils();
                return util.requestGET((String) params[0], (HashMap) params[1]);
            } catch (Exception e) {
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if (response == null || Objects.equals(response, "")) {
                return;
            }

            JSONObject map = null;
            try {
                hosting = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.e("Hosting", response);
            rejected(facebook_user_token);
        }
    }


    private void rejected(String accessToken) {

        HashMap<String, String> headers = new HashMap<>();
        HashMap<String, String> payload = new HashMap<>();

        headers.put("X-Auth-Token", accessToken);
        new GetRejectedEvents().execute("/v1/event/list/rejected", headers, payload);
    }

    class GetRejectedEvents extends AsyncTask<Object, Void, String> {

        public GetRejectedEvents() {
            super();
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(Object... params) {
            try {
                Utils util = new Utils();
                return util.requestGET((String) params[0], (HashMap) params[1]);
            } catch (Exception e) {
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if (response == null || Objects.equals(response, "")) {
                return;
            }

            JSONObject map = null;
            try {
                rejected = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("Rejected", response);

            // call fragment
            EventFragment fragment = EventFragment.newInstance(pending, hosting, invited, rejected);

            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

            // Highlight the selected item has been done by NavigationView
            nvDrawer.getMenu().getItem(1).setChecked(true);
            // Set action bar title
            setTitle(nvDrawer.getMenu().getItem(1).getTitle());
            // Close the navigation drawer
            mDrawer.closeDrawers();
        }
    }

}