package com.example.quakeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

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

        new EarthquakeAsyncTask().execute("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10");
    }
    private class EarthquakeAsyncTask extends AsyncTask<String, Void, String> {
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
            ArrayList<Earthquake> earthquakes = new ArrayList<>();
            // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
            // is formatted, a JSONException exception object will be thrown.
            // Catch the exception so the app doesn't crash, and print the error message to the logs.
            try {

                // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
                JSONObject baseJsonResponse = new JSONObject(result);
                JSONArray earthquakeArray = baseJsonResponse.getJSONArray("features");


                for(int i = 0 ; i < earthquakeArray.length() ; i++){

                    JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);
                    JSONObject properties = currentEarthquake.getJSONObject("properties");
                    String location = properties.getString("place");
                    double magnitude = properties.getDouble("mag");

                    boolean checkPrefix = location.contains("of");
                    String prefix = "";
                    String suffix = "";

                    if(checkPrefix){
                        int ind = location.indexOf("of");
                        ind = ind + 2;
                        prefix = location.substring(0 , ind);
                        suffix = location.substring(ind + 1 , location.length());
                    }
                    else{

                        prefix = "Near the ";
                        suffix = location.substring(0 , location.length());
                    }


                    long time = properties.getLong("time");
                    Earthquake earthquake = new Earthquake(magnitude , prefix , suffix , time);
                    earthquakes.add(earthquake);
                }
                // build up a list of Earthquake objects with the corresponding data.

            } catch (JSONException e) {
                // If an error is thrown when executing any of the above statements in the "try" block,
                // catch the exception here, so the app doesn't crash. Print a log message
                // with the message from the exception.
                Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
            }

            // Return the list of earthquakes
            EarthquakeAdapter earthquakeAdapter = new EarthquakeAdapter(MainActivity.this, earthquakes);
            ListView listView = findViewById(R.id.listView);
            listView.setAdapter(earthquakeAdapter);
        }
    }

}