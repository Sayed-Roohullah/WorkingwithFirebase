package com.solutiontech.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
 import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;


public class Videoview extends AppCompatActivity {
    private static final int EXTERNAL_STORAGE_PERMISSION_CODE = 1;

    VideoView video;
    ImageView download;
    private String path = "https://firebasestorage.googleapis.com/v0/b/workingwithfirebase-49162.appspot.com/o/car.mp4?alt=media&token=17a55a02-7ad2-4767-951f-912e60dc8c64";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoview);

        MediaController mediaController = new MediaController(this);
        download = findViewById(R.id.downloadV);
        video = findViewById(R.id.showvideo);
        Uri uri = Uri.parse(path);
        video.setVideoURI(uri);
        video.requestFocus();
        mediaController.setAnchorView(video);
        video.setMediaController(mediaController);
        video.start();



        download.setOnClickListener(v -> {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                PackageManager.PERMISSION_DENIED) {
                            String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                            requestPermissions(permission, EXTERNAL_STORAGE_PERMISSION_CODE);
                        }
                        else {
                            downloadManager(path);
                        }
                    }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case EXTERNAL_STORAGE_PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(Videoview.this, "Permission enable", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void downloadManager(String url) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription("download");
        request.setTitle("");
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,".mp4");

         DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
         manager.enqueue(request);
    }
}