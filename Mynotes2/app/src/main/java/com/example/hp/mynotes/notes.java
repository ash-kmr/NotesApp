package com.example.hp.mynotes;

import android.content.Context;

import java.util.TimeZone;

import java.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hp on 02-06-2017.
 */

public class notes implements Serializable{
    private long datetime;
    private String title;
    private String content;

    public notes(long datetime, String title, String content) {
        this.datetime = datetime;
        this.title = title;
        this.content = content;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDatetime() {
        return datetime;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getdtstring(Context context){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy hh:mm:ss", context.getResources().getConfiguration().locale);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date(datetime));
    }
}
