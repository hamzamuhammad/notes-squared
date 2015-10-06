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

public class RegisterActivity extends AppCompatActivity {

    private static boolean USER_AGREEMENT_VALID = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void userAgreement(View view) {
        USER_AGREEMENT_VALID = !USER_AGREEMENT_VALID;
    }

    public void createAccount(View view) { //need to do something with the email!
        Context context = getApplicationContext();
        if (USER_AGREEMENT_VALID) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    getString(R.string.preference_file_name), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            EditText usernameEditText = (EditText) findViewById(R.id.username_value);
            EditText passwordEditText = (EditText) findViewById(R.id.password_value);
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String defaultValue = "";
            if (!username.equals("") && !password.equals("")) { //need to make a loop that will keep asking?
                if (sharedPref.getString(username, defaultValue).equals(defaultValue)) {
                    editor.putString(username, password);
                    makeAlertDialog("Registration complete");
                    Intent intent = new Intent(this, NoteActivity.class);
                    intent.putExtra("USERNAME", username);
                    startActivity(intent);
                }
                else
                    makeAlertDialog("Username already exists, please select another one");
            }
            editor.commit();
        }
        else //copied from stack overflow, http://stackoverflow.com/questions/26097513/android-simple-alert-dialog
            makeAlertDialog("You must accept the user agreement to create an account");
    }

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
