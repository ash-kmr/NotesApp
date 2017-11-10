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

/**
 * Created by hp on 03-06-2017.
 */

public class noteAdapter extends ArrayAdapter<notes>{

    public noteAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<notes> objects) {
        super(context, resource, objects);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.items_itemnote, null);
        }
        notes note = getItem(position);
        if (note!= null){
            TextView title = (TextView)convertView.findViewById(R.id.list_notetitle);
            TextView date = (TextView)convertView.findViewById(R.id.list_notedate);
            title.setText(note.getTitle());
            date.setText(note.getdtstring(getContext()));
        }
        return convertView;
    }
}
