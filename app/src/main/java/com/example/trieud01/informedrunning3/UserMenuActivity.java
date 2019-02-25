package com.example.trieud01.informedrunning3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class UserMenuActivity extends AppCompatActivity {

    private TextView welcomeTextView, createTextView, viewTextView, sharedTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);

        welcomeTextView = findViewById(R.id.welcome_text_view);
        createTextView = findViewById(R.id.create_text_view);
        viewTextView = findViewById(R.id.view_text_view);
        sharedTextView = findViewById(R.id.shared_text_view);

        createTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserMenuActivity.this, RunActivity.class);
                startActivity(intent);
                Log.e("UserMenuActivity", "Going to RunActivity");

            }
        });

        viewTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserMenuActivity.this, RunningLogActivity.class);
                startActivity(intent);
                Log.e("UserMenuActivity", "Going to RunningActivity");

            }
        });

        sharedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserMenuActivity.this, UserMenuActivity.class);
                startActivity(intent);
                Log.e("UserMenuActivity", "Staying in Screen");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_options, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.options_log_out) {

            Backendless.UserService.logout(new AsyncCallback<Void>() {
                @Override
                public void handleResponse(Void response) {
                    Intent intent = new Intent(UserMenuActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Log.e("UserMenuActivity", "Log out Successful");
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Log.e("UserMenuActivity", "Could not log you out");

                }

            });

        }

        if (id == R.id.options_create_run) {
            Intent intent = new Intent(UserMenuActivity.this, RunActivity.class);
            startActivity(intent);


        }
        return true;
    }
}