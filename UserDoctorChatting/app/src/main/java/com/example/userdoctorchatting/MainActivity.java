package com.example.userdoctorchatting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    DatabaseReference reference;
    EditText textmail , textpassword;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null){
            Intent chat = new Intent(MainActivity.this , ActiveInactive.class);
            startActivity(chat);
            finish();
        }
        else
        {
            setContentView(R.layout.activity_main);
            textmail = (EditText)findViewById(R.id.username);
            textpassword = (EditText)findViewById(R.id.password);
        }
    }

    public void LoginUser(View v) {
        String username = textmail.getText().toString();
        String password = textpassword.getText().toString();
        if (!username.equals("") && !password.equals("")) {
            auth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();
                                Intent temp = new Intent(MainActivity.this, ActiveInactive.class);
                                startActivity(temp);
                            } else {
                                Toast.makeText(getApplicationContext(), "Invalid email / password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "Email/Password can't be empty", Toast.LENGTH_SHORT).show();
        }
    }
    public void gotoRegister(View v)
    {
        Intent i = new Intent(MainActivity.this , RegisterActivity.class);
        startActivity(i);
    }

    public void goToActiveListActivity(View v)
    {
        Intent i = new Intent(MainActivity.this , ShowUsersDynamicActiveList.class);
        startActivity(i);
    }

};