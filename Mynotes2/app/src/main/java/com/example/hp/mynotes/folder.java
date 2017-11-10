package com.example.hp.mynotes;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hp on 15-06-2017.
 */

public class folder implements Serializable {
    private String foldername;
    private long datetime;

    public folder(String foldername, long datetime) {
        this.foldername = foldername;
        this.datetime = datetime;
    }

    public String getFoldername() {
        return foldername;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setFoldername(String foldername) {
        this.foldername = foldername;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getdtstring(Context context){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy hh:mm:ss", context.getResources().getConfiguration().locale);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date(datetime));
    }
}
