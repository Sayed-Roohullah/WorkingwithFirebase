package com.solutiontech.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.solutiontech.myapplication.MainActivity;
import com.solutiontech.myapplication.R;
import com.solutiontech.myapplication.Uploadto;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashSet;

public class Notes extends AppCompatActivity {
    static ArrayList<String> notes = new ArrayList<>();
    static ArrayAdapter adapter;
    SharedPreferences sharedPreferences;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        listView = findViewById(R.id.listview);
        sharedPreferences = getSharedPreferences("notes", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("note",null);

        if (set == null){
            notes.add("add new notes");
        }else{
            notes = new ArrayList<>(set);
        }
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,notes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Notes.this, NotesCreate.class);
                intent.putExtra("noteId",position);
                startActivity(intent);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final int itemToRemoved = position;
                new AlertDialog.Builder(Notes.this)
                        .setTitle("Are you Sure")
                        .setMessage("Do you want delete this note ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                notes.remove(itemToRemoved);
                                adapter.notifyDataSetChanged();
                                SharedPreferences sharedPreferences = getSharedPreferences("notes",Context.MODE_PRIVATE);
                                HashSet<String> strings = new HashSet<>(Notes.notes);
                                sharedPreferences.edit().putStringSet("note",strings).apply();
                            }
                        }).setNegativeButton("No",null)
                        .show();

             }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.addnote){
            Intent intent = new Intent(Notes.this, NotesCreate.class);
            startActivity(intent);
            return true;
        }
        return false;
    }
}