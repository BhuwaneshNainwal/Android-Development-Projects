package com.example.userdoctorchatting;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import Models.Doctor;

public class RegisterActivity extends AppCompatActivity {

    EditText email , password , name;
    TextView filepath;
    DatabaseReference reference;
    FirebaseAuth auth;
    Uri file;
    ActivityResultLauncher<Intent> someActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        filepath = (TextView)findViewById(R.id.tv_file_path);
        name = (EditText)findViewById(R.id.name);
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Doctors");
        TextView mtext = (TextView)findViewById(R.id.tv_file_path);

        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        file = data.getData();
                        String src = file.getPath();
                        mtext.setText(src);
                    }
                });
    }
    public void Clicked(View view)
    {
        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("*/*");
        chooseFile = Intent.createChooser(chooseFile, "Choose a file");
        someActivityResultLauncher.launch(chooseFile);
    }

    public void RegisterUser(View v){

        final String temail = email.getText().toString();
        final String tpassword = password.getText().toString();
        final String tname = name.getText().toString();
        final String tfilepath = filepath.getText().toString();
        if(!temail.equals("") && !tpassword.equals("") && !tname.equals("") && !tfilepath.equals(""))
        {
            auth.createUserWithEmailAndPassword(temail, tpassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                FirebaseUser firebaseUser = auth.getCurrentUser();
                                Doctor u = new Doctor();
                                u.setName(tname);
                                u.setEmail(temail);
                                u.setUid(firebaseUser.getUid());

                                FirebaseStorage storage = FirebaseStorage.getInstance();
                                StorageReference storageRef = storage.getReference().child("Doctors");
                                StorageReference riversRef = storageRef.child(temail);
                                UploadTask uploadTask = riversRef.putFile(file);

                                // Register observers to listen for when the download is done or if it fails
                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle unsuccessful uploads
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                                        // ...
                                    }
                                });

                                uploadTask = riversRef.putFile(file);

                                // Register observers to listen for when the download is done or if it fails
                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle unsuccessful uploads
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                                        // ...
                                    }
                                });


                                reference.child(firebaseUser.getUid()).setValue(u).addOnCompleteListener(task1 -> {

                                    if(task1.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "User registered successfully!", Toast.LENGTH_SHORT).show();
//                                finish(); // Finish current activity
//                                    Intent i = new Intent(RegisterActivity.this, ChatActivity.class);
//                                    startActivity(i);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "User Can't be  gfdg registered", Toast.LENGTH_SHORT).show();
                                    }

                                });
                            }
                            else {
                                // If sign in fails, display a message to the user.
                                FirebaseAuthException e = (FirebaseAuthException )task.getException();
                                Toast.makeText(RegisterActivity.this, "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else{
            Toast.makeText(getApplicationContext(), "User Can't be registered", Toast.LENGTH_SHORT).show();

        }
    }

    public void gotologin(View v)
    {
        Intent i = new Intent(RegisterActivity.this , MainActivity.class);
        startActivity(i);
    }
}