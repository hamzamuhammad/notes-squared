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

public class LoginActivity extends AppCompatActivity {


    public final static String SUCCESSFUL_LOGIN = "com.mural.hamzamuhammad.notes_squared.SUCCESSFUL_LOGIN";

    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Context context = getApplicationContext();
        sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_name), Context.MODE_PRIVATE);
    }

    public void automaticallyLogin(View view) {
        SharedPreferences.Editor editor = sharedPref.edit();
        String autoLoginKeyStringId = getString(R.string.auto_login_key);
        int defaultValue = 2;
        int autoLoginStatus = sharedPref.getInt(autoLoginKeyStringId, defaultValue);
        if (autoLoginStatus == 1)
            editor.putInt(autoLoginKeyStringId, 0);
        else
            editor.putInt(autoLoginKeyStringId, 1);
        editor.commit();
    }

    public void pressLogin(View view) {
        //whenever user logins, we need to check A. whether the username is valid and B. whether
        //the user/password combo is valid.
        Intent intent = new Intent(this, NoteActivity.class);
        Boolean isLogin = false;
        EditText usernameEditText = (EditText) findViewById(R.id.username_value);
        EditText passwordEditText = (EditText) findViewById(R.id.password_value);
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String defaultValue = "";
        String usernameKey = sharedPref.getString(username, defaultValue);
        if (usernameKey.equals(defaultValue))
            makeAlertDialog("Username does not exist");
        else if (!password.equals(usernameKey))
            makeAlertDialog("Incorrect password");
        else
            isLogin = true;
        intent.putExtra(SUCCESSFUL_LOGIN, true);
        intent.putExtra("USERNAME", username);
        if (isLogin)
            startActivity(intent);
    }

    public void pressRegister(View view) {
        //since the user is trying to make a new account, we redirect them to a new activity
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

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
