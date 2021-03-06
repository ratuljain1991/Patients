package com.example.lol.patients;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class HttpPost {

    private static final String LOG_TAG = HttpPost.class.getSimpleName();

    public static JSONObject SendHttpPost(String url_rec, JSONObject jsonObjSend) {

        JSONObject jsonObjRecv = null;
        try {

            URL url = new URL(url_rec);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String input = jsonObjSend.toString();

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            StringBuilder sb = new StringBuilder();

            while ((output = br.readLine()) != null) {
                sb.append(output + "\n");
            }

            output = sb.toString();
            jsonObjRecv = new JSONObject(output);

            Log.v(LOG_TAG, jsonObjRecv.toString());

            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();
            Log.v(LOG_TAG, e.getMessage());

        } catch (IOException e) {

            e.printStackTrace();
            Log.v(LOG_TAG, e.getMessage());

        } catch (JSONException e) {
            e.printStackTrace();
            Log.v(LOG_TAG, e.getMessage());
        }

        return jsonObjRecv;

    }
}
