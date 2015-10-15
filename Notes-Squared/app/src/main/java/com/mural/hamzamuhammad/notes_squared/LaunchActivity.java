package com.mural.hamzamuhammad.notes_squared;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.File;

/*
This is the first activity that is launched whenever the app is opened. It's main purpose is to
decide which activity to start first, based on whether the user has used the app before and what
settings he used.
 */
public class LaunchActivity extends AppCompatActivity { //appcompatactivity helps maintain compatibility for older android devices

    /*
    Only useful method to override is the onCreate one, since when the activity is created the
    following actions must take place. First, we need the app context in order to access a private
    shared preferences file named TEMP (which stores information as key/value pairs). Then, we make
    an intent to call the activity LoginActivity which brings the user to the main login page.
    However, we need to check whether a user has successfully logged into the app before AND that
    they selected the 'log in automatically' option. A key value pair of type boolean is made when
    the user actually gets to the NoteActivity (more on this later) and if it's true along with the
    auto login option being 1 (or yes), change the intent from LoginActivity to NoteActivity, the
    main activity of the app (skipping the part of logging in again). Another crucial thing to do
    is to get the username of the most recent user who used the app, and send it to the NoteActivity
    which will eventually need this (explained in detail later on). Then we simply start the intent.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Intent intent = new Intent(this, LoginActivity.class);
        if (sharedPref.getBoolean("SUCCESSFUL_LOGIN", false) &&
                (sharedPref.getInt(getString(R.string.auto_login_key), 2) == 1)) {
            intent = new Intent(this, NoteActivity.class);
            String username = sharedPref.getString("LAST_USER", "");
            intent.putExtra("USERNAME", username);
        }
        if (!sharedPref.getBoolean("NOT_FIRST_LAUNCH", false)) {
            File file = new File(context.getFilesDir(), "userEmails.txt");
            editor.putBoolean("NOT_FIRST_LAUNCH", true);
            editor.commit();
        }
        startActivity(intent);
    }
}
