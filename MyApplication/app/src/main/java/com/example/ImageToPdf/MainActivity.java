package com.example.ImageToPdf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button goToSelectAndConvert;

        goToSelectAndConvert = findViewById(R.id.goToImageToPdf);


        goToSelectAndConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }

            public void openNewActivity() {

                Intent intent = new Intent(MainActivity.this, SelectAndConvert.class);
                startActivity(intent);
            }
        });
    }
}