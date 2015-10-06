package com.mural.hamzamuhammad.notes_squared;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //at the beginning, we need to check a bunch of things before we decide
        //which activity to start. if there IS an existing account AND the checkbox button is
        //checked, then go straight to noteactivity.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_name), Context.MODE_PRIVATE);
        Intent intent = new Intent(this, LoginActivity.class);
        if (sharedPref.getBoolean("SUCCESSFUL_LOGIN", false) &&
                (sharedPref.getInt(getString(R.string.auto_login_key), 2) == 1)) {
            intent = new Intent(this, NoteActivity.class);
            String username = sharedPref.getString("LAST_USER", "");
            intent.putExtra("USERNAME", username);
        }
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_launch, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
