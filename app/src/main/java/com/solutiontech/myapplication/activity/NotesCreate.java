package com.solutiontech.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.solutiontech.myapplication.R;

import java.util.HashSet;

public class NotesCreate extends AppCompatActivity {

    EditText editText;
    int noteId ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_create);

        editText = findViewById(R.id.editnote);
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId",-1);
        if (noteId != -1){
            editText.setText(Notes.notes.get(noteId));
        }else{
            Notes.notes.add("");
            Notes.adapter.notifyDataSetChanged();
            noteId = Notes.notes.size() -1;
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Notes.notes.set(noteId,String.valueOf(s));
                Notes.adapter.notifyDataSetChanged();
                SharedPreferences sharedPreferences = getSharedPreferences("notes", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<String>(Notes.notes);
                sharedPreferences.edit().putStringSet("note",set).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}