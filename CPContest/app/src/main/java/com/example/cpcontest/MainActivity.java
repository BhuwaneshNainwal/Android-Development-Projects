package com.example.cpcontest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();

        TextView goToContest;

        goToContest = findViewById(R.id.gotToContest);

        TextView about;

        about = findViewById(R.id.about);

        TextView help;
        help = findViewById(R.id.help);

        TextView rate;

        rate = findViewById(R.id.rate);


        ImageView allImage = findViewById(R.id.allImage);
        ImageView aboutImage = findViewById(R.id.aboutImage);
        ImageView helpImage = findViewById(R.id.helpImage);
        ImageView rateImage = findViewById(R.id.rateImage);

        goToContest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }

            public void openNewActivity() {


                if(isNetworkStatusAvialable (getApplicationContext())) {

                    Intent intent = new Intent(MainActivity.this, contest_item.class);
                    startActivity(intent);

                } else {

                    goToContest.setEnabled(false);
                    Toast.makeText(getApplicationContext(), "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            goToContest.setEnabled(true);

                        }
                    },3000);// set time as per your requirement
                }

            }
        });



        allImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }

            public void openNewActivity() {


                if(isNetworkStatusAvialable (getApplicationContext())) {

                    Intent intent = new Intent(MainActivity.this, contest_item.class);
                    startActivity(intent);

                } else {

                    goToContest.setEnabled(false);
                    Toast.makeText(getApplicationContext(), "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            goToContest.setEnabled(true);

                        }
                    },3000);// set time as per your requirement
                }

            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                openNewActivity();
            }

            public void openNewActivity() {
                Intent intent = new Intent(MainActivity.this, About.class);
                startActivity(intent);
            }
        });

        aboutImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                openNewActivity();
            }

            public void openNewActivity() {
                Intent intent = new Intent(MainActivity.this, About.class);
                startActivity(intent);
            }
        });


        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }

            public void openNewActivity() {
                Intent intent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        });

        helpImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }

            public void openNewActivity() {
                Intent intent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        });

        rateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rateApp();
            }

        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rateApp();
            }

        });
    }
    private void createNotificationChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel("CP CONTEST", "ANDROID", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            channel.setDescription("Contest Reminder");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    //check internet connection
    public static boolean isNetworkStatusAvialable (Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null)
        {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if(netInfos != null)
            {
                return netInfos.isConnected();
            }
        }
        return false;
    }


    /*
     * Start with rating the app
     * Determine if the Play Store is installed on the device
     *
     * */
    public void rateApp()
    {
        try
        {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        }
        catch (ActivityNotFoundException e)
        {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details?id=com.ABSTech.cpcontest");
            startActivity(rateIntent);
        }
    }

    private Intent rateIntentForUrl(String url)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21)
        {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        }
        else
        {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }

}

