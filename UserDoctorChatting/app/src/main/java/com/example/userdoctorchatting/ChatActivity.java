package com.example.userdoctorchatting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.userdoctorchatting.Adapters.MessageAdapters;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Models.Message;
import Models.Doctor;

public class ChatActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference messagedb;
    MessageAdapters messageadapter;
    Doctor u;
    List<Message> messages;

    RecyclerView rcMessage;
    EditText etMessage;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        init();
    }

    public void init()
    {
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        u = new Doctor();
        rcMessage = (RecyclerView)findViewById(R.id.rvMessage);
        etMessage = (EditText)findViewById(R.id.etmessage);
        button = (Button)findViewById(R.id.sendbut);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(etMessage.getText().toString()))
                {
                    Message message = new Message(etMessage.getText().toString() , u.getName());
                    etMessage.setText("");
                    messagedb.push().setValue(message);
                }
                else{
                    Toast.makeText(getApplicationContext() ,"Message can't be empty!" , Toast.LENGTH_SHORT).show();
                }

            }
        });
        messages = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuLogout)
        {
            auth.signOut();
            finish();
            startActivity(new Intent(ChatActivity.this , MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final FirebaseUser currentUser = auth.getCurrentUser();
        u.setUid(currentUser.getUid());
        u.setEmail(currentUser.getEmail());

        database.getReference("Users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                u = snapshot.getValue(Doctor.class);
                u.setUid(currentUser.getUid());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        messagedb = database.getReference("messages");
        messagedb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Message message = snapshot.getValue(Message.class);
                message.setKey(snapshot.getKey());
                messages.add(message);
                displayMessages(messages);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                Message message = snapshot.getValue(Message.class);
                message.setKey(snapshot.getKey());
                List<Message> newMessages = new ArrayList<Message>();

                for(Message m : messages)
                {
                    if(m.getKey().equals(message.getKey()))
                    {
                        newMessages.add(message);
                    }
                    else{

                        newMessages.add(m);
                    }
                }
                messages = newMessages;
                displayMessages(messages);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    Message message = snapshot.getValue(Message.class);
                    message.setKey(snapshot.getKey());
                    List<Message> newMessages = new ArrayList<Message>();
                    for(Message m : messages){
                        if(!m.getKey().equals(message.getKey()))
                        {
                            newMessages.add(m);
                        }
                    }

                    messages = newMessages;
                    displayMessages(messages);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        messages = new ArrayList<>();

    }

    private void displayMessages(List<Message> messages){
//        rcMessage.setLayoutManager(new LinearLayoutManager(ChatActivity.this , messages , messagedb));
        messageadapter = new MessageAdapters(ChatActivity.this , messages,messagedb);
        rcMessage.setAdapter(messageadapter);
    }
}