package com.example.cpcontest;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class About extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        String str_text = "<a href=https://www.linkedin.com/in/bhuwanesh-nainwal-000727195 >https://www.linkedin.com/in/bhuwanesh-nainwal-000727195 </a>";
        TextView link;
        link = (TextView) findViewById(R.id.linkedinProfile);
        link.setMovementMethod(LinkMovementMethod.getInstance());
        link.setText(Html.fromHtml(str_text));
        link.setLinkTextColor(Color.BLUE);

    }
}

