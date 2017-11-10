package com.example.hp.mynotes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FolderFragment extends Fragment {
    private View rootview;
    private ListView folderlist;
    private ArrayList<folder> myfolderlist;
    private folderAdapter na;
    private FloatingActionButton createfolder;
    private EditText folder_name;
    public FolderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_folder, null);
        return rootview;
    }

    @Override
    public void onStart() {
        super.onStart();
        folderlist = (ListView)rootview.findViewById(R.id.folderlist);
        createfolder = (FloatingActionButton) rootview.findViewById(R.id.creatingfolder);
        /*createfolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),folderActivity.class);
                startActivity(intent);
            }
        });*/
        //createfolder = (Button)rootview.findViewById(R.id.folder_create);
        createfolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View alertview = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_layout, null);
                folder_name = (EditText)alertview.findViewById(R.id.folder_name);
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setView(alertview).setTitle("Enter Folder Name").setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newfoldername = folder_name.getText().toString();
                        folder Folder = new folder(newfoldername, System.currentTimeMillis());
                        Toast.makeText(getActivity(), "Folder Created", Toast.LENGTH_SHORT).show();
                        FolderFragment fragment = new FolderFragment();
                        FragmentManager manager = getFragmentManager();
                        manager.beginTransaction().replace(R.id.fragment_layout, fragment).commit();
                        if(!Utilities.saveFolder(getActivity(), Folder)){
                            Toast.makeText(getActivity(), "cant save", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("Cancel", null).setCancelable(false).create().show();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        folderlist.setAdapter(null);
        myfolderlist = Utilities.getAllSavedFolder(getActivity());
        if(myfolderlist == null || myfolderlist.size() == 0){
            Toast.makeText(getActivity(), "No Folders", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            na = new folderAdapter(getActivity(), R.layout.item_itemfolder, myfolderlist);
            folderlist.setAdapter(na);
            folderlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String foldername = String.valueOf(((folder)folderlist.getItemAtPosition(position)).getDatetime());
                    Intent newintent = new Intent(getActivity(), FolderNote.class);
                    newintent.putExtra("extension", foldername);
                    startActivity(newintent);
                }
            });
            folderlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity())
                            .setTitle("Are you sure?").setMessage("Do you really want to delete this folder?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    final ArrayList<notes> tobedeleted = Utilities.getAllSavedNotes(getActivity(), String.valueOf(((folder) folderlist.getItemAtPosition(position)).getDatetime()));
                                    Utilities.deletefolder(getActivity().getApplicationContext(), ((folder) folderlist.getItemAtPosition(position)).getDatetime() + Utilities.fext);
                                    if (tobedeleted.size() > 0) {
                                        for (int i = 0; i < tobedeleted.size(); i++) {
                                            Utilities.deletenote(getActivity(), String.valueOf(((notes) tobedeleted.get(i)).getDatetime()));
                                        }
                                    }
                                    myfolderlist.remove(position);
                                    na.notifyDataSetChanged();
                                }
                            }).setNegativeButton("No", null).setCancelable(false);
                    dialog.show();
                    return true;
                }
            });
        }
    }
}
