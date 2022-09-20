package com.solutiontech.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;

import com.solutiontech.myapplication.activity.Notes;

public class MainActivity extends AppCompatActivity {


      @Override
    protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);

      }

    public void onImage(View view) {
          Intent intent = new Intent(MainActivity.this,ImgFirebase.class);
          startActivity(intent);
    }

    public void onMusic(View view) {
        Intent intent = new Intent(MainActivity.this,MusicFirebase.class);
        startActivity(intent);

    }

    public void onVideo(View view) {
        Intent intent = new Intent(MainActivity.this,Videoview.class);
        startActivity(intent);
    }

    public void onPdf(View view) {
        Intent intent = new Intent(MainActivity.this,PdfFirebase.class);
        startActivity(intent);
    }

    public void onUpload(View view) {
        Intent intent = new Intent(MainActivity.this,Uploadto.class);
        startActivity(intent);
    }

    public void onNotes(View view) {
        Intent intent = new Intent(MainActivity.this, Notes.class);
        startActivity(intent);
    }
}



