package com.example.covidtrackingapp;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import javax.mail.*;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;

public class SlotCheck extends Worker {

    public SlotCheck(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    /*
     * This method is responsible for doing the work
     * so whatever work that is needed to be performed
     * we will put it here
     *
     * For example, here I am calling the method displayNotification()
     * It will display a notification
     * So that we will understand the work is executed
     * */

    @NonNull
    @Override
    public Result doWork() {
        fetchData();
        return Result.success();
    }

    /*
     * The method is doing nothing but only generating
     * a simple notification
     * If you are confused about it
     * you should check the Android Notification Tutorial
     * */
    private void fetchData() {

        String input = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByDistrict?district_id=709&date=16-05-2021";
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
        }

//        Parse And Send Mail

        String body = "";
        try {

            JSONObject baseJsonResponse = new JSONObject(current);
            JSONArray centers = baseJsonResponse.getJSONArray("Centers");


//
//            int totalactive = baseJsonResponse.getInt("activeCases");
//            int newactive = baseJsonResponse.getInt("activeCasesNew");
//            int totaldeaths = baseJsonResponse.getInt("deaths");
//            String date = baseJsonResponse.getString("lastUpdatedAtApify");
//            String formattedDate  = date.substring(8 , 10) + date.substring(7 , 8) + date.substring(5, 7) + date.substring(7 , 8) + date.substring(0 , 4) + getString(R.string.timePrefix) + date.substring(11 , 19);
//            JSONArray arr = baseJsonResponse.getJSONArray("regionData");
//            String last =  Integer.toString(totalcase) + " " + Integer.toString(totalactive) + " " + Integer.toString(newactive) + " " + Integer.toString(totaldeaths) + " " +
//                    formattedDate;

            for(int i = 0; i < centers.length(); i++) {
                String centerName = centers.getJSONObject(i).getString("name ");
                String centerAddress = centers.getJSONObject(i).getString("address");
                String districtName = centers.getJSONObject(i).getString("district_name");
                String block = centers.getJSONObject(i).getString("block_name");
                int pinCode = centers.getJSONObject(i).getInt("pincode");

                JSONArray sessions = centers.getJSONObject(i).getJSONArray("sessions");
                int minimumAge = sessions.getJSONObject(0).getInt("min_age_limit");
                String vaccineType = sessions.getJSONObject(0).getString("vaccine");
                int dose1 = sessions.getJSONObject(0).getInt("available_capacity_dose1");
                int dose2 = sessions.getJSONObject(0).getInt("available_capacity_dose2");
                body = body + "centerName : " + centerName + "\n";
            }
            if(body.length() > 0)
                SendEmail(body);

        }
        catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
    }

    public void SendEmail(String body)
    {

        // email ID of Recipient.
        String recipient = "linuxkali194@gmail.com";

        // email ID of  Sender.
        String sender = "linuxkali194@gmail.com";

        // using host as localhost
        String host = "127.0.0.1";

        // Getting system properties
        Properties properties = System.getProperties();

        // Setting up mail server
        properties.setProperty("mail.smtp.host", host);

        // creating session object to get properties
        Session session = Session.getDefaultInstance(properties);

        try
        {
            // MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From Field: adding senders email to from field.
            message.setFrom(new InternetAddress(sender));

            // Set To Field: adding recipient's email to from field.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

            // Set Subject: subject of the email
            message.setSubject("Vaccination Slot Found!");

            // set body of the email.
            message.setContent(body,"text/html");

            // Send email.
            Transport.send(message);
        }
        catch (MessagingException mex)
        {
            mex.printStackTrace();
        }
    }
}