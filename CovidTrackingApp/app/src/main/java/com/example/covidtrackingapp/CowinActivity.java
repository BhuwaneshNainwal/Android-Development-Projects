package com.example.covidtrackingapp;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class CowinActivity extends AppCompatActivity
implements View.OnClickListener
 {
        private Button mButton;
        private Button nButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cowin);
        mButton = findViewById(R.id.buttonsubmit);
        mButton.setOnClickListener(this);

        nButton = findViewById(R.id.buttoncancel);
        nButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        PeriodicWorkRequest send;
        int id = v.getId();
        if(id == R.id.buttonsubmit)
        {
            EditText emailText = (EditText) findViewById(R.id.email);
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

            EditText stateText = (EditText) findViewById(R.id.state);
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

            EditText districtText = (EditText) findViewById(R.id.district);
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


            send = new PeriodicWorkRequest.Builder(SlotCheck.class , 16 , TimeUnit.MINUTES).addTag("SendMail").setInputData(
                    new Data.Builder()
                            .putString("email", emailText.getText().toString()).putString("state", stateText.getText().toString()).putString("district", districtText.getText().toString())
                            .build()).build();
            WorkManager.getInstance().enqueue(send);
        }
        else
        {

            EditText emailText = (EditText) findViewById(R.id.email);
            EditText stateText = (EditText) findViewById(R.id.state);
            EditText districtText = (EditText) findViewById(R.id.district);
            districtText.getText().clear();
            stateText.getText().clear();
            emailText.getText().clear();
            WorkManager.getInstance().cancelAllWorkByTag("SendMail");
        }

    }



}
