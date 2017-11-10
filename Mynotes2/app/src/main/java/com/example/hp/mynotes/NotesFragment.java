package com.example.hp.mynotes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.example.hp.mynotes.R;
import com.example.hp.mynotes.Utilities;
import com.example.hp.mynotes.noteAdapter;
import com.example.hp.mynotes.notes;
import com.example.hp.mynotes.notesActivity;

import java.util.ArrayList;


public class NotesFragment extends Fragment {
    private ListView listView;
    noteAdapter na;
    private View rootview;
    private String ext = Utilities.ext;
    private FloatingActionButton new_note;
    public NotesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_notes, container, false);
        return rootview;
    }

    @Override
    public void onStart() {
        super.onStart();
        listView = (ListView)rootview.findViewById(R.id.listView);
        new_note = (FloatingActionButton) rootview.findViewById(R.id.new_note);
        new_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent noteintent = new Intent(getActivity(), notesActivity.class);
                startActivity(noteintent);
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        listView.setAdapter(null);
        final ArrayList<notes> noteslist = Utilities.getAllSavedNotes(getActivity(), ext);
        if (noteslist == null || noteslist.size() == 0){
            Toast.makeText(getActivity(), "no notes", Toast.LENGTH_SHORT).show();
            return;
        }else{
            na = new noteAdapter(getActivity(), R.layout.items_itemnote, noteslist);
            listView.setAdapter(na);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String filename = ((notes)listView.getItemAtPosition(position)).getDatetime() + ext;
                    Intent viewintent = new Intent(getActivity(), notesActivity.class);
                    viewintent.putExtra("notefile", filename);
                    startActivity(viewintent);
                }
            });
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position2, long id) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity())
                            .setTitle("Are you sure?")
                            .setMessage("Do you really want to delete this note?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Utilities.deletenote(getActivity().getApplicationContext(), ((notes)listView.getItemAtPosition(position2)).getDatetime() + Utilities.ext);
                                    Toast.makeText(getActivity(), "Note was deleted", Toast.LENGTH_SHORT).show();
                                    NotesFragment fragment = new NotesFragment();
                                    FragmentManager manager = getFragmentManager();
                                    manager.beginTransaction().replace(R.id.fragment_layout, fragment).commit();
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
}