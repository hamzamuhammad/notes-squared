package com.mural.hamzamuhammad.notes_squared;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

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
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("SUCCESSFUL_LOGIN", true);
        editor.putString("LAST_USER", username);
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
