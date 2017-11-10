package com.example.hp.mynotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class folderActivity extends AppCompatActivity {
    private Button button;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder);
        button = (Button)findViewById(R.id.buttoninactivity);
        editText = (EditText)findViewById(R.id.foldernameinactivity);
    }
    private void saveFolder(){

        folder Folder = new folder(editText.getText().toString(),System.currentTimeMillis());

        if(!Utilities.saveFolder(this, Folder)){
            Toast.makeText(folderActivity.this, "cant save", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveFolder();
    }
}
