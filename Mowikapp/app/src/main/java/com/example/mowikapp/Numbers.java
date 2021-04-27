package com.example.mowikapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Numbers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        //Storing words
        ArrayList<Word> words = new ArrayList<Word>(10);
        words.add(new Word("one", "lutti" , R.drawable.number_one));
        words.add(new Word("two", "otiiko" , R.drawable.number_two));
        words.add(new Word("three", "tolookosu" , R.drawable.number_three));
        words.add(new Word("four", "oyyisa" , R.drawable.number_four));
        words.add(new Word("five", "massokka" , R.drawable.number_five));
        words.add(new Word("six", "temmokka" , R.drawable.number_six));
        words.add(new Word("seven", "kenekaku" , R.drawable.number_seven));
        words.add(new Word("eight", "kawinta" , R.drawable.number_eight));
        words.add(new Word("nine", "wo’e" , R.drawable.number_nine));
        words.add(new Word("ten", "na’aacha" , R.drawable.number_ten));

        AdapterArr adapter = new AdapterArr(this , words , R.color.category_numbers);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
    }



}