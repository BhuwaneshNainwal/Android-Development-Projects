package com.example.covidtrackingapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

public class Instructions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructions);

//        TextView textView = findViewById(R.id.ins);
//        Typeface typeface = Typeface.createFromAsset(
//                getAssets(),
//                "dancing.ttf");
//        textView.setTypeface(typeface);
    }
}
