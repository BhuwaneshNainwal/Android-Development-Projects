package com.example.cpcontest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.example.cpcontest.R.layout.list_item_view;

class ContestAdapter implements ListAdapter {

    public static String notificationTittle;

    ArrayList<Contest> arrayList;
    Context context;
    public ContestAdapter(Context context, ArrayList<Contest> arrayList) {
        this.arrayList=arrayList;
        this.context=context;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Contest contest=arrayList.get(position);
        if(convertView==null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView=layoutInflater.inflate(list_item_view, null);

            TextView tittle=convertView.findViewById(R.id.contestName);
            tittle.setText(contest.contestName);

            tittle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(contest.link));
                    context.startActivity(i);
                }
            });

            ImageView imageView = convertView.findViewById(R.id.contestImage);
            imageView.setImageResource(contest.getImageId());


            TextView startTime = convertView.findViewById(R.id.contestStartTime);
            startTime.setText(contest.getStartTime());

            TextView endTime = convertView.findViewById(R.id.contestEndTime);
            endTime.setText(contest.getEndTime());



            if (contest.getRunningStatus() == 1) {

                LinearLayout currentLayout;
                currentLayout = (LinearLayout) convertView.findViewById(R.id.list);

                currentLayout.setBackgroundColor(Color.parseColor("#EF9A9A"));
                currentLayout = (LinearLayout) convertView.findViewById(R.id.vertical);
                currentLayout.setBackgroundColor(Color.parseColor("#EF9A9A"));
                tittle.setBackgroundColor(Color.parseColor("#EF9A9A"));

                ImageView image = convertView.findViewById(R.id.bellIcon);
                image.setBackgroundColor(Color.parseColor("#EF9A9A"));

            }
            else if(contest.getTwentyFourHours().equals("Yes"))
            {
                LinearLayout currentLayout;
                currentLayout = (LinearLayout) convertView.findViewById(R.id.list);

                currentLayout.setBackgroundColor(Color.parseColor("#c6f68d"));
                currentLayout = (LinearLayout) convertView.findViewById(R.id.vertical);
                currentLayout.setBackgroundColor(Color.parseColor("#c6f68d"));
                tittle.setBackgroundColor(Color.parseColor("#c6f68d"));

                ImageView image = convertView.findViewById(R.id.bellIcon);
                image.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(context,"You will be notified 10 minutes before",Toast.LENGTH_SHORT).show();
                        image.setEnabled(false);

                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                image.setEnabled(true);

                            }
                        },3000);// set time as per your requirement

                        notificationTittle = tittle.getText().toString();
                        Intent intent = new Intent(context, ReminderBroadcast.class);
                        intent.putExtra( "ContestName", contest.getContestName());
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
                        

                        DateTimeFormatter parser = DateTimeFormatter.ofPattern("MMM")
                                .withLocale(Locale.ENGLISH);
                        int month = parser.parse(contest.getStartTime().substring(3, 6)).get(ChronoField.MONTH_OF_YEAR);
                        int date = Integer.parseInt(contest.getStartTime().substring(0, 2));
                        int year = Integer.parseInt(contest.getStartTime().substring(7, 11));
                        int hour = Integer.parseInt(contest.getStartTime().substring(16, 18));
                        int minutes = Integer.parseInt(contest.getStartTime().substring(19, 21));

                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.MONTH,month - 1);
                        c.set(Calendar.YEAR,year);
                        c.set(Calendar.DAY_OF_MONTH, date);
                        c.set(Calendar.HOUR_OF_DAY,hour);
                        c.set(Calendar.MINUTE,minutes);
                        c.set(Calendar.SECOND,0);
                        c.add(Calendar.MINUTE, -11);

                        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
                    }
                });

                Drawable myDrawable = image.getResources().getDrawable(R.drawable.bell);
                image.setImageDrawable(myDrawable);
                image.setBackgroundColor(Color.parseColor("#c6f68d"));

            }
            else
            {
                ImageView image = convertView.findViewById(R.id.bellIcon);
                Drawable myDrawable = image.getResources().getDrawable(R.drawable.bell);
                image.setImageDrawable(myDrawable);

                LinearLayout currentLayout = (LinearLayout) convertView.findViewById(R.id.list);
                currentLayout.setBackgroundColor(Color.parseColor("#ffc77d"));
                currentLayout = (LinearLayout) convertView.findViewById(R.id.vertical);
                currentLayout.setBackgroundColor(Color.parseColor("#ffc77d"));
                tittle.setBackgroundColor(Color.parseColor("#ffc77d"));
                image.setBackgroundColor(Color.parseColor("#ffc77d"));

                image.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(context,"You will be notified 10 minutes before",Toast.LENGTH_SHORT).show();
                        image.setEnabled(false);
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                image.setEnabled(true);

                            }
                        },3000);// set time as per your requirement

                            notificationTittle = tittle.getText().toString();
                            Intent intent = new Intent(context, ReminderBroadcast.class);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

                            DateTimeFormatter parser = DateTimeFormatter.ofPattern("MMM")
                                    .withLocale(Locale.ENGLISH);
                            int month = parser.parse(contest.getStartTime().substring(3, 6)).get(ChronoField.MONTH_OF_YEAR);
                            int date = Integer.parseInt(contest.getStartTime().substring(0, 2));
                            int year = Integer.parseInt(contest.getStartTime().substring(7, 11));
                            int hour = Integer.parseInt(contest.getStartTime().substring(16, 18));
                            int minutes = Integer.parseInt(contest.getStartTime().substring(19, 21));

                            Log.d("DHOONDLE", String.valueOf(minutes));

                            Calendar c = Calendar.getInstance();
                            c.set(Calendar.MONTH,month - 1);
                            c.set(Calendar.YEAR,year);
                            c.set(Calendar.DAY_OF_MONTH, date);
                            c.set(Calendar.HOUR_OF_DAY,hour);
                            c.set(Calendar.MINUTE,minutes);
                            c.set(Calendar.SECOND,0);
                            c.add(Calendar.MINUTE, -11);

                            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                            alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
                        }


                });




            }
        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return arrayList.size();
    }


    @Override
    public boolean isEmpty() {
        return false;
    }
}