package com.example.covidtrackingapp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_root);





        Button stats;
        Button slot;

        stats = (Button) findViewById(R.id.india);
        slot = (Button) findViewById(R.id.slotcheck);

        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }
            public void openNewActivity(){
                Intent intent = new Intent(MainActivity.this, IndiaStatsActivity.class);
                startActivity(intent);
            }
        });

        slot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }
            public void openNewActivity(){
                Intent intent = new Intent(MainActivity.this, CowinActivity.class);
                startActivity(intent);
            }
        });
    }



}