package com.doan.quanlycachly.helpers;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;


import com.doan.quanlycachly.R;

import java.io.InputStream;
import java.net.URL;

public class DownLoadImage extends AsyncTask<String, Void, Bitmap> {

    @SuppressLint("StaticFieldLeak")
    private
    ImageView bmImage;
    private String urlImage;

    public DownLoadImage(ImageView bmImage, String URL) {
        this.bmImage = bmImage;
        this.urlImage = URL;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap bmp = null;

        try {
            InputStream in = new URL(urlImage).openStream();
            bmp = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return bmp;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
//        if (bitmap == null){
//            bmImage.setImageResource(R.drawable.ic_profile);
//        }else{
//            bmImage.setImageBitmap(bitmap);
//        }

    }
}
