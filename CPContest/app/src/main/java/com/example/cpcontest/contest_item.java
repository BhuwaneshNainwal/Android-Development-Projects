package com.example.cpcontest;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.TimeZone;

public class contest_item extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contest_item);
        new ContestAsyncTask().execute("https://kontests.net/api/v1/all");
    }

    private class ContestAsyncTask extends AsyncTask<String, Void, String> {

        ArrayList<Contest> contest  = new ArrayList<>();

        ProgressDialog progress = new ProgressDialog(contest_item.this , ProgressDialog.THEME_HOLO_DARK);

        @Override
        protected void onPreExecute() {
            this.progress.setMessage("Please wait");
            this.progress.setCancelable(false);
            this.progress.show();
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
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
                    }

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

                JSONArray arr =  new JSONArray(current);

                for(int i = 0; i < arr.length(); i++) {
                    JSONObject currentObj = arr.getJSONObject(i);
                    String contestName = currentObj.getString("name");

                    String link = currentObj.getString("url");
                    String site = currentObj.getString("site");

                    String startTime = currentObj.getString("start_time");

                    int isRunning = 0;
                    if(currentObj.getString("status").equals("CODING"))
                        isRunning = 1;

                    Date date1;

                    if(startTime.charAt(startTime.length() - 1) == 'Z')
                    {

                        startTime = startTime.substring(0, startTime.length() - 5) + 'Z';

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                        sdf.parse(startTime);
                        date1 = sdf.parse(startTime);
                        startTime = sdf.parse(startTime).toString();
                    }

                    else
                    {
                        startTime = startTime.substring(0, startTime.length() - 3) ;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

                        date1 = sdf.parse(startTime);
                        startTime = sdf.parse(startTime).toString();
                    }


                    String year = startTime.substring(startTime.length() - 4, startTime.length());
                    startTime = startTime.substring(0, startTime.length() - 14) + year;

                    ArrayList<String> mediater = new ArrayList<String>(5);


                    StringTokenizer st1 = new StringTokenizer(
                            startTime, " ");


                    // Condition holds true till there is single token
                    // remaining using hasMoreTokens() method
                    while (st1.hasMoreTokens())
                    {
                        mediater.add(st1.nextToken());
                    }


                    startTime = mediater.get(2) + ' ' + mediater.get(1) + ' ' + mediater.get(4)+ ' ' + mediater.get(0)+ ' ' + mediater.get(3).charAt(0) + mediater.get(3).charAt(1) + mediater.get(3).charAt(2) + mediater.get(3).charAt(3) + mediater.get(3).charAt(4);
                    String endTime = currentObj.getString("end_time");

                    Date date2;

                    if(endTime.charAt(endTime.length() - 1) == 'Z')
                    {
                        endTime = endTime.substring(0, endTime.length() - 5) + 'Z';
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                        sdf.parse(endTime);
                        endTime = sdf.parse(endTime).toString();
                    }
                    else
                    {
                        endTime = endTime.substring(0, endTime.length() - 3) ;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                        endTime = sdf.parse(endTime).toString();
                    }

                    String year1 = endTime.substring(endTime.length() - 4, endTime.length());
                    endTime = endTime.substring(0, endTime.length() - 14) + year1;

                    ArrayList<String> mediater1 = new ArrayList<String>(5);

                    StringTokenizer st2 = new StringTokenizer(
                            endTime, " ");

                    while (st2.hasMoreTokens())
                    {
                        mediater1.add(st2.nextToken());
                    }

                    endTime = mediater1.get(2) + ' ' + mediater1.get(1) + ' ' + mediater1.get(4)+ ' ' + mediater1.get(0)+ ' ' + mediater1.get(3).charAt(0) + mediater1.get(3).charAt(1) + mediater1.get(3).charAt(2) + mediater1.get(3).charAt(3) + mediater1.get(3).charAt(4);

                    String img = site.toLowerCase();
                    img= img.replaceAll("\\s", "");

                    String twentyFourHours = currentObj.getString("in_24_hours");
                    Contest currentContest = new Contest(contestName, startTime, endTime, twentyFourHours, isRunning, link, getResources().getIdentifier(img, "drawable", getPackageName()), 0);
                    contest.add(currentContest);
                }


            } catch (JSONException | ParseException e) {

                Log.e("QueryUtils", "Problem parsing the contest JSON results", e);
            }
            return "";
        }
        protected void onPostExecute(String result) {
            if (progress.isShowing()) {
                progress.dismiss();
            }

            ListView listView;
            ContestAdapter contestAdapter;
            listView = findViewById(R.id.listView);
            contestAdapter = new ContestAdapter(contest_item.this, contest);
            listView.setAdapter(contestAdapter);
        }
    }

}

