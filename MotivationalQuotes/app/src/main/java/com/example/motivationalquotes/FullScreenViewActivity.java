package com.example.motivationalquotes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.motivationalquotes.R;

public class FullScreenViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_activity);


        // calling the action bar
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        int position = i.getExtras().getInt("position");


        FullScreenAdapter imageAdapter = null;

        if(MainActivity.activity == 1)
            imageAdapter = new FullScreenAdapter(FullScreenViewActivity.this, Motivation.allImages);

        else if(MainActivity.activity == 2)
            imageAdapter = new FullScreenAdapter(FullScreenViewActivity.this, Love.allImages);

        else if(MainActivity.activity == 3)
            imageAdapter = new FullScreenAdapter(FullScreenViewActivity.this, Birthday.allImages);
        else
            imageAdapter = new FullScreenAdapter(FullScreenViewActivity.this, Thankful.allImages);


        Log.d("Dhoondle", String.valueOf(position));
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(imageAdapter);
        viewPager.setCurrentItem(position);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
