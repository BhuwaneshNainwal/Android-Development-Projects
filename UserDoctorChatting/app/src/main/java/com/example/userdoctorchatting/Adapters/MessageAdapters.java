package com.example.userdoctorchatting.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

import Models.Message;

public class MessageAdapters extends RecyclerView.Adapter<MessageAdapters.MessageAdaptersViewHolder> {

    Context context;
    List<Message> messages;
    DatabaseReference messagedb;

    public MessageAdapters(Context context , List<Message> messages , DatabaseReference messagedb)
    {
        this.context = context;
        this.messagedb = messagedb;
        this.messages = messages;
    }


    @NonNull
    @Override
    public MessageAdaptersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdaptersViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MessageAdaptersViewHolder extends  RecyclerView.ViewHolder
    {
        TextView tvtitle;
        Button ibdelete;

        public MessageAdaptersViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
