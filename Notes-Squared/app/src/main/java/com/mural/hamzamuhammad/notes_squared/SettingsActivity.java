package com.mural.hamzamuhammad.notes_squared;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/*
A relatively simple activity that has 3 buttons that allow the user to do some basic settings
management. The XML page is also fairly unsophisticated, but the important functionality is there,
such as clearing the notes, deleting current account, and logout.
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    /*
    Method that will eventually completely wipe the notes stored for the current user. It should
    also ask "ARE YOU SURE?" before doing so.
     */
    public void clearList(View view) {
        //to do
    }

    /*
    Finally, the username value we have been passing from activity to activity is used here. Now this
    activity knows who's account we are on, so we make a SharedPreferences object from the app context
    and now we can make an editor to remove the key/value pair and commit this change. An alert
    dialog is created to inform the user.
     */
    public void deleteAccount(View view) {
        String username = this.getIntent().getStringExtra("USERNAME");
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        int userNumber = sharedPref.getInt(username + "/COUNT", 0);
        editor.remove(username);
        editor.commit();
        ArrayList<String> newFile = new ArrayList<String>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new
                    File(context.getFilesDir(), "userEmails.txt")));
            String currLine = bufferedReader.readLine();
            int count = 1;
            while ((currLine != null)) {
                if (count != userNumber)
                    newFile.add(currLine);
                currLine = bufferedReader.readLine();
                count++;
            }
            bufferedReader.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new
                    File(context.getFilesDir(), "userEmails.txt"), false));
            for (String s : newFile) {
                bufferedWriter.write(s);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        makeAlertDialog("Account deleted");
    }

    //Simply makes an intent and launches LoginActivity
    public void logoutAccount(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    //Same method as in LaunchActivity, but the LoginActivity is started WITHIN the onClickListener
    //so the pop up box does not immediately disappear when it appears.
    private void makeAlertDialog(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(SettingsActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                });
        alertDialog.show();
    }
}
