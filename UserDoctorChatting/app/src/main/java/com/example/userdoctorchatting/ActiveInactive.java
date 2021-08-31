package com.example.userdoctorchatting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import Models.Doctor;

public class ActiveInactive extends AppCompatActivity {

    Doctor doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_inactive);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("Doctors").child(firebaseUser.getUid());

        // Attach a listener to read the data at our doctors reference
        ref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            doctor = dataSnapshot.getValue(Doctor.class);
            Log.d("Your Error" , doctor.getName());
        }

        Button button = (Button)findViewById(R.id.submit);

            @Override
        public void onCancelled(DatabaseError databaseError) {
            System.out.println("The read failed: " + databaseError.getCode());
        }
        });

    }

    public void toggleAction(View view)
    {
        ToggleButton toggleButton = (ToggleButton)findViewById(R.id.toggleButton);
        if(toggleButton.isChecked())
        {
            final FirebaseDatabase databas = FirebaseDatabase.getInstance();

            DatabaseReference ref = databas.getReference();

            ref.child("Active Doctors List").child(doctor.getName()).setValue(doctor.getName()).addOnCompleteListener(task1 -> {

                if(task1.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Kaam ho gya", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Kaam nhi hua", Toast.LENGTH_SHORT).show();
                }

            });
        }
        else{

            DatabaseReference dbref= FirebaseDatabase.getInstance().getReference().child("Active Doctors List").child(doctor.getName());
            // we are use add listerner
            // for event listener method
            // which is called with query.
            Query query=dbref;
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // remove the value at refernce
                    if(dataSnapshot.exists())
                        dataSnapshot.getRef().removeValue();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public void back(View view)
    {
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(ActiveInactive.this , MainActivity.class);
        startActivity(i);
    }
}