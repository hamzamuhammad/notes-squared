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

/*
Often the first activity a new user interacts with. It's main purpose is to check whether the user
has entered valid account information, whether they want to automatically login next time the app
is launched, and also redirect the user to a registration page if they don't have an account.
 */
public class LoginActivity extends AppCompatActivity {

    //global string to maintain consistency, and a global SharedPreferences object to be used throughout program
    public final static String SUCCESSFUL_LOGIN = "com.mural.hamzamuhammad.notes_squared.SUCCESSFUL_LOGIN";
    private SharedPreferences sharedPref;

    /*
    Whenever the app is made, get the app context and gain access to the shared preferences file
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Context context = getApplicationContext();
        sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_name), Context.MODE_PRIVATE);
    }

    /*
    An onClick method (which is specified in the XML page, activity_login.xml) that does the following
    if the auto login checkbox is selected. First, we made a shared preferences editor to modify
    the file. Then, we say that the default value if no key/value pair is found is 2 (meaning the
    user is using the app for the first time). Now, if we get 1, that means the user selected yes
    initially and him clicking it again (DESELECTING the checkbox) should change it to no, so we
    put 0 as the integer. Note that 0 is no, 1 is yes, and 2 is null (or first time user). Otherwise,
    in any other case (such as a first time checkbox select or saying 'yes' for the auto login option)
    set the integer to 1. Then (IMPORTANT) commit the editor changes.
     */
    public void automaticallyLogin(View view) {
        SharedPreferences.Editor editor = sharedPref.edit();
        String autoLoginKeyStringId = getString(R.string.auto_login_key);
        int defaultValue = 2;
        int autoLoginStatus = sharedPref.getInt(autoLoginKeyStringId, defaultValue);
        if (autoLoginStatus == 1)
            editor.putInt(autoLoginKeyStringId, 0);
        else
            editor.putInt(autoLoginKeyStringId, 1);
        editor.apply();
    }

    /*
    Crucial method that checks whether what the information a user entered in is either correct,
    or not associated to any account. We make an intent to the main activity, NoteActivity and
    create a boolean isLogin to determine whether the user passed the checks. We then get the String
    values the user typed in, the username and password, and then we get the username and password
    values from the shared preferences file. Again, if no key/value pair exists, the default value
    is a blank string "". If we get the default value, meaning no account exists, we inform the user
    that the account doesn't exist (meaning a non existent username was typed in). If the username
    does exist, but the password is wrong, inform user that the password is incorrect. Otherwise,
    the information is true and set isLogin to true. So, we then add info to the intent, that the
    login was successful (to tell the LaunchActivity that a user has logged in at least once) and
    we pass on the user username to the next activity. If the user logged in, then launch the activity.
     */
    public void pressLogin(View view) {
        Intent intent = new Intent(this, NoteActivity.class);
        Boolean isLogin = false;
        EditText usernameEditText = (EditText) findViewById(R.id.username_value);
        EditText passwordEditText = (EditText) findViewById(R.id.password_value);
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String defaultValue = "";
        String usernameKey = sharedPref.getString(username, defaultValue);
        if (usernameKey.equals(defaultValue))
            makeAlertDialog("Account does not exist");
        else if (!password.equals(usernameKey))
            makeAlertDialog("Incorrect password");
        else
            isLogin = true;
        intent.putExtra(SUCCESSFUL_LOGIN, true);
        intent.putExtra("USERNAME", username);
        if (isLogin)
            startActivity(intent);
    }

    //Simple method that makes an intent to send the user to the RegisterActivity.
    public void pressRegister(View view) {
        //since the user is trying to make a new account, we redirect them to a new activity
        Intent intent = new Intent(this, RegisterActivity.class);
        EditText usernameField = (EditText) findViewById(R.id.username_value);
        String username = usernameField.getText().toString();
        intent.putExtra("DESIRED_USERNAME", username);
        startActivity(intent);
    }

    /*
    Code copied from a StackOverflow thread that makes an AlertDialog, but modified to generate a
    dialog based on the sent String parameter.
     */
    private void makeAlertDialog(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
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
