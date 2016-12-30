package com.example.alizardo.thirdparty.libs;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Eduardo Silva on 29/12/2016.
 */

public class Utils {

    private String serverURL;

    public Utils() {
        this.serverURL = "http://thirdparty.thevigilante.xyz:80";
    }

    public Utils(String serverURL) {
        this.serverURL = "http://" + serverURL;
    }

    public String requestGET(String route) {
        String line = "";
        try {
            URL url = new URL(this.serverURL + route);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                line = bufferedReader.readLine();
                bufferedReader.close();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
        }

        return line;
    }


    public String request(String route, String type, HashMap headers, HashMap content) {
        String line = "";
        try {
            URL url = new URL(this.serverURL + route);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            switch (type) {
                case "POST":
                    urlConnection.setRequestMethod("POST");
                    break;
                case "PUT":
                    urlConnection.setRequestMethod("PUT");
                    break;
                case "DELETE":
                    urlConnection.setRequestMethod("DELETE");
                    break;
                default:
                    return "ERROR - Invalid type of request";
            }

            // Add headers
            Iterator it = headers.entrySet().iterator();
            while (it.hasNext()) {
                HashMap.Entry pair = (HashMap.Entry) it.next();
                urlConnection.setRequestProperty((String) pair.getKey(), (String) pair.getValue());
                it.remove();
            }

            // Send post request
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setDoOutput(true);
            byte[] outputInBytes = HashMapToJson(content).getBytes("UTF-8");
            OutputStream os = urlConnection.getOutputStream();
            os.write(outputInBytes);
            os.close();

            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                line = bufferedReader.readLine();
                bufferedReader.close();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
        }

        return line;
    }

    public String HashMapToJson(HashMap map) {
        JSONObject obj = new JSONObject(map);
        return obj.toString();
    }


    public HashMap jsonToHashMap(String t) {
        System.out.println("CONVERTING!");
        HashMap result = new HashMap<>();
        JSONObject jObject = null;

        try {
            jObject = new JSONObject(t);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Iterator<?> keys = jObject.keys();

        while (keys.hasNext()) {
            String key = (String) keys.next();
            String value = null;

            try {
                value = jObject.getString(key);
            } catch (JSONException e) {
                //e.printStackTrace();
            }
            result.put(key, value);
        }
        return result;
    }
}

/*          EXAMPLES OF ASYNC TASKS

class GET extends AsyncTask<String, Void, String> {

    protected void onPreExecute() {
        Log.i("STATUS", "Starting GET request...");
    }

    protected String doInBackground(String... params) {
        Utils util = new Utils();
        return util.requestGET(params[0]);
    }

    protected void onPostExecute(String response) {
        if (response == null) {
            response = "THERE WAS AN ERROR";
        }s
        Utils util = new Utils();
        HashMap map = util.jsonToHashMap(response);

        // TREAT DATA IN map HERE //
    }
}

class POST extends AsyncTask<Object, Void, String> {

    protected void onPreExecute() {
        Log.i("STATUS", "Starting GET request...");
    }

    protected String doInBackground(Object... params) {
        Utils util = new Utils();
        return util.request((String) params[0], (String) params[1], (HashMap) params[2], (HashMap) params[3]);
    }

    protected void onPostExecute(String response) {
        if (response == null) {
            response = "THERE WAS AN ERROR";
        }
        Log.i("response: ", response);
        Utils util = new Utils();
        HashMap map = util.jsonToHashMap(response);

        // TREAT DATA IN map HERE
    }
} */