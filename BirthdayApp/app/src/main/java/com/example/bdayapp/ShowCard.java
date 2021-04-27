package com.example.BirthdayApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.BirthdayApp.R;

public class ShowCard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_card);

        TextView receiver_msg = (TextView)findViewById(R.id.to);
        Intent intent = getIntent();
        String str = intent.getStringExtra("message_to");
        receiver_msg.setText(str);

        TextView receiver_msg1 = (TextView)findViewById(R.id.from);
        Intent intent1 = getIntent();
        String str1 = intent1.getStringExtra("message_from");
        receiver_msg1.setText(str1);

    }
}