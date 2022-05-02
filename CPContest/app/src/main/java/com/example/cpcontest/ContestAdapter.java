package com.example.cpcontest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.example.cpcontest.R.layout.list_item_view;

class ContestAdapter implements ListAdapter {

    public static String notificationTittle;
    private static int NOTIFICATION_ID = 1;

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


                ImageButton image = convertView.findViewById(R.id.bellIcon);
                image.setEnabled(false);
                image.setVisibility(View.GONE);

                image = convertView.findViewById(R.id.bellCrossIcon);
                image.setEnabled(false);
                image.setVisibility(View.GONE);

                currentLayout = (LinearLayout) convertView.findViewById(R.id.bellLayout);
                currentLayout.setBackgroundColor(Color.parseColor("#EF9A9A"));

                ArrayList<String> arrayList = getArrayList("contest");
                if(arrayList.contains(contest.getContestName())) {
                    arrayList.remove(contest.getContestName());
                }

            }
            else if(contest.getTwentyFourHours().equals("Yes"))
            {

                LinearLayout currentLayout;
                currentLayout = (LinearLayout) convertView.findViewById(R.id.list);

                currentLayout.setBackgroundColor(Color.parseColor("#c6f68d"));
                currentLayout = (LinearLayout) convertView.findViewById(R.id.vertical);
                currentLayout.setBackgroundColor(Color.parseColor("#c6f68d"));
                tittle.setBackgroundColor(Color.parseColor("#c6f68d"));

                currentLayout = (LinearLayout) convertView.findViewById(R.id.bellLayout);
                currentLayout.setBackgroundColor(Color.parseColor("#c6f68d"));

                ImageButton image = convertView.findViewById(R.id.bellIcon);
                image.setBackgroundColor(Color.parseColor("#c6f68d"));

                ImageButton image1 = convertView.findViewById(R.id.bellCrossIcon);

                image1.setBackgroundColor(Color.parseColor("#c6f68d"));

                ArrayList<String> contestAdded = getArrayList("contest");

                if(contestAdded.contains(contest.getContestName())){

                    image.setEnabled(false);
                    image.setVisibility(View.GONE);
                }


                image.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {

                        contestAdded.add(contest.getContestName());
                        saveArrayList(contestAdded, "contest");

                        final Animation animation = AnimationUtils.loadAnimation(context,R.anim.bounce_animation);
                        image.setAnimation(animation);

                        animation.setAnimationListener(new Animation.AnimationListener(){
                            @Override
                            public void onAnimationStart(Animation arg0) {


                            }
                            @Override
                            public void onAnimationRepeat(Animation arg0) {
                            }
                            @Override
                            public void onAnimationEnd(Animation arg0) {

                                image.clearAnimation();
                                image.setVisibility(View.GONE);
                                image.setEnabled(false);
                            }

                        });

                        image.startAnimation(animation);

                        notificationTittle = tittle.getText().toString();
                        Intent intent = new Intent(context, ReminderBroadcast.class);
                        intent.putExtra( "ContestName", notificationTittle);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        NOTIFICATION_ID++;

                        DateTimeFormatter parser = DateTimeFormatter.ofPattern("MMM")
                                .withLocale(Locale.ENGLISH);
                        int month = parser.parse(contest.getStartTime().substring(3, 6)).get(ChronoField.MONTH_OF_YEAR);
                        int date = Integer.parseInt(contest.getStartTime().substring(0, 2));
                        int year = Integer.parseInt(contest.getStartTime().substring(7, 11));
                        int hour = Integer.parseInt(contest.getStartTime().substring(16, 18));
                        int minutes = Integer.parseInt(contest.getStartTime().substring(19, 21));

                        Calendar c = Calendar.getInstance();
                        c.setTimeInMillis(System.currentTimeMillis());
                        c.set(year, month - 1, date, hour, minutes - 10, 0);
                        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

                        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){

                            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
                        }
                        else {
                            alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
                        }
                    }
                });

            }
            else
            {
                ImageButton image = convertView.findViewById(R.id.bellIcon);
                ImageButton image1 = convertView.findViewById(R.id.bellCrossIcon);

                ArrayList<String> contestAdded = getArrayList("contest");

                if(contestAdded.contains(contest.getContestName())){

                    image.setEnabled(false);
                    image.setVisibility(View.GONE);
                }


                LinearLayout currentLayout = (LinearLayout) convertView.findViewById(R.id.list);
                currentLayout.setBackgroundColor(Color.parseColor("#ffc77d"));
                currentLayout = (LinearLayout) convertView.findViewById(R.id.vertical);
                currentLayout.setBackgroundColor(Color.parseColor("#ffc77d"));
                tittle.setBackgroundColor(Color.parseColor("#ffc77d"));

                currentLayout = (LinearLayout) convertView.findViewById(R.id.bellLayout);
                currentLayout.setBackgroundColor(Color.parseColor("#ffc77d"));

                image.setBackgroundColor(Color.parseColor("#ffc77d"));
                image1.setBackgroundColor(Color.parseColor("#ffc77d"));

                image.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {

                        contestAdded.add(contest.getContestName());
                        saveArrayList(contestAdded, "contest");

                        final Animation animation = AnimationUtils.loadAnimation(context,R.anim.bounce_animation);
                        image.setAnimation(animation);

                        animation.setAnimationListener(new Animation.AnimationListener(){
                            @Override
                            public void onAnimationStart(Animation arg0) {

                            }
                            @Override
                            public void onAnimationRepeat(Animation arg0) {
                            }
                            @Override
                            public void onAnimationEnd(Animation arg0) {

                                image.clearAnimation();
                                image.setVisibility(View.GONE);
                                image.setEnabled(false);
                            }

                        });

                        image.startAnimation(animation);


                        Toast.makeText(context, "You will be notified 10 minutes before", Toast.LENGTH_SHORT).show();

                        notificationTittle = tittle.getText().toString();
                        Intent intent = new Intent(context, ReminderBroadcast.class);
                        intent.putExtra("ContestName", notificationTittle);

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        NOTIFICATION_ID++;

                        DateTimeFormatter parser = DateTimeFormatter.ofPattern("MMM")
                                .withLocale(Locale.ENGLISH);
                        int month = parser.parse(contest.getStartTime().substring(3, 6)).get(ChronoField.MONTH_OF_YEAR);
                        int date = Integer.parseInt(contest.getStartTime().substring(0, 2));
                        int year = Integer.parseInt(contest.getStartTime().substring(7, 11));
                        int hour = Integer.parseInt(contest.getStartTime().substring(16, 18));
                        int minutes = Integer.parseInt(contest.getStartTime().substring(19, 21));

                        Calendar c = Calendar.getInstance();
                        c.setTimeInMillis(System.currentTimeMillis());
                        c.set(year, month - 1, date, hour, minutes - 10, 0);
                        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

                        } else {
                            alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
                        }

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


    public void saveArrayList(ArrayList<String> list, String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();

    }

    public ArrayList<String> getArrayList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);

        if(json == null || json == "")
            return new ArrayList<>();
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }




}