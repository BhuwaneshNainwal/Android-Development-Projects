package com.example.covidtrackingapp;

import android.content.Context;
import android.content.SharedPreferences;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class CowinActivity extends AppCompatActivity
implements View.OnClickListener
 {

        private Button msubmit;
        private Button mcancel;
        SharedPreferences spStateButton;
        SharedPreferences.Editor spEditor;

        @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cowin);
        spStateButton= getApplicationContext().getSharedPreferences("Button_State", 0);
        spEditor = spStateButton.edit();

        msubmit = findViewById(R.id.buttonsubmit);
        msubmit.setOnClickListener(this);
        mcancel = findViewById(R.id.buttoncancel);
        mcancel.setOnClickListener(this);
        mcancel.setVisibility(View.GONE);
    }

     @Override
     protected void onResume() {
         super.onResume();


         EditText emailText = (EditText) findViewById(R.id.email);
         EditText stateText = (EditText) findViewById(R.id.state);
         EditText districtText = (EditText) findViewById(R.id.district);
         SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
         String s1 = sh.getString("email", "");
         String s2 = sh.getString("state", "");
         String s3 = sh.getString("district", "");

         emailText.setText(s1);
         stateText.setText(s2);
         districtText.setText(s3);
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

             // write all the data entered by the user in SharedPreference and apply
             myEdit.putString("email", emailText.getText().toString());
             myEdit.putString("state", stateText.getText().toString());
             myEdit.putString("district", districtText.getText().toString());
             myEdit.apply();
         }

     }

    @Override
    public void onClick(View v) {


        EditText emailText = (EditText) findViewById(R.id.email);
        EditText stateText = (EditText) findViewById(R.id.state);
        EditText districtText = (EditText) findViewById(R.id.district);
        PeriodicWorkRequest send;
        int id = v.getId();
        Toast emailDetachedToast = new Toast(getApplicationContext());
        Toast submitToast = new Toast(getApplicationContext());



        if(id == R.id.buttonsubmit)
        {


            emailDetachedToast.cancel();
            emailText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            String name = "";
            name = emailText.getText().toString();
            if(name.matches("")){
                Context context = getApplicationContext();
                CharSequence text = "Email field cannot be empty";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                return;
            }

            name = "";
            name = stateText.getText().toString();
            if(name.matches("")){
                Context context = getApplicationContext();
                CharSequence text = "State field cannot be empty";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                return;
            }

            name = "";
            name = districtText.getText().toString();
            if(name.matches("")){
                Context context = getApplicationContext();
                CharSequence text = "District field cannot be empty";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                return;
            }

            String emailToText = emailText.getText().toString();

            if (!emailToText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailToText).matches()) {

            } else {
                emailText.setError("Invalid Email");
                return;
            }




            EditText firstName = (EditText)findViewById(R.id.state);
            String stateName = stateText.getText().toString();
            if( stateName.charAt(0) < 'A' || stateName.charAt(0) > 'Z') {
                firstName.setError("First letter of state must be capital");
                return;
            }


            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.customtoast, (ViewGroup) findViewById(R.id.custom_toast_layout));
            TextView tv = (TextView) layout.findViewById(R.id.txtvw);
            tv.setText("Will notify you about open slots once available!");
            submitToast = new Toast(getApplicationContext());
            submitToast.setGravity(Gravity.CENTER_VERTICAL, 0, 100);
            submitToast.setDuration(Toast.LENGTH_LONG);
            submitToast.setView(layout);
            submitToast.show();

            mcancel.setVisibility(View.VISIBLE);
            msubmit.setVisibility(View.GONE);

//            emailText.setFocusable(false);
//            emailText.setClickable(false);
//            districtText.setClickable(false);
//            districtText.setFocusable(false);
//            stateText.setClickable(false);
//            stateText.setFocusable(false);

            send = new PeriodicWorkRequest.Builder(SlotCheck.class , 16 , TimeUnit.MINUTES).addTag("SendMail").setInputData(
                    new Data.Builder()
                            .putString("email", emailText.getText().toString()).putString("state", stateText.getText().toString()).putString("district", districtText.getText().toString())
                            .build()).build();
            WorkManager.getInstance().enqueue(send);


        }
        else
        {

            mcancel.setVisibility(View.GONE);
            msubmit.setVisibility(View.VISIBLE);
            submitToast.cancel();
            districtText.getText().clear();
            stateText.getText().clear();
            emailText.getText().clear();
//
//            emailText.setFocusable(true);
//            emailText.setClickable(true);
//            districtText.setClickable(true);
//            districtText.setFocusable(true);
//            stateText.setClickable(true);
//            stateText.setFocusable(true);


            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.customtoastcancel, (ViewGroup) findViewById(R.id.custom_toast_cancel_layout));
            TextView tv = (TextView) layout.findViewById(R.id.txtvw);
            tv.setText("Email id has been detached successfully!");
            emailDetachedToast.setGravity(Gravity.CENTER_VERTICAL, 0, 100);
            emailDetachedToast.setDuration(Toast.LENGTH_LONG);
            emailDetachedToast.setView(layout);
            emailDetachedToast.show();
            WorkManager.getInstance().cancelAllWorkByTag("SendMail");
        }

    }





}
