package com.example.covidtrackingapp;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SlotCheck extends Worker {

    public SlotCheck(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Result doWork() {
        String email = getInputData().getString("email");
        String state = getInputData().getString("state");

        String district = getInputData().getString("district");
        String input = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByDistrict?district_id=";
        String stateUrl = "https://cdn-api.co-vin.in/api/v2/admin/location/states";

        int stateId = getStateId(state , stateUrl);
        String districtUrl = "https://cdn-api.co-vin.in/api/v2/admin/location/districts/" + stateId;

        int districtId = getDistrictId(district , districtUrl);

        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);
        Log.d("date",  thisDate);

        input = input + districtId + "&date=" + thisDate;
        fetchData(input , email);
        return Result.success();
    }

    private int getStateId(String state , String stateUrl){
        String current = "";
        int end = -1;
        try {
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(stateUrl);
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
        }

//        Parse And Send Mail

        try {

            JSONObject baseJsonResponse = new JSONObject(current);
            JSONArray centers = baseJsonResponse.getJSONArray("states");
            for(int i = 0 ; i < centers.length() ; i++)
            {
                String stateName = centers.getJSONObject(i).getString("state_name");


                int state_id = centers.getJSONObject(i).getInt("state_id");
                if(stateName.equals(state)) {
                    end = state_id;
                    return  end;
                }

            }

        }


        catch(JSONException e){
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return end;
    }

    private int getDistrictId(String district , String districtUrl){
        String current = "";

        try {
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(districtUrl);
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
        }

//        Parse And Send Mail

        try {

            JSONObject baseJsonResponse = new JSONObject(current);
            JSONArray centers = baseJsonResponse.getJSONArray("districts");
            for(int i = 0 ; i < centers.length() ; i++)
            {
                String districtName = centers.getJSONObject(i).getString("district_name");

                int districtId = centers.getJSONObject(i).getInt("district_id");
                if(districtName.equals(district) )
                    return districtId;
            }

        }


        catch(JSONException e){
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return -1;
    }

    private void fetchData(String input , String recept) {
        String body = "";
        String subject = "Vaccine slot found!";
        String current = "";
        try {
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(input);
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
        }

//        Parse And Send Mail

        try {

            JSONObject baseJsonResponse = new JSONObject(current);
            JSONArray centers = baseJsonResponse.getJSONArray("centers");

            for (int i = 0; i < centers.length(); i++) {
                String centerName = centers.getJSONObject(i).getString("name");
                String centerAddress = centers.getJSONObject(i).getString("address");
                String districtName = centers.getJSONObject(i).getString("district_name");
                String block = centers.getJSONObject(i).getString("block_name");
                int pinCode = centers.getJSONObject(i).getInt("pincode");
                String fee = centers.getJSONObject(i).getString("fee_type");

                JSONArray sessions = centers.getJSONObject(i).getJSONArray("sessions");
                int minimumAge = sessions.getJSONObject(0).getInt("min_age_limit");
                String vaccineType = sessions.getJSONObject(0).getString("vaccine");

                int dose1 = sessions.getJSONObject(0).getInt("available_capacity_dose1");

                int dose2 = sessions.getJSONObject(0).getInt("available_capacity_dose2");

                body = body + "District : " + districtName + '\n' + "Center Name : " + centerName + '\n' + "Center Address : " + centerAddress + '\n' + "Block : " + block + '\n' + "Pin Code : " + pinCode
                + '\n' + "Minimum Age Limit : " + minimumAge + "\n" + "Vaccine Type : " + vaccineType + "\n" + "Dose1 Slots : " + dose1 +  " and " + "Dose2 Slots : " + dose2 + '\n' + "Fee type : " + fee;

                body = body + "\n\n\n\n\n";
            }
        }


        catch(JSONException e){
                // If an error is thrown when executing any of the above statements in the "try" block,
                // catch the exception here, so the app doesn't crash. Print a log message
                // with the message from the exception.
                Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
            }

            if(body.length() > 0)
            {

                try {

                    GMailSender sender = new GMailSender("linuxkali1947@gmail.com", "Harshit@123");


                    sender.sendMail(subject,


                            body ,"linuxkali1947@gmail.com",


                            recept);

                } catch (Exception e) {


                    Log.e("error", e.getMessage(), e);

                }
            }
        }
}