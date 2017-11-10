package com.example.hp.mynotes;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FolderNote extends AppCompatActivity {
    private ListView notesinfolder;
    private String extension;
    private FloatingActionButton newfoldernote;
    private ArrayList<notes> foldernoteslist;
    foldernoteAdapter na;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_note);
        notesinfolder = (ListView)findViewById(R.id.notesinfolder);
        Intent initiatorintent = getIntent();
        extension = initiatorintent.getStringExtra("extension");
        newfoldernote = (FloatingActionButton) findViewById(R.id.newfoldernote);
        newfoldernote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FolderNote.this, FolderNotesActivityNew.class);
                intent.putExtra("extension", extension);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        notesinfolder.setAdapter(null);
        foldernoteslist = Utilities.getAllSavedNotes(FolderNote.this, extension);
        if(foldernoteslist == null || foldernoteslist.size() == 0){
            Toast.makeText(FolderNote.this, "No notes", Toast.LENGTH_SHORT).show();
            return;
        }else{
            na = new foldernoteAdapter(FolderNote.this, R.layout.items_itemnote, foldernoteslist);
            notesinfolder.setAdapter(na);
            notesinfolder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String filename = ((notes)notesinfolder.getItemAtPosition(position)).getDatetime()+extension;
                    Intent intent = new Intent(FolderNote.this, FolderNotesActivityNew.class);
                    intent.putExtra("filename", filename);
                    intent.putExtra("extension", extension);
                    startActivity(intent);
                }
            });
            notesinfolder.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(FolderNote.this)
                            .setTitle("Are you sure?")
                            .setMessage("Do you really want to delete this note?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Utilities.deletenote(FolderNote.this, ((notes)notesinfolder.getItemAtPosition(position)).getDatetime() + extension);
                                    Toast.makeText(FolderNote.this, "Note was deleted", Toast.LENGTH_SHORT).show();
                                    recreate();
                                }
                            })
                            .setNegativeButton("No", null)
                            .setCancelable(false);
                    dialog.show();
                    return true;
                }
            });
        }
    }

    @Override
    public void recreate() {
        super.recreate();
    }
}
