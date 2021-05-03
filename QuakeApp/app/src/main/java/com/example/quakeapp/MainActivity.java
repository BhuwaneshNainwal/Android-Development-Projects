package com.example.quakeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        EarthquakeAdapter earthquakeAdapter = new EarthquakeAdapter(this , QueryUtils.extractEarthquakes());

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(earthquakeAdapter);

    }
}