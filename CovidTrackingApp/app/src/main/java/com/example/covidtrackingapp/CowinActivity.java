package com.example.covidtrackingapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;


public class CowinActivity extends AppCompatActivity
 {

        private Button msubmit;
        private Button mcancel;


        @Override
        protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cowin);


        TextView textView = findViewById(R.id.ins);
        Typeface typeface = Typeface.createFromAsset(
                getAssets(),
                "dance.ttf");
        textView.setTypeface(typeface);





        msubmit = findViewById(R.id.buttonsubmit);
        mcancel = findViewById(R.id.buttoncancel);




        EditText emailText = (EditText) findViewById(R.id.email);
        EditText stateText = (EditText) findViewById(R.id.state);
        EditText districtText = (EditText) findViewById(R.id.district);
        EditText vaccineText = (EditText)findViewById(R.id.tovaccine);
        final SharedPreferences prefs = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);

            msubmit.setVisibility(prefs.getBoolean("submit", true) ? View.VISIBLE : View.INVISIBLE);
            mcancel.setVisibility(prefs.getBoolean("cancel", true) ? View.VISIBLE : View.INVISIBLE);

            if(prefs.getBoolean("submit", true) == true)
            {
                prefs.edit().putBoolean("cancel", false).apply();
                mcancel.setVisibility(View.INVISIBLE);
            }


            Toast emailDetachedToast = new Toast(getApplicationContext());
            Toast submitToast = new Toast(getApplicationContext());

            msubmit.setOnClickListener(new View.OnClickListener() {
                Toast toast = null;

            @Override
            public void onClick(View v) {

                PeriodicWorkRequest send;

                emailDetachedToast.cancel();
                emailText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                String name = "";


                String emailToText = emailText.getText().toString();
                if (emailToText.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailToText).matches()) {
                    emailText.setError("Invalid Email");
                    return;
                }

                name = "";
                name = stateText.getText().toString();
                if(name.matches("")){
                    Context context = getApplicationContext();
                    CharSequence text = "State field cannot be empty";

                        toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                        toast.show();
                    return;
                }

                name = vaccineText.getText().toString();
                if(!name.equals("COVAXIN") && !name.equals("COVISHIELD"))
                {
                    Context context = getApplicationContext();
                    CharSequence text = "Invalid Vaccine Type";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    return;
                }
                name = districtText.getText().toString();
                if(name.matches("")){
                    Context context = getApplicationContext();
                    CharSequence text = "District field cannot be empty";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    return;
                }



                EditText firstName = (EditText)findViewById(R.id.state);
                String stateName = stateText.getText().toString();
                if( stateName.charAt(0) < 'A' || stateName.charAt(0) > 'Z') {
                    firstName.setError("First letter must be capital");
                    return;
                }



                Context context = getApplicationContext();
                CharSequence text = "You will be notified once slot available!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();




                prefs.edit().putBoolean("submit", false).apply();
                msubmit.setVisibility(View.INVISIBLE);

                prefs.edit().putBoolean("cancel", true).apply();
                mcancel.setVisibility(View.VISIBLE);


                send = new PeriodicWorkRequest.Builder(SlotCheck.class , 16 , TimeUnit.MINUTES).addTag("SendMail").setInputData(
                        new Data.Builder()
                                .putString("email", emailText.getText().toString()).putString("state", stateText.getText().toString()).putString("district", districtText.getText().toString()).putString("vaccine type", vaccineText.getText().toString())
                                .build()).build();
                WorkManager.getInstance().enqueue(send);
            }
        });


        mcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                prefs.edit().putBoolean("cancel", false).apply();
                mcancel.setVisibility(View.INVISIBLE);


                prefs.edit().putBoolean("submit", true).apply();
                msubmit.setVisibility(View.VISIBLE);


                submitToast.cancel();
                districtText.getText().clear();
                stateText.getText().clear();
                emailText.getText().clear();

                Context context = getApplicationContext();
                CharSequence text = "Email has been detached";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                WorkManager.getInstance().cancelAllWorkByTag("SendMail");

            }
        });


        }

     @Override
     protected void onResume() {
         super.onResume();

         EditText emailText = (EditText) findViewById(R.id.email);
         EditText stateText = (EditText) findViewById(R.id.state);
         EditText districtText = (EditText) findViewById(R.id.district);
         EditText vaccineText = (EditText)findViewById(R.id.tovaccine);
         SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
         String s1 = sh.getString("email", "");
         String s2 = sh.getString("state", "");
         String s3 = sh.getString("district", "");
         String s4 = sh.getString("vaccine type", "");
         emailText.setText(s1);
         stateText.setText(s2);
         districtText.setText(s3);
         vaccineText.setText(s4);
     }

     @Override
     protected void onPause() {
         super.onPause();
         if (mcancel.isEnabled()) {


             SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
             SharedPreferences.Editor myEdit = sharedPreferences.edit();


             EditText emailText = (EditText) findViewById(R.id.email);
             EditText stateText = (EditText) findViewById(R.id.state);
             EditText districtText = (EditText) findViewById(R.id.district);
             EditText vaccineText = (EditText)findViewById(R.id.tovaccine);

             // write all the data entered by the user in SharedPreference and apply
             myEdit.putString("email", emailText.getText().toString());
             myEdit.putString("state", stateText.getText().toString());
             myEdit.putString("district", districtText.getText().toString());
             myEdit.putString("vaccine type" , vaccineText.getText().toString());
             myEdit.apply();
         }

     }


}
