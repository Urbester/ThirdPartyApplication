package com.example.alizardo.thirdparty.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alizardo.thirdparty.R;
import com.example.alizardo.thirdparty.libs.Utils;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by alizardo on 27/12/2016.
 */
public class LoginRegisterActivity extends Activity {
    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private Bundle b;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    private boolean doubleBackToExitPressedOnce = false;

    private GoogleApiClient client;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = new Bundle();
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.alizardo.thirdparty",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.login_register_facebook);
        info = (TextView) findViewById(R.id.info);
        loginButton = (LoginButton) findViewById(R.id.login_button);

        // Load Logo
        ImageView img = (ImageView) findViewById(R.id.Logo);
        Picasso.with(getApplicationContext()).load(R.drawable.logo).centerCrop().resize(550, 550).into(img);

        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_friends"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());

                                try {

                                    HashMap<String, String> headers = new HashMap<>();
                                    HashMap<String, String> payload = new HashMap<>();

                                    b.putString("AccessToken", loginResult.getAccessToken().getToken());
                                    b.putString("Name", object.getString("name"));
                                    b.putString("Email", object.getString("email"));
                                    b.putString("PhotoLink", object.getJSONObject("picture").getJSONObject("data").getString("url"));


                                    payload.put("AccessToken", loginResult.getAccessToken().getToken());
                                    payload.put("Name", object.getString("name"));
                                    payload.put("Email", object.getString("email"));
                                    payload.put("PhotoLink", object.getJSONObject("picture").getJSONObject("data").getString("url"));

                                    CreateAccount worker = new CreateAccount();
                                    worker.execute("/v1/account", "POST", headers, payload);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,email,name,picture.type(large)");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginRegisterActivity.this, "Login canceled, try again later.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(LoginRegisterActivity.this, "Please, try again later.", Toast.LENGTH_SHORT).show();
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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

    class CreateAccount extends AsyncTask<Object, Void, String> {

        @Override
        protected void onPreExecute() {
            Log.i("STATUS", "Starting GET request...");
        }

        @Override
        protected String doInBackground(Object... params) {
            try {
                Utils util = new Utils();
                return util.request((String) params[0], (String) params[1], (HashMap) params[2], (HashMap) params[3]);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String response) {
            if (response == null || Objects.equals(response, "")) {
                response = "THERE WAS AN ERROR";
                Toast.makeText(LoginRegisterActivity.this, "Service is unavailable. Try again later.", Toast.LENGTH_SHORT).show();
                LoginManager.getInstance().logOut();
                return;
            }
            Log.i("response: ", response);
            Utils util = new Utils();
            HashMap map = util.jsonToHashMap(response);

            // TREAT DATA IN map HERE
            Toast.makeText(LoginRegisterActivity.this, "Welcome, " + b.getString("Name"), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplication(), MainActivity.class);
            i.putExtras(b);
            startActivity(i);
            finish();
        }
    }

}
