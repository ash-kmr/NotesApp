package com.example.hp.mynotes;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by hp on 03-06-2017.
 */

public class Utilities {
    public static final String ext = "121.txt";
    public static final String fext = ".fld";
    public static boolean savefoldernote(Context context, notes note, String extension){
        String Filename = String.valueOf(note.getDatetime()+extension);
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;
        try{
            fileOutputStream = context.openFileOutput(Filename, context.MODE_PRIVATE);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(note);
            objectOutputStream.close();
            fileOutputStream.close();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static boolean saveNote(Context context, notes note ){
        String Filename = note.getDatetime()+ ext;
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;
        try{
            fileOutputStream = context.openFileOutput(Filename, context.MODE_PRIVATE);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(note);
            objectOutputStream.close();
            fileOutputStream.close();
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean saveFolder(Context context, folder Folder) {
        String Foldername = Folder.getDatetime() + fext;
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;
        try {
            fileOutputStream = context.openFileOutput(Foldername, context.MODE_PRIVATE);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(Folder);
            objectOutputStream.close();
            fileOutputStream.close();
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static ArrayList<notes> getAllSavedNotes(Context context, String extension){
        ArrayList<notes> noted = new ArrayList<>();
        File filesdir = context.getFilesDir();
        ArrayList<String> notefiles = new ArrayList<>();
        for(String file : filesdir.list()){
            if (file.endsWith(extension)){
                notefiles.add(file);
            }
        }
        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;
        for(int i = 0; i < notefiles.size(); i++){
            try {
                fileInputStream = context.openFileInput(notefiles.get(i));
                objectInputStream = new ObjectInputStream(fileInputStream);
                noted.add((notes)objectInputStream.readObject());
                fileInputStream.close();
                objectInputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        return noted;
    }
    public static ArrayList<folder> getAllSavedFolder(Context context){
        ArrayList<folder> foldered = new ArrayList<>();
        File filesdir = context.getFilesDir();
        ArrayList<String> folderfiles = new ArrayList<>();
        for(String folders : filesdir.list()){
            if(folders.endsWith(fext)){
                folderfiles.add(folders);
            }
        }
        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;
        for (int i = 0; i < folderfiles.size(); i++){
            try{
                fileInputStream = context.openFileInput(folderfiles.get(i));
                objectInputStream = new ObjectInputStream(fileInputStream);
                foldered.add((folder)objectInputStream.readObject());
                fileInputStream.close();
                objectInputStream.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return foldered;
    }
    public static folder getFolderByName(Context context, String foldername){
        File file = new File(context.getFilesDir(), foldername);
        folder foldered = null;
        if(file.exists()){
            FileInputStream fileInputStream;
            ObjectInputStream objectInputStream;
            try{
                fileInputStream = context.openFileInput(foldername);
                objectInputStream = new ObjectInputStream(fileInputStream);
                foldered = (folder)objectInputStream.readObject();
                fileInputStream.close();
                objectInputStream.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return foldered;
    }
    public static notes getNoteByName(Context context, String filename){
        File file = new File(context.getFilesDir(), filename);
        notes noted = null;
        if(file.exists()){
            FileInputStream fileInputStream;
            ObjectInputStream objectInputStream;
            try {
                fileInputStream = context.openFileInput(filename);
                objectInputStream = new ObjectInputStream(fileInputStream);
                noted = (notes) objectInputStream.readObject();
                fileInputStream.close();
                objectInputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return noted;
        }
        return null;
    }

    public static void deletenote(Context context, String filename) {
        File file = new File(context.getFilesDir(), filename);
        if (file.exists()){
            file.delete();
        }
    }
    public static void deletefolder(Context context, String foldername){
        File file = new File(context.getFilesDir(), foldername);
        if (file.exists()){
            file.delete();
        }
    }
}
