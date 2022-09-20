package com.solutiontech.myapplication;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PdfFirebase extends AppCompatActivity {
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    StorageReference ref;

    TextView textView;
    ImageView  pdfimg;
    Uri uri = Uri.parse("gs://workingwithfirebase-49162.appspot.com/Personal_Doc.pdf");
    private static final int EXTERNAL_STORAGE_PERMISSION_CODE = 1;

    AppCompatButton downloadpdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_firebase);

        pdfimg = findViewById(R.id.imgpdf);
        textView = findViewById(R.id.texts);
        downloadpdf = findViewById(R.id.downloadbtn);

        storageReference = firebaseStorage.getInstance().getReference();
        ref = storageReference.child("Personal_Doc.pdf");

        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String n = ref.getName();
                pdfimg.setImageResource(R.drawable.pdf);
                textView.setText(n);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        downloadpdf.setOnClickListener(v -> {
            download();
        });

        // generateImageFromPdf(uri);
    }

    private void download() {
        storageReference = firebaseStorage.getInstance().getReference();
        ref = storageReference.child("Personal_Doc.pdf");
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                downloadFile(PdfFirebase.this,"PersonalDoc",".pdf",DIRECTORY_DOWNLOADS,url);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    public void downloadFile(Context context, String filename, String fileExtension, String destinationDir, String url){
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context,destinationDir,filename+fileExtension);

        downloadManager.enqueue(request);
    }

}