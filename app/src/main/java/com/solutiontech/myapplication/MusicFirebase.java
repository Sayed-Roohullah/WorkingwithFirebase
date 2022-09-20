package com.solutiontech.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
public class MusicFirebase extends AppCompatActivity implements MediaPlayer.OnPreparedListener,MediaController.MediaPlayerControl {
        private static final String TAG = "AudioPlayer";

        StorageReference storageReference;
        StorageReference ref;
        private MediaPlayer mediaPlayer;
        private MediaController mediaController;
        private String audioFile="https://firebasestorage.googleapis.com/v0/b/workingwithfirebase-49162.appspot.com/o/mehrab.mp3?alt=media&token=67ca348b-d1a0-48dc-9329-00031a049b4f";
        ;

        private Handler handler = new Handler();



        public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_music_firebase);

                ((TextView) findViewById(R.id.now_playing_text)).setText(audioFile);

                 mediaPlayer = new MediaPlayer();
                mediaPlayer.setOnPreparedListener(this);

                mediaController = new MediaController(this);

                try {
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediaPlayer.setDataSource(audioFile);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                 } catch (IOException e) {
                        Log.e(TAG, "Could not open file " + audioFile + " for playback.", e);
                }

//                storageReference = FirebaseStorage.getInstance().getReference();
//                ref = storageReference.child("mehrab.mp3");
//                //StorageReference filepath=storage.child("Audio").child(timeStamp+".mp3");
//                Uri uri = Uri.fromFile(new File(fileName));
//                ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                String audio_url = ref.getDownloadUrl().toString();
//
//                        }
//                }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//
//                        }
//                });
        }


        @Override
        protected void onStop() {
                super.onStop();
                mediaController.hide();
                mediaPlayer.stop();
                mediaPlayer.release();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
                //the MediaController will hide after 3 seconds - tap the screen to make it appear again
                mediaController.show();
                return false;
        }

        @Override
        public int getAudioSessionId() {
                return 0;
        }

        public void start() {
                mediaPlayer.start();
        }

        public void pause() {
                mediaPlayer.pause();
         }

        public int getDuration() {
                return mediaPlayer.getDuration();
        }

        public int getCurrentPosition() {
                return mediaPlayer.getCurrentPosition();
        }

        public void seekTo(int i) {
                mediaPlayer.seekTo(i);
        }

        public boolean isPlaying() {
                return mediaPlayer.isPlaying();
         }

        public int getBufferPercentage() {
                return 0;
        }

        public boolean canPause() {
                return true;
        }

        public boolean canSeekBackward() {
                return true;
        }

        public boolean canSeekForward() {
                return true;
        }
        //--------------------------------------------------------------------------------

        public void onPrepared(MediaPlayer mediaPlayer) {
                Log.d(TAG, "onPrepared");
                mediaController.setMediaPlayer(this);
                mediaController.setAnchorView(findViewById(R.id.main_audio_view));

                handler.post(new Runnable() {
                        public void run() {
                                mediaController.setEnabled(true);
                                mediaController.show();
                        }
                });
        }


}
