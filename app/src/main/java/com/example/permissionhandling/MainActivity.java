package com.example.permissionhandling;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.BitSet;

public class MainActivity extends AppCompatActivity {
    Button btnSave;
    static final int MY_PERMISSION_REQUEST_WRITE_EXTERNAL_storage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSION_REQUEST_WRITE_EXTERNAL_storage);

                } else {
                    if (isExternalStorageWritable()){
                        Toast.makeText(MainActivity.this, "Mounted", Toast.LENGTH_LONG).show();
                        doStuffsWithExternalStorage();
                    }
                }
            }



        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSION_REQUEST_WRITE_EXTERNAL_storage:{
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    doStuffsWithExternalStorage();
                }
                else {
            }
            return;
        }
    }
}

public boolean isExternalStorageWritable(){
   String state= Environment.getExternalStorageState();
   if(Environment.MEDIA_MOUNTED.equals(state)){
       Log.d(MainActivity.class.getSimpleName(), "Mounted");
       return true;
   }
   return false;
}

    private void doStuffsWithExternalStorage()  {
        File directory  = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyApp");
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.img);
        File imgFile=new File(directory,"img.jpg");
        FileOutputStream fos= null;
        try {
            fos = new FileOutputStream(imgFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}