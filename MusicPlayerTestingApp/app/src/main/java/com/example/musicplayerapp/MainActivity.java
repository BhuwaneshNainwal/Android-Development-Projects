package com.example.musicplayerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    //Global
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this , R.raw.song);

        Button playButton = (Button)findViewById(R.id.play_button);
        playButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                Context context = getApplicationContext();
                CharSequence text = "Playing...";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                mediaPlayer.start();
            }
        });

        Button pauseButton = (Button)findViewById(R.id.pause_button);
        pauseButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                Context context = getApplicationContext();
                CharSequence text = "Paused";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                mediaPlayer.pause();
            }
        });
    }
}