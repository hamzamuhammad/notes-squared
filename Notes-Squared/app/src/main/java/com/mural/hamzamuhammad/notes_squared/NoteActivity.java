package com.mural.hamzamuhammad.notes_squared;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/*
Since we have not developed a concrete concept of what this Activity does, we choose to leave the
comments blank. An important thing to note is that whichever activity calls this sends an intent
that has a username String attached to it, which this activity uses to not only say that a
successful login has occurred but the last user to successfully login (which is whoever sent the
intent). In addition the ActionBar is utilized by making the setting button redirect to the
SettingsActivity, along with an intent that contains the current username (which allows the delete
account method to function by telling it who's account we are deleting).
 */
public class NoteActivity extends AppCompatActivity {

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        username = this.getIntent().getStringExtra("USERNAME");
        Log.d("USER VAL", username);
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("SUCCESSFUL_LOGIN", true);
        editor.putString("LAST_USER", username);
        editor.apply();
        ArrayList<String> userNotes = new ArrayList<String>();
        //need to get set from preferences, and sort that shit
        Set<String> notes = sharedPref.getStringSet(username + "_NOTES", new HashSet<String>());
        for (String s : notes)
            userNotes.add(s);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout
                .simple_list_item_1, userNotes);
        ListView listView = (ListView) findViewById(R.id.note_view);
        final Intent intent = new Intent(this, EditActivity.class);
        // Create a message handling object as an anonymous class.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                String note = parent.getItemAtPosition(position).toString();
                intent.putExtra("USER_NOTE", note);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
                return true;
            case R.id.new_note:
                intent = new Intent(this, EditActivity.class);
                intent.putExtra("USER_NOTE", "New Note");
                intent.putExtra("USERNAME", username);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}