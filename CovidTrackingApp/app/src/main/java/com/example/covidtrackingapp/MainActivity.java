package com.example.covidtrackingapp;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new CovidAsyncTask().execute("https://api.apify.com/v2/key-value-stores/toDWvRj1JpTXiM8FF/records/LATEST?disableRedirect=true");
    }
    private class CovidAsyncTask extends AsyncTask<String, Void, String> {
        ProgressDialog progress = new ProgressDialog(MainActivity.this);

        ArrayList<Covid> covid = new ArrayList<>();
        @Override
        protected void onPreExecute() {
            progress.setMessage("Fetching data , Please wait...");
            progress.show();
        }

        protected String doInBackground(String... apiUrl) {

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
//                    return current;
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
            try {

                JSONObject baseJsonResponse = new JSONObject(current);
                int totalcase = baseJsonResponse.getInt("totalCases");
                int totalactive = baseJsonResponse.getInt("activeCases");
                int newactive = baseJsonResponse.getInt("activeCasesNew");
                int totaldeaths = baseJsonResponse.getInt("deaths");
                String date = baseJsonResponse.getString("lastUpdatedAtApify");
                String formattedDate  = date.substring(8 , 10) + date.substring(7 , 8) + date.substring(5, 7) + date.substring(7 , 8) + date.substring(0 , 4) + getString(R.string.timePrefix) + date.substring(11 , 19);
                JSONArray arr = baseJsonResponse.getJSONArray("regionData");
                String last =  Integer.toString(totalcase) + " " + Integer.toString(totalactive) + " " + Integer.toString(newactive) + " " + Integer.toString(totaldeaths) + " " +
                        formattedDate;

                for(int i = 0; i < arr.length(); i++) {
                    String state = arr.getJSONObject(i).getString("region");
                    int total = arr.getJSONObject(i).getInt("totalInfected");
                    int active = arr.getJSONObject(i).getInt("activeCases");
                    int deceased = arr.getJSONObject(i).getInt("deceased");
                    int recovery = arr.getJSONObject(i).getInt("recovered");

                    Covid c = new Covid(state , total, active , deceased , recovery);
                    covid.add(c);
                }

                return last;
            } catch (JSONException e) {
                // If an error is thrown when executing any of the above statements in the "try" block,
                // catch the exception here, so the app doesn't crash. Print a log message
                // with the message from the exception.
                Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
            }
            return  "";


        }
        protected void onPostExecute(String result) {

            // Create an empty ArrayList that we can start adding earthquakes to
            // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
            // is formatted, a JSONException exception object will be thrown.
            // Catch the exception so the app doesn't crash, and print the error message to the logs.

            if (progress.isShowing()) {
                progress.dismiss();
            }
            // create StringTokenizer object
            StringTokenizer st = new StringTokenizer(result , " ");

            // create ArrayList object
            ArrayList<String> list=new ArrayList<String>();

            // iterate through StringTokenizer tokens
            while(st.hasMoreTokens()) {

                // add tokens to AL
                list.add(st.nextToken());
            }

            TextView totalCount = (TextView) findViewById(R.id.totalcount);
            totalCount.setText(list.get(0));

            TextView totalActive = (TextView) findViewById(R.id.totalactivecount);
            totalActive.setText(list.get(1));
            TextView totalDeaths = (TextView) findViewById(R.id.totaldeathscount);
            totalDeaths.setText(list.get(3));
            String temp = "Last Updated on " + list.get(4);
            TextView lastUpdated = (TextView) findViewById(R.id.lastupdate);
            lastUpdated.setText(temp);

            CovidAdapter covidadapter = new CovidAdapter(MainActivity.this, covid);
            ListView listView = findViewById(R.id.listView);
            listView.setAdapter(covidadapter);
        }
    }

}