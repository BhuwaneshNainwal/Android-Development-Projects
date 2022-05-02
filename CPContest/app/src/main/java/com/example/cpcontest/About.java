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

        String str_text1 = "<a href=https://lovepik.com/images/png-bell.html> Bell Png vectors by Lovepik.com </a>";
        TextView link1;
        link1 = (TextView) findViewById(R.id.credits1);
        link1.setMovementMethod(LinkMovementMethod.getInstance());
        link1.setText(Html.fromHtml(str_text1));
        link1.setLinkTextColor(Color.BLUE);

        String about = "<a href=https://www.vecteezy.com/free-vector/description>Description Vectors by Vecteezy</a>";
        String rate =  "<a href=https://www.vecteezy.com/free-vector/5-star-logo>5 Star Logo Vectors by Vecteezy</a>";
        String all =  "<a href=https://www.vecteezy.com/free-vector/code>Code Vectors by Vecteezy</a>";
        String top =  "<a href=https://www.vecteezy.com/free-vector/programming>Programming Vectors by Vecteezy</a>";
        String help =  " <a href=https://www.vecteezy.com/free-vector/help>Help Vectors by Vecteezy</a>";

        TextView link2;
        link2 = (TextView) findViewById(R.id.credits2);
        link2.setMovementMethod(LinkMovementMethod.getInstance());
        link2.setText(Html.fromHtml(about));
        link2.setLinkTextColor(Color.BLUE);


        TextView link3;
        link3 = (TextView) findViewById(R.id.credits3);
        link3.setMovementMethod(LinkMovementMethod.getInstance());
        link3.setText(Html.fromHtml(rate));
        link3.setLinkTextColor(Color.BLUE);

        TextView link4;
        link4 = (TextView) findViewById(R.id.credits4);
        link4.setMovementMethod(LinkMovementMethod.getInstance());
        link4.setText(Html.fromHtml(all));
        link4.setLinkTextColor(Color.BLUE);

        TextView link5;
        link5 = (TextView) findViewById(R.id.credits5);
        link5.setMovementMethod(LinkMovementMethod.getInstance());
        link5.setText(Html.fromHtml(top));
        link5.setLinkTextColor(Color.BLUE);

        TextView link6;
        link6 = (TextView) findViewById(R.id.credits6);
        link6.setMovementMethod(LinkMovementMethod.getInstance());
        link6.setText(Html.fromHtml(help));
        link6.setLinkTextColor(Color.BLUE);
    }


}

