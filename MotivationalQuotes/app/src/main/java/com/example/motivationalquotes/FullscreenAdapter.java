package com.example.motivationalquotes;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.motivationalquotes.R;
import com.artjimlop.altex.AltexImageDownloader;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import github.ishaan.buttonprogressbar.ButtonProgressBar;

class FullScreenAdapter extends PagerAdapter {

    private ProgressDialog progressDialog;
    private Activity _activity;
    private ArrayList<String> imgUrl;
    private LayoutInflater inflater;
    private int progressType = 0;

    // constructor
    public FullScreenAdapter(Activity activity,
                                  ArrayList<String> imgUrl) {
        this._activity = activity;
        this.imgUrl = imgUrl;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public int getCount() {
        return this.imgUrl.size();
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imgDisplay;
        Button btnDownload;
        Log.i("Dhoondle", imgUrl.get(position));

        progressType = position;
        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.fullscreen_layout, container,
                false);

        imgDisplay = (ImageView) viewLayout.findViewById(R.id.imgDisplay);

        Picasso.get().load(imgUrl.get(position)).into(imgDisplay);

        final ButtonProgressBar bar = viewLayout.findViewById(R.id.download);
        bar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DownloadFromURL().execute(imgUrl.get(position));
            }
        });

        ((ViewPager) container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }

    class DownloadFromURL extends AsyncTask<String, String, String> {

        ProgressDialog progress = new ProgressDialog(_activity , ProgressDialog.THEME_HOLO_DARK);
        @Override
        protected void onPreExecute() {
            this.progress.setMessage("Downloading");
            this.progress.setCancelable(false);
            this.progress.show();
        }

        @Override
        protected String doInBackground(String... fileUrl) {
            try {
                URL url = new URL(fileUrl[0]);

                Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                boolean saved;
                OutputStream fos;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    ContentResolver resolver = _activity.getContentResolver();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "");
                    contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
                    contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/" +"MotivationQuotes");
                    Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                    fos = resolver.openOutputStream(imageUri);
                } else {
                    String imagesDir = Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DCIM).toString() + File.separator + "MotivationQuotes";

                    File file = new File(imagesDir);

                    if (!file.exists()) {
                        file.mkdir();
                    }

                    File image = new File(imagesDir, "img" + ".png");
                    fos = new FileOutputStream(image);

                }

                saved = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
            return null;
        }


        @Override
        protected void onPostExecute(String file_url) {
            if (progress.isShowing()) {
                progress.dismiss();
            }
        }



    }

}