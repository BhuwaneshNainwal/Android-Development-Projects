package com.example.BirthdayApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.BirthdayApp.R;
import com.example.BirthdayApp.ShowCard;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void OnClickMethod(View view) {
        Intent intent = new Intent(this, ShowCard.class);
        EditText from_info = (EditText) findViewById(R.id.from);
        String from = "From : ";
        from += from_info.getText().toString();
        intent.putExtra("message_from", from);

        EditText to_info = (EditText) findViewById(R.id.to);
        String to = "To : ";
        to += to_info.getText().toString();
        intent.putExtra("message_to", to);
        startActivity(intent);
    }

};