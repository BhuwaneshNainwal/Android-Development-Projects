package com.example.motivationalquotes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.motivationalquotes.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class DataModalAdapter extends ArrayAdapter<DataModal> {

    private Context context;
    // constructor for our list view adapter.
    public DataModalAdapter(@NonNull Context context, ArrayList<DataModal> dataModalArrayList) {
        super(context, 0, dataModalArrayList);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // below line is use to inflate the
        // layout for our item of list view.
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.grid_view_items, parent, false);
        }

        // after inflating an item of listview item
        // we are getting data from array list inside
        // our modal class.
        DataModal dataModal = getItem(position);

        ImageView courseIV = listitemView.findViewById(R.id.idIVimage);

        // in below line we are using Picasso to load image
        // from URL in our Image VIew.

        Glide.with(context).load(dataModal.getImgUrl()).into(courseIV);
//        Picasso.get().load(dataModal.getImgUrl()).into(courseIV);

        // below line is use to add item
        // click listener for our item of list view.
        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on the item click on our list view.
                // we are displaying a toast message.
//                Toast.makeText(getContext(), "Item clicked is : " + dataModal.getName(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, FullScreenViewActivity.class);
                i.putExtra("position", position);
                i.putExtra("url", dataModal.getImgUrl());
                context.startActivity(i);
            }
        });
        return listitemView;
    }
}
