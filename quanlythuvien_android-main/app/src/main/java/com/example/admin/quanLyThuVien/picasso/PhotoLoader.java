package com.example.admin.quanLyThuVien.picasso;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;

public class PhotoLoader implements Target {
    private final String name;

    public PhotoLoader(String name) {
        this.name = name;
    }

    @Override
    public void onPrepareLoad(Drawable arg0) {
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom arg1) {
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + name);
        if (file.exists()) {
            file.delete();
            file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + name);

        }

        try {
            file.createNewFile();
            FileOutputStream ostream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, ostream);
            ostream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

    }
}