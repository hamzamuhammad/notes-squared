package com.mural.hamzamuhammad.notes_squared;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/*
Important activity that takes user account information (email address, username, and password) and
stores the username/password in the shared preferences file, TEMP. The email address issue HAS NOT
been taken care of yet.
 */
public class RegisterActivity extends AppCompatActivity {

    //boolean that stores whether a user has selected the user agreement checkbox or not
    private static boolean USER_AGREEMENT_VALID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        USER_AGREEMENT_VALID = false;
        Bundle extras = this.getIntent().getExtras(); //we see if our intent has any extras, if
        // so we put that into the edittext text
        String desiredUsername;
        if (extras != null) {
            desiredUsername = this.getIntent().getStringExtra("DESIRED_USERNAME");
            EditText usernameField = (EditText) findViewById(R.id.username_value);
            usernameField.setText(desiredUsername, TextView.BufferType.EDITABLE);
        }
    }

    //simply flip the boolean based on the onClick event of the checkbox
    public void userAgreement(View view) {
        USER_AGREEMENT_VALID = !USER_AGREEMENT_VALID;
    }

    /*
    This method creates an account by first checking whether the user has agreed to the user agreement,
    and informs the user in a pop up that you cannot make an account without agreeing to it. We need
    a context to edit the shared preferences file, so if the agreement is valid we create an editor
    through the usual means and then obtain the strings entered in by the user (found by the resource
    id specified in the XML file). We have a default value to tell us if the username has been taken
    or not (if the key/pair value exists, it is taken). If it is not taken, simply enter the key/value
    pair in the shared pref file and alert the user of the registration. In addition, create an
    intent that has the current username (this is used in LaunchActivity) attached to it and start
    NoteActivity. Commit changes at the end
     */
    public void createAccount(View view) { //need to do something with the email!
        if (USER_AGREEMENT_VALID) {
            Context context = getApplicationContext();
            SharedPreferences sharedPref = context.getSharedPreferences(
                    getString(R.string.preference_file_name), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            EditText usernameEditText = (EditText) findViewById(R.id.username_value);
            EditText passwordEditText = (EditText) findViewById(R.id.password_value);
            EditText emailEditText = (EditText) findViewById(R.id.email_address);
            EditText secondPasswordEditText = (EditText) findViewById(R.id.second_password_value);
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String secondPassword = secondPasswordEditText.getText().toString();
            String defaultValue = "";
            if (!username.equals("") && !password.equals("")) { //do we need to check condition #2?
                if (sharedPref.getString(username, defaultValue).equals(defaultValue) && password
                        .equals(secondPassword)) {
                    editor.putString(username, password);
                    //this is where we put the email in text file
                    try {
                        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new
                                File(context.getFilesDir(), "userEmails.txt"), true));
                        bufferedWriter.write(email);
                        bufferedWriter.newLine();
                        bufferedWriter.close();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    makeAlertDialog("Registration complete");
                    Intent intent = new Intent(this, NoteActivity.class);
                    intent.putExtra("USERNAME", username);
                    startActivity(intent); //do we need to commit changes BEFORE we call the intent???
                }
                else if (!(password.equals(secondPassword)))
                    makeAlertDialog("Passwords do not match");
                else
                    makeAlertDialog("Username already exists, please select another one");
            }
            editor.commit();
        }
        else //copied from stack overflow, http://stackoverflow.com/questions/26097513/android-simple-alert-dialog
            makeAlertDialog("You must accept the user agreement to create an account");
    }

    //Same method as LaunchActivity
    private void makeAlertDialog(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }


}
