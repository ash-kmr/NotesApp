package com.example.hp.mynotes;

import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.AlarmClock;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Locale;

public class notesActivity extends AppCompatActivity {
    private EditText notestitle;
    private EditText notesbody;
    private String nameoffile;
    private TextToSpeech tt;
    private notes loadednote;
    private Button texttospeech;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notesmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.setalarm):
                View alarmview = LayoutInflater.from(notesActivity.this).inflate(R.layout.alarmlayout, null);
                final TimePicker timePicker = (TimePicker) alarmview.findViewById(R.id.timePicker);
                AlertDialog.Builder dialog = new AlertDialog.Builder(notesActivity.this);
                dialog.setView(alarmview).setTitle("Alarm").setCancelable(false).setNegativeButton("Cancel", null)
                        .setPositiveButton("Set Alarm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int hours = timePicker.getHour();
                                int minutes = timePicker.getMinute();
                                if (notestitle.length() > 0) {
                                    Toast.makeText(notesActivity.this, String.valueOf(hours), Toast.LENGTH_SHORT).show();
                                    createAlarm(notestitle.getText().toString(), hours, minutes);
                                } else {
                                    createAlarm(notesbody.getText().toString(), hours, minutes);
                                }
                            }
                        }).create().show();
                break;
            case (R.id.sendemail):
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, notestitle.getText().toString() + "\n\n" + notesbody.getText().toString());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
        }
        return super.onOptionsItemSelected(item);
    }
    private void saveNote(){
        notes note;
        if(loadednote == null) {
            note = new notes(System.currentTimeMillis(), notestitle.getText().toString(), notesbody.getText().toString());
        }else{
            note = new notes(loadednote.getDatetime(), notestitle.getText().toString(), notesbody.getText().toString());
        }
        if(!Utilities.saveNote(this, note)){
            Toast.makeText(notesActivity.this, "cant save", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        texttospeech = (Button)findViewById(R.id.texttospeech);
        tt = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status!=TextToSpeech.ERROR){
                    tt.setLanguage(Locale.ENGLISH);
                }
            }
        });
        texttospeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notestitle.getText().toString().length() > 0){
                    tt.speak("The title is",TextToSpeech.QUEUE_FLUSH,null, null);
                    boolean speakingEnd = tt.isSpeaking();
                    do{
                        speakingEnd = tt.isSpeaking();
                    } while (speakingEnd);
                    tt.speak(notestitle.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, null);
                } else{
                    tt.speak("The title is empty", TextToSpeech.QUEUE_FLUSH, null, null);
                }
                boolean speakingEnd1 = tt.isSpeaking();
                do{
                    speakingEnd1 = tt.isSpeaking();
                } while (speakingEnd1);
                if(notesbody.getText().toString().length() > 0){
                    tt.speak("The content is",TextToSpeech.QUEUE_FLUSH, null, null);
                    boolean speakingEnd2 = tt.isSpeaking();
                    do{
                        speakingEnd2 = tt.isSpeaking();
                    } while (speakingEnd2);
                    tt.speak(notesbody.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, null);
                }else{
                    boolean speakingEnd3 = tt.isSpeaking();
                    do{
                        speakingEnd3 = tt.isSpeaking();
                    } while (speakingEnd3);
                    tt.speak("The content is empty.", TextToSpeech.QUEUE_FLUSH, null,null);
                }
            }
        });
        notestitle = (EditText)findViewById(R.id.notetitle);
        notesbody = (EditText)findViewById(R.id.notebody);
        Intent intent = getIntent();
        nameoffile = intent.getStringExtra("notefile");
        if(nameoffile != null && !nameoffile.isEmpty()){
            loadednote = Utilities.getNoteByName(this ,nameoffile);
            if (loadednote != null){
                notestitle.setText(loadednote.getTitle());
                notesbody.setText(loadednote.getContent());
            }
        }
    }
    public void composeEmail(String[] addresses, String subject, String Body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, Body);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    public void createAlarm(String message, int hour, int minutes) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                .putExtra(AlarmClock.EXTRA_HOUR, hour)
                .putExtra(AlarmClock.EXTRA_MINUTES, minutes)
                .putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveNote();
    }
}
