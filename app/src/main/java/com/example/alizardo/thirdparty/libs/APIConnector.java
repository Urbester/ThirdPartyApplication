package com.example.alizardo.thirdparty.libs;

import android.content.Context;
import android.support.v4.util.ArrayMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

import github.nisrulz.optimushttp.HttpReq;
import github.nisrulz.optimushttp.OptimusHTTP;

/**
 * Created by Eduardo Silva on 28/12/2016.
 */

public class APIConnector {

    private String serverURL;
    private ArrayMap<String, String> params;
    private ArrayMap<String, String> headers;
    private OptimusHTTP client;
    private HashMap result;

    public APIConnector(Context context, String serverURL) {
        this.client = new OptimusHTTP(context);
        this.params = new ArrayMap<>();
        this.headers = new ArrayMap<>();
        this.serverURL = serverURL;
        this.client.setContentType(OptimusHTTP.CONTENT_TYPE_JSON);
    }

    public APIConnector() {
        this.params = new ArrayMap<>();
        this.headers = new ArrayMap<>();
        this.client.setContentType(OptimusHTTP.CONTENT_TYPE_JSON);

    }

    public void toggleDebugging() {
        client.enableDebugging();
    }

    public void addHeader(String name, String value) {
        this.headers.put(name, value);
        this.client.setHeaderMap(this.headers);
    }

    public void addParam(String name, String value) {
        this.params.put(name, value);
    }

    public void jsonToHashMap(String t) {
        System.out.println("CONVERTING!");
        HashMap<String, String> map = new HashMap<>();
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
                e.printStackTrace();
            }
            map.put(key, value);
        }

        this.result = map;
    }

    void makeRequest(String route, String request) {

        if(request.equals("GET")) {
            this.client.setMethod(OptimusHTTP.METHOD_GET);
        }
        else if(request.equals("POST")) {
            this.client.setMethod(OptimusHTTP.METHOD_POST);
        }
        else{
            System.out.println("NOT A VALID REQUEST");
            return;
        }


        try {
            HttpReq req = this.client.makeRequest(this.serverURL + route, params, new OptimusHTTP.ResponseListener() {
                @Override
                public void onSuccess(String msg) {
                    System.out.println("SUCCESS ---> " + msg);
                    jsonToHashMap(msg);
                }

                @Override
                public void onFailure(String msg) {
                    System.out.println("REQUEST ERROR!!");
                }
            });

            if (req != null) {
                System.out.println("Request OK!");
            } else {
                this.client.cancelReq(req);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HashMap getResult() {
        return this.result;
    }
}
