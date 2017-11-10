package com.example.hp.mynotes;

import android.content.Context;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 15-06-2017.
 */

public class folderAdapter extends ArrayAdapter<folder> {
    public folderAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<folder> objects) {
        super(context, resource, objects);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_itemfolder, null);
        }
        folder Folder = getItem(position);
        if (Folder != null){
            TextView ftitle = (TextView)convertView.findViewById(R.id.ftitle);
            TextView fdate = (TextView)convertView.findViewById(R.id.fdate);
            ftitle.setText(Folder.getFoldername());
            fdate.setText(Folder.getdtstring(getContext()));
        }
        return convertView;
    }
}
