package com.example.ImageToPdf;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class SelectAndConvert extends AppCompatActivity {


    ActivityResultLauncher<Intent> someActivityResultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_item);


        ArrayList<Image> image  = new ArrayList<>();

        ImageView mImage = (ImageView) findViewById(R.id.currentImage);

        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();

                        int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.

                        Log.d("Dhoondle", String.valueOf(count));

                        for(int i = 0; i < count; i++) {

                            Uri uri = data.getClipData().getItemAt(i).getUri();
                            
                        }
                    }
                });
        }

    public void Clicked(View view)
    {
        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("image/*");
        chooseFile.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        chooseFile = Intent.createChooser(chooseFile, "Choose image files");
        someActivityResultLauncher.launch(chooseFile);
    }


}
