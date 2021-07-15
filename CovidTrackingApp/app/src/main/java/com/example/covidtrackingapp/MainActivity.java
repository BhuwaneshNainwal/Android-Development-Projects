package com.example.covidtrackingapp;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.covidtrackingapp.Covid;
import com.example.covidtrackingapp.CovidAdapter;
import com.example.covidtrackingapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new CovidAsyncTask().execute("https://api.apify.com/v2/key-value-stores/toDWvRj1JpTXiM8FF/records/LATEST?disableRedirect=true");
    }
    private class CovidAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String ... apiUrl) {

            String current = "";
            try {
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL(apiUrl[0]);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = urlConnection.getInputStream();
                    InputStreamReader isw = new InputStreamReader(in);
                    int data = isw.read();
                    while (data != -1) {
                        current += (char) data;
                        data = isw.read();
//                        System.out.print(current);
                    }
//                    Log.d("datalength",""+current.length());
                    // return the data to onPostExecute method
                    return current;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
            return current;

        }

        protected void onPostExecute(String result) {

            // Create an empty ArrayList that we can start adding earthquakes to
            ArrayList<Covid> covids = new ArrayList<>();
            // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
            // is formatted, a JSONException exception object will be thrown.
            // Catch the exception so the app doesn't crash, and print the error message to the logs.
            try {

                // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
                JSONObject baseJsonResponse = new JSONObject(result);
                int totalcase = baseJsonResponse.getInt("totalCases");
                int totalactive = baseJsonResponse.getInt("activeCases");
                int totaldeaths = baseJsonResponse.getInt("deaths");


                Covid covid = new Covid(totalcase , totalactive , totaldeaths);
                covids.add(covid);

            } catch (JSONException e) {
                // If an error is thrown when executing any of the above statements in the "try" block,
                // catch the exception here, so the app doesn't crash. Print a log message
                // with the message from the exception.
                Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
            }

            // Return the list of earthquakes
            CovidAdapter covidAdapter = new CovidAdapter(MainActivity.this, covids);
            ListView listView = findViewById(R.id.listView);
            listView.setAdapter(covidAdapter);
        }
    }

}