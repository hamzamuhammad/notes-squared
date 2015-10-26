package com.mural.hamzamuhammad.notes_squared;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Set;

public class EditActivity extends AppCompatActivity {

    private String notes;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        notes = this.getIntent().getStringExtra("USER_NOTE");
        username = this.getIntent().getStringExtra("USERNAME");
        EditText editText = (EditText) findViewById(R.id.edit_text);
        editText.setText(notes, TextView.BufferType.EDITABLE);
    }

    public void onSaveClick(View view) {
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Set<String> userNotes = sharedPref.getStringSet(username + "_NOTES", null);
        assert userNotes != null;
        EditText editText = (EditText) findViewById(R.id.edit_text);
        userNotes.add(editText.getText().toString());
        editor.putStringSet(username + "_NOTES", userNotes);
        editor.apply();
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra("USERNAME", username);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra("USERNAME", username);
        startActivity(intent);
    }
}
